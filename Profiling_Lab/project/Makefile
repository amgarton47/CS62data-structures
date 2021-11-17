#
# This makefile builds a list-intensive application with several different
# dictionary implementations, so that they can be profiled and call counted.
#
# Why is this being done in C?
#
#    1.	Execution profiling in Java (an interpreted language with garbage
#	collection) is both complex and noisy.  Profiling results are much
#	clearer for a simple, compiled language.
#    2. Alternative List implementations beg to be done by multiple
#	sub-classes in an Object Oriented language, but the name-mangling in C++
#	makes results a little bit harder to interpret.  
#

OS := $(shell uname)
ifeq ($(OS),Darwin)
	CC=clang -std=gnu99 -Wall -O2 -fprofile-instr-generate
	RAWDATA=default.profraw
else
	# all warnings, call-counting and execution profiling for all modules
	CC=gcc -std=gnu99 -pg -Wall
	# brief output, no call-graphs, suppress reporting for make/choose_word
	GP_OPTS=-b -Q -Pmake_words -Pchoose_word
	RAWDATA=gmon.out 
endif

REPORTS=linear.txt sorted.txt open.txt bucket.txt
OBJS=list_tester.o words.o linear_list.o sorted_list.o open_hash.o bucket_hash.o

# default target ... complete report
report.txt: $(REPORTS)
	cat linear.txt > $@
	@echo "\n======================================================\n" >> $@
	cat sorted.txt >> $@
	@echo "\n======================================================\n" >> $@
	cat open.txt >> $@
	@echo "\n======================================================\n" >> $@
	cat bucket.txt >> $@
	@echo
	@echo Combined report is in $@

# per-implementation reports
ifeq ($(OS),Darwin)
# Mac produces default.profraw, analyze with llvm-profdata
linear.txt: list_tester
	./list_tester linear 2048 1000000 > $@
	xcrun llvm-profdata show -function=linear default.profraw | grep -v "Hash:" >> $@

sorted.txt: list_tester
	./list_tester sorted 2048 1000000 > $@
	xcrun llvm-profdata show -function=sorted default.profraw | grep -v "Hash:" >> $@

open.txt: list_tester
	./list_tester open 2048 100000000 > $@
	xcrun llvm-profdata show -function=open default.profraw | grep -v "Hash:" >> $@

bucket.txt: list_tester
	./list_tester bucket 2048 100000000 > $@
	xcrun llvm-profdata show -function=bucket default.profraw | grep -v "Hash:" >> $@
else
# Linux produces gmon.out, analyze with gprof
linear.txt: list_tester
	./list_tester linear 2048 1000000 > $@
	gprof $(GP_OPTS) list_tester >> $@

sorted.txt: list_tester
	./list_tester sorted 2048 1000000 > $@
	gprof $(GP_OPTS) list_tester >> $@

open.txt: list_tester
	./list_tester open 2048 100000000 > $@
	gprof $(GP_OPTS) list_tester >> $@

bucket.txt: list_tester
	./list_tester bucket 2048 100000000 > $@
	gprof $(GP_OPTS) list_tester >> $@
endif

# this is the test program to be profiled
list_tester: $(OBJS)

# most of the modules include the word_list header file
$(OBJS): word_list.h

# delete all intermediate products
clean:
	rm -f $(OBJS) $(RAWDATA)

# restore working directory to its original state
clobber: clean
	rm -f list_tester report.txt $(REPORTS)
