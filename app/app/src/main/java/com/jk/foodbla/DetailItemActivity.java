package com.jk.foodbla;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DetailItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Bier      ", "     1.10 CHF"));
        items.add(new Item("Merlot 7dl", "    12.95 CHF"));


        ArrayAdapter<Item> a = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, items);

        ListView l = (ListView)findViewById(R.id.listViewDetail);
        l.setAdapter(a);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Return selected item
                Intent result = new Intent();
                result.putExtra("position", position);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
