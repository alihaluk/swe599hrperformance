package tr.edu.boun.hrperformance.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
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
import tr.edu.boun.hrperformance.adapters.EmployeeTasksAdapter;
import tr.edu.boun.hrperformance.models.EmployeeTask;

public class EmployeeHomeFragment extends Fragment
{

    private static final String ARG_PARAM1 = "ARG_USER_ID";

    private String mUserID;

    private Context context;
    private RecyclerView recyclerView;

    public EmployeeHomeFragment()
    {
        // Required empty public constructor
    }

    public static EmployeeHomeFragment newInstance(String param1)
    {
        EmployeeHomeFragment fragment = new EmployeeHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_employee_home, container, false);

        // Set the adapter
        if(view instanceof RecyclerView)
        {
            context = view.getContext();
            recyclerView = (RecyclerView) view;

            LinearLayoutManager llm = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(llm);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);

            PopulateEmployeeTasks();
        }

        return view;
    }

    public void PopulateEmployeeTasks()
    {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener taskListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot employeeTasksSnapshot = dataSnapshot.child("employee_tasks");
                DataSnapshot myTaskSnapshot = employeeTasksSnapshot.child(mUserID);

                Iterable<DataSnapshot> myTasks = myTaskSnapshot.getChildren();

                List<EmployeeTask> myTask_list = new ArrayList<>();
                for (DataSnapshot e : myTasks)
                {
                    EmployeeTask task = e.getValue(EmployeeTask.class);

                    if (task != null)
                        myTask_list.add(task);
                }

                if (myTask_list.size() <= 0)
                {
                    myTask_list.add(new EmployeeTask("1","Blockchain technology research", "", "", "", "20.12.2017"));
                    myTask_list.get(0).startTime = "13.12.2017";
                    myTask_list.add(new EmployeeTask("2","Watch the video about Firebase", "", "", "", "19.12.2017"));
                    myTask_list.get(1).startTime = "09.12.2017";
                    myTask_list.get(1).finishTime = "10.12.2017";
                    myTask_list.add(new EmployeeTask("3","Fill review form", "", "", "", "27.12.2017"));
                }

                recyclerView.setAdapter(new EmployeeTasksAdapter(myTask_list));
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
