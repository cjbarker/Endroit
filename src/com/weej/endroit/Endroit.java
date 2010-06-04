package com.weej.endroit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Endroit extends Activity implements OnClickListener 
{
	// static vars
	private static final String TAG = "Endroit";
	
	// member vars
	private Button buttonStart, buttonStop;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    buttonStart = (Button) findViewById(R.id.buttonStart);
	    buttonStop = (Button) findViewById(R.id.buttonStop);

	    buttonStart.setOnClickListener(this);
	    buttonStop.setOnClickListener(this);
	    
	    Log.d(TAG, "Activity created");
	}
	
	public void onClick(View src) {
		switch (src.getId()) {
		    case R.id.buttonStart:
		      Log.d(TAG, "onClick: starting srvice");
		      startService(new Intent(this, EndroitService.class));
		      break;
		    case R.id.buttonStop:
		      Log.d(TAG, "onClick: stopping srvice");
		      stopService(new Intent(this, EndroitService.class));
		      break;
		}
	}
}