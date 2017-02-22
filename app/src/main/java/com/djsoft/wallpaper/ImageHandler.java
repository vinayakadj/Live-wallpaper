package com.djsoft.wallpaper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageHandler implements Runnable{
	
	
	private String LogString = "Taj wallpaper";
	int imageId;
	Resources res;
	int reqWidth;
	int reqHeight;
	Boolean isLoaded=false;
	Bitmap image = null;
	public ImageHandler(Resources res,int imageId,int reqWidth,int reqHeight) {
	       // store parameter for later user
		this.imageId =imageId;
		this.res =res;
		this.reqWidth = reqWidth;
		this.reqHeight =reqHeight;
		
	   }

	public ImageHandler() {
		// TODO Auto-generated constructor stub
	}

	public Boolean IsLoaded()
	{
		
		Log.d(LogString,"ImageHandler:loadImage():isLoaded()():"+isLoaded);
		return isLoaded;
		
	}
	
	public void SetParams(Resources res,int imageId,int reqWidth,int reqHeight) {
	       // store parameter for later user
		this.imageId =imageId;
		this.res =res;
		this.reqWidth = reqWidth;
		this.reqHeight =reqHeight;
		
	   }

	public Bitmap GetImage(){
		isLoaded = false;
		
		return this.image;
		
	}
	public Bitmap loadImage(Resources res,int imageId,int reqWidth,int reqHeight) {

		Bitmap image = null;	
		try{
		Log.d(LogString,"ImageHandler:loadImage()");
		

		image = BitmapFactory.decodeResource(res, imageId);
		if (reqHeight > 0 && reqWidth > 0) {

			image = Bitmap.createScaledBitmap(image, reqWidth,
					reqHeight, false);
		}

		isLoaded =true;
		}
		catch(Exception e){
			Log.d(LogString,"ImageHandler:loadImage():failed:");
		}
		
		return image;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Log.d(LogString,"ImageHandler:run");
		this.image = loadImage(res, imageId, reqWidth, reqHeight);
		
	}

}
