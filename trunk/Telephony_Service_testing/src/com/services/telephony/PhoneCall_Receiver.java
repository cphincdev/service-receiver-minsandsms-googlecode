/**
 * 
 */
package com.services.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

/**
 * @author Rohan
 *
 */
public class PhoneCall_Receiver extends BroadcastReceiver{
	
	public Bundle phoneBundle;
	public Context globalContext;
	public Intent globalIntent;
	public String phoneState = null;
	public String incommingNumber = null;
	public final int NOTIFICATION_ID = 1 ;
	
	public String serviceName = null;
	public TelephonyManager telephonyManager; 
	public int phoneType = 1; //1 = PHONE_TYPE_GSM taking it as GSM from initial stage
			//telephonyManager.PHONE_TYPE_NONE;	//for tablets or device with no Radio 	
	public String deviceID = null; 
	public String device_OSVersion = null; 

	public String networkCountry = null;
	public String networkOperatorID = null;
	public String networkOperatorName = null;
	public int simState = 5; //5 = SIM_STATE_READY taking initially as SIM is present 
	public String displayMsg = null;
	
	public boolean isitGSM = false;
	public boolean isitCDMA = false;
	
	public boolean callForwarding_State = false;
	
	public UserPhone_Details userPhone_details;
	
	@Override
	public void onReceive(Context context,Intent intent)
	{
		globalIntent = intent;
		globalContext = context;

		//need to define own PhoneStateListener, hence interfaces is being called
		PhoneStateListener phoneStateListener = new PhoneStateListener() 
		{
			public void onCallForwardingIndicatorChanged(boolean cfi){}
			public void onCallStateChanged (int state, String incomingnumber){}
			public void onCellLocationChanged (CellLocation cellLocation){}
			public void onServiceStateChanged (ServiceState serviceState){}
		};
		
		serviceName=context.TELEPHONY_SERVICE;
		telephonyManager = (TelephonyManager)context.getSystemService(serviceName);
		userPhone_details.phoneType(context);
		phoneType = userPhone_details.phoneType;
		simState = userPhone_details.simState;
		
		networkCountry = userPhone_details.networkCountry;
		networkOperatorID = userPhone_details.networkOperatorID;
		networkOperatorName = userPhone_details.networkOperatorName;
		
		isitGSM = userPhone_details.GSM;
		isitCDMA = userPhone_details.CDMA;
		
		telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR|
								PhoneStateListener.LISTEN_CALL_STATE | 
								PhoneStateListener.LISTEN_CELL_LOCATION |
								PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR |
								PhoneStateListener.LISTEN_SERVICE_STATE |
								PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		
		//Cellphone call state changed
		PhoneStateListener callStateListener = new PhoneStateListener ()
		{
			public void onCallStateListener (int state,String incomingnumber)
			{
				phoneBundle=globalIntent.getExtras();
				
				if(phoneBundle != null)
				{
					phoneState = globalIntent.getStringExtra(telephonyManager.EXTRA_STATE);
					if(telephonyManager.CALL_STATE_RINGING == state)
					{
						incommingNumber = incomingnumber;		
						userPhone_details.notificationOnTop(globalContext,incommingNumber);
						displayMsg = "Incoming Number is :";
						userPhone_details.Toast_msg(globalContext,displayMsg+incommingNumber);
						Log.i("Broadcast Recieved", "incomming call"+incommingNumber);
					}
					else if(telephonyManager.CALL_STATE_IDLE == state)
					{
						userPhone_details.notificationOnTop(globalContext,displayMsg);
						userPhone_details.Toast_msg(globalContext, displayMsg);
						Log.i("Broadcast Recieved", "phone is idle right now");
					}
					else if(telephonyManager.CALL_STATE_OFFHOOK == state)
					{
						displayMsg = "Phone is currently in call";
						userPhone_details.notificationOnTop(globalContext,displayMsg);
						userPhone_details.Toast_msg(globalContext, displayMsg);
						Log.i("Broadcast Recieved", "dialling/active/hold");
					}
				}	
			}
		};
		telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		
		//tracking changes in Users Cell Location constantly
		PhoneStateListener cellLocationListener = new PhoneStateListener ()
		{
				public void onCellLocationListener (CellLocation cellLocation)
				{
					if(isitGSM == true)
					{
						GsmCellLocation gsmLocation = (GsmCellLocation) cellLocation;
					}
					else if(isitCDMA == true)
					{
						CdmaCellLocation cdmalocation = (CdmaCellLocation) cellLocation;
					}
				}
		};

		telephonyManager.listen(cellLocationListener, PhoneStateListener.LISTEN_CELL_LOCATION);
		
		PhoneStateListener serviceStateListener = new PhoneStateListener() 
		{
			public void onServiceStateChanged(ServiceState serviceState)
			{
				if(serviceState.getState() == ServiceState.STATE_IN_SERVICE)
				{
					displayMsg = serviceState.getOperatorAlphaLong();
					userPhone_details.Toast_msg(globalContext, displayMsg);
					userPhone_details.notificationOnTop(globalContext, displayMsg);
				}
				else if (serviceState.getState() == ServiceState.STATE_OUT_OF_SERVICE)
				{
					displayMsg = serviceState.getOperatorAlphaLong() + " Phone is out of service.";
					userPhone_details.Toast_msg(globalContext, displayMsg);
					userPhone_details.notificationOnTop(globalContext, displayMsg);
				}
				else if (serviceState.getState() == ServiceState.STATE_POWER_OFF)
				{
					displayMsg = serviceState.getOperatorAlphaLong() + " Phone is on Air-Plane Mode";
					userPhone_details.Toast_msg(globalContext, displayMsg);
					userPhone_details.notificationOnTop(globalContext, displayMsg);
				}
				else if (serviceState.getState() == ServiceState.STATE_EMERGENCY_ONLY)
				{
					displayMsg = serviceState.getOperatorAlphaLong() + "Phone is under EMERGENCY ONLY STATE";
					userPhone_details.Toast_msg(globalContext, displayMsg);
					userPhone_details.notificationOnTop(globalContext, displayMsg);
				}
			}
		};
		
		telephonyManager.listen(serviceStateListener, PhoneStateListener.LISTEN_SERVICE_STATE);
		
		PhoneStateListener forwardcallStateChanged = new PhoneStateListener() 
		{
			public void onCallForwardingIndicatorChanged (boolean cfi)
			{
				callForwarding_State = cfi;
			}
		};
		
		telephonyManager.listen(forwardcallStateChanged, PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR);

	}
	
}