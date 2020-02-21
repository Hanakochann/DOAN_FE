package com.hanakochan.doan.models;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hanakochan.doan.R;
import com.hanakochan.doan.activities.AdminDetailRoomActivity;
import com.hanakochan.doan.activities.DetailRoomActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class AdminRecyclerViewAdapterSearch extends RecyclerView.Adapter<AdminRecyclerViewAdapterSearch.MyViewHolder> {
    RequestOptions options;
    private Context mcontext;
    private ArrayList<Room> mData;
    private ArrayList<Room> mDataList;
    private String verified;
    private String id_post;
    StringRequest stringRequest;
    private static String URL_DELETE_POST = ip_config + "/delete_post_history.php";

    public AdminRecyclerViewAdapterSearch(Context mcontext, ArrayList<Room> mData) {
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
        view = inflater.inflate(R.layout.listview_admin_search_room_layout, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, AdminDetailRoomActivity.class);
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
                intent.putExtra("detail_id", mData.get(myViewHolder.getAdapterPosition()).getId_post());
                intent.putExtra("detail_verified", mData.get(myViewHolder.getAdapterPosition()).getVerified());
                mcontext.startActivity(intent);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_type.setText(mData.get(position).getType());
        holder.tv_price.setText(mData.get(position).getPrice());
        holder.tv_address.setText("Địa chỉ: " + mData.get(position).getNumber() + ", " + mData.get(position).getStreet() + ", " + mData.get(position).getWard() + ", " + mData.get(position).getDistrict() + ", " + mData.get(position).getCity());
        holder.tv_time.setText(mData.get(position).getTime());
        holder.tv_id.setText(mData.get(position).getId_post());
        Glide.with(mcontext).load(mData.get(position).getImage()).apply(options).into(holder.imageView);
        holder.search_verified.setText(mData.get(position).getVerified());
        verified = (String) mData.get(position).getVerified();
        if(verified.equals("1")) {
            holder.image_search_verified.setVisibility(View.VISIBLE);
        }else {
            holder.image_search_verified.setVisibility(View.INVISIBLE);
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
        TextView tv_id;
        TextView search_verified;
        ImageView imageView, image_search_verified;
        LinearLayout view_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.aa_container);
            tv_type = itemView.findViewById(R.id.tvTitle);
            tv_price = itemView.findViewById(R.id.tvPrice);
            tv_address = itemView.findViewById(R.id.tvAddress);
            tv_time = itemView.findViewById(R.id.tvTime);
            tv_id = itemView.findViewById(R.id.tvID);
            search_verified = itemView.findViewById(R.id.search_verified_id);
            search_verified.setVisibility(View.INVISIBLE);
            imageView = itemView.findViewById(R.id.imgView);
            image_search_verified = itemView.findViewById(R.id.search_verified);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    id_post = (String) tv_id.getText();
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Bạn có chắc chắn muốn xóa bài này hay không?");
                    DialogInterface.OnClickListener dOnClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    notifyDataSetChanged();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialogInterface.cancel();
                                    break;
                            }
                        }
                    };
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            stringRequest = new StringRequest(Request.Method.POST, URL_DELETE_POST + "?id=" + id_post,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String success_text = jsonObject.getString("success");
                                                if (success_text.equals("1")) {
                                                    Toast.makeText(mcontext, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Toast.makeText(mcontext, "Xóa không thành công!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(mcontext, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", id_post);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
                            requestQueue.add(stringRequest);
                            delete(getAdapterPosition());
                        }
                    });
                    builder.setNegativeButton("Không", dOnClickListener);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            });
        }
    }
    public void delete(int position){
        try {
            mData.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

}
