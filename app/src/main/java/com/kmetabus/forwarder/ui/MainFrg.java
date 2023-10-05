package com.kmetabus.forwarder.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kmetabus.forwarder.R;


/*
메인화면, 공인중개사, 급매화면으로 전환
 */
public class MainFrg extends Fragment   {

    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //layout
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView budong = view.findViewById(R.id.budong);
        // 공인중개사 화면
        budong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MainFrg.this)
                        .navigate(R.id.action_main_to_list);
            }
        });
        // 급매화면
        ImageView qsale = view.findViewById(R.id.qsale);
        qsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MainFrg.this)
                        .navigate(R.id.action_main_to_qlist);
            }
        });
        // 개인 급매화면
        ImageView psale = view.findViewById(R.id.persale);
        psale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MainFrg.this)
                        .navigate(R.id.action_main_to_perlist);
            }
        });
    }

}

