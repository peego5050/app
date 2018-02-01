package com.jk.foodbla;

/**
 * Created by jonas on 19.01.18.
 */

public class Item {
    public String name;
    public String price;

    public Item(){

    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String toString(){
        return name + ": " + price;
    }

    public Item(String name, String price){
        this.name = name;
        this.price = price;
    }
}
