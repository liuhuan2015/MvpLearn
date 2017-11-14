package com.liuh.mvplearn.service.presenter;

import android.content.Intent;

import com.liuh.mvplearn.ui.iview.MView;

/**
 * Created by huan on 2017/11/14 10:38.
 */

public interface BasePresenter {

    void onCreate();

    void onStart();

    void onStop();

    void onPause();

    /**
     * 用于绑定我们自己的View
     *
     * @param view
     */
    void attachView(MView view);

    void attachIncomingIntent(Intent intent);

}
