package com.hanakochan.doan.models;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.hanakochan.doan.activities.DetailHistoryActivity;
import com.hanakochan.doan.activities.DetailRoomActivity;
import com.hanakochan.doan.fragments.HistoryFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class RecyclerViewAdapter_History extends RecyclerView.Adapter<RecyclerViewAdapter_History.MyViewHolder> {
    RequestOptions options;
    private Context mcontext;
    private String id_post;
    StringRequest stringRequest;
    private String verified;
    private List<Room> mData_history = new ArrayList<>();
    private static String URL_DELETE_POST = ip_config + "/delete_post_history.php";

    public RecyclerViewAdapter_History(Context mcontext, List<Room> mData_history) {
        this.mcontext = mcontext;
        this.mData_history = mData_history;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.upload)
                .error(R.drawable.upload);

    }

    @NonNull
    @Override
    public RecyclerViewAdapter_History.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.listview_history_layout, parent, false);
        final RecyclerViewAdapter_History.MyViewHolder myViewHolder = new RecyclerViewAdapter_History.MyViewHolder(view);
        myViewHolder.history_view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, DetailHistoryActivity.class);
                intent.putExtra("detail_history_username", mData_history.get(myViewHolder.getAdapterPosition()).getUsername());
                intent.putExtra("detail_history_type_room", mData_history.get(myViewHolder.getAdapterPosition()).getType());
                intent.putExtra("detail_history_price", mData_history.get(myViewHolder.getAdapterPosition()).getPrice());
                intent.putExtra("detail_history_lenght", mData_history.get(myViewHolder.getAdapterPosition()).getLenght());
                intent.putExtra("detail_history_width", mData_history.get(myViewHolder.getAdapterPosition()).getWidth());
                intent.putExtra("detail_history_slot_available", mData_history.get(myViewHolder.getAdapterPosition()).getSlot_available());
                intent.putExtra("detail_history_other", mData_history.get(myViewHolder.getAdapterPosition()).getOther());
                intent.putExtra("detail_history_city_name", mData_history.get(myViewHolder.getAdapterPosition()).getCity());
                intent.putExtra("detail_history_district_name", mData_history.get(myViewHolder.getAdapterPosition()).getDistrict());
                intent.putExtra("detail_history_ward_name", mData_history.get(myViewHolder.getAdapterPosition()).getWard());
                intent.putExtra("detail_history_street_name", mData_history.get(myViewHolder.getAdapterPosition()).getStreet());
                intent.putExtra("detail_history_number", mData_history.get(myViewHolder.getAdapterPosition()).getNumber());
                intent.putExtra("detail_history_time", mData_history.get(myViewHolder.getAdapterPosition()).getTime());
                intent.putExtra("detail_history_img_room", mData_history.get(myViewHolder.getAdapterPosition()).getImage());
                intent.putExtra("detail_history_id", mData_history.get(myViewHolder.getAdapterPosition()).getId_post());
                mcontext.startActivity(intent);
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_History.MyViewHolder holder, final int position) {
        holder.history_tv_type.setText(mData_history.get(position).getType());
        holder.history_tv_price.setText(mData_history.get(position).getPrice());
        holder.history_tv_address.setText("Địa chỉ: " + mData_history.get(position).getNumber()+ ", " + mData_history.get(position).getStreet() + ", " + mData_history.get(position).getDistrict() + ", " + mData_history.get(position).getCity());
        holder.history_tv_id.setText(mData_history.get(position).getId_post());
        Glide.with(mcontext).load(mData_history.get(position).getImage()).apply(options).into(holder.history_imageView);
        verified = (String) mData_history.get(position).getVerified();
        if(verified.equals("1")) {
            holder.history_image_verified.setVisibility(View.VISIBLE);
        }else {
            holder.history_image_verified.setVisibility(View.INVISIBLE);
        }
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mData_history.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView history_tv_price;
        TextView history_tv_type;
        TextView history_tv_address;
        TextView history_tv_id;
        TextView history_verified;
        ImageView history_imageView, history_image_verified;
        LinearLayout history_view_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            history_view_container = itemView.findViewById(R.id.aa_history_container);
            history_tv_type = itemView.findViewById(R.id.history_tvTitle);
            history_tv_price = itemView.findViewById(R.id.history_tvPrice);
            history_tv_address = itemView.findViewById(R.id.history_tvAddress);
            history_tv_id = itemView.findViewById(R.id.history_tvId);
            history_imageView = itemView.findViewById(R.id.history_imgView);
            history_image_verified = itemView.findViewById(R.id.history_verified);
            history_verified = itemView.findViewById(R.id.verified_history_id);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    id_post = (String) history_tv_id.getText();
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
            mData_history.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
}
