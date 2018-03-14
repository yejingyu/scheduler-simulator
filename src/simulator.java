/* This file has the main() function of the program. It generate tasks and
 * put them into each schedulers. Then it runs the schedulers. At the end,
 * it shows the status of each schedulers.
 */

import java.io.BufferedWriter;
import java.util.concurrent.ThreadLocalRandom;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class simulator
{
	static scheduler schedulerList[];
	static int totalTask;
	static int remainTask;
	static int maxTaskPerRound;
	static int taskPerRound;
	static int totalRound;
	static int totalRun;
	static int maxTime;
	
	public static void main(String[] args)
	{
		
		//simulator set up
		maxTime = 100;			//How many time does the schedulers have?
		totalTask = 400;		//How many tasks should be placed into scheduler?
		maxTaskPerRound = 20;	//The max tasks that are placed into scheduler in each round.
		totalRun = 30;			//How many test run do you want to try?
		
		//if we have only one test run, shows all detail for debug;
		//Otherwise, output the result as .csv file for analysis.
		if(totalRun == 1)
		{
			singleRun();
		}
		else
		{
			try {
				multiRun(totalRun);
				System.out.println("Done!\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Reset the counter and schedulers
	static void initialize()
	{
		remainTask = totalTask;
		taskPerRound = 0;
		totalRound = 0;
		schedulerList = new scheduler[]{
							new fcfs(maxTime),
							new sjf(maxTime),
							new ljf(maxTime),
							new pf(maxTime),
							new knapsack(maxTime),
							new binPacking(maxTime)
							};
	}
	
	//Run the test one time. In each loop, print the tasks in scheduler
	//before and after the scheduling. At the end, print the result.
	static void singleRun()
	{
		int i,j;
		task taskBuffer;
		boolean allSchedulerEmpty = true;
		initialize();
		
		do
		{
			//get a random number of how many new task this round
			taskPerRound  = ThreadLocalRandom.current().nextInt(1, maxTaskPerRound + 1);
			if(remainTask - taskPerRound < 0)//if random number larger than remain needed task
				taskPerRound = remainTask;
			
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
			
			remainTask -= taskPerRound;
			totalRound++;
		}
		while(remainTask > 0 || !allSchedulerEmpty);
		
		System.out.print("\nStatistics:\n");
		for(i = 0; i < schedulerList.length; i++)
			schedulerList[i].printStat(totalRound);
	}
	
	//Run the test multiple time and Print the result to a csv file which
	//you can open them using most of the spreadsheet application, such as Excel. 
	static void multiRun(int runs) throws IOException
	{
		int i,j;
		task taskBuffer;
		boolean allSchedulerEmpty = true;
		BufferedWriter executionRoundFile = new BufferedWriter(new FileWriter("executionRoundFile.csv"));
		BufferedWriter emptyRoundFile = new BufferedWriter(new FileWriter("emptyRoundFile.csv"));
		BufferedWriter maxJobRemainFile = new BufferedWriter(new FileWriter("maxJobRemainFile.csv"));
		BufferedWriter maxRuntimeRemainFile = new BufferedWriter(new FileWriter("maxRuntimeRemain.csv"));
		BufferedWriter sumOfJobRemainFile = new BufferedWriter(new FileWriter("sumOfJobRemain.csv"));
		BufferedWriter sumOfRuntimeRemainFile = new BufferedWriter(new FileWriter("sumOfRuntimeRemain.csv"));
		BufferedWriter sumOfPriorityRemainFile = new BufferedWriter(new FileWriter("sumOfPriorityRemain.csv"));
		BufferedWriter avgJobRemainFile = new BufferedWriter(new FileWriter("avgJobRemain.csv"));
		BufferedWriter avgRuntimeRemainFile = new BufferedWriter(new FileWriter("avgRuntimeRemain.csv"));
		BufferedWriter avgPriorityRemainFile = new BufferedWriter(new FileWriter("avgPriorityRemain.csv"));
		
		//output the name of each scheduler to each file
		initialize();
		writeTitle(executionRoundFile);
		writeTitle(emptyRoundFile);
		writeTitle(maxJobRemainFile);
		writeTitle(maxRuntimeRemainFile);
		writeTitle(sumOfJobRemainFile);
		writeTitle(sumOfRuntimeRemainFile);
		writeTitle(sumOfPriorityRemainFile);
		writeTitle(avgJobRemainFile);
		writeTitle(avgRuntimeRemainFile);
		writeTitle(avgPriorityRemainFile);
		
		for(int k = 0; k < runs; k++)
		{
			allSchedulerEmpty = true;
			initialize();
			do
			{
				//get a random number of how many new task this round
				taskPerRound  = ThreadLocalRandom.current().nextInt(1, maxTaskPerRound + 1);
				if(remainTask - taskPerRound < 0)//if random number larger than remain needed task
					taskPerRound = remainTask;
				
				//add new task to each scheduler
				for(i = 0; i < taskPerRound; i++)
				{
					taskBuffer = new task(taskType.random());
					for(j = 0; j < schedulerList.length; j++)
						schedulerList[j].add(taskBuffer);
				}
				
				//run each scheduler
				for(i = 0; i < schedulerList.length; i++)
					schedulerList[i].schedule();
				
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
				
				remainTask -= taskPerRound;
				totalRound++;
			}
			while(remainTask > 0 || !allSchedulerEmpty);
			
			//output the statistics of each scheduler to each file
			//1. execution round
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					executionRoundFile.write(",");
				executionRoundFile.write(String.valueOf(schedulerList[i].getExecutionRound()));
			}
			executionRoundFile.write("\n");
			
			//2. Empty Round
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					emptyRoundFile.write(",");
				emptyRoundFile.write(String.valueOf(schedulerList[i].getEmptyRound()));
			}
			emptyRoundFile.write("\n");
			
			//3. Max number of Job Remaining
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					maxJobRemainFile.write(",");
				maxJobRemainFile.write(String.valueOf(schedulerList[i].getMaxJobRemain()));
			}
			maxJobRemainFile.write("\n");
			
			//4. Max Runtime Remaining
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					maxRuntimeRemainFile.write(",");
				maxRuntimeRemainFile.write(String.valueOf(schedulerList[i].getMaxRuntimeRemain()));
			}
			maxRuntimeRemainFile.write("\n");
			
			//5. Sum of Job Remain
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					sumOfJobRemainFile.write(",");
				sumOfJobRemainFile.write(String.valueOf(schedulerList[i].getSumOfJobRemain()));
			}
			sumOfJobRemainFile.write("\n");
			
			//6. Sum of Runtime Remain
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					sumOfRuntimeRemainFile.write(",");
				sumOfRuntimeRemainFile.write(String.valueOf(schedulerList[i].getSumOfRuntimeRemain()));
			}
			sumOfRuntimeRemainFile.write("\n");
			
			//7. Sum of Priority Remain
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					sumOfPriorityRemainFile.write(",");
				sumOfPriorityRemainFile.write(String.valueOf(schedulerList[i].getSumOfPriorityRemain()));
			}
			sumOfPriorityRemainFile.write("\n");
			
			//8. Average Job Remain
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					avgJobRemainFile.write(",");
				avgJobRemainFile.write(String.valueOf(schedulerList[i].getSumOfJobRemain()/totalRound));
			}
			avgJobRemainFile.write("\n");
			
			//9. Average Runtime Remain
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					avgRuntimeRemainFile.write(",");
				avgRuntimeRemainFile.write(String.valueOf(schedulerList[i].getSumOfRuntimeRemain()/totalRound));
			}
			avgRuntimeRemainFile.write("\n");
			
			//10. Average Priority Remain
			for(i = 0; i < schedulerList.length; i++)
			{
				if(i != 0)
					avgPriorityRemainFile.write(",");
				avgPriorityRemainFile.write(String.valueOf(schedulerList[i].getSumOfPriorityRemain()/totalRound));
			}
			avgPriorityRemainFile.write("\n");
		}
		
		executionRoundFile.close();
		emptyRoundFile.close();
		maxJobRemainFile.close();
		maxRuntimeRemainFile.close();
		sumOfJobRemainFile.close();
		sumOfRuntimeRemainFile.close();
		sumOfPriorityRemainFile.close();
		avgJobRemainFile.close();
		avgRuntimeRemainFile.close();
		avgPriorityRemainFile.close();
	}
	
	//output the name of each scheduler to file
	static void writeTitle(Writer output) throws IOException
	{
		for(int i = 0; i < schedulerList.length; i++)
		{
			if(i != 0)
				output.write(",");
			output.write(schedulerList[i].getInitials());
		}
		output.write("\n");
	}
}
