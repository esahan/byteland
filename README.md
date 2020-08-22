
***************************************************  
# Byteland Project Assignment  
  
  
***************************************************  
  
******************  
**Author : Ege Sahan**  
******************  
  
***************************************************  
**Problem Description**  
***************************************************  
  
Byteland is a strange country, with many cities, but with a poorly developed road network (in fact, there is exactly one route from each city to any other city, possibly leading through other cities). Until recently, the cities of Byteland were independently governed by proud Mayors, who chose not to integrate too tightly with their neighbours. However, recent opinion polls among Bytelandian computer programmers have shown a number of disturbing trends, including a sudden drop in pizza consumption. Since this had never before happened in Byteland and seemed quite inexplicable,  the Mayors sought guidance of the High Council of Wise Men of Byteland. After a long period of deliberation, the Council ruled that the situation was  very serious indeed: the economy was in for a long-term depression! Moreover, they claimed that tighter integration was the only way for the Bytelandian cities to survive. Whether they like it or not, the Mayors must now find a way to unite their cities as quickly as possible.  However, this is not as easy as it sounds, as there are a number of important constraints which need to be fulfilled: 

•  Initially, each city is an independent State. The process of integration is divided into steps.  
•  At each step, due to the limited number of diplomatic envoys available, a State can only be involved in a unification process with at most one other state.   
   At each step two States can unite to form a new State, but only if there exists a road directly connecting some two cities of the uniting States.  
•  The unification process is considered to be complete when all the cities belong to the same, global State.  
The Mayors have asked you to arrange a schedule for the diplomatic talks, so that unification can be completed in as few steps as possible. Can you handle this delicate task?  
  
  
***************************************************  
**Explanation**  
***************************************************  
As problem describes, Byteland country be formed of city states with connections between them. We can represent this country as a graph, it's city states as a vertices   
and connection between city states as edges.   
  
example:  
  
"0 1 1 1 1 0 2 2 8 9 9" input represents a tree  
  
	   1 
	  /  \ \ \ \ 
     2    3 4 5 0  
    / \          \  
    7   8          6  
     \  
      9  
     / \  
    10  11  
  
  
This graph must be a connected tree without cycle to solve this problem. I used Depth-first search (DFS is an algorithm for traversing or searching) for traversing these graphs   
and start to merge them while backtracking from end of the leaves.  
  
For this project I implemented;  
  
•  Input Handler that receives and validate inputs for given program arguments  
•  Adjacency List Graph Representation with some utils to modify graph  
•  Merge Handler for processing input and apply merge algorithm  
•  Merge Runner for thread processing   
  
  
  
***************************************************  
**Prerequisites**  
***************************************************  
You need to have ;  
•  JDK 1.8+  
•  Maven   
Installed computer to build and run this Project.  
  
  
***************************************************  
**Installation**  
***************************************************  
You can import this project to your favorite ide as a maven project or   
you can build it with "maven package" command (or "maven install" if you also want to put the package in your local repository).  
  
  
***************************************************  
**Running - Program Arguments**  
***************************************************  
This Program reads input from text file and it takes file information via program arguments. These parameters can be passed using program configration on your IDE or you can use them with "java -jar" command.  
It takes up to 2 arguments {Local or Path} {Filename or Filepath}  
  
There are 2 first parameter types;  
First one is "FileLocal" , "FileLocal" is option for using files within classpath of the project, this is designed for IDE usage.  
If used without second parameter it will default to Input.txt inside classpath or second parameter can be used to choose another file from classpath.  
  
**example1**: "java -jar byteland-0.0.1-SNAPSHOT-jar-with-dependencies.jar" , "java -jar byteland-0.0.1-SNAPSHOT-jar-with-dependencies.jar FileLocal"  
no parameter usage or just first parameter with "FileLocal" value refers to Input.txt file  
  
**example2**: "java -jar byteland-0.0.1-SNAPSHOT-jar-with-dependencies.jar FileLocal Input2.txt"  
with second parameter passed it will look for Input2.txt inside classpath  
  
Second one is "FilePath" , "FilePath" is option for giving exact input file location from your drives. Second parameter is a must when using "FilePath" as a first parameter.  
  
**example**: "java -jar byteland-0.0.1-SNAPSHOT-jar-with-dependencies.jar FilePath C:\Users\egesa\Desktop\InputByteland.txt"