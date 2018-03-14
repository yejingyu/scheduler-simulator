/* This file has the implementation of knapsack scheduler. 
 */
import java.util.LinkedList;

public class knapsack extends scheduler
{
	private backpack backpackMatrix[][];
	
	knapsack()
	{
		super();
		initials = "KNAP";
	}
	
	knapsack(int givenMaxTime)
	{
		super(givenMaxTime);
		initials = "KNAP";
	}
	
	public int schedule()
	{
		int runtimeRemain = 0;
		int timer = 0;
		task taskBuffer;
		LinkedList<Integer> runList;
		//if the scheduler has no task, return 0
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
			taskBuffer = taskList.get(i);
			
			backpackMatrix[i][0] = new backpack();//backpackMatrix initialization
			for(int j=1; j <= maxRuntime; j++)
			{
				//backpackMatrix initialization
				if(i > 0)
					backpackMatrix[i][j] = new backpack(backpackMatrix[i-1][j]);
				else
					backpackMatrix[i][j] = new backpack();
				
				
				if(taskBuffer.getRuntime()<= j)
				{
					//max((the Value Of This Task + the max value Of( allowed runtime - This Task)),
					//    (the max value Of current allowed runtime without this task))
					if(taskBuffer.getPriority()
							+ backpackMatrix[i][j-taskBuffer.getRuntime()].getValue()
							> backpackMatrix[i][j].getValue())
					{	
						if(i > 0)
							backpackMatrix[i][j] = new backpack(backpackMatrix[i-1][j-taskBuffer.getRuntime()]);
						backpackMatrix[i][j].addTesk(i, taskBuffer);
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
		
		if(taskList.size() > maxJobRemain)
			maxJobRemain = taskList.size();
		for(task element : taskList)
		{
			runtimeRemain += element.getRuntime();
			sumOfRuntimeRemain += element.getRuntime();
			sumOfPriorityRemain += element.getPriority();
		}
		if(runtimeRemain > maxRuntimeRemain)
			maxRuntimeRemain = runtimeRemain;
		return timer; //return total time of tasks has been run
	}
	
	//add new task to the list, higher priority first
	//if priority are the same, fcfs
	public boolean add(task source) {
		task buffer = new task(source);
		
		taskList.addLast(buffer);
		return false;
	}

	//print the name of the scheduler and use the function
	//in parent to print the status
	public void printStat(int toatlRound)
	{
		System.out.print("Knapsack:");
		super.printStat(toatlRound);
	}
}
