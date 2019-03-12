package com.example.searchloadnamesample;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class LoadRegionName {
    private final String roadNamejuso = "http://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage=1&countPerPage=5&resultType=json";
    private final String confmKey = "U01TX0FVVEgyMDE5MDMwNTIxMDI1NDEwODU1NzI=";

    private final String regionJuso = "http://www.kma.go.kr/DFSROOT/POINT/DATA/";
    private final String regionLastUrl = ".json.txt";

    private ArrayList<Region> regionList = new ArrayList<>();
    private ArrayList<RoadName> roadNameList = new ArrayList<>();

    private LoadNameHttpConnection loadNameHttpConnection;

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    @SuppressWarnings("unchecked")
    public ArrayList<Region> getRegionArrayJson(int... code) {  // 안넣거나, 1개만 넣거나
        if (code.length == 0) {
            loadNameHttpConnection = new LoadNameHttpConnection(regionJuso + "top" + regionLastUrl);
            Log.d("현재의 url", regionJuso + "top" + regionLastUrl);
        }
        else {
            loadNameHttpConnection = new LoadNameHttpConnection(regionJuso + "mdl." + code[0] + regionLastUrl);
            Log.d("현재의 url", regionJuso + "mdl." + code[0] + regionLastUrl);
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

        ArrayList<Region> r = (ArrayList<Region>) (regionList.clone());

        Collections.sort(r, new Comparator<Region>() {
            @Override
            public int compare(Region r1, Region r2) {
                return r1.getValue().compareTo(r2.getValue());
            }
        });

        regionList.clear();

        return r;
    }

    public ArrayList<RoadName> getRoadNameJSON(String keyWord) {
        loadNameHttpConnection = new LoadNameHttpConnection(roadNamejuso + "&keyword=" + keyWord + "&confmKey=" + confmKey);
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
