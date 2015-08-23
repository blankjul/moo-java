CC = gcc
MACHINE := $(shell gcc -dumpmachine 2>&1)
ifeq ($(MACHINE),mingw32)
    RM=-del /Q
    GETRUSAGE_O = getrusage.o
    RESOURCE_H = resource.h
endif
ifdef arch
  ARCH_OPT = -march=$(arch)
endif
OPT = $(ARCH_OPT) -O3 -funroll-loops
CFLAGS = -Wall -g $(OPT) -DAVL_DEPTH -DARCH=\"$(arch)\" -DVERSION=\"$(VERSION)\" -D_ISOC99_SOURCE
VERSION = 0.95

all: eaf eaf-test

eaf: main-eaf.o eaf2d.o eaf3d.o io.o timer.o avl.o $(GETRUSAGE_O)
	$(CC) -o eaf main-eaf.o eaf2d.o eaf3d.o io.o timer.o avl.o $(GETRUSAGE_O)

eaf-test: eaf_test.o eaf_test_main.o io.o
	$(CC) -o eaf-test eaf_test.o eaf_test_main.o io.o

main-eaf.o: main-eaf.c
eaf2d.o: eaf2d.c eaf.h
io.o: io.c io.h
eaf_test.o: eaf_test.c eaf.h
eaf_test_main.o: eaf_test_main.c eaf.h io.h
timer.o: timer.c timer.h $(RESOURCE_H)
avl.o: avl.c avl.h
getrusage.o: getrusage.c resource.h

clean:
	$(RM) main-eaf.o eaf2d.o eaf_test_main.o eaf_test.o eaf.o eaf3d.o io.o timer.o avl.o $(GETRUSAGE_O)

distclean: clean
	$(RM) eaf eaf-test *~

