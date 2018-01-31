package com.jk.foodbla;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderNoTabActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference ref;
    String orderKey;
    Order o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_no_tab);

        populateListView();

        db = FirebaseDatabase.getInstance();
        int ORDER_ID = 1;
        String userId = "12345";
        ref = db.getReference().child(userId);
        orderKey = ref.child("orders").push().getKey();
        o = new Order(orderKey);
        ref.child(orderKey).setValue(o);

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

    public void populateListView(){
        // Example items
        String[] items = new String[]{"Pizza", "Pasta", "Drinks", "Dessert", "Specials"};
        // Add items to an array adapter
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        // Load the listview from the layout and display the data by setting the adapter of the list view
        ListView itemsView = (ListView)findViewById(R.id.listViewOrderOverview);
        itemsView.setAdapter(itemsAdapter);

        itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Record item selected
                o.addItem(new Item("Item " + position, "0.00"));
                ref.child("orders").child(orderKey).setValue(o);


            }
        });


    }

}
