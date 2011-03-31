

package ie.ul;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.content.Context;
import android.content.Intent;


public class Welcome extends Activity
{


	private PopupWindow popup;


	public void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.welcome );

		Button next = ( Button ) findViewById( R.id.Button01 );
		next.setOnClickListener( new View.OnClickListener()
		{


			public void onClick( View view )
			{

				Intent myIntent = new Intent( view.getContext() , ULTT.class );
				startActivityForResult( myIntent , 0 );
			}

		} );
	}


	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{

		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.menu , menu );
		return true;
	}


	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{

		switch ( item.getItemId() )
		{
		case R.id.create :
			popupWindow();
			break;
		case R.id.info :

			break;
		case R.id.options :

			break;

		}
		return true;
	}


	public void popupWindow()
	{

		LayoutInflater inflater = ( LayoutInflater ) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View layout = inflater.inflate( R.layout.popup , ( ViewGroup ) findViewById( R.id.popup_menu_root ) );
		this.popup = new PopupWindow( layout , 100 , 200 , true );

		this.popup.showAtLocation( layout , Gravity.CENTER , 0 , 0 );

		Log.e( "popup" , "being?" );

	}

}
