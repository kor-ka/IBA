package com.mycompany.myapp;

import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;

import java.util.Set;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
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
	Set<String> cls;
	
	SharedPreferences shp;
	SharedPreferences.Editor ed;
	
	AutoCompleteTextView  glassEt;
	AutoCompleteTextView  iceEt;
	AutoCompleteTextView  toppingEt;
	AutoCompleteTextView  methodEt;
	AutoCompleteTextView  ingrEt;
	AutoCompleteTextView  clEt;
	
	Button done;
	
	ImageButton addToping;
	ImageButton addMethod;
	ImageButton addIngr;
	
	LinearLayout lltopings;
	LinearLayout llmethod;
	LinearLayout llingr;
	
	//llparams fr new fields 
	LinearLayout.LayoutParams llpEt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1);
	LinearLayout.LayoutParams llpBtn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,3);
	
	//Lists to add all new strings to intent
	ArrayList<AutoCompleteTextView> toppingEds;
	ArrayList<AutoCompleteTextView> methodEds;
	ArrayList<AutoCompleteTextView> ingrEds;
	ArrayList<AutoCompleteTextView> clEds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_guess);
		//fIX KEYBOARD
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		shp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = shp.edit();
        
		toppingEds = new ArrayList<AutoCompleteTextView>();
		methodEds = new ArrayList<AutoCompleteTextView>();
		ingrEds = new ArrayList<AutoCompleteTextView>();
		clEds = new ArrayList<AutoCompleteTextView>();
		
		//Set margins 4 new et & btn
		int margDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                (float) 5, getResources().getDisplayMetrics());
		llpEt.setMargins(margDp, margDp, margDp, margDp);
		llpBtn.setMargins(margDp, margDp, margDp, margDp);
		
      //Get sets
        topings= shp.getStringSet("toping", null);
        methods=shp.getStringSet("method", null);
        glasss=shp.getStringSet("glass", null);
        ices=shp.getStringSet("ice", null);
        ingrs=shp.getStringSet("ingr", null);
        cls=shp.getStringSet("cl", null);
        
        //SetUp View
        glassEt = (AutoCompleteTextView) findViewById(R.id.guessGlassEt);
        iceEt = (AutoCompleteTextView) findViewById(R.id.guessIceEt);
		toppingEt =(AutoCompleteTextView) findViewById(R.id.guessToppingEt);
		methodEt =(AutoCompleteTextView) findViewById(R.id.guessMethodEt);
		ingrEt =(AutoCompleteTextView) findViewById(R.id.guessIngrEt);
		clEt =(AutoCompleteTextView) findViewById(R.id.guessClEt);
		
		lltopings = (LinearLayout) findViewById(R.id.guessToppingsLay);
		llmethod = (LinearLayout) findViewById(R.id.guessMethodLay);
		llingr = (LinearLayout) findViewById(R.id.guessIngrLay);
		
		
		addToping = (ImageButton) findViewById(R.id.guessAddToping);
		addToping.setOnClickListener(this);
		addMethod = (ImageButton) findViewById(R.id.guessAddMethod);
		addMethod.setOnClickListener(this);
		addIngr = (ImageButton) findViewById(R.id.guessAddIngr);
		addIngr.setOnClickListener(this);
		
		done = (Button) findViewById(R.id.guessDone);
        done.setOnClickListener(this);
        //Set up AutoComplete
        ArrayAdapter<String> glassAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, glasss.toArray(new String[glasss.size()]));
        ArrayAdapter<String> iceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ices.toArray(new String[ices.size()]));
        ArrayAdapter<String> toppingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topings.toArray(new String[topings.size()]));
        ArrayAdapter<String> methodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, methods.toArray(new String[methods.size()]));
        ArrayAdapter<String> ingrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingrs.toArray(new String[ingrs.size()]));
        ArrayAdapter<String> clAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cls.toArray(new String[cls.size()]));
        
        glassEt.setAdapter(glassAdapter);
        iceEt.setAdapter(iceAdapter);
        toppingEt.setAdapter(toppingAdapter);
        methodEt.setAdapter(methodAdapter);
        ingrEt.setAdapter(ingrAdapter);
        clEt.setAdapter(clAdapter);
     
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
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
			switch(item.getItemId()){
			case R.id.guessMenuDone:
				Intent iResult = new Intent();

				iResult.putExtra("ice", iceEt.getText().toString());
				iResult.putExtra("glass", glassEt.getText().toString());
				//put toppings
				ArrayList<String> toppingsToSend = new ArrayList<String>();
				for(AutoCompleteTextView tv: toppingEds){
					toppingsToSend.add(tv.getText().toString());
				}
				toppingsToSend.add(toppingEt.getText().toString());
				iResult.putExtra("toping",toppingsToSend);

				setResult(RESULT_OK, iResult);
				finish();
			break;
			}
		return super.onMenuItemSelected(featureId, item);
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
				toppingsToSend.add(toppingEt.getText().toString());
				iResult.putExtra("toping",toppingsToSend);
				//put methods
				ArrayList<String> methodsToSend = new ArrayList<String>();
				for(AutoCompleteTextView tv: methodEds){
					methodsToSend.add(tv.getText().toString());
				}
				methodsToSend.add(methodEt.getText().toString());
				iResult.putExtra("method",methodsToSend);
				//put ingrs+cls
				//ingr
				ArrayList<String> ingrsToSend = new ArrayList<String>();
				for(AutoCompleteTextView tv: ingrEds){
					ingrsToSend.add(tv.getText().toString());
				}
				ingrsToSend.add(ingrEt.getText().toString());
				iResult.putExtra("ingr",ingrsToSend);
				//cl
				ArrayList<String> clsToSend = new ArrayList<String>();
				for(AutoCompleteTextView tv: clEds){
					clsToSend.add(tv.getText().toString());
				}
				clsToSend.add(clEt.getText().toString());
				iResult.putExtra("cl",clsToSend);

				setResult(RESULT_OK, iResult);
				finish();
			break;
			
			case R.id.guessAddToping:
				//LL + ET
				final AutoCompleteTextView newToppingsEt = new AutoCompleteTextView(this);
				ArrayAdapter<String> toppingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topings.toArray(new String[topings.size()]));
				newToppingsEt.setAdapter(toppingAdapter);
				LinearLayout newTopingsll = new LinearLayout(this);
				//Add to list
				toppingEds.add(newToppingsEt);
				//Button				
				Button newTopingsLlDel = new Button(this);
				newTopingsLlDel.setText("del");
				newTopingsLlDel.setTextColor(Color.WHITE);
				newTopingsLlDel.setBackgroundResource(R.color.colordel);
				newTopingsLlDel.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v)
						{
							LinearLayout l = (LinearLayout) v.getParent();
							l.setVisibility(View.GONE);
							toppingEds.remove(newToppingsEt);
						}
						
					
				});
				//Adding views
				newTopingsll.addView(newToppingsEt, llpEt);
				newTopingsll.addView(newTopingsLlDel, llpBtn);
				lltopings.addView(newTopingsll);
				newToppingsEt.requestFocus();
			break;
			
			case R.id.guessAddMethod:
				//LL + ET
				final AutoCompleteTextView newMethodsEt = new AutoCompleteTextView(this);
				ArrayAdapter<String> methodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, methods.toArray(new String[methods.size()]));
				newMethodsEt.setAdapter(methodAdapter);
				LinearLayout newMethodsll = new LinearLayout(this);
				//Add to list
				methodEds.add(newMethodsEt);
				//Button				
				Button newMethodLlDel = new Button(this);
				newMethodLlDel.setText("del");
				newMethodLlDel.setTextColor(Color.WHITE);
				newMethodLlDel.setBackgroundResource(R.color.colordel);
				newMethodLlDel.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v)
						{
							LinearLayout l = (LinearLayout) v.getParent();
							l.setVisibility(View.GONE);
							methodEds.remove(newMethodsEt);
						}
						
					
				});
				//Adding views
				newMethodsll.addView(newMethodsEt, llpEt);
				newMethodsll.addView(newMethodLlDel, llpBtn);
				llmethod.addView(newMethodsll);
				newMethodsEt.requestFocus();
			break;
			
			case R.id.guessAddIngr:
				//LL + ET
				final AutoCompleteTextView newIngrEt = new AutoCompleteTextView(this);
				final AutoCompleteTextView newClEt = new AutoCompleteTextView(this);
				ArrayAdapter<String> ingrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingrs.toArray(new String[ingrs.size()]));
		        ArrayAdapter<String> clAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cls.toArray(new String[cls.size()]));
		        newIngrEt.setAdapter(ingrAdapter);
		        newClEt.setAdapter(clAdapter);
		        
				LinearLayout newIngrll = new LinearLayout(this);
				//Add to list
				ingrEds.add(newIngrEt);
				clEds.add(newClEt);
				//Button				
				Button newIngrDel = new Button(this);
				newIngrDel.setText("del");
				newIngrDel.setTextColor(Color.WHITE);
				newIngrDel.setBackgroundResource(R.color.colordel);
				newIngrDel.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v)
						{
							LinearLayout l = (LinearLayout) v.getParent();
							l.setVisibility(View.GONE);
							ingrEds.remove(newIngrEt);
							clEds.remove(newClEt);
						}
						
					
				});
				//Adding views
				newIngrll.addView(newIngrEt, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2.5f));
				newIngrll.addView(newClEt, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2.5f));
				newIngrll.addView(newIngrDel, llpBtn);
				llingr.addView(newIngrll);
				newIngrEt.requestFocus();
			break;
		}
	
		
	}

}
