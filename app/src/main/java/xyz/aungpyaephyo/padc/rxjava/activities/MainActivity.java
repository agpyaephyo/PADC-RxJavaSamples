package xyz.aungpyaephyo.padc.rxjava.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import xyz.aungpyaephyo.padc.rxjava.R;
import xyz.aungpyaephyo.padc.rxjava.RxJavaApp;
import xyz.aungpyaephyo.padc.rxjava.data.vo.RestaurantVO;
import xyz.aungpyaephyo.padc.rxjava.network.responses.RestaurantListResponse;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_text)
    TextView tvText;

    private PublishSubject<Integer> mTestSubject;
    private int mValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        mTestSubject = PublishSubject.create();
        mTestSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                tvText.setText("Operation has finished. The value is " + integer + "\n");

                /*
                switch (integer) {
                    case 1:
                        tvText.setText("Yayyyy. We are one.");
                        break;
                    case 2:
                        tvText.setText("Hmmmm, two. Still acceptable though.");
                        break;
                    case 3:
                        tvText.setText("Ok people. WTF. We are dripping off. Three ?");
                        break;
                    case 4:
                        tvText.setText("Listen up, unless we keep push up in next time, we are screwed.");
                        break;
                    case 5:
                        tvText.setText("We are screwed.");
                        break;
                    default:
                        tvText.setText("We are officially irrelevant now.");
                }
                */
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onTapFab(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.btn_in_code_one)
    public void onTapBtnInCodeOne(View view) {
        tvText.setText("");
        helloRxJava("the", "real", "PADC", ":", "Android", "Developer", "Course");
    }

    @OnClick(R.id.btn_in_code_two)
    public void onTapBtnInCodeTwo(View view) {
        tvText.setText("");
        Observable<RestaurantListResponse> restaurantListResponseObservable = getRestaurantListResponseObservable();
        restaurantListResponseObservable
                .subscribeOn(Schedulers.io()) //run "value creation code" on a specific thread (non-UI thread)
                .observeOn(AndroidSchedulers.mainThread()) //observe the emitted value of the Observable on an appropriate thread
                .subscribe(new Observer<RestaurantListResponse>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RestaurantListResponse restaurantListResponse) {
                        for (RestaurantVO restaurant : restaurantListResponse.getRestaurantList()) {
                            tvText.setText(tvText.getText() + "Rx Api : \"" + restaurant.getTitle() + "\"" + " has " + restaurant.getTagList().size() + " special meals.\n");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        tvText.setText("onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.btn_in_code_three)
    public void onTapBtnInCodeThree(View view) {
        operationExecution();
    }

    @OnClick(R.id.btn_in_code_four)
    public void onTapBtnInCodeFour(View view) {
        mValue++;
        mTestSubject.onNext(mValue);
    }

    @OnClick(R.id.btn_in_code_five)
    public void onTapBtnInCodeFive(View view) {
        tvText.setText("");
        Observable<RestaurantListResponse> restaurantListResponseObservable = getRestaurantListResponseObservable();
        restaurantListResponseObservable
                .subscribeOn(Schedulers.io()) //run value creation code on a specific thread (non-UI thread)
                .map(new Function<RestaurantListResponse, List<RestaurantVO>>() {
                    @Override
                    public List<RestaurantVO> apply(@NonNull RestaurantListResponse restaurantListResponse) throws Exception {
                        return restaurantListResponse.getRestaurantList();
                    }
                })
                .flatMap(new Function<List<RestaurantVO>, ObservableSource<RestaurantVO>>() {
                    @Override
                    public ObservableSource<RestaurantVO> apply(@NonNull List<RestaurantVO> restaurantVOs) throws Exception {
                        return Observable.fromIterable(restaurantVOs);
                    }
                })
                .filter(new Predicate<RestaurantVO>() {
                    @Override
                    public boolean test(@NonNull RestaurantVO restaurant) throws Exception {
                        return !TextUtils.isEmpty(restaurant.getTitle());
                    }
                })
                .take(5)
                .doOnNext(new Consumer<RestaurantVO>() {
                    @Override
                    public void accept(@NonNull RestaurantVO restaurantVO) throws Exception {
                        Log.d(RxJavaApp.TAG, "Saving restaurant" + restaurantVO.getTitle() + " info into disk");
                    }
                })
                .map(new Function<RestaurantVO, String>() {
                    @Override
                    public String apply(@NonNull RestaurantVO restaurant) throws Exception {
                        String readableText = "Rx Api : \"" + restaurant.getTitle() + "\"" + " has " + restaurant.getTagList().size() + " special meals.\n";
                        return readableText;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //observe the emitted value of the Observable on an appropriate thread
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String readableText) {
                        tvText.setText(tvText.getText() + readableText);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void helloRxJava(String... names) {
        Observable<String> nameObservable = Observable.fromArray(names);
        nameObservable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String name) {
                Log.d(RxJavaApp.TAG, "Rx : \"" + name + "\"" + " has " + name.length() + " characters.");
                tvText.setText(tvText.getText() + "Rx : \"" + name + "\"" + " has " + name.length() + " characters.\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private Observable<RestaurantListResponse> getRestaurantListResponseObservable() {
        RxJavaApp rxJavaApp = (RxJavaApp) getApplication();
        return rxJavaApp.getRestaurantApi().getRestaurantList();
    }

    private Integer operation() {
        try {
            // "Simulate" the delay.
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int operationResult = 100;
        return operationResult;
    }

    private void operationExecution() {
        tvText.setText("");
        Single<Integer> integerSingle = Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return operation();
            }
        });

        integerSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        tvText.setText("Operation has finished. The value is " + integer + "\n");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

        recursiveLogger(1);

    }

    private void recursiveLogger(final int index) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvText.setText(tvText.getText() + "Time waited " + index + ") " + (index + 500) + "\n");
                if (index < 20) {
                    recursiveLogger(index + 1);
                }
            }
        }, 500);
    }
}
