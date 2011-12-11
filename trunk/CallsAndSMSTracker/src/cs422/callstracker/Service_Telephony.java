package cs422.callstracker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Service_Telephony extends Service{
	
	public void onCreate(){
		
	}
	
	public IBinder onBind(Intent intent){
		return null;
	}

	public int onStartCommand(Intent intent,int flags,int startId) {
		if ((flags & START_FLAG_RETRY) == 0){
			//TODO
		}
		else{
			//TODO
		}
		return Service.START_STICKY;
	}
	
}
