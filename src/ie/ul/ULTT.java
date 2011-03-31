

package ie.ul;

import ie.ul.TimeTable.TimeTableDownloadTask;
import ie.ul.slot.iSlot;
import java.lang.ref.WeakReference;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;


public class ULTT extends Activity
{


	public static WeakReference< ULTT > me;
	private final String[] mLabelArray = { "Monday" , "Tuesday" , "Wednesday" , "Thursday" , "Friday" };
	private FlingBook pages;
	private int pageColor;



	@Override
	public boolean onTouchEvent( MotionEvent event )
	{

		return pages.onGalleryTouchEvent( event );
	}



	public void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		ULTT.me = new WeakReference< ULTT >( this );
		this.pageColor = Color.argb( 255 , 255 , 255 , 255 );
		this.setupFlingPageInterface();
		
		TimeTableDownloadTask k = new TimeTableDownloadTask( "0813001" , new WeakReference< ULTT >( this ) );
		k.execute( "blah" );
	}


	public void setupFlingPageInterface()
	{

		this.pages = new FlingBook( this );
		this.pages.setPaddingWidth( 5 );
		this.pages.setAdapter( new ArrayAdapter< String >( getApplicationContext() , android.R.layout.simple_list_item_1 , mLabelArray )
		{
			@Override
			public View getView( int position , View convertView , ViewGroup parent )
			{

				if( convertView != null && convertView instanceof FlingPage )
				{
					FlingPage view = ( FlingPage ) convertView;
					return view;
				}

				return new FlingPage( getApplicationContext() , position , mLabelArray[ position ] , 0 );
			}
		} );

		LinearLayout layout = new LinearLayout( getApplicationContext() );
		layout.setOrientation( LinearLayout.VERTICAL );

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.MATCH_PARENT );

		layoutParams.setMargins( 10 , 10 , 10 , 10 );
		layout.setBackgroundColor( this.pageColor );
		layoutParams.weight = 1.0f;
		layout.addView( this.pages , layoutParams );

		setContentView( layout );

	}



	public void moveNext()
	{

		this.pages.moveNext();
	}


	public void movePrevious()
	{

		this.pages.movePrevious();
	}


	public void drawTable( iSlot[][] table )
	{

		/*
		 * TextView t = ( TextView ) findViewById( R.id.test ); t.setText(
		 * "complete!!!" );
		 */
	}

}
