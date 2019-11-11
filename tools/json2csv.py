"""
Print to stdout a (csv) spreadsheet with
      a column per (non-autograder) rubric item
      a row per student

usage: python json2csv.py [-t assignment.json] [-r classroom.json]

    rationale

        I had once considered translating a partially completed json into a
        csv spreadsheet for further editing ... but the csv cannot hold
        comments, so information is lost in that json->csv conversion.

        This led me to decide that json (despite editing difficulty)
        is the primary format for score information, and the spreadsheet
        is only for merging non-autograder scores into an in-progress
        json.

        Given this, I decided that the spreadsheet should only contain
        columns for a subset of (non-autograder) rubric items.  And
        that meant I needed a way to match columns with rubric items.
"""
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
            return sorted(all_students)
    except Exception as e:
        msg = e.message if hasattr(e, 'message') else str(e)
        logging.error("unable to read roster" + roster +
                      " - " + msg)
        return []


if __name__ == "__main__":
    """
    1. process the command line arguments

    """
    umsg = "usage: %prog [options]"
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

    # see if all rubric items are manual
    manual = 'runner' in all_scores and \
             'type' in all_scores['runner'] and \
             all_scores['runner']['type'] == "manual"

    # generate a list of column headings
    header = '"student ID"'
    for test in all_scores['tests']:
        testname = test["name"]
        # do not include autograder tests
        if not manual and ".Autograder)" in testname:
            continue
        header += ',"' + testname + '"'
    print(header)

    # generate a line per student
    for student in list_students(opts.roster):
        print(student)
