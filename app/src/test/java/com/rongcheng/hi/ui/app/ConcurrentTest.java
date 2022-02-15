package com.rongcheng.hi.ui.app;

import android.os.AsyncTask;
import android.util.Log;


public class ConcurrentTest {
    private static final String TAG = "ConcurrentTest";

    public static void main(String[] args) {
        class MyAsyncTask extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String... strings) {
                for (int i = 0; i < 10; i++) {
                    publishProgress(i * 10);
                }
                return strings[0];
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                Log.e(TAG, "onProgressUpdate: " + values[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                Log.e(TAG, "onPostExecute: " + s);
            }
        }
        MyAsyncTask myAsyncTask=new MyAsyncTask();
        myAsyncTask.execute("execute myasynctask");
    }


}
