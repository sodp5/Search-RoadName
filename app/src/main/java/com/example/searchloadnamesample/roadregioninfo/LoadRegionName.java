package com.example.searchloadnamesample.roadregioninfo;

import android.util.Log;

import com.example.searchloadnamesample.httpurlconnection.LoadNameHttpConnection;
import com.example.searchloadnamesample.roadnamerecyclerview.RoadName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class LoadRegionName {
    private final static String ROAD_NAME_JUSO = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage=1&countPerPage=5&resultType=json";
    private final static String CONFM_KEY = "U01TX0FVVEgyMDE5MDMwNTIxMDI1NDEwODU1NzI=";

    private final static String REGION_JUSO = "http://www.kma.go.kr/DFSROOT/POINT/DATA/";
    private final static String REGION_LAST_URL = ".json.txt";

    private ArrayList<Region> regionList = new ArrayList<>();
    private ArrayList<RoadName> roadNameList = new ArrayList<>();

    private LoadNameHttpConnection loadNameHttpConnection;

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    @SuppressWarnings("unchecked")
    public ArrayList<Region> getRegionArray(int... code) {  // 안넣거나, 1개만 넣거나
        if (code.length == 0) {
            loadNameHttpConnection = new LoadNameHttpConnection(REGION_JUSO + "top" + REGION_LAST_URL);
            Log.d("현재의 url", REGION_JUSO + "top" + REGION_LAST_URL);
        }
        else {
            loadNameHttpConnection = new LoadNameHttpConnection(REGION_JUSO + "mdl." + code[0] + REGION_LAST_URL);
            Log.d("현재의 url", REGION_JUSO + "mdl." + code[0] + REGION_LAST_URL);
        }
        loadNameHttpConnection.execute();

        try {
            Log.d("받아온 json은?", loadNameHttpConnection.get());
            jsonArray = new JSONArray(loadNameHttpConnection.get());
            for(int i = 0; i < jsonArray.length(); i++) {
                Log.d("json의 길이는?", "" + jsonArray.length());
                jsonObject = jsonArray.getJSONObject(i);
                regionList.add(new Region(jsonObject.getString("code"), jsonObject.getString("value")));
                Log.d("넣은 region클래스는?", jsonObject.getString("code") + jsonObject.getString("value"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayList<Region> regions = (ArrayList<Region>) (regionList.clone());

        Collections.sort(regions, (r1, r2) -> r1.getValue().compareTo(r2.getValue()));

        regionList.clear();

        return regions;
    }

    public ArrayList<RoadName> getRoadName(String keyWord) {
        loadNameHttpConnection = new LoadNameHttpConnection(ROAD_NAME_JUSO + "&keyword=" + keyWord + "&confmKey=" + CONFM_KEY);
        loadNameHttpConnection.execute();

        try {
            jsonObject = new JSONObject(loadNameHttpConnection.get());
            Log.d("제이슨이다", loadNameHttpConnection.get());
            jsonObject = jsonObject.getJSONObject("results");
            jsonArray = jsonObject.getJSONArray("juso");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Log.d("제이슨이다", jsonObject.getString("roadAddr") + ", " + jsonObject.getString("jibunAddr"));
                roadNameList.add(new RoadName(jsonObject.getString("roadAddr"), jsonObject.getString("jibunAddr")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return roadNameList;
    }
}
