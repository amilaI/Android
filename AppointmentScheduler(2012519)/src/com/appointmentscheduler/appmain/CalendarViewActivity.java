package com.appointmentscheduler.appmain;


import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.appointmentscheduler.persistence.SQLiteDatabaseFactory;
import com.example.androidcalendarview.R;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 2 (Android)
 * Submission Date : 2015 - 04- 18
 */
public class CalendarViewActivity  extends Activity{

	 CalendarView calendar;
	 private static int month;
	 private static int day;
	 private static int year;
	 private SQLiteDatabaseFactory databaseFactory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moveappointment);
		initializeCalendar();
		int index = getIntent().getIntExtra("ID", 0);
		
		//Initialize buttons in home screen
		Button moveBtn = (Button) findViewById (R.id.moveBtn);
		moveBtn.setOnClickListener(moveAppointment(index));
	}
	
	/**
     * move Appointment
	 * @param index 
     * @return onclick listener
     */
    public OnClickListener moveAppointment(final int indexInt) {
    	
    	OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				databaseFactory = new SQLiteDatabaseFactory(CalendarViewActivity.this);
				String selectedDate = year +"-"+month+"-"+day;
				databaseFactory.updateDate(indexInt, selectedDate);
				Toast.makeText(CalendarViewActivity.this, "Appointment moved successfully.",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(CalendarViewActivity.this, MainCalendarActivity.class);
	        	startActivity(intent);
			}
		};
    	
		return clickListener;		
	} 
    
	private void initializeCalendar() {
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
	    	TextView textView = (TextView) findViewById(R.id.selectedDateMove);
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
	            	TextView textView = (TextView) findViewById(R.id.selectedDateMove);
	            	textView.setText("The Selected Date : " +day + "/" + month+ "/" + year);
	            }
	        });
	}
}
