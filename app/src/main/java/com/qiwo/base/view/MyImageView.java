package com.qiwo.base.view;



/**  
 * @version 1.0
 * @author Lickky
 * @createTime 2012-9-24
 * 此类代码是根据android系统自带的ImageViewTouchBase代码修改,主要用于实现缩放功能
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.qiwo.enumlistdemo.AlbumDemoActivity;


public class MyImageView extends ImageView {
	@SuppressWarnings("unused")
	private static final String TAG = "ImageViewTouchBase";

	protected Matrix initMatrix = new Matrix();
	protected Matrix savedMatrix = new Matrix();
	protected Matrix lastMatrix = new Matrix();
	private final Matrix mDisplayMatrix = new Matrix();
	private final float[] mMatrixValues = new float[9];
	private ImageState mapState = new ImageState();
	float imageWidth;// 图片的真正宽�?
	float imageHeight;
	protected Bitmap image;// 用来保存�?��始的bitmap
	float[] values = new float[9];
	float mMaxZoom = 2.0f;// �?��缩放比例
	float mMinZoom;// �?��缩放比例
	private int bitmapWidth;// 图片的原始宽�?
	private int bitmapHeight;// 图片的原始高�?
	float firstDist = 0;
	private float scaleRate;// 图片适应屏幕的缩放比�?
	// 用来记录第一个点
	private PointF firstPoint = new PointF();
	// 记录缩放比例
	float scale = 0;

	int screenWidth = AlbumDemoActivity.screenWidth;
	int screenHeight = AlbumDemoActivity.screenHeight;

	@Override
	protected void onDraw(Canvas canvas) {
		// 正在显示的图片实际宽�?
		float width = bitmapWidth * getScale();
		if (width > screenWidth) {
			// 如果图宽大于屏宽，就不用水平居中
			center(false, true);
		} else {
			center(true, true);
		}
		super.onDraw(canvas);

	}

	// 构�?方法
	public MyImageView(Context context) {
		super(context);

	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		// TODO 自动生成的方法存�?
		super.setImageBitmap(bm);
		this.bitmapHeight = bm.getHeight();
		this.bitmapWidth = bm.getWidth();
		image = bm;
		// 计算适应屏幕的比�?
		arithScaleRate();
		// 缩放到屏幕大�?
		zoomTo(scaleRate, screenWidth / 2f, screenHeight / 2f);
		bitmapWidth = image.getWidth();
		bitmapHeight = image.getHeight();
		setView();
		init();
	}

	public MyImageView(Context context, AttributeSet attrs, int imageWidth,
			int imageHeight) {
		super(context, attrs);
		this.bitmapHeight = imageHeight;
		this.bitmapWidth = imageWidth;
		init();
	}

	public MyImageView(Context context, int width, int height) {
		super(context);
		this.bitmapHeight = width;
		this.bitmapWidth = height;
		init();
	}

	// 计算适应屏幕的比�?
	private void arithScaleRate() {
		float scaleWidth = screenWidth / (float) bitmapWidth;
		float scaleHeight = screenHeight / (float) bitmapHeight;
		scaleRate = Math.min(scaleWidth, scaleHeight);
	}

	// 设置居中
	protected void center(boolean horizontal, boolean vertical) {
		if (image == null) {
			return;
		}
		Matrix m = getImageViewMatrix();
		RectF rect = new RectF(0, 0, image.getWidth(), image.getHeight());
		m.mapRect(rect);
		float height = rect.height();
		float width = rect.width();
		float deltaX = 0, deltaY = 0;
		if (vertical) {
			int viewHeight = getHeight();
			if (height < viewHeight) {
				deltaY = (viewHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < viewHeight) {
				deltaY = getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int viewWidth = getWidth();
			if (width < viewWidth) {
				deltaX = (viewWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < viewWidth) {
				deltaX = viewWidth - rect.right;
			}
		}

		postTranslate(deltaX, deltaY);
		setImageMatrix(getImageViewMatrix());
	}

	protected float getScale() {
		savedMatrix.getValues(mMatrixValues);
		mMinZoom = (screenWidth / 2f) / bitmapWidth;
		return mMatrixValues[Matrix.MSCALE_X];
	}

	// Combine the base matrix and the supp matrix to make the final matrix.
	protected Matrix getImageViewMatrix() {
		// The final matrix is computed as the concatentation of the base matrix
		// and the supplementary matrix.
		mDisplayMatrix.set(initMatrix);
		mDisplayMatrix.postConcat(savedMatrix);
		return mDisplayMatrix;
	}

	// 缩放，用于初始化的时候改变图�?
	protected void zoomTo(float scale, float centerX, float centerY) {
		if (scale > mMaxZoom) {
			scale = mMaxZoom;
		} else if (scale < mMinZoom) {
			scale = mMinZoom;
		}

		float oldScale = getScale();
		float deltaScale = scale / oldScale;

		savedMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		setImageMatrix(getImageViewMatrix());
		center(true, true);
	}

	// 移动，用于初始化的时候居�?
	public void postTranslate(float dx, float dy) {
		savedMatrix.postTranslate(dx, dy);
		setImageMatrix(getImageViewMatrix());

	}

	// 刷新界面，重新记录当前图片的状�?
	public void setView() {
		this.setImageMatrix(savedMatrix);
		this.setScaleType(ScaleType.MATRIX);
		Rect rect = this.getDrawable().getBounds();
		this.getImageMatrix().getValues(values);
		imageWidth = rect.width() * values[0];
		imageHeight = rect.height() * values[0];
		mapState.left = values[2];
		mapState.top = values[5];
		mapState.right = mapState.left + imageWidth;
		mapState.bottom = mapState.top + imageHeight;
	}

	// 记录图片的实�?
	private class ImageState {
		private float left;
		private float top;
		private float right;
		private float bottom;
	}

	// 手指向左�?
	public boolean getNext() {
		return mapState.right <= screenWidth && mapState.left <= -2;
	}

	// 手指向右�?
	public boolean getBack() {
		return mapState.left >= 0 && mapState.right >= screenWidth;

	}

	// 设置图片ScaleType为MATRIX
	private void init() {
		setScaleType(ScaleType.MATRIX);
	}

	// 初始化，记录第一次按下的�?
	public void init(MotionEvent event) {
		lastMatrix.set(savedMatrix);
		firstPoint.set(event.getX(), event.getY());
	}

	// 拖动
	public void drag(MotionEvent event) {
		savedMatrix.set(lastMatrix);
		// 上下左右都至少有�?��出界时，能随意拖
		if ((mapState.left <= 0 || mapState.right >= screenWidth)
				&& (mapState.top <= 0 || mapState.bottom >= screenHeight)) {
			savedMatrix.postTranslate(event.getX() - firstPoint.x, event.getY()
					- firstPoint.y);
			// // 当只有上下一方出界，只能上下拖动
		} else if (mapState.top <= 0 || mapState.bottom >= screenHeight) {
			savedMatrix.postTranslate(0, event.getY() - firstPoint.y);
			// 当只有左右一方出界时，只能左右拖
		} else if (mapState.left <= 0 || mapState.right >= screenWidth) {
			savedMatrix.postTranslate(event.getX() - firstPoint.x, 0);
		}
	}

	// 计算两点距离
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		//noinspection deprecation
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * 算出两点的距离firstDist,用于计算缩放比例
	 */
	public void getTheFirstDist(MotionEvent event) {
		this.firstDist = this.spacing(event);
		if (firstDist > 10f) {
			lastMatrix.set(savedMatrix);
		}
	}

	// 图片缩放
	public void zoom(MotionEvent event) {
		float newDist = spacing(event);
		if (newDist > 10f) {
			savedMatrix.set(lastMatrix);
			scale = newDist / firstDist;
			// 缩放模式为缩
			if (scale < 1) {

				savedMatrix.postScale(scale, scale, screenWidth / 2,
						screenHeight / 2);
			} else {// 缩放模式为放

				savedMatrix.postScale(scale, scale, screenWidth / 2,
						screenHeight / 2);

			}
		}
	}

	// 返回当前图片的宽�?
	public int getImageWidth() {
		return (int) imageWidth;

	}
}
