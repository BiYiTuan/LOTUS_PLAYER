package com.video.view;

import com.video.mplayer.R;

import android.view.View;
import android.widget.RelativeLayout;

public class OSDBeforLoad extends OSD{

	private RelativeLayout mBeforLoad;
	private View mParentView;
	private PlayVideoView mPlayview;
	public OSDBeforLoad(View parentView,PlayVideoView playview){
		setProperity(PROPERITY_LEVEL_6);
		mPlayview=playview;
		mParentView = parentView;
		mBeforLoad = (RelativeLayout) mParentView.findViewById(R.id.osd_befor_load);
	    
	}
	public void setKBS(){
//		mBeforLoad.setPromptText(mPlayview.GetKb());
	}
	@Override
	public void setVisibility(int visible) {
		mBeforLoad.setVisibility(visible);
	}
	public int getVisibility(){
		return mBeforLoad.getVisibility();
	}
	
	
	public void setPromptText(int traffic){
//		mVodLoadAnimation.setPromptText(traffic);
	}
}
