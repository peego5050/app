package com.jk.foodbla;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmActivity extends AppCompatActivity implements OnMapReadyCallback{

    DatabaseReference orderReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapConfirm);
        mapFragment.getMapAsync(this);

        orderReference = getDatabaseReference(getIntent());
        displayOrder();


    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Zürich,
        // and move the map's camera to the same location.
        // set zoom to 18 (closest 21, farthest: 1)
        LatLng zurich = new LatLng(47.361268, 8.525749);
        googleMap.addMarker(new MarkerOptions().position(zurich)
                .title("Marker in Zurich"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zurich, 18));
    }

    public void toggleDeadline(View v){
        Switch s = (Switch)findViewById(R.id.switchDeadline);
        if(s.isChecked()){
            // Button checked, show deadline
            Log.d("Confirm", "deadline selected");

            Context context = getApplicationContext();
            CharSequence text = "deadline activated";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            // Button unselected, hide deadline
            Log.d("Confirm", "deadline unselected");
        }
    }

    public DatabaseReference getDatabaseReference(Intent i){
        // Get the order id
        String orderId = i.getStringExtra("ORDER_ID");
        Log.d("Confirm", "Order id: " + orderId);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("user2");

        return ref.child("orders").child(orderId);
    }

    public void displayOrder(){

        orderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order o = dataSnapshot.getValue(Order.class);
                Log.d("Confirm", "Loaded order: " + o.toString());

                // Populate listview
                ListView lv = (ListView)findViewById(R.id.listViewConfirm);

                ArrayAdapter<Item> a = new ArrayAdapter<Item>(getApplication(), android.R.layout.simple_list_item_1, o.itemsOrdered);
                lv.setAdapter(a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        // Display items with the help of an adapter
    }

}
