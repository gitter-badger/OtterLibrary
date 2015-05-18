package com.otter.otterlibrarydemo.oconnectivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.OConnectivity;
import com.otter.otterlibrarydemo.DemoActivity;

public class OConnectivityDemo extends DemoActivity {
    private static final String BILLBOARD =
            "Detect connectivity status.";
    private static final String[] OPERATION_ITEM = {
            "Open Settings", "isConnected", "isConnectedWifi", "isConnectedMobile"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String setBillboard() {
        return BILLBOARD;
    }

    @Override
    public String[] setOperationItem() {
        return OPERATION_ITEM;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: // Open Settings
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case 1: // isConnected
                setLog("isConnected: " + OConnectivity.isConnected(getApplicationContext()));
                break;
            case 2: // isConnectedWifi
                setLog("isConnectedWifi: " + OConnectivity.isConnectedWifi(getApplicationContext()));
                break;
            case 3: // isConnectedMobile
                setLog("isConnectedMobile: " + OConnectivity.isConnectedMobile(getApplicationContext()));
                break;
            default:
                break;
        }
    }
}