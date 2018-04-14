package com.example.anshuman_hp.technews;

import android.os.AsyncTask;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Anshuman-HP on 27-03-2017.
 */

public class downloadTask extends AsyncTask<String,String,ArrayList<articles>> {
    @Override
    protected ArrayList<articles> doInBackground(String... params) {

        return null;
    }

}