package com.video.view;





import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.forcetech.android.ForceTV;
import com.video.mplayer.R;
import com.video.util.MACUtils;
import com.video.util.MD5Util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class PlayVideoView extends LinearLayout {
    private Context mContext;
    public VideoView mVideoView;
    public String chnnelId,streamIp;
    private Timer mTimerCache = new Timer();
    public VideoCallBack mCallback;
    public interface VideoCallBack{
        void LoadOver();
        void PlayOver();
        void PlayStart();
    }
	public void SetVideoCB(VideoCallBack callBack){
		this.mCallback=callBack;
	}
	
    private static final String PLAY_SERVER = "http://127.0.0.1:9906/";
    private static final String PLAY_LOGIN = "http://127.0.0.1:9906/forcetech/login";
    //获取播放信息
    private static final String GET_FORCE_PLAY_INFO ="http://127.0.0.1:9906/api?func=query_chan_info&id=";
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
	private Long[] parseForcePlayInfo(String forcePlayInfo) {
		Document doc = null;
		try {
			String parseXml = forcePlayInfo.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");// .replace("ver=1.0", "");
			doc = DocumentHelper.parseText(parseXml); 
//			Log.d("parseXml",parseXml);
			Node root = doc.getRootElement();
			Pattern p = Pattern.compile("download_flowkbps=\"(\\d+)\"");	
			Matcher m = p.matcher(parseXml);
			long traffic=0;
//			while(m.find()){
			m.find(0);
			traffic= Integer.valueOf(m.group(1));
			
			traffic=traffic/8;
//			long traffic=dd;
//			m.find();
//			Log.d("root",traffic+"");
//			Element element = (Element)root.selectSingleNode("channel");
//			root.
//			Element element = (Element) root.selectSingleNode("/forcetv/channel");
//			long cacheTime = Integer.valueOf(element.attribute("cache_time").getValue());
//			long traffic = Integer.valueOf(element.attribute("download_flowkbps").getValue());
	 
			return new Long[]{(long) traffic,(long) traffic};
		} catch (DocumentException e) {
		
		} catch (Exception e) {
			
		}
		return new Long[]{1l,1l};
	};
	private String kb="0";
	public String GetKb(){
		return kb;
	}
	private TimerTask mTimerTaskCache = new TimerTask() {
		@Override
		public void run() {
			FinalHttp fn=new FinalHttp();
			fn.get(GET_FORCE_PLAY_INFO+chnnelId, new AjaxCallBack<Object>() {

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					// TODO Auto-generated method stub
					super.onFailure(t, errorNo, strMsg);
					Log.d("forceinfo","error");
				}
 
				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
//					Log.d("forceinfo",t.toString());
					Long playInfo[] = parseForcePlayInfo(t.toString());
//					long traffic = playInfo[1] / 8;
					Log.d("kb",playInfo[1]+"");
					kb=playInfo[1]+"";
					mOSDLoading.setKBS();
//					OSDLoading.setKBS();
				}
				 
			}); 
		   
		}
	};
	private void initView() {
		// TODO Auto-generated method stub
		
		View view = LayoutInflater.from(mContext).inflate(R.layout.playvideoview, this);
		mVideoView=(VideoView) view.findViewById(R.id.myvideo);
		mVideoView.setOnPreparedListener(mVideoPrepareListener);
		initOSD();
		new ForceTV().initForceClient(); 
//
		new Thread(){
			@Override
			public void run() {
//				new ForceTV().initForceClient();//not execute this,couldn't play video,keeping on loading video state
			}
		}.start();
		//playVideo();
	
		
		
	}
	 
	public OSDManager mOSDManager;
	public OSDLoading mOSDLoading;
	public OSDBeforLoad mOSDBefor;
	private View mContainer;
	
	private void initOSD() {
		// TODO Auto-generated method stub
		mContainer = findViewById(R.id.osd_container);
		mOSDManager = new OSDManager();
		mOSDLoading = new OSDLoading(mContainer,this);
		mOSDBefor= new OSDBeforLoad(mContainer,this);
		mOSDManager.addOSD(OSDManager.OSD_LOADING,mOSDLoading);
		mOSDManager.addOSD(OSDManager.OSD_BEFORLOAD,mOSDBefor);
	}



	public void playVideo(String url){
		Log.d("INurl",url);
		if(url.indexOf("http")==0){
			//playhttp
			mOSDManager.showOSD(mOSDLoading);
			mVideoView.setVideoPath(url);
		}else{
			//playforcegetStartUrl
//			FinalHttp finalHttp = new FinalHttp();
//			finalHttp.addHeader("User-Agent", "cwhttp/v1.0");
//			String URL=PLAY_LOGIN+"?mac="+MACUtils.getMac()+"&sn=22&username=1&type=1&key="+MD5Util.getStringMD5_32(MACUtils.getMac()+"22");
//			Log.d("forceUrl",URL);
//			finalHttp.get(URL, mStartVodCallBack2);
			
			
			FinalHttp finalHttp = new FinalHttp();
			mOSDManager.showOSD(mOSDBefor);
			finalHttp.addHeader("User-Agent", "cwhttp/v1.0");
			Log.d("forceUrl",getStartUrl(url));
			finalHttp.get(getStartUrl(url), mStartVodCallBack);
		}
		
		 
	
	} 
	private AjaxCallBack<Object> mStartVodCallBack2 = new AjaxCallBack<Object>() {
		public void onSuccess(Object t) {
			super.onSuccess(t);
			Log.d("login","success");
			Log.d("loginstr",t.toString());
		}; 
		
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			Log.d("login","error");
		}; 
	};  
	private AjaxCallBack<Object> mStartVodCallBack = new AjaxCallBack<Object>() {
		public void onSuccess(Object t) {
			super.onSuccess(t);
			mOSDManager.closeOSD(mOSDBefor);
			Log.d("fource","success");
			String playUrl=new StringBuilder(PLAY_SERVER).append(chnnelId).append(".ts").toString();
			mOSDManager.showOSD(mOSDLoading);
			mVideoView.setVideoPath(playUrl);
//			mCheckPlayHandler.sendEmptyMessage(Configs.SUCCESS);
			try{
				mTimerCache.schedule(mTimerTaskCache, 1 * 1000, 1 * 1000);  
			}catch(Exception e){
				e.printStackTrace();
			}
		}; 
		
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			Log.d("fource","error");
		}; 
	};  
	private String getStartUrl(String url){
		
         
           String[] urlArr=url.split("/");
//           for (int i = 0; i < urlArr.length; i++) {
//        	   Log.d("urlarr",urlArr[i]);
//           }
			chnnelId =urlArr[3];
			streamIp =urlArr[2];
			String link = "";
			StringBuilder sb = new StringBuilder("http://127.0.0.1:9906/cmd.xml?cmd=");
			sb.append("switch_chan").append("&id=").append(chnnelId).append("&server=").append(streamIp);
			 
			if (link != null && !link.equals("")) {
				sb.append("&link=").append(link);
			}
		
			 
			
			return sb.toString();
	
		
	}
	private TimerTask mTimerTaskGetPlayPos = new TimerTask() {
		@Override
		public void run() {
			mHandlerPlayPos.sendEmptyMessage(0);
		}
	};
	private Timer mTimerGetPlayPos = new Timer();
	private boolean mIsVodPause = false;
	private long mPreiousTime = 0;
	private Handler mHandlerPlayPos = new Handler(){
		public void handleMessage(Message msg) {
			int currentPlayPos =(int) mVideoView.getCurrentPosition();
			if(currentPlayPos <=3) return;  //ǰ3�벻�ж��Ƿ���Կ���
//			if(mIsVodPause || isLoadingVod) return;
			
//			Log.d("ddTime","mPreiousTime:"+mPreiousTime+"--currentPlayPos")
			if(mPreiousTime == currentPlayPos){
				Log.d("paus","paus");
				if(View.GONE == mOSDLoading.getVisibility())
					mOSDManager.showOSD(mOSDLoading);
			} else {
				if(View.VISIBLE == mOSDLoading.getVisibility())
					mOSDManager.closeOSD(mOSDLoading);
			}
//			mOSDControl.setCurrentPlayTime(currentPlayPos);
			mPreiousTime = currentPlayPos;
		};
	};
	private OnPreparedListener mVideoPrepareListener = new MediaPlayer.OnPreparedListener() {
		public void onPrepared(MediaPlayer paramAnonymousMediaPlayer) {
			mOSDManager.closeOSD(mOSDLoading);
			mVideoView.start();
			if(mCallback!=null){
				mCallback.LoadOver();
			}
			try{
				mTimerGetPlayPos.schedule(mTimerTaskGetPlayPos, 0, 1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			return;
		}
	};

	
}
