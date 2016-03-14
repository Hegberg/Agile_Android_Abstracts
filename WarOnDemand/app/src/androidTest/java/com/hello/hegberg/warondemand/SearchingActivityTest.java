package com.hello.hegberg.warondemand;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Chris on 3/1/2016.
 */
public class SearchingActivityTest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    Activity activity;
    EditText textInput;

    public SearchingActivityTest() {
        super(SearchingActivityTest.class);
    }

    public void testNoKeyword(){
        Intent intent = new Intent();
        setActivityIntent(intent);
        SearchingActivity tester = (SearchingActivity) getActivity();
        //textInput = ((EditText) activity.findViewById(R.id.editText));
        //((Button) tester.findViewById(R.id.start_search)).performClick();
    }

    public void testWithKeyword(){
        Intent intent = new Intent();
        setActivityIntent(intent);
        SearchingActivity tester = (SearchingActivity) getActivity();
        /*
        textInput = ((EditText) activity.findViewById(R.id.editText));
        tester.setKeywords(textInput.toString());
        ((Button) tester.findViewById(R.id.start_search)).performClick();
        */
    }


}