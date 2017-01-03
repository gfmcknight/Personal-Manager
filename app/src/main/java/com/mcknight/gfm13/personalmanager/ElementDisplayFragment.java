package com.mcknight.gfm13.personalmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcknight.gfm13.personalmanager.ElementDisplayTypes.PriorityDisplay;
import com.mcknight.gfm13.personalmanager.ElementDisplayTypes.ProjectDisplay;
import com.mcknight.gfm13.personalmanager.ElementDisplayTypes.TaskDisplay;
import com.mcknight.gfm13.personalmanager.Refreshing.IRefreshListener;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEvent;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshInvoker;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ElementDisplayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ElementDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public abstract class ElementDisplayFragment extends Fragment implements IRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public enum DisplayType {
        Priority, Task, Project
    }

    private OnFragmentInteractionListener mListener;

    protected List<View> taskViews;
    protected String pageTitle;

    public ElementDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ElementDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ElementDisplayFragment newInstance(DisplayType displayType) {
        ElementDisplayFragment fragment;
        switch (displayType) {
            case Priority:
                fragment = new PriorityDisplay();
                break;
            case Task:
                fragment = new TaskDisplay();
                break;
            case Project:
                fragment = new ProjectDisplay();
                break;
            default:
                fragment = new TaskDisplay();
                break;
        }
        Bundle args = new Bundle();
        fragment.setArguments(args);

        RefreshInvoker.getInstance().addRefreshListener(fragment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        if (savedInstanceState != null){
            return;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_view, container, false);
        return view;

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

    private void refresh() {
        if (getView() != null) {
            LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.linear_task_layout);
            TextView pageTitleTextView = ((TextView) getView().findViewById(R.id.page_title));

            if (taskViews != null) {
                for (View view : taskViews) {
                    linearLayout.removeView(view);
                }
            }

            taskViews = getPageElements();

            for (View view : taskViews) {
                linearLayout.addView(view);
            }
            if (taskViews != null) {
                pageTitleTextView.setText(pageTitle + " (" +
                        taskViews.size() + "):");
            }
        }
    }

    public void onRefresh(RefreshEvent e) {
        //TODO: Become selective with when to refresh based on RefreshEvent properties
        refresh();
    }

    abstract protected List<View> getPageElements();

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume(){
        refresh();

        super.onResume();
    }

    @Override
    public void onStart(){
        refresh();
        super.onStart();
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
