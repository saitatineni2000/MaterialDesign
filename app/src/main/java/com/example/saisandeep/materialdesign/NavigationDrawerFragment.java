package com.example.saisandeep.materialdesign;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment implements RecylerViewerAdapter.ClickListener {

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecylerViewerAdapter adapter;

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    View row;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        //readPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false");

        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recylerview_layout);
        adapter = new RecylerViewerAdapter(getActivity(), getData());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public static List<Information> getData() {
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_next, R.drawable.next};
        String[] titles = {"Sandeep", "Sai"};


        //for general purpose
       /* for(int i=0;i< icons.length && i< titles.length;i++)
        {
            Information current=new Information();

            current.iconID=icons[i];
            current.title=titles[i];

            data.add(current);
        }*/


        //for looping items using modulus operator
        for (int i = 0; i < 100; i++) {

            Information current = new Information();

            current.iconID = icons[i % icons.length];
            current.title = titles[i % titles.length];

            data.add(current);
        }
        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {

        row = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");

                }
                getActivity().invalidateOptionsMenu();//will make ur activty to draw ur action bar again when u slide the nav drawer
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(row);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //synchronize the navig drawer with the drawer layout(main content) and hamburger sign genrally done in onPostCreate in Activity since fragment used an seperate thread
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sp = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(preferenceName, defaultValue);
    }

    @Override
    public void itemClicked(View view, int position) {
        Intent i = new Intent(getActivity(), Activity2.class);
        startActivity(i);
    }
}
