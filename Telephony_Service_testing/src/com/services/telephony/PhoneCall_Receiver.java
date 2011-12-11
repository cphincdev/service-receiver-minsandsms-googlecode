/**
 * 
 */
package com.services.telephony;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author Rohan
 *
 */
public class PhoneCall_Receiver extends BroadcastReceiver{
	
	public TelephonyManager tele;
	public Bundle phoneBundle;
	public String phoneState;
	public String incommingNumber;
	public Context globalContext;
	public final int NOTIFICATION_ID = 1 ;
	
	@Override
	public void onReceive(Context context,Intent intent){
		globalContext = context;
		phoneBundle=intent.getExtras();
				
		if(phoneBundle != null)
		{
			phoneState = intent.getStringExtra(tele.EXTRA_STATE);
			if(tele.EXTRA_STATE_RINGING.equals(phoneState))
			{
				incommingNumber = phoneBundle.getString(tele.EXTRA_INCOMING_NUMBER);
				//Toast.makeText(	, "Incomming Number: "+incommingNumber, Toast.LENGTH_LONG).show();
						//wont work Toast can only be used in Activities and Services		
				notificationOnTop(incommingNumber);
				Log.i("Broadcast Recieved", "incomming call"+incommingNumber);
				
				//if(intent.getExtras(android.intent.action.ANSWER)){
					
				//}
				
			}
			else if(tele.EXTRA_STATE_IDLE.equals(phoneState))
			{
				//do something
				notificationOnTop(phoneState);
				Log.i("Broadcast Recieved", "phone is idle right now");
			}
			else if(tele.EXTRA_STATE_OFFHOOK.equals(phoneState))
			{
				//do something
				notificationOnTop(phoneState);
				Log.i("Broadcast Recieved", "dialling/active/hold");
			}
			
			//else if(tele.e)
			//phoneState = intent.getStringExtra(intent.EXTRA_PHONE_NUMBER)
		}
	}
	
	public void notificationOnTop(String string){
		NotificationManager notificationManager = (NotificationManager) globalContext.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(android.R.drawable.sym_action_call,"CallSMSTracker Notification",System.currentTimeMillis());
		
		PendingIntent notification_Content_PendingIntent = PendingIntent.getActivity(globalContext,0,new Intent(globalContext,PhoneCall_Receiver.class),0);
		
		notification.setLatestEventInfo(globalContext, "Call And SMS Tracker", string ,notification_Content_PendingIntent);
		
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
	
}