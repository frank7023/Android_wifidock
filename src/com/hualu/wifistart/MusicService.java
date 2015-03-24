package com.hualu.wifistart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hualu.wifistart.MusicActivity;
import com.hualu.wifistart.music.LrcProcess;
import com.hualu.wifistart.music.LrcView;
import com.hualu.wifistart.music.Music;
import com.hualu.wifistart.music.MusicList;
import com.hualu.wifistart.music.LrcProcess.LrcContent;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service implements Runnable,OnErrorListener,OnCompletionListener{//

	private final String TAG = "MusicActivity";
	public static MediaPlayer player ;//= new MediaPlayer();
	private List<Music> lists;
	public static int _id = 1; // ��ǰ����λ��
	public static Boolean isRun = true;
	public LrcProcess mLrcProcess;
	public LrcView mLrcView;
	public static int playing_id = 0;
	public static Boolean playing = false;
	private SeekBarBroadcastReceiver mReceiver;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG,"music service oncreate");
		//lists = MusicList.getMusicData(getApplicationContext());
		//lists = MusicList.getPlayListMusicData(getApplicationContext());
		mReceiver = new SeekBarBroadcastReceiver();
		IntentFilter filter = new IntentFilter("com.hualu.wifistart.music.seekBar");
		registerReceiver(mReceiver, filter);
		//player = new MediaPlayer();
		new Thread(this).start();		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		
		String play = intent.getStringExtra("play");
		
		_id = intent.getIntExtra("id", 0);
		Log.i(TAG,"service start " + _id + " " + play);
		
		lists = MusicList.getPlayListMusicData(getApplicationContext());
		if (play.equals("play")) {
			if (null != player) {				
				player.release();
				player = null;
			}
			playMusic(_id);

		} else if (play.equals("pause")) {
			if (null != player) {
				playing = false;
				player.pause();
			}
		} else if (play.equals("playing")) {
			if (player != null) {
				playing = true;
				player.start();
				//lrcProcess();
			} else {
				playMusic(_id);
			}
		} else if (play.equals("replaying")) {

		} else if (play.equals("first")) {
			int id = intent.getIntExtra("id", 0);
			playMusic(id);
		} else if (play.equals("rewind")) {
			int id = intent.getIntExtra("id", 0);
			playMusic(id);
		} else if (play.equals("forward")) {
			int id = intent.getIntExtra("id", 0);
			playMusic(id);
		} else if (play.equals("last")) {
			int id = intent.getIntExtra("id", 0);
			playMusic(id);
		}

	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG,"onDestroy ");
		
		unregisterReceiver(mReceiver);
		player.release();
		player = null;
		super.onDestroy();
	}
	/*
	private void lrcProcess()
	{
		
			mLrcProcess = new LrcProcess();
			// ��ȡ����ļ�
			mLrcProcess.readLRC(lists.get(_id).getUrl());
			// ���ش����ĸ���ļ�
			lrcList = mLrcProcess.getLrcContent();
			
			if(lrcList.size() == 0){
				MusicActivity.downLrc.setVisibility(View.VISIBLE);
				MusicActivity.showLrc.setVisibility(View.GONE);
				MusicActivity.txtLyrInfo.setText("û�и��...");
			}else{
				MusicActivity.showLrc.setVisibility(View.VISIBLE);
				MusicActivity.downLrc.setVisibility(View.GONE);
				ArrayList<String> lrcs = new ArrayList<String>();
				for(int i =0;i<lrcList.size();i++){
					lrcs.add(lrcList.get(i).getLrc());
				}
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.music_lrc_item,R.id.lrcText,lrcs);
				MusicActivity.lrc_List.setAdapter(adapter);
			}	
	}*/
	private void playMusic(int id) {
		Log.i(TAG,"playMusic " + id + " " + player);
		// /////////////////////// ��ʼ��������� /////////////////////// //
		//lrcProcess();
		//MusicActivity.lrc_view.setSentenceEntities(lrcList);
		// �л���������ʾ���
		//MusicActivity.lrc_view.setAnimation(AnimationUtils.loadAnimation(
		//		MusicService.this, R.anim.alpha_z));
		
		// �����߳�
		//zhaoyu mHandler.post(mRunnable);
		// /////////////////////// ��ʼ��������� /////////////////////// //
		
		if (null != player) {
			player.release();
			player = null;
			//player = new MediaPlayer();
		}
		

		
		if (id >= lists.size() - 1) {
			_id = lists.size() - 1;
		} else if (id <= 0) {
			_id = 0;
		}
		Music m = lists.get(_id);
		String url = m.getUrl();
		Uri myUri = Uri.parse(url);
		player = new MediaPlayer();		
		player.reset();
		
		Log.i(TAG,"playMusic " + player + " " + myUri);
		//player.setAudioStreamType(AudioManager.);
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			player.setDataSource(getApplicationContext(), myUri);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.start();
		player.setOnCompletionListener(this);
		player.setOnErrorListener(this);
		playing = true;
	}
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		Log.i(TAG,"onError " + what + " " + extra);
		
		if (null != player) {
			player.release();
			player = null;
		}
		Music m = lists.get(_id);
		String url = m.getUrl();
		Uri myUri = Uri.parse(url);
		player = new MediaPlayer();
		player.reset();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			player.setDataSource(getApplicationContext(), myUri);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.start();
		return false;
	}
		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			// ��һ��
			Log.i(TAG,"onCompletion " + MusicActivity.playState + " " + _id);
			//"SINGLE_PLAY", "SINGLE_REPEAT","ALL_REPEAT","RANDOM_PLAY"
			if(MusicActivity.playState==PlayState.SINGLE_PLAY){
				//player.release();				
				//player = null;
				player.stop();
				playing = false;
				Intent intent = new Intent("com.hualu.wifistart.music.completion");
				sendBroadcast(intent);
			}else if(MusicActivity.playState==PlayState.SINGLE_REPEAT){
				player.reset();
				Intent intent = new Intent("com.hualu.wifistart.music.completion");
				sendBroadcast(intent);
				playing = false;
				playMusic(_id);
			}else if(MusicActivity.playState==PlayState.ALL_REPEAT){
				player.reset();
				Intent intent = new Intent("com.hualu.wifistart.music.completion");
				sendBroadcast(intent);
				playing = false;
				_id = _id + 1;
				if(_id>=lists.size())
					_id = 0;//_id = lists.size() - 1;
				playMusic(_id);
			}else if(MusicActivity.playState==PlayState.RANDOM_PLAY){
				player.reset();
				Intent intent = new Intent("com.hualu.wifistart.music.completion");
				sendBroadcast(intent);
				playing = false;
				Random random = new Random();
				_id = random.nextInt(lists.size());
				playMusic(_id);
			}
			/*
			if (MusicActivity.isLoop == true) {
				player.reset();
				Intent intent = new Intent("com.hualu.wifistart.music.completion");
				sendBroadcast(intent);
				_id = _id + 1;
				playMusic(_id);
			} else { // ��������
				player.reset();
				Intent intent = new Intent("com.hualu.wifistart.music.completion");
				sendBroadcast(intent);
				playMusic(_id);
			}	*/		
		}

	private class SeekBarBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int seekBarPosition = intent.getIntExtra("seekBarPosition", 0);
			// System.out.println("--------"+seekBarPosition);
			player.seekTo(seekBarPosition * player.getDuration() / 100);
			player.start();
		}

	}
	int prePosition = 0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRun) {
			try {
				Thread.sleep(400);//200
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ((null != player)&& playing) {
				try{
					int position = player.getCurrentPosition();
					int total = player.getDuration();
					if(prePosition != position){
						Intent intent = new Intent("com.hualu.wifistart.music.progress");
						intent.putExtra("position", position);
						intent.putExtra("total", total);
						sendBroadcast(intent);
						prePosition = position;
				}
				}catch(Exception e){
					
				}
			}
			/*
			if (null != player) {
				if (player.isPlaying()) {
					playing = true;
				} else {
					playing = false;
				}
			}*/
		}
	}

	Handler mHandler = new Handler();
	// ��ʹ����߳�
	Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
		//	MusicActivity.lrc_view.SetIndex(LrcIndex());
		//	MusicActivity.lrc_view.invalidate();
			mHandler.postDelayed(mRunnable, 100);
		}
	};

	// ��������
	private List<LrcContent> lrcList = new ArrayList<LrcContent>();
	// ��ʼ����ʼ���ֵ
	private int index = 0;
	// ��ʼ����������ʱ��ı���
	private int CurrentTime = 0;
	// ��ʼ��������ʱ��ı���
	private int CountTime = 0;

	/**
	 * ���ͬ��������
	 */
	public int LrcIndex() {
		if (player.isPlaying()) {
			// ��ø����������ĵ�ʱ��
			CurrentTime = player.getCurrentPosition();
			// ��ø�����ʱ�䳤��
			CountTime = player.getDuration();
		}
		if (CurrentTime < CountTime) {

			for (int i = 0; i < lrcList.size(); i++) {
				if (i < lrcList.size() - 1) {
					if (CurrentTime < lrcList.get(i).getLrc_time() && i == 0) {
						index = i;
					}
					if (CurrentTime > lrcList.get(i).getLrc_time()
							&& CurrentTime < lrcList.get(i + 1).getLrc_time()) {
						index = i;
					}
				}
				if (i == lrcList.size() - 1
						&& CurrentTime > lrcList.get(i).getLrc_time()) {
					index = i;
				}
			}
		}
		return index;
	}

}
