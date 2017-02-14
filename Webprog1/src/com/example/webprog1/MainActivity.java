package com.example.webprog1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class MainActivity extends Activity {
	
	WebView wv;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.wv=(WebView) this.findViewById(R.id.webView1);
        
        this.wv.loadUrl("http://ccs.uc.edu.ph");
    }

}
