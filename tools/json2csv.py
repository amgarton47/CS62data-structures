import json
import csv
from optparse import OptionParser
from sys import exit
import logging

verbose = False


def list_students(roster):
    """
    return a list of names of all known students
    :param (str) roster: name of student roster file
    :return [(str)...]: names of students
    """
    try:
        with open(roster, 'r') as infile:
            all_students = json.load(infile)
            infile.close()
            return all_students
    except Exception as e:
        msg = e.message if hasattr(e, 'message') else str(e)
        logging.error("unable to read roster" + roster +
                      " - " + msg)
        return []


def make_csv(tests, student_json):
    """
    return a csv line for the scores in a student json file
    :param [tests]: list of defined tests
    :param (str) student_json: name of student file to process
    :return (str): csv line for student and their scores
    """
    # try to open the json file

    # get the student name

    # assemble a list of scores

    # turn it into a string


if __name__ == "__main__":
    umsg = "usage: %prog [options] [input.json ...]"
    parser = OptionParser(usage=umsg)
    parser.add_option("-v", "--verbose", action="store_true", dest="verbose",
                      help="verbose output")
    parser.add_option("-t", "--template", type="string", dest="template",
                      metavar="FILE", default="assignment.json",
                      help="assignment test description")
    parser.add_option("-r", "--roster", type="string", dest="roster",
                      metavar="FILE", default="../classroom.json",
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

    # generate a list of column headings
    header = '"student ID"'
    column = 1
    for test in all_scores['tests']:
        header += ',#' + str(column)
        column += 1
    print(header)

    # try to generate a line per student
    if len(files) == 0:
        # just the names
        for student in list_students(opts.roster):
            print(student)
    else:
        # all the accumulated scores
        for json_file in files:
            make_csv(all_scores[tests], json_file)
