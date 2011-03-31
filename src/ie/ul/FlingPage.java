

package ie.ul;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class FlingPage extends TableLayout
{


	private static final int LAYOUT_MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
	private static final int LAYOUT_WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;

	private TextView pageLabel;
	private Button previousButton;
	private Button nextButton;
	private int pageColor;
	private String pageName;

	private OnClickListener previousButtonListener = new OnClickListener()
	{
		@Override
		public void onClick( View view )
		{

			ULTT.me.get().movePrevious();
		}
	};

	private OnClickListener nextButtonListener = new OnClickListener()
	{
		@Override
		public void onClick( View view )
		{

			ULTT.me.get().moveNext();
		}
	};


	public FlingPage( Context context , int position , String pageName , int color )
	{

		super( context );		
			
		this.pageName = pageName;
		this.pageColor = Color.argb( 255 , 255 , 255 , 255 );
		this.setBackgroundColor( this.pageColor );

		this.setOrientation( LinearLayout.VERTICAL );
		this.setLayoutParams( new LinearLayout.LayoutParams( LAYOUT_MATCH_PARENT , LAYOUT_MATCH_PARENT ) );
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT );
		
		LinearLayout table = new LinearLayout( context );
		table.setOrientation( LinearLayout.VERTICAL );
		LinearLayout row = new LinearLayout( context );		
		row.setOrientation( LinearLayout.HORIZONTAL );			

		this.previousButton = new Button( context );
		this.previousButton.setText( "<<" );
		this.previousButton.setGravity( Gravity.LEFT );
		this.previousButton.setOnClickListener( this.previousButtonListener );		
		row.addView( this.previousButton );
		
		this.pageLabel = new TextView( context );
		this.pageLabel.setText( this.pageName );
		this.pageLabel.setTextSize( 30 );
		this.pageLabel.setGravity( Gravity.LEFT );
		this.pageLabel.setBackgroundColor( this.pageColor );
		row.addView( this.pageLabel );

		this.nextButton = new Button( context );
		this.nextButton.setText( ">>" );
		this.nextButton.setGravity( Gravity.RIGHT );
		this.nextButton.setOnClickListener( this.nextButtonListener );
		row.addView( this.nextButton );
		
		row.setGravity( Gravity.CENTER );
		table.addView( row , layoutParams );
	
		
		this.addView( table );

	}

}
