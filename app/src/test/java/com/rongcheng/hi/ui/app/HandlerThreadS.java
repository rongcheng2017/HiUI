package com.rongcheng.hi.ui.app;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.ThreadPoolExecutor;

public class HandlerThreadS {

   private final static int MSG=1;
    public static void main(String[] args) {
        HandlerThread thread= new HandlerThread("concurrent-thread");
        thread.start();
        ThreadHandler handler= new ThreadHandler(thread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case MSG:
                        break;
                }
            }
        };
        handler.sendEmptyMessage(MSG);
        thread.quitSafely();
    }

    static  class ThreadHandler extends Handler{
        public ThreadHandler(@NonNull Looper looper) {
            super(looper);
        }
    }
}
