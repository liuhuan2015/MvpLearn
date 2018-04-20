package com.liuh.mvplearn.service.presenter;

import com.liuh.mvplearn.service.RetrofitHelper;
import com.liuh.mvplearn.service.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by huan on 2017/11/14 10:38.
 */

public abstract class BasePresenter<V> {

    //CompositeDisposable是用来存放RxJava中的订阅关系,
    //请求完数据要及时清掉这个订阅关系，不然会发生内存泄漏
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected RetrofitService retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService.class);

    protected V mView;

    public BasePresenter(V mView) {
        this.mView = mView;
    }

    public void addSubscription(Observable<? extends Object> observable, DisposableObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        mCompositeDisposable.add(observer);
    }

    public void detachView() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

}
