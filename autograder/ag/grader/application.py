import json
import os
import sys
import argparse

from runhelpers import Specifications, Student
from server import run_server

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--classpath', help='classroom file')
    args = parser.parse_args()
    classroom_path = args.classpath

    if not classroom_path:
        classroom_path = os.path.join(os.getcwd(), 'classroom.json')

    assignment_path = os.path.join(os.getcwd(), 'assignment.json')
    output_path = os.path.join(os.getcwd(), '_output')

    if not os.path.exists(output_path):
        os.mkdir(output_path, 0700)

    if not os.path.isfile(assignment_path) or not os.path.isfile(
            classroom_path):
        print('Missing assignment or classroom file.')
        sys.exit(1)

    classroom = dict()

    with open(classroom_path, 'r') as raw_data:
        data = json.load(raw_data)

    specs = Specifications(assignment_path)

    for i in data:

        complete = False
        if os.path.exists(os.path.join(output_path, i + '.json')):
            complete = True

        classroom[i] = Student(i, data[i]['name'], data[i]['email'], complete)

    run_server(classroom, specs)
