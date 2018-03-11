/* This file has the main() function of the program. It generate tasks and
 * put them into each schedulers. Then it runs the schedulers. At the end,
 * it shows the status of each schedulers.
 */

import java.util.concurrent.ThreadLocalRandom;

public class simulator
{
	static scheduler schedulerList[];
	static int totalTask;
	static int maxTaskPerRound;
	
	public static void main(String[] args)
	{
		int taskPerRound = 0;
		int totalRound = 0;
		int maxTime;
		int i,j;
		task taskBuffer;
		boolean allSchedulerEmpty = true;
		
		//simulator initialization
		maxTime = 100;
		totalTask = 100;
		maxTaskPerRound = 20;
		schedulerList = new scheduler[]{
							new fcfs(maxTime),
							new sjf(maxTime),
							new ljf(maxTime),
							new pf(maxTime),
							new knapsack(maxTime),
							new binPacking(maxTime)
							};
		
		//run all scheduler
		do
		{
			//get a random number of how many new task this round
			taskPerRound  = ThreadLocalRandom.current().nextInt(1, maxTaskPerRound + 1);
			if(totalTask - taskPerRound < 0)//if random number larger than remain needed task
				taskPerRound = totalTask;
			
			//add new task to each scheduler
			for(i = 0; i < taskPerRound; i++)
			{
				taskBuffer = new task(taskType.random());
				for(j = 0; j < schedulerList.length; j++)
					schedulerList[j].add(taskBuffer);
			}
			
			//print the task in each scheduler
			System.out.print("\nBefor runing:\n");
			for(i = 0; i < schedulerList.length; i++)
				schedulerList[i].printTask();
			
			//run each scheduler
			for(i = 0; i < schedulerList.length; i++)
				schedulerList[i].schedule();
			
			//print the task in each scheduler
			System.out.print("\nAfter runing:\n");
			for(i = 0; i < schedulerList.length; i++)
				schedulerList[i].printTask();
			
			totalTask -= taskPerRound;
			
			//check all scheduler is empty or not 
			allSchedulerEmpty = true;
			for(i = 0; i < schedulerList.length; i++)
			{
				if(!schedulerList[i].isEmpty())
				{
					allSchedulerEmpty = false;
					break;
				}
			}
			totalRound++;
		}
		while(totalTask > 0 || !allSchedulerEmpty);
		
		System.out.print("\nStatistics:\n");
		for(i = 0; i < schedulerList.length; i++)
			schedulerList[i].printStat(totalRound);
	}
}
