package com.hanakochan.doan.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hanakochan.doan.activities.AddRoomActivity;
import com.hanakochan.doan.R;
import com.hanakochan.doan.activities.MainActivity;
import com.hanakochan.doan.activities.SearchRoomActivity;
import com.hanakochan.doan.models.RecyclerViewAdapter;
import com.hanakochan.doan.models.Room;
import com.hanakochan.doan.models.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class HomepageFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Toolbar toolbar;
    private static String URL_READ = ip_config + "/load_room.php";
    private static final String TAG = HomepageFragment.class.getSimpleName();
    SessionManager sessionManager;
    String getId, getUrlImage;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private ArrayList<Room> lstRoom = new ArrayList<>();

    public HomepageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
        getUrlImage = user.get(sessionManager.IMAGE);
        lstRoom = new ArrayList<>();
        toolbar = view.findViewById(R.id.toolbar_home_id);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Room");
        setHasOptionsMenu(true);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        recyclerView = view.findViewById(R.id.recyclerView);
        collectData();
        return view;
    }

    private void collectData() {
        request = new JsonArrayRequest(URL_READ, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Room room = new Room();
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
                        room.setBirthday(jsonObject.getString("birthday"));
                        room.setHometown(jsonObject.getString("hometown"));
                        room.setGender(jsonObject.getString("gender"));
                        room.setPhone(jsonObject.getString("phone"));
                        room.setImage_user(jsonObject.getString("photo"));
                        lstRoom.add(room);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                setupRecyclerView(lstRoom);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private void setupRecyclerView(ArrayList<Room> lstRoom) {
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(), lstRoom);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search_home) {

            startSearchRoom();
            return true;
        }
        if (id == R.id.action_add_home) {

            startAddRoom();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSearchRoom() {
        Intent intent = new Intent(getActivity(), SearchRoomActivity.class);
        startActivity(intent);
    }

    private void startAddRoom() {
        Intent intent = new Intent(getActivity(), AddRoomActivity.class);
        startActivity(intent);
    }

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
