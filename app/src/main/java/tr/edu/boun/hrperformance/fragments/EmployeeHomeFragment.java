package tr.edu.boun.hrperformance.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.adapters.EmployeeTasksAdapter;
import tr.edu.boun.hrperformance.models.Employee;
import tr.edu.boun.hrperformance.models.EmployeeTask;
import tr.edu.boun.hrperformance.services.DataProvider;
import tr.edu.boun.hrperformance.services.MockDataProvider;

public class EmployeeHomeFragment extends Fragment implements EmployeeTasksAdapter.OnTaskActionListener {

    private static final String ARG_PARAM1 = "ARG_USER_ID";

    private String mUserID;

    private Context context;
    private RecyclerView recyclerView;
    private TextView scoreTextView;

    public EmployeeHomeFragment() {
        // Required empty public constructor
    }

    public static EmployeeHomeFragment newInstance(String param1) {
        EmployeeHomeFragment fragment = new EmployeeHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserID = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_home, container, false);

        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.employee_list);
        scoreTextView = (TextView) view.findViewById(R.id.text_score);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        PopulateEmployeeTasks();
        UpdateScore();

        return view;
    }

    public void PopulateEmployeeTasks() {
        MockDataProvider.getInstance().getEmployeeTasks(mUserID, new DataProvider.DataCallback<List<EmployeeTask>>() {
            @Override
            public void onSuccess(List<EmployeeTask> result) {
                recyclerView.setAdapter(new EmployeeTasksAdapter(result, EmployeeHomeFragment.this));
            }

            @Override
            public void onError(Exception e) {
                Log.e("EmployeeHome", "Error fetching tasks", e);
            }
        });
    }

    public void UpdateScore() {
        MockDataProvider.getInstance().getEmployee(mUserID, new DataProvider.DataCallback<Employee>() {
            @Override
            public void onSuccess(Employee result) {
                if (scoreTextView != null) {
                    scoreTextView.setText("Score: " + result.score);
                }
            }

            @Override
            public void onError(Exception e) {
                // handle error
            }
        });
    }

    @Override
    public void onTaskStart(EmployeeTask task) {
        task.startTime = getNow();
        MockDataProvider.getInstance().updateTask(task, new DataProvider.DataCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(context, "Task Started!", Toast.LENGTH_SHORT).show();
                PopulateEmployeeTasks(); // Refresh list to update UI state
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "Error starting task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTaskFinish(final EmployeeTask task) {
        task.finishTime = getNow();
        MockDataProvider.getInstance().updateTask(task, new DataProvider.DataCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(context, "Task Finished! +10 Points", Toast.LENGTH_SHORT).show();

                // Update Score
                MockDataProvider.getInstance().getEmployee(mUserID, new DataProvider.DataCallback<Employee>() {
                    @Override
                    public void onSuccess(Employee emp) {
                        emp.score += 10;
                        MockDataProvider.getInstance().updateEmployee(emp, new DataProvider.DataCallback<Void>() {
                            @Override
                            public void onSuccess(Void result) {
                                UpdateScore();
                                PopulateEmployeeTasks(); // Refresh list
                            }

                            @Override
                            public void onError(Exception e) {}
                        });
                    }

                    @Override
                    public void onError(Exception e) {}
                });
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "Error finishing task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getNow() {
        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
        Date cal = Calendar.getInstance().getTime();
        return dateFormat.format(cal);
    }
}
