package com.appointmentscheduler.appmain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appointmentscheduler.persistence.SQLiteDatabaseFactory;
import com.example.androidcalendarview.R;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 2 (Android)
 * Submission Date : 2015 - 04- 18
 */
public class TranslateAppointmentActivity extends Activity {

	protected TextView dateTextView;
	protected Button translate;
	protected EditText editIndex;
	SQLiteDatabaseFactory databaseFactory;
	String currentDate;
	Cursor databaseCursor;
	private Spinner fromSpinner;
	private Spinner toSpinner;
	private String fromLang;
	private String toLang;
	private OnItemSelectedListener itemListener;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
    
     	currentDate = getIntent().getStringExtra("selectedDate").toString();  
   	    
   	    databaseFactory = new SQLiteDatabaseFactory(TranslateAppointmentActivity.this);
		databaseCursor = databaseFactory.select(currentDate);
		if(databaseCursor.getCount() ==0){
			 Toast.makeText(TranslateAppointmentActivity.this,
						"No appointments to translate",
						Toast.LENGTH_SHORT).show();
			 finish();
		}else{
	    setContentView(R.layout.translateappointment);
	    // Get date of the appointment
     	dateTextView = (TextView) findViewById(R.id.dateTextTranslate);
     	dateTextView.setText(currentDate);
    	translate = (Button)findViewById(R.id.button_translate);
   	    editIndex = (EditText)findViewById(R.id.index_to_translate);
   	    fromSpinner = (Spinner) findViewById(R.id.from_language);
		toSpinner = (Spinner) findViewById(R.id.to_language);
   	 	StrictMode.enableDefaults();
   	 	setAdapters();
		if (databaseCursor != null && databaseCursor.getCount()>0) {
			  databaseCursor.moveToFirst();
			  int i=1;
			StringBuilder builder = new StringBuilder("\n");
			do{
				
	               int index = i;
	               String time = databaseCursor.getString(3);
	               String title = databaseCursor.getString(2);
	               builder.append(index).append(" .)  ");	               
	               builder.append(time).append(" - ");
	               builder.append(title).append("\n");
	               i++;
	            }while (databaseCursor.moveToNext());
	            
	            TextView text = (TextView) findViewById(R.id.textviewAppointmetsTransalte);
	            text.setText(builder);
	            
	            setListeners();
	            
	            translate.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						try {
							int indexId= Integer.parseInt(editIndex.getText().toString());
							if (indexId > databaseCursor.getCount() || indexId <= 0) {
								Toast.makeText(TranslateAppointmentActivity.this,
										"Please enter a valid appointment no",
										Toast.LENGTH_SHORT).show();
							}else{
							databaseCursor.moveToFirst();
							int j=1;
							while(j!=indexId){
								databaseCursor.moveToNext();
								j++;
							}
							int id = databaseCursor.getInt(0);
							String title1 = databaseCursor.getString(2);
							String time1 = databaseCursor.getString(3);
							String details1 = databaseCursor.getString(4);
							String newDetails = "";
							String newTitle = doTranslate(title1.trim(), fromLang, toLang);
							if(!details1.isEmpty()){
								newDetails = doTranslate(details1.trim(), fromLang, toLang);
							}
							if(newTitle.equals("null") || newTitle.equals("Translation error")){
								Toast.makeText(TranslateAppointmentActivity.this,
										"Cannot do the translation. Unrecognized language pair",
										Toast.LENGTH_SHORT).show();
							}else{
																					
							Intent intent = new Intent(TranslateAppointmentActivity.this,TranslateEditAppointmentActivity.class);
							intent.putExtra("ID", id);
							intent.putExtra("date", currentDate);
							intent.putExtra("title", newTitle);
							intent.putExtra("time", time1);
							intent.putExtra("details", newDetails);
							startActivity(intent);
							}
							}
						} catch (NumberFormatException e) {
							Toast.makeText(TranslateAppointmentActivity.this,
									"Please enter a valid appointment no",
									Toast.LENGTH_SHORT).show();
						}
					}

				
				});

       }
	}
   	    
	}
	private String doTranslate(String original, String fromLang,
			String toLang) {
		String result = "Translation error";
		
		try {
			String q = URLEncoder.encode(original, "UTF-8");
			URL url = new URL("http://api.apertium.org/json/translate" + "?q="
					+ q + "&langpair=" + fromLang + "%7C" + toLang);
			
			URL obj = url;
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
			// optional default is GET
			con.setRequestMethod("GET");
 
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
 
			//print result
			result = response.toString();
			// Parse to get translated text

			JSONObject jsonObject = new JSONObject(result);
			result = jsonObject.getJSONObject("responseData").getString("translatedText").replace("&#39;", "'")
								.replace("&amp;", "&").replace("&#32", " ");
		} catch (MalformedURLException e) {
			Toast.makeText(TranslateAppointmentActivity.this,"Check Internet Connection ",	Toast.LENGTH_SHORT).show();
		} catch (ProtocolException e) {
			Toast.makeText(TranslateAppointmentActivity.this,"Check Internet Connection ",	Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(TranslateAppointmentActivity.this,"Check Internet Connection ",	Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			Toast.makeText(TranslateAppointmentActivity.this,"Bad Response from apertium ",	Toast.LENGTH_SHORT).show();
		}
		return result;
	
	}	
	private void setAdapters() {
		
		// Resource which includes a spinner list 
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fromSpinner.setAdapter(adapter);
		toSpinner.setAdapter(adapter);
		
		// Automatically selects two spinner items
		
		fromSpinner.setSelection(8); 
		toSpinner.setSelection(25); 
		}
	
	private void setListeners() {
		itemListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView parent,View v, int position,long id) {
		fromLang = getLang(fromSpinner);
		toLang = getLang(toSpinner);
		
		}
		
		public void onNothingSelected(AdapterView parent)
		{
		
		}
		
		};
		fromSpinner.setOnItemSelectedListener(itemListener);
		toSpinner.setOnItemSelectedListener(itemListener);
	}
	
		
		private String getLang(Spinner spinner) {
			String result = spinner.getSelectedItem().toString();
			int lparen = result.indexOf('(');
			int rparen = result.indexOf(')');
			result = result.substring(lparen + 1, rparen);
			return result;
			}


}
