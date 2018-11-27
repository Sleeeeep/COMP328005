package com.example.test.moappteam;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private MainFragment mainFragment;
    private MypageFragment mypageFragment;
    private SpeakFragment speakFragment;
    private RankFragment rankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = (FrameLayout)findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView)findViewById(R.id.main_nav);

        mainFragment = new MainFragment();
        mypageFragment = new MypageFragment();
        speakFragment = new SpeakFragment();
        rankFragment = new RankFragment();

        setFragment(mainFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_main :
                        setFragment(mainFragment);
                        return true;
                    case R.id.nav_mypage :
                        setFragment(mypageFragment);
                        return true;
                    case R.id.nav_speak :
                        setFragment(speakFragment);
                        return true;
                    case R.id.nav_rank :
                        setFragment(rankFragment);
                        return true;
                        default:
                            return false;
                }
            }
        });

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

}
