package tr.edu.boun.hrperformance.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
import tr.edu.boun.hrperformance.adapters.EmployeeTasksAdapter;
import tr.edu.boun.hrperformance.models.Employee;
import tr.edu.boun.hrperformance.models.EmployeeTask;
import tr.edu.boun.hrperformance.models.HRGroup;
import tr.edu.boun.hrperformance.models.HRLeader;


public class ProfileFragment extends Fragment
{
    private static final String ARG_PARAM1 = "ARG_REQUESTED_USER_ID";

    private Context context;
    TextView tv_name;
    TextView tv_email;
    TextView tv_user_type;
    TextView tv_hrgroup;
    Button btn_add2group;

    String requestedUserId;
    String selectedHRGroupName = "";

    public ProfileFragment()
    {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String requestedUserId)
    {
         Bundle args = new Bundle();
        args.putString(ARG_PARAM1, requestedUserId);
         ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            requestedUserId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        context = view.getContext();
        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences("user_info",Context.MODE_PRIVATE);
        String userID = prefs.getString("userID", "");
        final String userType = prefs.getString("userType", "");

        tv_name = view.findViewById(R.id.profile_name);
        tv_email = view.findViewById(R.id.profile_email);
        tv_user_type = view.findViewById(R.id.profile_user_type);
        tv_hrgroup = view.findViewById(R.id.profile_hrgroup);
        btn_add2group = view.findViewById(R.id.profile_add2group);
        btn_add2group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // add to group

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("hr_groups");

                ValueEventListener taskListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> groupSnaps = dataSnapshot.getChildren();

                        List<String> groupList = new ArrayList<>();
                        for (DataSnapshot ds : groupSnaps)
                        {
                            HRGroup g =  ds.getValue(HRGroup.class);
                            groupList.add(g.name);
                        }

                        View view = getLayoutInflater().inflate(R.layout.dialog_hrgroup_selection, null);
                        Spinner spn_hrgroups = (Spinner) view.findViewById(R.id.spinner_hrgroup_profile);
                        ArrayAdapter<String> adapInfo = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, groupList);
                        adapInfo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spn_hrgroups.setAdapter(adapInfo);
                        spn_hrgroups.setSelection(0);
                        spn_hrgroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                        {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
                            {
                                selectedHRGroupName = arg0.getItemAtPosition(arg2).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0)
                            {

                            }
                        });
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Choose HR Group to add the Employee");
                        builder.setView(view);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // save new group

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference();

                                Map<String, Boolean> employeeGroups = new HashMap<>();
                                employeeGroups.put(selectedHRGroupName, true);

                                myRef.child("employees").child(requestedUserId).child("hr_groups").setValue(employeeGroups);
                                
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                };

                myRef.addListenerForSingleValueEvent(taskListener);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        if (userType.equals("employee"))
        {
            DatabaseReference employeeRef = myRef.child("employees").child(requestedUserId.isEmpty() ? userID : requestedUserId);

            ValueEventListener taskListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Employee emp = dataSnapshot.getValue(Employee.class);
                    tv_name.setText(emp.name);
                    tv_email.setText(emp.email);
                    tv_hrgroup.setText(emp.groups.toString());
                    tv_user_type.setText("Employee");
                    if (requestedUserId.isEmpty())
                    {
                        btn_add2group.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };

            employeeRef.addListenerForSingleValueEvent(taskListener);

        }
        else if (userType.equals("hrleader"))
        {
            if (requestedUserId.isEmpty())
            {
                DatabaseReference hrleaderRef = myRef.child("hrleaders").child(userID);

                ValueEventListener taskListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        HRLeader hr = dataSnapshot.getValue(HRLeader.class);
                        tv_name.setText(hr.name);
                        tv_email.setText(hr.email);
                        tv_user_type.setText(userType);
                        tv_hrgroup.setVisibility(View.GONE);
                        btn_add2group.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                };

                hrleaderRef.addListenerForSingleValueEvent(taskListener);
            }
            else
            {
                DatabaseReference employeeRef = myRef.child("employees").child(requestedUserId);

                ValueEventListener taskListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Employee emp = dataSnapshot.getValue(Employee.class);
                        tv_name.setText(emp.name);
                        tv_email.setText(emp.email);
                        tv_hrgroup.setText(emp.groups.toString());
                        tv_user_type.setText("Employee");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                };

                employeeRef.addListenerForSingleValueEvent(taskListener);
            }

        }

        return view;
    }

}
