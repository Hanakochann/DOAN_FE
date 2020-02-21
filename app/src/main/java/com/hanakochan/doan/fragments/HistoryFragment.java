package com.hanakochan.doan.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.hanakochan.doan.R;
import com.hanakochan.doan.models.RecyclerViewAdapter_History;
import com.hanakochan.doan.models.RecyclerViewAdapter_History_Roommate;
import com.hanakochan.doan.models.Room;
import com.hanakochan.doan.models.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.android.volley.VolleyLog.v;
import static com.hanakochan.doan.models.Config.ip_config;

public class HistoryFragment extends Fragment {

    private static String URL_READ = ip_config + "/load_history.php";
    private static String URL_READ_ROOMMATE = ip_config + "/load_history_roommate.php";
    private static String URL_DELETE = ip_config + "/delete_history.php";
    Toolbar toolbar;
    SessionManager sessionManager;
    String getId, getUsername;
    TextView tv_empty;
    Button btn_delete;

    RecyclerView recyclerView, recyclerView_Roommate;
    private JsonArrayRequest jsonArrayRequest, jsonArrayRequestRoommate;
    private StringRequest stringRequest;
    private RequestQueue requestQueue, requestQueue_Roommate;
    private List<Room> lstRoom = new ArrayList<>();
    private List<Room> lstRoommate = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
//        getUsername = user.get(sessionManager.NAME);

        toolbar = view.findViewById(R.id.toolbar_history_id);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("History");
        setHasOptionsMenu(true);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        collectDataHistory();
        collectDataHistoryRoommate();
        return view;

    }

    private void collectDataHistory() {
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL_READ + "?id_user=" + getId, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                if (response == null) {
                    String text = "Bạn chưa đăng bài nào";
                    tv_empty.setText(text);
                } else {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            Room room = new Room();
                            room.setId_post(jsonObject.getString("id"));
                            room.setUsername(jsonObject.getString("username"));
                            room.setType(jsonObject.getString("type_room"));
                            room.setPrice(jsonObject.getString("price"));
                            room.setLenght(jsonObject.getString("lenght"));
                            room.setWidth(jsonObject.getString("width"));
                            room.setSlot_available(jsonObject.getString("slot_available"));
                            room.setOther(jsonObject.getString("other"));
                            room.setImage(jsonObject.getString("image_name"));
                            room.setCity(jsonObject.getString("city_name"));
                            room.setDistrict(jsonObject.getString("district_name"));
                            room.setWard(jsonObject.getString("ward_name"));
                            room.setStreet(jsonObject.getString("street_name"));
                            room.setNumber(jsonObject.getString("number"));
                            room.setVerified(jsonObject.getString("verified"));
                            room.setTime(jsonObject.getString("time_post"));
                            lstRoom.add(room);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    setupRecyclerViewHistory(lstRoom);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

    }

    private void setupRecyclerViewHistory(List<Room> lstRoom) {
        recyclerView = getActivity().findViewById(R.id.recyclerView_history);
        RecyclerViewAdapter_History myAdapter = new RecyclerViewAdapter_History(getActivity(), lstRoom);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
        recyclerView.setHasFixedSize(true);

    }

    private void collectDataHistoryRoommate() {
        jsonArrayRequestRoommate = new JsonArrayRequest(Request.Method.GET, URL_READ_ROOMMATE + "?id_user=" + getId, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                if (response == null) {
                    String text = "Bạn chưa đăng bài nào";
                    tv_empty.setText(text);
                } else {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            Room room = new Room();
                            room.setId_post(jsonObject.getString("id"));
                            room.setId_user(jsonObject.getString("id_user"));
                            room.setUsername(jsonObject.getString("username"));
                            room.setPrice(jsonObject.getString("price"));
                            room.setImage(jsonObject.getString("img_user"));
                            room.setCity(jsonObject.getString("city_name"));
                            room.setDistrict(jsonObject.getString("district_name"));
                            room.setWard(jsonObject.getString("ward_name"));
                            room.setStreet(jsonObject.getString("street_name"));
                            room.setNote(jsonObject.getString("note"));
                            room.setGender_roommate(jsonObject.getString("gender_roommate"));
                            room.setTime(jsonObject.getString("time_post"));
                            lstRoommate.add(room);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    setupRecyclerViewHistoryRoommate(lstRoommate);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue_Roommate = Volley.newRequestQueue(getActivity());
        requestQueue_Roommate.add(jsonArrayRequestRoommate);

    }

    private void setupRecyclerViewHistoryRoommate(List<Room> lstRoommate) {
        recyclerView_Roommate = getActivity().findViewById(R.id.recyclerView_history_room);
        RecyclerViewAdapter_History_Roommate myAdapter = new RecyclerViewAdapter_History_Roommate(getActivity(), lstRoommate);
        recyclerView_Roommate.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_Roommate.setAdapter(myAdapter);
        recyclerView_Roommate.setHasFixedSize(true);

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_history, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_delete_history) {
//            DeleteIcon();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void DeleteIcon() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("Bạn chắc chắn muốn xóa tất cả bài đăng?");
//        DialogInterface.OnClickListener dOnClickListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                switch (i) {
//                    case DialogInterface.BUTTON_POSITIVE:
////                        DeleteAll();
//                        break;
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        dialogInterface.cancel();
//                        break;
//                }
//            }
//        };
//        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DELETE + "?id_user=" + getId, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success_text = jsonObject.getString("success");
//                            if (success_text.equals("1")) {
//                                Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getActivity(), HistoryFragment.class);
//                                startActivity(intent);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(getActivity(), "Xóa không thành công!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//        builder.setNegativeButton("Không", dOnClickListener);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

//    private void DeleteAll() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE + "/?" + "id_user=" + getId, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success_text = jsonObject.getString("success");
//                    if (success_text.equals("1")) {
//                        Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getActivity(), HistoryFragment.class);
//                        startActivity(intent);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity(), "Xóa không thành công!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
