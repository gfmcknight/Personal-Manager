package com.mcknight.gfm13.personalmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mcknight.gfm13.personalmanager.ElementDisplayTypes.PriorityDisplay;
import com.mcknight.gfm13.personalmanager.ElementDisplayTypes.ProjectDisplay;
import com.mcknight.gfm13.personalmanager.ElementDisplayTypes.TaskDisplay;
import com.mcknight.gfm13.personalmanager.Refreshing.IRefreshListener;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshEvent;
import com.mcknight.gfm13.personalmanager.Refreshing.RefreshInvoker;
import com.mcknight.gfm13.personalmanager.Sorting.DueDateSort;
import com.mcknight.gfm13.personalmanager.Sorting.GroupSort;
import com.mcknight.gfm13.personalmanager.Sorting.IdSort;
import com.mcknight.gfm13.personalmanager.Sorting.MergeSorter;
import com.mcknight.gfm13.personalmanager.Sorting.NameSort;
import com.mcknight.gfm13.personalmanager.Sorting.SortAlgorithm;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ElementDisplayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ElementDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public abstract class ElementDisplayFragment extends Fragment implements IRefreshListener, AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public enum DisplayType {
        Priority, Task, Project
    }

    private OnFragmentInteractionListener mListener;

    protected List<View> taskViews;
    protected String pageTitle;

    private SortAlgorithm sortAlgorithm;

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

        sortAlgorithm = MergeSorter.DEFAULT_SORT;

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.group_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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



            taskViews = getPageElements(sortAlgorithm);

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

    abstract protected List<View> getPageElements(SortAlgorithm algorithm);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                sortAlgorithm = new DueDateSort(MergeSorter.DEFAULT_SORT);
                break;
            case 1:
                sortAlgorithm = new GroupSort(MergeSorter.DEFAULT_SORT);
                break;
            case 2:
                sortAlgorithm = new NameSort(MergeSorter.DEFAULT_SORT);
                break;
            case 3:
                sortAlgorithm = new IdSort(MergeSorter.DEFAULT_SORT);
                break;
            default:
                sortAlgorithm = MergeSorter.DEFAULT_SORT;
        }

        refresh();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {

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
