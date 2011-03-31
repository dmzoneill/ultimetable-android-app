package ie.ul.slot;


public class LectureSlot extends Slot implements iSlot 
{	
	public LectureSlot( String room , String module , int time )
	{
		this.module = module;
		this.room = room;
		this.time = time;
	}
}
