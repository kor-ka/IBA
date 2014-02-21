package com.mycompany.myapp;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.os.*;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.CommonDataKinds.Im;
import it.gmariotti.cardslib.library.internal.*;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;
import it.gmariotti.cardslib.library.view.*;
import java.util.*;

import org.json.*;

import android.widget.*;
import android.view.View.*;
import android.view.*;

public class MainActivity extends Activity
{
	Context ctx;
	String baseJsonString;
	Set<String> ingr;
	Set<String> cls;
	Set<String> toping;
	Set<String> glass;
	Set<String> ice;
	Set<String> method;
	ArrayList<String> cocktail;
	Map<String, Coctail> coctailMap;
	SharedPreferences shp;
	SharedPreferences.Editor ed;
	Coctail coctailToSetResult;
	Card cardtoSetResul;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ctx = this;
        shp = PreferenceManager.getDefaultSharedPreferences(ctx);
        ed = shp.edit();
        ArrayList<Card> cards = new ArrayList<Card>();
        
       
        
        jsonToMap();
        //init Cards
        for(String oneCoctailName:cocktail){
        	final Coctail oneCoctail = coctailMap.get(oneCoctailName);
        	
        	Card card = new Card(ctx);
        	CardHeader header = new CardHeader(ctx);
        	CardThumbnail thumb = new CardThumbnail(ctx);
        	//Set  thumb pic
        	
        	thumb.setDrawableResource(this.getResources().getIdentifier(oneCoctailName.toLowerCase(), "drawable", this.getPackageName()));
        	
        	header.setTitle(oneCoctail.getName());
        	
        	String text = "";
        	String[] ingrs =  oneCoctail.getIngr();
        	String[] cls =  oneCoctail.getCl();
        	for(int i=0; i<ingrs.length; i++ ){
        		 int mCl = 0;
				 try
				 {
					 mCl = Integer.parseInt(cls[i]);
				 }
				 catch (NumberFormatException e)
				 {}
				String mClStr= "";
				if (mCl!=0){mClStr=""+mCl+"cl ";}
        		text = text+"• "+mClStr+ingrs[i]+"\n";
        	}
        	text = text+"\n"+oneCoctail.getDescr();
        	card.setTitle(text);
        	card.addCardHeader(header);
        	card.addCardThumbnail(thumb);
        	
        	card.setOnClickListener(new OnCardClickListener(){

				@Override
				public void onClick(Card card, View view) {
					Intent i = new Intent(ctx, Guess.class);
					i.putExtra("name", oneCoctail.getName());
					/*
					i.putExtra("ice", oneCoctail.getIce());
					i.putExtra("glass", oneCoctail.getGlass());
					i.putExtra("method", oneCoctail.getMethod());
					i.putExtra("cl", oneCoctail.getCl());
					i.putExtra("ingr", oneCoctail.getIngr());
					i.putExtra("toping", oneCoctail.getToping());
					
					i.putExtra("descr", oneCoctail.getDescr());
					*/
					coctailToSetResult = oneCoctail;
					cardtoSetResul = card;
					startActivityForResult(i, 1);
					
				}
        		
        	});
        	
        	cards.add(card);
        }
		
        
		CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this,cards);
        CardListView listView = (CardListView) this.findViewById(R.id.fullCardsList);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode==1 && resultCode == RESULT_OK){
    		
    		Boolean iceGuessed = data.getStringExtra("ice").equals( coctailToSetResult.getIce());
    		Boolean glassGuessed = data.getStringExtra("glass").equals(coctailToSetResult.getGlass());
			//Compare toppings
			HashSet<String> topSet1 = new HashSet<String>( data.getStringArrayListExtra("toping"));
			HashSet<String> topSet2 = new HashSet<String>(Arrays.asList(coctailToSetResult.getToping()) );
			Boolean topingGuessed = topSet1.equals(topSet2);
			//Compare methods
			HashSet<String> metSet1 = new HashSet<String>( data.getStringArrayListExtra("method"));
			HashSet<String> metSet2 = new HashSet<String>(Arrays.asList(coctailToSetResult.getMethod()) );
			Boolean methodGuessed = metSet1.equals(metSet2);
			//Compare ingrs & cls
			//ingrs check
			HashSet<String> ingrSet1 = new HashSet<String>( data.getStringArrayListExtra("ingr"));
			HashSet<String> ingrSet2 = new HashSet<String>(Arrays.asList(coctailToSetResult.getIngr()) );
			Boolean ingrsGuessed = ingrSet1.equals(ingrSet2);
			if (ingrsGuessed) {
				//cl check
				String[] ingrsOrig = coctailToSetResult.getIngr();
				String[] clssOrig = coctailToSetResult.getCl();
				ArrayList<String> ingrsToCheck = data.getStringArrayListExtra("ingr");
				ArrayList<String> clsToCheck = data.getStringArrayListExtra("cl");
				for (int i = 0; i < ingrsOrig.length&&ingrsGuessed; i++) {
					ingrsGuessed=clssOrig[i].equals(clsToCheck.get(ingrsToCheck.indexOf(ingrsOrig[i])));

				}
			}
			
			if(iceGuessed&&glassGuessed&&topingGuessed&&methodGuessed&&ingrsGuessed){
    			Toast.makeText(ctx, "good", Toast.LENGTH_LONG).show();
    		}else{
    			Toast.makeText(ctx, "loooooser", Toast.LENGTH_LONG).show();
    		}
    	}
    }
    
    public void jsonToMap(){
    	
    	
		ingr = new HashSet<String>();
		cls = new HashSet<String>();
		toping = new HashSet<String>();
		glass = new HashSet<String>();
		ice = new HashSet<String>();
		method = new HashSet<String>();
		cocktail = new ArrayList<String>();
		
		coctailMap = new HashMap<String, Coctail>();
		
		baseJsonString = getString(R.string.base);
		
		 try {
		 	JSONObject baseJson = new JSONObject(baseJsonString.toString());
		 	JSONArray jArray = baseJson.getJSONArray("base");
			 //Toast.makeText(ctx, "langth"+jArray.length(), Toast.LENGTH_SHORT).show();
		 	for(int i = 0; i < jArray.length(); i++){
		 		try {
		 			 JSONObject oneObject = jArray.getJSONObject(i);

					 //Cocktail data
				     JSONArray ingrs = oneObject.getJSONArray("ingr");
				     String[] oneingrs = ingrs.toString().replaceAll("\"", "").replaceAll("\\]", "").replaceAll("\\[", "").split(",");
					 JSONArray cl = oneObject.getJSONArray("cl");
					 String[] onecls = cl.toString().replaceAll("\"", "").replaceAll("\\]", "").replaceAll("\\[", "").split(",");
					 
					 JSONArray topings = oneObject.getJSONArray("toping");
					 JSONArray methods = oneObject.getJSONArray("method");
					 String[] onetopings = topings.toString().replaceAll("\"", "").replaceAll("\\]", "").replaceAll("\\[", "").split(",");					 
					 String[] onemethods = methods.toString().replaceAll("\"", "").replaceAll("\\]", "").replaceAll("\\[", "").split(",");					 
					 Coctail newcocktail = new Coctail(oneObject.getString("name"),
							 							oneObject.getString("descr"),
							 							oneObject.getString("glass"),
							 							oneObject.getString("ice"),
							 							oneingrs,
							 							onecls,
							 							onetopings,
							 							onemethods);
					 coctailMap.put(oneObject.getString("name"), newcocktail);
					 
					 
					//Sets
					 for(String onetoping:onetopings){toping.add(onetoping);}
					 for(String onemethod:onemethods){method.add(onemethod);}
					 glass.add(oneObject.getString("glass"));
					 ice.add(oneObject.getString("ice"));
					 cocktail.add(oneObject.getString("name"));
					 for(String oneingr:oneingrs){ingr.add(oneingr);}
					 for(String onecl:onecls){cls.add(onecl);}
					 
					 ed.putStringSet("toping", toping);
					 ed.putStringSet("method", method);
					 ed.putStringSet("glass", glass);
					 ed.putStringSet("ice", ice);
					 ed.putStringSet("ingr", ingr);
					 ed.putString("test", "test");
					 ed.putStringSet("cl", cls);
					 ed.commit();
					 
		 		} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 Toast.makeText(ctx, "oops for\n"+e.getLocalizedMessage(), 999999999).show();
				}
				
		 	}//for end
		 } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(ctx, "oops\n" +e.getLocalizedMessage(), 999999999).show();
		}
		
    	
    }
    
    public int getCoctailPicByString(String name){
    	int resId = R.drawable.ic_launcher;
    	if(name=="AMERICANO"){
    		resId = R.drawable.americano;
    	}else if(name=="ALEXANDER"){
    		resId = R.drawable.alexander;
    	}
    	
    	return resId;
    }
}

	
