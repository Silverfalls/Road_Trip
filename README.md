HOW TO GET EVERYTHING SET UP TO RUN ROAD TRIP:

Ubuntu
Install Java 8 like described on this site
http://tecadmin.net/install-oracle-java-8-jdk-8-ubuntu-via-ppa/

if Java is successfully installed open up the terminal and enter the following:

java -jar "path to RoadTrip.jar"

Windows
Install Java 8 from the official java.com site.
If the jar filetype is connected to java then you can just double click the
jar file. Otherwise execute the following in the console:

java -jar "path to RoadTrip.jar"

Mac
Install Java 8 from the official Java.com site.  Here is a link:
http://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html

Open terminal and execute the following command:
java -jar "path to RoadTrip.jar"



HOW TO USE ROAD TRIP:

To run a certain type of comparison, simply enter the corresponding 
option character(s) for the comparison you'd like to run.

Depending on the goal of the comparison, you may be asked to enter a 
number of nodes which corresponds to the size of the graph the 
comparison will use.  
The minimum number of nodes is 3 and the maximum is 100.

Also depending on the goal of the comparison, you may be asked to enter 
the number of iterations the comparison should run.  If for instance a 
comparison should run algorithm A on graph of size X, this number of 
iterations will tell the comparison to run algorithm A on graph of 
size X Y times.  This is likely being asked for by the comparison so
it can average results for certain metrics.  

After you have gotten this far, you will be asked what algorithms you 
would like to execute.  You can choose one of them or all of them or 
somewhere in between.

Given the following choices in the algorithm screen:

A : My Algorithm
B : Your Algorithm
C : Her Algorithm

You could choose only Your Algorithm by entering B.
You could choose My Algorithm and Her Algorithm by entering AC.
You could choose all three algorithms by entering ABC (in any order), or 
by entering *.



What does each comparison do?

Basic Comparison:
-prompts user for graph size (number of nodes)

This comparison simply runs the selected algorithms on the same graph 
once and reports the results.

Basic Comparison Averaged:
-prompts user for graph size (number of nodes)
-prompts user for number of iterations

This comparison runs the selected algorithms on the same graph number 
of iterations times and averages the running time for each algorithm.

Compare Accuracy Comparison:
-prompts user for graph size (number of nodes)
-prompts user for number of iterations

This comparison requires that at least one exact algorithm and at least 
one non-exact algorithm are selected.  It then picks one of the exact 
algorithms as the control.  It then runs every algorithm against the same 
graph number of iterations times.  However, after each iteration, it 
generates a new graph of the same size.  Then, after all iterations, it 
reports how many times each non-exact solution was not the optimal solution 
and on average how far off the optimal solution it was.


What does each algorithm do?

Brute Force Algorithm:

The Brute Force algorithm is a naive solution to the TSP.
It simply calculates every possible route, adds up the cost of the that
route and picks the lowest cost route.

This is an exact algorithm.

An iteration for this algorithm means that the algorithm checked one complete
path.


Nearest Neighbor Algorithm:

The Nearest Neighbor algorithm is a greedy algorithm that simply keeps
picking the next closest node and marking it as visited.  Once all nodes
have been visited, it adds a trip back to the starting node to the tour
and is complete.

This is a non-exact algorithm.

An iteration for this algorithm means that the algorithm added one node to it's
path.


Branch and Bound Algorithm (List Based & Tree Based):

The Branch and Bound algorithm keeps extending branches one node at a time until
it becomes apparent that route can not be the shortest route.  For example, say
it already has found a route that is total cost of 100.  If any other routes are
already 100 or greater and not yet finished, there is no way they could be the
shortest branch and shouldn't be considered further.

An iteration for this algorithm means that the algorithm added a path branch.