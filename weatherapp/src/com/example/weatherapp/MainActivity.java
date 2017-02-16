package com.example.weatherapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	EditText txtcity;
	ImageView iv;
	TextView weathertype,cityName,temp,humidity;
	ImageButton btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       this.iv=(ImageView) this.findViewById(R.id.imageView1);
       this.weathertype=(TextView) this.findViewById(R.id.textView1);
       this.cityName=(TextView) this.findViewById(R.id.textView2);
       this.temp=(TextView) this.findViewById(R.id.textView3);
       this.humidity=(TextView) this.findViewById(R.id.textView4);
       this.btnImage=(ImageButton) this.findViewById(R.id.imageButton1);
       this.txtcity=(EditText) this.findViewById(R.id.editText1);
       
       this.btnImage.setOnClickListener(this);
    }
	@Override
	public void onClick(View arg0) {
		   String mycity=txtcity.getText().toString();
	       StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
	       StrictMode.setThreadPolicy(policy);
		
       try {
		URL url = new URL ("http://api.openweathermap.org/data/2.5/weather?q="+mycity+"&appid=83f0b3ffb2f313fb10dc3b02ed8e3f8f");
	    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
	    StringBuffer sb=new StringBuffer();
	    InputStream is=conn.getInputStream();
	    int c=0;
	    while((c=is.read())!=-1){
	    	sb.append((char)c);
	  
	    }
	    is.close();
	    conn.disconnect();

	    
	    JSONObject json=new JSONObject(sb.toString());
	   JSONArray weather=json.getJSONArray("weather");
	   JSONObject desc=weather.getJSONObject(0).getJSONObject("weather");
	    String ds=desc.getString("description");
	    String icon=desc.getString("icon");
	    
	    ///retrieve the weather icon from the server
	    url=new URL("http://openweathermap.org/img/w"+icon+".png");
	    conn=(HttpURLConnection) url.openConnection();
	    is=conn.getInputStream();
	    
       Bitmap bmp=BitmapFactory.decodeStream(is);
       this.iv.setImageBitmap(bmp);
       
	    is.close();
	    conn.disconnect();
	
	    this.weathertype.setText(ds);
       
	    String city=json.getString("name");
	    JSONObject main=json.getJSONObject("main");
	    String temp=main.getString("temp");
	    String hum=main.getString("humidity");
	    
	    double celsius=Double.parseDouble(temp)-272.3;
	    
	    this.cityName.setText(city);
	  
	    this.temp.setText(String.format("%2", celsius)+"°C");
	    this.humidity.setText(hum+ "%");
	    
	  
       
       } catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }


}
