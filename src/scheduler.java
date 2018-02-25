/* This file has the implementation of scheduler. It is the base class of
 * all other schedulers. It is respond to store and print the data and
 * statistics data.
 * eexecutionRound is how many runs the scheduler process at lease one job.
 * emptyRound is how many runs the scheduler process all its job.
 * sumOfJobRemain is the sum of the number of remain jobs after each runs.
 */

import java.util.LinkedList;

public abstract class scheduler
{
	protected int maxRuntime;
	protected int executionRound;
	protected int emptyRound;
	protected int sumOfJobRemain;
	protected int sumOfRuntimeRemain;
	protected int sumOfPriorityRemain;
	protected LinkedList<task> taskList;
	
	abstract public boolean add(task source);
	
	scheduler()
	{
		maxRuntime = 150;
		executionRound = 0;
		emptyRound = 0;
		sumOfJobRemain = 0;
		sumOfRuntimeRemain = 0;
		sumOfPriorityRemain = 0;
		taskList = new LinkedList<task>();
	}
	
	scheduler(int givenMaxTime)
	{
		maxRuntime = givenMaxTime;
		executionRound = 0;
		emptyRound = 0;
		sumOfJobRemain = 0;
		sumOfRuntimeRemain = 0;
		sumOfPriorityRemain = 0;
		taskList = new LinkedList<task>();
	}
	
	//set the max runtime the scheduler can run
	void setMaxRuntime(int givenMaxRuntime)
	{
		maxRuntime = givenMaxRuntime;
	}
	
	/* This is the base scheduler that pop as much task as possible
	 * before the time run out. If the scheduler has its own requirement
	 * of scheduling, this one should not be use. It returns <0 if error
	 * occuer; otherwise, it return the total task runtime.
	 */
	public int schedule()
	{
		int timer = 0;
		
		//if the scheduler has no tesk, return 0
		if(taskList.isEmpty())
			return 0;
		
		//if a task is blocking the scheduler, return error
		if(taskList.get(0).getRuntime() > maxRuntime)
			return -1;
		
		//run scheduler
		while(!taskList.isEmpty())
		{
			if(timer + taskList.get(0).getRuntime() <= maxRuntime)
				timer += taskList.get(0).getRuntime();
			else
				break;
			taskList.remove();
		}
		
		//Calculate statistics status
		executionRound++;
		if(taskList.isEmpty())
			emptyRound++;
		sumOfJobRemain += taskList.size();
		for(task element : taskList)
		{
			sumOfRuntimeRemain += element.getRuntime();
			sumOfPriorityRemain += element.getPriority();
		}
		
		return timer; //return total time of tasks has been run
	}
	
	//print the task in the list
	void printTask()
	{
		boolean notFirst = false;
		for(task element : taskList)
		{
			if(notFirst)
				System.out.print("->");
			else
				notFirst = true;
			System.out.print(element.toString());
		}
		System.out.print("\n");
	}
	
	//print the statistics status of the scheduler
	void printStat()
	{
		System.out.println("\n  Eexecution Round      : " + executionRound +
						   "\n  Empty Round           : " + emptyRound +
						   "\n  Sum of Job Remain     : " + sumOfRuntimeRemain +
						   "\n  Sum of Runtime Remain : " + sumOfPriorityRemain +
						   "\n  Sum of Priority Remain: " + sumOfJobRemain + "\n");
	}
	
	//print the status of the scheduler
	boolean isEmpty()
	{
		return taskList.isEmpty();
	}
}