/**
 * 
 */
package com.services.telephony;

import java.io.FileOutputStream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;

public class OutgoingCall_Handler extends BroadcastReceiver {

	public String outgoingNumber = null;
	
	public final String FILENAME = "Data_File";
	public String temp_String = null;
	public byte[] temp_byteArray = null;
	public FileOutputStream fos;
	
	public DBAdapter model;
	public Cursor cursor;
	public final String FAVORITE_TABLE = "FAVORITE_NUMBER";
	
	public String eveningTime = null;
	public boolean weekendFree = false;
	public boolean favoriteNumbers_Plan = false;

	public boolean favorite_5 = false;
	public boolean favorite_10 = false;
	
	public String favoriteNumber1 = null;
	public String favoriteNumber2 = null;
	public String favoriteNumber3 = null;
	public String favoriteNumber4 = null;
	public String favoriteNumber5 = null;
	public String favoriteNumber6 = null;
	public String favoriteNumber7 = null;
	public String favoriteNumber8 = null;
	public String favoriteNumber9 = null;
	public String favoriteNumber10 = null;
	
	public int seconds = 0;
	public int minutes = 0;
	public int hours = 0;
	public String timeFormat = "%02d:%02d:%02d";
	
	//public long callDuration = 0;
	
    @Override
    public void onReceive(Context context,Intent intent)
    {
    	outgoingNumber = this.getResultData();		//phone number of present outgoing call
    	
    	temp_String = "Outgoing Number:" + outgoingNumber;

    	fos = context.openFileOutput ( FILENAME , context.MODE_PRIVATE );
    	
    	context.startService(new Intent(context,Telephony_ActiveServices.class));
    			
    			//Telephony_ActiveServices);
    	
    	model = new DBAdapter(this);
    	
    	cursor = model.getValues(FAVORITE_TABLE);
    	
    	if( cursor.getString(0).toString().equals("True") )
    	{
    		favoriteNumbers_Plan = true;
    	}
    	else
    	{
    		favoriteNumbers_Plan = false;
    	}
    	
    	if (favoriteNumbers_Plan)
    	{
    		temp_String += "\n"+"Favorite Plan:No";
    		
	    	if( cursor.getString(1).equals("5") )
	    	{
	    		favorite_5 = true;
	    	}
	    	else// if (cursor.getString(1).equals("10"))
	    	{
	    		favorite_10 = true;
	    	}

	    	favoriteNumber1 = cursor.getString(2).toString();
	    	favoriteNumber2 = cursor.getString(3).toString();
	    	favoriteNumber3 = cursor.getString(4).toString();
	    	favoriteNumber4 = cursor.getString(5).toString();
	    	favoriteNumber5 = cursor.getString(6).toString();
	    	
	    	if(favorite_10)
	    	{
		    	favoriteNumber6 = cursor.getString(7).toString();
		    	favoriteNumber7 = cursor.getString(8).toString();
		    	favoriteNumber8 = cursor.getString(9).toString();
		    	favoriteNumber9 = cursor.getString(10).toString();
		    	favoriteNumber10 = cursor.getString(11).toString();
		    	
		    	if( !(outgoingNumber.equals(favoriteNumber1)) || 
		    			outgoingNumber.equals(favoriteNumber2) ||
		    			outgoingNumber.equals(favoriteNumber3) ||
		    			outgoingNumber.equals(favoriteNumber4) ||
		    			outgoingNumber.equals(favoriteNumber5) ||
		    			outgoingNumber.equals(favoriteNumber6) ||
		    			outgoingNumber.equals(favoriteNumber7) ||
		    			outgoingNumber.equals(favoriteNumber8) ||
		    			outgoingNumber.equals(favoriteNumber9) ||
		    			outgoingNumber.equals(favoriteNumber10) )
		    	{
		    		temp_String += "\n"+"Is in Favorite 10:Yes";
		    		//startTimer();
		    	}
	    	}
	    	
	    	else if( !(outgoingNumber.equals(favoriteNumber1)) || 
		    			outgoingNumber.equals(favoriteNumber2) ||
		    			outgoingNumber.equals(favoriteNumber3) ||
		    			outgoingNumber.equals(favoriteNumber4) ||
		    			outgoingNumber.equals(favoriteNumber5) )
	    	{
	    		temp_String += "\n"+"Is in Favorite 5:Yes";
	    		//startTimer();
	    	}
	    	
    	}
    	
    	else
    	{
    		temp_String += "\n"+"Favorite Plan:No";
    	}
    	
    	temp_byteArray = temp_String.getBytes();
    	fos.write(temp_byteArray);
    	
    }
    
    public final Handler handler;
    public Runnable trackCallDuration; 
    
    public void startTimer()
    {
    	handler = new Handler ();
    	Runnable trackCallDuration = new Runnable()
    	{
    		public void run()
    		{
    			updateCallTimer();
    		}
    	};    
    }
    
    public void updateCallTimer()
    {
    	
    }
    
    
}