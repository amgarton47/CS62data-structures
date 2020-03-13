#TEST_FILES=`cat contestants.txt | sort -R | head -n 4`
TEST_FILES=`cat contestants.txt | perl -MList::Util=shuffle -e 'print shuffle(<STDIN>);' | head -n 4`
CP=bailey.jar:objectdraw.jar:.

%.class: %.java
	javac -cp ${CP} $<

all: DarwinRR.class Darwin.class

run: DarwinRR.class
	java -cp ${CP} DarwinRR

show: DarwinShowOff.class
	java -cp ${CP} DarwinShowOff

rtest: Darwin.class
	echo Testing random subset:
	echo "${TEST_FILES}"
	java -cp ${CP} Darwin ${TEST_FILES}

test: Darwin.class
	echo Testing:
	sh test-all.sh

clean:
	rm *.class

.PHONY: all run test rtest clean
