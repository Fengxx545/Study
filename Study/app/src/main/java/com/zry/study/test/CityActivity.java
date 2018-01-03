package com.zry.study.test;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zry.extendlibrary.DefaultTitleBar;
import com.zry.extendlibrary.OtherActivity;
import com.zry.study.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hasee on 2017/12/9.
 */

public class CityActivity extends OtherActivity {
    private RecyclerView mCityList;
    private SlideBarView mSlideBarView;
    private RelativeLayout mCityLayout;





    private LinearLayoutManager mLinearLayoutManager;
    private SortAdapter mSortAdapter;
    private String[] mAllDataList;
    private List<CountryDataModel> mCountryDataList;
    private CountryCompare mCountryCompare;
    private TextView mCenterView;


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_city);
    }

    @Override
    protected void initTitle() {
        DefaultTitleBar defaultTitleBar = new DefaultTitleBar.Builder(this,
                (ViewGroup) findViewById(R.id.root_view))
                .setTitle("好人")
                .builder();
    }

    @Override
    protected void initView() {
        mCityList = (RecyclerView) findViewById(R.id.city_list);
        mSlideBarView = (SlideBarView) findViewById(R.id.cityLetterListView);
        mCityLayout = (RelativeLayout) findViewById(R.id.city_layout);
        mCenterView = (TextView) findViewById(R.id.center_view);

    }

    @Override
    protected void initData() {
        mCountryCompare = new CountryCompare();
        mCountryDataList = fillData(getResources().getStringArray(R.array.country));
        Collections.sort(mCountryDataList,mCountryCompare);
        //RecyclerView社置manager
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCityList.setLayoutManager(mLinearLayoutManager);
        mSortAdapter = new SortAdapter(this, mCountryDataList);
        mCityList.setAdapter(mSortAdapter);

        mSlideBarView.setCenterView(mCenterView);

        mSlideBarView.setOnTouchingBarChangedListener(new SlideBarView.OnTouchingBarChangedListener() {
            @Override
            public void onTouchingBarChanged(String s) {
                int pos = mSortAdapter.getFirstPosition(s.charAt(0));
                if (pos != -1) {
                    mLinearLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        });


    }

    private List<CountryDataModel> fillData(String[] stringArray) {
        List<CountryDataModel> countryDataList = new ArrayList<>();

        for (String s : stringArray) {
            CountryDataModel model = new CountryDataModel();
            model.setCountry(s);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(s);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                model.setTag(sortString.toUpperCase());
            } else {
                model.setTag("#");
            }
            countryDataList.add(model);
        }
        return countryDataList;
    }

}
