package com.comment.tek.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.comment.tek.base.BaseFragment;
import com.comment.tek.fragment.DiscoveryFragment;
import com.comment.tek.fragment.DynamicFragment;
import com.comment.tek.fragment.HomePageFragment;
import com.comment.tek.fragment.MineFragment;
import com.comment.tek.widget.buttomIndictor.DMTabHost;

public class MainActivity extends FragmentActivity implements DMTabHost.OnCheckedChangeListener {
    private int currentIndex = 0;
    private DMTabHost mTabHost;
    private BaseFragment[] mFmTabs;
    private long firstPressedTime;
    public static final String INDEX = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //保存非正常情况下关闭的状态。
        if (null != savedInstanceState) {
            currentIndex = savedInstanceState.getInt(INDEX);
        }
        showControl(currentIndex);
    }

    /**
     * 所显示的Fragment
     *
     * @param currentIndex
     */
    private void showControl(int currentIndex) {
        // 初始化所要显示的fragment
        mFmTabs = new BaseFragment[]{new HomePageFragment(), new DiscoveryFragment(), new DynamicFragment(), new MineFragment()};
        MainActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.fm_content, mFmTabs[currentIndex]).show(mFmTabs[currentIndex]).commit();
        mTabHost = findViewById(R.id.tab_host);
        //启动时默认选择首页
        mTabHost.setChecked(0);
        //可以设置纬度消息，小红点提示
//        mTabHost.setHasNew(2, true);
        //设置未读消息，红色圆圈加未读条目
//        mTabHost.setUnreadCount(2, 2);
        mTabHost.setOnCheckedChangeListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(INDEX, currentIndex);
        super.onSaveInstanceState(outState, outPersistentState);
    }


    /**
     * 界面之间的切换
     *
     * @param index
     */
    public void currentTab(int index) {
        if (currentIndex != index) {
            FragmentTransaction ft = MainActivity.this.getSupportFragmentManager().beginTransaction();
            ft.hide(mFmTabs[currentIndex]);
            BaseFragment fragment = mFmTabs[index];
            if (mFmTabs[index].isAdded()) {
                ft.show(fragment);
                ft.commit();
            } else {
                ft.add(R.id.fm_content, fragment).show(fragment);
                ft.commit();
            }
            currentIndex = index;
        }
    }

    @Override
    public void onCheckedChange(int checkedPosition, boolean byUser) {
//        if (checkedPosition == 2) {
//            mTabHost.setHasNew(2, false);//只要点击了，就设置为false,没有数据时设置也不会有问题
//            mTabHost.setUnreadCount(2, -1);
//        }
        currentTab(checkedPosition);
    }


    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - firstPressedTime) < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstPressedTime = System.currentTimeMillis();
        }
    }

}
