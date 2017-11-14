package com.liuh.mvplearn.service.manager;

import android.content.Context;

import com.liuh.mvplearn.service.RetrofitHelper;
import com.liuh.mvplearn.service.RetrofitService;
import com.liuh.mvplearn.service.entity.Book;

import rx.Observable;

/**
 * Created by huan on 2017/11/14 10:19.
 */

public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context) {
        this.mRetrofitService = RetrofitHelper.getInstance(context).getService();
    }

    public Observable<Book> getSearchBooks(String name, String tag, int start, int count) {
        return mRetrofitService.getSearchBook(name, tag, start, count);

    }
}
