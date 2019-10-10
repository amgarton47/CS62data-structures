import copy
import json
import subprocess
import logging
import collections

from flask import Flask, render_template, abort, request, \
    redirect, url_for

from runhelpers import JavaPackageRunner, Submission, convertHtmlToPdf
from setuputilities import make_temp_directory, fix_permissions

def run_server(classroom, specs):
    sorted_classroom = collections.OrderedDict(sorted(classroom.items()))
    output_dir = os.path.join(os.getcwd(), '_output')
    if not os.path.exists(output_dir):
        os.mkdir(output_dir)
    fix_permissions(output_dir)

    app = Flask(__name__)
    # TODO - Don't assume we have a runner or that it is a java package runner
    autos = os.path.join(os.getcwd(), '_autos')
    if not os.path.exists(autos):
        autos = None

    jp = JavaPackageRunner(specs.runner['package'], specs.runner['mainclass'],
                           autos)

    @app.route('/')
    def landing():
        return render_template('welcome.html', title=specs.title,
                               classroom=sorted_classroom, specs=specs)

    @app.route('/grade/<dci>/', methods=['POST'])
    def student_page(dci):
        if dci in classroom:
            student_results = copy.deepcopy(specs.tests)

            # run autos
            sub_path = os.path.join(os.getcwd(), dci)
            out_path = os.path.join(output_dir, dci + '.autos')

            run_results = None
            autodetect = None

            if os.path.exists(sub_path):
                autodetect = sub_path
                if autos:
                    print("Running autos for student '{}'...".format(dci))
                    try:
                        sub = Submission(sub_path, None)
                        jp.run_autos(out_path, sub)
                        fix_permissions(out_path)
                        jp.destroy_current_build()
                        with open(out_path, 'r') as raw:
                            run_results = json.load(raw)
                        print("  ...autos succeeded.")
                    except:
                        print("  ...autos FAILED!")
                        pass

                subprocess.call(['open', sub_path])

            # Get any pre-seeded run results if no new ones generated
            if not run_results and os.path.exists(out_path):
                print(
                    "Looking for pre-existing autos for student '{}'...".format(
                        dci
                    )
                )
                try:
                    with open(out_path, 'r') as raw:
                        run_results = json.load(raw)
                    print("  ...found pre-existing autos.")
                except:
                    print("  ...no pre-existing autos available.")
                    pass

            # TODO use existent dictionaries
            if run_results:
                for test in student_results:
                    try:
                      if test in run_results['passes']:
                          student_results[test]['earned'] = specs.tests[test][
                              'score']
                          student_results[test][
                              'comment'] = 'Automatically passed.'
                      else:
                          a_test = next(
                            (a_test for a_test in run_results['failures'] if
                             a_test['testname'] == test), None)

                          if a_test:
                              student_results[test]['earned'] = 0.0
                              student_results[test]['comment'] = (
                                a_test['message']
                                if 'message' in a_test
                                else "No message available (run this test manually, or maybe check for a missing constructor?)"
                              )
                              student_results[test]['trace'] = a_test['trace']
                    except Exception as e:
                      logging.exception("Python error interpreting results.")
                      student_results[test]['earned'] = 0.0
                      student_results[test]['comment'] = 'ERROR (try to test this feature manually).'

            return render_template('grading.html', student=classroom[dci],
                                   specs=specs, results=student_results,
                                   autodetect=autodetect)
        else:
            abort(404)

    @app.route('/grade/<dci>/run/', methods=['POST'])
    def run_submission(dci):
        sub_path = os.path.join(os.getcwd(), dci)
        if os.path.exists(sub_path) and dci in classroom:
            try:
                sub = Submission(sub_path, None)
                cliargs = None
                if 'args' in request.form:
                    cliargs = request.form['args']

                jp.run_manual(sub, cliargs)
                jp.destroy_current_build()
                return 'running'
            except:
                abort(500)
        else:
            abort(404)

    @app.route('/grade/<dci>/rubric', methods=['POST'])
    def save_rubric(dci):
        if dci in classroom:
            sub_path = os.path.join(os.getcwd(), dci)
            with make_temp_directory() as temp_dir:
                final_output = os.path.join(output_dir, dci + '.pdf')
                output_file = os.path.join(temp_dir, 'rubric.pdf')
                output_json = os.path.join(output_dir, dci + '.json')
                final_results = copy.deepcopy(specs.tests)
                possible_score, earned_score = 0.0, 0.0

                for i in final_results:
                    comment_key = 'temp__comment__' + str(
                            final_results[i]['_id'])
                    earned_key = 'temp__earned__' + str(
                            final_results[i]['_id'])

                    if not request.form[earned_key]:
                        final_results[i]['earned'] = 0.0
                    else:
                        final_results[i]['earned'] = float(
                                request.form[earned_key])

                    if request.form[comment_key] is None:
                        final_results[i]['comment'] = 'N/A'
                    else:
                        final_results[i]['comment'] = str(
                                request.form[comment_key])

                    possible_score += final_results[i]['score']
                    earned_score += final_results[i]['earned']

                possible_flags = ['flag_quality', 'flag_general',
                                  'flag_dishonesty', 'flag_regrade']
                flagged = []

                for flag in possible_flags:
                    if flag in request.form:
                        flagged.append(flag)

                with open(output_json, 'w') as a_file:
                    json.dump({'tests': final_results,
                               'earned_score': earned_score, 'flags': flagged},
                              a_file, indent=4)
                    classroom[dci].complete = True
                fix_permissions(output_json)

                src_files = []
                if os.path.exists(sub_path):
                    src_files = get_source_files(sub_path, '.java')
                    src_files.extend(get_source_files(sub_path, '.c'))
                    src_files.extend(get_source_files(sub_path, '.h'))

                convertHtmlToPdf(
                        render_template('rubric.html', results=final_results,
                                        score=(earned_score, possible_score),
                                        comments=request.form["comments"],
                                        student=classroom[dci]),
                        output_file)

                make_pdf(temp_dir, src_files, final_output)
                fix_permissions(final_output)

            return redirect(url_for('landing'))

    app.run()


