# TODO: get the strings rather than the test case names
# TODO: generate the ID's so I can sort my output
import json
import os.path
import logging
from optparse import OptionParser


verbose = False
classroom = None


def full_name(uid):
    """
    look up username in roster.json and return full name
    """
    if uid in classroom:
        record = classroom[uid]
        return record['name']
    else:
        return uid + " NOT IN ROSTER"


def email(uid):
    """
    look up username in roster.json and return email address
    """
    if uid in classroom:
        record = classroom[uid]
        return record['email']
    else:
        return uid + " NOT IN ROSTER"


def report(score_file):
    """
    generate a text report from a json score

    @param score_file: name of input file
    @return: total score
    """
    # find the user name
    out_directory = os.path.dirname(score_file)
    username = os.path.splitext(os.path.basename(score_file))[0]

    # read in the base into which we are merging
    try:
        with open(score_file, 'r') as infile:
            all_scores = json.load(infile)
            infile.close()
    except Exception as e:
        logging.error("unable to read score file " + score_file
              + " - " + e)
        return None

    # get the list of all known tests
    assgt_title = all_scores['title'] if 'title' in all_scores else "UNKNOWN"
    assgt_name = all_scores['assignment'] \
        if 'assignment' in all_scores else "UNKNOWN"
    assgt_flags = all_scores['flags'] if 'flags' in all_scores else []
    if 'tests' in all_scores:
        all_tests = all_scores['tests']
    else:
        logging.error(score_file + " does not contain \"tests\":")
        return None

    # form the output file name from the user and assignment names
    output = out_directory + "/" + assgt_name + "-" + username + ".txt"

    print("Generating report from " + score_file + " into " + output)
    with open(output, 'w') as outfile:
        outfile.write("Assignment: " + assgt_title + "\n")
        outfile.write("\n")

        # print out student information
        outfile.write("Info\n")
        outfile.write("\tStudent: " + full_name(username) + "\n")
        outfile.write("\tUsername: " + username + "\n")
        outfile.write("\tEmail: " + email(username) + "\n")

        # enumerate the tests and earned points
        outfile.write("\n\nRubric points\n")
        earned_score = 0.0
        possible_score = 0.0
        for test in all_tests:
            test_name = test['name']
            outfile.write("\n\t" + test_name + "\n")

            if 'earned' in test:
                earned_score += test['earned']
                outfile.write("\tScore: " + str(test['earned']) + "/" +
                              str(test['score']) + "\n")
            else:
                outfile.write("\tNO POINTS RECORDED" + "\n")
            possible_score += test['score']

            if 'comment' in test:
                outfile.write("\tComments: " + test['comment'] + "\n")

        outfile.write("\n")
        outfile.write("Final Score: " + str(round(earned_score,2)) + "/" +
                      str(round(possible_score,2)) + "\n")

        if 'flag_regrade' in assgt_flags:
            print("NOTE: there is a REGRADE flag on this assignment")

        if 'flag_dishonesty' in assgt_flags:
            print("NOTE: there is a DISHONESTY flag on this assignment")

        if 'flag_quality' in assgt_flags:
            print("NOTE: there is a QUALITY flag on this assignment")

        if 'flag_general' in assgt_flags:
            print("NOTE: there is a GENERAL flag on this assignment")
        outfile.close()

    return earned_score


if __name__ == "__main__":
    umsg = "usage: %prog [options] input_file ..."
    parser = OptionParser(usage=umsg)
    parser.add_option("-v", "--verbose", action="store_true", dest="verbose",
                      help="verbose output")
    parser.add_option("-r", "--roster", type="string", dest="roster",
                      metavar="FILE", default="classroom.json",
                      help="list of students")

    (opts, files) = parser.parse_args()
    verbose = opts.verbose

    # digest the uid to user info map
    with open(opts.roster, 'r') as infile:
        classroom = json.load(infile)
        infile.close()

    # process the specified files
    for file in files:
        report(file)
