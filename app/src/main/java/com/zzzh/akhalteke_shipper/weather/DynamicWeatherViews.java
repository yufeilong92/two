package com.zzzh.akhalteke_shipper.weather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AnimationUtils;

public class DynamicWeatherViews extends SurfaceView implements SurfaceHolder.Callback {

	static final String TAG = DynamicWeatherViews.class.getSimpleName();
	private DrawThreads mDrawThreads;

	public DynamicWeatherViews(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	SurfaceHolder mSurface;
	private BaseDrawer preDrawer, curDrawer;
	private float curDrawerAlpha = 0f;
	private BaseDrawer.Type curType = BaseDrawer.Type.DEFAULT;
	private int mWidth, mHeight;

	private void init(Context context) {
		curDrawerAlpha = 0f;
//		mDrawThread = new DrawThread();
		mSurface=getHolder();
		final SurfaceHolder surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setFormat(PixelFormat.RGBA_8888);
//		mDrawThread.start();

	}

	private void setDrawer(BaseDrawer baseDrawer) {
		if (baseDrawer == null) {
			return;
		}
		curDrawerAlpha = 0f;
		if (this.curDrawer != null) {
			this.preDrawer = curDrawer;
		}
		this.curDrawer = baseDrawer;
	}

	public void setDrawerType(BaseDrawer.Type type) {
		if (type == null) {
			return;
		}
		// UiUtil.toastDebug(getContext(), "setDrawerType->" + type.name());
		if (type != curType) {
			curType = type;
			setDrawer(BaseDrawer.makeDrawerByType(getContext(), curType));
		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// updateDrawerSize(w, h);
		mWidth = w;
		mHeight = h;
	}

	// private void updateDrawerSize(int w, int h) {
	// if (w == 0 || h == 0) {
	// return;
	// }// 这里必须加锁，因为在DrawThread.drawSurface的时候调用的是各种Drawer的绘制方法
	// // 绘制的时候会遍历内部的各种holder
	// // 然而那些个雨滴/星星的holder是在setSize的时候生成的
	// if (this.curDrawer != null) {
	// synchronized (curDrawer) {
	// if (this.curDrawer != null) {
	// curDrawer.setSize(w, h);
	// }
	// }
	// }
	// if (this.preDrawer != null) {
	// synchronized (preDrawer) {
	// if (this.preDrawer != null)
	// {//简直我就震惊了synchronized之前不是null，synchronized之后就有可能是null!
	// preDrawer.setSize(w, h);
	// }
	// }
	// }
	//
	// }

	private boolean drawSurface(Canvas canvas) {
		final int w = mWidth;
		final int h = mHeight;
		if (w == 0 || h == 0) {
			return true;
		}
		boolean needDrawNextFrame = false;
		if (curDrawer != null) {
			curDrawer.setSize(w, h);
			needDrawNextFrame = curDrawer.draw(canvas, curDrawerAlpha);
		}
		if (preDrawer != null && curDrawerAlpha < 1f) {
			needDrawNextFrame = true;
			preDrawer.setSize(w, h);
			preDrawer.draw(canvas, 1f - curDrawerAlpha);
		}
		if (curDrawerAlpha < 1f) {
			curDrawerAlpha += 0.04f;
			if (curDrawerAlpha > 1) {
				curDrawerAlpha = 1f;
				preDrawer = null;
			}
		}
		return needDrawNextFrame;
	}

	public void onResume() {
		if (mDrawThreads != null) {
			mDrawThreads.setSuspend(false);
		}
	}

	public void onPause() {
        if (mDrawThreads!=null){
        	mDrawThreads.setSuspend(true);
		}
	}

	public void onDestroy() {
		if (mDrawThreads!=null){
			mDrawThreads.setRunning(false);
		}
		getHolder().removeCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mDrawThreads=new DrawThreads();
		mDrawThreads.setRunning(true);
		mDrawThreads.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mDrawThreads.setRunning(false);
	}
	private class DrawThreads extends  Thread{
		private boolean isRunning = false;

		private boolean suspended = false;

		private final Object control = new Object();
		public void setRunning(boolean running) {
			isRunning = running;
		}

		public void setSuspend(boolean suspend) {
			if (!suspend) {
				synchronized (control) {
					control.notifyAll();
				}
			}

			this.suspended = suspend;
		}
		@Override
		public void run() {
			while (isRunning) {
				if (suspended) {
					try {
						synchronized (control) {
							control.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				final long startTime = AnimationUtils.currentAnimationTimeMillis();
				Canvas canvas = mSurface.lockCanvas();
				if (canvas != null) {
					canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
					drawSurface(canvas);
					mSurface.unlockCanvasAndPost(canvas);
					final long drawTime = AnimationUtils.currentAnimationTimeMillis() - startTime;
					final long needSleepTime = 16 - drawTime;
					System.out.print("fuck");
					if (needSleepTime > 0) {
						SystemClock.sleep(needSleepTime);
					}
				}

			}

		}
	}


}
