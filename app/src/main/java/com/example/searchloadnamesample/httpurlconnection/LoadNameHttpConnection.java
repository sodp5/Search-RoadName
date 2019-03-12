package com.example.searchloadnamesample.httpurlconnection;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadNameHttpConnection extends AsyncTask<Void, Void, String> {
    private HttpURLConnection httpURLConnection;
    private URL url;

    public LoadNameHttpConnection(String url) {
        try {
            this.url = new URL(url);
            httpURLConnection = (HttpURLConnection) this.url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private String connect(){
        String line ="";
        String data ="";
        BufferedReader bufferedReader = null;

        try {
            httpURLConnection.connect();

            InputStream inputStream=httpURLConnection.getInputStream();
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            while((line=bufferedReader.readLine())!=null){
                data+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return connect();
    }
}
