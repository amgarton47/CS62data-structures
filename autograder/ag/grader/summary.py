import csv
import json
import os
import argparse

GRADE_FLAGS = ['flag_quality',
               'flag_general',
               'flag_dishonesty',
               'flag_regrade']


def merge(dir, csv_path):
    oringal_csv = []
    with open(csv_path, 'r') as csv_file:
        reader = csv.DictReader(csv_file)
        for i in reader:
            oringal_csv.append(i)

    with open(csv_path, 'w') as out_file:
        writer = csv.DictWriter(out_file,
                                ['DCI', 'EMAIL', 'NAME', 'SID', 'LAB',
                                 'ASSIGNMENT'] + GRADE_FLAGS)
        writer.writeheader()

        for row in oringal_csv:
            json_result = os.path.join(dir, row['DCI'] + '.json')

            if os.path.exists(json_result):
                with open(json_result, 'r') as json_file:
                    data = json.load(json_file)
                    row['ASSIGNMENT'] = data['earned_score']
                    if 'flags' in data:
                        for f in GRADE_FLAGS:
                            if f in data['flags']:
                                row[f] = True

            writer.writerow(row)

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('processed', help='directory of the processed json files')
    parser.add_argument('csv', help='csv with the class info')

    args = parser.parse_args()
    merge(args.processed, args.csv)
