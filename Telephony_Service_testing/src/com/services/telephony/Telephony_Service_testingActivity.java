package com.services.telephony;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Telephony_Service_testingActivity extends Activity {
    /** Called when the activity is first created. */
	
	Intent service_intent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        service_intent = new Intent(this,Telephony_ActiveServices.class);
        startService(service_intent);
        
    }
}