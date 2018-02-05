package com.jk.foodbla;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderNoTabActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference ref;
    public String orderKey;
    public Order o;
    public int currentSelection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_no_tab);

        populateListView();

        db = FirebaseDatabase.getInstance();
        int ORDER_ID = 1;
        String userId = "user2";
        ref = db.getReference().child(userId);
        orderKey = ref.child("orders").push().getKey();
        o = new Order(orderKey);
        ref.child("orders").child(orderKey).setValue(o);
        DatabaseReference orderRef = ref.child("orders").child(orderKey);
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Data changed, update object
                Order changedOrder = dataSnapshot.getValue(Order.class);
                Log.d("Order", "Order changed: " + changedOrder.toString());

                o = changedOrder;

                // Update Cart button
                Button cartButton = (Button)findViewById(R.id.buttonShowCart);
                cartButton.setText("Cart (" + o.itemsOrdered.size() + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Order addedOrder = dataSnapshot.getValue(Order.class);
                Log.d("Order", "Added: " + addedOrder.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Order changedOrder = dataSnapshot.getValue(Order.class);
                Log.d("Order", "Added: " + changedOrder.toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
    }



    public void showCart(View v){
        // Open Activity to display the contents of the cart. For this, pass the reference?
        Intent i = new Intent(this, CartViewActivity.class);
        i.putExtra("ORDER_ID", orderKey);

        startActivity(i);

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


        // Update Cart button
        Log.d("Order", "onResume");
        Button cartButton = (Button)findViewById(R.id.buttonShowCart);
        cartButton.setText("Cart (" + o.itemsOrdered.size() + ")");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        final String[] itemsArray = new String[]{"fruits & vegetables", "pasta&rice", "drinks", "household", "specials"};
        final String[] prices = new String[]{"5.50", "6.00", "3.00", "2.50", "2.00"};



        Log.d("Order", "Activity returned");

        //hier wenn möglich nur items der gewählten kategorie
        //wenn möglich items (name, preis, bildlink, gewicht, etc.) direkt aus firebase beziehen, image-resource via bildlink aus firebase storage)
        //
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("De Cecco", "2.60"));
        items.add(new Item("M-Budget", "1.75"));

        // Get id of item
        int position = data.getIntExtra("position", 0);

        // Record item selected
        Log.d("Order", "Adding Item " + position);
        o.addItem(new Item(itemsArray[currentSelection] + ": " + items.get(position).name, items.get(position).price));
        ref.child("orders").child(orderKey).setValue(o);

        // Update Cart button
        Button cartButton = (Button)findViewById(R.id.buttonShowCart);
        cartButton.setText("Cart (" + o.itemsOrdered.size() + ")");


    }


    public void populateListView(){
        // Example items
        final String[] items = new String[]{"fruits & vegetables", "pasta&rice", "drinks", "household", "specials"};
        final String[] prices = new String[]{"5.50", "6.00", "0.00", "2.50", "2.00"};
        // Add items to an array adapter
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        // Load the listview from the layout and display the data by setting the adapter of the list view
        ListView itemsView = (ListView)findViewById(R.id.listViewOrderOverview);
        itemsView.setAdapter(itemsAdapter);

        itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSelection = position;
                // Open dialog for detailed selection
                Intent i = new Intent(getApplicationContext(), DetailItemActivity.class);
                startActivityForResult(i, 0);


            }


        });


    }

}
