package com.appointmentscheduler.appmain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
public class TranslateEditAppointmentActivity extends Activity {
	private TextView txtdate;
	private EditText title;
	private EditText time;
	private EditText details;
	private Button save;
	private SQLiteDatabaseFactory databaseFactory;
	private int id;
	private String date,title1,time1,details1;
	private final String INVALID_TITLE ="title exists";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addappointment);
        
        TextView title_label = (TextView) findViewById(R.id.title_label);
        title_label.setText("Translate Edit Appointment Event");
   	 	onNewIntent(getIntent());
   	 	
   	 	title = (EditText)findViewById(R.id.title);
   	 	time = (EditText)findViewById(R.id.time);
   	 	details = (EditText)findViewById(R.id.details);
   	 	time.setFocusable(false);
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
				databaseFactory = new SQLiteDatabaseFactory(TranslateEditAppointmentActivity.this);
				String status = databaseFactory.update(id, date,titleToUpdate,timeToUpdate,detailsToUpdate);
				
				if(status.equals(INVALID_TITLE)){
					 AlertDialog.Builder dialog = new AlertDialog.Builder(TranslateEditAppointmentActivity.this);
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
					Toast.makeText(TranslateEditAppointmentActivity.this, "Appointment updated successfully.",Toast.LENGTH_SHORT).show();
					Intent in = new Intent(TranslateEditAppointmentActivity.this,MainCalendarActivity.class);
					startActivity(in);
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

}
