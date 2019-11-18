package com.hanakochan.doan.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hanakochan.doan.R;
import com.hanakochan.doan.activities.DetailRoommateActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_Roommate extends RecyclerView.Adapter<RecyclerViewAdapter_Roommate.MyViewHolder>  {
    RequestOptions options ;
    private Context mcontext;
    private List<Room> mData_roommate = new ArrayList<>();
    public RecyclerViewAdapter_Roommate(Context mcontext, List<Room> mData_roommate) {
        this.mcontext = mcontext;
        this.mData_roommate = mData_roommate;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.upload)
                .error(R.drawable.upload);
    }
    @NonNull
    @Override
    public RecyclerViewAdapter_Roommate.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.listview_roommate_layout,parent,false);
        final RecyclerViewAdapter_Roommate.MyViewHolder myViewHolder = new RecyclerViewAdapter_Roommate.MyViewHolder(view);
        myViewHolder.roommate_view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, DetailRoommateActivity.class);
                intent.putExtra("roommate_detail_name", mData_roommate.get(myViewHolder.getAdapterPosition()).getUsername());
                intent.putExtra("roommate_detail_price", mData_roommate.get(myViewHolder.getAdapterPosition()).getPrice());
                intent.putExtra("roommate_detail_city_name", mData_roommate.get(myViewHolder.getAdapterPosition()).getCity());
                intent.putExtra("roommate_detail_district_name", mData_roommate.get(myViewHolder.getAdapterPosition()).getDistrict());
                intent.putExtra("roommate_detail_street_name", mData_roommate.get(myViewHolder.getAdapterPosition()).getStreet());
                intent.putExtra("roommate_detail_gender", mData_roommate.get(myViewHolder.getAdapterPosition()).getGender());
                intent.putExtra("roommate_detail_img_room", mData_roommate.get(myViewHolder.getAdapterPosition()).getImage());
                mcontext.startActivity(intent);
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_Roommate.MyViewHolder holder, int position) {
        holder.roommate_tv_username.setText(mData_roommate.get(position).getUsername());
        holder.roommate_tv_price.setText(mData_roommate.get(position).getPrice());
        holder.roommate_tv_city.setText(mData_roommate.get(position).getCity());
        holder.roommate_tv_district.setText(mData_roommate.get(position).getDistrict());
        holder.roommate_tv_street.setText(mData_roommate.get(position).getStreet());
        holder.roommate_tv_gender.setText(mData_roommate.get(position).getGender());

        Glide.with(mcontext).load(mData_roommate.get(position).getImage()).apply(options).into(holder.roommate_imageView);
    }

    @Override
    public int getItemCount() {
        return mData_roommate.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView roommate_tv_price;
        TextView roommate_tv_username;
        TextView roommate_tv_city;
        TextView roommate_tv_district;
        TextView roommate_tv_street;
        TextView roommate_tv_gender;
        ImageView roommate_imageView;
        LinearLayout roommate_view_container;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            roommate_view_container = itemView.findViewById(R.id.aa_roommate_container);
            roommate_tv_username = itemView.findViewById(R.id.roommate_tvTitle);
            roommate_tv_price = itemView.findViewById(R.id.roommate_tvPrice);
            roommate_tv_city = itemView.findViewById(R.id.roommate_tvcity);
            roommate_tv_district = itemView.findViewById(R.id.roommate_tvdistrict);
            roommate_tv_street = itemView.findViewById(R.id.roommate_tvstreet);
            roommate_tv_gender = itemView.findViewById(R.id.roommate_tvgender);
            roommate_imageView = itemView.findViewById(R.id.roommate_imgView);


        }
    }
}
