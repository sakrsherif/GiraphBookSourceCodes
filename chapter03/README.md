This directory contains two projects from chapter 03.

### VertexSimilarity
It contains the vertex similarity algorithm. Everything is pre-configured.
Just run the ```JaccardRunner.java``` file and see the output in ```src/main/resources/output``` folder.
The ```src/main/resources/input``` folder contains the input to the program.

### GiraphDriverPrograms
This project demonstrates 3 different ways of writing the Giraph driver program. 

* The ```src/main/java/GiraphDriverMain.java``` uses the 'Main' method as a driver.
* The ```src/main/java/GiraphDriverTool``` uses the ```ToolRunner``` for running the Giraph job.
* The ```src/main/java/GiraphDriverGiraphRunner``` uses the ```GiraphRunner``` for running the Giraph job.

The files ```GiraphDriverTool``` and ```GiraphDriverGiraphRunner``` do not contain any Giraph configuration in the source 
and expect the configuration to be provided at the command line. Each of the files are readily executable. Open their respective
run configuration in the IntelliJ IDEA to view or modify the Giraph configuration options.
