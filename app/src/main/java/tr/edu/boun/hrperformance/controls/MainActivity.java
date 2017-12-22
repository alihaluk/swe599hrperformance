package tr.edu.boun.hrperformance.controls;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.fragments.EmployeeListFragment;
import tr.edu.boun.hrperformance.fragments.EmployeeHomeFragment;
import tr.edu.boun.hrperformance.fragments.HRLeaderHomeFragment;
import tr.edu.boun.hrperformance.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity
{
//    private FirebaseDatabase mDatabase;
//    private static final String FIREBASE_TAG = "Firebase";

    private String userType;
    private String userId;

    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {

            switch(item.getItemId())
            {
                case R.id.nav_home:
                    if (userType.equals("employee"))
                    {
                        replaceFragment(EmployeeHomeFragment.newInstance(userId),"Home");
                    }
                    else if (userType.equals("hrleader"))
                    {
                        replaceFragment(HRLeaderHomeFragment.newInstance(userId),"Home");
                    }
                    return true;
                case R.id.nav_groups:
//                    replaceFragment(ExploreFragment.newInstance(),"Explore");
                    return true;
                case R.id.nav_employees:
                    replaceFragment(EmployeeListFragment.newInstance(),"Employees");
                    return true;
                case R.id.nav_profile:
                    replaceFragment(ProfileFragment.newInstance(""),"Profile");
                    return true;
            }

            return false;
        }

    };

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, newFragment, tag)
                .commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userType = getIntent().getStringExtra("userType");
        userId = getIntent().getStringExtra("userID");

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TextView tv = findViewById(R.id.main_text);

        tv.setText("Wellcome " + userType + "!\n" + userId);
        tv.setVisibility(View.GONE);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("user_info",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("userID", userId);
        edit.putString("userType", userType);
        edit.commit();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (userType.equals("employee"))
        {
            ft.add(R.id.fragment_container, EmployeeHomeFragment.newInstance(userId), "Home").commit();
        }
        else if (userType.equals("hrleader"))
        {
            ft.add(R.id.fragment_container, HRLeaderHomeFragment.newInstance(userId), "Home").commit();
        }

//        mDatabase = FirebaseDatabase.getInstance();

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference();


        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(FIREBASE_TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(FIREBASE_TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

    public void populateTasks()
    {

//        DatabaseReference myRef = mDatabase.getReference();
//
//        ValueEventListener taskListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                DataSnapshot employeeTasksSnapshot = dataSnapshot.child("employee_tasks");
//                DataSnapshot myTaskSnapshot = employeeTasksSnapshot.child(userId);
//
//                Iterable<DataSnapshot> myTasks = myTaskSnapshot.getChildren();
//
//                List<EmployeeTask> myTask_list = new ArrayList<>();
//                for (DataSnapshot e : myTasks)
//                {
//                    EmployeeTask task = e.getValue(EmployeeTask.class);
//
//                    if (task != null)
//                        myTask_list.add(task);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        };
//
//        myRef.addValueEventListener(taskListener);
    }

    public void populateEmployees()
    {
        //        DatabaseReference employeeRef = database.getReference("employees");
//        employeeRef.setValue("hello");
//
//        List<Employee> initial_employees = new ArrayList<Employee>();
//        initial_employees.add(new Employee("Haluk Seven", "haluk.seven@boun.edu.tr", "haluks"));
//        initial_employees.add(new Employee("Muhsin Aler", "muhsin.aler@boun.edu.tr", "muhsina"));
//        initial_employees.add(new Employee("Abdullah Gündüz", "abdullah.gunduz@boun.edu.tr", "abdullahg"));
//
//        for(Employee e : initial_employees)
//        {
//            String userid = myRef.child("employees").push().getKey();
//            e.uid = userid;
//
//            myRef.child("employees").child(userid).setValue(e);
//        }
    }

    public void populateHRLeaders()
    {

//        DatabaseReference employeeRef = database.getReference("hrleaders");
//        employeeRef.setValue("hello");
//
//        List<HRLeader> initial_employees = new ArrayList<HRLeader>();
//        initial_employees.add(new HRLeader("Sertaç", "sertact@boun.edu.tr", "sertact"));
//        initial_employees.add(new HRLeader("Umut", "umuts@boun.edu.tr", "umuts"));
//        initial_employees.add(new HRLeader("Murat", "murata@boun.edu.tr", "murata"));
//
//        for(HRLeader e : initial_employees)
//        {
//            String userid = myRef.child("hrleaders").push().getKey();
//            e.uid = userid;
//
//            myRef.child("hrleaders").child(userid).setValue(e);
//        }
    }
}
