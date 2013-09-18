package com.trhura.android.zbar.examples;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import com.trhura.android.zbar.scanner.ZBarConstants;
import com.trhura.android.zbar.scanner.ZBarFragment;
import com.trhura.android.zbar.scanner.ZBarScanner;

public class MainActivity extends FragmentActivity implements ZBarFragment.ResultListener {

    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;
    private static final String TAG = "ZBarFragment";
    private GestureDetector gestureDetector;
    private ZBarScanner scanner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(this, new GestureListener());
        setContentView(R.layout.main);

        FragmentManager manager = getSupportFragmentManager();
        ZBarFragment fragment = (ZBarFragment) manager.findFragmentById(R.id.scan_fragment);
        scanner = fragment.getScanner();

        int[] modes = new int[] {ZBarConstants.CODE128, ZBarConstants.QRCODE};
        scanner.setModes(modes);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setLabel(getString(R.string.scan_label));

        // start scanning only after a tap
        hideOverlay();
        scanner.stopScanning();
    }

    @Override
    public void onPause()
    {
        super.onResume();

        if (scanner != null && scanner.isScanning()) {
            scanner.stopScanning();
        }
    }

    @Override
    public void onResult(String result) {
        hideOverlay();
        setLabel(getString(R.string.scan_result_label) + result);
        gestureDetector = null; // disable single tap while showing result

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setLabel(getString(R.string.scan_label) + " again");
                gestureDetector = new GestureDetector(MainActivity.this, new GestureListener());
            }
        }, 3 * 1000);
    }

    @Override
    public boolean onTouchEvent (MotionEvent e)
    {
        if (gestureDetector != null)
            return gestureDetector.onTouchEvent (e);

        return super.onTouchEvent(e);
    }

    private void setLabel (String label)
    {
        TextView labelview = (TextView) findViewById(R.id.scan_label);
        labelview.setText(label);
    }

    private void hideOverlay ()
    {
        View overlay = findViewById(R.id.overlay_rectangle);
        overlay.setVisibility(View.INVISIBLE);
    }

    private void showOverlay ()
    {
        View overlay = findViewById(R.id.overlay_rectangle);
        overlay.setVisibility(View.VISIBLE);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed (MotionEvent e)
        {
            Log.d(TAG, "Single Tap Confirmed.");
            if (scanner == null || scanner.isScanning())
                return false;

            showOverlay();
            scanner.startScanning();
            setLabel(getString(R.string.scanning_label));
            Log.d(TAG, "Ok, scanning now.");

            return true;
        }
    }
}

