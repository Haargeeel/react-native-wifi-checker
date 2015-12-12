package com.rayglaeske.react.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public class WifiCheckerModule extends ReactContextBaseJavaModule {

    public WifiCheckerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "WifiChecker";
    }

    @ReactMethod
    public void getWifiList(Promise promise) {
        StringBuilder response = new StringBuilder();
        WifiManager wifiManager = (WifiManager) getReactApplicationContext().getSystemService(Context.WIFI_SERVICE);

        WritableArray wifiList = Arguments.createArray();
        for (int i = 0; i < wifiManager.getScanResults().size(); i++) {
            ScanResult result = wifiManager.getScanResults().get(i);
            WritableMap model = Arguments.createMap();
            model.putString("SSID", result.SSID);
            model.putString("BSSID", result.BSSID);
            model.putString("capabilities", result.capabilities);
            model.putInt("level", WifiManager.calculateSignalLevel(result.level, 100));
            wifiList.pushMap(model);
        }
        if (wifiList.size() > 0) {
          promise.resolve(wifiList);
        } else {
            promise.reject("No found wifi connections");
        }
    }

    @ReactMethod
    public void scan(Promise promise) {
        WifiManager wifiManager = (WifiManager) getReactApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.startScan()) {
            promise.resolve(true);
        } else {
            promise.reject("Can not scan for wifi");
        }
    }

    class WifiModel {
        public String SSID;
        public String BSSID;
        public String capabilities;
        public int level;
        public WifiModel(String SSID, String BSSID, String capabilities, int level) {
            this.SSID = SSID;
            this.BSSID = BSSID;
            this.capabilities = capabilities;
            this.level = level;
        }
    }
}
