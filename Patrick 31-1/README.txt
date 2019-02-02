1) Scheduler_Queue.java

Run this file as a java application first to start up.
Scheduler sends and receives packets from both Floor subsystem and Elevator subsystem.

2) Floor.java

Run this file next.
It controls the Elevator Out panel on each floor, which sends and receives from Scheduler.

3) Elevator_model.java

Run this file next.
It controls the Elevator In panel inside the elavtor, which sends and recieve packets from scheduler.

4) Elevator_outPanel.java

Run this file next.
Type if u want to go up or down. Sends a packet request to floor.

5) Elevator_inPanel.java

Run this file next.
Type out the floor you want the eleavator to go to. It sends request to the Elevator_model subsystem as a packet.






Testing the code

Notes: 
output key:
elevator movement is output in the command window of Elevator_Model.
outside panel lights are output in the command window of Floor.
critical packet informtion is output in the command window of Scheduler_Queue.

Run the files accordingly like mentioned above in the order (1-3).
Run the Elevator_outPanel.java
Type if u want to go up or down. (Eg. Type "down")
Elevator starts at floor 1. But for the OutPanel, it is set to Floor Random(Random number generator for human simulation) for testing purposes.
The light for down is on at Floor Random. 
Elevator goes up to Floor Random.
The light is off since Elevator reached Floor Random.
Run the Elevaor_InPanel.java.
Since we Typed down, choose a floor below Floor Random. If u typed Up choose a Floor above Floor Random.
Type Floor 2 for testing.
Elevator goes down to Floor 2 and currently at Floor 2.
Open Elevator_inPanel again.
Assume a person getting inside and goes to (Eg. Type 9)
Print statements describes the previous information about the Elevator's situation.
Elevator goes up to Floor 9.
Elevator OutPanel is already running in console asking for direction.
Type down. A random number is generated to go the floor.(Eg. 4)
Light is on at Floor 4.
Elevator has reached Floor 4 and Light is off.



Packet Information.

Please refer to the word Document Array.docx



Distribution of work.

Everyone worked and helped on every file which was made. Work was shared.

Patrick
Worked on Scheduler and Elevator model Subsystems, inPanel, outPanel.

Goutham
Worked on Floor and Scheduler subsytems.

Nikhil
Worked on Elevator inPanel, outPanel, Queues and UML class diagrams.

Zehui
Worked on Elevator model and State machine Diagrams. 