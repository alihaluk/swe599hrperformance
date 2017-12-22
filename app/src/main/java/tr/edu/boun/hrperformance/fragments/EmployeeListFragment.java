package tr.edu.boun.hrperformance.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.adapters.EmployeeItemRecyclerViewAdapter;
import tr.edu.boun.hrperformance.models.Employee;


public class EmployeeListFragment extends Fragment
{

    private Context context;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmployeeListFragment()
    {
    }

    public static EmployeeListFragment newInstance()
    {
        EmployeeListFragment fragment = new EmployeeListFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_employee_list, container, false);

        // Set the adapter
        if(view instanceof RecyclerView)
        {
            context = view.getContext();
            recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            populateEmployees();
        }
        return view;
    }

    public void populateEmployees()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("employees");

        ValueEventListener employeeListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Iterable<DataSnapshot> employeeSnapshots = dataSnapshot.getChildren();

                List<Employee> employees = new ArrayList<>();
                for (DataSnapshot emp : employeeSnapshots)
                {
                    Employee e = emp.getValue(Employee.class);
                    employees.add(e);
                }

                recyclerView.setAdapter(new EmployeeItemRecyclerViewAdapter(employees));
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
            }
        };

        myRef.addListenerForSingleValueEvent(employeeListener);
    }
}
