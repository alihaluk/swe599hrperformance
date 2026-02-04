package tr.edu.boun.hrperformance.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.adapters.EmployeeItemRecyclerViewAdapter;
import tr.edu.boun.hrperformance.models.Employee;
import tr.edu.boun.hrperformance.services.DataProvider;
import tr.edu.boun.hrperformance.services.MockDataProvider;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EmployeeListFragment extends Fragment
{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmployeeListFragment()
    {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EmployeeListFragment newInstance(int columnCount)
    {
        EmployeeListFragment fragment = new EmployeeListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public static EmployeeListFragment newInstance()
    {
        return newInstance(1);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_employee_list, container, false);

        // Set the adapter
        if(view instanceof RecyclerView)
        {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if(mColumnCount <= 1)
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else
            {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // Mock Data
            MockDataProvider.getInstance().getAllEmployees(new DataProvider.DataCallback<List<Employee>>() {
                @Override
                public void onSuccess(List<Employee> result) {
                    recyclerView.setAdapter(new EmployeeItemRecyclerViewAdapter(result));
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
//        if(context instanceof OnListFragmentInteractionListener)
//        {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else
//        {
//            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Employee item);
    }
}
