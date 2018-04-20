package com.liuh.mvplearn.service.presenter;


import com.liuh.mvplearn.service.entity.Book;
import com.liuh.mvplearn.ui.iview.BookView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by huan on 2017/11/14 10:42.
 */

public class BookPresenter extends BasePresenter<BookView> {

    public BookPresenter(BookView mView) {
        super(mView);
    }

    public void getSearchBooks(String name, String tag, int start, int count) {

        addSubscription(retrofitService.getSearchBook(name, tag, start, count), new DisposableObserver<Book>() {


            @Override
            public void onNext(Book book) {
                if (book != null) {
                    mView.onSuccess(book);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.onError("请求失败 " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

}
