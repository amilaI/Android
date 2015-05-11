package com.appointmentscheduler.appmain;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;

import com.example.androidcalendarview.R;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 2 (Android)
 * Submission Date : 2015 - 04- 18
 */
public class MainCalendarActivity extends ActionBarActivity {
	 CalendarView calendar;
	 private static int month;
	 private static int day;
	 private static int year;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_calendar);
		initializeCalendar();
		
		//Initialize buttons in home screen
		Button createBtn = (Button) findViewById (R.id.addAppointmentBtn);
		Button deleteBtn = (Button) findViewById (R.id.deleteAppointmentBtn);
		Button viewEditBtn = (Button) findViewById (R.id.viewEditAppointmentBtn);
		Button moveBtn = (Button) findViewById (R.id.moveAppointmentBtn);
		Button searchBtn = (Button) findViewById (R.id.searchAppointmentBtn);
		Button translateBtn = (Button) findViewById (R.id.translateAppointmentBtn);
		
		
		createBtn.setOnClickListener(addAppointment());
		deleteBtn.setOnClickListener(deleteAppointment());
		viewEditBtn.setOnClickListener(viewEditAppointment());
		moveBtn.setOnClickListener(moveAppointment());
		searchBtn.setOnClickListener(searchAppointment());
		translateBtn.setOnClickListener(translateAppointment());
		
	}
	/**
     * Start a new appointment
     * @return onclick listener
     */
    public OnClickListener addAppointment() {
    	
    	OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainCalendarActivity.this, AddAppointmentActivity.class);
				String selectedDate = year +"-"+month+"-"+day;
				intent.putExtra("selectedDate",selectedDate);
	        	startActivity(intent);
			}
		};
    	
		return clickListener;		
	} 
	/**
     * search appointment
     * @return onclick listener
     */
    public OnClickListener searchAppointment() {
    	
    	OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainCalendarActivity.this, SearchAppointmentActivity.class);
			   	startActivity(intent);
			}
		};
    	
		return clickListener;		
	} 
    /**
     * translate appointment
     * @return onclick listener
     */
    public OnClickListener translateAppointment() {
    	
    	OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainCalendarActivity.this, TranslateAppointmentActivity.class);
				String selectedDate = year +"-"+month+"-"+day;
				intent.putExtra("selectedDate",selectedDate);
	        	startActivity(intent);
			}
		};
    	
		return clickListener;		
	} 
    /**
     * view/edit appointment
     * @return onclick listener
     */
    public OnClickListener viewEditAppointment() {
    	
    	OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainCalendarActivity.this, ViewAppointmentActivity.class);
				String selectedDate = year +"-"+month+"-"+day;
				intent.putExtra("selectedDate",selectedDate);
	        	startActivity(intent);
			}
		};
    	
		return clickListener;		
	} 
    
	/**
     * move appointment
     * @return onclick listener
     */
    public OnClickListener moveAppointment() {
    	
    	OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainCalendarActivity.this, MoveAppointmentActivity.class);
				String selectedDate = year +"-"+month+"-"+day;
				intent.putExtra("selectedDate",selectedDate);
	        	startActivity(intent);
			}
		};
    	
		return clickListener;		
	} 
    
	/**
     * Delete appointments
     * @return onclick listener
     */
    public OnClickListener deleteAppointment() {
    	
    	OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainCalendarActivity.this, DeleteAppointmentActivity.class);
				String selectedDate = year +"-"+month+"-"+day;
				intent.putExtra("selectedDate",selectedDate);
	        	startActivity(intent);
			}
		};
    	
		return clickListener;		
	}    
    
	private void initializeCalendar() {
		 // TODO Auto-generated method stub
           calendar = (CalendarView) findViewById(R.id.calendarView1);
	 
	        // sets whether to show the week number.
	        calendar.setShowWeekNumber(false);
	 
	        // sets the first day of week according to Calendar.
	        // here we set Monday as the first day of the Calendar
	        calendar.setFirstDayOfWeek(2);
	 
	        //The background color for the selected week.
	        //calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));
	         
	        //sets the color for the dates of an unfocused month.
	        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.not_selected_days_color));
	     
	        //sets the color for the separator line between weeks.
	        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.selected_day_week_color));
	      	         
	        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
	        calendar.setSelectedDateVerticalBar(R.color.selected_day_color);
	        calendar.setSelectedWeekBackgroundColor(Color.parseColor("#BDC3C7"));
	        
	        Calendar javaCalendar = Calendar.getInstance();
	        javaCalendar.setTimeInMillis(calendar.getDate());
	        year = javaCalendar.get(Calendar.YEAR);
	        month = javaCalendar.get(Calendar.MONTH)+ 1;
	        day = javaCalendar.get(Calendar.DAY_OF_MONTH);
	    	TextView textView = (TextView) findViewById(R.id.selectedDate);
	    	textView.setText("The Selected Date : " +day + "/" + month + "/" + year);
	        
	        //sets the listener to be notified upon selected date change.
	        calendar.setOnDateChangeListener(new OnDateChangeListener() {
	                       //show the selected date as a toast
	            @Override
	            public void onSelectedDayChange(CalendarView view, int mYear, int mMonth, int mDay) {
	            	//Date format example : 01st January 2015 --> 2015-00-01
                //Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
	            	year=mYear;
	            	month=mMonth+1;
	            	day=mDay;
	            	TextView textView = (TextView) findViewById(R.id.selectedDate);
	            	textView.setText("The Selected Date : " +day + "/" + month+ "/" + year);
	            }
	        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_calendar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//Back press functionality
		@Override
		public void onBackPressed() {
			   new AlertDialog.Builder(this)
	           .setMessage("Are you sure you want to exit?")
	           .setCancelable(false)
	           .setNegativeButton("Continue", null)
	           .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
							
				public void onClick(DialogInterface arg0, int arg1) {
					 //Finish activity
					 MainCalendarActivity.this.finish();										
				}
			}).show();
			 
			   
		}


}
