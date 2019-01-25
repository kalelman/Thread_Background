package com.kalelman.threadbackground;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.button)
    Button btnStart;
    @BindView(R.id.button2)
    Button butStop;

    private Handler mainHandler = new Handler();
    private volatile  boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void listenerStartThread() {
        stopThread = false;

        ExampleRunnable runnable = new ExampleRunnable(10);
        new Thread(runnable).start();

        /*
        ExampleThread thread = new ExampleThread(10);
        thread.start();
        */

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                // work
                executeThread(10);
            }
        }).start();
        */
    }

    @OnClick(R.id.button2)
    public void listenerStopThread() {
        stopThread = true;
    }

    private void executeThread(int seconds) {
        for (int i = 0; i <= seconds; i++) {
            if (stopThread)
                return;
            if (i == 5) {
                //Method 1 to do and execute the Thread with the UI Main Thread
                /*Handler threadHandler = new Handler(Looper.getMainLooper());
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        btnStart.setText("50%");
                    }
                });*/

                //Method 2 to do and execute the Thread with the UI Main Thread
                /*btnStart.post(new Runnable() {
                    @Override
                    public void run() {
                        btnStart.setText("50%");
                    }
                });*/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnStart.setText("50%");
                    }
                });
            }
            Log.d(TAG,"StartThread: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class ExampleThread extends Thread {
        int seconds;

        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            executeThread(seconds);
        }
    }

    public class ExampleRunnable implements Runnable {
        int seconds;

        ExampleRunnable(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            executeThread(seconds);
        }
    }
}
