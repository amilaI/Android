package com.appointmentscheduler.appmain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
 * This Activity will handle the deletion of the appointments
 * @author Amila Iddamalgoda
 *
 */
public class DeleteAppointmentActivity extends Activity {
	
	//Initialization of elements
	private TextView dateTextView;
	private Button deleteAllBtn;
	private Button selectAllBtn;
	private SQLiteDatabaseFactory databaseFactory;
	private String currentDate;
	private Cursor databaseCursor;
	private EditText indexTobeRemoved;
	private Button deleteSelectedBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deleteappointment);

		// Get date of the appointment
		dateTextView = (TextView) findViewById(R.id.dateTextViewDelete);
		String selectedDate = getIntent().getStringExtra("selectedDate");
		dateTextView.setText(selectedDate);
		currentDate = dateTextView.getText().toString();
		
		//Get index to be removed
		indexTobeRemoved = (EditText) findViewById(R.id.index);
		
		final TextView appointmentsList = (TextView) findViewById(R.id.appointments);
		final TextView text_label = (TextView) findViewById(R.id.text_label);
		
		//Instantiate button elements
		deleteSelectedBtn = (Button) findViewById(R.id.delete_selected);
		deleteAllBtn = (Button) findViewById(R.id.delete_all);
		selectAllBtn = (Button) findViewById(R.id.select_all);
		
		//On click listener to delete all appointments
		deleteAllBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Access the SQLLite database
				databaseFactory = new SQLiteDatabaseFactory(DeleteAppointmentActivity.this);
				//Delete all the appointments comes under the current date
				databaseFactory.deleteAll(currentDate);
				//Successful Toast
				Toast.makeText(DeleteAppointmentActivity.this,"All Appointments successfully deleted.", Toast.LENGTH_SHORT).show();
				finish();

			}
		});

		//On click listener to select all appointments
		selectAllBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//Access the SQLLite database
				databaseFactory = new SQLiteDatabaseFactory(DeleteAppointmentActivity.this);
				//Get appointments user the selected Date
				databaseCursor = databaseFactory.select(currentDate);
				//Validating database cursor
				if (databaseCursor.getCount() == 0) {
					Toast.makeText(DeleteAppointmentActivity.this,
							"There are no saved appointments.",
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					//Get the corresponding appointment
					databaseCursor.moveToFirst();
					int i = 1;
					StringBuilder builder = new StringBuilder(
							"\n");
					do {

						int index = i;
						String time = databaseCursor.getString(3);
						String title = databaseCursor.getString(2);
						builder.append(index).append(" .)  ");	               
			            builder.append(time).append(" - ");
			            builder.append(title).append("\n");
						i++;
					} while (databaseCursor.moveToNext());

					//Display appointments list
					appointmentsList.setText(builder);
					text_label.setVisibility(0);
					indexTobeRemoved.setVisibility(0);
					deleteSelectedBtn.setVisibility(0);
					
				}

			}
		});
		
		// Delete selected appointment
		deleteSelectedBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//Prompt user
				   new AlertDialog.Builder(DeleteAppointmentActivity.this , AlertDialog.THEME_HOLO_DARK)
		           .setMessage("Would you like to delete this event?")
		           .setCancelable(false)
		           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		            	   try {
		   					int indexId = Integer.parseInt(indexTobeRemoved.getText().toString());
		   					if (indexId > databaseCursor.getCount() || indexId <= 0) {
		   						Toast.makeText(DeleteAppointmentActivity.this,
		   								"Please enter a valid appointment no",
		   								Toast.LENGTH_SHORT).show();
		   					} else {
		   						databaseCursor.moveToFirst();
		   						int j = 1;
		   						while (j != indexId) {
		   							databaseCursor.moveToNext();
		   							j++;
		   						}
		   						//Get corresponding appointment ID
		   						int valueID = databaseCursor.getInt(0);
		   						//Access the SQLLite database
		   						databaseFactory = new SQLiteDatabaseFactory(DeleteAppointmentActivity.this);
		   						//Delete selected appointment row
		   						databaseFactory.deleteRow(valueID);
		   						//Successful Toast
		   						Toast.makeText(DeleteAppointmentActivity.this,"Appointment deleted successfully.",Toast.LENGTH_SHORT).show();						
		   						finish();
		   					}
		   				} catch (NumberFormatException e) {
		   					Toast.makeText(DeleteAppointmentActivity.this,
		   							"Please enter a valid appointment no",
		   							Toast.LENGTH_SHORT).show();
		   				}      	   
		            	  
		               }
					}).setNegativeButton("No", null).show();
				
				}
		});

	}

}
