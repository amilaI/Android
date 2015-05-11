package com.appointmentscheduler.appmain;

import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appointmentscheduler.persistence.SQLiteDatabaseFactory;
import com.example.androidcalendarview.R;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 2 (Android)
 * Submission Date : 2015 - 04- 18
 */
public class SearchAppointmentActivity extends Activity {
	public Calendar month;
	protected EditText searchWord;
	protected Button search;
	protected TextView result;
	SQLiteDatabaseFactory databaseFactory;	
	String day;
	String date;
	Cursor c;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchappointment);
       
        search = (Button)findViewById(R.id.searchBtn);
   	    searchWord = (EditText)findViewById(R.id.search_word);
   	    result = (TextView) findViewById(R.id.text_search);
   	    
   	    search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String word = searchWord.getText().toString();
				if(word.isEmpty()){
					Toast.makeText(SearchAppointmentActivity.this, "Please enter a search key word",Toast.LENGTH_SHORT).show();
				}else{
				
				databaseFactory = new SQLiteDatabaseFactory(SearchAppointmentActivity.this);
				c = databaseFactory.searchAllApointments();
				if (c != null && c.getCount() >0) {
					
					c.moveToFirst();
					int i=1;
					StringBuilder builder = new StringBuilder("Search Results :\n\n");
					do{
						
			               int index = i;
			               String time = c.getString(3);
			               String title = c.getString(2);
			               String date = c.getString(1);
			               String details = c.getString(4);
			               if(title.matches("(?i).*" + word+ ".*")||details.matches("(?i).*" + word+ ".*")) {
			            	  
			            	   builder.append(index).append(" .)  ");
				               builder.append(date).append("  -  ");
				               builder.append(time).append(" - ");
				               builder.append(title).append("\n");
				               i++;
			               }
			              
			            }while (c.moveToNext());
			            
			                if(builder.toString().equals("Search Results :\n\n"))
			            	result.setText("There is no match found.");
			            else
			            	result.setText(builder);
				}else{
					result.setText("There is no match found.");
				}
			}
			}
		});
   	    
   	 	
	}	

}
