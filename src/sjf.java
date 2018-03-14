/* This file has the implementation of Shortest Job First scheduler. When
 * adding tasks into list, it sort the task from shortest to longest.
 * Then the tasks at the head of the list run first.
 */

public class sjf extends scheduler
{
	sjf()
	{
		super();
		initials = "SJF";
	}
	
	sjf(int givenMaxTime)
	{
		super(givenMaxTime);
		initials = "SJF";
	}

	//Run the scheduler, pop as much scheduler as possible before
	//the time run out. It returns <0 if error occur, otherwise,
	//it return the total task runtime.
	public int schedule()
	{
		return super.schedule();
	}

	//add new task to the list, shortest runtime first
	//if runtime are the same, fcfs
	public boolean add(task source)
	{
		task buffer = new task(source);
		
		for(int i = 0; i < taskList.size(); i++)
			if(taskList.get(i).getRuntime() > buffer.getRuntime())
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
		System.out.print("Shortest Job First:");
		super.printStat(toatlRound);
	}
}
