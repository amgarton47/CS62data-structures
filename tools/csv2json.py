import json
import csv
from optparse import OptionParser
import logging

verbose = False


def make_json(tests, scores):
    """
    create json score descriptions from a csv list

    @param [tests]: list of tests from this assgt
    @param scores: name of the csv file with scores

        row 1:  headings
        col 1:  student ID (to use for filenames)
        col 2-n: scores (corresponding to "tests" in template
    """

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
                for test in tests:
                    if column >= len(row) or row[column].strip() == "":
                        this_tests.append(test)
                    else:
                        # create a new entry describing this score
                        temp = {}
                        temp['name'] = test['name']
                        temp['score'] = test['score']
                        earned = float(row[column])
                        temp['earned'] = earned
                        if earned != temp['score']:
                            temp['comment'] = "EXPLAIN DEDUCTION"
                        this_tests.append(temp)
                        this_score += earned
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
        logging.error("unable to read CSV score file " + scores +
                      " - " + msg)


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

    # read in the base lab description
    try:
        with open(opts.template, 'r') as infile:
            all_scores = json.load(infile)
            infile.close()
    except Exception as e:
        logging.error("unable to read test template " + opts.template +
                      " - " + e.message)
        sys.exit(-1)

    # make sure we have a list of tests
    if 'tests' not in all_scores:
        logging.error("ERROR: " + opts.template + " contains no tests")
        sys.exit(-1)

    # generate a json for each row in the csv
    for file in files:
        make_json(tests, file)
