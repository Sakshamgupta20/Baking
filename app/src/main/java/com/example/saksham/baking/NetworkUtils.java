package com.example.saksham.baking;

import android.text.TextUtils;
import android.util.Log;

import com.example.saksham.baking.ingredients.Ingredients;
import com.example.saksham.baking.steps.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saksham on 16-03-2018.
 */

public class NetworkUtils {


    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";


        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Bakinga> extractBakingFromJson(String bakingJSON) {

        if (TextUtils.isEmpty(bakingJSON)) {
            return null;
        }

        List<Bakinga> baking1 = new ArrayList<>();

        ArrayList<Ingredients> ingredients=new ArrayList<>();
        ArrayList<Steps> steps=new ArrayList<>();

        try {

            JSONArray jsonArray = new JSONArray(bakingJSON);



            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject e = jsonArray.getJSONObject(i);

                String id = e.getString("id");
                String name = e.getString("name");
                String servings = e.getString("servings");
                String image = e.getString("image");


                Bakinga bakinga = new Bakinga(id, name, servings,image);

                baking1.add(bakinga);
            }
        } catch (JSONException e) {
            Log.e("NetworkUtils", "Problem parsing the JSON results", e);
        }
        return baking1;
    }

    public static List<Bakinga> fetchBakingData(String requestUrl) {

        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        List<Bakinga> baking1 = extractBakingFromJson(jsonResponse);

        return baking1;
    }
}

