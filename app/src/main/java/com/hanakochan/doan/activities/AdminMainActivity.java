package com.hanakochan.doan.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hanakochan.doan.R;
import com.hanakochan.doan.fragments.AdminProfileFragment;
import com.hanakochan.doan.fragments.AdminRoomFragment;
import com.hanakochan.doan.fragments.AdminRoommateFragment;
import com.hanakochan.doan.fragments.AdminUserFragment;
import com.hanakochan.doan.fragments.HistoryFragment;
import com.hanakochan.doan.fragments.HomepageFragment;
import com.hanakochan.doan.fragments.ProfileFragment;
import com.hanakochan.doan.fragments.RoommateFragment;

public class AdminMainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_room:
                    transaction.replace(R.id.container, new AdminRoomFragment()).commit();
                    return true;
                case R.id.navigation_roommate:
                    transaction.replace(R.id.container, new AdminRoommateFragment()).commit();
                    return true;
                case R.id.navigation_user:
                    transaction.replace(R.id.container, new AdminUserFragment()).commit();
                    return true;
                case R.id.navigation_profile:
                    transaction.replace(R.id.container, new AdminProfileFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, new AdminRoomFragment()).commit();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_room);
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn chắc chắc muốn thoát?")
                .setCancelable(false).setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AdminMainActivity.super.onBackPressed();
            }
        })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
