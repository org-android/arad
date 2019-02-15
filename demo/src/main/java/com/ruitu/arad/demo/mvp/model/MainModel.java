package com.ruitu.arad.demo.mvp.model;

import com.ruitu.arad.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class MainModel extends BaseModel {

    public List<String> reqData1() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("" + i);
        }
        return dataList;
    }
}
