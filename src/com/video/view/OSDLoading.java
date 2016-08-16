package com.video.view;

import com.video.mplayer.R;

import android.view.View;

public class OSDLoading extends OSD{

	private VodLoadAnimation mVodLoadAnimation;
	private View mParentView;
	private PlayVideoView mPlayview;
	public OSDLoading(View parentView,PlayVideoView playview){
		setProperity(PROPERITY_LEVEL_6);
		mPlayview=playview;
		mParentView = parentView;
		mVodLoadAnimation = (VodLoadAnimation) mParentView.findViewById(R.id.vod_load_animation);
	    
	}
	public void setKBS(){
		mVodLoadAnimation.setPromptText(mPlayview.GetKb());
	}
	@Override
	public void setVisibility(int visible) {
		mVodLoadAnimation.setVisibility(visible);
	}
	public int getVisibility(){
		return mVodLoadAnimation.getVisibility();
	}
	
	
	public void setPromptText(int traffic){
//		mVodLoadAnimation.setPromptText(traffic);
	}
}
