#===============================================================================
# This makefile is meant to be used while learning to program in C (or with
# minor modifications C++). For this reason, the programs built by this makefile
# are optimized for debugability not for performance. This makefile has been
# tested with clang version 3.8 on OS X (10.11) and Linux.
#
# Please enter your name below:
#
# Your name(s):
author_name = Your Name
#
# Porject name:
project_name = Assignment12test
#
# Command line arguments:
CMD_ARGS ?=
#
# You will use the following commands provided by this makefile:
#
# make                    -  builds the project
# make run                -  runs the built project
# make memcheck           -  run program with memory checker
# make package            -  creates deliverable package on the Desktop
# make clean              -  remove binary files
#
# Author of makefile: Michael J Bannister (2016)
# version: 0.6
#===============================================================================


#################################################################
#### You should not need to change anything below this line. ####
#################################################################


#### Start: Variables ####

# Variables for C compiler (CC), warning flags (WFLAGS), debug flags (DFLAGS) and sanitizer flags (SFLAGS).
CC = clang
WFLAGS = -Wall -Wextra -pedantic -lm
DFLAGS = -g -O0 -fno-omit-frame-pointer -fno-optimize-sibling-calls
#SFLAGS = -fsanitize=address,undefined
SFLAGS = -fsanitize=address,return,unreachable,bounds -fsanitize-undefined-trap-on-error
CFLAGS += -std=c11 $(WFLAGS) $(DFLAGS)

# Variables for locations of source (SRC), header (HDR) files.
location = $(shell pwd)
SRC = $(wildcard src/*.c)
HDR = $(wildcard src/*.h)

#
# Rules for compiling
#
.PHONY: all
all: bin/$(project_name).sanitized.out

bin/$(project_name).sanitized.out: $(SRC) $(HDR) bin/
	$(CC) $(CFLAGS) $(SFLAGS) $(SRC) -o ./bin/$(project_name).sanitized.out

bin/$(project_name).debug.out: $(SRC) $(HDR) bin/
	$(CC) $(CFLAGS) $(SRC) -o ./bin/$(project_name).debug.out

bin/:
	mkdir bin

#
# Rules for running a program
#
.PHONY: run
run: bin/$(project_name).sanitized.out
	./bin/$(project_name).sanitized.out $(CMD_ARGS)

.PHONY: test
test: bin/$(project_name).debug.out
	./bin/$(project_name).debug.out $(CMD_ARGS)

VFLAGS = --tool=memcheck --leak-check=full --track-origins=yes --show-reachable=yes --suppressions=memcheck.supp
.PHONY: memcheck
memcheck: bin/$(project_name).debug.out
	valgrind $(VFLAGS) ./bin/$(project_name).debug.out $(CMD_ARGS)
.PHONY: memcheck-supp
memcheck-supp: bin/$(project_name).debug.out
	valgrind $(VFLAGS) --gen-suppressions=all ./bin/$(project_name).debug.out $(CMD_ARGS)

#
# Rules for packaging
#
space_char = $(subst ,, )
package_name = $(subst $(space_char),_,$(project_name) $(author_name))
package_dir = ~/Desktop/

.PHONY: package
package:
	@mkdir $(package_dir)$(package_name) 2> /dev/null \
	  && cp -r . $(package_dir)$(package_name) \
	  && echo "\n\033[1;32m*** Folder $(package_name) ready to turn in. ***\033[0m" \
	  || echo "\n\033[1;31m*** Folder $(package_name) already exists. Please rename or move this folder. ***\033[0m"

#
# Rules for cleaning up
#
.PHONY: clean
clean:
	rm -r bin/* ||:
