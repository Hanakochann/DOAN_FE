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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hanakochan.doan.activities.AddRoommateActivity;
import com.hanakochan.doan.R;
import com.hanakochan.doan.models.RecyclerViewAdapter_Roommate;
import com.hanakochan.doan.models.Room;
import com.hanakochan.doan.models.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hanakochan.doan.models.Config.ip_config;


public class RoommateFragment extends Fragment {

    private static String URL_READ = ip_config+"/load_roommate.php";
    Toolbar toolbar;
    SessionManager sessionManager;
    String getId, getUsername;

    RecyclerView recyclerView;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Room> lstRoom = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public RoommateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roommate, container, false);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
//        getUsername = user.get(sessionManager.NAME);

        toolbar = view.findViewById(R.id.toolbar_roommate_id);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("ROOMMATE");
        setHasOptionsMenu(true);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        recyclerView = view.findViewById(R.id.recyclerView_Roommate);

        collectDataRoommate();
        return view;
    }

    private void collectDataRoommate()
    {
        request = new JsonArrayRequest(URL_READ, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
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
                        room.setStreet(jsonObject.getString("street_name"));
                        room.setGender(jsonObject.getString("gender"));
                        room.setTime(jsonObject.getString("time_post"));
                        lstRoom.add(room);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                setupRecyclerViewRoommate(lstRoom);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    private void setupRecyclerViewRoommate(List<Room> lstRoom) {
        RecyclerViewAdapter_Roommate myAdapter = new RecyclerViewAdapter_Roommate(getActivity(), lstRoom);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_roommate, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search_roommate) {

            // Do something
            return true;
        }
        if (id == R.id.action_add_roommate) {

            startAddroommate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startAddroommate(){
        Intent intent = new Intent(getActivity(), AddRoommateActivity.class);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
