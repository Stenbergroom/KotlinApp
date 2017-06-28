package com.kotlintest.common;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kotlintest.services.lifecycle.LifecycleManager;
import com.kotlintest.services.eventbus.*;
import de.greenrobot.event.EventBus;

/**
 * Created by alex on 12/27/16.
 */

public abstract class BaseFragment extends Fragment {

    protected MaterialDialog mProgressDialog;
    protected boolean isAvailable = true;

    public final String TAG = getClass().getSimpleName();

    protected final EventBus         eventBus         = EventBusCreator.Companion.createDefault();
    private         LifecycleManager lifecycleManager = new LifecycleManager(eventBus);

    protected String screenCode;

    public boolean stopped;
    public boolean paused;
    public boolean destroyed;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
        lifecycleManager.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d(TAG, "onCreate()");
    }

    // Don't work as always overridden in subclasses.
    // Call super.onCreateView() if you want this to work.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        return null;
    }

    @Override
    public void onStart() {
        stopped = false;
        super.onStart();
        Log.d(TAG, "onStart()");
        lifecycleManager.onStart();
    }

    @Override
    public void onStop() {
        lifecycleManager.onStop();
        Log.d(TAG, "onStop()");
        super.onStop();
        stopped = true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged()");
        lifecycleManager.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDestroy() {
        destroyed = true;
        Log.d(TAG, "onDestroy()");
        lifecycleManager.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");
        lifecycleManager.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        paused = false;
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        paused = true;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

}