import os


def get_source_files(dir, suffix):
    the_files = []
    for root, dirs, files in os.walk(dir):
        for file in files:
            if file.endswith(suffix):
                the_files.append(os.path.join(root, file))

    return the_files


def make_pdf(temp_dir, source, outfile):
    rubric_pdf = os.path.join(temp_dir, 'rubric.pdf')
    source_pdf = os.path.join(temp_dir, 'source.pdf')

    pdf_source(source_pdf, source, temp_dir)
    gs = [
        'gs', '-q', '-dBATCH', '-dNOPAUSE', '-sDEVICE=pdfwrite',
        '-sOutputFile=' + outfile,
        rubric_pdf, os.path.join(temp_dir, 'source.pdf')]

    subprocess.call(gs)
    subprocess.call(['open', outfile])


def pdf_source(outfile, source, temp_dir):
    ps_files = []
    for i in source:
        if i.endswith('.java') and not os.path.split(i)[-1].startswith('.'):
            ps_files.append(enscript(i, temp_dir, 'java'))
        if i.endswith('.c') and not os.path.split(i)[-1].startswith('.'):
            ps_files.append(enscript(i, temp_dir, 'c'))
        if i.endswith('.h') and not os.path.split(i)[-1].startswith('.'):
            ps_files.append(enscript(i, temp_dir, 'c'))

    gs = [
             'gs', '-q', '-dBATCH', '-dNOPAUSE', '-sDEVICE=pdfwrite',
             '-sOutputFile=' + outfile] + ps_files
    subprocess.call(gs)
    # subprocess.call(['open', outfile])
    return outfile


def enscript(original_path, output_dir, lang=None):
    """ An enscript wrapper to create .ps files.

    @param original_path: path to original text file/source code
    @param output_dir: output directory for produced .ps files
    @param lang: optional language for syntax highlighting
    @return: path to generated .ps file, None if error
    """
    ps = os.path.join(output_dir, os.path.basename(original_path)) + '.ps'

    # create the command to execute
    command = ['enscript', '-q', '-b', 'File: $n', '--color=1', '-o', ps,
               original_path]
    if lang:
        command.append('-E' + lang)

    # check exit status
    if subprocess.call(command) != 0:
        return None

    return str(ps)
