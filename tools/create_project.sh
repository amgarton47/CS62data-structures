#!/bin/bash
#
# Usage: create_project.sh project_path url_name
#

GIT_ACCOUNT="PomonaCS"

# confirm the validity of the project path
if [ -d "$1" ]
then
	if [ -d "$1/project" ]
	then
		PROJ_DIR="$1/project"
	else
		>&2 echo "ERROR: unable to access project contents $1/project"
		exit -1
	fi
else
	>&2 echo "ERROR: unable to access project directory $1"
	exit -1
fi

# create a new (temp) copy of the contents
REPO_DIR=/tmp/repo_temp.$$
cp -r --preserve=mode $PROJ_DIR $REPO_DIR

# turn it into a repo
cd $REPO_DIR
git init
git add *
git commit -m "Created from $$1"

# push it to github
git remote add origin git@github.com://$GIT_ACCOUNT/$2
git push -u origin master

# make sure we are OK
git status
