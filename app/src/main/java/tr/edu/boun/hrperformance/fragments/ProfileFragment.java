package tr.edu.boun.hrperformance.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.models.Employee;
import tr.edu.boun.hrperformance.models.HRLeader;
import tr.edu.boun.hrperformance.services.DataProvider;
import tr.edu.boun.hrperformance.services.MockDataProvider;


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
                Toast.makeText(context, "Add to group not implemented in Beta", Toast.LENGTH_SHORT).show();
            }
        });

        if (userType.equals("employee"))
        {
             MockDataProvider.getInstance().getEmployee(requestedUserId == null || requestedUserId.isEmpty() ? userID : requestedUserId, new DataProvider.DataCallback<Employee>() {
                 @Override
                 public void onSuccess(Employee emp) {
                     tv_name.setText(emp.name);
                     tv_email.setText(emp.email);
                     tv_hrgroup.setText("Score: " + emp.score);
                     tv_user_type.setText("Employee");
                     if (requestedUserId == null || requestedUserId.isEmpty())
                     {
                         btn_add2group.setVisibility(View.GONE);
                     }
                 }
                 @Override
                 public void onError(Exception e) {}
             });

        }
        else if (userType.equals("hrleader"))
        {
            if (requestedUserId == null || requestedUserId.isEmpty())
            {
                tv_name.setText("HR Leader");
                tv_email.setText("hr@boun.edu.tr");
                tv_user_type.setText(userType);
                tv_hrgroup.setVisibility(View.GONE);
                btn_add2group.setVisibility(View.GONE);
            }
            else
            {
                MockDataProvider.getInstance().getEmployee(requestedUserId, new DataProvider.DataCallback<Employee>() {
                 @Override
                 public void onSuccess(Employee emp) {
                     tv_name.setText(emp.name);
                     tv_email.setText(emp.email);
                     tv_hrgroup.setText("Score: " + emp.score);
                     tv_user_type.setText("Employee");
                 }
                 @Override
                 public void onError(Exception e) {}
             });
            }

        }

        return view;
    }

}
