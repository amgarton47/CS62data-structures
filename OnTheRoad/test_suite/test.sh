#!/bin/sh
oldwd=`pwd`
suite=/common/cs/cs062/admin/autograder/suites/asg11
if [ $# -gt 0 ]
then
  cd $1
fi
if [ ! -d "src" -o ! -d "bin" -o ! -f "Makefile" ]
then
  echo "Couldn't find src/, bin/ and 'Makefile'; aborting."
  exit 1
fi
if [ ! -f "memcheck.supp" ]
then
  cp /common/cs/cs062/assignments/assignment11/memcheck.supp .
fi
if [ -f "data/orig-sample.txt" ]
then
   echo "Old orig-sample.txt file found; using it to replace data/sample.txt."
   mv data/orig-sample.txt data/sample.txt
#  echo "Old orig-sample.txt found. Do you wish to use it to replace current sample.txt? (y/N)"
#  read $ans
#  case $ans in
#    y)
#      echo "Okay, replacing the old sample.txt."
#      mv data/orig-sample.txt data/sample.txt
#      ;;
#    *)
#      echo "Okay, nothing will be changed."
#      ;;
#  esac
fi
echo "Testing submission `basename \`pwd\``..."
makefile=$suite/makefile
make -f $makefile clean
echo "===================================================================="
echo "Normal debug run:"
echo "===================================================================="
make -f $makefile test data/sample.txt
echo "===================================================================="
echo "Fully-sanitized run:"
echo "===================================================================="
make -f $makefile run data/sample.txt
echo "===================================================================="
echo "Debug run with short test file"
echo "===================================================================="
mv data/sample.txt data/orig-sample.txt
ln -s $suite/short-test.txt data/sample.txt
make -f $makefile test data/sample.txt
echo "===================================================================="
echo "Debug run with long test file"
echo "===================================================================="
rm data/sample.txt
ln -s $suite/long-test.txt data/sample.txt
make -f $makefile test data/sample.txt
echo "===================================================================="
echo "Debug run with disconnected test file"
echo "===================================================================="
rm data/sample.txt
ln -s $suite/disjoint-test.txt data/sample.txt
make -f $makefile test data/sample.txt
echo "===================================================================="
echo "Debug run with weakly-connected test file"
echo "===================================================================="
rm data/sample.txt
ln -s $suite/weak-test.txt data/sample.txt
make -f $makefile test data/sample.txt
echo "===================================================================="
echo "Debug run with reverse-weakly-connected test file"
echo "===================================================================="
rm data/sample.txt
ln -s $suite/weak-test-2.txt data/sample.txt
make -f $makefile test data/sample.txt
echo ""
echo "...cleaning up..."
mv data/orig-sample.txt data/sample.txt
echo "...testing done."
cd $oldwd
