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

    public void setItemsOrdered(List<Item> itemsOrdered){
        this.itemsOrdered = itemsOrdered;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public List<Item> itemsOrdered = new ArrayList<Item>();
    public String orderId;

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Order id: " + orderId + ", Items: ");
        for(Item i: itemsOrdered){
            s.append(i.toString() + ", ");
        }
        return s.toString();
    }

}
