import contextlib
import json
import shutil
import subprocess
import tempfile
from os import path, mkdir

from xhtml2pdf import pisa

from setuputilities import wildcard_copy, find_ext


@contextlib.contextmanager
def make_temp_directory():
    """ Creates a temporary directory in context and remove once complete.
    @yield: directory path
    """
    temp_dir = tempfile.mkdtemp()
    yield temp_dir
    shutil.rmtree(temp_dir)


class Specifications:
    def __init__(self, spec_file):
        with open(spec_file, 'r') as raw_rubric:
            data = json.load(raw_rubric)

        self.title = data['title']
        self.assignment = data['assignment']
        self.info = data['info']
        self.runner = data['runner']
        self.writeup = data['writeup']
        self.tests = dict()

        id_num = 0
        for i in data['tests']:
            self.tests[i['name']] = {
                'score': i['score'],
                '_id': id_num
            }
            id_num += 1


class Student:
    def __init__(self, username, fullname, email, complete=False):
        """ Initialize student information.

        @param username: student username
        @param fullname: student full name
        @param email: student email address
        """
        self.username = username
        self.fullname = fullname
        self.email = email
        self.complete = complete


class Classroom:
    def __init__(self, students=None):
        if students:
            self.students = students
        else:
            self.students = dict()

    def add_student(self, student):
        self.students[student.username] = student


class Submission:
    def __init__(self, filepath, assignment):
        """ Create a new submission.

        @param filepath: filepath to the original submission
        @param assignment: an assignment object corresponding to the submisison
        """
        self.students = set()
        self.filepath = filepath
        self.assignment = assignment

    def add_student(self, student):
        self.students.add(student)


class JavaPackageRunner:
    def __init__(self, package, mainclass, unittests=None):
        """ A basic runner for an assignment structured with a single java
            package.

        @param package: name of the java package
        @param mainclass: name of the main class to be run
        """
        self.pkg = package
        self.mainclass = mainclass
        self.currentbuild = None
        self.unittests = unittests

    def build_submission(self, submission):
        """ Builds a submission according to the JavaPackageRunner standard.

        @param submission: submission to be built
        @return: path to submission if successful, None otherwise
        """

        # destroy any previous build and create a new working directory
        self.destroy_current_build()
        temp_dir = tempfile.mkdtemp()
        print(str(temp_dir))
        self.currentbuild = temp_dir

        # scaffold directories
        temp_bin, temp_dep, temp_src, temp_jit, temp_pkg = self._dir_scaffold(
                temp_dir)

        # copy student source code into src directory
        try:
            # TODO make overriding stream to avoid multiple read/writes
            copied = wildcard_copy(
                    path.join(submission.filepath, 'src', self.pkg),
                    path.join(temp_dir, 'src', self.pkg))

            # alter scope of the copied files
            for i in copied:
                if i.endswith('.java'):
                    with open(i, 'r') as input_file:
                        input_file = input_file.read()

                    with open(i, 'w') as output_file:
                        output_file.write(
                                input_file.replace('private ', 'protected '))

            # copy unit tests and dependencies if they exist
            if self.unittests:
                wildcard_copy(path.join(self.unittests, 'src', 'junitmods'),
                              temp_jit)
                wildcard_copy(path.join(self.unittests, 'dependencies'),
                              temp_dep)
                wildcard_copy(path.join(self.unittests, 'src', self.pkg),
                              temp_pkg)

            # compile the code into bin
            java_src = find_ext(temp_src, '*.java')
            response = subprocess.call(
                    ['javac', '-d', temp_bin, '-cp',
                     temp_dep + '/*', ] + java_src)

            if response != 0:
                self.destroy_current_build()
                return None

            return temp_dir
        except:
            self.destroy_current_build()
            return None

    def run_autos(self, outfile, submission=None):
        self._check_build(submission)
        cp = path.join(self.currentbuild, 'dependencies',
                       '*') + ':' + path.join(
                self.currentbuild, 'bin')

        command = ['java', '-Dtesting=' + self.currentbuild, '-cp', cp, 'junitmods/PomonaRunner', outfile]
        return subprocess.call(command)

    def run_manual(self, submission=None, cliargs=None):
        print('calling run manual...')
        self._check_build(submission)
        cp = path.join(self.currentbuild, 'dependencies',
                       '*') + ':' + path.join(
                self.currentbuild, 'bin')

        command = ['java', '-Dtesting=' + self.currentbuild, '-cp', cp, path.join(self.pkg, self.mainclass)]
        if cliargs:
            # TODO fix this....
            command.extend(cliargs.split('\n')) # this is why we need to be running these on isolated VMs


        return subprocess.call(command)

    def _check_build(self, submission):
        if not self.currentbuild and submission:
            print '===== building'
            self.currentbuild = self.build_submission(submission)
        elif not self.currentbuild and not submission:
            self.JavaPackageRunnerException('Trying to run without building.')

    def destroy_current_build(self):
        """ If a current build exists, destroy it and reset the current build.
        """
        if self.currentbuild:
            shutil.rmtree(self.currentbuild)
            self.currentbuild = None

    class JavaPackageRunnerException(Exception):
        def __init__(self, value):
            self.value = value

        def __str__(self):
            return repr(self.value)

    def _dir_scaffold(self, temp_dir):
        """ Create the scaffolding directories in the temporary directory
        @param temp_dir:
        @return:
        """
        temp_bin = path.join(temp_dir, 'bin')
        temp_dep = path.join(temp_dir, 'dependencies')
        temp_src = path.join(temp_dir, 'src')
        temp_jit = path.join(temp_src, 'junitmods')
        temp_pkg = path.join(temp_src, self.pkg)

        mkdir(temp_bin)
        mkdir(temp_src)
        mkdir(temp_dep)
        mkdir(temp_jit)
        mkdir(temp_pkg)

        return temp_bin, temp_dep, temp_src, temp_jit, temp_pkg


def convertHtmlToPdf(sourceHtml, outputFilename):
    # open output file for writing (truncated binary)
    resultFile = open(outputFilename, "w+b")

    # convert HTML to PDF
    pisaStatus = pisa.CreatePDF(
            sourceHtml,  # the HTML to convert
            dest=resultFile)  # file handle to recieve result

    # close output file
    resultFile.close()  # close output file

    # return True on success and False on errors
    return pisaStatus.err


if __name__ == '__main__':
    pass

    # sub = Submission('/Users/raw/Desktop/Assignment01_LiuBoyu', None)
    # jp = JavaPackageRunner('silverdollar', 'GraphicsCoinStrip', '/Users/raw/Desktop/autograder/suites/assign01')
    # jp.build_submission(sub)
    # # jp.run_autos('/Users/raw/Desktop/test.json', sub)
    # jp.run_manual(sub)
    # jp.destroy_current_build()
