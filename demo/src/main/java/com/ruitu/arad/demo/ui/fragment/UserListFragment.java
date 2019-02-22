package com.ruitu.arad.demo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.ruitu.arad.base.base_list.BaseRefreshFragment;
import com.ruitu.arad.base.base_list.ListBaseAdapter;
import com.ruitu.arad.base.base_list.SuperViewHolder;
import com.ruitu.arad.demo.R;
import com.ruitu.arad.demo.mvp.model.UserModel;
import com.ruitu.arad.demo.mvp.presenter.UserPresenter;
import com.ruitu.arad.support.widget.progress.ProgressLayout;
import com.ruitu.router_module.bean.User;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends BaseRefreshFragment<UserPresenter, UserModel> {

    private List<User> userList = new ArrayList<>();
    private int currPage = 1;
    private UserListAdapter adapter;

    public static UserListFragment newInstance() {
        Bundle args = new Bundle();
        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initViews(View v) {
        getUserList();
    }

    @Override
    protected ProgressLayout findProgressLayout(View v) {
        return v.findViewById(R.id.progress);
    }

    @Override
    protected LRecyclerView findRecyclerView(View v) {
        return v.findViewById(R.id.lcv_list);
    }

    @Override
    protected RecyclerView.Adapter initAdapter() {
        adapter = new UserListAdapter(getActivity());
        return adapter;
    }

    private void getUserList() {
        showProgress();
        rxList.add(p.getUserList(currPage));
    }

    @Override
    protected void reqFirst() {
        currPage = 1;
        userList.clear();
        getUserList();
    }

    @Override
    protected void reqMore() {
        currPage++;
        if (currPage >= 5) {
            refreshNoMoreData();
        } else {
            getUserList();
        }
    }

    @Override
    public void onReqComplete(int code, boolean isOk, Object data) {
        if (code == 1) {
            List<User> newData = (List<User>) data;
            userList.addAll(newData);
            adapter.setDataList(userList);
        }
    }

    private class UserListAdapter extends ListBaseAdapter<User> {

        public UserListAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_user_list;
        }

        @Override
        public void onBindItemHolder(SuperViewHolder holder, int position) {
            TextView tv_name = holder.getView(R.id.tv_name);
            TextView tv_id = holder.getView(R.id.tv_id);

            User u = mDataList.get(position);
            tv_name.setText(u.getUserName());
            tv_id.setText(u.getId());
        }
    }
}
