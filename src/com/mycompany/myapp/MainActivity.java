package com.mycompany.myapp;

import android.app.*;
import android.content.*;
import android.os.*;
import it.gmariotti.cardslib.library.internal.*;
import it.gmariotti.cardslib.library.view.*;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.HashMap;

public class MainActivity extends Activity
{
	Context ctx;
	String baseJsonString;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		ctx = this;
		ArrayList<Card> cards = new ArrayList<Card>();

		//Create a Card
		Card card = new Card(ctx);

		//Create a CardHeader
		CardHeader header = new CardHeader(ctx);
		header.setTitle("hi!");
		//Add Header to card
		card.addCardHeader(header);

		cards.add(card);
		
		CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this,cards);
		
		baseJsonString = getString(R.string.base);
		
		 try {
		 	JSONObject baseJson = new JSONObject(baseJsonString);
		 	JSONArray jArray = baseJson.getJSONArray("base");
		 	
		 	for(int i = 0; i < jArray.length(); i++)){
		 		try {
		 			JSONObject oneObject = jArray.getJSONObject(i);	
		 		} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		 	}//for end
		 } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        CardListView listView = (CardListView) this.findViewById(R.id.fullCardsList);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }	
    }
}
