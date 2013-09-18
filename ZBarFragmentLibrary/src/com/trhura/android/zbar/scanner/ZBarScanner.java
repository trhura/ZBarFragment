package com.trhura.android.zbar.scanner;

/**
 * Created with IntelliJ IDEA.
 * User: trhura
 * Date: 9/11/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ZBarScanner {
    public void startScanning ();
    public void stopScanning ();
    public boolean isScanning ();

    public void setModes (int[] modes);
}
