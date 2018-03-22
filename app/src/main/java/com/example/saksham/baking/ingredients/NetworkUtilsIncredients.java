package com.example.saksham.baking.ingredients;

import android.text.TextUtils;
import android.util.Log;

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
 * Created by Saksham on 17-03-2018.
 */

public class NetworkUtilsIncredients {
    private static final String LOG_TAG = NetworkUtilsIncredients.class.getSimpleName();

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

    private static List<Ingredients> extractBakingFromJson(String bakingJSON, int pos) {

        if (TextUtils.isEmpty(bakingJSON)) {
            return null;
        }



        List<Ingredients> ingredients=new ArrayList<>();

        try {

            JSONArray jsonArray = new JSONArray(bakingJSON);

            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject e = jsonArray.getJSONObject(i);

                String idS = e.getString("id");
                int id=Integer.parseInt(idS);

                if(id==pos)
                {
                    JSONArray in=e.getJSONArray("ingredients");
                    for(int j = 0; j < in.length(); j++)
                    {
                        JSONObject inr = in.getJSONObject(j);
                        String quantity=inr.getString("quantity");
                        String measure=inr.getString("measure");
                        String ingredient=inr.getString("ingredient");
                        Ingredients bakinga = new Ingredients(quantity,measure,ingredient);
                        ingredients.add(bakinga);

                    }
                }

            }
        } catch (JSONException e) {
            Log.e("NetworkUtils", "Problem parsing the JSON results", e);
        }
        return ingredients;
    }

    public static List<Ingredients> fetchBakingInData(String requestUrl,int pos) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        List<Ingredients> baking1 = extractBakingFromJson(jsonResponse,pos);

        return baking1;
    }
}
