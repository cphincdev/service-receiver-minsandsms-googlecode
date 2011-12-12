/**
 * 
 */
package com.services.telephony;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * @author RKM
 *
 */
public class UserPhone_Details  {
	
	public String serviceName = null;
	public TelephonyManager telephonyManager; 
	public int phoneType = 1; //1 = PHONE_TYPE_GSM taking it as GSM from initial stage
			//telephonyManager.PHONE_TYPE_NONE;	//for tablets or device with no Radio 	
	public String deviceID = null; 
	public String device_OSVersion = null; 
	
	public String networkCountry = null;
	public String networkOperatorID = null;
	public String networkOperatorName = null;
	public boolean GSM = true;
	public boolean CDMA = false;
	public final int NOTIFICATION_ID = 1;
	public String displayMsg = null;
	public int simState = 5; //5 = SIM_STATE_READY taking initially as SIM is present 
	
	public void phoneType(Context context)
	//very doubtful about this code..if problem occurs it might be due to getSystemService
	{
		
		serviceName=context.TELEPHONY_SERVICE;
		telephonyManager = (TelephonyManager)context.getSystemService(serviceName);
		phoneType = telephonyManager.getPhoneType();
		simState = telephonyManager.getSimState();
		
		if(phoneType == TelephonyManager.PHONE_TYPE_CDMA)
		{
			CDMA = true;
			displayMsg="Phone is on CDMA Band";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);
		}
		else if (phoneType == TelephonyManager.PHONE_TYPE_GSM)
		{
			GSM = true;
			displayMsg="Phone is on GSM Band";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);
		}
		else if (phoneType == TelephonyManager.PHONE_TYPE_NONE)
		{
			GSM = false;
			CDMA = false;
			displayMsg="Phone Radio not present. This app is not meant for devices apart from phones";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);			
		}
		else if (simState == TelephonyManager.SIM_STATE_ABSENT)
		{
			displayMsg="SIM card is not inserted";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);
		}
		else if (simState == TelephonyManager.SIM_STATE_PIN_REQUIRED ||
				 simState == TelephonyManager.SIM_STATE_NETWORK_LOCKED ||
				 simState == TelephonyManager.SIM_STATE_PIN_REQUIRED ||
				 simState == TelephonyManager.SIM_STATE_PUK_REQUIRED)
		{
			displayMsg = "something is wrong with your SIM card";
			notificationOnTop(context,displayMsg);
			Toast_msg(context,displayMsg);
		}
		
		deviceID = telephonyManager.getDeviceId(); //this uses permission READ_PHONE_STATE
		device_OSVersion = telephonyManager.getDeviceSoftwareVersion(); 
		
		
	}
	
	public void phone_Country(Context context)
	{
		networkCountry = telephonyManager.getNetworkCountryIso();
		networkOperatorID = telephonyManager.getNetworkOperator();
		networkOperatorName = telephonyManager.getNetworkOperatorName();
	}
	
	public void Toast_msg(Context context,String msg){
		Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
	}
	
	public void notificationOnTop(Context context,String string)
	{
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(android.R.drawable.sym_action_call,"CallSMSTracker Notification",System.currentTimeMillis());
		
		PendingIntent notification_Content_PendingIntent = PendingIntent.getActivity(context,0,new Intent(context,PhoneCall_Receiver.class),0);
		
		notification.setLatestEventInfo(context, "Call And SMS Tracker", string ,notification_Content_PendingIntent);
		
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
}