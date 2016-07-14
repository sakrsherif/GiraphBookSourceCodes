This directory contains source codes for section 2.9 of the Giraph book. 
The projects can be opened in their respective IDEs (reflected in the name of the project directories).
Following is the short description of each of the projects:

#### GiraphDemoRunnerEclipseNoMaven
The project has all the dependencies required and can be run without any modification as the dependency jars are included in project. It uses Giraph version 1.1.0 with Hadoop 1.2.1.
Following is the description of each of its directories

* The ```input``` folder contains the input graph
* The ```src``` folder contains the Java source file
* The ```output``` folder contains the output of the project when it executes successfully. 
Eclipse does not immediately refresh the directory structure.
So, after running the project, right click on the package explorer and click ```Refresh``` to see the produced output folder ```graph_out```.
Before running the project again, delete the ```graph_out``` folder.
* The ```lib/giraph-1.1.0``` folder contains all the Giraph dependency jar files.
* The ```lib/hadoop-1.2.1``` folder contains all the Hadoop dependency jar files.
* The ```_bsp``` folder contains the data and configuration of the local Zookeeper instance that Girah runs in the local mode.

#### GiraphDemoRunnerEclipseMaven
The project requires the internet to download all the dependencies. It uses Giraph version 1.1.0 with Hadoop 2.5.1.

* The ```input``` folder contains the input graph
* The ```src``` folder contains the Java source file
* The ```_bsp``` folder contains the data and configuration of the local Zookeeper instance that Girah runs in the local mode.
* The ```output``` folder contains the output of the project when it executes successfully. 
Eclipse does not immediately refresh the directory structure.
So, after running the project, right click on the package explorer and click ```Refresh``` to see the produced output folder ```graph_out```.
Before running the project again, delete the ```graph_out``` folder.
* The ```target``` folder contains the output of the maven compliation i.e. jar file of the project.
* The ```pom.xml``` file contains the project dependencies that are downloaded automatically.

The project can be compiled by right-clicking on the project, clicking on ```Run as``` and chosing ```Maven build...```.
In the maven build window, write ```compile``` in the ```goals``` text box and click on the ```Run``` button. 
This will compile the project. After this, you can right click on the ```GiraphDemoRunner.java``` file in the package explorer
window and choose ```Run As-->Java Application```.

#### GiraphDemoRunnerIntellijNoMaven
This intelliJ project does not use maven for managing project dependencies and is not configured to point to the dependencies. It uses Giraph version 1.1.0 with Hadoop 1.2.1.
Follow chapter 2 of the book to see how to add jar files as dependencies. 
Downloading Giraph or Hadoop is not required as all the jar files are located in the ```lib``` folder and can be added using the project structure window.

Following is the directory structure of the project:
* The ```input``` folder contains the input graph
* The ```src``` folder contains the Java source file
* The ```_bsp``` folder contains the data and configuration of the local Zookeeper instance that Girah runs in the local mode.
* The ```output``` folder contains the output of the project when it executes successfully. 
Before running the project again, delete the ```graph_out``` folder located inside the ```output``` folder.
* The ```out``` folder contains the output of the compliation i.e. generated class files.
* The ```.idea``` folder contains IntelliJ IDEA project settings.


#### GiraphDemoRunnerIntellijMaven
The project requires the internet to download all the dependencies. It uses Giraph version 1.1.0 with Hadoop 2.5.1.
Right-click on the ```GiraphDemoRunner.java``` file in project explorer window and choose ```Run```.

Following is the directory structure of the project:
* The ```src/main/resources/input``` folder contains the input graph
* The ```src/main/java``` folder contains the Java source file
* The ```_bsp``` folder contains the data and configuration of the local Zookeeper instance that Girah runs in the local mode.
* The ```src/main/resources/output``` folder contains the output of the project when it executes successfully. 
Before running the project again, delete the ```graph_out``` folder located inside the ```output``` folder.
* The ```target``` folder contains the output of the maven compliation i.e. jar file of the project.
* The ```pom.xml``` file contains the project dependencies that are downloaded automatically.
* The ```.idea``` folder contains IntelliJ IDEA project settings.
* ```GiraphDemoRunnerIntellijMaven.iml``` is the IntelliJ IDEA project file.
