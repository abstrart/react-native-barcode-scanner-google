package com.ekreutz.barcodescanner;

import android.util.Log;

import com.ekreutz.barcodescanner.ui.BarcodeScannerView;
import com.ekreutz.barcodescanner.util.BarcodeFormat;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * A companion module to the ViewManager BarcodeScannerManager.
 * This module is used to invoke some native methods of the BarcodeScannerView.
 * (Native methods can't be invoked from ViewManagers)
 */

public class BarcodeScannerModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private BarcodeScannerManager mBarcodeScannerManager;

    public BarcodeScannerModule(ReactApplicationContext reactContext, BarcodeScannerManager barcodeScannerManager) {
        super(reactContext);

        reactContext.addLifecycleEventListener(this);
        mBarcodeScannerManager = barcodeScannerManager;
    }

    /**
     * The name intended for React Native.
     */
    @Override
    public String getName() {
        return "RCTBarcodeScannerModule";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("BarcodeType", BarcodeFormat.REVERSE_FORMATS);
                put("FocusMode", getFocusModes());
            }
        });
    }

    private static Map<String, Integer> getFocusModes() {
        return Collections.unmodifiableMap(new HashMap<String, Integer>() {
            {
                put("AUTO", 0);
                put("TAP", 1);
                put("FIXED", 2);
            }
        });
    }

    /* ----------------------------------------------
     * ------------- Methods for JS -----------------
     * ---------------------------------------------- */

    @ReactMethod
    public void start(Promise promise) {
        Log.d("BARCODETYPE", "Calling start.");

        if (start())
            promise.resolve(null);
        else
            promise.reject("1", "Start: Scanner view was null...");
    }

    @ReactMethod
    public void resume(Promise promise) {
        if (resume())
            promise.resolve(null);
        else
            promise.reject("2", "Resume: Scanner view was null...");
    }

    @ReactMethod
    public void pause(Promise promise) {
        if (pause())
            promise.resolve(null);
        else
            promise.reject("3", "Pause: Scanner view was null...");
    }

    /* ----------------------------------------------
     * ------------- Lifecycle events ---------------
     * ---------------------------------------------- */

    @Override
    public void onHostResume() {
        resume();
    }

    @Override
    public void onHostPause() {
        pause();
    }

    @Override
    public void onHostDestroy() {
        release();
    }


    /* ----------------------------------------------
     * ------------- Utility methods ----------------
     * ---------------------------------------------- */

    private boolean start() {
        BarcodeScannerView view = mBarcodeScannerManager.getBarcodeScannerView();

        if (view != null) {
            view.start();
        }

        return view != null;
    }

    private boolean resume() {
        BarcodeScannerView view = mBarcodeScannerManager.getBarcodeScannerView();

        if (view != null) {
            view.resume();
        }

        return view != null;
    }

    private boolean pause() {
        BarcodeScannerView view = mBarcodeScannerManager.getBarcodeScannerView();

        if (view != null) {
            view.pause();
        }

        return view != null;
    }

    private boolean release() {
        BarcodeScannerView view = mBarcodeScannerManager.getBarcodeScannerView();

        if (view != null) {
            view.release();
        }

        return view != null;
    }
}