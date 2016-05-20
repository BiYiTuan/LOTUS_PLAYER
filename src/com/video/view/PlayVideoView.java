package com.video.view;





import com.video.mplayer.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class PlayVideoView extends LinearLayout{
    private Context mContext;
    public VideoView mVideoView;
   
	public PlayVideoView(Context context) {
		super(context);
		this.mContext=context;
		initView();
		// TODO Auto-generated constructor stub
	}



	public PlayVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext=context;
		initView();
		// TODO Auto-generated constructor stub
	}

	public PlayVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext=context;
		initView();
		// TODO Auto-generated constructor stub
	}
	
	
	private void initView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(R.layout.playvideoview, this);
		mVideoView=(VideoView) view.findViewById(R.id.myvideo);
		mVideoView.setOnPreparedListener(mVideoPrepareListener);
		initOSD();

		
		//playVideo();
	
		
		
	}
	 
	public OSDManager mOSDManager;
	public OSDLoading mOSDLoading;
	private View mContainer;
	private void initOSD() {
		// TODO Auto-generated method stub
		mContainer = findViewById(R.id.osd_container);
		mOSDManager = new OSDManager();
		mOSDLoading = new OSDLoading(mContainer);
		mOSDManager.addOSD(OSDManager.OSD_LOADING,mOSDLoading);
	}



	public void playVideo(String url){
		
		mVideoView.setVideoPath(url);
		mOSDManager.showOSD(mOSDLoading);
	
	} 
	private OnPreparedListener mVideoPrepareListener = new MediaPlayer.OnPreparedListener() {
		public void onPrepared(MediaPlayer paramAnonymousMediaPlayer) {
			mOSDManager.closeOSD(mOSDLoading);
			mVideoView.start();
		
			return;
		}
	};

	
}
