# react-native-wifi-checker

Get a list of available Wifi connections

... but only for Android right now

## Installation

```
npm install --save react-native-wifi-checker
```

### Android

Add the following lines to `android/settings.gradle`

```
include ':react-native-wifi-checker'
project(':react-native-wifi-checker').projectDir = new File(settingsDir, '../node_modules/react-native-wifi-checker/android')
```

Add also in your dependencies in `android/app/build.gradle`

```
compile project(':react-native-wifi-checker')
```

Don't forget to register the module in `MainActivity.java`

```java
import com.rayglaeske.react.wifi.WifiCheckerPackage;  // <--- import

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {
  ......

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mReactRootView = new ReactRootView(this);

    mReactInstanceManager = ReactInstanceManager.builder()
      .setApplication(getApplication())
      .setBundleAssetName("index.android.bundle")
      .setJSMainModuleName("index.android")
      .addPackage(new MainReactPackage())
      .addPackage(new WifiCheckerPackage()) // <------ add this line to yout MainActivity class
      .setUseDeveloperSupport(BuildConfig.DEBUG)
      .setInitialLifecycleState(LifecycleState.RESUMED)
      .build();

    mReactRootView.startReactApplication(mReactInstanceManager, "AndroidRNSample", null);

    setContentView(mReactRootView);
  }

  ......

}
```

### iOS

So I have to check ObjectiveC right?

## Usage

```js
var wifi = require('react-native-wifi-checker');

// get the current wifi list
wifi.getWifiList()
  .then((wifiList) => {
    // do something with the list for example
    console.log(wifiList);
  }).done();

// I also implented a scan
wifi.scan()
  .then((scanned) => {
    if (scanned)
      console.log('cool we did a scan');
  }).done();
```
