import json
import os.path
import logging
from optparse import OptionParser

full_credit = False
verbose = False
template = ""


def merge_scores(raw_scores):
    """
    Merge a new set of raw scores into a set of complete scores.

    @param raw_scores: name of input file
    @return: name of new merged score file
    """

    # find the name of our json output ile
    basename = os.path.splitext(raw_scores)[0]
    output = basename + ".json"

    # find the input file with which we are to merge
    if os.path.exists(output):
        merge_with = output
        newfile = False
    elif os.path.exists(template):
        merge_with = template
        newfile = True
    else:
        logging.error("no existing " + output + " or " + template)
        return None

    # read in the base into which we are merging
    try:
        with open(merge_with, 'r') as infile:
            all_scores = json.load(infile)
            infile.close()
    except Exception as e:
        logging.error("unable to read test template " + merge_with
              + " - " + e.message)
        return None

    # read in the updated raw scores
    try:
        with open(raw_scores, 'r') as infile:
            raw_results = json.load(infile)
            infile.close()
    except Exception as e:
        logging.error("unable to read raw results file " + raw_scores
              + " - " + e.message)
        return None

    # get the list of all known tests
    assgt_title = all_scores['title'] \
        if 'title' in all_scores else "UNKNOWN"
    assgt_name = all_scores['assignment'] \
        if 'assignment' in all_scores else "UNKNOWN"
    assgt_flags = all_scores['flags'] if 'flags' in all_scores else []
    if 'tests' in all_scores:
        all_tests = all_scores['tests']
    else:
        logging.error(merge_with + " does not contain \"tests\":")
        return None

    print("Merging " + raw_scores + " w/" + merge_with +
          " into " + output)

    # merge earned and comment from raw results into the output file
    earned_score = 0.0
    for test in all_tests:
        test_name = test['name']
        test_score = test['score']

        # if we are in the passes list, we simply earn the full score
        if 'passes' in raw_results and test_name in raw_results['passes']:
            test['earned'] = test_score
            test['comment'] = "Automatically passed."
            earned_score += test_score
            continue
        elif verbose:
            print("... " + test_name + " is not in passes")

        # if we are in the failures list, copy the error message
        if 'failures' in raw_results:
            failures = raw_results['failures']
            for failure in failures:
                if 'testname' in failure and failure['testname'] == test_name:
                    test['earned'] = 0.0

                    # figure out what information we want for the coment
                    if 'message' in failure:
                        # start with the JUnit message
                        test['comment'] = failure['message']
                        # if it wasn't a straight Assert, include the trace
                        if 'trace' in failure and \
                           "AssertionError" not in failure['trace']:
                            test['comment'] += ' ... Stack Trace Follows:\n\t' + failure['trace'];
                    elif 'trace' in failure:
                        test['comment'] = failure['trace']
                    else:
                        test['comment'] = "PLEASE REVIEW JUNIT OUTPUT"
                    break
        elif verbose:
            print("... " + testname + " is not in failures")

        # if we know what we've earned tally it
        if 'earned' in test:
            earned_score += test['earned']
            continue

        # are we supposed to initialize to full credit?
        if newfile and full_credit:
            test['earned'] = test_score
            test['comment'] = "DEFAULT - PLEASE REVIEW"
            earned_score += test_score

    # reconstruct the flag list
    possible_flags = ['flag_quality', 'flag_general',
                      'flag_dishonesty', 'flag_regrade']
    flags = []
    for flag in possible_flags:
        if flag in assgt_flags:
            flags.append(flag)

    with open(output, 'w') as outfile:
        json.dump({'title': assgt_title,
                   'assignment': assgt_name,
                   'tests': all_tests,
                   'earned_score': round(earned_score, 2),
                   'flags': flags},
                  outfile, indent=4)
        outfile.close()

    return output


if __name__ == "__main__":
    umsg = "usage: %prog [options] input_file ..."
    parser = OptionParser(usage=umsg)
    parser.add_option("-v", "--verbose", action="store_true", dest="verbose",
                      help="verbose output")
    parser.add_option("-t", "--template", type="string", dest="template",
                      metavar="FILE", default="assignment.json",
                      help="assignment test description")
    parser.add_option("-f", "--fullcredit", action="store_true",
                      dest="fullcredit", help="default to full credit")

    (opts, files) = parser.parse_args()
    verbose = opts.verbose
    full_credit = opts.fullcredit
    template = opts.template

    for file in files:
        merge_scores(file)
