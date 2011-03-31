package ie.ul.WebInteraction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class WebHelper 
{
	private String baseUrl;
	private String pageStudent;
	private String lastError;
	
	public WebHelper()
	{
		this.baseUrl = "http://www.timetable.ul.ie/";
		this.pageStudent = "tt2.asp";		
	}
	
	
	public String getStudentTimetable( String ID )
	{		
	    String resp = "";
	    
	    try 
	    {
	    	HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost( this.baseUrl + this.pageStudent );
		    HttpResponse response;

		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        nameValuePairs.add( new BasicNameValuePair( "T1" , ID ) );
	        httppost.setEntity( new UrlEncodedFormEntity( nameValuePairs ) );
	        
	        response = httpclient.execute( httppost );
	        
	        InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
            StringBuilder str = new StringBuilder();
            
            String line = null;
            
            while( ( line = reader.readLine() ) != null )
            {
                str.append( line );
            }
            in.close();
            
            resp = str.toString();
	        
	    } 
	    catch ( Exception p ) 
	    {
	    	this.lastError = p.getMessage();
	        resp = "Error";
	    } 
	    
		return resp;		
	}
	
	
	public String getLastError()
	{
		return this.lastError;
	}
	
}
