package com.kmetabus.forwarder.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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
import com.kmetabus.bugongsan.R;
import com.kmetabus.bugongsan.service.ServerResponse;
import com.kmetabus.bugongsan.service.Util;
import com.kmetabus.bugongsan.vo.ListAdapter;
import com.kmetabus.bugongsan.vo.ListItem;
import com.kmetabus.bugongsan.vo.OnListItemClickListener;

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
급매 화면
 */
public class QlistFrg extends Fragment    implements OnListItemClickListener {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private EditText tsearch;
    private Button qbtn;
    private AdView mAdView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //layout
        View view = inflater.inflate(R.layout.fragment_qlist, container, false);

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView =(AdView)view. findViewById(R.id.qadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        qbtn = (Button)view.findViewById(R.id.retrieve);
        tsearch =  (EditText)view.findViewById(R.id.qsearch);
        recyclerView = view.findViewById(R.id.qlist_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()); // 필수
        recyclerView.setLayoutManager(linearLayoutManager); // 필수
        ProgressBar progressBar = view.findViewById(R.id.qprogress_bar);

        String bunm = tsearch.getText().toString();
        // 찾기, 서버통신후(공통모듈)  목록을 보여준다.
        qbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recyclerView.setAdapter(null);
                ServerResponse req = new ServerResponse();
                req.getQsale( bunm , new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // Handle the response here
                        if(response.isSuccessful()){
                            try {
                                progressBar.setVisibility(View.VISIBLE);
                                String jsonString = response.body().string();
                                JSONArray jsonArray = new JSONArray(jsonString);
                                List<ListItem> list = new ArrayList<ListItem>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String seq =  jsonObject.getString("seq");
                                    String nm =  jsonObject.getString("nm");
                                    String juso =  jsonObject.getString("juso");
                                    String tel =  jsonObject.getString("tel");
                                    String dong =  jsonObject.getString("dong");
                                    String cont =  jsonObject.getString("cont");
                                    ListItem vo = new ListItem(seq,nm,tel,juso,dong, cont, null,false ,null);
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
        fetchData(progressBar,bunm);
        return view;

    }
    public void load(List<ListItem> list,ProgressBar progressBar){
        if(list.size() ==0){
            Util.alert(getContext(),"Data가 없습니다.");
        }else {
            listAdapter = new ListAdapter(list, this,"qsale");
            recyclerView.setAdapter(listAdapter);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onListItemClick(View v, ListItem listItem) {

    }

    public void fetchData(ProgressBar progressBar,String bnum) {
        progressBar.setVisibility(View.VISIBLE);

        // 백그라운드 스레드에서 실행
        new Thread(new Runnable() {
            @Override
            public void run() {

                getServerData(bnum,progressBar);
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

    private List<ListItem> getServerData(String bunm,ProgressBar progressBar) {
        ServerResponse req = new ServerResponse();
        Response<ResponseBody> response;
        try {
            req.getQsale( bunm , new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // Handle the response here
                    if(response.isSuccessful()){
                        try {
                            String jsonString = response.body().string();
                            JSONArray jsonArray = new JSONArray(jsonString);
                            List<ListItem> list = new ArrayList<ListItem>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String seq =  jsonObject.getString("seq");
                                String nm =  jsonObject.getString("nm");
                                String juso =  jsonObject.getString("juso");
                                String tel =  jsonObject.getString("tel");
                                String dong =  jsonObject.getString("dong");
                                String cont =  jsonObject.getString("cont");
                                ListItem vo = new ListItem(seq,nm,tel,juso,dong, cont, null,false ,null);
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

