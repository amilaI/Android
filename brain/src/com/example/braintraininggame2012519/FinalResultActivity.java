package com.example.braintraininggame2012519;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 1
 * Submission Date : 2015 - 03- 16
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * This Activity is corresponds to the final result view
 */
public class FinalResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);	    
		Bundle argument = getIntent().getExtras();
		populateView(argument);
		populateReturnButton();	      
	}
	//Populate and set onclick listener to back button
	private void populateReturnButton() {
		  final Button btnMainMenu = (Button) findViewById(R.id.buttonBackToMain);
	        btnMainMenu.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	FinalResultActivity.this.finish();
	            	Intent i = new Intent(FinalResultActivity.this,GameInitializerActivity.class);
	                startActivity(i);
	                
	            }
	        });		
	}
	//Populate view with elements
	private void populateView(Bundle argument) {
	 		int points=0;
			int correct_answers = 0;
			int incorrect_answers=0;
			TextView textViewPoints;
			TextView textViewCorrect;
			TextView textViewIncorrect;
		        if(argument != null)
		        {
		        	points = argument.getInt("Points"); 
		        	correct_answers = argument.getInt("Correct");
		        	incorrect_answers = argument.getInt("Incorrect");
		        }
		              
		    textViewCorrect = (TextView) findViewById(R.id.textViewCorrect);
		    textViewCorrect.setText("Correct Answers: " + correct_answers);
		        
		    textViewIncorrect = (TextView) findViewById(R.id.textViewIncorrect);
		    textViewIncorrect.setText("Incorrect Answers: " + incorrect_answers);
		        
		    textViewPoints = (TextView) findViewById(R.id.textViewYourPoints);
		    textViewPoints.setText("Your Points: " + points);		
	}
}
