package com.video.view;

import com.video.mplayer.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class VodLoadAnimation extends LinearLayout {

	private TextView mTextPrompt;

	public VodLoadAnimation(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public VodLoadAnimation(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VodLoadAnimation(Context context) {
		this(context, null);
	}

	private void init(Context context) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.vod_load_anim, this);
		ImageView progressImageView = (ImageView) view
				.findViewById(R.id.vod_load_img);
		mTextPrompt = (TextView) view.findViewById(R.id.load_prompt);
		AnimationDrawable ad = (AnimationDrawable) progressImageView
				.getDrawable();
		ad.start();
	}

	public void setPromptText(String traffic) {
		String promptText = "(" + traffic + "/KB)";
		 mTextPrompt.setText(promptText);
	}

	private String getString(int resId) {
		return getResources().getText(resId).toString();
	}
}
