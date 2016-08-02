package com.henrydyc.housefinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONException;


import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private static String street;
    private static String city;
    private static String state;
    private static String JsonResult;
    private static JSONObject jObj;
    public static ArrayList<Bitmap> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.state);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetErrMsg();
                street = ((EditText) findViewById(R.id.street)).getText().toString();
                city = ((EditText) findViewById(R.id.city)).getText().toString();
                state = ((Spinner) findViewById(R.id.state)).getSelectedItem().toString();

                if (validateInput(street, city, state) == true) {
                    new JSONRetriever().execute();
                } //else do nothing
            }
        });

    }



    private class JSONRetriever extends AsyncTask<Void, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {

            JsonResult = getResultFromHttpGet(street, city, state);
            if (JsonResult.equals("Failed")) {
                try {
                    jObj = new JSONObject();
                    jObj.put("query_status", "network_error");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    jObj = new JSONObject(JsonResult);
                    images = new ArrayList<Bitmap>();
                    images.add(downloadImage(jObj.getString("one_year")));
                    images.add(downloadImage(jObj.getString("five_year")));
                    images.add(downloadImage(jObj.getString("ten_year")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return jObj;
        }

        @Override
        protected void onPostExecute(JSONObject jObj) {
            try {

                String query_status = jObj.getString("query_status");
                if (query_status.equals("network_error")) {
                    TextView err = (TextView) findViewById(R.id.err);
                    err.setText("There is a network error. Please try again");

                } else if (query_status.equals("failed")) {
                    TextView err = (TextView) findViewById(R.id.err);
                    err.setText("No exact match found--Verify that the given address is correct.");
                } else { // "successful"

                    //pass data to ResultActivity for display
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("json", jObj.toString());
                    Log.d("DEBUG", "after putExtra json");

                    Log.d("DEBUG", "after putExtra images");

                    MainActivity.this.startActivity(intent);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private static Bitmap downloadImage(String src) {
        Bitmap img = null;
        try {
            img = BitmapFactory.decodeStream((InputStream) new URL(src).getContent());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return img;
    }

    private String getResultFromHttpGet(String street, String city, String state) {
        String ret = "";
        InputStream is = null;
        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority("54.183.165.25")
                .path("HouseFinder.php")
                .appendQueryParameter("street", street)
                .appendQueryParameter("state", state)
                .appendQueryParameter("submit", "submit")
                .build();

        try {
            URL url = new URL(uri.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(20000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

            Reader reader = null;
            reader = new InputStreamReader(is, "UTF-8");
            final int MAX_BUF_LENGTH = 10000;
            char[] buffer = new char[MAX_BUF_LENGTH];
            reader.read(buffer);
            ret =  new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;

    }

    private void resetErrMsg() {
        TextView street_err = (TextView) findViewById(R.id.street_err);
        street_err.setVisibility(TextView.GONE);
        TextView city_err = (TextView) findViewById(R.id.city_err);
        city_err.setVisibility(TextView.GONE);
        TextView state_err = (TextView) findViewById(R.id.state_err);
        state_err.setVisibility(TextView.GONE);
        TextView err = (TextView) findViewById(R.id.err);
        err.setText("");
    }


    private boolean validateInput(String street, String city, String state) {
        boolean validateResult = true;

        if (street.length() == 0) {
            TextView street_err = (TextView) findViewById(R.id.street_err);
            street_err.setVisibility(TextView.VISIBLE);
            validateResult = false;
        }

        if (city.length() == 0) {
            TextView city_err = (TextView) findViewById(R.id.city_err);
            city_err.setVisibility(TextView.VISIBLE);
            validateResult = false;
        }

        if (state.equals("Choose State")) {
            TextView state_err = (TextView) findViewById(R.id.state_err);
            state_err.setVisibility(TextView.VISIBLE);
            validateResult = false;
        }

        return validateResult;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}

