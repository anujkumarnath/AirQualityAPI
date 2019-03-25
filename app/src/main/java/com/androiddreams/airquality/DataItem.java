package com.androiddreams.airquality;

public class DataItem {
    private String mLocation, mCity, mCOcontent, mPM10content, mPM25content, mSO2content, mO3content;

    public DataItem(String mLocation, String mCity, String mCOcontent,
                    String mPM10content, String mPM25content,
                    String mSO2content, String mO3content) {

        this.mLocation = mLocation;
        this.mCity = mCity;
        this.mCOcontent = mCOcontent;
        this.mPM10content = mPM10content;
        this.mPM25content = mPM25content;
        this.mSO2content = mSO2content;
        this.mO3content = mO3content;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmCOcontent() {
        return mCOcontent;
    }

    public String getmPM10content() {
        return mPM10content;
    }

    public String getmPM25content() {
        return mPM25content;
    }

    public String getmSO2content() {
        return mSO2content;
    }

    public String getmO3content() {
        return mO3content;
    }
}
