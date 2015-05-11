package com.appointmentscheduler.appmain;

import java.text.ParseException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class AddAppointmentActivity extends Activity {
	
	private TextView dateTextView;
	private EditText title;
	private EditText time;
	private EditText details;
	private Button save;
	private SQLiteDatabaseFactory databaseFactory;
	private final String INVALID_TITLE ="title exists";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//set content view
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addappointment);
		
		//Get date of the appointment
		dateTextView  = (TextView) findViewById(R.id.dateTextView);
		String date = getIntent().getStringExtra("selectedDate");
		dateTextView.setText(date);
		
		//Get appointment title , time and details
		title = (EditText)findViewById(R.id.title);
   	 	time = (EditText)findViewById(R.id.time);
   	 	details = (EditText)findViewById(R.id.details);
   	 	
   	 	//Set onclick listener to save button
   	    save = (Button)findViewById(R.id.save);
   	    save.setOnClickListener(saveAppointment());
	}
	
	/**
     * save an appointment
     * @return onclick listener
     */
    public OnClickListener saveAppointment() {
    	
    	OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Getting data to be saved
				String appointmentDate = dateTextView.getText().toString();
				String appointmentTitle = title.getText().toString().toLowerCase();
				String appointmentTime = time.getText().toString();
				String appointmentDetails = details.getText().toString();
				
				//Validating user inout fields
				if(appointmentTitle.isEmpty()){					
					Toast toast=Toast.makeText(AddAppointmentActivity.this, "Please Enter a title !!",Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 60);
					toast.show();  
				}else if(userTimeFormatValidation(appointmentTime)){
					Toast toast=Toast.makeText(AddAppointmentActivity.this, "Please Enter a valid time!!.",Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 60);
					toast.show(); 					
				}else{
					//Accessing SQLLite database
					databaseFactory = new SQLiteDatabaseFactory(AddAppointmentActivity.this);
					
					//Insert Query for data
					String response = databaseFactory.insert(appointmentDate,appointmentTitle,appointmentTime,appointmentDetails);
					
					//If response comes with "Invalid title " then the title exists in the db
					if (response.equals(INVALID_TITLE)) {
						
						//Pop up window to display the error
						AlertDialog.Builder dialog = new AlertDialog.Builder(AddAppointmentActivity.this , AlertDialog.THEME_HOLO_DARK);
						dialog.setTitle("Appointment " + appointmentTitle+ " already exists");
						dialog.setMessage("Please choose a different event title");
						dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,	int which) {
										title.setText("");
										dialog.cancel();
									}
								});
						dialog.show();
					}
					
					else {
						Toast.makeText(AddAppointmentActivity.this, "Appointment successfully saved.",Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			}

		
		};
    	
		return clickListener;		
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
