package com.example.pulltorefreshtest;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

public class MainActivity extends Activity implements OnRefreshListener{

    private PullToRefreshLayout mPullToRefreshLayout;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
        mPullToRefreshLayout = new PullToRefreshLayout(context);
        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
        ActionBarPullToRefresh.from(this)
                .allChildrenArePullable()
                .listener(this)
                .setup(mPullToRefreshLayout);

    }


    class Handler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            Log.e("MyError", "Throwable: " + e.getMessage());
            Log.e("MyError", t.toString());
        }
    }

    @Override
    public void onRefreshStarted(View view) {
        /**
         * Simulate Refresh with 4 seconds sleep
         */
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(500000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                // Notify PullToRefreshLayout that the refresh has finished
                mPullToRefreshLayout.setRefreshComplete();
            }
        }.execute();
    }

}
