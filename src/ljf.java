/* This file has the implementation of Longest Job First scheduler. When
 * adding tasks into list, it sort the task as from longest to shortest.
 * Then the tasks at the head of the list run first.
 */

public class ljf extends scheduler
{
	ljf()
	{
		super();
	}
	
	ljf(int givenMaxTime)
	{
		super(givenMaxTime);
	}

	//Run the scheduler, pop as much scheduler as possible before
	//the time run out. It returns <0 if error occuer, otherwise,
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
	
	//pring the name of the scheduler and use the function
	//in parent to print the task list
	public void printTask()
	{
		System.out.print("LJF : ");
		super.printTask();
	}

	//pring the name of the scheduler and use the function
	//in parent to print the status
	public void printStat()
	{
		System.out.print("Longest Job First:");
		super.printStat();
	}
}