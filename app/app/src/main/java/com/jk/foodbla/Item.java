package com.jk.foodbla;

/**
 * Created by jonas on 19.01.18.
 */

public class Item {
    String name;
    String price;

    public Item(){

    }

    public String toString(){
        return name + ": " + price;
    }

    public Item(String name, String price){
        this.name = name;
        this.price = price;
    }
}
