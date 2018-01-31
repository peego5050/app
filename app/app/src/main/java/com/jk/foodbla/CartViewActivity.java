package com.jk.foodbla;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartViewActivity extends AppCompatActivity {

    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        c = this.getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Load data from intent
        Intent intent = getIntent();
        final String ORDER_ID = intent.getStringExtra("ORDER_ID");

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child("orders").child(ORDER_ID);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order o = dataSnapshot.getValue(Order.class);

                Item[] itemsArray = new Item[o.itemsOrdered.size()];
                o.itemsOrdered.toArray(itemsArray);
                ArrayAdapter<Item> itemsAdapter = new ArrayAdapter<Item>(c, android.R.layout.simple_list_item_1, itemsArray);

                // Load the listview from the layout and display the data by setting the adapter of the list view
                ListView itemsView = (ListView)findViewById(R.id.listViewCart);
                itemsView.setAdapter(itemsAdapter);

                itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Record item selected
                        /*
                        o.addItem(new Item("Item " + position, "0.00"));
                        ref.child("orders").child(orderKey).setValue(o);
                        */


                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
