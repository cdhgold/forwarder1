package com.kmetabus.forwarder.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kmetabus.forwarder.R;
import com.kmetabus.forwarder.service.ServerResponse;
import com.kmetabus.forwarder.service.Util;
import com.kmetabus.forwarder.vo.ListAdapter;
import com.kmetabus.forwarder.vo.ListItem;
import com.kmetabus.forwarder.vo.OnListItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
포워더 업체정보
 */
public class ListFrg extends Fragment  implements OnListItemClickListener {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private EditText search_forw;
    private EditText search_addr;
    private Button sbtn; // 조회 버튼
    private Button inbtn; // 급매정보 등록 버튼
    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //layout
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        mAdView =(AdView)view. findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        search_forw = (EditText)view.findViewById(R.id.search1);// 업체명으로 검색
        search_addr = (EditText)view.findViewById(R.id.search2);// 주소로 검색
        sbtn = (Button)view.findViewById(R.id.retrieve);
        recyclerView = view.findViewById(R.id.list_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()); // 필수
        recyclerView.setLayoutManager(linearLayoutManager); // 필수
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);


        // 찾기, 서버통신후(공통모듈)  목록을 보여준다. 버튼으로 조회시
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recyclerView.setAdapter(null);
                String forwarder = search_forw.getText().toString();
                String addr = search_addr.getText().toString();
                ServerResponse req = new ServerResponse();

                req.getForwarder(forwarder,addr , new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // Handle the response here
                        if(response.isSuccessful()){
                            try {
                                progressBar.setVisibility(View.VISIBLE);
                                String jsonString = response.body().string();
                                JSONArray jsonArray = new JSONArray(jsonString);
                                List<ListItem>  list = new ArrayList<ListItem>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String seq =  jsonObject.getString("seq");
                                    String nm =  jsonObject.getString("cnm");
                                    String juso =  jsonObject.getString("juso");
                                    String tel =  jsonObject.getString("tel");
                                    String hp =  jsonObject.getString("hp");
                                    ListItem vo = new ListItem(seq,nm,tel,juso,hp, null,null,false ,null);
                                    list.add(vo);

                                }
                                load(list,progressBar);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Handle the error here
                    }
                });
            }
        });
        fetchData(progressBar ); // default
        return view;

    }
    public void load(List<ListItem> list,ProgressBar progressBar){
        if(list.size() ==0){
            Util.alert(getContext(),"Data가 없습니다.");
        }else {
            listAdapter = new ListAdapter(list, this, null);
            recyclerView.setAdapter(listAdapter);
        }
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onListItemClick(View v, ListItem listItem) {
    }
    private void fetchData(ProgressBar progressBar ) {
        progressBar.setVisibility(View.VISIBLE);

        // 백그라운드 스레드에서 실행
        new Thread(new Runnable() {
            @Override
            public void run() {

                getServerData( progressBar);
                // UI 스레드에서 실행
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    private List<ListItem> getServerData( ProgressBar progressBar) {
        ServerResponse req = new ServerResponse();
        Response<ResponseBody> response;
        try {
            String forwarder = search_forw.getText().toString();
            String addr = search_addr.getText().toString();
            req.getForwarder(forwarder,addr , new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // Handle the response here
                    if(response.isSuccessful()){
                        try {
                            progressBar.setVisibility(View.VISIBLE);
                            String jsonString = response.body().string();
                            JSONArray jsonArray = new JSONArray(jsonString);
                            List<ListItem>  list = new ArrayList<ListItem>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String seq =  jsonObject.getString("seq");
                                String nm =  jsonObject.getString("cnm");
                                String juso =  jsonObject.getString("juso");
                                String tel =  jsonObject.getString("tel");
                                String hp =  jsonObject.getString("hp");
                                ListItem vo = new ListItem(seq,nm,tel,juso,hp, null,null,false ,null);
                                list.add(vo);

                            }
                            load(list,progressBar);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Handle the error here
                }
            });
        } catch ( Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

