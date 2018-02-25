/* This file has the implementation of backpack class, which is used
 * by the knapsack scheduler. 
 */
import java.util.LinkedList;

public class backpack
{
	private LinkedList<Integer> jobPosition;
	private int totalValue;
	private int totalWeight;
	
	backpack()
	{
		jobPosition = new LinkedList<Integer>();
		totalValue = 0;
		totalWeight = 0;
	}
	
	backpack(backpack source)
	{
		clone(source);
	}
	
	public void clone(backpack source)
	{
		jobPosition = new LinkedList<Integer>(source.jobPosition);
		totalValue = source.totalValue;
		totalWeight = source.totalWeight;
	}
	
	public void addTesk(int taskPosition, task inData)
	{
		Integer buffer = new Integer(taskPosition);

		totalValue += inData.getPriority();
		totalWeight += inData.getRuntime();
		
		for(int i = 0; i < jobPosition.size(); i++)
			if(buffer.intValue() > jobPosition.get(i).intValue())
			{
				jobPosition.add(i, buffer);
				return;
			}
		jobPosition.addLast(buffer);
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
		return jobPosition;
	}
}
