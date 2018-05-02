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

     private void init() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持Rxjava
                .build();
    }
    public RetrofitService getService() {
        return mRetrofit.create(RetrofitService.class);
    }
    
发起网络请求

    public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context) {
        this.mRetrofitService = RetrofitHelper.getInstance(context).getService();
    }

    public Observable<Book> getSearchBooks(String name, String tag, int start, int count) {
        return mRetrofitService.getSearchBook(name, tag, start, count);
      }
    }
    
Presenter中的代码

    public void getSearchBooks(String name, String tag, int start, int count) {
        mCompositeSubscription.add(mDataManager.getSearchBooks(name, tag, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {
                        if (mBook != null) {
                            mBookView.onSuccess(mBook);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mBookView.onError("请求失败 " + e.getMessage());
                    }

                    @Override
                    public void onNext(Book book) {
                        mBook = book;
                    }
                }));
    }
