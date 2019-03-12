package com.example.searchloadnamesample;

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
//    String myUrl;
//    private String juso1 = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage=1&countPerPage=10&keyword=%EA%B0%95%EC%84%9C%EB%A1%9C7%EA%B8%B8&confmKey=TESTJUSOGOKR&resultType=json";
//    private final String juso = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage=1&countPerPage=5&resultType=json";
//    private final String confmKey = "U01TX0FVVEgyMDE5MDMwNTIxMDI1NDEwODU1NzI=";
//
//    private final String regionJuso = "http://www.kma.go.kr/DFSROOT/POINT/DATA/";
//    private final String regionLastUrl = "json.txt";

    public LoadNameHttpConnection(String url) {
        try {
//            myUrl = juso + "&keyword=" + keyWord + "&confmKey=" + confmKey;
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
