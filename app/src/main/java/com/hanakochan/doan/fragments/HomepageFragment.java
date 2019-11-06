package com.hanakochan.doan.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.hanakochan.doan.AddRoomActivity;
import com.hanakochan.doan.R;
import com.hanakochan.doan.SearchRoomActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomepageFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Toolbar toolbar;
    private static String URL_READ = "http://192.168.119.23/android_register_login/load_room.php";
    String[] title;
    String[] price;
    String[] address;
    String[] imgPath;
    ListView listView;
    BufferedInputStream bufferedInputStream;
    String line = null;
    String result = null;



    public HomepageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        toolbar = view.findViewById(R.id.toolbar_home_id);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Home");
        setHasOptionsMenu(true);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        listView = view.findViewById(R.id.lview);
//        collectData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
    private void startSearchRoom(){
        Intent intent = new Intent(getActivity(), SearchRoomActivity.class);
        startActivity(intent);
    }
    private void startAddRoom(){
        Intent intent = new Intent(getActivity(), AddRoomActivity.class);
        startActivity(intent);
    }


//    private void collectData(){
////connection
//        try {
//            URL url = new URL(URL_READ);
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setRequestMethod("GET");
//            bufferedInputStream = new BufferedInputStream(conn.getInputStream());
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        //content
//        try {
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(bufferedInputStream));
//            StringBuilder sb = new StringBuilder();
//            while ((line = br.readLine()) != null){
//                sb.append(line+"\n");
//
//            }
//            bufferedInputStream.close();
//            result = sb.toString();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        //JSON
//        try {
//
//            JSONArray jsonArray = new JSONArray(result);
//            JSONObject object = null;
//            title = new String[jsonArray.length()];
//            price = new String[jsonArray.length()];
//            address = new String[jsonArray.length()];
//            imgPath = new String[jsonArray.length()];
//            for (int i = 0; i <= jsonArray.length(); i++){
//                object = jsonArray.getJSONObject(i);
//                title[i] = object.getString("type_room");
//                price[i] = object.getString("price");
//                address[i] = object.getString("address");
//                imgPath[i] = object.getString("photo");
//
//            }
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
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
