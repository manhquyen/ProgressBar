package com.example.mypc.manhquyen.progressbar;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    AtomicBoolean isRunning = new AtomicBoolean();
    //int progress=0;
    TextView tvInfo,tvInfo2;
    int processid=1;
    //boolean isRunning=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        setProgressBarVisibility(true);
        setProgress(5000);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.setProgress(50);
        tvInfo=(TextView)findViewById(R.id.tv_info1);
        tvInfo2=(TextView)findViewById(R.id.tv_info2);

    }
    public void btStartProcess (View v)
    {
        Thread thread = new Thread(longTask);
        isRunning.set(true);
        thread.start();

    }
    public void btStartProcess2 (View v)
    {
        Thread thread = new Thread(longTask2);
        isRunning.set(true);
        thread.start();


    }
    public void btStopProcess (View v)
    {
        isRunning.set(false);
    }
    private Runnable longTask = new Runnable() {
        @Override
        public void run() {
            int progress=0;
            for(int i=0; i<10;i++)
            {   if (isRunning.get() == false)
                    break;
                SystemClock.sleep(250);
                progress+=10;
                progressBar.setProgress(progress);
                //String str="Thực hiện: "+progress+ "%";
                //tvInfo.setText(str); // luồng phụ k thể can thiệp hàm luồng chính trừ progressBar
                // phải gửi message đến luồng chính điều khiển


                //bước 1 : lấy đối tượng hander
                //bước 2: cập nhập thành phần giao diện đồ họa : tạo message, nhờ hander để gửi thông tiệp
                //+ bước 2a sử dụng đỗi tượng handle để tạo 1 message
                Message message = handler.obtainMessage();
                //+ bước 2b đưa các dữ liệu cần cập nhập gắn vào trong message
                //message.obj= new Integer(progress); // tạo đối tượng và gắn vào message gửi dữ liệu bằng obj
                // bước 2c sư dụng dối tượng handle để gửi message này
                //  đến hàng đợi thông điệp\
                Bundle myBundle = new Bundle();//gửi dữ liệu bằng Bundle
                myBundle.putInt("progress_key",progress);//cho vào bundle
                message.what=1;
                message.setData(myBundle);//cho vào mesage
                handler.sendMessage(message);
            }
        }
    };
    private Runnable longTask2 = new Runnable() {
        @Override
        public void run() {
            int progress=0;
            for(int i=0; i<10;i++)
            {   if (isRunning.get() == false)
                    break;
                SystemClock.sleep(250);
                progress+=10;
                progressBar.setProgress(progress);
                //String str="Thực hiện: "+progress+ "%";
                //tvInfo.setText(str); // luồng phụ k thể can thiệp hàm luồng chính trừ progressBar
                // phải gửi message đến luồng chính điều khiển


                //bước 1 : lấy đối tượng hander
                //bước 2: cập nhập thành phần giao diện đồ họa : tạo message, nhờ hander để gửi thông tiệp
                //+ bước 2a sử dụng đỗi tượng handle để tạo 1 message
                Message message = handler.obtainMessage();
                //+ bước 2b đưa các dữ liệu cần cập nhập gắn vào trong message
                //message.obj= new Integer(progress); // tạo đối tượng và gắn vào message gửi dữ liệu bằng obj
                // bước 2c sư dụng dối tượng handle để gửi message này
                //  đến hàng đợi thông điệp\
                Bundle myBundle = new Bundle();//gửi dữ liệu bằng Bundle
                myBundle.putInt("progress_key2",progress);//cho vào bundle
                message.what=2;
                message.setData(myBundle);//cho vào mesage
                handler.sendMessage(message);
            }
        }
    };
    // buoc 1 lay dieu khien handle
    // day la doi tuong quan va thao tac hang doi thong diep
    // giao dien do hoa cua Activity
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // lấy dữ liệu do luồng Backgroud gửi đến
            //Integer myProgress= (Integer)msg.obj;
            //int progress = myProgress.intValue();

            String str;

            switch (msg.what){
                case 1:
                    Bundle myBundle= msg.getData();
                    int progress = myBundle.getInt("progress_key");
                    str="Thực hiện: "+progress+ "%";
                    tvInfo.setText(str);
                case 2:
                    Bundle myBundle2= msg.getData();
                    int progress2 = myBundle2.getInt("progress2_key");
                    str="Thực hiện: "+progress2+ "%";
                    tvInfo2.setText(str);
            }
            //if(progress==100)
              //  progress=0;
        }
//Bước 3 : khi Message được xử lý thì hàm handleMessage được gọi
        // thực hiện các thao tác cập nhập giao diện trên luồng chính trong hàm này
    };
}
