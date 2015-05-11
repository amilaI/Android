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
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.braintraininggame2012519.expression.ArithmeticExpression;
/**
 * This Activity is corresponds to start a new game
 */
public class NewGameActivity extends Activity {
	//game instance variables/class variables
	public static final String PREFS_FILE = "BrainTrainerPreferences";
	private int minNoOfTerms;
	private int maxNoOfTerms;
	private int loop_count;
	private int count;
	private int points;
	private String answer;
	private TextView countDownView;
	private TextView arithmeticExpression;
	private TextView textViewValidation;
	private CountDownTimer countDown;
	private int correct_answers;
	private long timer;
	private int actualAnswer;
	private boolean isHintOn;
	private final static int LOOP_DEAD_COUNT=15;
	private boolean hashEnabled =false;
	private int hintCount=0	;
	
	//On create method where game begins
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		//Set content xml view
		setContentView(R.layout.activity_new_game);
		//Create Elements
		this.arithmeticExpression = (TextView) findViewById(R.id.expressionTextView);	
		this.textViewValidation = (TextView) findViewById(R.id.textViewValidation);	
		this.countDownView = (TextView) findViewById(R.id.countDownTime);
		//Get shared preferences bundle
		Bundle bundle=getIntent().getExtras();
		
		if(bundle.getString("lastExpression") != null){
			//Get continue game saved data			
			continuegame(bundle);
		}else{
			//Starts a new game
			this.loop_count = 0;
			this.count=0;
			this.timer = 0;
			this.correct_answers = 0;		
			this.minNoOfTerms=(Integer) bundle.getInt("min");
			this.maxNoOfTerms=(Integer) bundle.getInt("max");
			this.isHintOn=bundle.getBoolean("isHintOn");
			showArithmeticExpression();
		}
		//Create Button Elements and Set on click listeners
		createButtonElements();		
	}
	//Set on click listeners to all buttons
	private void createButtonElements() {
		Button btn1 = (Button) findViewById(R.id.calBtn1);
		Button btn2 = (Button) findViewById(R.id.calBtn2);
		Button btn3 = (Button) findViewById(R.id.calBtn3);
		Button btn4 = (Button) findViewById(R.id.calBtn4);
		Button btn5 = (Button) findViewById(R.id.calBtn5);
		Button btn6 = (Button) findViewById(R.id.calBtn6);
		Button btn7 = (Button) findViewById(R.id.calBtn7);
		Button btn8 = (Button) findViewById(R.id.calBtn8);
		Button btn9 = (Button) findViewById(R.id.calBtn9);
		Button btn0 = (Button) findViewById(R.id.calBtn0);
		Button btnHash = (Button) findViewById(R.id.calBtnHash);
		Button btnDelete = (Button) findViewById(R.id.calBtnDel);
		Button btnDash = (Button) findViewById(R.id.calBtnMinus);
		//Set listeners
		btn1.setOnClickListener(setNumberBtnClick("1"));
		btn2.setOnClickListener(setNumberBtnClick("2"));
		btn3.setOnClickListener(setNumberBtnClick("3"));
		btn4.setOnClickListener(setNumberBtnClick("4"));
		btn5.setOnClickListener(setNumberBtnClick("5"));
		btn6.setOnClickListener(setNumberBtnClick("6"));
		btn7.setOnClickListener(setNumberBtnClick("7"));
		btn8.setOnClickListener(setNumberBtnClick("8"));
		btn9.setOnClickListener(setNumberBtnClick("9"));
		btn0.setOnClickListener(setNumberBtnClick("0"));
		btnHash.setOnClickListener(validateExpression());
		btnDelete.setOnClickListener(delete());
		btnDash.setOnClickListener(setNumberBtnClick("-"));		
	}

	//get Continue Game data
	private void continuegame(Bundle bundle) {
		this.loop_count = 	bundle.getInt("loop_count");
		this.count=	bundle.getInt("count");
		this.timer =bundle.getLong("timer");
		this.points=bundle.getInt("points");
		this.correct_answers = bundle.getInt("correct_answers");		
		this.minNoOfTerms=bundle.getInt("minNoOfTerms");
		this.maxNoOfTerms=bundle.getInt("maxNoOfTerms");
		this.isHintOn=bundle.getBoolean("isHintOn");
		this.arithmeticExpression.setText(bundle.getString("lastExpression"));
	 	this.textViewValidation.setText(bundle.getString("textViewValidation"));
	 	this.answer = 	bundle.getString("answer");
	   	this.countDownView.setText(bundle.getString("countDownView"));
	    this.actualAnswer=	bundle.getInt("actualAnswer");		
	    countDownTimer();
	}
	//Back press functionality
	@Override
	public void onBackPressed() {
		   new AlertDialog.Builder(this)
           .setMessage("Are you sure you want to exit?")
           .setCancelable(false)
           .setPositiveButton("Save and Exit", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
            	    //Open shared preferences            	   
            	    SharedPreferences settings = getSharedPreferences(PREFS_FILE, 0);
            	    SharedPreferences.Editor editor = settings.edit();
            	    //Push changes to the editor  
            	   	editor.putString("lastExpression", arithmeticExpression.getText().toString());
            	   	editor.putInt("minNoOfTerms", minNoOfTerms);
            	   	editor.putInt("maxNoOfTerms", maxNoOfTerms);
            	   	editor.putInt("loop_count", loop_count);
            	   	editor.putInt("count", count);
            	   	editor.putInt("points", points);
            	   	editor.putInt("correct_answers", correct_answers);
            	   	editor.putInt("actualAnswer", actualAnswer);
            	   	editor.putLong("timer", timer);
            	   	editor.putString("answer", answer);
            	    editor.putString("countDownView", countDownView.getText().toString());
            	   	editor.putBoolean("isHintOn", isHintOn);
            	   	editor.putString("textViewValidation", textViewValidation.getText().toString());            	  
            	    //Commit the edits!
            	    editor.commit();
            	    //Go to game home
                    NewGameActivity.this.finish();
                    Intent gameHomeIntent = new Intent(NewGameActivity.this, GameInitializerActivity.class );
   				    startActivity(gameHomeIntent);
               }
           })
           .setNegativeButton("Continue", null)
           .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
						
			public void onClick(DialogInterface arg0, int arg1) {
				 //Clear all editor commits
				 SharedPreferences settings = getSharedPreferences(PREFS_FILE, 0);
				 settings.edit().clear().commit();
				 //Finish activity
				 NewGameActivity.this.finish();
				 Intent gameHomeIntent = new Intent(NewGameActivity.this, GameInitializerActivity.class );
				 //Go to game home intent
				 startActivity(gameHomeIntent);
				
			}
		}).show();
		 
		   
	}
	//Set Arithmetic Expression
	private String setExpression() {
		//Generates a new expression
		ArithmeticExpression expression = new ArithmeticExpression(minNoOfTerms ,maxNoOfTerms);
		String stringExpression=expression.generateExpresssion();
		actualAnswer=0;
		//Get actual answer
		actualAnswer = expression.getFinalAnswer();
		return stringExpression;
	}
	//Delete expression answer
	private OnClickListener delete() {
		OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int length = answer.length();
				if (length != 0) {
					//Delete character by character
					answer = answer.substring(0, length - 1);
					String expre = arithmeticExpression.getText().toString();
					arithmeticExpression.setText(expre.substring(0,
							expre.length() - 1));
				}
				
			}
		};
		return clickListener;
	
	}
	//Set corresponding numbers
	private OnClickListener setNumberBtnClick(final String digit){
		OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Append number to the answer
				int length = arithmeticExpression.getText().length();
				char character = arithmeticExpression.getText().charAt(length - 1);
				if (character == '?') {
					String text = arithmeticExpression.getText().toString();
					text = text.substring(0, length - 1);
					answer = digit;
					arithmeticExpression.setText(text + digit);
				} else {
					answer += digit;
					arithmeticExpression.setText(arithmeticExpression.getText()
							+ digit);
				}

			}
		};
		//return listener
		return clickListener;
	}
	
	//Show Arithmetic Expression and the timer
	public void showArithmeticExpression() {
		textViewValidation.setText("");
		loop_count++;
		if (loop_count <= 10) {			
			answer = "?";
			//Set the generated expression in view
			arithmeticExpression.setText(loop_count +".) Guess: " + setExpression() + " = " + answer);
			//Start a new timer
			countDownTimer();
		} else {
			if(loop_count != LOOP_DEAD_COUNT){
				//Go to results view
				 gotoResultsView();
			}
		}
	}
	//Count down Timer functionality
	public void countDownTimer() {
		
		countDown = new CountDownTimer(11000, 1000) {
			
			public void onTick(long millisUntilFinished) {
				//Set count down timer for 10 seconds 
				countDownView.setText("Time remaining: " + millisUntilFinished
						/ 1000 + " Secs");
				timer = millisUntilFinished / 1000;
			}
			//Timer onFinish() method
			public void onFinish() {
							
				if (loop_count <= 10) {
					//Validate
					validateExpression().onClick(null);		
					//Increase count
					count++;
					//Show a new expression
					showArithmeticExpression();
				} else {
					if(loop_count != LOOP_DEAD_COUNT){
					 //Go to results view
					 gotoResultsView();
					}
				}			
				
			}
		}.start();
	}
	//Get results
	public void gotoResultsView() {
		//Finish this activity
		finish();
		//Clear saved data
		SharedPreferences settings = getSharedPreferences(PREFS_FILE, 0);
		settings.edit().clear().commit();
		//Start new activity
		Intent i = new Intent(NewGameActivity.this, FinalResultActivity.class);
		i.putExtra("Points", points);
		i.putExtra("Correct", correct_answers);
		i.putExtra("Incorrect", (10-correct_answers));
		startActivity(i);
	}
	//Validate the expression answer
	public OnClickListener validateExpression() {
		OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Question no: count (Should be 10 questions)
				if (loop_count <= 10) {
					
					// If hint is off then the user will get only one attempt per question
					if (!isHintOn) {
						// Stopping the current count down
						countDown.cancel();
						//Validate current expression scenario
						if ((count % 2) == 0) {
							//If user has given no inputs
							if (answer.equals("?") || answer.equals("-") || answer.length() == 0 || answer.charAt(answer.length() - 1) == '-') {
								
								textViewValidation.setTextColor(0xFFFF0000);
								textViewValidation.setText("WRONG!");
								count++;
								
							} else {

								int user_answer = 0;
								boolean isNumberNotValid=false;
								try {
									user_answer = Integer.parseInt(answer);
								} catch (NumberFormatException e) {
									//User has given an invalid input
									isNumberNotValid = true;									
								}
								//If user enters a invalid input
								if(isNumberNotValid){
									textViewValidation.setTextColor(0xFFFF0000);
									textViewValidation.setText("WRONG!");									
									count++;
									Toast.makeText(getApplicationContext(), "Invalid Number !!", Toast.LENGTH_SHORT).show();
								}
								//If users's answer is correct
								else if (user_answer == actualAnswer) {
									textViewValidation.setTextColor(Color.parseColor("#01DF01"));
									textViewValidation.setText("CORRECT!");
									correct_answers++;
									//Calculate points (If user answer it in 10s user will be offered 100 points)
									if (timer == 10) {
										points += 100;
									} else {
										points += 100 / (10 - timer);
									}
									count++;
								} else {
									
									textViewValidation.setTextColor(0xFFFF0000);
									textViewValidation.setText("WRONG!");									
									count++;
								}
							}
						} else {
							//Resetting the count down
							countDown = null;
							//Show a new Expression
							showArithmeticExpression();
							count++;
						}
					} else {
						
						// If hint is on then user will given 4 attempts (begins with count=0)						
						if(hashEnabled){
							
							countDown.cancel();
							countDown = null;
							showArithmeticExpression();
							hintCount = 0;
							hashEnabled =false;
							
						}else{
							//When hint is on user will be given 4 attempts
							if (hintCount < 4) {
								if(hintCount==2){
									hashEnabled = true;
								}

								textViewValidation.setText("");
								
								if (answer == "?" || answer == "-" || answer.length() == 0
										|| answer.charAt(answer.length() - 1) == '-') {
									textViewValidation.setTextColor(0xFFFF0000);
									textViewValidation.setText("WRONG!");
									hintCount++;
								} else {

									int user_answer = 0;
									boolean isNumberNotValid=false;
									try {
										user_answer = Integer.parseInt(answer);
									} catch (NumberFormatException e) {
										//User has given an invalid input
										isNumberNotValid = true;									
									}
									//If user enters a invalid input
									if(isNumberNotValid){
										textViewValidation.setTextColor(0xFFFF0000);
										textViewValidation.setText("WRONG!");									
										count++;
										Toast.makeText(getApplicationContext(), "Invalid Number !!", Toast.LENGTH_SHORT).show();
									}else if (user_answer == actualAnswer) {
										//When answer is correct stop the timer show CORRECT and calculate points
										countDown.cancel();
										textViewValidation.setTextColor(Color.parseColor("#01DF01"));
										textViewValidation.setText("CORRECT!");
										correct_answers++;
											if (timer == 10) {
												//User will be given 100 points
												points += 100;
											} else {
												//User will be given following points
												points += 100 / (10 - timer);
											}									
										hashEnabled = true;
										
									} else {
										//Hint Functionality
										if (user_answer > actualAnswer) {
											textViewValidation.setTextColor(0xFFFF0000);
											textViewValidation.setText("WRONG! - Hint:LESSER");
										} else if (user_answer < actualAnswer) {
											textViewValidation.setTextColor(0xFFFF0000);
											textViewValidation.setText("WRONG!- Hint:GREATER");
										}

										hintCount++;										
										String currentExpression = (String) arithmeticExpression.getText();
										String[] rewritedExpression = currentExpression.split("=");
										arithmeticExpression.setText(rewritedExpression[0]	+ "= ");
										answer = "";
									}
								}

							} else {
								countDown.cancel();
								countDown = null;
								showArithmeticExpression();
								hintCount = 0;
								hashEnabled = false;
							}

						}
					}

				} else {
					if (loop_count != LOOP_DEAD_COUNT) {
						//Go to results view
						gotoResultsView();
					}
				}
			}

		};
		return listener;

	}

}
