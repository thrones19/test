package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by opple on 17/9/22.
 */

public class SecondActivity extends AppCompatActivity {


    private List<String> list = new ArrayList<>();
    private Button bt_add ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        bt_add = (Button) findViewById(R.id.bt_addnumber);


     for (int i=0; i<4 ;i++){
         list.add("第"+i+"个数据");list.add("第"+i+"个数据");

         Log.d("main",list.get(0));
         if (list.size() == 3){
             list.remove(0);
             Log.d("remove","*******应该是null**"+list.get(0)+"***应该是第1个数据*"+list.get(1));
         }
     }


    }

}
