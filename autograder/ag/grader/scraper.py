import argparse
import json
import os
import subprocess
import re

ALTNAMES = {
  "Alex Lee": ["Suhjin Lee",],
  "Bharath Kodungudi": ["Bharathrham Kodungudi","Bharathrhan Kodungudi"],
  "Raequan Nelson": ["Rae'Quan Nelson",],
}

def namebits(name):
  bits = re.sub(r"[_&+.,'/=-]", ' ', name)
  bits = re.sub(r"([A-Z])", r" \1", bits)
  return bits.title().split()

def findcollabs(classroom, name_parts):
  collabs = []
  for user in classroom:
    un = classroom[user]["name"]
    bits = namebits(un)

    altbits = None
    if un in ALTNAMES:
      altbits = [namebits(a) for a in ALTNAMES[un]]

    altuser = None
    if user[-1] in "0123456789":
      altuser = user[:re.search("[0-9]",user).start()]

    # DEBUG:
    #print(
    #  "Bits: '{}' from classfile; '{}' from submission".format(
    #    bits,
    #    name_parts
    #  )
    #)

    if (
      all(b in name_parts for b in bits)
   or (altbits and any(all(b in name_parts for b in ab) for ab in altbits))
    ):
      collabs.append(user)
    elif (
      (user.title() in name_parts)
   or (altuser and altuser.title() in name_parts)
    ):
      collabs.append(user)
  return collabs

def get_latest_paths(classroom, root, manifest_name):
  processed = []
  unprocessed = []
  for submission in os.listdir(root):
    submission_path = os.path.join(root, submission)
    print("Scanning: " + submission_path)

    if not os.path.isdir(submission_path):
      continue

    # Find a manifest anywhere in the submission:
    manifest = None
    for base, dirs, files in os.walk(submission_path):
      for f in files:
        if manifest_name.lower() == f.lower():
          manifest = os.path.join(base, f)
          break;
      if manifest:
        break;

    tag = os.path.join(submission_path, "submit-tag.json")
    if not os.path.exists(tag):
      tag = None

    if tag:
      with open(tag, 'r') as fin:
        try:
          orig_file = json.load(fin)['orig-file']
          print("  ...original file was: '{}'...".format(orig_file))
        except:
          print("  ...found submit tag but could not parse it...".format())
          pass
    else:
      print("  ...(no submit tag)...")

    collabs = None
    if manifest:
      print("  ...using manifest...")
      # Use the manifest to figure out who the submitter(s) are:
      with open(manifest, 'r') as fin:
        try:
          rawlist = json.load(fin)['collaborators']
          collabs = []
          for r in rawlist:
            collabs.extend(findcollabs(classroom, namebits(r)))
        except:
          pass
      if collabs:
        for c in collabs:
          if c not in classroom:
            print(
              "  ...\033[91munknown student '{}'\033[0m;falling back...".format(
                c
              )
            )
            collabs = None

    if not collabs and manifest:
      print("  ...using manifest (simplified)...")
      found = []
      with open(manifest, 'r') as fin:
        mlines = fin.readlines()
        for line in mlines:
          hit = False
          for name in [x.strip() for x in line.split(",")]:
            cl = findcollabs(classroom, namebits(name))
            if cl:
              found.extend(cl)
              hit = True
              break
          if not hit:
            print(
              "  ...unknown student '{}'; ignoring...".format(
                line[:-1]
              )
            )
      if found:
        collabs = found

    if not collabs and tag:
      print("  ...using submit tag...")
      # Use the submit tag to figure out who the submitter(s) are:
      with open(tag, 'r') as fin:
        try:
          orig_file = json.load(fin)['orig-file']
          collabs = findcollabs(classroom, namebits(orig_file))
        except:
          pass

    if not collabs:
      print("  ...using filename...")
      # Fallback based on directory name:
      collabs = findcollabs(classroom, namebits(submission))
      if len(collabs) > 1: # TODO: Filename-based groups? (watch for sub-names!)
        print("  Warning: Multi-collab from filename (add .json to fix):")
        for c in collabs:
          print("    " + c)

    if not collabs:
      print("  ...\033[91mcouldn't find collaborator(s); ignoring\033[0m ...")
      continue

    print("  ...\033[94mgot collaborators: {}\033[0m ...".format(str(collabs)))

    # Add 'subpath' and 'mtime' properties to the classroom structure:
    for collab in collabs:
      if collab in classroom:
        if 'collaborators' not in classroom[collab]:
          classroom[collab]['collaborators'] = set(collabs)
        if 'subpath' not in classroom[collab]:
          classroom[collab]['subpath'] = submission_path
          classroom[collab]['mtime'] = os.path.getmtime(submission_path)
        else:
          sub_time = os.path.getmtime(submission_path)
          if classroom[collab]['mtime'] < sub_time:
            classroom[collab]['mtime'] = sub_time
            classroom[collab]['subpath'] = submission_path

    processed.append(submission)

  unprocessed = [f for f in os.listdir(root) if f not in processed]
  return processed, unprocessed


def copy_paths_with_rename(classroom, destination):
    success = set()
    fail = set()
    script = "#!/bin/sh\n"
    for i in classroom:
        if 'subpath' in classroom[i]:
            script += "cp -r \"{}\" \"{}\"\n".format(
                classroom[i]['subpath'],
                os.path.join(destination, i)
            )
            success.add(i)
        else:
            fail.add(i)

    return success, fail, script

def list_collaborators(classroom):
  result = ""
  processed = set()
  for st in sorted(list(classroom.keys())):
    if st in processed:
      continue
    rec = classroom[st]
    if 'collaborators' in rec:
      sub = False
      for c in rec['collaborators']:
        if 'subpath' in classroom[c]:
          sub = True
          break
      processed = processed.union(rec['collaborators'])
      result += '+'.join(sorted(list(rec['collaborators'])))
      if not sub:
        result += " (no submission)"
      result += "\n"
    else:
      processed.add(st)
      sub = 'subpath' in rec
      result += st
      if not sub:
        result += " (no submission)"
      result += "\n"
  return result


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('source',
                        help='source directory containing student submissions')
    parser.add_argument('destination', help='destination directory for the submissions')
    parser.add_argument('manifest', help='case insensitive manifest name')
    parser.add_argument('classroom', help='classroom json file')

    args = parser.parse_args()

    classroom = {}
    with open(args.classroom, 'r') as raw_class:
      classroom = json.load(raw_class)

    processed, unprocessed = get_latest_paths(
      classroom,
      args.source,
      args.manifest
    )
    success, fail, script = copy_paths_with_rename(classroom, args.destination)
    with open("copy.sh", 'w') as fout:
        fout.write(script)

    collabs = list_collaborators(classroom)
    with open("collabs.txt", 'w') as fout:
      fout.write(collabs)

    print('Automatically detected:')
    for f in success:
        print('\t\033[94m%s\033[0m' % (f))

    print('\nMissing or not present:')
    for f in fail:
        print('\t\033[91m%s\033[0m' % (f))

    print('Unprocessed:')
    for f in unprocessed:
        print('\t\033[91m%s\033[0m' % (f))

    print("Inspect and run the created 'copy.sh' script.")
    print("Collaborators are listed in 'collabs.txt'.")


if __name__ == '__main__':
    main()
