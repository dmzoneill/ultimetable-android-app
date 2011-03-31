package ie.ul.slot;


public class TutorialSlot extends Slot implements iSlot 
{	
	public TutorialSlot( String room , String module , int time )
	{
		this.module = module;
		this.room = room;
		this.time = time;
	}
}
