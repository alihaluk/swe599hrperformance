package tr.edu.boun.hrperformance.controls;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.models.Employee;
import tr.edu.boun.hrperformance.models.EmployeeTask;
import tr.edu.boun.hrperformance.models.HRGroup;

public class AddTaskActivity extends AppCompatActivity
{

    EditText edt_title;
    EditText edt_body;
    static String str_deadline = "";

    Spinner spn_hrGroups;
    Spinner spn_employees;

    List<String> lvArray_hrGroups = new ArrayList<>();
    List<HRGroup> hrGroupList = new ArrayList<>();
    String selectedHRGroup = "";

    List<rowLayout> lvArray_employees = new ArrayList<>();
    List<Employee> employees = new ArrayList<>();
    List<String> selectedEmployees = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        edt_title = findViewById(R.id.addTask_title);
        edt_body = findViewById(R.id.addTask_body);

        spn_hrGroups = findViewById(R.id.hrgroup_spinner);
        spn_hrGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (i > 0)
                {
                    selectedHRGroup = hrGroupList.get(i).uid;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        spn_employees = findViewById(R.id.spinner_employees);

        Button deadlinePickerBtn = findViewById(R.id.addTask_btn_deadline);
        deadlinePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DialogFragment f = new MyDatePickerFragment();
                f.show(getSupportFragmentManager(), "");
            }
        });

        // populate spinners
        populateHRGroups();
        populateEmployees();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_add_task, menu);

        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_add_task_send) {
            // send task
            edt_title.setError(null);
            edt_body.setError(null);

            String title = edt_title.getText().toString();
            String body = edt_body.getText().toString();

            if (TextUtils.isEmpty(title))
            {
                edt_title.setError("Title is required!");
                return false;
            }
            if (TextUtils.isEmpty(str_deadline))
            {
                Toast.makeText(AddTaskActivity.this, "Deadline is required!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (selectedEmployees.size() <= 0 && selectedHRGroup.isEmpty())
            {
                Toast.makeText(AddTaskActivity.this, "Task is not assigned!", Toast.LENGTH_SHORT).show();
                return false;
            }

            // create task and assign to selected employees
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("user_info",Context.MODE_PRIVATE);
            String userID = prefs.getString("userID", "");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            String taskUid = myRef.child("tasks").push().getKey();
            for (String employeeUid : selectedEmployees)
            {
                EmployeeTask task = new EmployeeTask(taskUid, title, userID, employeeUid, body, str_deadline);
                Map<String, Object> taskValues = task.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/tasks/" + taskUid, taskValues);
                childUpdates.put("/employee-tasks/" + employeeUid + "/" + taskUid, taskValues);

                myRef.updateChildren(childUpdates);
            }

            finish();
            return true;
        }
        return false;
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

                for (DataSnapshot emp : employeeSnapshots)
                {
                    Employee e = emp.getValue(Employee.class);
                    employees.add(e);
                    lvArray_employees.add(new rowLayout(e.uid, e.name, false));
                }

                MultiSelectSpinnerAdapter adapter = new MultiSelectSpinnerAdapter(AddTaskActivity.this, lvArray_employees);
                spn_employees.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
            }
        };

        myRef.addListenerForSingleValueEvent(employeeListener);
    }

    public void populateHRGroups()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hr_groups");

        ValueEventListener hrgroupListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Iterable<DataSnapshot> hr_groups = dataSnapshot.getChildren();

                for (DataSnapshot hr : hr_groups)
                {
                    HRGroup g = hr.getValue(HRGroup.class);
                    hrGroupList.add(g);
                    lvArray_hrGroups.add(g.name);
                }

                spn_hrGroups.setAdapter(new ArrayAdapter<String>(AddTaskActivity.this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, lvArray_hrGroups));
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
            }
        };

        myRef.addListenerForSingleValueEvent(hrgroupListener);
    }

    public static class MyDatePickerFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener
    {
        int datePicker = -1;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {

            Calendar c = Calendar.getInstance();
            String title = "";
            title = "Deadline";

            datePicker = R.id.addTask_btn_deadline;

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH); // MONTH (0-11)
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.setTitle(title);
            dpd.getDatePicker().setMinDate(c.getTimeInMillis());

            return dpd;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            Button btn = (Button) getActivity().findViewById(datePicker);
            String result = year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
            str_deadline = result;
            btn.setText(result);
        }
    }

    public class rowLayout
    {

        String pId;
        String pName;
        Boolean pChecked;

        public rowLayout(String id, String pName, Boolean pChecked)
        {

            this.pId = id;
            this.pName = pName;
            this.pChecked = pChecked;
        }
    }

    public class ViewHolder
    {
        TextView name;
        CheckBox checkbox1;
    }

    public class MultiSelectSpinnerAdapter extends ArrayAdapter<rowLayout>
    {
        private final Context context;
        private final List<rowLayout> values;

        public MultiSelectSpinnerAdapter(Context context, List<rowLayout> values)
        {
            super(context, R.layout.spinneritem_multiple_employee, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            ViewHolder holder;

            if(convertView == null)
            {
                convertView = View.inflate(context, R.layout.spinneritem_multiple_employee, null);
                holder = new ViewHolder();

                holder.name = convertView.findViewById(R.id.textView_employeeName);
                holder.checkbox1 = convertView.findViewById(R.id.checkbox_employeeSelected);
                holder.checkbox1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        lvArray_employees.get(view.getId()).pChecked = !(lvArray_employees.get(view.getId()).pChecked);

                        if (lvArray_employees.get(view.getId()).pChecked)
                        {
                            selectedEmployees.add(lvArray_employees.get(view.getId()).pId);
                        }
                        else
                        {
                            selectedEmployees.remove(lvArray_employees.get(view.getId()).pId);
                        }
                    }
                });

                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(lvArray_employees.get(position).pName);
            holder.name.setId(position);

            holder.checkbox1.setChecked(lvArray_employees.get(position).pChecked);
            holder.checkbox1.setId(position);

            return convertView;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            ViewHolder holder;

            if (convertView == null)
            {
                convertView = View.inflate(context, R.layout.spinneritem_multiple_employee, null);
                holder = new ViewHolder();

                holder.name = convertView.findViewById(R.id.textView_employeeName);
                holder.checkbox1 = convertView.findViewById(R.id.checkbox_employeeSelected);
                holder.checkbox1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        lvArray_employees.get(view.getId()).pChecked = !(lvArray_employees.get(view.getId()).pChecked);

                        if (lvArray_employees.get(view.getId()).pChecked)
                        {
                            selectedEmployees.add(lvArray_employees.get(view.getId()).pId);
                        }
                        else
                        {
                            selectedEmployees.remove(lvArray_employees.get(view.getId()).pId);
                        }
                    }
                });

                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(lvArray_employees.get(position).pName);
            holder.name.setId(position);

            holder.checkbox1.setChecked(lvArray_employees.get(position).pChecked);
            holder.checkbox1.setId(position);

            return convertView;
        }
    }
}
