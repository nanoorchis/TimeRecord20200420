package com.example.android.timerecord20200420;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd ahh:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //读取变量
        resumeData();
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //测试，输出单击了按钮
                // System.out.println("-------------clickRunButton");
                //单击按钮后，根据按钮显示内容的不同来进行操作，分 开始 和 结束 两种状态
                //第一个分支执行开始时的操作
                if (((Button)v).getText().toString().equals("开始")){
                    //定义变量记录项目开始时间
                    String startTimeStr=sdf.format(new Date());
                    //输出开始的时间，用于测试
                    //System.out.println("------------"+startTimeStr);
                    //在infoText中输出开始时间和tab
                    ((EditText)findViewById(R.id.infoText)).append(startTimeStr+"\t");
                    //在startTime中显示开始时间
                    ((EditText)findViewById(R.id.startTimeText)).setText(startTimeStr);
                    //清空totalTime
                    ((EditText)findViewById(R.id.totalTimeText)).setText("");
                    //将按钮的显示内容改为 结束
                    ((Button)v).setText("结束");
                    //测试，输出按钮显示的内容
                    //System.out.println(((Button)v).getText().toString());
                    // 第二个分支执行结束时的操作
                }else if (((Button)v).getText().toString().equals("结束")){
                    //定义变量记录结束的时间和项目名称
                    String endTimeStr=(sdf.format(new Date()));
                    //定义变量保存 项目 栏 inputText 中的内容。
                    String inputTextString=((EditText)findViewById(R.id.inputText)).getText().toString();
                    //在 输出栏 infoText 中输出 结束时间
                    ((EditText)findViewById(R.id.infoText)).append(endTimeStr+"\t");
                    //定义变量保存开始时间
                    String startTimeStr=((EditText)findViewById(R.id.startTimeText)).getText().toString();
                    //将开始时间 和结束时间 转换为 Long 类型，便于计算，算出的时间单位是毫秒
                    Long startTimeL=(sdf.parse(startTimeStr,new ParsePosition(0))).getTime();
                    Long endTimeL=(sdf.parse(endTimeStr,new ParsePosition(0))).getTime();
                    //String totalTimeStr=((endTimeL-startTimeL)/1000)+"\t秒";
                    String totalTimeStr=((endTimeL-startTimeL)/1000/60)+"\t分";
                    //在 totalTime 栏输出总用时
                    ((EditText)findViewById(R.id.totalTimeText)).setText(totalTimeStr);
                    //在 输出栏 infoText 中输出 总用时
                    ((EditText)findViewById(R.id.infoText)).append("\t"+totalTimeStr+"\t");
                    //在 输出栏 infoText 中输出 项目简介
                    ((EditText)findViewById(R.id.infoText)).append("\t"+inputTextString+"\n");
                    //将按钮显示内容设置为 开始
                    ((Button)v).setText("开始");
                    //清空inputText输入栏
                    ((EditText)findViewById(R.id.inputText)).setText("");
                    //测试，输出按钮显示的内容
                    //System.out.println(((Button)v).getText().toString());
                }
            }
        });
        //为详情按钮定义操作
        findViewById(R.id.showInfoButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("-------------showInfoButton");
                //补充单击详情按钮时进行的操作
            }
        });
    }
    //保存界面中数据的方法
    public void saveData(){
        //学习完方法中参数传递后，再抽象一层，减少重复代码
        //获取preferences对象
        SharedPreferences preferences=getSharedPreferences("RecordTime",MODE_PRIVATE);
        //获取 editor对象
        SharedPreferences.Editor editor=preferences.edit();
        //保存按钮文本
        String buttonText=((Button)findViewById(R.id.startButton)).getText().toString();
        if(buttonText==null)System.out.println("buttonText is null");else{
            System.out.println("buttonText: "+buttonText);
            editor.putString("buttonText",buttonText);
        }
        //
        String inputTextString=((EditText)findViewById(R.id.inputText)).getText().toString();
        if(inputTextString==null)System.out.println("inputTextString is null");else{
            System.out.println("inputTextString: "+inputTextString);
            editor.putString("inputTextString",inputTextString);
        }
        //
        String infoTextString=((EditText)findViewById(R.id.infoText)).getText().toString();
        if(infoTextString==null)System.out.println("infoTextString is null.");else{
            System.out.println("infoTextString: "+infoTextString);
            editor.putString("infoTextString",infoTextString);
        }
        //保存开始时间
        String startTimeTextString=((EditText)findViewById(R.id.startTimeText)).getText().toString();
        if(startTimeTextString==null)System.out.println("startTimeTextString is null.");else{
            System.out.println("startTimeTextString: "+startTimeTextString);
            editor.putString("startTimeTextString",startTimeTextString);
        }
        //保存总时间
        String totalTimeTextString=((EditText)findViewById(R.id.totalTimeText)).getText().toString();
        if(totalTimeTextString==null)System.out.println("totalTimeTextString is null.");else{
            System.out.println("totalTimeTextString: "+totalTimeTextString);
            editor.putString("totalTimeTextString",totalTimeTextString);
        }
        //
        editor.commit();
    }
    //恢复显示界面中数据的方法
    public void resumeData(){
        //获取 preferences对象
        SharedPreferences preferences=getSharedPreferences("RecordTime",MODE_PRIVATE);
        //恢复
        String buttonText=preferences.getString("buttonText",null);
        if(buttonText!=null)((Button)findViewById(R.id.startButton)).setText(buttonText);
        //恢复
        String inputTextString=preferences.getString("inputTextString",null);
        if(inputTextString!=null)((EditText)findViewById(R.id.inputText)).setText(inputTextString);
        //恢复
        String infoTextString=preferences.getString("infoTextString",null);
        if(infoTextString!=null)((EditText)findViewById(R.id.infoText)).setText(infoTextString);
        //恢复开始时间
        String startTimeTextString=preferences.getString("startTimeTextString",null);
        if(startTimeTextString!=null)((EditText)findViewById(R.id.startTimeText)).setText(startTimeTextString);
        //恢复总时间
        String totalTimeTextString=preferences.getString("totalTimeTextString",null);
        if(totalTimeTextString!=null)((EditText)findViewById(R.id.totalTimeText)).setText(totalTimeTextString);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("-------------onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("-------------onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("-------------onPause");
        //需要保存变量
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("-------------onResume");
        //读取变量，更新显示
        resumeData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("-------------onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("-------------onDestroy");

    }


}
