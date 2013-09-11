package com.trhura.android.zbar.examples;

import android.os.Bundle;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import com.trhura.android.zbar.scanner.ZBarFragment;
import com.trhura.android.zbar.scanner.ZBarScanner;

public class MainActivity extends FragmentActivity implements ZBarFragment.ResultListener {

    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onResult(String result) {
        Toast toast = Toast.makeText(this, "Result = " + result, Toast.LENGTH_LONG);
        toast.show();

        /* Start scanning again */
        //ZBarScanner scanner = (ZBarScanner) getFragmentManager().findFragmentById(R.id.zbar_fragment);
        //scanner.startScanning();
    }
}
