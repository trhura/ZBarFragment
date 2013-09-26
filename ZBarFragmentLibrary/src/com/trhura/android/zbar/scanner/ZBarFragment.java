package com.trhura.android.zbar.scanner;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.sourceforge.zbar.*;

/**
 * Created with IntelliJ IDEA.
 * User: trhura
 * Date: 9/11/13
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ZBarFragment extends Fragment implements Camera.PreviewCallback, ZBarScanner {
    static final String TAG = "ZBarFragment";
    private CameraPreview preview;
    private Camera camera;
    private ImageScanner scanner;
    private ResultListener listener;

    static
    {
        System.loadLibrary("iconv");
    }

    @Override
    public void startScanning() {
        /* try opening the default camera */
        camera = Camera.open();

        if (camera == null) {
            Log.w (TAG, "Unable to open default camera.");

            /* open any camera */
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            int cameraCount = Camera.getNumberOfCameras();

            for ( int i = 0; i < cameraCount; i++ ) {
                try {
                    camera = Camera.open(i);
                    break;
                } catch (RuntimeException e) {
                    Log.w (TAG, "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        /* camera still null? probably no camera */
        if (camera == null) {
            Log.e (TAG, "Unable to open any camera");
            return;
        }

        /* Changed Parameters */

        try {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setSceneMode(Camera.Parameters.SCENE_MODE_BARCODE);
            camera.setParameters(parameters);
        } catch (Exception e){
            Log.w (TAG, "SceneMode Barcode Failed: " + e.getMessage());
        }

        try {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
            camera.setParameters(parameters);
        } catch (Exception e){
            Log.w (TAG, "Focus Mode MACRO Failed: " + e.getMessage());
        }


        /* show preview */
        preview.showSurfaceView(camera);
    }

    @Override
    public synchronized void stopScanning() {
        if (camera != null) {
            preview.hideSurfaceView();
            camera.cancelAutoFocus();
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public ZBarScanner getScanner(){
        return (ZBarScanner) this;
    }

    @Override
    public boolean isScanning() {
        return (camera != null);
    }

    @Override
    public void setModes (int[] modes) {
        if (modes != null) {
            scanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
            for (int mode : modes) {
                scanner.setConfig(mode, Config.ENABLE, 1);
            }
        }
    }

    public interface ResultListener
    {
        public void onResult(String result);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        /* TODO: Check whether there is a camera available */

        if (!(activity instanceof ResultListener)) {
            Log.e (TAG, "The attached activity must implement ResultListener.");
            throw new RuntimeException(
                    "The attached activity must implement ResultListener."
            );
        }

        listener = (ResultListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        preview = new CameraPreview (getActivity(), this);
        setupScanner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return preview;
    }

    private void setupScanner()
    {
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);
    }


    @Override
    public void onStart()
    {
        super.onStart();
        Log.d (TAG, "Starting Fragment" + TAG);
        startScanning();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "Resuming Fragment" + TAG);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d (TAG, "Pausing Fragment" + TAG);

        /* In case, the activity forgets to stop */
        stopScanning();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera)
    {
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = parameters.getPreviewSize();

        Image barcode = new Image(size.width, size.height, "Y800");
        barcode.setData(data);

        int result = scanner.scanImage(barcode);

        if (result != 0) {
            stopScanning();

            SymbolSet syms = scanner.getResults();
            Log.d (TAG, "Got some results, looking for non-empty string...");
            for (Symbol sym : syms) {
                String symData = sym.getData();
                if (!TextUtils.isEmpty(symData)) {
                    listener.onResult(symData);
                    break;
                }
            }
        }
    }
}
