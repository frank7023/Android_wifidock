package com.hualu.wifistart;

import java.io.IOException;  
import java.util.List;

import android.content.Context;  
import android.content.res.Configuration;
import android.hardware.Camera;  
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;  
import android.util.Log;  
import android.view.SurfaceHolder;  
import android.view.SurfaceView;

public class CameraSurfacePreview extends SurfaceView implements SurfaceHolder.Callback{
	private SurfaceHolder mHolder;  
    public Camera mCamera;
	private Camera.PictureCallback pictureCallback;//zhaoyou0402
  
    @SuppressWarnings("deprecation")
	public CameraSurfacePreview(Context context,Camera.PictureCallback pictureCallback) {
        super(context);  
  
        // Install a SurfaceHolder.Callback so we get notified when the  
        // underlying surface is created and destroyed.  
        mHolder = getHolder();  
        mHolder.addCallback(this);  
        // deprecated setting, but required on Android versions prior to 3.0  
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);  
		this.pictureCallback = pictureCallback;
    }  
  
    public void surfaceCreated(SurfaceHolder holder) {  
          
        Log.d("Dennis", "surfaceCreated() is called");  
          
        try {  
            // Open the Camera in preview mode
            mCamera = Camera.open();
            mCamera.setPreviewDisplay(holder);  
        } catch (IOException e) {  
            Log.d("Dennis", "Error setting camera preview: " + e.getMessage());  
        }  
    }  
      
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {  
          
        Log.d("Dennis", "surfaceChanged() is called");  
          
        try {  
        	//����Camera�Ĳ���
        	Camera.Parameters parameters = mCamera.getParameters();
        	//����豸֧�ֵ�PreviewSizes
        	int num;
        	List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();  
        	//Iterator<Camera.Size> itor1 = sizeList.iterator();
        	if(sizeList.get(0).width>sizeList.get(1).width){
        		parameters.setPreviewSize(sizeList.get(0).width, sizeList.get(0).height);
        		Log.i("camera","setpreviewsize1 " + sizeList.get(0).width + " " + sizeList.get(0).height);
        	}
        	else
        	{
        		for(num = 0; num < sizeList.size() - 1; num++);
        			parameters.setPreviewSize(sizeList.get(num).width, sizeList.get(num).height);
        		Log.i("camera","setpreviewsize2 " + sizeList.get(sizeList.size() - 1).width + " " + sizeList.get(sizeList.size() - 1).height);
        	}
        	
        	if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
        	{
	        	//���������
	        	parameters.set("orientation", "portrait");
	        	//��2.2���Ͽ���ʹ��
	        	mCamera.setDisplayOrientation(90);
        	}
        	else
        	{
	        	parameters.set("orientation", "landscape");
	        	//��2.2���Ͽ���ʹ��
	        	mCamera.setDisplayOrientation(0);
        	}
        	parameters.setFocusMode("auto");//zhaoyu0402
        	mCamera.setParameters(parameters);
        	try {
        	//������ʾ
        	mCamera.setPreviewDisplay(holder);
        	} catch (IOException exception) {
        	mCamera.release();
        	mCamera = null;
        	}
        	//��ʼԤ��
            mCamera.startPreview();  
  
        } catch (Exception e){  
            Log.d("Dennis", "Error starting camera preview: " + e.getMessage());  
        }
    }  
  
    public void surfaceDestroyed(SurfaceHolder holder) {  
        if (mCamera != null) {  
            mCamera.stopPreview();  
            mCamera.release();  
            mCamera = null;  
        }  
          
        Log.d("Dennis", "surfaceDestroyed() is called");  
    }  
	//zhaoyu0402
	/**
	 * �Զ��Խ����ص�Camera.PreviewCallback
	 */
	public void AutoFocusAndPreviewCallback()
	{
		if(mCamera!=null)
			mCamera.autoFocus(mAutoFocusCallBack);
	}
	
	/**
     * �Զ��Խ�
     */
    private Camera.AutoFocusCallback mAutoFocusCallBack = new Camera.AutoFocusCallback() {  
    	  
        @Override  
        public void onAutoFocus(boolean success, Camera camera) {   
        	Log.i("test","onAutoFocus " + success);
            if (success) {  //�Խ��ɹ����ص�Camera.PreviewCallback
            	mCamera.takePicture(null,null,pictureCallback); 
            	//mCamera.setOneShotPreviewCallback(null);
            }  
        }  
    };
	//zhaoyu0402
    /*  
    public void takePicture(PictureCallback imageCallback) {  
        mCamera.takePicture(null, null, imageCallback);
        mCamera.autoFocus(new MyAutoFocusCallback());
    }
    private class MyAutoFocusCallback implements AutoFocusCallback{
    	@Override
    	public void onAutoFocus(boolean success,Camera camera)
    	{
    		camera.takePicture(null, null, null, null);
    	}
    }*/
    
}
