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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class AdminRecyclerViewAdapter_User extends RecyclerView.Adapter<AdminRecyclerViewAdapter_User.MyViewHolder> {
    RequestOptions options;
    private Context mcontext;
    private ArrayList<User> mData;
    private ArrayList<User> mDataList;
    private String verified;
    private String id_post;
    StringRequest stringRequest;
    private static String URL_DELETE_POST = ip_config + "/delete_user.php";

    public AdminRecyclerViewAdapter_User(Context mcontext, ArrayList<User> mData) {
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
        view = inflater.inflate(R.layout.listview_admin_user_layout, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.tv_id.setText(mData.get(position).getId());
        holder.tv_username.setText(mData.get(position).getUsername());
        holder.tv_hometown.setText(mData.get(position).getHometown());
        holder.tv_birthday.setText(mData.get(position).getBirthday());
        holder.tv_gender.setText(mData.get(position).getGender());
        Glide.with(mcontext).load(mData.get(position).getPhoto()).apply(options).into(holder.imageView);
        holder.tv_phone.setText(mData.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setFilter(ArrayList<User> newlist) {
        mData = new ArrayList<>();
        mData.addAll(newlist);
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_username;
        TextView tv_birthday;
        TextView tv_hometown;
        TextView tv_gender;
        TextView tv_phone;
        TextView tv_id;
        ImageView imageView, imageView_Verified;
        LinearLayout view_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.aa_container);
            tv_username = itemView.findViewById(R.id.tvTitle);
            tv_birthday = itemView.findViewById(R.id.admin_tvBirthday);
            tv_hometown = itemView.findViewById(R.id.admin_tvAddress);
            tv_gender = itemView.findViewById(R.id.admin_tvGender);
            tv_id = itemView.findViewById(R.id.admin_tvID);
            tv_phone = itemView.findViewById(R.id.admin_tvPhone);
            imageView = itemView.findViewById(R.id.imgView);
            imageView_Verified = itemView.findViewById(R.id.verified);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    id_post = (String) tv_id.getText();
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
            mData.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

}
