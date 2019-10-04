import json
import csv
from optparse import OptionParser
import logging

verbose = False


def make_csv(template, classfile=None):
    """
    output an empty csv file from a specified template (and class list)

    @param template: name of the json assignment defintion
    @param classfile: list of students (to pre-populate rows)
    """

    # read in the base lab description
    try:
        with open(template, 'r') as infile:
            all_scores = json.load(infile)
            infile.close()
    except Exception as e:
        logging.error("unable to read test template " + template
              + " - " + e.message)
        return

    # make sure we have a list of tests
    if 'tests' not in all_scores:
        logging.error("ERROR: " + template + " contains no tests")
        return

    # generate a list of column headings
    header='"student ID"'
    column = 1
    for test in all_scores['tests']:
        header += ',#' + str(column)
        column += 1
    print(header)

    if classfile is not None:
        try:
            with open(classfile, 'r') as infile:
                all_students = json.load(infile)
                infile.close()
        except Exception as e:
            msg = e.message if hasattr(e, 'message') else str(e)
            logging.error("unable to read roster" + classfile
                  + " - " + msg)

    # generate a row for each student in the roster
    for student in all_students:
        print(student)

    return


def make_json(template, scores):
    """
    create json score descriptions from a csv list

    @param template: name of the json assignment definition
    @param scores: name of the csv file with scores

        row 1:  headings
        col 1:  student ID (to use for filenames)
        col 2-n: scores (corresponding to "tests" in template
    """

    # read in the base lab description
    try:
        with open(template, 'r') as infile:
            master = json.load(infile)
            infile.close()
    except Exception as e:
        msg = e.message if hasattr(e, 'message') else str(e)
        logging.error("unable to read test template " + template
              + " - " + msg)
        return

    # make sure we have a list of tests
    if 'tests' not in master:
        logging.error("ERROR: " + template + " contains no tests")
        return

    # read the CSV score file
    try:
        with open(scores) as input:
            csv_reader = csv.reader(input, delimiter=',')
            line = 0
            for row in csv_reader:
                # first line is just the headings
                if line == 0:
                    line += 1
                    continue

                # generate the scores for this student
                this_tests = []
                this_score = 0.0
                column = 1

                # accumulate the tests and scores
                for test in master['tests']:
                    if column >= len(row) or row[column].strip() == "":
                        this_tests.append(test)
                    else:
                        # create a new entry describing this score
                        temp = {}
                        temp['comment'] = "FROM CSV"
                        temp['name'] = test['name']
                        temp['score'] = test['score']
                        temp['earned'] = float(row[column])
                        this_tests.append(temp)
                        this_score += float(row[column])
                    column += 1

                # write them out in a per-student file
                outfile = row[0] + ".json"
                with open(outfile, 'w') as output:
                    json.dump({'title': master['title'],
                               'assignment': master['assignment'],
                               'tests': this_tests,
                               'earned_score': round(this_score, 2),
                               'flags': []},
                              output, indent=4)
                    output.close()
                line += 1
            input.close()
    except Exception as e:
        msg = e.message if hasattr(e, 'message') else str(e)
        logging.error("unable to read CSV score file " + scores
              + " - " + msg)


if __name__ == "__main__":
    umsg = "usage: %prog [options] [input.csv ...]"
    parser = OptionParser(usage=umsg)
    parser.add_option("-v", "--verbose", action="store_true", dest="verbose",
                      help="verbose output")
    parser.add_option("-t", "--template", type="string", dest="template",
                      metavar="FILE", default="assignment.json",
                      help="assignment test description")
    parser.add_option("-r", "--roster", type="string", dest="roster",
                      metavar="FILE", default="classroom.json",
                      help="list of students")

    (opts, files) = parser.parse_args()
    verbose = opts.verbose

    if len(files) == 0:
        make_csv(opts.template, opts.roster)
    else:
        for file in files:
            make_json(opts.template, file)
