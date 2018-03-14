import java.util.LinkedList;

/* This file has the implementation of Best-fit bin packing scheduler. When
 * adding tasks into list, it sort the task as from longest to shortest.
 * Then the tasks at the head of the list run first.
 */
public class binPacking extends scheduler
{
	binPacking()
	{
		super();
		initials = "BINP";
	}
	
	binPacking(int givenMaxTime)
	{
		super(givenMaxTime);
		initials = "BINP";
	}
	
	public int schedule()
	{	
		int runtimeRemain = 0;
		LinkedList<bin> binList = new LinkedList<bin>();
		LinkedList<Integer> runList;
		int timer = 0;
		int minBin = 0;
		int minRemain = 0;
		boolean canNotFit = true; 
		
		//if the scheduler has no task, return 0
		if(taskList.isEmpty())
			return 0;
		
		//if a task is blocking the scheduler, return error
		if(taskList.get(0).getRuntime() > maxRuntime)
			return -1;
		
		//run scheduler
		for(int i = 0; i < taskList.size(); i++)
		{
			canNotFit = true;
			minRemain = maxRuntime;
			for(int j = 0; j < binList.size(); j++)
			{
				//Find the smallest bin which is big enough to fit the task
				if(binList.get(j).getRemain() + taskList.get(j).getRuntime()<= maxRuntime &&
						binList.get(j).getRemain() < minRemain)
				{
					minRemain = binList.get(j).getRemain();
					minBin = j;
					canNotFit = false;
				}
			}
			//if bin can not fit in any bin, make new bin
			if(canNotFit)
			{
				binList.add(new bin(maxRuntime));
				minBin = binList.size()-1;
			}
			binList.get(minBin).add(i,taskList.get(i));//add task into bin
		}
		
		//find out which bin is the best fit bin
		minBin = 0;
		minRemain = binList.get(0).getRemain();
		for(int i = 1; i < binList.size(); i++)
		{
			if(binList.get(i).getRemain() < minRemain)
			{
				minBin = i;
				minRemain = binList.get(i).getRemain();
			}
		}
		//get the list of should-be-run task and run them
		runList = binList.get(minBin).getTaskList();
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
	
	//add new task to the end of queue
	public boolean add(task source)
	{
		task buffer = new task(source);
		
		taskList.addLast(buffer);
		return true;
	}

	//print the name of the scheduler and use the function
	//in parent to print the status
	public void printStat(int toatlRound)
	{
		System.out.print("Best-fit Bin Packing:");
		super.printStat(toatlRound);
	}
}
