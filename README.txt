Author - Kevin Filanowski
Author - Jake Ginn
Version - December 5th, 2018 - 1.0.0

TABLE-OF-CONTENTS:
------------------
Description
Contents
Compiling
Usage
Input File
------------------

DESCRIPTION:
------------------
This program is a general database program for Employee records. It takes two
files as input, admin.txt and faculty.txt, where both text files contain
information about each Employee. It will create two tables, where each row
is an employee. There are a number of operations that can be used, such as:
0) Quit - Exits the program
1) Intersect - Creates a new table from two tables comprised of records
that have the same value for a specific attribute.
2) Difference - Creates a new table comprised of records in one table
but not another.
3) Union - Creates a new table comprised of records that occur in both tables.
4) Select - Creates a new table comprised of nodes having a value for a
specific attribute
5) Remove - Removes a table record by a matching ID.
6) Print both tables.

The program will be fairly picky with the input file, described in the
section 'INPUT FILE'

CONTENTS:
------------------
README - This file.
doc - A HTML document showing off the java docs.
src - A folder containing the source files of the program.

Inside src:

Driver.java : The main driver of the program. This file should be called
when the program is to be run. This simply takes a command line argument 
of a file name and passes it onto Heap to do the processing.

PathNode.java : 

Heap.java : 

input.txt : A sample text file showing an example of what the input file
should look like.

COMPILING:
------------------
To compile the program, ensure that the files described in 'CONTENT',
specifically the src folder, are all in the same directory.
Then run the following command to compile all java files in your current
directory:

javac *.java

There should be no errors or warnings. Many class files should appear.

USAGE:
------------------
java Driver <inputfile>
or
./java Driver <inputfile>

The program will then proceed to print output, before and after the heapify.

Input File:
------------------
The input file describes the entire tree. Each line in the file is considered
to be a PathNode, and on each line, we hold integers, separated by spaces new lines
to define an edge. The file should not be empty, or contain any non-digit characters.
For example: 
0 4 3
0 1
0 4 1 2 3 
0 1 2 
0 2 
0 4 1 2 
0 2 3 
0 4 
0 1 2 3 
0 4 1

This input will create 10 PathNodes, since there are 10 lines, and each line is
the data that a PathNode carries.
