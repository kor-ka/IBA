package com.mycompany.myapp;

import android.app.*;
import android.content.*;
import android.os.*;
import it.gmariotti.cardslib.library.internal.*;
import it.gmariotti.cardslib.library.view.*;
import java.util.*;

public class MainActivity extends Activity
{
	Context ctx;
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

        CardListView listView = (CardListView) this.findViewById(R.id.fullCardsList);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }	
    }
}
