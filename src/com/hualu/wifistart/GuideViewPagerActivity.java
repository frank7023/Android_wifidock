package com.hualu.wifistart;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
  
public class GuideViewPagerActivity extends Activity implements OnClickListener, OnPageChangeListener{  
      
    private ViewPager viewPager;  
    private ViewPagerAdapter pagerAdapter;  
    private List<View> views;  
    private Button button;
      
    public String versioncode = "";
    //����ͼƬ��Դ  
    private static final int[] pics = {   
            R.drawable.guide02, R.drawable.guide03,  
            R.drawable.guide04,R.drawable.guide05 };  
      
    //�ײ�С��ͼƬ  
    private ImageView[] dots ;  
      
    //��¼��ǰѡ��λ��  
    private int currentIndex;  
      
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
          
        views = new ArrayList<View>();  
         
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  
                LinearLayout.LayoutParams.WRAP_CONTENT);  
          
        //��ʼ������ͼƬ�б�  
        for(int i=0; i<pics.length; i++) {  
            ImageView iv = new ImageView(this);  
            iv.setLayoutParams(mParams);  
            iv.setImageResource(pics[i]);  
            views.add(iv);  
        }  
        viewPager = (ViewPager) findViewById(R.id.viewpager);  
        //��ʼ��Adapter  
        pagerAdapter = new ViewPagerAdapter(views);  
        viewPager.setAdapter(pagerAdapter);  
        //�󶨻ص�  
        viewPager.setOnPageChangeListener(this);  
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				SharedPreferences shared = new SharedConfig(GuideViewPagerActivity.this).GetConfig();
				Editor editor = shared.edit();
				versioncode = getVersionName();
				editor.putString("versioncode", versioncode);
				editor.putBoolean("isFirstUse", false);
				editor.commit();
				
				Intent intent = new Intent(GuideViewPagerActivity.this,WifiStarActivity.class);
				
				GuideViewPagerActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				GuideViewPagerActivity.this.finish();
			}
        	
        });
        //��ʼ���ײ�С��  
        initDots();  
          
    }  
      
   

	private void initDots() {  
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);  
  
        dots = new ImageView[pics.length];  
  
        //ѭ��ȡ��С��ͼƬ  
        for (int i = 0; i < pics.length; i++) {  
            dots[i] = (ImageView) ll.getChildAt(i);  
            dots[i].setEnabled(true);//����Ϊ��ɫ  
            dots[i].setOnClickListener(this);  
            dots[i].setTag(i);//����λ��tag������ȡ���뵱ǰλ�ö�Ӧ  
        }  
  
        currentIndex = 0;  
        dots[currentIndex].setEnabled(false);//����Ϊ��ɫ����ѡ��״̬  
    }  
      
    /** 
     *���õ�ǰ������ҳ  
     */  
    private void setCurView(int position)  
    {  
        if (position < 0 || position >= pics.length) {  
            return;  
        }  
  
        viewPager.setCurrentItem(position);  
    }  
  
    /** 
     *��ֻ��ǰ����С���ѡ��  
     */  
    private void setCurDot(int positon)  
    {  
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {  
            return;  
        }  
  
        dots[positon].setEnabled(false);  
        dots[currentIndex].setEnabled(true);  
  
        currentIndex = positon;  
    }  
  
    //������״̬�ı�ʱ����  
    @Override  
    public void onPageScrollStateChanged(int arg0) {  
        // TODO Auto-generated method stub  
          
    }  
  
    //����ǰҳ�汻����ʱ����  
    @Override  
    public void onPageScrolled(int arg0, float arg1, int arg2) {  
        // TODO Auto-generated method stub  
          
    }  
  
    //���µ�ҳ�汻ѡ��ʱ����  
    @Override  
    public void onPageSelected(int arg0) {  
        //���õײ�С��ѡ��״̬  
        setCurDot(arg0);  
        if(arg0 == 3){
        	button.setVisibility(View.VISIBLE);
        	
        }
    }  
  
    @Override  
    public void onClick(View v) {  
        int position = (Integer)v.getTag();  
        setCurView(position);  
        setCurDot(position);
        
    }  
    
    private String getVersionName() {
	
    	try{
    		 PackageManager manager = this.getPackageManager();
    		 PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
    		 String version = info.versionName;
    		 return version;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    	
        
    }
}