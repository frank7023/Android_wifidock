package com.hualu.wifistart.smbsrc.widgets;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class ExImageButton extends ImageButton {

	static final public int BUTTON_STYLE_NORMAL = 1;//
	static final public int BUTTON_STYLE_PUSH = 2;//
	
	private int enableFocusID = -1;
	private int disableFocusID = -1;
	private int curForcusID = -1;
	
	public boolean isNeedScale = false;
	
	private int style = BUTTON_STYLE_NORMAL;
	
	private RectF newPad;
	private BitmapDrawable  enableDrawble  = null;
	private BitmapDrawable  disableDrawble  = null;
	
	
	public ExImageButton(Context context) {
		super(context);
		this.setBackgroundColor(0x00000000);
		registEvent();
	}

	@SuppressWarnings("deprecation")
	public ExImageButton(Context context, int hasFocusID, int lostFocusID) {
		this(context);
		this.enableFocusID = hasFocusID;
		this.disableFocusID = lostFocusID;
		this.enableDrawble = new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(),enableFocusID));
		this.disableDrawble = new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(),disableFocusID));
		this.curForcusID = this.disableFocusID;
		this.setImageDrawable(disableDrawble);
	}

	public ExImageButton(Context context, int enableFocusID,
			int disableFocusID, int style,
			float paddingRateTop, float paddingRateLeft,
			float paddingRateWidth, float paddingRateHeight) {
		this(context);
		this.enableFocusID = enableFocusID;
		this.disableFocusID = disableFocusID;
		this.style = style;
		isNeedScale = true;
		this.newPad = new RectF(paddingRateLeft,paddingRateTop,paddingRateLeft+paddingRateWidth,paddingRateTop+paddingRateHeight);
		this.enableDrawble = ImageHelper.GeneralScaleBmpByRID(context,enableFocusID,newPad);
		this.disableDrawble = ImageHelper.GeneralScaleBmpByRID(context,disableFocusID,newPad);
		this.curForcusID = this.disableFocusID;
		this.setImageDrawable(disableDrawble);
	}
	
	protected void doActionDown() {
		BitmapDrawable temp = enableDrawble;
		int tempRID = curForcusID;
		if(style == BUTTON_STYLE_PUSH){
			if(curForcusID == enableFocusID){
				temp = disableDrawble;
				tempRID = disableFocusID;
			}else if(curForcusID == disableFocusID){
				temp = enableDrawble;
				tempRID = enableFocusID;
			}
		}
		setImageDrawable(temp);
		curForcusID = tempRID;
	}

	protected void doActionUp() {
		if(style != BUTTON_STYLE_PUSH){
			setImageDrawable(disableDrawble);
		}
	}

	protected void doClick() {
	}	
	
	private void registEvent(){
		this.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean flag) {
			}
		});
		this.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				doClick();
			}
		});
		this.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					doActionDown();
					break;
				case MotionEvent.ACTION_UP:
					doActionUp();
					break;
				}
				return false;
			}
		});
	}
}


