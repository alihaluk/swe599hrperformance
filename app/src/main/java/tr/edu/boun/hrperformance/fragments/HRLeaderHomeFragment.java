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
import tr.edu.boun.hrperformance.models.Employee;
import tr.edu.boun.hrperformance.models.EmployeeTask;


public class HRLeaderHomeFragment extends Fragment
{

    private static final String ARG_PARAM1 = "ARG_USER_ID";

    private String mUserID;

    private Context context;
    private RecyclerView recyclerView;

    public HRLeaderHomeFragment()
    {
        // Required empty public constructor
    }

    public static HRLeaderHomeFragment newInstance(String param1)
    {
        HRLeaderHomeFragment fragment = new HRLeaderHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            mUserID = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hrleader_home, container, false);

        // Set the adapter
        if(view instanceof RecyclerView)
        {
            context = view.getContext();
            recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            PopulateHRGroupTasks();
        }

        return view;
    }

    public void PopulateHRGroupTasks()
    {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener taskListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                DataSnapshot allEmployeesSnapshot = dataSnapshot.child("employees");
//                DataSnapshot employeeTasksSnapshot = dataSnapshot.child("tasks");
//
//                Iterable<DataSnapshot> allTasks = employeeTasksSnapshot.getChildren();
//
//                List<EmployeeTask> myTask_list = new ArrayList<>();
//                for (DataSnapshot e : allTasks)
//                {
//                    EmployeeTask task = e.getValue(EmployeeTask.class);
//
//                    if (task != null && task.assigner.equals(mUserID))
//                    {
//                        DataSnapshot employeeSnapshot = allEmployeesSnapshot.child(task.employee);
//                        Employee employee = employeeSnapshot.getValue(Employee.class);
//
//                        if (employee.groups.containsKey(mUserID))
//
//                    }
//                        myTask_list.add(task);
//                }
//
//                recyclerView.setAdapter(new EmployeeTasksAdapter(myTask_list));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        myRef.addValueEventListener(taskListener);
    }
}
