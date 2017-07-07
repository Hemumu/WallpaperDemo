package com.helin.wallpaperdemo;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by qyhl2 on 2017/7/5.
 */

public class VideoWallpaper extends WallpaperService {

    public static final String KEY_ACTION = "action";
    public static final int ACTION_VOICE_SILENCE = 110;
    public static final int ACTION_VOICE_NORMAL = 111;
    private Camera camera;


    @Override
    public Engine onCreateEngine() {
        return new CameraEngine();
    }



    class CameraEngine extends Engine   implements Camera.PreviewCallback {

        private MediaPlayer mMediaPlayer;
        private Camera camera;

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            camera.addCallbackBuffer(data);
        }


        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            startPreview();
            // 设置处理触摸事件
            setTouchEventsEnabled(true);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            // 时间处理:点击拍照,长按拍照
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            stopPreview();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                startPreview();
            } else {
                stopPreview();
            }

//            if (visible) {
//                mMediaPlayer.start();
//            } else {
//                mMediaPlayer.pause();
//            }
        }


        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
//            mMediaPlayer = new MediaPlayer();
//            mMediaPlayer.setSurface(holder.getSurface());
//            try {
//                AssetManager assetMg = getApplicationContext().getAssets();
//                AssetFileDescriptor fileDescriptor = assetMg.openFd("test1.mp4");
//                mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
//                        fileDescriptor.getStartOffset(), fileDescriptor.getLength());
//                mMediaPlayer.setLooping(true);
//                mMediaPlayer.setVolume(0, 0);
//                mMediaPlayer.prepare();
//                mMediaPlayer.start();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }


        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
//            mMediaPlayer.release();
//            mMediaPlayer = null;

        }


        /**
         * 开始预览
         */
        public void startPreview() {

            try {
                releaseCameraAndPreview();
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } catch (Exception e) {
                Log.e(getString(R.string.app_name), "failed to open Camera");
                e.printStackTrace();
            }
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(getSurfaceHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();

        }

        private void releaseCameraAndPreview() {
            if (camera != null) {
                camera.release();
                camera = null;
            }
        }
        /**
         * 停止预览
         */
        public void stopPreview() {
            if (camera != null) {
                try {
                    camera.stopPreview();
                    camera.setPreviewCallback(null);
                    camera.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                camera = null;
            }
        }
    }
}
