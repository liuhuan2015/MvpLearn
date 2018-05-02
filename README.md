# MvpLearn
学习mvp，retrofit，rxjava。
学习的blog地址是http://www.jianshu.com/p/7b839b7c5884。
自己照着博客手写了一遍代码。写代码的时候感觉这个框架挺好的，完全可以拿来直接作为一个新项目的框架。
下面贴一些自己觉得需要注意的代码。


接口定义：

    public interface RetrofitService {
    //https://api.douban.com/v2/book/search?q=西游记&tag=&start=0&count=1
    @GET("book/search")
    Observable<Book> getSearchBook(@Query("q") String name,
                                   @Query("tag") String tag,
                                   @Query("start") int start,
                                   @Query("count") int count);
    }
    
retrofit初始化配置

    public static Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            OkHttpClient.Builder mBuild = new OkHttpClient.Builder();//使用这个mBuild可以做一些对请求进行拦截的事情
            OkHttpClient mOkHttpClient = mBuild.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("https://api.douban.com/v2/")
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持Rxjava
                    .build();
        }
        return mRetrofit;
    }
    
发起网络请求

MainActivity中部分代码：
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);

        mBookPresenter.getSearchBooks("西游记", null, 0, 1);
    }
    
Presenter中的代码

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
