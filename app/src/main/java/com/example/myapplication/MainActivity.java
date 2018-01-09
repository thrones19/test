package com.example.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> deviceList = new ArrayList<>();
    private ListView deviceListview;
    private Button begain;
    private ArrayAdapter<String> adapter;
    private DeviceReceiver myDevice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVIew();

        IntentFilter filterStart = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter filterEnd = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(myDevice, filterStart);
        registerReceiver(myDevice, filterEnd);


        begain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启蓝牙
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enabler, 1);
                }
            }
        });
        mBluetoothAdapter.startDiscovery();


        //获得范围内的蓝牙设备列表
/*
        Set<BluetoothDevice> device = mBluetoothAdapter.getBondedDevices();
         if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering()){
            deviceList.clear();
            adapter.notifyDataSetChanged();
        }
        if (device.size() > 0) {
            for (Iterator<BluetoothDevice> it = device.iterator(); it.hasNext(); ) {
                BluetoothDevice btd = it.next();
                deviceList.add(btd.getName() + '\n' + btd.getAddress());
                adapter.notifyDataSetChanged();

            }
        }else{//不存在已经配对的蓝牙设备
                deviceList.add("无蓝牙设备");
                adapter.notifyDataSetChanged();

            }

    */
    }
    private  void setVIew(){
        myDevice = new DeviceReceiver();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        deviceListview = (ListView)findViewById(R.id.lv_blueDevice);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,deviceList);
        deviceListview.setAdapter(adapter);
        begain = (Button) findViewById(R.id.bt_enabler_blueT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                mBluetoothAdapter.enable();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myDevice);
        super.onDestroy();
    }

    private class DeviceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)){//搜索到新设备
                BluetoothDevice btd = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //搜索没有配过对的蓝牙设备
                if (btd.getBondState() != BluetoothDevice.BOND_BONDED){
                    deviceList.add(btd.getName()+'\n'+btd.getAddress());
                    adapter.notifyDataSetChanged();

                }
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){//搜索结束
                if (deviceListview.getCount() == 0){
                    deviceList.add("没有搜到蓝牙...");
                    adapter.notifyDataSetChanged();
                }

            }
        }
    }

}
