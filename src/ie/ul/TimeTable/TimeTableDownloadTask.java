

package ie.ul.TimeTable;

import java.lang.ref.WeakReference;
import ie.ul.ULTT;
import ie.ul.slot.iSlot;
import android.os.AsyncTask;


public class TimeTableDownloadTask extends AsyncTask< String , Void , iSlot[][] >
{


	private TimeTable studentTimetable;
	private final WeakReference< ULTT > parent;
	private String studentID;


	public TimeTableDownloadTask( String StudentID , WeakReference< ULTT > parent )
	{

		this.parent = parent;
		this.studentID = StudentID;
	}


	@Override
	protected iSlot[][] doInBackground( String... params )
	{

		this.studentTimetable = new TimeTable( this.studentID );

		if( this.studentTimetable.getTimeTable() != null )
		{
			return this.studentTimetable.getTimeTable();
		}

		return null;
	}


	@Override
	protected void onPostExecute( iSlot[][] result )
	{

		if( isCancelled() )
		{
			return;
		}

		ULTT parentWindow = this.parent.get();
		parentWindow.drawTable( result );

	}

}
