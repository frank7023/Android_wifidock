package com.hualu.wifistart.update;
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;  
import java.net.URL;  

import org.json.JSONArray;
import org.json.JSONObject;

import com.hualu.wifistart.R;
import com.hualu.wifistart.custom.HuzAlertDialog;
import com.hualu.wifistart.update.NetworkTool;
import com.hualu.wifistart.update.UpdateNew;
 
import android.app.Dialog;  
import android.content.Context;  
import android.content.DialogInterface;  
import android.content.Intent;  
import android.content.DialogInterface.OnClickListener;  
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;  
import android.os.Environment;
import android.os.Handler;  
import android.os.Message;  
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.View;  
import android.widget.ProgressBar;
  
public class UpdateManager {
	public static final int DOWN_UPDATE = 1;
    public static final int DOWN_OVER = 2;
	private static final String TAG = "UpdateConfig";
  
    //private SettingActivity mSettingActivity;
    private Context mContext;
    private Handler mHandler;
      
    private Dialog downloadDialog;        
    private static String saveFileName = null;
    private ProgressBar mProgress;      
    public static int progress;  
      
    private Thread downLoadThread;  
      
    private boolean interceptFlag = false;
    
    private Handler loadHandler = new Handler(){  
        public void handleMessage(Message msg) {  
            switch (msg.what) {  
            case DOWN_UPDATE:  
                mProgress.setProgress(progress);  
                break;  
            case DOWN_OVER:                    
                installApk();  
                break;  
            default:  
                break;  
            }  
        };  
    };
      
    public UpdateManager(Context context) {  
        this.mContext = context;
    }
    public int getVerCode(Context context) {
        int verCode = -1;
        try {
                verCode = context.getPackageManager().getPackageInfo(
                                "com.hualu.wifistart", 0).versionCode;
        } catch (NameNotFoundException e) {
                Log.e(TAG, e.getMessage());
        }
        return verCode;
    }
    
  //wdx	add	1213
    public boolean getServerVerCode(Handler handler) {
    	//��ʾProgressDialog
    	this.mHandler = handler;
        try {
                //String verjson = NetworkTool.getContent(mSettingActivity.UPDATE_SERVER + mSettingActivity.UPDATE_VERJSON);
        	String verjson = NetworkTool.getContent("http://www.hualudigital.com/download/verjson.txt");
                JSONArray array = new JSONArray(verjson);
                if (array.length() > 0) {
                        JSONObject obj = array.getJSONObject(0);
                        try {
                        	UpdateNew.newVerCode = Integer.parseInt(obj.getString("verCode"));
                        	UpdateNew.newVerName = obj.getString("verName");
                        } catch (Exception e) {
                        	UpdateNew.newVerCode = -1;
                        	UpdateNew.newVerName = "";
                            return false;
                        }
                }
        } catch (Exception e) {
                //Log.e(TAG, e.getMessage());
                mHandler.sendEmptyMessage(UpdateNew.CONNECT_FAIL);
                return false;
        }
        mHandler.sendEmptyMessage(UpdateNew.CHECK_OVER);
        return true;
    }

    public String getVerName(Context context) {
        String verName = "";
        try {
                verName = context.getPackageManager().getPackageInfo(
                                "com.hualu.wifistart", 0).versionName;
        } catch (NameNotFoundException e) {
                Log.e(TAG, e.getMessage());
        }
        return verName; 

    }

    public static String getAppName(Context context) {
        String verName = context.getResources()
        .getText(R.string.app_name).toString();
        return verName;
    }
    public void showDownloadDialog(){
    	HuzAlertDialog.Builder builder = new HuzAlertDialog.Builder(mContext);
        builder.setTitle("�������");
          
        final LayoutInflater inflater = LayoutInflater.from(mContext);  
        View v = inflater.inflate(R.layout.progress, null);  
        mProgress = (ProgressBar)v.findViewById(R.id.progress);  
          
        builder.setView(v);
        builder.setNegativeButton(R.string.set_cancel, new OnClickListener() {   
            @Override  
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();  
                interceptFlag = true;  
            }  
        });  
        downloadDialog = builder.create();  
        downloadDialog.show();  
          
        downloadApk();  
    }  
      
    private Runnable mdownApkRunnable = new Runnable() {      
        @Override  
        public void run() {  
            try {
                // �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��  
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))  
                {  
                    // ��ô洢����·��  
                    String sdpath = Environment.getExternalStorageDirectory() + "/";  
                    saveFileName = sdpath + "download";  
                    URL url = new URL("http://www.hualudigital.com/download/WiFiDock.apk");  
                    // ��������  
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
                    conn.connect();  
                    // ��ȡ�ļ���С  
                    int length = conn.getContentLength();  
                    // ����������  
                    InputStream is = conn.getInputStream();  
  
                    File file = new File(saveFileName);  
                    // �ж��ļ�Ŀ¼�Ƿ����  
                    if (!file.exists())  
                    {  
                        file.mkdir();  
                    }  
                    File apkFile = new File(saveFileName, "WiFiDock.apk");  
                    FileOutputStream fos = new FileOutputStream(apkFile);  
                    int count = 0;  
                    // ����  
                    byte buf[] = new byte[1024];  
                    // д�뵽�ļ���  
                    do  
                    {  
                        int numread = is.read(buf);  
                        count += numread;  
                        // ���������λ��  
                        progress = (int) (((float) count / length) * 100);  
                        // ���½���  
                        loadHandler.sendEmptyMessage(DOWN_UPDATE);  
                        if (numread <= 0)  
                        {  
                            // �������  
                        	loadHandler.sendEmptyMessage(DOWN_OVER);  
                            break;  
                        }  
                        // д���ļ�  
                        fos.write(buf, 0, numread);  
                    } while (!interceptFlag);// ���ȡ����ֹͣ����.  
                    fos.close();  
                    is.close();  
                } 
            } catch (MalformedURLException e) {  
                e.printStackTrace();  
            } catch(IOException e){  
                e.printStackTrace();  
            }  
              
        }  
    };
    
    public void NoticeDialog()
    {
    	mHandler.sendEmptyMessage(UpdateNew.UPDATE_ABLE);
    }
    public void NewVersionShow()
    {
    	mHandler.sendEmptyMessage(UpdateNew.UPDATE_UNABLE);
    }
     /** 
     * ����apk 
     * @param url 
     */  
      
    public void downloadApk(){  
        downLoadThread = new Thread(mdownApkRunnable);  
        downLoadThread.start();  
    }  
     /** 
     * ��װapk 
     * @param url 
     */  
    public void installApk(){
        File apkfile = new File(Environment.getExternalStorageDirectory(),"download/" + "WiFiDock.apk");  
        if (!apkfile.exists()) {  
            return;  
        }      
        Intent i = new Intent(Intent.ACTION_VIEW);  
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");   
        mContext.startActivity(i);
    }
}
