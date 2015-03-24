package com.hualu.wifistart.wifisetting.utils;

import com.hualu.wifistart.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����Toast��һ�������࣬������Ҫ�������˶�ϵͳToast�������޸�
 * @author Administrator
 *
 */
public class ToastBuild {
	
	/**
	 * 
	 * @param context �����Ķ���
	 * @param msg Ҫ��ʾ����Ϣ
	 * @param timeTag ʱ����� ���ǡ�s����ʾ��ʱ����ʾ 
	 * 						     ���ǡ�l����СдL����ʾ��ʱ����ʾ
	 */
	public static void toast(Context context, int msg){
		/*
		int time = Toast.LENGTH_SHORT;
		if(timeTag == null || "l".equals(timeTag)){
			time = Toast.LENGTH_LONG;
		}
		Toast toast = Toast.makeText(context, null, time);
		LinearLayout layout = (LinearLayout)toast.getView(); 
		layout.setBackgroundResource(R.drawable.title2);
		//layout.setOrientation(LinearLayout.HORIZONTAL);
		//layout.setGravity(Gravity.CENTER_VERTICAL);
		layout.setGravity(Gravity.CENTER);
		TextView tv = new TextView(context);
		tv.setLayoutParams(new  LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));   
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(Color.parseColor("#ffffffff"));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		tv.setPadding(0, 0, 0, 0);
		tv.setText(msg);
		layout.addView(tv);
		toast.show();
		*/
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View toastView = inflater.inflate(R.layout.toastbuild, null);
		Toast toast=new Toast(context.getApplicationContext());
		toast.setView(toastView);
		TextView tv=(TextView)toastView.findViewById(R.id.TextViewInfo);
		tv.setText(msg);
		toast.show();
	}
	public static void toast(Context context, String msg){
		/*
		int time = Toast.LENGTH_SHORT;
		if(timeTag == null || "l".equals(timeTag)){
			time = Toast.LENGTH_LONG;
		}
		Toast toast = Toast.makeText(context, null, time);
		LinearLayout layout = (LinearLayout)toast.getView(); 
		layout.setBackgroundResource(R.drawable.title2);
		//layout.setOrientation(LinearLayout.HORIZONTAL);
		//layout.setGravity(Gravity.CENTER_VERTICAL);
		layout.setGravity(Gravity.CENTER);
		TextView tv = new TextView(context);
		tv.setLayoutParams(new  LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));   
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(Color.parseColor("#ffffffff"));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		tv.setPadding(0, 0, 0, 0);
		tv.setText(msg);
		layout.addView(tv);
		toast.show();
		*/
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View toastView = inflater.inflate(R.layout.toastbuild, null);
		Toast toast=new Toast(context.getApplicationContext());
		toast.setView(toastView);
		TextView tv=(TextView)toastView.findViewById(R.id.TextViewInfo);
		tv.setText(msg);
		toast.show();
	}
}
