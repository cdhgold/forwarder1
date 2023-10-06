package com.kmetabus.forwarder.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.gson.Gson;
import com.kmetabus.forwarder.R;
import com.kmetabus.forwarder.service.ServerResponse;
import com.kmetabus.forwarder.service.Util;
import com.kmetabus.forwarder.vo.Psale;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
개인 급매 정보 등록
 */
public class PersaleInFrg extends Fragment   {
    private EditText cont;
    private EditText nm;
    private EditText tel;
    private EditText addr;
    private Button savebtn;
    private RecyclerView recyclerView;

    private InterstitialAd mInterstitialAd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //layout
        View view = inflater.inflate(R.layout.frg_forwin, container, false); // 개인 급매 등록화면

        savebtn = (Button)view.findViewById(R.id.qsale_btn); // 저장버튼
        cont = (EditText)view.findViewById(R.id.psale_cont); // 급매정보
        nm = (EditText)view.findViewById(R.id.psale_nm); // 이름
        tel = (EditText)view.findViewById(R.id.psale_tel); // 연락처
        addr = (EditText)view.findViewById(R.id.psale_addr); // 주소
        return view;
    }
    // onCreateView() 호출 직후에 시스템에 의해 호출
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scont = cont.getText().toString();
                String snm = nm.getText().toString();
                String stel = tel.getText().toString();
                String saddr = addr.getText().toString();

                //Util.alert(getContext(), finalSeq);
                ServerResponse req = new ServerResponse();
                Psale vo = new Psale();

                vo.setCont(scont);
                vo.setNm(snm);
                vo.setTel(stel);
                vo.setAddr(saddr);

                // Create an instance of Gson
                Gson gson = new Gson();

                // Convert the VO to JSON
                String json = gson.toJson(vo);
                req.savePsale(json, new Callback<ResponseBody>() {
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String jsonString = response.body().string();
                                //Log.i("qsale 저장", jsonString);
                                Util.alert(getContext(),"저장성공!");
                                NavHostFragment.findNavController(PersaleInFrg.this)
                                        .navigate(R.id.action_perIn_to_perlist);// list로 가기
                                showAd();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    } // ok
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Handle the error here
                    } // faile

                });
            }
        });



    }
    private void showAd(){

        AdView adView = new AdView(getContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-9238000306830122/8250975861");

        // Create an ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad.
        adView.loadAd(adRequest);
        InterstitialAd.load(getContext(),"ca-app-pub-9238000306830122/8250975861", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.i("광고", "onAdLoaded");
                        // 광고가 로드된 후에 show() 메서드 호출
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(getActivity());
                        }

                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("광고", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
        }); // end

    }
}

