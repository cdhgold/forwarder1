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

        ImageView forwarder = view.findViewById(R.id.forwarder);
        // 업체정보
        forwarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MainFrg.this)
                        .navigate(R.id.action_main_to_list);
            }
        });
        // qa
        ImageView qa = view.findViewById(R.id.qa);
        qa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MainFrg.this)
                        .navigate(R.id.action_main_to_qalist);
            }
        });
        // job
        ImageView job = view.findViewById(R.id.job);
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MainFrg.this)
                        .navigate(R.id.action_main_to_perlist);
            }
        });
        // kmetabus
        ImageView kmetabus = view.findViewById(R.id.kmetabus);
        kmetabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MainFrg.this)
                        .navigate(R.id.action_main_to_perlist);
            }
        });
    }

}

