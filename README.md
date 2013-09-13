About
=====

This  is a Fragment port of [ZBarScanner](https://github.com/DushyanthMaguluru/ZBarScanner).

Usage
=====

* Add `ZBarFragment` in your Activity.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:gravity="center"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">

	.......
	
    <fragment android:name="com.trhura.android.zbar.scanner.ZBarFragment"
              android:id="@+id/scan_fragment"
              android:layout_width="fill_parent"
              android:layout_height="fill_parent"/>

</LinearLayout>
```

* Your activity needs to implement `ZBarFragment.ResultListener`, and override `onResult(String result)` method.

* Use `ZBarFragment.getScanner` to get the scanner instance.

```java
public interface ZBarScanner {
    public void startScanning ();
    public void stopScanning ();
    public boolean isScanning ();
}
```

* `ZBarFragment` will start scanning onStart, you may want to call `startScanning` / `stopScanning` methods in your Activity `onResume` / `onPause` cycles. See the example app for more details.


Credits
=======

1. CameraPreview app from Android SDK APIDemos
2. The ZBar Android SDK: http://sourceforge.net/projects/zbar/files/AndroidSDK/
3. https://github.com/DushyanthMaguluru/ZBarScanner.
