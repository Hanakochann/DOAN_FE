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
import com.hanakochan.doan.activities.AdminDetailRoommateActivity;
import com.hanakochan.doan.activities.DetailRoommateActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class AdminRecyclerViewAdapter_Roommate extends RecyclerView.Adapter<AdminRecyclerViewAdapter_Roommate.MyViewHolder> {
    RequestOptions options;
    private Context mcontext;
    private List<Room> mData_roommate = new ArrayList<>();
    private String id_post;
    StringRequest stringRequest;
    private static String URL_DELETE_POST = ip_config + "/delete_post_history_roommate.php";

    public AdminRecyclerViewAdapter_Roommate(Context mcontext, List<Room> mData_roommate) {
        this.mcontext = mcontext;
        this.mData_roommate = mData_roommate;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.upload)
                .error(R.drawable.upload);
    }

    @NonNull
    @Override
    public AdminRecyclerViewAdapter_Roommate.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.listview_admin_roommate_layout, parent, false);
        final AdminRecyclerViewAdapter_Roommate.MyViewHolder myViewHolder = new AdminRecyclerViewAdapter_Roommate.MyViewHolder(view);
        myViewHolder.roommate_view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, AdminDetailRoommateActivity.class);
                intent.putExtra("roommate_detail_id", mData_roommate.get(myViewHolder.getAdapterPosition()).getId_post());
                intent.putExtra("roommate_detail_id_user", mData_roommate.get(myViewHolder.getAdapterPosition()).getId_user());
                intent.putExtra("roommate_detail_name", mData_roommate.get(myViewHolder.getAdapterPosition()).getUsername());
                intent.putExtra("roommate_detail_hometown", mData_roommate.get(myViewHolder.getAdapterPosition()).getHometown());
                intent.putExtra("roommate_detail_gender", mData_roommate.get(myViewHolder.getAdapterPosition()).getGender());
                intent.putExtra("roommate_detail_birthday", mData_roommate.get(myViewHolder.getAdapterPosition()).getBirthday());
                intent.putExtra("roommate_detail_price", mData_roommate.get(myViewHolder.getAdapterPosition()).getPrice());
                intent.putExtra("roommate_detail_city_name", mData_roommate.get(myViewHolder.getAdapterPosition()).getCity());
                intent.putExtra("roommate_detail_district_name", mData_roommate.get(myViewHolder.getAdapterPosition()).getDistrict());
                intent.putExtra("roommate_detail_ward_name", mData_roommate.get(myViewHolder.getAdapterPosition()).getWard());
                intent.putExtra("roommate_detail_street_name", mData_roommate.get(myViewHolder.getAdapterPosition()).getStreet());
                intent.putExtra("roommate_detail_phone", mData_roommate.get(myViewHolder.getAdapterPosition()).getPhone());
                intent.putExtra("roommate_detail_gender_roommate", mData_roommate.get(myViewHolder.getAdapterPosition()).getGender_roommate());
                intent.putExtra("roommate_detail_time", mData_roommate.get(myViewHolder.getAdapterPosition()).getTime());
                intent.putExtra("roommate_detail_note", mData_roommate.get(myViewHolder.getAdapterPosition()).getNote());
                intent.putExtra("roommate_detail_img_room", mData_roommate.get(myViewHolder.getAdapterPosition()).getImage());
                mcontext.startActivity(intent);
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRecyclerViewAdapter_Roommate.MyViewHolder holder, int position) {
        holder.roommate_tv_username.setText(mData_roommate.get(position).getUsername());
        holder.roommate_tv_price.setText(mData_roommate.get(position).getPrice());
        holder.roommate_tv_address.setText("Địa chỉ: " + mData_roommate.get(position).getStreet() + ", " + mData_roommate.get(position).getDistrict() + ", " + mData_roommate.get(position).getWard()+ ", " +mData_roommate.get(position).getCity());
        holder.roommate_tv_gender_roommate.setText(mData_roommate.get(position).getGender_roommate());
        holder.roommate_tv_time.setText(mData_roommate.get(position).getTime());
        holder.roommate_tv_id.setText(mData_roommate.get(position).getId_post());
        Glide.with(mcontext).load(mData_roommate.get(position).getImage()).apply(options).into(holder.roommate_imageView);
    }

    @Override
    public int getItemCount() {
        return mData_roommate.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder    {

        TextView roommate_tv_price;
        TextView roommate_tv_username;
        TextView roommate_tv_address;
        TextView roommate_tv_gender_roommate;
        TextView roommate_tv_time;
        TextView roommate_tv_id;
        ImageView roommate_imageView;
        LinearLayout roommate_view_container;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            roommate_view_container = itemView.findViewById(R.id.aa_roommate_container);
            roommate_tv_username = itemView.findViewById(R.id.roommate_tvTitle);
            roommate_tv_price = itemView.findViewById(R.id.roommate_tvPrice);
            roommate_tv_address = itemView.findViewById(R.id.roommate_tvaddress);
            roommate_tv_gender_roommate = itemView.findViewById(R.id.roommate_tvgender);
            roommate_tv_time = itemView.findViewById(R.id.roommate_tvtime);
            roommate_tv_id = itemView.findViewById(R.id.roommate_tvID);
            roommate_imageView = itemView.findViewById(R.id.roommate_imgView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    id_post = (String) roommate_tv_id.getText();
                    Toast.makeText(mcontext, id_post, Toast.LENGTH_SHORT).show();
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
            mData_roommate.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
}
