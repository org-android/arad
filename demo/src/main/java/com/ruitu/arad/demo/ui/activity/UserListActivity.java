package com.ruitu.arad.demo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.ruitu.arad.base.base_list.BaseRefreshActivity;
import com.ruitu.arad.base.base_list.ListBaseAdapter;
import com.ruitu.arad.base.base_list.SuperViewHolder;
import com.ruitu.arad.demo.R;
import com.ruitu.arad.demo.mvp.model.UserModel;
import com.ruitu.arad.demo.mvp.presenter.UserPresenter;
import com.ruitu.arad.support.widget.progress.ProgressLayout;
import com.ruitu.router_module.bean.User;
import com.ruitu.router_module.util.RcvUtils;

import java.util.ArrayList;
import java.util.List;

//模拟用户列表页面 很多用户,20个一页
public class UserListActivity extends BaseRefreshActivity<UserPresenter, UserModel> {
    private List<User> userList = new ArrayList<>();

    private int currPage = 1;
    private UserListAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeadTitle("用户列表");
        getUserList();
        RcvUtils.refreshCompleteDelay(lRecyclerView, null);
    }

    @Override
    protected RecyclerView.Adapter initAdapter() {
        adapter = new UserListAdapter(this);
        return adapter;
    }

    @Override
    protected LRecyclerView findRecyclerView() {
        return findViewById(R.id.lrcv_user);
    }

    private void getUserList() {
        showProgress();
        p.getUserList(currPage);
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

    @Override
    protected ProgressLayout findProgressLayout() {
        return findViewById(R.id.progress);
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
