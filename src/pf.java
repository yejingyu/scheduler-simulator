/* This file has the implementation of Priority First scheduler. When
 * adding tasks into list, it sort the task from highest to smallest.
 * Then the tasks at the head of the list run first.
 */

public class pf extends scheduler
{
	pf()
	{
		super();
		initials = "PF";
	}
	
	pf(int givenMaxTime)
	{
		super(givenMaxTime);
		initials = "PF";
	}

	//Run the scheduler, pop as much scheduler as possible before
	//the time run out. It returns <0 if error occur, otherwise,
	//it return the total task runtime.
	public int schedule()
	{
		return super.schedule();
	}

	//add new task to the list, higher priority first
	//if priority are the same, fcfs
	public boolean add(task source)
	{
		task buffer = new task(source);
		
		for(int i = 0; i < taskList.size(); i++)
			if(buffer.getPriority() > taskList.get(i).getPriority())
			{
				taskList.add(i, buffer);
				return true;
			}
		taskList.addLast(buffer);
		return true;
	}

	//print the name of the scheduler and use the function
	//in parent to print the status
	public void printStat(int toatlRound)
	{
		System.out.print("Priority First:");
		super.printStat(toatlRound);
	}
}
