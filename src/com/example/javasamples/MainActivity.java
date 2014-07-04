package com.example.javasamples;
import java.io.*;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Environment;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.*;
import com.example.javasamples.*;


public class MainActivity extends Activity implements SensorEventListener{
	// GUI controls
	EditText txtData;
	Button btnWriteSDFile;
	Button btnReadSDFile;
	Button btnClearScreen;
	Button btnClose;
	private SensorManager sensorManager;
	
	  private View view;
	  private long lastUpdate;
	// define the display assembly compass picture
	    private ImageView image;

	    // record the compass picture angle turned
	    private float currentDegree = 0f;

	    // device sensor manager
	    private SensorManager mSensorManager;

	    TextView tvHeading;

	
	
	 

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	final File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/xfile.txt");
	txtData = (EditText) findViewById(R.id.duration);
    image = (ImageView) findViewById(R.id.imageViewCompass);

    // TextView that will tell the user what degree is he heading
    tvHeading = (TextView) findViewById(R.id.tvHeading);

    // initialize your android device sensor capabilities
    mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

	 sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    lastUpdate = System.currentTimeMillis();
	//txtData.setHint("Enter some lines of data here...");
	try {
		myFile.createNewFile();
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	FileInputStream fIn=null;
	try {
		fIn = new FileInputStream(myFile);
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
	
	
	// bind GUI elements with local controls
	
	btnWriteSDFile = (Button) findViewById(R.id.btnWriteSDFile);
	btnWriteSDFile.setOnClickListener(new OnClickListener() {
		
		
	public void onClick(View v) {
		// write on SD card file data in the text box
		try {
			btnWriteSDFile.setEnabled(false);	
			
			FileOutputStream fOut = new FileOutputStream(myFile);
			final OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);
			
			
		
		//timer = new Timer();
	
		
		String temp=txtData.getText().toString();
		
		int duration=Integer.parseInt(temp);
	   // timer.schedule(new RemindTask(), 2000,2000);
//			  Thread thread = new Thread(runnable);
	//		  thread.start();
		
		 
		  /*while(curr/1000 < prev/1000 + 30){
			  curr=System.currentTimeMillis();
			  if(curr/1000-temp/1000==incr){
				  java.util.Date date= new java.util.Date();
					final Timestamp a= new Timestamp(date.getTime());
					final double latitude = gps.getLatitude();
		            final double longitude = gps.getLongitude();
				  
				  
				  myOutWriter.append(a.toString()+"lat:"+String.valueOf(latitude) +"long:"+String.valueOf(longitude)+"lastX:"+ String.valueOf(mLastX)+"lastY"+String.valueOf(mLastY)+"lastZ"+String.valueOf(mLastZ)+"degree:"+String.valueOf(currentDegree));
			  incr++;}	
		  }*/
		int i;
		for(i=0;i<duration;i++){
			Thread.sleep(1000);
			java.util.Date date= new java.util.Date();
			final Timestamp a= new Timestamp(date.getTime());
			gpstracker gps = new gpstracker(MainActivity.this);
			final double latitude = gps.getLatitude();
            final double longitude = gps.getLongitude();
		  myOutWriter.append(a.toString()+"lat:"+String.valueOf(latitude) +"long:"+String.valueOf(longitude)+"\n");
		  
		}
	    
		

			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(),
					"Done writing SD 'mysdfile.txt'",
					Toast.LENGTH_SHORT).show();
			
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	
	}// onClick
	}); // btnWriteSDFile

		

		

		btnClose = (Button) findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// clear text box
				finish();
			}
		}); // btnClose

	}// onCreate
	@Override
	protected void onPause() {
		 super.onPause();
		   // sensorManager.unregisterListener(this);
		 //   mSensorManager.unregisterListener(this);
	}
	
	@Override
	protected void onDestroy() {
		 super.onPause();
		    sensorManager.unregisterListener(this);
		    mSensorManager.unregisterListener(this);
	}
	
	@Override
    protected void onResume() {
		 super.onResume();
		    // register this class as a listener for the orientation and
		    // accelerometer sensors
	        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
	                SensorManager.SENSOR_DELAY_GAME);

		    sensorManager.registerListener(this,
		        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		        SensorManager.SENSOR_DELAY_NORMAL);
    }
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	//can be safely ignored for this demo
		}

	private void getAccelerometer(SensorEvent event) {
	    float[] values = event.values;
	    // Movement
	    float x = values[0];
	    float y = values[1];
	    float z = values[2];

	    float accelationSquareRoot = (x * x + y * y + z * z)
	        / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
	    long actualTime = event.timestamp;
	    if (accelationSquareRoot >= 2) //
	    {
	      if (actualTime - lastUpdate < 200) {
	        return;
	      }
	      lastUpdate = actualTime;
	      //global_x=x;global_y=y;global_z=z;
	     	    }
	  }
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	        float[] values = event.values;
	        // Movement
	        float x = values[0];
	        float y = values[1];
	        float z = values[2];

	        float accelationSquareRoot = (x * x + y * y + z * z)
	            / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
	        long actualTime = event.timestamp;
	        if (accelationSquareRoot >= 2) //
	        {
	          if (actualTime - lastUpdate < 200) {
	            return;
	          }
	          lastUpdate = actualTime;
	          Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
	              .show();
	        
	        
			 
				// TODO Auto-generated catch block
	          
	          
	          java.util.Date date= new java.util.Date();
				final Timestamp a= new Timestamp(date.getTime());
				String rootPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/accel/";
	            File file=new File(rootPath);
	if(!file.exists()){
	file.mkdirs();
	}
				File myFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/accel/"+String.valueOf(System.currentTimeMillis()+".txt"));
	          FileOutputStream fOut = null;
	          try {
	      		myFile.createNewFile();
	      	} catch (IOException e) {
	      		// TODO Auto-generated catch block
	      	     Toast.makeText(this, "problem in file creation", Toast.LENGTH_SHORT)
	               .show();
	      		e.printStackTrace();
	      	}
			try {
				fOut = new FileOutputStream(myFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Toast.makeText(this, "line101", Toast.LENGTH_SHORT)
		         .show();
				e.printStackTrace();
			}
			
	      	OutputStreamWriter myOutWriter = 
	      							new OutputStreamWriter(fOut);
	      	try {
				myOutWriter.append(a.toString()+ " X : "+ String.valueOf(x)+" Y: " +String.valueOf(y)+" Z: "+String.valueOf(z));
				myOutWriter.close();
				fOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Toast.makeText(this, "line112", Toast.LENGTH_SHORT)
		         .show();
				e.printStackTrace();
			}
	      	
				

			
			Toast.makeText(getBaseContext(),
					"Done writing SD 'accelerometer.txt'",
					Toast.LENGTH_SHORT).show();
	        }
	    }
		
		
		
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree, 
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f, 
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
        
        
        
        
        java.util.Date date= new java.util.Date();
  				final Timestamp a= new Timestamp(date.getTime());
  				String rootPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass/";
  	            File file=new File(rootPath);
  	if(!file.exists()){
  	file.mkdirs();
  	}
  	
		String rootPath2=Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass/";
          File file2=new File(rootPath);
          if(file.isDirectory()){
        	  
        	  if(file.list().length==0){		
       File myFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass/"+String.valueOf(System.currentTimeMillis()/1000) +".txt");
	          FileOutputStream fOut = null;
	          try {
	      		myFile.createNewFile();
	      	} catch (IOException e) {
	      		// TODO Auto-generated catch block
	      	     Toast.makeText(this, "problem in file creation", Toast.LENGTH_SHORT)
	               .show();
	      		e.printStackTrace();
	      	}
			try {
				fOut = new FileOutputStream(myFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Toast.makeText(this, "line101", Toast.LENGTH_SHORT)
		         .show();
				e.printStackTrace();
			}
			
	      	OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	      	try {
				myOutWriter.append(a.toString()+" degree: "+String.valueOf(degree));
				myOutWriter.append("  "+a.toString()+" newsample: "+String.valueOf(currentDegree-degree));
				myOutWriter.close();
				fOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Toast.makeText(this, "line112", Toast.LENGTH_SHORT)
		         .show();
				e.printStackTrace();
			}
	      	
				

			
			Toast.makeText(getBaseContext(),
					"Done writing SD 'compass.txt'",
					Toast.LENGTH_SHORT).show();
			
        	  }
        	  else{
        		  
        		 
        	  
        		  String rootPath3=Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass/"+String.valueOf((System.currentTimeMillis()-1000)/1000)+".txt";
                  File file3=new File(rootPath);
                  if(file.exists()){
                      File myFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass/"+String.valueOf(System.currentTimeMillis()/1000) +".txt");
        	          FileOutputStream fOut = null;
        	          try {
        	      		myFile.createNewFile();
        	      	} catch (IOException e) {
        	      		// TODO Auto-generated catch block
        	      	     Toast.makeText(this, "problem in file creation", Toast.LENGTH_SHORT)
        	               .show();
        	      		e.printStackTrace();
        	      	}
        			try {
        				fOut = new FileOutputStream(myFile);
        			} catch (FileNotFoundException e) {
        				// TODO Auto-generated catch block
        				Toast.makeText(this, "line101", Toast.LENGTH_SHORT)
        		         .show();
        				e.printStackTrace();
        			}
        			
        	      	OutputStreamWriter myOutWriter = 
        	      							new OutputStreamWriter(fOut);
        	      	try {
        				myOutWriter.append(a.toString()+" degree: "+String.valueOf(degree));
        				myOutWriter.append("  "+a.toString()+" newsample: "+String.valueOf(currentDegree-degree));
        				myOutWriter.close();
        				fOut.close();
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				Toast.makeText(this, "line112", Toast.LENGTH_SHORT)
        		         .show();
        				e.printStackTrace();
        			}
        	      	
        				

        			
        			Toast.makeText(getBaseContext(),
        					"Done writing SD 'compass.txt'",
        					Toast.LENGTH_SHORT).show();
                	  
                  }
        		  
        		  
        	  }
        	  
          
          }
  	
  	
  		
	}

}// AndSDcard
