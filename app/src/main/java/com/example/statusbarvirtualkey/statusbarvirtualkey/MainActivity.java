package com.example.statusbarvirtualkey.statusbarvirtualkey;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolBar;
    BottomNavigationView mBottomNavigationView;
    TextView home_left;
    TextView toolbar_title;
    private Fragment[]mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragments = DataGenerator.getFragments("BottomNavigationView Tab");
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        home_left = (TextView) findViewById(R.id.home_left);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        //下面的代码可以写在BaseActivity里面
        highApiEffects();
        mToolBar = (Toolbar) getWindow().findViewById(R.id.home_title);
        setSupportActionBar(mToolBar);

        init();
    }

    private void init() {
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        //mBottomNavigationView.getMaxItemCount()

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onTabItemSelected(item.getItemId());
                return true;
            }
        });
        // 由于第一次进来没有回调onNavigationItemSelected，因此需要手动调用一下切换状态的方法
        onTabItemSelected(R.id.tab_menu_home);

    }

    private void onTabItemSelected(int id){
        Fragment fragment = null;
        switch (id){
            case R.id.tab_menu_home:
                fragment = mFragments[0];
                home_left.setVisibility(View.VISIBLE);
                toolbar_title.setText("收款");
                break;
            case R.id.tab_menu_discovery:
                fragment = mFragments[1];
                home_left.setVisibility(View.VISIBLE);
                toolbar_title.setText("钱包");
                break;
            case R.id.tab_menu_attention:
                fragment = mFragments[2];
                home_left.setVisibility(View.GONE);
                toolbar_title.setText("商城");
                break;
            case R.id.tab_menu_profile:
                fragment = mFragments[3];
                home_left.setVisibility(View.GONE);
                toolbar_title.setText("我");
                break;
        }
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void highApiEffects() {
        getWindow().getDecorView().setFitsSystemWindows(true);
        //透明状态栏 @顶部
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏 @底部    这一句不要加，目的是防止沉浸式状态栏和部分底部自带虚拟按键的手机（比如华为）发生冲突，注释掉就好了
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
