package com.mcknight.gfm13.personalmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BreakFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BreakFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BreakFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public BreakFragment() {
        // Required empty public constructor
    }

    public static BreakFragment newInstance() {
        BreakFragment fragment = new BreakFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_break, container, false);

        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.edit_ID), Context.MODE_PRIVATE);
        int points = preferences.getInt("Points", 0);
        int level = getLevelForPoints(points);
        int pointsNeeded = getPointsForLevel(level) - points;
        if (level > 12) {
            level = 13;
            pointsNeeded = 0;
        }
        int hours = preferences.getInt("TotalHoursWorked" , 0);
        int tasks = preferences.getInt("TasksCompleted", 0);
        int projects = preferences.getInt("ProjectsCompleted", 0);

        int completion = (int)((1d - (double)pointsNeeded / (getPointsForLevel(level) - getPointsForLevel(level - 1))) * 100);

        ((TextView)(view.findViewById(R.id.rank))).setText("Rank: " +
                getContext().getResources().getStringArray(R.array.ranks)[level - 1]);

        ((TextView)(view.findViewById(R.id.pointDisplay))).setText(
                String.format("Points: %1$d\nTo Next Rank: %2$d", points, pointsNeeded));

        ((ProgressBar)(view.findViewById(R.id.bar))).setProgress(completion);

        ((TextView)(view.findViewById(R.id.stats))).setText(
                String.format("Hours Worked: %1$d\nTasks Finished: %2$d\nProjects Finished: %3$d",
                        hours, tasks, projects));


        String[] achievementNames = getContext().getResources().getStringArray(R.array.achievement_names);

        LinearLayout layout = (LinearLayout)view.findViewById(R.id.achievements);

        for (int i = 0; i < achievementNames.length; i++) {
            layout.addView(ViewFactory.makeAchievementView(getContext(), achievementNames[i],
                    preferences.getBoolean("Achievement-" + i, false)));
            layout.setVisibility(View.VISIBLE);
        }


        return view;


    }

    private int getLevelForPoints(int point) {
        int i;
        for (i = 1; point > getPointsForLevel(i); i++);
        return i;
    }

    private int getPointsForLevel(int level) {
        return level * level * 50;
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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
