package com.djsoft.wallpaper;

/**
 * Created by Manjunath on 10/13/13.
 */
import java.util.Calendar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MywallpaperService extends WallpaperService {

	float x1, x2, y1, y2, dx, dy;
	int direction;
	Calendar cal;
	Boolean firsttime = true;
	private String LogString = "Taj wallpaper";

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Engine onCreateEngine() {

		return new MyWallpaperServiceEngine();
	}

	public class MyWallpaperServiceEngine extends Engine {

		public static final int RIGHT = 1;
		public static final int LEFT = 2;
		public static final int UP = 3;
		public static final int DOWN = 4;

		public static final int MORNGING = 0;
		public static final int AFTERNOON = 1;
		public static final int EVENING = 2;
		public static final int NIGHT = 3;

		public static final int START = 0;
		public static final int PLAYING = 1;
		public static final int STOP = 2;
		public static final int LAST = 3;

		private final Handler handler = new Handler();
		int BirdState = START;
		int crackerState = STOP;
		int dayState = MORNGING;
		Boolean dayStateChanged = true;
		Boolean Birdflyup =false;
		GestureDetector gestureDetector;

		private final Runnable drawRunnerbirds = new Runnable() {
			@Override
			public void run() {
				drawbird();
			}
		};

		private void drawbird() {

			BirdState = START;

			handler.removeCallbacks(drawRunnerbirds);
			if (visible) {
				handler.postDelayed(drawRunnerbirds, 100000); // delay 10
				// mileseconds
			}

		}

		public Bitmap loadImage(int imageId) {

			Bitmap image = null;

			image = BitmapFactory.decodeResource(getResources(), imageId);

			if (ScreenHeight > 0 && ScreenWidth > 0) {
				image = Bitmap.createScaledBitmap(image, ScreenWidth,
						ScreenHeight, false);
			}

			return image;
		}

		private final Runnable drawRunnerTajmahal = new Runnable() {
			@Override
			public void run() {
				draw();
			}
		};
		private boolean visible = true;
		public Bitmap imageTajmahal, backgroundImage, imagebird[];
		ImageHandler[] imgHandler;
		ImageHandler[] imgBGHandler;
		Boolean mIsImageloaded = false;
		Boolean mIsImageBGloaded = false;

		public Bitmap CrackerImage[];

		int ImageWidth;
		int ImageHeight;
		int ImageBirdHeight;
		int ImageBirdWidth;
		int ScreenWidth;
		int ScreenHeight;
		int tajXpos = 0;
		int tajYpos = 0;
		int BgXpos = 0;
		int BgYpos = 0;
		int birdImgIndex = 0;
		int CrackerImgIndex;
		int birdXpos = 0;
		int birdYpos = 70;
		int delay;
		int delay2;
		private long lastUpdateTime;
		private long currentTime;

		MyWallpaperServiceEngine() {

			gestureDetector = new GestureDetector(getApplicationContext(),
					mGestureListener);
			imagebird = new Bitmap[7];
			CrackerImage = new Bitmap[10];

			DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
			ScreenHeight = metrics.heightPixels;
			ScreenWidth = metrics.widthPixels;

			// get the fish and background image references

			imageTajmahal = loadImage(R.drawable.morning);

			imgHandler = new ImageHandler[4];
			imgBGHandler = new ImageHandler[4];

			for (int i = 0; i < 4; i++) {
				imgHandler[i] = new ImageHandler();
				imgBGHandler[i] = new ImageHandler();

			}

			imgHandler[MORNGING].SetParams(getResources(), R.drawable.morning,
					ScreenWidth, ScreenHeight);
			imgHandler[AFTERNOON].SetParams(getResources(),
					R.drawable.afternoon, ScreenWidth, ScreenHeight);
			imgHandler[EVENING].SetParams(getResources(), R.drawable.evening,
					ScreenWidth, ScreenHeight);
			imgHandler[NIGHT].SetParams(getResources(), R.drawable.night,
					ScreenWidth, ScreenHeight);

			imgBGHandler[MORNGING].SetParams(getResources(),
					R.drawable.morningbackground, ScreenWidth, ScreenHeight);
			imgBGHandler[AFTERNOON].SetParams(getResources(),
					R.drawable.afternoonbackground, ScreenWidth, ScreenHeight);
			imgBGHandler[EVENING].SetParams(getResources(),
					R.drawable.eveningbackground, ScreenWidth, ScreenHeight);
			imgBGHandler[NIGHT].SetParams(getResources(),
					R.drawable.nightbackground, ScreenWidth, ScreenHeight);

			ImageWidth = imageTajmahal.getWidth();
			ImageHeight = imageTajmahal.getHeight();

			tajXpos = ScreenWidth / 2 - ImageWidth / 2;
			// tajYpos = ScreenHeight / 2 - ImageHeight / 2;
			tajYpos = ScreenHeight - ImageHeight;
			tajYpos = Math.abs(tajYpos);

			backgroundImage = loadImage(R.drawable.morningbackground);

			imagebird[0] = BitmapFactory.decodeResource(getResources(),
					R.drawable.bird1);
			imagebird[1] = BitmapFactory.decodeResource(getResources(),
					R.drawable.bird2);
			imagebird[2] = BitmapFactory.decodeResource(getResources(),
					R.drawable.bird3);
			imagebird[3] = BitmapFactory.decodeResource(getResources(),
					R.drawable.bird4);
			imagebird[4] = BitmapFactory.decodeResource(getResources(),
					R.drawable.bird5);
			imagebird[5] = BitmapFactory.decodeResource(getResources(),
					R.drawable.bird6);
			imagebird[6] = BitmapFactory.decodeResource(getResources(),
					R.drawable.bird7);

			ImageBirdHeight = imagebird[0].getHeight();
			ImageBirdWidth= imagebird[0].getWidth();
			ImageWidth = backgroundImage.getWidth();
			ImageHeight = backgroundImage.getHeight();
			BgXpos = ScreenWidth / 2 - ImageWidth / 2;
			BgYpos = ScreenHeight / 2 - ImageHeight / 2;

			for (int i = 0; i < 10; i++) {
				String url = "drawable/" + "cracker1" + (i);
				int imageKey = getResources().getIdentifier(url, "drawable",
						getPackageName());
				CrackerImage[i] = BitmapFactory.decodeResource(getResources(),
						imageKey);
			}

		}

		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;

			// if screen wallpaper is visible then draw the image otherwise do
			// not draw
			Log.d(LogString, "OnVisiblityChnaged :" + visible);
			if (visible) {
				// BirdState = START;
				handler.post(drawRunnerTajmahal);
				// handler.post(drawRunnerbirds);

			} else {
				handler.removeCallbacks(drawRunnerTajmahal);
				// handler.removeCallbacks(drawRunnerbirds);
			}
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawRunnerTajmahal);
			// handler.removeCallbacks(drawRunnerbirds);

		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			// Toast.makeText(getApplicationContext(),
			// "width:" + width + "height:" + height, Toast.LENGTH_SHORT)
			// .show();
		}

		// @TargetApi(Build.VERSION_CODES.FROYO)
		@Override
		public void onTouchEvent(MotionEvent event) {
			
			Log.d(LogString,"onTouchEvent");
			float eventX = event.getX();
			float eventY = event.getY();
			
			if(BirdState == PLAYING){
				int maxx=30;
				int maxy =30;
				float diffx =Math.abs(eventX-(birdXpos+ImageBirdWidth/2));
				float diffy =Math.abs(eventY-(birdYpos+ImageBirdHeight/2));
				int diffintx = (int)diffx;
				int diffinty = (int)diffy;
				Log.d(LogString, "onTouchEvent(): diffintx"+diffintx);
				Log.d(LogString, "onTouchEvent(): diffinty"+diffinty);
				Log.d(LogString, "onTouchEvent(): maxx:"+maxx);
				Log.d(LogString, "onTouchEvent(): maxy:"+maxy);
				if(diffintx < maxx &&
						diffinty < maxx ){
					Birdflyup = true;
					Log.d(LogString, "onTouchEvent(): Birdflyup");
				}
			}
			
			gestureDetector.onTouchEvent(event);
			

			/*
			 * // get pointer index from the event object int pointerIndex =
			 * event.getActionIndex();
			 * 
			 * // get pointer ID int pointerId =
			 * event.getPointerId(pointerIndex);
			 * 
			 * // get masked (not specific to a pointer) action int maskedAction
			 * = event.getActionMasked();
			 * 
			 * switch (maskedAction) {
			 * 
			 * case MotionEvent.ACTION_DOWN: case
			 * MotionEvent.ACTION_POINTER_DOWN: { // TODO use data x1 =
			 * event.getX(); y1 = event.getY();
			 * 
			 * break; } case MotionEvent.ACTION_MOVE: { // a pointer was moved
			 * // TODO use data x2 = event.getX(); y2 = event.getY(); dx = x2 -
			 * x1; dy = y2 - y1;
			 * 
			 * // Use dx and dy to determine the direction if (Math.abs(dx) >
			 * Math.abs(dy)) { if (dx > 0) { direction = RIGHT;
			 * //Toast.makeText(getApplicationContext(), "RIGHT", //
			 * Toast.LENGTH_SHORT).show(); } else { direction = LEFT; //
			 * Toast.makeText(getApplicationContext(), // "LEFT",
			 * Toast.LENGTH_SHORT).show(); } } else { if (dy > 0) { direction =
			 * DOWN; // Toast.makeText(getApplicationContext(), // "DOWN",
			 * Toast.LENGTH_SHORT).show(); } else { direction = UP; //
			 * Toast.makeText(getApplicationContext(), // "UP",
			 * Toast.LENGTH_SHORT).show(); } }
			 * 
			 * x1 = x2; // added y1 = y2;// added for right left correct
			 * movement break; } case MotionEvent.ACTION_UP: case
			 * MotionEvent.ACTION_POINTER_UP: case MotionEvent.ACTION_CANCEL: {
			 * // TODO use data break; } } draw(); //
			 * Toast.makeText(getApplicationContext(), // "ontouch",
			 * Toast.LENGTH_SHORT).show();
			 */
		}

		private final GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}

			// event when double tap occurs

			@Override
			public boolean onDoubleTap(MotionEvent e) {
				float x = e.getX();
				float y = e.getY();

				handler.post(drawRunnerTajmahal);

				Log.d("Double Tap", "Tapped at: (" + x + "," + y + ")");
				if (dayState == NIGHT) {
					crackerState = START;
					delay = 10;
					handler.postDelayed(drawRunnerTajmahal, delay);
				} else {
					if (BirdState != PLAYING) {
						BirdState = START;
						delay = 10;
						lastUpdateTime = System.currentTimeMillis();
						handler.postDelayed(drawRunnerTajmahal, delay);
					}

				}
				return true;
			}
		};

		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			super.onOffsetsChanged(xOffset, yOffset, xStep, yStep, xPixels,
					yPixels);

			// draw();
			// Toast.makeText(getApplicationContext(),
			// "xOffset:"+xOffset+"yOffset"+yOffset+"xStep"+xStep+"yStep"+yStep,
			// Toast.LENGTH_SHORT).show();
			// Toast.makeText(getApplicationContext(),
			// "xPixels:"+xPixels+"yPixels"+yPixels, Toast.LENGTH_SHORT).show();
		}

		void drawbirdAnimate(Canvas c, float x, float y) {

			c.drawBitmap(imagebird[birdImgIndex], x, y, null);
			birdImgIndex = birdImgIndex + 1;
			birdImgIndex = birdImgIndex % imagebird.length;

		}

		void draw() {
			
			final SurfaceHolder holder = getSurfaceHolder();

			cal = Calendar.getInstance();
			int hourofday = cal.get(Calendar.HOUR_OF_DAY);
			Canvas c = null;

			Log.d(LogString, "draw");
			try {
				c = holder.lockCanvas();
				// clear the canvas
				if (c != null) {
					c.drawColor(Color.BLACK);
					ScreenWidth = c.getWidth();

					c.save();
					if (direction == RIGHT) {

						if (tajXpos >= ((ImageWidth - ScreenWidth) * -1))
							tajXpos = tajXpos - 4;
					}

					if (direction == LEFT) {
						if (tajXpos <= 0) {
							tajXpos = tajXpos + 4;
						}
					}

					if (hourofday > 0 && hourofday < 6) {

						if (dayState != NIGHT) {
							dayState = NIGHT;
							dayStateChanged = true;
						}

					} else if (hourofday >= 6 && hourofday <= 9) {

						if (dayState != MORNGING) {
							dayState = MORNGING;
							dayStateChanged = true;
						}

					} else if (hourofday > 9 && hourofday < 18) {
						if (dayState != AFTERNOON) {
							dayState = AFTERNOON;
							dayStateChanged = true;
						}

					} else if (hourofday >= 18 && hourofday <= 19) {

						if (dayState != EVENING) {
							dayState = EVENING;
							dayStateChanged = true;
						}

					} else {
						if (dayState != NIGHT) {
							dayState = NIGHT;
							dayStateChanged = true;
						}
					}

					if (dayStateChanged == true) {
						delay2 = 100;
					}

					switch (dayState) {

					case NIGHT:

						Log.d(LogString, "Morning");
						if (dayStateChanged) {
							Log.d(LogString, "Morning day state is changed");
							new Thread(imgHandler[NIGHT]).start();
							new Thread(imgBGHandler[NIGHT]).start();
							dayStateChanged = false;
							mIsImageloaded = false;
							mIsImageBGloaded = false;
							BirdState = STOP;
						}

						if (imgHandler[NIGHT].IsLoaded()
								&& mIsImageloaded == false) {
							Log.d(LogString, "NIGHT image loaded");
							imageTajmahal = imgHandler[NIGHT].GetImage();
							mIsImageloaded = true;

						}

						if (imgBGHandler[NIGHT].IsLoaded()
								&& mIsImageBGloaded == false) {
							backgroundImage = imgBGHandler[NIGHT].GetImage();
							mIsImageBGloaded = true;
						}

						Log.d(LogString,
								"Morning:imgBGHandler[NIGHT].IsLoaded():"
										+ imgBGHandler[NIGHT].IsLoaded()
										+ "mIsImageloaded:" + mIsImageBGloaded);
						c.drawBitmap(backgroundImage, BgXpos, BgYpos, null);

						c.drawBitmap(imageTajmahal, tajXpos, tajYpos, null);
						if (crackerState == START) {
							crackerState = PLAYING;
							CrackerImgIndex = 0;
						}
						if (crackerState == PLAYING) {
							c.drawBitmap(CrackerImage[CrackerImgIndex], 50, 50,
									null);
							CrackerImgIndex = CrackerImgIndex + 1;
						}

						if (CrackerImgIndex == CrackerImage.length) {
							crackerState = LAST;
							CrackerImgIndex = 0;

						}

						/*
						 * Log.d(LogString, "Night day state is changed"); if (
						 * dayStateChanged) {
						 * 
						 * new Thread(imgHandler[NIGHT]).start(); new
						 * Thread(imgBGHandler[NIGHT]).start(); dayStateChanged
						 * = false; mIsImageloaded= false; mIsImageBGloaded=
						 * false; }
						 * 
						 * if (imgHandler[NIGHT].IsLoaded() && mIsImageloaded ==
						 * false) { imageTajmahal =
						 * imgHandler[NIGHT].GetImage(); mIsImageloaded = true;
						 * Log.d(LogString, "Front image changed");
						 * 
						 * }
						 * 
						 * if (imgBGHandler[NIGHT].IsLoaded() &&
						 * mIsImageBGloaded == false) { backgroundImage =
						 * imgBGHandler[NIGHT].GetImage(); mIsImageBGloaded =
						 * true; Log.d(LogString, "BG image changed"); }
						 * 
						 * c.drawBitmap(backgroundImage, BgXpos, BgYpos, null);
						 * c.drawBitmap(imageTajmahal, tajXpos, tajYpos, null);
						 * 
						 * if (crackerState == START) { crackerState = PLAYING;
						 * CrackerImgIndex = 0; } if (crackerState == PLAYING) {
						 * // c.drawBitmap(CrackerImage[CrackerImgIndex], 50, //
						 * 50, // null); CrackerImgIndex = CrackerImgIndex + 1;
						 * }
						 * 
						 * if (CrackerImgIndex >= CrackerImage.length) {
						 * crackerState = LAST;
						 * 
						 * }
						 * 
						 */
						break;
					case MORNGING:
						Log.d(LogString, "Morning");
						if (dayStateChanged) {
							Log.d(LogString, "Morning day state is changed");
							new Thread(imgHandler[MORNGING]).start();
							new Thread(imgBGHandler[MORNGING]).start();
							dayStateChanged = false;
							mIsImageloaded = false;
							mIsImageBGloaded = false;
						}

						if (imgHandler[MORNGING].IsLoaded()
								&& mIsImageloaded == false) {
							Log.d(LogString, "morning image loaded");
							imageTajmahal = imgHandler[MORNGING].GetImage();
							mIsImageloaded = true;

						}

						if (imgBGHandler[MORNGING].IsLoaded()
								&& mIsImageBGloaded == false) {
							backgroundImage = imgBGHandler[MORNGING].GetImage();
							mIsImageBGloaded = true;
						}

						Log.d(LogString,
								"Morning:imgBGHandler[MORNGING].IsLoaded():"
										+ imgBGHandler[MORNGING].IsLoaded()
										+ "mIsImageloaded:" + mIsImageBGloaded);
						c.drawBitmap(backgroundImage, BgXpos, BgYpos, null);

						c.drawBitmap(imageTajmahal, tajXpos, tajYpos, null);

						break;
					case AFTERNOON:

						if (dayStateChanged) {
							new Thread(imgHandler[AFTERNOON]).start();
							new Thread(imgBGHandler[AFTERNOON]).start();
							dayStateChanged = false;
							mIsImageloaded = false;
							mIsImageBGloaded = false;
						}

						if (imgHandler[AFTERNOON].IsLoaded()
								&& mIsImageloaded == false) {
							imageTajmahal = imgHandler[AFTERNOON].GetImage();
							mIsImageloaded = true;
						}

						if (imgBGHandler[AFTERNOON].IsLoaded()
								&& mIsImageBGloaded == false) {
							backgroundImage = imgBGHandler[AFTERNOON]
									.GetImage();
							mIsImageBGloaded = true;
						}

						c.drawBitmap(backgroundImage, BgXpos, BgYpos, null);

						c.drawBitmap(imageTajmahal, tajXpos, tajYpos, null);
						break;
					case EVENING:

						if (dayStateChanged) {
							new Thread(imgHandler[EVENING]).start();
							new Thread(imgBGHandler[EVENING]).start();
							dayStateChanged = false;
							mIsImageloaded = false;
							mIsImageBGloaded = false;
						}

						if (imgHandler[EVENING].IsLoaded()
								&& mIsImageloaded == false) {
							imageTajmahal = imgHandler[EVENING].GetImage();
							mIsImageloaded = true;
						}

						if (imgBGHandler[EVENING].IsLoaded()
								&& mIsImageBGloaded == false) {
							backgroundImage = imgBGHandler[EVENING].GetImage();
							mIsImageBGloaded = true;
						}
						c.drawBitmap(backgroundImage, BgXpos, BgYpos, null);
						c.drawBitmap(imageTajmahal, tajXpos, tajYpos, null);
						break;

					default:
						break;
					}

					Log.d(LogString, "dayState" + dayState);
					Log.d(LogString, "biirdState" + BirdState);
					if (dayState != NIGHT) {

						if (BirdState == START) {
							birdXpos = 0;
							BirdState = PLAYING;
						}

						if (BirdState == PLAYING) {
							if (birdXpos <= ScreenWidth && 
									birdYpos >=0) {

								c.drawBitmap(imagebird[birdImgIndex], birdXpos,
										birdYpos, null);

								// drawbirdAnimate(c,birdXpos,birdYpos);
								Log.d(LogString, " drawbirdAnimate");
								/*
								 * birdXpos = birdXpos + 5; birdImgIndex =
								 * birdImgIndex + 1; birdImgIndex = birdImgIndex
								 * % imagebird.length;
								 */
								currentTime = System.currentTimeMillis();
								int delta = (int) (currentTime - lastUpdateTime);
								
								Log.d(LogString, "delta :" + delta);

								int change = (int) (0.05 * delta);
								change = Math.abs(change);
								Log.d(LogString, "0.5*delta change" + change);
								birdXpos = birdXpos + change;
								lastUpdateTime = currentTime;
								if(Birdflyup == true){
									birdYpos = birdYpos - change;
									Log.d(LogString, "ondraw(): Birdflyup == true");
									Log.d(LogString, "ondraw(): BirdYpos"+birdYpos);
								}
								
								change = (int) (0.01 * delta);

								Log.d(LogString, "0.1*delta" + change);
								change = Math.abs(change);
								birdImgIndex = birdImgIndex + change;
								birdImgIndex = birdImgIndex % imagebird.length;
								if (birdImgIndex < 0) {
									birdImgIndex = 0;
								}

								Log.d(LogString, "bird x pos" + birdXpos);

							} else {
								BirdState = STOP;
								Birdflyup = false;
								birdYpos = 70;
							}
						}
					}

					/*
					 * if(birdXpos <=ScreenWidth){
					 * 
					 * c.drawBitmap(imagebird[birdImgIndex], birdXpos, birdYpos,
					 * null); birdImgIndex = birdImgIndex + 1; birdImgIndex =
					 * birdImgIndex % imagebird.length;
					 * drawbirdAnimate(c,birdXpos,birdYpos); birdXpos = birdXpos
					 * +1; }
					 */
					// drawbirdAnimate(c,birdXpos,birdYpos);
					c.restore();
				}
			} catch(Exception e){
				
				Log.e(LogString, "Exception caught ="+e);
				delay = 100;
				handler.postDelayed(drawRunnerTajmahal, delay); // delay 10
			}finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}

			handler.removeCallbacks(drawRunnerTajmahal);
			if (visible) {
				if (BirdState == PLAYING) {
					Log.d(LogString, "BirdState == PLAYING");
					delay = 100;
				} else if (crackerState == PLAYING) {
					Log.d(LogString, "crackerState == PLAYING");
					delay = 100;
				} else if (crackerState == LAST) {
					crackerState = STOP;
					Log.d(LogString, "crackerState == LAST");
					delay = 100;
				}

				else if (BirdState == PLAYING && crackerState == PLAYING) {
					Log.d(LogString,
							"BirdState == PLAYING && crackerState == PLAYING");
					delay = 100;
				} else {
					Log.d(LogString, "delay= in 1000");
					delay = 10000;
				}

				if (mIsImageBGloaded == false) {
					delay = 100;
				}

				Log.d(LogString, "Birdstate=" + BirdState + "crackerState="
						+ crackerState);
				Log.d(LogString, "delay=" + delay);
				handler.postDelayed(drawRunnerTajmahal, delay); // delay 10
				// mileseconds
			}

		}
	}

}
