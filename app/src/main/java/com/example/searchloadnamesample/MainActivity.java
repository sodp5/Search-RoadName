package com.example.searchloadnamesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.searchloadnamesample.roadnamerecyclerview.RoadName;
import com.example.searchloadnamesample.roadnamerecyclerview.RoadNameRecyclerAdapter;
import com.example.searchloadnamesample.roadregioninfo.LoadRegionName;
import com.example.searchloadnamesample.roadregioninfo.Region;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LoadRegionName loadRegionName;
    private ArrayList<Region> topRegionList;
    private ArrayList<Region> mdlRegionList;

    private Spinner spnSiDo;
    private Spinner spnSiGunGu;

    private String lastClickSiDo;
    private String lastClickSiGunGu;

    private EditText edtJuso;
    private Button btnJuso;
    private TextView tvJuso;

    private RecyclerView rvRegionName;
    private RoadNameRecyclerAdapter roadNameRecyclerAdapter;
    private ArrayList<RoadName> roadNameList;

    private String resultJuso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initInstance();

        btnJuso.setOnClickListener(v -> setRoadNameList());

        setSpnSiDo();
    }

    private void initView() {
        spnSiDo = findViewById(R.id.spnSiDo);
        spnSiGunGu = findViewById(R.id.spnSiGunGu);

        edtJuso = findViewById(R.id.edtJuso);
        btnJuso = findViewById(R.id.btnJuso);
        tvJuso = findViewById(R.id.tvJuso);

        rvRegionName = findViewById(R.id.rvRegionName);
    }

    private void initInstance() {
        loadRegionName = new LoadRegionName();
        topRegionList = loadRegionName.getRegionArrayJson(); // 여기에 시/도 코드를 넣으면 시/군/구 가 나옴
    }

    private void setSpnSiDo() {
        final ArrayList<String> siDoNameList= new ArrayList<>();
        for(Region r : topRegionList)
            siDoNameList.add(r.getValue());

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, siDoNameList);

        spnSiDo.setAdapter(regionAdapter);
        spnSiDo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lastClickSiDo = siDoNameList.get(i);
                Log.d("인덱스는", lastClickSiDo);
                int clickedIndex = -1;

                for(int j = 0; j < topRegionList.size(); j++) {
                    Log.d("인덱스는", topRegionList.get(j).getValue());
                    if (topRegionList.get(j).getValue().equals(lastClickSiDo)) {

                        clickedIndex = j;
                        break;
                    }
                }
                setSpnSiGunGu(topRegionList.get(clickedIndex).getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSpnSiGunGu(String code) {
        final ArrayList<String> siGunGuNameList= new ArrayList<>();

        mdlRegionList = loadRegionName.getRegionArrayJson(Integer.parseInt(code));

        for(Region r : mdlRegionList)
            siGunGuNameList.add(r.getValue());

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, siGunGuNameList);

        spnSiGunGu.setAdapter(regionAdapter);
        spnSiGunGu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lastClickSiGunGu = siGunGuNameList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setRoadNameList() {
        if(roadNameList != null)
            roadNameList.clear();
        roadNameList = loadRegionName.getRoadNameJSON(String.format("%s", lastClickSiDo + " " + lastClickSiGunGu + " " + edtJuso.getText()));
        if(roadNameList.isEmpty())
            tvJuso.setText("주소를 찾을 수 없습니다.");
        rvRegionName.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        roadNameRecyclerAdapter = new RoadNameRecyclerAdapter(roadNameList) {
            @Override
            public void setItemClickListener(String s) {
                tvJuso.setText(s); // 테스트용 코드
//                resultJuso = s;  // 실제 사용하는 코드
            }
        };

        rvRegionName.setAdapter(roadNameRecyclerAdapter);
    }
}
