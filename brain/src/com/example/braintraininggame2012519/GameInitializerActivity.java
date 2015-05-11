package com.example.braintraininggame2012519;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 1
 * Submission Date : 2015 - 03- 16
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * This Activity is corresponds to the initial view of the application (Game home page)
 */
public class GameInitializerActivity extends Activity {
 
    private static boolean isHintOn = false;
    public static final String PREFS_FILE = "BrainTrainerPreferences";
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set game home XML View 
        setContentView(R.layout.activity_game_initializer);
        //Initialize the application and populate elements
        initializeGame();        		
    }
    
    /**
     * Populate Buttons and their onclick listeners
     */
    public void initializeGame() {
    	//Set all button elements in the view
        Button newGameBtn = (Button) findViewById((R.id.newGameBtnId)); 
        Button continueGameBtn = (Button) findViewById((R.id.continueGameBtnId));      
        Button aboutGameBtn = (Button) findViewById((R.id.aboutGameBtnId));
        Button exitBtn = (Button) findViewById((R.id.exitGameBtnId));
        //Set onclick listeners
        continueGameBtn.setOnClickListener(continueGame());
        newGameBtn.setOnClickListener(activateNewGame());       
        exitBtn.setOnClickListener(exitApplication());
        aboutGameBtn.setOnClickListener(aboutApplication());
	}
    
    /**
     * Continue Game Feature
     * @return on click listener
     */
    private OnClickListener continueGame() {
		OnClickListener listener = new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Get Shared preferences
				 SharedPreferences settings = getSharedPreferences(PREFS_FILE, 0);		 
			     String lastExpression = settings.getString("lastExpression", null);
			     
			     if(lastExpression == null){
			    	 //No data saved in phone
			    	 Toast.makeText(getApplicationContext(), "You have no game data saved to continue", Toast.LENGTH_SHORT).show();
			     }else{
			    	 //Get all saved data
				     String answer = settings.getString("answer", null);
				     String countDownView = settings.getString("countDownView", null);
				     String textViewValidation = settings.getString("textViewValidation", null);
				     int minNoOfTerms = settings.getInt("minNoOfTerms", 0);
				     int maxNoOfTerms = settings.getInt("maxNoOfTerms", 0);
				     int loop_count = settings.getInt("loop_count", 0);
				     int count = settings.getInt("count", 0);
				     int points = settings.getInt("points", 0);
				     int correct_answers = settings.getInt("correct_answers", 0);
				     int actualAnswer = settings.getInt("actualAnswer", 0);
				     boolean isHintOn = settings.getBoolean("isHintOn", false);
				     long timer = settings.getLong("timer" ,0); 
				     
	        	    // Starting continue game intent
				 	Intent continueGameIntent = new Intent(GameInitializerActivity.this, NewGameActivity.class );
				 	//Put all shared preference data
				 	continueGameIntent.putExtra("lastExpression", lastExpression);
				 	continueGameIntent.putExtra("minNoOfTerms", minNoOfTerms);
	        	   	continueGameIntent.putExtra("maxNoOfTerms", maxNoOfTerms);
	        	   	continueGameIntent.putExtra("loop_count", loop_count);
	        	   	continueGameIntent.putExtra("count", count);
	        	   	continueGameIntent.putExtra("points", points);
	        	   	continueGameIntent.putExtra("correct_answers", correct_answers);
	        	   	continueGameIntent.putExtra("actualAnswer", actualAnswer);
	        	   	continueGameIntent.putExtra("timer", timer);
	        	   	continueGameIntent.putExtra("answer", answer);
	        	    continueGameIntent.putExtra("countDownView", countDownView);
	        	   	continueGameIntent.putExtra("isHintOn", isHintOn);
	        	   	continueGameIntent.putExtra("textViewValidation", textViewValidation);
	        	   	//Start Continue game activity
					startActivity(continueGameIntent);
			     }
				
			}
		};
		return listener;
	}
    
    /**
     * Phone orientation on change xml view handling
     */
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	//If mode is landscape
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			//Set corresponding xml view
			setContentView(R.layout.activity_game_initializerlandscape);
			initializeGame();
		} else {
			setContentView(R.layout.activity_game_initializer);
			initializeGame();
		}    	
    }
	
	/**
	 * Set hint on/off setting action menu
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_activity_actions, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    /**
     * Set response for selecting options
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	 if(item.isChecked()){
             item.setChecked(false);
    	 	 isHintOn = false;
    	 	 Toast.makeText(getApplicationContext(), "Game Hint is OFF", Toast.LENGTH_SHORT).show();
    	 	 }
         else{
             item.setChecked(true);
    	     isHintOn = true;
    	     Toast.makeText(getApplicationContext(), "Game Hint is ON", Toast.LENGTH_SHORT).show();
         }
    	 return true;
    }
    /**
     * About this game feature
     * @return onclick listener
     */
    private OnClickListener aboutApplication() {
		OnClickListener listener = new OnClickListener() {
			   final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		                switch (which){
		               //Cancel the current popup dialog
			            case DialogInterface.BUTTON_NEUTRAL:
			                dialog.cancel();
			                break;
			            }
		            }
		        };
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				//Show details on a popup window
				AlertDialog alertDialog = new AlertDialog.Builder(GameInitializerActivity.this).create();
				LayoutInflater factory = LayoutInflater.from(GameInitializerActivity.this);
				final View view = factory.inflate(R.layout.activity_about_popupwindow, null);
				alertDialog.setView(view);
				alertDialog.setButton3("OK. I Got this !!", dialogClickListener);
				alertDialog.show();				
				
			}
		};
		return listener;
	}

    /**
     * Exit Application
     * @return onclick listener
     */
	private OnClickListener exitApplication() {
             
        OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
					//Prompt user
				   new AlertDialog.Builder(GameInitializerActivity.this)
		           .setMessage("Are you sure you want to exit?")
		           .setCancelable(false)
		           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		            	   GameInitializerActivity.this.finish();       	   
		            	   System.exit(0);
		               }
					}).setNegativeButton("No", null).show();
			}
		};
		return listener;
	}
    /**
     * On home back press 
     */
	@Override
	public void onBackPressed() {
		exitApplication().onClick(null);
		super.onBackPressed();
	}

	/**
     * Start a new game
     * @return onclick listener
     */
    public OnClickListener activateNewGame() {
    	
    	OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newGameIntent = new Intent(GameInitializerActivity.this, SelectLevelActivity.class );
				//Pass boolean value - if hint is on or off
				newGameIntent.putExtra("isHintOn", isHintOn);
				startActivity(newGameIntent);
			}
		};
    	
		return clickListener;		
	}    
}
