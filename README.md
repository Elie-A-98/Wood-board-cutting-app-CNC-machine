# Wood-board-cutting-app-CNC-machine

Desktop application (built with Java) and CNC machine built and programmed in C from scratch, to help cutting rectangular pieces of wood from a large piece with minimal waste.

The user gives the application the size of the large piece of wood, and the sizes of the pieces he wants to cut.

A heuristic 2D packing algorithm is used to solve the minimal waste problem, then the output of that algorithm is used to draw the ouput on the screen. 

Finally, this output is sent to the microcontroller of the CNC machine, where a simple drawing algorithm is used to control 4 stepper motors and move them based on the microcnotroller's input (the desktop application output) to draw marks on the large piece of wood that needs to be cut.


