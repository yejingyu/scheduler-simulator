/* This file has the implementation of knapsack scheduler. 
 */
import java.util.LinkedList;

public class knapsack extends scheduler
{
	private backpack backpackMatrix[][];
	
	knapsack()
	{
		super();
	}
	
	knapsack(int givenMaxTime)
	{
		super(givenMaxTime);
	}
	
	public int schedule()
	{
		int timer = 0;
		task buffer;
		LinkedList<Integer> runList;
		//if the scheduler has no tesk, return 0
		if(taskList.isEmpty())
			return 0;
		
		//if a task is blocking the scheduler, return error
		if(taskList.get(0).getRuntime() > maxRuntime)
			return -1;
		
		/* run scheduler
		 * find out which tasks should be run.
		 * i = the number that represent the task
		 * j = allowed runtime or backpack size
		 */
		backpackMatrix = new backpack[taskList.size()][maxRuntime+1];//include 0 runtime
		for(int i=0; i < taskList.size(); i++)
		{
			buffer = taskList.get(i);
			backpackMatrix[i][0] = new backpack();//backpackMatrix initialization
			for(int j=1; j <= maxRuntime; j++)
			{
				//backpackMatrix initialization
				if(i > 0)
					backpackMatrix[i][j] = new backpack(backpackMatrix[i-1][j]);
				else
					backpackMatrix[i][j] = new backpack();
				
				
				if(buffer.getRuntime()<= j)
				{
					//max((the Value Of This Task + the max value Of( allowed runtime - This Task)),
					//    (the max value Of current allowed runtime without this task))
					if(buffer.getPriority()
							+ backpackMatrix[i][j-buffer.getRuntime()].getValue()
							> backpackMatrix[i][j].getValue())
					{							
						backpackMatrix[i][j].addTesk(i, buffer);
					}
				}
			}
		}
		//get the list of should-be-run task and run them
		runList = backpackMatrix[taskList.size()-1][maxRuntime].getPositions();
		while(!runList.isEmpty())
		{
			taskList.remove(runList.get(0).intValue());
			runList.remove();
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
	
	//add new task to the list, higher priority first
	//if priority are the same, fcfs
	public boolean add(task source) {
		task buffer = new task(source);
		
		taskList.addLast(buffer);
		return false;
	}
	
	//pring the name of the scheduler and use the function
	//in parent to print the task list
	public void printTask()
	{
		System.out.print("KNAP: ");
		super.printTask();
	}

	//pring the name of the scheduler and use the function
	//in parent to print the status
	public void printStat()
	{
		System.out.print("Knapsack:");
		super.printStat();
	}
}