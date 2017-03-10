package com.qiwo.base.view;



/**  
 * MyGallery.java
 * @version 1.0
 * @author Haven
 * @createTime 2011-12-9 下午03:42:53
 * android.widget.Gallery的子函数。此类很重要。建议仔细看
 */



import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

import com.qiwo.enumlistdemo.AlbumDemoActivity;


public class MyGallery extends Gallery {
	private MyImageView imageView;
	static final int NONE = 0;// 初始状态
	static final int DRAG = 1;// 拖动
	static final int ZOOM = 2;// 缩放
	int mode = NONE;

	public MyGallery(Context context) {
		super(context);

	}

	public MyGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.setOnTouchListener(new OnTouchListener() {

			// 重写onTouch方法实现缩放
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				View view = MyGallery.this.getSelectedView();
				imageView = (MyImageView) view;
				imageView.setView();
				if (view instanceof MyImageView) {

					switch (event.getAction() & MotionEvent.ACTION_MASK) {
					// 主点按下
					case MotionEvent.ACTION_DOWN:
						imageView.init(event);
						imageView.setView();
						mode = DRAG;

						break;
					// 副点按下
					case MotionEvent.ACTION_POINTER_DOWN:
						//
						imageView.getTheFirstDist(event);
						mode = ZOOM;
						break;

					case MotionEvent.ACTION_UP:
						imageView.setView();
						mode = NONE;
					case MotionEvent.ACTION_POINTER_UP:
						mode = NONE;
						break;
					case MotionEvent.ACTION_MOVE:
						// 取得图片当前位置的坐标
						if (mode == DRAG) {
							if (imageView.getImageWidth() <= AlbumDemoActivity.screenWidth) {
								break;
							}

							// 向右画
							if (imageView.getBack()) {
								break;

							}
							// 向左画
							else if (imageView.getNext()) {
								break;
							} else {

								imageView.drag(event);
								imageView.setView();
								return true;
							}
						} else if (mode == ZOOM) {
							imageView.zoom(event);
							imageView.setView();
							return true;
						}
					}
				}

				return false;

			}

		});
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		int kEvent;
		if (isScrollingLeft(e1, e2)) {
			// Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			// Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO 自动生成的方法存根

		if (imageView.getBack() || imageView.getNext()) {
			super.onScroll(e1, e2, distanceX, distanceY);
		}
		if (imageView.getImageWidth() <= AlbumDemoActivity.screenWidth
				&& mode == DRAG) {
			super.onScroll(e1, e2, distanceX, distanceY);

		}
		return false;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
//		if (imageView.getBack() || imageView.getNext()) {
//			return e2.getX() > e1.getX();
//		}
		return e2.getX() > e1.getX();
	}

}