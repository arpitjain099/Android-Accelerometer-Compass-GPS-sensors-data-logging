//currently working code
package com.example.jadoo;
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

import com.example.jadoo.*;



public class MainActivity extends Activity implements SensorEventListener{
    // GUI controls
    EditText txtData;
    Button btnWriteSDFile;
    Button btnReadSDFile;
    Button btnClearScreen;
    Button btnClose;
    private SensorManager sensorManager;
    Float azimut;  
    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
  //SensorManager lets you access the device's sensors
   
      private View view;
      private long lastUpdate;
    // define the display assembly compass picture
   

        // record the compass picture angle turned
        private float currentDegree = 0f;

        // device sensor manager
        
        String rootPath;
        String globaltimestamp;
        String gpsRootpath;

        long endtime;
    
     

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    globaltimestamp=String.valueOf(System.currentTimeMillis());
    //final File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/gps"+globaltimestamp+".txt");
    txtData = (EditText) findViewById(R.id.duration);
    
   // rootPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/accel"+globaltimestamp+"/";
    String rootPath2=Environment.getExternalStorageDirectory().getAbsolutePath()+"/accel"+globaltimestamp+"/";
    File file=new File(rootPath2);
    if(!file.exists()){
    file.mkdirs();
    }
    String gpsRootpath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/gps"+globaltimestamp+"/";
     file=new File(gpsRootpath);
    if(!file.exists()){
    file.mkdirs();
    }
   String compassRootpath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass"+globaltimestamp+"/";
    file=new File(compassRootpath);
   if(!file.exists()){
   file.mkdirs();
   }

    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    lastUpdate = System.currentTimeMillis();
  
    // TextView that will tell the user what degree is he heading
   
    //String rootPath_new=Environment.getExternalStorageDirectory().getAbsolutePath()+"/accel"+globaltimestamp+"/";
    //File file_new=new File(rootPath_new);
//if(!file_new.exists()){
//file_new.mkdirs();
//}

    // initialize your android device sensor capabilities
    mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

     
        lastUpdate = System.currentTimeMillis();
    //txtData.setHint("Enter some lines of data here...");
 //       String rootPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass"+globaltimestamp+"/";
        
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
          accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
    
    
    // bind GUI elements with local controls
    
