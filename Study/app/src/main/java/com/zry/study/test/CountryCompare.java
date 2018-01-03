package com.zry.study.test;

import java.util.Comparator;

/**
 * Created by Hasee on 2017/12/11.
 */

public class CountryCompare implements Comparator<CountryDataModel> {
    @Override
    public int compare(CountryDataModel o1, CountryDataModel o2) {
        if (o1.getTag().equals("@")
                || o2.getTag().equals("#")) {
            return -1;
        } else if (o1.getTag().equals("#")
                || o2.getTag().equals("@")) {
            return 1;
        } else {
            return o1.getTag().compareTo(o2.getTag());
        }
    }
}
