

package ie.ul.TimeTable;

import ie.ul.WebInteraction.WebHelper;
import ie.ul.slot.EmptySlot;
import ie.ul.slot.LabratorySlot;
import ie.ul.slot.LectureSlot;
import ie.ul.slot.TutorialSlot;
import ie.ul.slot.iSlot;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TimeTable
{


	private String studentID;
	private iSlot[][] schedule;
	private String error;
	private boolean errorOccured;


	public TimeTable( String studentID )
	{

		this.schedule = new iSlot[ 5 ][ 9 ];
		this.studentID = studentID;
		this.errorOccured = false;

		WebHelper helper = new WebHelper();
		String html = helper.getStudentTimetable( this.studentID );

		if( html.compareTo( "Error" ) == 0 )
		{
			this.error = helper.getLastError();
			this.errorOccured = true;
			return;
		}

		this.createTimeTable( html );
	}


	private void createTimeTable( String html )
	{

		Pattern Lecture = Pattern.compile( "LEC" );
		Pattern Tutorial = Pattern.compile( "TUT" );
		Pattern Labratory = Pattern.compile( "LAB" );

		Pattern Module = Pattern.compile( "[A-Z]{2,3}[0-9]{3,4}" , Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
		Pattern Room = Pattern.compile( "[a-zA-Z]{1,2}[0-9]{2,3}" , Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
		Pattern Times = Pattern.compile( "[0-9][0-9]:[0-9][0-9]" , Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );

		Pattern table = Pattern.compile( "<table[^>]*>(.*?)</table>" , Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
		Matcher tableMatches = table.matcher( html );

		int columCount = 0;
		String result = "";

		if( tableMatches.find() )
		{

			Pattern dayColumns = Pattern.compile( "<td[^>]*>(.*?)</td>" , Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
			Matcher dayMatches = dayColumns.matcher( tableMatches.group() );

			while ( dayMatches.find() )
			{
				if( columCount > 5 && columCount < 11 )
				{
					Pattern slots = Pattern.compile( "<p[^>]*>(.*?)</p>" , Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
					Matcher slotMatcher = slots.matcher( dayMatches.group() );

					while ( slotMatcher.find() )
					{
						String module = "";
						String room = "";
						String starttime = "";
						String endtime = "";

						Matcher mods = Module.matcher( slotMatcher.group() );

						if( mods.find() )
						{
							module = mods.group();
						}
						else
						{
							module = "";
						}

						Matcher rooms = Room.matcher( slotMatcher.group() );

						if( rooms.find() )
						{
							rooms.find();
							room = mods.group();
						}
						else
						{
							room = "";
						}

						Matcher times = Times.matcher( slotMatcher.group() );

						if( times.find() )
						{
							starttime = times.group();
							times.find();
							endtime = times.group();
						}
						else
						{
							starttime = "0";
							endtime = "0";
						}

						if( starttime.contains( ":" ) )
						{
							String[] parts = starttime.split( ":" );
							parts[ 0 ] = ( parts[ 0 ].startsWith( "0" ) ) ? parts[ 0 ].substring( 1 ) : parts[ 0 ];
							starttime = parts[ 0 ];
						}

						if( endtime.contains( ":" ) )
						{
							String[] parts = endtime.split( ":" );
							parts[ 0 ] = ( parts[ 0 ].startsWith( "0" ) ) ? parts[ 0 ].substring( 1 ) : parts[ 0 ];
							endtime = parts[ 0 ];
						}

						int startIntTime = Integer.parseInt( starttime );
						int endIntTime = Integer.parseInt( endtime );

						iSlot slotEntry1;
						iSlot slotEntry2;

						boolean lecDouble = false;
						boolean tutDouble = false;
						boolean labDouble = false;

						if( Lecture.matcher( slotMatcher.group() ).matches() )
						{
							slotEntry1 = new LectureSlot( room , module , startIntTime );
							lecDouble = ( endIntTime - startIntTime > 1 ) ? true : false;
						}
						else if( Tutorial.matcher( slotMatcher.group() ).matches() )
						{
							slotEntry1 = new TutorialSlot( room , module , startIntTime );
							tutDouble = ( endIntTime - startIntTime > 1 ) ? true : false;
						}
						else if( Labratory.matcher( slotMatcher.group() ).matches() )
						{
							slotEntry1 = new LabratorySlot( room , module , startIntTime );
							labDouble = ( endIntTime - startIntTime > 1 ) ? true : false;
						}
						else
						{
							slotEntry1 = new EmptySlot();
						}

						this.schedule[ columCount - 6 ][ startIntTime - 9 ] = slotEntry1;

						if( lecDouble )
						{
							slotEntry2 = new LectureSlot( room , module , startIntTime + 1 );
							this.schedule[ columCount - 6 ][ startIntTime - 8 ] = slotEntry2;
						}

						if( tutDouble )
						{
							slotEntry2 = new TutorialSlot( room , module , startIntTime + 1 );
							this.schedule[ columCount - 6 ][ startIntTime - 8 ] = slotEntry2;
						}

						if( labDouble )
						{
							slotEntry2 = new LabratorySlot( room , module , startIntTime + 1 );
							this.schedule[ columCount - 6 ][ startIntTime - 8 ] = slotEntry2;
						}
					}
				}
				columCount++;
			}
		}
	}


	public iSlot[][] getTimeTable()
	{

		if( this.errorOccured == true )
		{
			return null;
		}

		return this.schedule;
	}


	public String getLastError()
	{

		return this.error;
	}

}
