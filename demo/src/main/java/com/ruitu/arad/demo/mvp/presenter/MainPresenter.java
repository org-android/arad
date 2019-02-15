package com.ruitu.arad.demo.mvp.presenter;

import com.ruitu.arad.base.BasePresenter;
import com.ruitu.arad.demo.MainActivity;
import com.ruitu.arad.demo.mvp.model.MainModel;

import java.util.List;

public class MainPresenter extends BasePresenter<MainActivity, MainModel> {
    public void getData1() {
        List<String> data1 = m.reqData1();
        v.onReqComplete(1, true, data1);
    }
}
