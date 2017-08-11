package xyz.aungpyaephyo.padc.rxjava.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import xyz.aungpyaephyo.padc.rxjava.R;
import xyz.aungpyaephyo.padc.rxjava.RxJavaApp;
import xyz.aungpyaephyo.padc.rxjava.data.vo.RestaurantVO;
import xyz.aungpyaephyo.padc.rxjava.network.responses.RestaurantListResponse;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_text)
    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);
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
                .subscribeOn(Schedulers.io()) //run value creation code on a specific thread (non-UI thread)
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
}
