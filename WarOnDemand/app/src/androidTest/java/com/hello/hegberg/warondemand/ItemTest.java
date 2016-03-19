package com.hello.hegberg.warondemand;

import android.app.Application;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import junit.framework.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ItemTest extends ActivityInstrumentationTestCase2 {
    public ItemTest() {
        super(Application.class);
    }

    private void addItem(String testName,String testDesc) {
        //Simulate button click here.
    }
    private void deleteItem(String testName){
        //Simulate button click.
    }
    private void editItem(String newText){
        //Simulate button click.
    }
    private void viewItems(){
        //Simulate button click that views items.
    }

    public void testAddItem(){

    }

    public void testViewItems(){


    }

    public void testViewItem() {


    }

    public void testEditItem(){
    }

    public void testDeleteItem(){

    }

    public void testSetReturn(){

    }

    public void testOffline(){

    }
}