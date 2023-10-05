package com.kmetabus.forwarder.vo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.kmetabus.bugongsan.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<ListItem> listItems;   // list 항목들
    private OnListItemClickListener onListItemClickListener;    // click interface
    private String gbn;
    public ListAdapter(List<ListItem> listItems, OnListItemClickListener onListItemClickListener,String gbn) {
        this.listItems = listItems;
        this.onListItemClickListener = onListItemClickListener;
        this.gbn = gbn;
    }
    public ListAdapter(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;

    }
    public void addItems(List<ListItem> list ){
        this.listItems.addAll(list);
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        if("qsale".equals(this.gbn) ){
            view = inflater.inflate(R.layout.qlist_item, parent, false);
        }else{
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        return new ListViewHolder(view,onListItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        // 각항목을 ListViewHolder 여기서 정의해야한다.
        ListItem listitem = listItems.get(position);
        holder.seq.setText(listitem.getSeq());
        holder.col1.setText(listitem.getCol1());
        holder.col2.setText(listitem.getCol2());
        holder.col3.setText(listitem.getCol3());
        holder.col4.setText(listitem.getCol4());
        holder.col5.setText(listitem.getCol5());
        holder.inbtn.setOnClickListener(new View.OnClickListener() {// 급매등록 화면 으로 전환
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생 시 해당 항목의 seq 값을 얻습니다.
                String seq = listitem.getSeq();
                try {
                    // Bundle을 통해 seq 값을 전달할 수 있습니다.
                    Bundle bundle = new Bundle();
                    bundle.putString("seq", seq);
                    // Navigate 메소드에 bundle을 추가합니다.
                    Navigation.findNavController(v).navigate(R.id.action_main_to_qsalein, bundle);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        int itemsize = 0;
        if(listItems != null){
            itemsize =listItems.size();
        }
        return itemsize;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button inbtn; // 급매 버튼
        TextView seq;
        TextView col1;
        TextView col2;
        TextView col3;
        TextView col4;
        TextView col5;
        ImageView ListItemImg;
        OnListItemClickListener onListItemClickListener;
        public ListViewHolder(@NonNull View itemView, OnListItemClickListener onListItemClickListener) {
            super(itemView);
            inbtn = itemView.findViewById(R.id.inbtn);
            seq = itemView.findViewById(R.id.seq);
            col1 = itemView.findViewById(R.id.col1);
            col2 = itemView.findViewById(R.id.col2);
            col3 = itemView.findViewById(R.id.col3);
            col4 = itemView.findViewById(R.id.col4);
            col5 = itemView.findViewById(R.id.col5);

            this.onListItemClickListener = onListItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClickListener.onListItemClick(v , listItems.get(getBindingAdapterPosition()));
        }


    }
}
