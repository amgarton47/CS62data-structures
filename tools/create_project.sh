#!/bin/bash
#
# Usage: create_project.sh project_path name-of-repo
# cd in CS062_Projects
# Example: tools/create_project.sh Introduction pomonacs622020sp/lab0
#
# Note: the (empty) repo must exist on github before you do this
#

DFLT_ACCOUNT="pomonacs622021fa"

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

# save current working dir
CWD=$(pwd)

# create a new (temp) copy of the contents
REPO_DIR=/tmp/repo_temp.$$
#cp -r $PROJ_DIR $REPO_DIR

# turn it into a repo
mkdir ${REPO_DIR}
cd $REPO_DIR
git init

# set the origin (either ssh or http)
#git remote add origin git@github.com:$REPO_NAME.git
git remote add origin https://github.com/$REPO_NAME.git

git pull origin master

# back to CWD
cd "${CWD}"
# copy contents
rsync -rv --delete --exclude=.git "${PROJ_DIR}"/ "${REPO_DIR}"
# back to repo
cd "${REPO_DIR}"

git add -A
if [ -f .gitignore ]
then
	git add .gitignore
fi
git commit -m "Created from $1"

git push origin master

