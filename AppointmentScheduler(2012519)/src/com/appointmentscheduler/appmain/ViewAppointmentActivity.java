package com.appointmentscheduler.appmain;

import com.appointmentscheduler.persistence.SQLiteDatabaseFactory;
import com.example.androidcalendarview.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 2 (Android)
 * Submission Date : 2015 - 04- 18
 */
public class ViewAppointmentActivity extends Activity {

	protected TextView dateTextView;
	protected Button edit;
	protected EditText editIndex;
	SQLiteDatabaseFactory databaseFactory;
	String date;
	Cursor databaseCursor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewappointment);
   	 	
		//Get date of the appointment		
		date = getIntent().getStringExtra("selectedDate");		
		//Get date of the appointment
		dateTextView  = (TextView) findViewById(R.id.dateTextViewEdit);	
		dateTextView.setText(date);
		
   	 	edit = (Button)findViewById(R.id.button_selected);
   	    editIndex = (EditText)findViewById(R.id.index_to_edit);   	    
   	    
   	    databaseFactory = new SQLiteDatabaseFactory(ViewAppointmentActivity.this);
		databaseCursor = databaseFactory.select(date);
		if(databaseCursor.getCount()==0){
			Toast.makeText(ViewAppointmentActivity.this, "There are no saved appointments.",Toast.LENGTH_SHORT).show();
			finish();
		}
		
		else{
			  databaseCursor.moveToFirst();
			  int i=1;
			StringBuilder builder = new StringBuilder("\n\n");
			do{
				
	               int index = i;
	               String time = databaseCursor.getString(3);
	               String title = databaseCursor.getString(2);
	               
	               builder.append(index).append(" .)  ");	               
	               builder.append(time).append(" - ");
	               builder.append(title).append("\n");
	               	            
	               i++;
	            }while (databaseCursor.moveToNext());
	            
	            TextView text = (TextView) findViewById(R.id.textview);
	            text.setText(builder);
	            
	            edit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						try {
							int indexId= Integer.parseInt(editIndex.getText().toString());
							if (indexId > databaseCursor.getCount() || indexId <= 0) {
								Toast.makeText(ViewAppointmentActivity.this,
										"Please enter a valid appointment no",
										Toast.LENGTH_SHORT).show();
							}else{
								databaseCursor.moveToFirst();
								int j=1;
								while(j!=indexId){
									databaseCursor.moveToNext();
									j++;
								}
								int id = databaseCursor.getInt(0);
								String title1 = databaseCursor.getString(2);
								String time1 = databaseCursor.getString(3);
								String details1 = databaseCursor.getString(4);
								Intent intent = new Intent(ViewAppointmentActivity.this,EditAppointmentActivity.class);
								intent.putExtra("ID", id);
								intent.putExtra("date", date);
								intent.putExtra("title", title1);
								intent.putExtra("time", time1);
								intent.putExtra("details", details1);
								startActivity(intent);
							}
						} catch (NumberFormatException e) {
							Toast.makeText(ViewAppointmentActivity.this,
		   							"Please enter a valid appointment no",
		   							Toast.LENGTH_SHORT).show();
						}
					}
				});

       }
   	    
	}
	


}
