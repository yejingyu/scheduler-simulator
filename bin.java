import java.util.LinkedList;

/* This file has the implementation of bin class, which is used
 * by the bin packing scheduler. 
 */
public class bin {
	private LinkedList<Integer> taskPosition;
	private int remainWeight;
	
	bin()
	{
		taskPosition = new LinkedList<Integer>();
		remainWeight = 0;
	}
	
	bin(int size)
	{
		taskPosition = new LinkedList<Integer>();
		remainWeight = size;
	}
	
	//when adding task, sort the task position from large to small so that
	//when the scheduler remove the task, the position would not be massed up
	boolean add(int sourceTaskPosition, task inData)
	{
		if(inData.getRuntime() > remainWeight)
			return false;
		
		Integer buffer = new Integer(sourceTaskPosition);
		remainWeight -= inData.getRuntime();
		
		for(int i = 0; i < taskPosition.size(); i++)
			if(buffer.intValue() > taskPosition.get(i).intValue())
			{
				taskPosition.add(i, buffer);
				return true;
			}
		taskPosition.addLast(buffer);
		return true;
	}
	
	public LinkedList<Integer> getTaskList()
	{
		return taskPosition;
	}
	
	public int getRemain()
	{
		return remainWeight;
	}
	
}
