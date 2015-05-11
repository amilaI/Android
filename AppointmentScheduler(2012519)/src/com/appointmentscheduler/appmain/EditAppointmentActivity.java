package com.appointmentscheduler.appmain;

import java.text.ParseException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
public class EditAppointmentActivity extends Activity {
	private TextView txtdate;
	private EditText title;
	private EditText time;
	private EditText details;
	private Button save;
	private SQLiteDatabaseFactory databaseFactory;
	private int id;
	private String date,title1,time1,details1;
	private final String INVALID_TITLE ="title exist";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addappointment);
        
        TextView title_label = (TextView) findViewById(R.id.title_label);
        title_label.setText("Edit Appointment Event");
   	 	onNewIntent(getIntent());
   	 	
   	 	title = (EditText)findViewById(R.id.title);
   	 	time = (EditText)findViewById(R.id.time);
   	 	details = (EditText)findViewById(R.id.details);
   	 	
   	 	title.setText(title1);
		time.setText(time1);
		details.setText(details1);
		
   	 	save = (Button)findViewById(R.id.save);
   	 	save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String date = txtdate.getText().toString();
				String titleToUpdate = title.getText().toString().toLowerCase();
				String timeToUpdate = time.getText().toString();
				String detailsToUpdate = details.getText().toString();
			if(date.isEmpty() || timeToUpdate.isEmpty() || titleToUpdate.isEmpty()){
				Toast.makeText(EditAppointmentActivity.this, "Empty fields",Toast.LENGTH_SHORT).show();
				
			}else if(userTimeFormatValidation(timeToUpdate)){
				Toast toast=Toast.makeText(EditAppointmentActivity.this, "Please Enter a valid time!!.",Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 60);
				toast.show(); 					
			}	else{
				databaseFactory = new SQLiteDatabaseFactory(EditAppointmentActivity.this);
				String status = databaseFactory.update(id, date,titleToUpdate,timeToUpdate,detailsToUpdate);
				
				if(status.equals(INVALID_TITLE)){
					 AlertDialog.Builder dialog = new AlertDialog.Builder(EditAppointmentActivity.this);
				      dialog.setTitle("Appointment,"+ titleToUpdate +" already exist");
				      dialog.setMessage("Please choose a different event title");
				      dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		              public void onClick(DialogInterface dialog, int which) {
		                        
		            	   title.setText("");
		                   dialog.cancel();
		                    }
		                });
				      dialog.show();
				}else {
					Toast.makeText(EditAppointmentActivity.this, "Appointment updated successfully.",Toast.LENGTH_SHORT).show();
					Intent in = new Intent(EditAppointmentActivity.this,MainCalendarActivity.class);
					startActivity(in);
				}
			}
				}
			
		});
	}
	
	public void onNewIntent(Intent intent) {
		id = intent.getIntExtra("ID",0);
		date = intent.getStringExtra("date");
		txtdate = (TextView) findViewById(R.id.dateTextView);
		txtdate.setText(date);
		title1= intent.getStringExtra("title");
		time1 = intent.getStringExtra("time");
		details1 = intent.getStringExtra("details");

	}
	/**
     * Validating the user input time
     * @param appointmentTime
     * @return
     */
	@SuppressLint("SimpleDateFormat")
	private boolean userTimeFormatValidation(String appointmentTime) {
		
		if(appointmentTime.isEmpty()){
			 return true;
		}
		appointmentTime=appointmentTime.concat(":00");
		java.text.DateFormat simpleDateFormat = new java.text.SimpleDateFormat("kk:mm:00");

	    try {
	    	simpleDateFormat.parse( appointmentTime );
	    	
	    	if(checkNumbers(appointmentTime)){
	    		 return true;
	    	}else{
	    		 return false;
	    	}
	    	
	       
	    } catch ( ParseException exc ) {
	    	return true;
	    }
	    
	}

	private boolean checkNumbers(String appointmentTime) {
		boolean value = false;
		String[] parts = appointmentTime.split(":");
		String part1 = parts[0]; //hours
		String part2 = parts[1]; // minutes
	
		try {
			int i = Integer.parseInt(part1);
			int j = Integer.parseInt(part2);
			
			if(i <0 || i >24 ){
				value =true;
			}else if(j < 0 || j >= 60){
				value =true;
			}
			
		} catch (Exception e) {
			value =true;
		}
		
		return value;
	}

}
