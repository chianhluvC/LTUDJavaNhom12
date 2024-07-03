package com.team12.DASpring.entity;

public class CartItem {

    private ElectronicDevice electronicDevice;
    private int quantity;


    public CartItem(ElectronicDevice electronicDevice, int quantity){
        this.electronicDevice = electronicDevice;
        this.quantity = quantity;
    }

    public ElectronicDevice gettElectronicDevice(){
        return  electronicDevice;
    }

    public void  settElectronicDevice(ElectronicDevice electronicDevice){
        this.electronicDevice = electronicDevice;
    }

    public int gettQuantity(){
        return quantity;
    }


    public void settQuantity(int quantity){
        this.quantity = quantity;
    }

    public double getAmount(){
        return quantity * electronicDevice.getDiscountPrice();
    }


}
