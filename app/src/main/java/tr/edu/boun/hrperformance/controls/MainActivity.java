package tr.edu.boun.hrperformance.controls;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.fragments.EmployeeHomeFragment;
import tr.edu.boun.hrperformance.fragments.HRLeaderHomeFragment;
import tr.edu.boun.hrperformance.models.EmployeeTask;

public class MainActivity extends AppCompatActivity
{
//    private FirebaseDatabase mDatabase;
//    private static final String FIREBASE_TAG = "Firebase";

    private String userType;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userType = getIntent().getStringExtra("userType");
        userId = getIntent().getStringExtra("userID");

        TextView tv = findViewById(R.id.main_text);

        tv.setText("Wellcome " + userType + "!\n" + userId);
        tv.setVisibility(View.GONE);

//        mDatabase = FirebaseDatabase.getInstance();

        if (userType.equals("employee"))
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, EmployeeHomeFragment.newInstance(userId), "Employee_Tasks")
                    .commit();
        }
        else if (userType.equals("hrleader"))
        {
            setTitle("Developers");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, HRLeaderHomeFragment.newInstance(userId), "HRGroup_Tasks")
                    .commit();
        }

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
