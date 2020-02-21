package com.hanakochan.doan.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hanakochan.doan.R;
import com.hanakochan.doan.activities.AdminSearchRoomActivity;
import com.hanakochan.doan.models.AdminRecyclerViewAdapter;
import com.hanakochan.doan.models.AdminRecyclerViewAdapter_User;
import com.hanakochan.doan.models.Room;
import com.hanakochan.doan.models.SessionManager;
import com.hanakochan.doan.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.hanakochan.doan.models.Config.ip_config;

public class AdminUserFragment extends Fragment {
    Toolbar toolbar;
    private static String URL_READ = ip_config + "/load_admin_user.php";
    private static final String TAG = AdminUserFragment.class.getSimpleName();
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    ArrayList<User> lstUser = new ArrayList<>();
    SessionManager sessionManager;
    String getId, getUrlImage;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public AdminUserFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_user, container, false);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
        getUrlImage = user.get(sessionManager.IMAGE);
        lstUser = new ArrayList<>();
        toolbar = view.findViewById(R.id.toolbar_home_id);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("User");
        setHasOptionsMenu(true);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        recyclerView = view.findViewById(R.id.admin_recyclerView);
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
                        User user = new User();
                        user.setId(jsonObject.getString("id"));
                        user.setUsername(jsonObject.getString("username"));
                        user.setBirthday(jsonObject.getString("birthday"));
                        user.setGender(jsonObject.getString("gender"));
                        user.setHometown(jsonObject.getString("hometown"));
                        user.setPhone(jsonObject.getString("phone"));
                        user.setPhoto(jsonObject.getString("photo"));
                        lstUser.add(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                setupRecyclerView(lstUser);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private void setupRecyclerView(ArrayList<User> lstUser) {
        AdminRecyclerViewAdapter_User myAdapter = new AdminRecyclerViewAdapter_User(getActivity(), lstUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_admin_home, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_search_home) {
//
//            startSearchRoom();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void startSearchRoom() {
//        Intent intent = new Intent(getActivity(), AdminSearchRoomActivity.class);
//        startActivity(intent);
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
