package com.example.bluetooth_jcg_1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private BluetoothAdapter myBluetoothAdapter;// to check if Bluetooth adapter is on or off
	private static final int RequestCode_EnableBlueTooth = 1;
	private static final String TAG = "mylog";
	private static TextView tv_BluetoothStatusHead; 
	private static final int REQUEST_ENABLE_BT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// to check if Bluetooth adapter is on or off
        tv_BluetoothStatusHead = (TextView) findViewById(R.id.tv_BluetoothStatusHead);
        updateBTadapterStatusInUI();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void checkBluetoothStatus(View view){//this function can be used if we just need to check the current status of blueTooth
    	Log.d(TAG, "entered function checkBluetoothStatus");
    	if(myBluetoothAdapter.isEnabled()){
    		Log.d(TAG, "in function checkBluetoothStatus(), Bluetooth found enabled");
    		Toast.makeText(getApplicationContext(), "Its ON",Toast.LENGTH_SHORT).show();
    	}else if(!myBluetoothAdapter.isEnabled()){
    		Log.d(TAG, "in function checkBluetoothStatus(), Bluetooth found disabled");
    		Toast.makeText(getApplicationContext(), "Its OFF",Toast.LENGTH_SHORT).show();
    	}else{
    		Log.d(TAG, "in function checkBluetoothStatus(), Bluetooth status unrecognized");
    		Toast.makeText(getApplicationContext(), "unknown state",Toast.LENGTH_SHORT).show();
    	}
    }
    
    public void switchOn(View view){
    	Log.d(TAG, "Entered switchOn() function");
    	if(!myBluetoothAdapter.isEnabled()){
    		Intent intent_turnOnBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    		startActivityForResult(intent_turnOnBlueTooth, RequestCode_EnableBlueTooth );
    		Log.d(TAG,"startedActvity for ONing Bluetooth");
    		tv_BluetoothStatusHead.setText("BlueTooth Status: turning on...");
	    }else{
	    	Toast.makeText(getApplicationContext(), "Already ON", Toast.LENGTH_SHORT).show();
	    }
    }
    
    public void switchOff(View view){
    	//this does not depend on onActivityResult() as its action does not depend on yes/no from the user ( on a pop up). but not sure
    	Log.d(TAG, "entered function switchOff()");
    	if(myBluetoothAdapter.isEnabled()){
    		Log.d(TAG, "in function switchOff(), Bluetooth found enabled. disabling...");
    		myBluetoothAdapter.disable();
    		tv_BluetoothStatusHead.setText("BlueTooth Status: off");//changed status directly as not dependant on onActivityResult()
    	}else{
    		Log.d(TAG, "in function switchOff(), Bluetooth found disabled already");
    		Toast.makeText(getApplicationContext(), "Already Off", Toast.LENGTH_SHORT).show();
    	}
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	//I think
    	Log.d(TAG, "entered onActivityResult with requestCode:"+requestCode+",resultCode:"+resultCode+",Intent:"+data);
    	if(requestCode == REQUEST_ENABLE_BT){
 		   if(myBluetoothAdapter.isEnabled()) {
 			  Log.d(TAG, "in onActivityResult & BT is enabled");
 			   tv_BluetoothStatusHead.setText("BlueTooth Status: Enabled");
 		   } else {   
 			  Log.d(TAG, "in onActivityResult & BT is disabled");
 			   tv_BluetoothStatusHead.setText("BlueTooth Status: Disabled");
 		   }
 	   }
    	
    }
    
    public void listDevicesInRange(View view){
    	Log.d(TAG, "entered the function listDevices");
    	if(!myBluetoothAdapter.isEnabled()){
    		Log.d(TAG, "in function listDevices, BT is off. exiting...");
    		//Bluetooth is off . tell the user to "switch on blueTooth 1st" and return
    		Toast.makeText(getApplicationContext(), "Switch on BlueTooth first", Toast.LENGTH_SHORT).show();
    		return;
    	}else{
    		Button button_discoverDevices = (Button) findViewById(R.id.button_discoverDevices);
        	if(myBluetoothAdapter.isDiscovering()){
        		Log.d(TAG, "in function listDevices, discovery cancelled by user");
        		// the button is pressed when it discovers, so cancel the discovery
        		myBluetoothAdapter.cancelDiscovery();
        		Toast.makeText(getApplicationContext(), "Scan cancelled", Toast.LENGTH_SHORT);
        	}else{
        		Log.d(TAG, "in function listDevices, strting discovery");
        		myBluetoothAdapter.startDiscovery();
//        		button_discoverDevices.setText("Searching...");
        	}
    	}
    }
    private void updateBTadapterStatusInUI() {
    	Log.d(TAG, "entered the function update BTadapterStatusInUI()");
    	if(myBluetoothAdapter.isEnabled()){
    		Log.d(TAG, "in function BTadapterStatusInUI(), Bluetooth found enabled");
    		tv_BluetoothStatusHead.setText("BlueTooth Status: Enabled");
    	}else if(!myBluetoothAdapter.isEnabled()){
    		Log.d(TAG, "in function BTadapterStatusInUI(), Bluetooth found disabled");
    		tv_BluetoothStatusHead.setText("BlueTooth Status: Disabled");
    	}else{
    		Log.d(TAG, "in function BTadapterStatusInUI(), Bluetooth status unrecognized");
    		tv_BluetoothStatusHead.setText("BlueTooth Status: Unknown State");
    	}
	}
}
