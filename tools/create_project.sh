#!/bin/bash
#
# Usage: create_project.sh project_path name-of-repo
# cd in CS062_Projects
# Example: tools/create_project.sh Introduction pomonacs622020sp/lab0
#
# Note: the (empty) repo must exist on github before you do this
#

DFLT_ACCOUNT="pomonacs622020sp"

# validate the arguments
if [ -z "$1" -o -z "$2" ]
then
	>&2 echo "Usage: $0 path-to-project name-of-repo (on github.com:)"
else
	PROJECT="$1"

	# repo name may or may not include an account
	account=`dirname "$2"`
	if [ "$account" == "." ]
	then
		echo "assuming $2 is under github.com:$DFLT_ACCOUNT"
		REPO_NAME="$DFLT_ACCOUNT/$2"
	else
		REPO_NAME="$2"
	fi
fi

# confirm the validity of the project path
if [ -d "$PROJECT" ]
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
cp -r $PROJ_DIR $REPO_DIR

# turn it into a repo
cd $REPO_DIR
git init
git add * 
if [ -f .gitignore ]
then
	git add .gitignore
fi
git commit -m "Created from $1"

# set the origin (either ssh or http)
#git remote add origin git@github.com:$REPO_NAME.git
git remote add origin https://github.com/$REPO_NAME.git

# make sure we are OK
echo
git status
git remote -v

echo
echo "To complete project repo creation:"
echo "   1. create the repo $REPO_NAME on github"
echo "   2. cd $REPO_DIR"
echo "   3. git push --set-upstream origin master"
