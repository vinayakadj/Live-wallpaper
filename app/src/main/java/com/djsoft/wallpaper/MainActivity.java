package com.djsoft.wallpaper;


import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import 	android.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

 public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void onClick(View view) {
    	Intent i = new Intent();

    	if(Build.VERSION.SDK_INT >= 16)
    	{
    	    i.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
    	    i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, 
    	    		new ComponentName(this, MywallpaperService.class));
    	}
    	else
    	{
    	    i.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
    	}
//    	  Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
//    	  intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
//    	    new ComponentName(this, MywallpaperService.class));
   	  startActivity(i);
    	} 


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


     /**
      * This class makes the ad request and loads the ad.
      */
     public static class AdFragment extends Fragment {

         private AdView mAdView;

         public AdFragment() {
         }

         @Override
         public void onActivityCreated(Bundle bundle) {
             super.onActivityCreated(bundle);

             // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
             // values/strings.xml.
             mAdView = (AdView) getView().findViewById(R.id.adView);

             // Create an ad request. Check logcat output for the hashed device ID to
             // get test ads on a physical device. e.g.
             // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
             AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                     .build();
 //            AdRequest adRequest =
 //                    new AdRequest.Builder().addTestDevice("ABCDEF012345").build();

             // Start loading the ad in the background.
             mAdView.loadAd(adRequest);
         }

         @Override
         public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
             return inflater.inflate(R.layout.fragment_ad, container, false);
         }

         /** Called when leaving the activity */
         @Override
         public void onPause() {
             if (mAdView != null) {
                 mAdView.pause();
             }
             super.onPause();
         }

         /** Called when returning to the activity */
         @Override
         public void onResume() {
             super.onResume();
             if (mAdView != null) {
                 mAdView.resume();
             }
         }

         /** Called before the activity is destroyed */
         @Override
         public void onDestroy() {
             if (mAdView != null) {
                 mAdView.destroy();
             }
             super.onDestroy();
         }

     }
}

