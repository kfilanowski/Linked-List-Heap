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
This program uses a Linked list to build a complete tree, and then applies
 heapify on it. It takes one file as input, where the text file contains
data about each node to be inserted into the tree. The program will 
print each level of the tree before and after the heapify.

CONTENTS:
------------------
README - This file.
doc - A HTML document showing off the java docs.
src - A folder containing the source files of the program.

Inside src:

Driver.java : The main driver of the program. This file should be called
when the program is to be run. This simply takes a command line argument 
of a file name and passes it onto Heap to do the processing.

PathNode.java : A node in the tree. The pathnodes contain a list of integers
as data as well as links to it's generation, child, and parent.

Heap.java : Performs all of the operations on the tree. Heap will read 
in the file, build the tree, set the proper links (including generations),
and heapify the tree after its been built.

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
to be a PathNode, and on each line, we hold integers, separated by spaces new 
lines to define an edge. The file should not be empty, or contain any 
non-digit characters.
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
