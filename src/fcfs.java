/* This file has the implementation of First Come First Served scheduler.
 * When adding tasks into list, it always add them to the tail.
 * Then the tasks at the head of the list run first.
 */

public class fcfs extends scheduler
{	
	fcfs()
	{
		super();
		initials = "FCFS";
	}
	
	fcfs(int givenMaxTime)
	{
		super(givenMaxTime);
		initials = "FCFS";
	}
	
	public int schedule()
	{
		return super.schedule();
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
		System.out.print("First Come First Served:");
		super.printStat(toatlRound);
	}
}
