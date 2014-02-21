package com.mycompany.myapp;

import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;

import java.util.Set;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.*;
import java.util.*;

public class Guess extends Activity implements OnClickListener {
	/*
	
	String descr;
	String glass;
	String ice;
	
	String[] toping;
	String[] method;
	String[] ingr;
	String[] cl;
	*/
	String name;
	
	Set<String> ingrs;
	Set<String> topings;
	Set<String> glasss;
	Set<String> ices;
	Set<String> methods;
	
	SharedPreferences shp;
	SharedPreferences.Editor ed;
	
	AutoCompleteTextView  glassEt;
	AutoCompleteTextView  iceEt;
	AutoCompleteTextView  toppingEt;
	Button done;
	ImageButton addToping;
	LinearLayout lltopings;
	ArrayList<AutoCompleteTextView> toppingEds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guess);
		
		shp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = shp.edit();
        
		toppingEds = new ArrayList<AutoCompleteTextView>();
		
      //Get sets
        topings= shp.getStringSet("toping", null);
        methods=shp.getStringSet("method", null);
        glasss=shp.getStringSet("glass", null);
        ices=shp.getStringSet("ice", null);
        ingrs=shp.getStringSet("ingr", null);
        
        //SetUp View
        glassEt = (AutoCompleteTextView) findViewById(R.id.guessGlassEt);
        iceEt = (AutoCompleteTextView) findViewById(R.id.guessIceEt);
		toppingEt =(AutoCompleteTextView) findViewById(R.id.guessToppingEt);
		lltopings = (LinearLayout) findViewById(R.id.guessToppingsLay);
		addToping = (ImageButton) findViewById(R.id.guessAddToping);
		addToping.setOnClickListener(this);
		done = (Button) findViewById(R.id.guessDone);
        done.setOnClickListener(this);
        
        ArrayAdapter<String> glassAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, glasss.toArray(new String[glasss.size()]));
        ArrayAdapter<String> iceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ices.toArray(new String[ices.size()]));
        glassEt.setAdapter(glassAdapter);
        iceEt.setAdapter(iceAdapter);
     
		//Get coctail data
		Intent i = getIntent();
		
		name = i.getStringExtra("name");
		
		/*descr = i.getStringExtra("descr");
		glass = i.getStringExtra("glass");
		ice = i.getStringExtra("ice");
		
		toping = i.getStringArrayExtra("toping");
		method = i.getStringArrayExtra("method");
		ingr =  i.getStringArrayExtra("ingr");
		cl =   i.getStringArrayExtra("cl");
		*/
		setTitle(name);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guess, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.guessDone:
				Intent iResult = new Intent();

				iResult.putExtra("ice", iceEt.getText().toString());
				iResult.putExtra("glass", glassEt.getText().toString());
				//put toppings
				ArrayList<String> toppingsToSend = new ArrayList<String>();
				for(AutoCompleteTextView tv: toppingEds){
					toppingsToSend.add(tv.getText().toString());
				}
				iResult.putExtra("toping",toppingsToSend);

				setResult(RESULT_OK, iResult);
				finish();
			break;
			
			case R.id.guessAddToping:
				AutoCompleteTextView newToppingsEt = new AutoCompleteTextView(this);
				ArrayAdapter<String> toppingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topings.toArray(new String[topings.size()]));
				newToppingsEt.setAdapter(toppingAdapter);
				LinearLayout newTopingsll = new LinearLayout(this);
				
				toppingEds.add(newToppingsEt);
				Button newTopingsLlDel = new Button(this);
				newTopingsLlDel.setText("del");
				newTopingsLlDel.setBackgroundResource(R.color.colordel);
				newTopingsLlDel.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v)
						{
							LinearLayout l = (LinearLayout) v.getParent();
							l.setVisibility(View.GONE);
						}
						
					
				});
				
				newTopingsll.addView(newToppingsEt, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
				newTopingsll.addView(newTopingsLlDel, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,3));
				lltopings.addView(newTopingsll);
			break;
		}
	
		
	}

}
