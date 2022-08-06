package com.example.carflix;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class carListAdapter extends RecyclerView.Adapter<carListAdapter.ViewHolder>{

    private String TAG = "carListAdapter";
    private Context context;
    private ArrayList<carData> dataList;//데이터를 담을 리스트

    public carListAdapter(Context context, ArrayList<carData> dataList){
        this.context = context;
        this.dataList = dataList;
    }
    //클릭 리스너 인터페이스
    public interface itemClickListener{
        void onItemClick(View v, int position);
        void onLookupInfoClick(View v, int position);
    }
    //리스너 객체 참조 변수
    private itemClickListener listener = null;
    //리스너 객체 참조를 어댑터에 전달
    public void setItemClickListener(itemClickListener listener){
        this.listener = listener;
    }
    //리스트의 각 항목을 이루는 디자인을 적용
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.car_list_item, parent, false);
        return new ViewHolder(view);
    }
    //리스트의 각 항목에 들어갈 데이터를 지정
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position){
        carData carData = dataList.get(position);
        holder.carImg.setImageResource(carData.getcarImg());
        holder.carName.setText(carData.getCarName());
        if(carData.isAvailable()) {
            holder.isAvailable.setText("운전 가능");
            holder.isAvailable.setTextColor(Color.parseColor("#4488FF"));
        }
        else {
            holder.isAvailable.setText("운전 불가능");
            holder.isAvailable.setTextColor(Color.parseColor("#FF5544"));
        }
    }
    //화면에 보여줄 데이터의 갯수를 반환
    @Override
    public int getItemCount(){
        return dataList.size();
    }
    //ViewHolder 객체에 저장되어 화면에 표시되고, 필요에 따라 생성 또는 재활용 된다.
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView carImg;
        TextView carName;
        TextView isAvailable;
        Button btn_LookupInfo;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            carImg = itemView.findViewById(R.id.carImg);
            carName = itemView.findViewById(R.id.carName);
            isAvailable = itemView.findViewById(R.id.isAvailable);
            btn_LookupInfo = itemView.findViewById(R.id.lookupInfo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (listener != null) {
                            listener.onItemClick(view, position);
                        }
                    }
                }
            });

            btn_LookupInfo.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view){
                   int position = getAdapterPosition();
                   if(position != RecyclerView.NO_POSITION){
                       if(listener!=null){
                           listener.onLookupInfoClick(view, position);
                       }
                   }
               }
            });
        }
    }
}
