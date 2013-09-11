package com.dm.zbar.android.examples;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarFragment;
import net.sourceforge.zbar.Symbol;

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
