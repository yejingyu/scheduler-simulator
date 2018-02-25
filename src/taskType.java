/* This file has the implementation of task types. It store the information of task type.
 * It can also randomly generated a task type or possible task runtime.
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum taskType
{
	test1(2,5,1),
	test2(5,15,3),
	test3(15,20,2);
	
	
	private int minRuntime;
	private int maxRuntime;
	private int priority;
	
	//setup for pick a random task type
	private static final Random RANDOM = new Random();
	private static final List<taskType> taskTypeSet = Collections.unmodifiableList(Arrays.asList(values()));
	
	//constructor
	taskType(int min, int max, int prio)
	{
		minRuntime = min;
		maxRuntime = max;
		priority = prio;
	}
	
	//return the min possible task runtime
	public int minRuntime()
	{
	    return minRuntime;
	}
	
	//return the max possible task runtime
	public int maxRuntime()
	{
	    return maxRuntime;
	}
	
	//return a possible task runtime
	public int possibleRuntime()
	{
		return ThreadLocalRandom.current().nextInt(minRuntime, maxRuntime+ 1);
	}
	
	//return the priority of task
	public int priority()
	{
	    return priority;
	}
	
	//pick a random task type
	public static taskType random()
	{
	    return taskTypeSet.get(RANDOM.nextInt(taskTypeSet.size()));
	}
}
