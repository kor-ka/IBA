package com.mycompany.myapp;

import android.app.*;
import android.content.*;
import android.os.*;
import it.gmariotti.cardslib.library.internal.*;
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
	Set<String> toping;
	Set<String> glass;
	Set<String> ice;
	Set<String> method;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		ctx = this;
		ingr = new HashSet<String>();
		toping = new HashSet<String>();
		glass = new HashSet<String>();
		ice = new HashSet<String>();
		method = new HashSet<String>();
		ArrayList<Card> cards = new ArrayList<Card>();
		
		baseJsonString = getString(R.string.base);
		
		 try {
		 	JSONObject baseJson = new JSONObject(baseJsonString.toString());
		 	JSONArray jArray = baseJson.getJSONArray("base");
			 //Toast.makeText(ctx, "langth"+jArray.length(), Toast.LENGTH_SHORT).show();
		 	for(int i = 0; i < jArray.length(); i++){
		 		try {
		 			 JSONObject oneObject = jArray.getJSONObject(i);	
				     JSONArray ingrs = oneObject.getJSONArray("ingr");
					 JSONArray cl = oneObject.getJSONArray("cl");
					 Map<String,String> ingrCl = new HashMap<String,String>();
					 String text = "";
					 for(int i2 =0 ; i2 < ingrs.length(); i2++){
						 ingrCl.put(ingrs.getString(i2), cl.getString(i2));
						 int mCl = 0;
						 try
						 {
							 mCl = Integer.parseInt(cl.getString(i2));
						 }
						 catch (NumberFormatException e)
						 {}
						String mClStr= "";
						if (mCl!=0){mClStr=""+mCl;}
						 text = text + "• " + mClStr + " " + ingrs.getString(i2) + "\n";
					 }
					 text =text+ "\n" + oneObject.getString("descr") +"\n";
					 JSONArray topings = oneObject.getJSONArray("toping");
					 JSONArray methods = oneObject.getJSONArray("method");
					 
					 Card newcard = new Card (ctx);
					 CardHeader newheader = new CardHeader(ctx);
					 CardExpand newcardex = new CardExpand(ctx);
					 CardThumbnail newthumb = new CardThumbnail(ctx);
					 
					 newheader.setTitle(oneObject.getString("name"));
					 newcardex.setTitle(text);
					 newthumb.setDrawableResource(R.drawable.ic_launcher);
					 //newheader.setButtonExpandVisible(true);
				
					 newcard.addCardHeader(newheader);
					 newcard.addCardExpand(newcardex);
					 newcard.addCardThumbnail(newthumb);
					
					 
					 //newcard.setViewToClickToExpand(ViewToClickToExpand.builder().setupView(newcard.getCardView()));
					 newcard.setTitle(text);
					 
					 cards.add(newcard);
					 
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
		CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this,cards);
        CardListView listView = (CardListView) this.findViewById(R.id.fullCardsList);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }	
    }
}
