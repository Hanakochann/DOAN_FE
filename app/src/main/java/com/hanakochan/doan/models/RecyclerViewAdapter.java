package com.hanakochan.doan.models;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hanakochan.doan.R;
import com.hanakochan.doan.activities.AddRoommateActivity;
import com.hanakochan.doan.activities.DetailRoomActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    RequestOptions options;
    private Context mcontext;
    private ArrayList<Room> mData;
    private ArrayList<Room> mDataList;
    private String verified;

    public RecyclerViewAdapter(Context mcontext, ArrayList<Room> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
        this.mDataList = mDataList;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.upload)
                .error(R.drawable.upload);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.listview_room_layout, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, DetailRoomActivity.class);
                intent.putExtra("detail_username", mData.get(myViewHolder.getAdapterPosition()).getUsername());
                intent.putExtra("detail_type_room", mData.get(myViewHolder.getAdapterPosition()).getType());
                intent.putExtra("detail_price", mData.get(myViewHolder.getAdapterPosition()).getPrice());
                intent.putExtra("detail_lenght", mData.get(myViewHolder.getAdapterPosition()).getLenght());
                intent.putExtra("detail_width", mData.get(myViewHolder.getAdapterPosition()).getWidth());
                intent.putExtra("detail_slot_available", mData.get(myViewHolder.getAdapterPosition()).getSlot_available());
                intent.putExtra("detail_other", mData.get(myViewHolder.getAdapterPosition()).getOther());
                intent.putExtra("detail_city_name", mData.get(myViewHolder.getAdapterPosition()).getCity());
                intent.putExtra("detail_district_name", mData.get(myViewHolder.getAdapterPosition()).getDistrict());
                intent.putExtra("detail_ward_name", mData.get(myViewHolder.getAdapterPosition()).getWard());
                intent.putExtra("detail_street_name", mData.get(myViewHolder.getAdapterPosition()).getStreet());
                intent.putExtra("detail_number", mData.get(myViewHolder.getAdapterPosition()).getNumber());
                intent.putExtra("detail_time", mData.get(myViewHolder.getAdapterPosition()).getTime());
                intent.putExtra("detail_img_room", mData.get(myViewHolder.getAdapterPosition()).getImage());
                intent.putExtra("detail_img_user", mData.get(myViewHolder.getAdapterPosition()).getImage_user());
                mcontext.startActivity(intent);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.tv_type.setText(mData.get(position).getType());
        holder.tv_price.setText(mData.get(position).getPrice());
        holder.tv_address.setText("Địa chỉ: " + mData.get(position).getNumber() + ", " + mData.get(position).getStreet() + ", " + mData.get(position).getWard() + ", " + mData.get(position).getDistrict() + ", " + mData.get(position).getCity());
        holder.tv_time.setText(mData.get(position).getTime());
        Glide.with(mcontext).load(mData.get(position).getImage()).apply(options).into(holder.imageView);
        holder.verified_id.setText(mData.get(position).getVerified());
        verified = (String) mData.get(position).getVerified();
        if(verified.equals("1")) {
            holder.imageView_Verified.setVisibility(View.VISIBLE);
        }else {
            holder.imageView_Verified.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setFilter(ArrayList<Room> newlist) {
        mData = new ArrayList<>();
        mData.addAll(newlist);
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_price;
        TextView tv_type;
        TextView tv_address;
        TextView tv_time;
        TextView verified_id;
        ImageView imageView, imageView_Verified;
        LinearLayout view_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.aa_container);
            tv_type = itemView.findViewById(R.id.tvTitle);
            tv_price = itemView.findViewById(R.id.tvPrice);
            tv_address = itemView.findViewById(R.id.tvAddress);
            tv_time = itemView.findViewById(R.id.tvTime);
            imageView = itemView.findViewById(R.id.imgView);
            imageView_Verified = itemView.findViewById(R.id.verified);
            verified_id = itemView.findViewById(R.id.verified_id);
            verified_id.setVisibility(View.INVISIBLE);
        }
    }

}
