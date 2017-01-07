package com.mcknight.gfm13.personalmanager;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CongratsPopup.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CongratsPopup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CongratsPopup extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ITEM_NAME = "ItemName";
    private static final String POINTS = "PointsEarned";
    private static final String ACHIEVEMENTS = "AchievementData";

    // TODO: Rename and change types of parameters
    private String itemName;
    private int points;
    private String[] achievementData;

    private OnFragmentInteractionListener mListener;

    public CongratsPopup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CongratsPopup.
     */
    // TODO: Rename and change types and number of parameters
    public static CongratsPopup newInstance(String itemName, int points, String achievementData) {
        CongratsPopup fragment = new CongratsPopup();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, itemName);
        args.putInt(POINTS, points);
        args.putString(ACHIEVEMENTS, achievementData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemName = getArguments().getString(ITEM_NAME);
            points = getArguments().getInt(POINTS);
            achievementData = getArguments().getString(ACHIEVEMENTS).split("!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_congrats_popup, container, false);

        getDialog().getWindow().setLayout((int)(400 * MainActivity.DP_PIXEL_SCALING),
                (int)(250 * MainActivity.DP_PIXEL_SCALING));

        StringBuilder congratsText = new StringBuilder();
        String compliments[] = getResources().getStringArray(R.array.compliments);
        int complimentIndex = (int)(Math.random() * compliments.length);
        congratsText.append(String.format(getString(R.string.congrats_text), itemName, points, compliments[complimentIndex]));

        for (int i = 0; i < achievementData.length / 2; i += 1) {
            congratsText.append(String.format(getString(R.string.achievement_text), achievementData[2 * i],
                    achievementData[2 * i + 1]));
        }

        ((TextView)(view.findViewById(R.id.congrats_description))).setText(congratsText.toString());

        ((Button)(view.findViewById(R.id.dismissButton))).setOnClickListener(this);

        return view;
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

    @Override
    public void onClick(View view) {
        dismiss();
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
