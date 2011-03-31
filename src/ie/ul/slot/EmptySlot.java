package ie.ul.slot;


public class EmptySlot extends Slot implements iSlot 
{
	public EmptySlot( )
	{
		this.module = "";
		this.room = "";
		this.time = 0;
	}
}
