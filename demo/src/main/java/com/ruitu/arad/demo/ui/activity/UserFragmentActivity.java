package com.ruitu.arad.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.ruitu.arad.base.BaseActivity;
import com.ruitu.arad.demo.R;
import com.ruitu.arad.demo.ui.fragment.UserListFragment;

public class UserFragmentActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHeadTitle("用户列表fragment");
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, UserListFragment.newInstance());
        ft.commit();
//        LocalBroadcastManager.getInstance(this).
    }
}
