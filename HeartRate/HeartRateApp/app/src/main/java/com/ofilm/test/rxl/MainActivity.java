package com.ofilm.test.rxl;



import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import com.ofilm.test.rxl.ecgview.PathView;
import com.ofilm.test.rxl.ecgview.TextBannerView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public PathView pathView;

    private TextView mHeartRate;
    private TextBannerView mMin;
    private TextBannerView mMax;
    private TextBannerView average;
    private  TextBannerView h_minimum;
    private  TextBannerView h_maximum;
    private  TextBannerView h_ave;
    private  List<String> mStringList ;

    private List<Integer> list = new ArrayList<>();
    private List<Integer> mList =new ArrayList<>();


    List<String> min1;
    List<String> max1;
    List<String> average1;
    List<String> h_minimum1;
    List<String> h_maximum1;
    List<String> h_ave1;
    public static int sum ;
    public   int min;
    public   int max;
    private int heartRate;
    private  int runCount =0;// 全局变量，用于判断是否是第一次执行
    final Handler mHandler = new Handler();
    private  TimerTask timerTask;
  //  private SharedPreferencesHelper sharedPreferencesHelper;

    private String DataBase_Name = "heart.db";
    private String Table_Name = "heartrate";
    private MyDatabaseHelper mMyDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
      //  sharedPreferencesHelper  = new SharedPreferencesHelper(MainActivity.this,"data");
        mMyDatabaseHelper = new MyDatabaseHelper(MainActivity.this,DataBase_Name,1);
        initView();
        loadData();
        getData();


        Runnable runnable = new Runnable(){
            @Override
            public void run() {
// TODO Auto-generated method stub 
                if(runCount == 1){// 执行完动画5秒后执行心率，则关闭定时执行操作 
                    Timer timer = new Timer();
                    timer.schedule(timerTask,1000,3000);
                    mHandler.removeCallbacks(this);
                }
                mHandler.postDelayed(this, 2000);
                runCount++;
            }
        };
        mHandler.postDelayed(runnable, 50);// 打开定时器，执行操作

        timerTask= new TimerTask() {
            int index = mList.size();
            @Override
            public void run() {
                Message message = new Message();
                message.what = index--;
                handler.sendMessage(message);
            }
        };

        pathView.SetData(list);
        setAverage(mList);
//        average.setText(Integer.toString(sum));
        setMin(mList);
        //mMin.setText(Integer.toString(min));
        setMax(mList);
        initData();
//        mMax.setText(Integer.toString(max));
        // setHeartRate(list);
        //mHeartRate.setText(Integer.toString(heartRate));
          saveData();
    }


    private  void initView() {

        pathView = (PathView) findViewById(R.id.pathView);
        mHeartRate=(TextView)findViewById(R.id.h_heartRate);
        mMin = (TextBannerView)findViewById(R.id.h_min);
        mMax = (TextBannerView)findViewById(R.id.h_max);
        average =(TextBannerView)findViewById(R.id.h_average);
        h_minimum =(TextBannerView)findViewById(R.id.h_minimum);
        h_maximum =(TextBannerView)findViewById(R.id.h_maximum);
        h_ave =(TextBannerView)findViewById(R.id.h_ave);
       // mHeartRate.setText("000");
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        mHeartRate.startAnimation(animation);//开始动画

    }



    private void loadData(){
        Random random = new Random();
        for(int i =0;i<20;i++){
            int num = random.nextInt(1000-375)+375;
            list.add(num);
        }
        pathView.SetData(list);

    }

    private  void  getData(){
        for(int i=0;i<list.size();i++){
            int   number = list.get(i);
            int  n = 60000/number;
            mList.add(n);
         //   Log.i("mlist","mlistmlist  "+mList);
        }
    }
    private  void  sort()

    {  min1=new ArrayList<>();
        max1=new ArrayList<>();
        average1=new ArrayList<>();
        h_minimum1=new ArrayList<>();
        h_maximum1=new ArrayList<>();
        h_ave1=new ArrayList<>();
        for(int i=0;i<mStringList.size();i++)
        {
            if(i%6==0){
                min1.add(mStringList.get(i));
            }
            else if(i%6==1)
            {
                max1.add(mStringList.get(i));
            }
            else if(i%6==2) {
                average1.add(mStringList.get(i));
            }
            else if(i%6==3){
                h_minimum1.add(mStringList.get(i));
            }
            else if(i%6==4){
                h_maximum1.add(mStringList.get(i));
            }
            else if(i%6==5){
                h_ave1.add(mStringList.get(i));
            }
        }
    }
    private  void initData() {

        mStringList = new ArrayList<>();
        mStringList.add(Integer.toString(min));
        mStringList.add(Integer.toString(max));
        mStringList.add(Integer.toString(sum));
        mStringList.add("最小");
        mStringList.add("最大");
        mStringList.add("平均");
        sort();

        /**
         * 设置数据
         */
        mMin.setDatas(min1);
        mMax.setDatas(max1);
        average.setDatas(average1);
        h_minimum.setDatas(h_minimum1);
        h_maximum.setDatas(h_maximum1);
        h_ave.setDatas(h_ave1);

    }

    private  void  saveData(){
//        sharedPreferencesHelper.put("mMin",Integer.toString(min));
//        sharedPreferencesHelper.put("mMax",Integer.toString(max));
//        sharedPreferencesHelper.put("average",Integer.toString(sum));
        ContentValues contentValues = new ContentValues();
        contentValues.put("min",Integer.toString(min));
        contentValues.put("max",Integer.toString(max));
        contentValues.put("average",Integer.toString(sum));
        SQLiteDatabase db = mMyDatabaseHelper.getWritableDatabase();
        db.insert(Table_Name,null,contentValues);
        db.close();
    }

    private void setAverage(List<Integer> data){
        for(int i=0; i<data.size();i++)
        {
            sum+=data.get(i);

          //  Log.i("b","aaaaaaaaaaaaaa"+sum);
        }
        sum=sum/data.size();
    }

    private void setMin(List<Integer> data){
        min = data.get(0);
        for (int i=1;i<data.size();i++){
            if(data.get(i)<min){
                min = data.get(i);
            }
        }
    }

    private  void setMax(List<Integer> data){
        max = data.get(0);
        for (int i= 1 ;i<data.size();i++){
            if (data.get(i)>max){
                max=data.get(i);
            }
        }
    }
    int i=0;
    private void setHeartRate(List<Integer> data){
        if(i<data.size()){
            heartRate = data.get(i);
        }
        i++;
    }


    private Handler handler = new Handler(){
        public  void handleMessage(Message message){
            if(message.what > 0){
                setHeartRate(mList);
                mHeartRate.setText(Integer.toString(heartRate));//String.format("%s",heartRate)
                mHeartRate.setTextColor(Color.WHITE);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyDatabaseHelper != null)
            mMyDatabaseHelper.close();
    }
}