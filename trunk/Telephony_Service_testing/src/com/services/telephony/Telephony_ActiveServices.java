package com.services.telephony;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Telephony_ActiveServices extends Service {
	
	@Override
	public void onCreate(){
		
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent,int flags,int startId){
		if ((flags & START_FLAG_RETRY ) ==0 ){
			//todo
		}
		else{
			//todo
		}
		//return Service.START_NOT_STICKY;	//causes to end/stop the service when onStartCommand is completed
		return Service.START_STICKY; 		//causes to explicitly stop and start the service from the activity
	}
	
}
