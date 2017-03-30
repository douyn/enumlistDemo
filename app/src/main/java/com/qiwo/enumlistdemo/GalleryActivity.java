/*
package com.qiwo.enumlistdemo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiwo.base.view.MyGallery;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
*/
/**
 * 大图查看
 * @author zhu
 * QQ  7617812
 * 2016年5月12日
 *//*

public class GalleryActivity extends BaseActivity  implements  LmbRequestCallBack{
	// 屏幕宽度

	// 屏幕高度
	public static int screenHeight;
	private MyGallery gallery;
	public static  TextView  textView,liketextView,commenttextView;
	public static ArrayList<PicItemBean>  images;
	private ImageView  shareimageView;
	public static 	ImageView  likeImageView;
	public static LinearLayout  commentlayout;
	public static LinearLayout  likelayout;
	public static  int  position;
	public static	GalleryAdapter   adapter;
	Activity activity;
	Picitem  picitem;
	int  enterposition;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.gallery);
		screenWidth = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();
		screenHeight = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight();
		if (getIntent().getSerializableExtra("picitem")!=null) {
			picitem  =(Picitem) getIntent().getSerializableExtra("picitem");
		}
		enterposition =getIntent().getIntExtra("position", 0);
		activity= GalleryActivity.this;
		if (getIntent().getParcelableArrayListExtra("images")!=null) {
			images =(ArrayList<PicItemBean>) getIntent().getSerializableExtra("images");
		}
		commentlayout  = (LinearLayout) findViewById(R.id.commentlayout);
		likelayout  = (LinearLayout) findViewById(R.id.likelayout);
		likeImageView   = (ImageView) findViewById(R.id.imageView1);
		gallery = (MyGallery) findViewById(R.id.gallery);
		textView  =  (TextView) findViewById(R.id.textView1);
		liketextView  =  (TextView) findViewById(R.id.liketextView);
		commenttextView  =  (TextView) findViewById(R.id.commenttextView);
		shareimageView  =  (ImageView) findViewById(R.id.shareimageView);
		gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
		gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
		adapter = new GalleryAdapter(GalleryActivity.this,images);
		gallery.setAdapter(adapter);
		gallery.setSelection(enterposition);
		gallery.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
					  alertDialog  = new AlertDialog.Builder(GalleryActivity.this).setMessage("保存到相册?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Bitmap bmp  =imageLoader.loadImageSync(images.get(position).thumb_url);
							Tools.saveImageToGallery(GalleryActivity.this,bmp);
							alertDialog.dismiss();
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertDialog.dismiss();
						}
					}).create();
					alertDialog.show();
				return true;
			}
		});
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				finish();
			}
		});



		likelayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (images.get(position).status.equals("1")) {
					sendLike(images.get(position).id, "1", "0");

				}else {
					sendLike(images.get(position).id, "1", "1");
				}
			}
		});

		shareimageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UMImage image = new UMImage(activity,images.get(position).thumb_url);


				String reg = "\\<[^\\>]*\\>";

			//ToolsUtil.shareMsg(getActivity(), title,title, "", link);
			 new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,
            		   SHARE_MEDIA.QZONE

            		   )
            		   .withTitle(picitem.title.replaceAll(reg, "").replace("&nbsp;", ""))
            				   .withText(images.get(position).desc.replaceAll(reg, "").replace("&nbsp;", "")+"@饭咖")
            		   .withMedia(image).withTargetUrl(images.get(position).thumb_url)

                       .setListenerList(umShareListener,umShareListener)
                       .open();
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		NetDealWith.getCommentList(executorService, images.get(position).id, "5",  GalleryActivity.this, GET_COMMENTList, GalleryActivity.this,1);
	}
	private final static  int   GET_COMMENTList=111;
	public    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(activity, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        */
/** attention to this below ,must add this**//*

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

	AlertDialog	  alertDialog;
	private int  DO_LIKE=101;


	public void sendLike(String  schedular_id,String   class_id,String status){
		LinkedHashMap<String, String>   hashMap  = new LinkedHashMap<String, String>();
		String url  = Define.host+"/news/sendLike";
		hashMap.put("schedular_id", schedular_id);
		hashMap.put("class_id", class_id);
		hashMap.put("status", status);
		Tools.putToken(hashMap, GalleryActivity.this);
		executorService.execute(new LmbRequestRunabel( GalleryActivity.this, DO_LIKE, url,hashMap, this));

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	//	menu.add(0, 0, 0, "分享").setIcon(R.drawable.share);
		return super.onCreateOptionsMenu(menu);


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0:
			Intent intent=new Intent(Intent.ACTION_SEND);
			  //intent.ACTION_SEND.split(Intent.EXTRA_EMAIL) ;
			   intent.setType("text/plain");

			   intent.putExtra(Intent.EXTRA_SUBJECT, "share");
			   intent.putExtra(Intent.EXTRA_TEXT, "I'am  useing  About Nina Dobrev");
			   startActivity(Intent.createChooser(intent, "share"));
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}


	float beforeLenght = 0.0f; // 两触点距离
	float afterLenght = 0.0f; // 两触点距离
	boolean isScale = false;
	float currentScale = 1.0f;// 当前图片的缩放比率

	*/
/**
	 * 就算两点间的距离
	 *//*

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	public void onStart(int type) {
			if (type!=GET_COMMENTList) {
				showLoadingDialog(GalleryActivity.this);
			}

	}
public static	int  likes;
boolean islike=false;
	@Override
	public void onSuccess(int type, String url, Map<String, String> params,
			String result) {
		dismissLoading(GalleryActivity.this);
		if (type==DO_LIKE) {
			try {
				JSONObject jsonObject  = new JSONObject(result);
				String  code  =  jsonObject.optString("code");
				if (images.get(position).status.equals("0")) {
					if ("200".equals(code)) {
						showShortToast("点赞成功");
					  likes	=Integer.parseInt(GalleryActivity.liketextView.getText().toString());
					likes++;
					images.get(position).likes = likes+"";
					images.get(position).status="1";
					adapter.notifyDataSetChanged();
					}else {
						showShortToast("点赞失败，请重试");

					}
				}else {
					if ("200".equals(code)) {
						showShortToast("取消点赞成功");
						  likes	=Integer.parseInt(GalleryActivity.liketextView.getText().toString());
					likes--;
					images.get(position).likes = likes+"";
					images.get(position).status="0";
					adapter.notifyDataSetChanged();
					}else {
						showShortToast("取消点赞失败，请重试");

					}
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		 if (type==GET_COMMENTList) {

			 try {
				 JSONObject jsonObject  = new JSONObject(result);
					JSONObject  dataJsonObject  = jsonObject.optJSONObject("data");
					String count  = dataJsonObject.optString("count");
					images.get(position).comments  = count+"";
					adapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 }

	}

	@Override
	public void onFault(int type, String url, String result) {
		// TODO Auto-generated method stub
		dismissLoading(GalleryActivity.this);
	}


	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

	}




}*/
