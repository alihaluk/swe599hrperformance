package tr.edu.boun.hrperformance.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.adapters.HRGroupTasksAdapter;
import tr.edu.boun.hrperformance.controls.AddTaskActivity;
import tr.edu.boun.hrperformance.models.Employee;
import tr.edu.boun.hrperformance.models.EmployeeTask;
import tr.edu.boun.hrperformance.models.HRGroupTask;


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


        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.group_task_list);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton btn_newTask = view.findViewById(R.id.fab);
        btn_newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(context.getApplicationContext(), AddTaskActivity.class);
                startActivity(i);
            }
        });

        PopulateHRGroupTasks();


        return view;
    }

    public void PopulateHRGroupTasks()
    {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener taskListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot employeeTasksSnapshot = dataSnapshot.child("employee-tasks");

                Iterable<DataSnapshot> allTasks = employeeTasksSnapshot.getChildren();

                List<EmployeeTask> myTask_list = new ArrayList<>();
                for (DataSnapshot e : allTasks)
                {
                    for (DataSnapshot t : e.getChildren())
                    {
                        EmployeeTask task = t.getValue(EmployeeTask.class);

                        if (task != null && task.assigner.equals(mUserID))
                        {
                            myTask_list.add(task);
                        }
                    }
                }


                List<HRGroupTask> groupTask_list = new ArrayList<>();
                Map<String, Integer> taskMap = new HashMap<>();
                Map<String, Integer> taskDoneMap = new HashMap<>();
                for (EmployeeTask et : myTask_list)
                {
                    if (!taskMap.containsKey(et.title))
                    {
                        taskMap.put(et.title, 1);
                        if (et.finishTime != null)
                        {
                            taskDoneMap.put(et.title, 1);
                        }
                    }
                    else
                    {
                        int count = taskMap.get(et.title);
                        taskMap.put(et.title, count+1);

                        if (et.finishTime != null)
                        {
                            int doneCount = taskDoneMap.get(et.title);
                            taskDoneMap.put(et.title, doneCount+1);
                        }
                    }
                }

                for (Map.Entry<String, Integer> entry : taskMap.entrySet())
                {
                    groupTask_list.add(new HRGroupTask(entry.getKey(),
                            (taskDoneMap.get(entry.getKey()) == null ? "0" : taskDoneMap.get(entry.getKey()).toString())
                                    + " / " + entry.getValue().toString()));
                }

                recyclerView.setAdapter(new HRGroupTasksAdapter(groupTask_list));

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
