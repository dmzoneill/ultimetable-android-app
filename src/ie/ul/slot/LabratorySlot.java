package ie.ul.slot;


public class LabratorySlot extends Slot implements iSlot 
{
	public LabratorySlot( String room , String module , int time )
	{
		this.module = module;
		this.room = room;
		this.time = time;
	}
}
