package tr.edu.boun.hrperformance.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tr.edu.boun.hrperformance.R;


public class HRLeaderHomeFragment extends Fragment
{
    private static final String ARG_PARAM1 = "ARG_USER_ID";
    private String mUserID;


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
        View view = inflater.inflate(R.layout.fragment_hrleader_home, container, false);
        // TextView tv = view.findViewById(R.id.textView2);
        // if(tv != null) tv.setText("HR Leader Home - Beta Version");
        return view;
    }

}
