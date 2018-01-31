package com.jk.foodbla;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonas on 19.01.18.
 */

public class Order {

    public Order(){

    }

    public Order(String orderId){
        this.orderId = orderId;
    }

    public void addItem(Item i){
        itemsOrdered.add(i);
    }

    public void removeItem(Item i){
        itemsOrdered.remove(i);
    }

    public void removeItem(int index){
        itemsOrdered.remove(index);
    }
    List<Item> itemsOrdered = new ArrayList<Item>();
    String orderId;

}
