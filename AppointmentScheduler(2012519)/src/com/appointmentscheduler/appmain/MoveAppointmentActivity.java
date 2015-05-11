package com.appointmentscheduler.appmain;

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

import com.appointmentscheduler.persistence.SQLiteDatabaseFactory;
import com.example.androidcalendarview.R;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 2 (Android)
 * Submission Date : 2015 - 04- 18
 */
public class MoveAppointmentActivity extends Activity {
	protected TextView dateTextView;
	protected Button select;
	protected EditText moveIndex;
	SQLiteDatabaseFactory databaseFactory;
	String date;
	Cursor databaseCursor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewappointment);
       
        TextView title_label = (TextView) findViewById(R.id.title_label_view);
        title_label.setText("Move Appointment Event");
        
        TextView textview_label_editView = (TextView) findViewById(R.id.textview_label_editView);
        textview_label_editView.setText("Enter the index of appointment you want to move"); 
        
		// Get date of the appointment
		dateTextView = (TextView) findViewById(R.id.dateTextViewEdit);
		String selectedDate = getIntent().getStringExtra("selectedDate");
		dateTextView.setText(selectedDate);
        
        
   	 	select = (Button)findViewById(R.id.button_selected);
   	    moveIndex = (EditText)findViewById(R.id.index_to_edit);
   	    date = dateTextView.getText().toString();
   	    
   	    databaseFactory = new SQLiteDatabaseFactory(MoveAppointmentActivity.this);
		databaseCursor = databaseFactory.select(date);
		if(databaseCursor.getCount()==0){
			Toast.makeText(MoveAppointmentActivity.this, "There are no saved appointments.",Toast.LENGTH_SHORT).show();
			finish();
		}
		else {
			  databaseCursor.moveToFirst();
			  int i=1;
			StringBuilder builder = new StringBuilder("\n");
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
	            
	            select.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							int indexId= Integer.parseInt(moveIndex.getText().toString());
							
							
							if (indexId > databaseCursor.getCount() || indexId <= 0) {
								Toast.makeText(MoveAppointmentActivity.this,
										"Please enter a valid appointment no",
										Toast.LENGTH_SHORT).show();
							} else {
							
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
							Intent intent = new Intent(MoveAppointmentActivity.this,CalendarViewActivity.class);
							intent.putExtra("ID", id);
							startActivity(intent);
							}
						}catch (NumberFormatException e) {
		   					Toast.makeText(MoveAppointmentActivity.this,
		   							"Please enter a valid appointment no",
		   							Toast.LENGTH_SHORT).show();
		   				} 
					}
				});

       }
   	    
	}
		
}
