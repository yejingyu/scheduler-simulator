# Scheduler-Simulator
Simulating different scheduler and see their performance with randomly generated task

## Introduction
This program simulate the task schedulers when runing System Management Mode. It would gr
It currently support 5 scheduler:
1. First Come First Served
2. Shortest Job First
3. Longest Job First
4. Priority First
5. Knapsack
6. Best-fit Bin Packing

## How to use
First, you need to set up the simulator. Let's go to simulator.java [line 23-25], you can change the setting of the simulator. Then you just need to compile and run the program. It is easy!<br />
If you want to add new scheduler, just make sure it inherited the scheduler class. Then you should add it into the scheduler list in simulator.java [line 31].<br />
If you want to add new task type, you can do so at taskType.java [line 15]. You can see the constructor for more detail.

## To-do List
1. Add real SMM task to taskType.java.
2. Build a input system so that the user not need to recompile after changing the setting.
3. Add more statistics infomation collection.
4. [Done!] ~~Support Bin Packing scheduler.~~
