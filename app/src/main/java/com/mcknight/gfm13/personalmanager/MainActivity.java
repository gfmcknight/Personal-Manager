package com.mcknight.gfm13.personalmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.mcknight.gfm13.personalmanager.Groups.GroupManager;
import com.mcknight.gfm13.personalmanager.Groups.GroupsEditor;
import com.mcknight.gfm13.personalmanager.Refreshing.IRefreshListener;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEvent;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEventType;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshInvoker;
import com.mcknight.gfm13.personalmanager.WorkItems.ItemManager;

public class MainActivity extends AppCompatActivity implements ElementDisplayFragment.OnFragmentInteractionListener,
        CongratsPopup.OnFragmentInteractionListener, BreakFragment.OnFragmentInteractionListener, IRefreshListener {

    public static double DP_PIXEL_SCALING = 1.0;

    private AchievementManager achievements;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ItemManager.getTaskManager().init(this);
        ItemManager.getTaskManager().purge();
        ItemManager.getTaskManager().commit();

        ItemManager.getProjectManager().init(this);
        ItemManager.getProjectManager().purge();
        ItemManager.getProjectManager().commit();
        GroupManager.getInstance().init(this);

        RefreshInvoker.getInstance().addRefreshListener(this);

        achievements = new AchievementManager(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        DP_PIXEL_SCALING = getResources().getDisplayMetrics().density;

    }

    public void AddButtonPressed(View view)
    {
        Intent intent = new Intent(this, NewTask.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_groups) {
            Intent intent = new Intent(this, GroupsEditor.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_new_task) {
            Intent intent = new Intent(this, NewTask.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_new_project) {
            Intent intent = new Intent(this, NewProject.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri){

    }

    @Override
    public void onRefresh(RefreshEvent e) {
        if (e.getEventType().equals(RefreshEventType.FINISH)) {
            boolean affectsProject = e.affectsProject();
            int pointAddition = 0;
            String itemName = "";
            if (!affectsProject) {
                try {
                    itemName = e.getAffectedTask().getName();
                    pointAddition = 10 + (int) (e.getAffectedTask().getHoursEstimate());
                    if (!e.isPriorityElement()) {
                        pointAddition += 5;
                    }
                } catch (IllegalAccessException exception) {

                }

            } else {
                try {
                    itemName = e.getAffectedProject().getName();
                    pointAddition = (int) (5 + e.getAffectedProject().getSteps().size() * 2 +
                            e.getAffectedProject().getHoursEstimate());
                } catch (IllegalAccessException exception) {

                }
            }
            SharedPreferences preferences = getSharedPreferences(getString(R.string.edit_ID),
                    Context.MODE_PRIVATE);

            SharedPreferences.Editor edit = preferences.edit();
            edit.putInt("Points", preferences.getInt("Points", 0) + pointAddition);
            if (affectsProject) {
                edit.putInt("ProjectsCompleted", preferences.getInt("ProjectsCompleted", 0) + 1);
                try {
                    edit.putInt("TotalHoursWorked", preferences.getInt("TotalHoursWorked", 0) +
                            (int) (e.getAffectedProject().getHoursEstimate()));
                } catch (IllegalAccessException exception) {}
            } else {
                edit.putInt("TasksCompleted", preferences.getInt("TasksCompleted", 0) + 1);
                try {
                    edit.putInt("TotalHoursWorked", preferences.getInt("TotalHoursWorked", 0) +
                            (int) (e.getAffectedTask().getHoursEstimate()));
                } catch (IllegalAccessException exception) {}
            }

            edit.commit();
            String s;

            String achievementChanges = "";
            if (affectsProject) {
                try {
                    achievementChanges = achievements.updateAchievements(
                            preferences.getInt("TasksCompleted", 0),
                            preferences.getInt("ProjectsCompleted", 0),
                            preferences.getInt("TotalHoursWorked", 0),
                            e.getAffectedProject().getSteps().size(),
                            (int)e.getAffectedProject().getHoursEstimate(),
                            preferences);
                } catch (Exception exception) {}
            } else {
                try {
                    achievementChanges = achievements.updateAchievements(
                            preferences.getInt("TasksCompleted", 0),
                            preferences.getInt("ProjectsCompleted", 0),
                            preferences.getInt("TotalHoursWorked", 0),
                            preferences);
                } catch (Exception exception) { }

            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            CongratsPopup fragment = CongratsPopup.newInstance(itemName, pointAddition, achievementChanges);
            fragmentTransaction.add(0, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return ElementDisplayFragment.newInstance(ElementDisplayFragment.DisplayType.Priority);
                case 1:
                    return ElementDisplayFragment.newInstance(ElementDisplayFragment.DisplayType.Task);
                case 2:
                    return ElementDisplayFragment.newInstance(ElementDisplayFragment.DisplayType.Project);
                default:
                    return BreakFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PRIORITY";
                case 1:
                    return "TASKS";
                case 2:
                    return "PROJECTS";
                case 3:
                    return "BREAKS";
            }
            return null;
        }
    }
}
