package com.hualu.wifistart.smbsrc.widgets;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class CustomLayout extends ViewGroup {

	public Map<View, RectF> layoutValue = new HashMap<View, RectF>();

	public CustomLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomLayout(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			RectF layout = (RectF) layoutValue.get(childView);
			if (layout != null) {
				Log.d("CustomLayout layout ", childView.toString() + ":"
						+ layout.toString());
				if( childView instanceof Button){
					((Button) childView).setGravity(Gravity.CENTER);
				}else if( childView instanceof ListView){
					childView.measure((int) (layout.width()),(int) (layout.height()));
				}
				childView.layout((int) layout.left, (int) layout.top,
						(int) layout.right, (int) layout.bottom);
			}
		}
	}
}
