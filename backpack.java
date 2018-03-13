/* This file has the implementation of backpack class, which is used
 * by the knapsack scheduler. 
 */
import java.util.LinkedList;

public class backpack
{
	private LinkedList<Integer> taskPosition;
	private int totalValue;
	private int totalWeight;
	
	backpack()
	{
		taskPosition = new LinkedList<Integer>();
		totalValue = 0;
		totalWeight = 0;
	}
	
	backpack(backpack source)
	{
		clone(source);
	}
	
	public void clone(backpack source)
	{
		taskPosition = new LinkedList<Integer>();
		for(Integer element : source.taskPosition)
			taskPosition.addLast(element);
		totalValue = source.totalValue;
		totalWeight = source.totalWeight;
	}
	
	
	//when adding task, sort the task position from large to small so that
	//when the scheduler remove the task, the position would not be massed up
	public void addTesk(int sourceTaskPosition, task inData)
	{
		Integer buffer = new Integer(sourceTaskPosition);

		totalValue += inData.getPriority();
		totalWeight += inData.getRuntime();
		
		for(int i = 0; i < taskPosition.size(); i++)
			if(buffer.intValue() > taskPosition.get(i).intValue())
			{
				taskPosition.add(i, buffer);
				return;
			}
		taskPosition.addLast(buffer);
	}
	
	public int getValue()
	{
		return totalValue;
	}
	
	public int getWeight()
	{
		return totalWeight;
	}
	
	public LinkedList<Integer> getPositions()
	{
		return taskPosition;
	}
}
