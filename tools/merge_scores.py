"""
usage: python merge_scores.py [--template assignment.json] input-file ...

    each specified (.autos or .csv) input file will be
    read, and merge those scores into a (new or already
    existing) <student>.json score file (in the current
    working directory).

    the template file is used to get a list of score item
    names and possible values

    there is also a --fullcredit option which says that any
    item for which no score is found should be given full
    credit (and a warning-to-review comment).  But given
    the spreadsheet option (where it is very easy to give
    everyone the same score) this option may no longer
    make sense.

    for any item that does not receive full credit, a
    warning-please-update comment will be put in the
    json file, so the grader can explain why points
    were deducted.

 Initial sets of scores are likely to come from two places:

   1. the autograder creates <user>.autos files for each
      run of Autograder.java

   2. a human grader may use json2csv.py to create a csv
      spreadsheet, and then edit that by hand (e.g. in excel).

   These may both exist, and will likely be orthogonal
   (containing scores for different items).
"""
# TODO merge into new json, test comments for scores < possible
# TODO merge into existing json, test not copying absent scores

import csv
import os.path
import json
from sys import stderr, exit
from optparse import OptionParser

verbose = False


def autos_score(autos_file, template):
    """
    read a .autos file and return a dict of scores

    :param autos_file (str): name of the autograder output file
    :param template (dict): the assignment defintion
        this gives us item names and the value of each
    :return (dict): testname:(score, comment)
        returned dict includes ONLY testnames for which a
        pass or fail was found in the .autos file
    """
    # read in the autos file
    try:
        with open(autos_file, 'r') as infile:
            raw_results = json.load(infile)
            infile.close()
    except Exception as e:
        stderr.write("unable to read input file " + autos_file
                     + " - " + e.message)
        return None

    # accumulate results from known tests
    results = {}
    earned_score = 0.0
    for test in template['tests']:
        name = test['name']
        value = test['score']
        # passes get full points and no comment
        if 'passes' in raw_results and name in raw_results['passes']:
            results[name] = (value, None)
        elif 'failures' in raw_results:
            # see if this test is listed among those that failed
            for failure in raw_results['failures']:
                if failure['testname'] == name:
                    # if there was a message or stack trace, that is comment
                    if 'message' in failure:
                        comment = failure['message']
                        if 'trace' in failure and \
                           'AssertionError' not in failure['trace']:
                            comment += ' ... Stack Trace Follows:\n\t' +\
                                    failure['trace']
                    elif 'trace' in failure:
                        comment = failure['trace']
                    else:
                        # otherwise, the grader will have to figure it out
                        comment = "PLEASE REVIEW JUNIT OUTPUT"
                    # zero points for this item, best comment we could find
                    results[name] = (0.0, comment)
                    break

    return results


def csv_score(row, headings):
    """
    merge info from a csv file into a (json) score file
    :param row [str, ...]: input line from the json file
    :param headers [str, ...]: per-column headers for CSV file
    :param template (dict): the assignment defintion
        this gives us item names and the value of each
    :return (dict): test:(score, comment)
        returned dict includes ONLY testnames for which a score was found

    The key to this merge is that the (line 1) heading strings
    in the csv file are IDENTICAL to the test names in the .json
    file.
    """
    results = {}
    for col in range(1, len(row)):
        value = row[col]
        if len(value) > 0:
            results[headings[col]] = (float(value), None)

    return results


def merge(scores, current, filename, full_credit):
    """
    merge a set of new scores into a set of current values
    and write them out as a json score file
    :param scores (dict):   name : (int) score
    :param current (dict):  existing score file (or template)
    :param filename (string): name of desired output file
    """
    # accumulate tests, merging scores into current
    results = []
    earned_score = 0.0
    for test in current['tests']:
        # copy the basic information from the orginal
        name = test['name']
        entry = {}
        entry['name'] = name
        entry['score'] = test['score']

        # new earned and comment can override the original
        if name in scores:
            (earned, comment) = scores[name]
            entry['earned'] = earned
            earned_score += earned
            if comment is not None:
                entry['comment'] = comment
            elif earned != test['score']:
                # other than full points requires a comment
                entry['comment'] = "EXPLAIN PARTIAL CREDIT"
        else:
            # pass through the previous earned/comment
            if 'earned' in test:
                entry['earned'] = test['earned']
                earned_score += test['earned']
                if 'comment' in test:
                    entry['comment'] = test['comment']
            elif full_credit:
                entry['earned'] = test['score']
                earned_score += test['score']
                entry['comment'] = "DEFAULT FULL CREDIT"
        results.append(entry)

    # reconstruct the flag list from the score file
    possible_flags = ['flag_quality', 'flag_general',
                      'flag_dishonesty', 'flag_regrade']
    flags = []
    if 'flags' in current:
        for flag in possible_flags:
            if flag in current['flags']:
                flags.append(flag)

    # write out the results in a new json score file
    if verbose:
        stderr.write(" ... creating score file " + filename + "\n")

    with open(filename, 'w') as outfile:
        json.dump({'title': current['title'],
                   'assignment': current['assignment'],
                   'tests': results,
                   'earned_score': round(earned_score, 2),
                   'flags': flags},
                  outfile, indent=4)
        outfile.write("\n")
        outfile.close()


if __name__ == "__main__":
    """
    1. parse the arguments
    2. digest the assignment.json into a dict
    3a read the per-user scores (from .autos file)
       merge the new scores into the old, and write a new .json
    3b read the per-user scores (from each line of .csv file)
       merge the new scores into the old, and write a new .json
    """
    # 1. parse the argument
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

    # 2. digest the assignment template
    try:
        with open(opts.template, 'r') as infile:
            template = json.load(infile)
            infile.close()
    except Exception as e:
        stderr.write("unable to read test template " + opts.template
                     + " - " + e.message + "\n")
        exit(-1)

    # process each input file
    for file in files:
        # 3a.
        if '.autos' in file:
            # get a score dict for this set of results
            results = autos_score(file, template)

            # form output file name from input file base name
            basename = os.path.splitext(file)[0]
            output = basename + ".json"

            # if there is an already existing score file
            if os.path.exists(output):
                with open(output, 'r') as infile:
                    existing = json.load(infile)
                    infile.close()
                # merge .autos scores with existing score file
                merge(results, existing, output, False)
            else:
                # merge .autos scores with the (no scores) template
                merge(results, template, output, opts.fullcredit)

        # 3b. process a CSV file into a .json for each line
        elif '.csv' in file:
            with open(file) as input:
                csv_reader = csv.reader(input, delimiter=',')
                headings = None
                for row in csv_reader:
                    # pull off the line of column headings
                    if headings is None:
                        headings = row
                    else:
                        # form a score dict from this line
                        results = csv_score(row, headings)

                        # figure out the output file name
                        student = row[0]
                        output = student + ".json"
                        if os.path.exists(output):
                            with open(output, 'r') as infile:
                                existing = json.load(infile)
                                infile.close()
                            # merge CSV scores with existing score file
                            merge(results, existing, output, False)
                        else:
                            # merge CSV scores with the template
                            merge(results, template, output, opts.fullcredit)
                input.close()

        else:
            stderr.write("ERROR: " + file + " - unrecognized file type\n")
