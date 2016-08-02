package com.henrydyc.housefinder;

import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;


public class ResultActivity extends AppCompatActivity {

    //vp and actionBar forms the main UI skeleton
    private Toolbar toolbar;
    private ViewPager vp;
    private TabLayout tabLayout;
    private PagerAdapter viewPagerAdapter;

    String fullAddress;
    HashMap<String, String> data;
    ArrayList<Bitmap> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        // 1. get passed intent
        Intent intent = getIntent();
        data = new HashMap<String, String>();
        buildData(intent.getStringExtra("json"), data);
        images = MainActivity.images;
        fullAddress = getFullAddress(data);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        vp = (ViewPager) findViewById(R.id.pager);

        viewPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        vp.setAdapter(viewPagerAdapter);
        setSupportActionBar(toolbar);


        final TabLayout.Tab info_tab = tabLayout.newTab();
        final TabLayout.Tab history_tab = tabLayout.newTab();
        info_tab.setText("BASIC INFO");
        history_tab.setText("HISTORICAL ZESTIMATES");

        tabLayout.addTab(info_tab, 0);
        tabLayout.addTab(history_tab, 1);

        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            switch (index) {
                case 0:
                    return new InfoFragment();
                case 1:
                    return new HistoryFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2; // 2 tabs in total
        }

    }


    public HashMap<String, String> getData() {
        return data;
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    private String getFullAddress(HashMap<String, String> data) {

        return data.get("street") + ", " + data.get("city") + ", " + data.get("state")
                + "-" + data.get("zipcode");
    }

    private void buildData(String json, HashMap<String, String> data) {
        try {
            JSONObject jObj = new JSONObject(json);
            data.put("homedetails", jObj.getString("homedetails"));
            data.put("useCode", jObj.getString("useCode"));
            data.put("street", jObj.getString("street"));
            data.put("city", jObj.getString("city"));
            data.put("state", jObj.getString("state"));
            data.put("zipcode", jObj.getString("zipcode"));
            data.put("lastSoldPrice", jObj.getString("lastSoldPrice"));
            data.put("yearBuilt", jObj.getString("yearBuilt"));
            data.put("lastSoldDate", jObj.getString("lastSoldDate"));
            data.put("lotSizeSqFt", jObj.getString("lotSizeSqFt"));
            data.put("estimateLastUpdate", jObj.getString("estimateLastUpdate"));
            data.put("estimateAmount", jObj.getString("estimateAmount"));
            data.put("finishedSqFt", jObj.getString("finishedSqFt"));
            data.put("estimateValueChangeSign", jObj.getString("estimateValueChangeSign"));
            data.put("imgn", jObj.getString("imgn"));
            data.put("imgp", jObj.getString("imgp"));
            data.put("estimateValueChange", jObj.getString("estimateValueChange"));
            data.put("bathrooms", jObj.getString("bathrooms"));
            data.put("estimateValuationRangeLow", jObj.getString("estimateValuationRangeLow"));
            data.put("estimateValuationRangeHigh", jObj.getString("estimateValuationRangeHigh"));
            data.put("bedrooms", jObj.getString("bedrooms"));
            data.put("restimateLastUpdate", jObj.getString("restimateLastUpdate"));
            data.put("restimateAmount", jObj.getString("restimateAmount"));
            data.put("taxAssessmentYear", jObj.getString("taxAssessmentYear"));
            data.put("restimateValueChangeSign", jObj.getString("restimateValueChangeSign"));
            data.put("restimateValueChange", jObj.getString("restimateValueChange"));
            data.put("taxAssessment", jObj.getString("taxAssessment"));
            data.put("restimateValuationRangeLow", jObj.getString("restimateValuationRangeLow"));
            data.put("restimateValuationRangeHigh", jObj.getString("restimateValuationRangeHigh"));
            data.put("one_year", jObj.getString("one_year"));
            data.put("five_year", jObj.getString("five_year"));
            data.put("ten_year", jObj.getString("ten_year"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
