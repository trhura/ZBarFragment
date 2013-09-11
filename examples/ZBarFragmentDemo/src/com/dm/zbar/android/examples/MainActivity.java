package com.dm.zbar.android.examples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.dm.zbar.android.scanner.ZBarFragment;

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
    }
}
