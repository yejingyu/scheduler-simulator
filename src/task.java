/* This file has the implementation of task. It has the runtime of the tasks
 * and it can randomly generated task base on the given task type.
 */

public class task {
	private int runtime;
	private int priority;
	
	task()
	{
		runtime = 0;
		priority = 0;
	}
	
	task(task source)
	{
		runtime = source.runtime;
		priority = source.priority;
	}
	
	//generate task base on the task type
	task(taskType source)
	{
		runtime = source.possibleRuntime();
		priority = source.possiblePriority();
	}
	
	task(int sourceTime, int sourcePriority)
	{
		runtime = sourceTime;
		priority = sourcePriority;
	}
	
	public void setTime(int sourceTime)
	{
		runtime = sourceTime;
	}
	
	//return the runtime of the task
	public int getRuntime()
	{
		return runtime;
	}
	
	//set the priority for the task
	public void setPriority(int sourcePriority)
	{
		priority = sourcePriority;
	}
	
	//return the priority of the task
	public int getPriority()
	{
		return priority;
	}
	
	//toString function for other displayment
	public String toString()
	{
		return "(" + runtime + ", " + priority + ")";
	}
}
