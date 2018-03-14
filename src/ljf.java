/* This file has the implementation of Longest Job First scheduler. When
 * adding tasks into list, it sort the task as from longest to shortest.
 * Then the tasks at the head of the list run first.
 */

public class ljf extends scheduler
{
	ljf()
	{
		super();
		initials = "LJF";
	}
	
	ljf(int givenMaxTime)
	{
		super(givenMaxTime);
		initials = "LJF";
	}

	//Run the scheduler, pop as much scheduler as possible before
	//the time run out. It returns <0 if error occur, otherwise,
	//it return the total task runtime.
	public int schedule()
	{
		return super.schedule();
	}

	//add new task to the list, longest runtime first
	//if runtime are the same, fcfs
	public boolean add(task source)
	{
		task buffer = new task(source);
		
		for(int i = 0; i < taskList.size(); i++)
			if(buffer.getRuntime() > taskList.get(i).getRuntime())
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
		System.out.print("Longest Job First:");
		super.printStat(toatlRound);
	}
}