    btnWriteSDFile = (Button) findViewById(R.id.btnWriteSDFile);
    btnWriteSDFile.setOnClickListener(new OnClickListener() {
        
        
    public void onClick(View v) {
        // write on SD card file data in the text box
        try {
            btnWriteSDFile.setEnabled(false);   
            
    
            
         
            

    		//String temp=txtData.getText().toString();
    		
    		//int duration=Integer.parseInt(temp);
            String rootPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/gps"+globaltimestamp+"/";
            File file=new File(rootPath);
    if(!file.exists()){
    file.mkdirs();
    }
         //int timeofclick= (int)System.currentTimeMillis()/1000;
    

         Timer timer = new Timer();
       	TimerTask tt = new TimerTask(){
       		@Override
       		public void run(){
        //Thread.sleep(1000);
        File myFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/gps"+globaltimestamp+"/"+String.valueOf(System.currentTimeMillis()/1000) +".txt");
        FileOutputStream fOut = null;
        try {
          myFile.createNewFile();
      } catch (IOException e) {
          // TODO Auto-generated catch block
          
          e.printStackTrace();
      }
      try {
          fOut = new FileOutputStream(myFile);
      } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          
          e.printStackTrace();
      }
      
      OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
      try {
    	  java.util.Date date= new java.util.Date();
          final Timestamp a= new Timestamp(date.getTime());
          gpstracker gps = new gpstracker(MainActivity.this);
           double latitude = gps.getLatitude();
           double longitude = gps.getLongitude();
        myOutWriter.append(a.toString()+","+String.valueOf(latitude) +","+String.valueOf(longitude)+"\n");
			
          myOutWriter.close();
          fOut.close();
      } catch (IOException e) {
          // TODO Auto-generated catch block
          
          e.printStackTrace();
      }
      
       		}  
    };
    

  	timer.schedule(tt, 1,1000);//	delay the task 1 second, and then run task every five seconds
    
    
    
    
    
   
            
            
    
        

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
         sensorManager.registerListener(this,
        	        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        	        SensorManager.SENSOR_DELAY_NORMAL);
                // Register the sensor listeners
                mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
                mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
              
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    //can be safely ignored for this demo
        }

    float[] mGravity;
    float[] mGeomagnetic;
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
 	          String rootPath3=Environment.getExternalStorageDirectory().getAbsolutePath()+"/accel"+globaltimestamp+"/"+String.valueOf((System.currentTimeMillis())/1000)+".txt";
 	          File file3=new File(rootPath3);
 	          if(!file3.exists()){   
 	          
 	          java.util.Date date= new java.util.Date();
 				final Timestamp a= new Timestamp(date.getTime());
 				
 				File myFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/accel"+ globaltimestamp+"/"+String.valueOf(System.currentTimeMillis()/1000)+".txt");
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
 				myOutWriter.append(a.toString()+ ","+ String.valueOf(x)+"," +String.valueOf(y)+","+String.valueOf(z));
 				myOutWriter.close();
 				fOut.close();
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				Toast.makeText(this, "line112", Toast.LENGTH_SHORT)
 		         .show();
 				e.printStackTrace();
 			}
 	      	
 				

 			
 			Toast.makeText(getBaseContext(),
 					"Done writing SD 'mysdfile.txt'",
 					Toast.LENGTH_SHORT).show();
 	        }}
 	    }

    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values.clone();
          if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values.clone();
          if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            /*Computes the inclination matrix I as well as the rotation matrix R transforming a vector from the device coordinate
             *  system to the world's coordinate system which is defined as a direct orthonormal basis*/
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
              float orientation[] = new float[3];
              /*Computes the device's orientation based on the rotation matrix*/
              SensorManager.getOrientation(R, orientation);
              azimut = orientation[0]; // orientation contains: azimut, pitch and roll
              azimut = (float)Math.toDegrees(orientation[0]);
              azimut=(float) ((azimut+ 360.0) % 360.0);
              
              java.util.Date date= new java.util.Date();
              final Timestamp a= new Timestamp(date.getTime());
              String rootPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass"+globaltimestamp+"/";
              File file=new File(rootPath);
      if(!file.exists()){
      file.mkdirs();
      }

      String rootPath2=Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass"+globaltimestamp+"/";
        File file2=new File(rootPath2);
        if(file2.isDirectory()){
            
            if(file2.list().length==0){        
      File myFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass"+globaltimestamp+"/"+String.valueOf(System.currentTimeMillis()/1000) +".txt");
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
              myOutWriter.append(a.toString()+","+String.valueOf(azimut));
              //myOutWriter.append("  "+a.toString()+" newsample: "+String.valueOf(-azimut*360/(2*3.14159f)));
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
                
               
            
                String rootPath3=Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass"+globaltimestamp+"/"+String.valueOf((System.currentTimeMillis()-1000)/1000)+".txt";
                File file3=new File(rootPath3);
                if(file3.exists()){
                    File myFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/compass"+globaltimestamp+"/"+String.valueOf(System.currentTimeMillis()/1000) +".txt");
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
                      myOutWriter.append(a.toString()+","+String.valueOf(azimut));
                //      myOutWriter.append("  "+a.toString()+" newsample: "+String.valueOf(-azimut*360/(2*3.14159f)));
                      myOutWriter.close();
                      fOut.close();
                  } catch (IOException e) {
                      // TODO Auto-generated catch block
                      Toast.makeText(this, "line112", Toast.LENGTH_SHORT)
                       .show();
                      e.printStackTrace();
                  }
                  
                      

                  
                    
                }
                
                
            }
            }
          }
         
        }
              
          
          }
    
    
        
    }

// AndSDcard
