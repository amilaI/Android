package com.example.braintraininggame2012519;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 1
 * Submission Date : 2015 - 03- 16
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 * This Activity is corresponds to select game levels
 */
public class SelectLevelActivity extends Activity  {
	
	public static final int TERM2 = 2;
	public static final int TERM3 = 3;
	public static final int TERM4 = 4;
	public static final int TERM6 = 6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set content views
		setContentView(R.layout.activity_dificulty_levels);
		//Populate buttons
		populateButtons(); 	
	}
	//Populate all buttons
	private void populateButtons() {
		Button noviceLevelBtn = (Button) findViewById((R.id.noviceLevelBtnId)); 
		Button easyLevelBtn = (Button) findViewById((R.id.easyLevelBtnId)); 
		Button mediumLevelBtn = (Button) findViewById((R.id.mediumLevelBtnId)); 
		Button guruLevelBtn = (Button) findViewById((R.id.guruLevelBtnId)); 
		//Set on click listeners 
		noviceLevelBtn.setOnClickListener(startGame(TERM2, TERM2)); 
		easyLevelBtn.setOnClickListener(startGame(TERM2, TERM3)); 
		mediumLevelBtn.setOnClickListener(startGame(TERM2, TERM4)); 
		guruLevelBtn.setOnClickListener(startGame(TERM4, TERM6)); 		
	}
	public OnClickListener startGame(final int min , final int max) {
		//Get bundle
		Bundle bundle=getIntent().getExtras();
		final boolean isHintOn=bundle.getBoolean("isHintOn");
		
		OnClickListener listener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//Start game intent
				Intent newGameIntent = new Intent(SelectLevelActivity.this, NewGameActivity.class );
				newGameIntent.putExtra("min", min);
				newGameIntent.putExtra("max", max);
				newGameIntent.putExtra("isHintOn", isHintOn);
				SelectLevelActivity.this.finish();
				startActivity(newGameIntent);
				
			}
		};		
		return listener;		
	}
	/**
	 * Help menu enabled
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.leveldetailsmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * Response toast for help menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 Toast.makeText(getApplicationContext(), R.string.difficulty_guide, Toast.LENGTH_SHORT).show();
		return super.onOptionsItemSelected(item);
	}

}
