package com.hualu.wifistart.filecenter.filebrowser;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class SearchDialog extends Dialog { 
    private static int default_width = 175; //Ĭ�Ͽ��
    private static int default_height = 55;//Ĭ�ϸ߶�
    public SearchDialog(Context context, int layout, int style) { 
        this(context, default_width, default_height, layout, style); 
    }

    public SearchDialog(Context context, int width, int height, int layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //set width,height by density and gravity
        float density = getDensity(context);
        params.width = (int) (width*density);
        params.height = (int) (height*density);
        params.alpha = 0.7f;//͸����
        params.dimAmount =0f;//������䰵
        params.gravity = Gravity.CENTER;//������ʾ
        window.setAttributes(params);
    }
    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
       return dm.density;
    }
}
