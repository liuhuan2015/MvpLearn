package com.liuh.mvplearn.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.liuh.mvplearn.service.entity.Book;
import com.liuh.mvplearn.R;
import com.liuh.mvplearn.service.presenter.BookPresenter;
import com.liuh.mvplearn.ui.iview.BookView;

public class MainActivity extends AppCompatActivity implements BookView {
    TextView tvContent;
    private BookPresenter mBookPresenter = new BookPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);

        mBookPresenter.getSearchBooks("西游记", null, 0, 1);
    }


    @Override
    public void onSuccess(Book book) {
        tvContent.setText(book.toString());
    }

    @Override
    public void onError(String result) {
        tvContent.setText(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBookPresenter.detachView();
    }
}
