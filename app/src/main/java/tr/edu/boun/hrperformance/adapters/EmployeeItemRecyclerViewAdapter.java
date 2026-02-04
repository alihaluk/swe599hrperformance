package tr.edu.boun.hrperformance.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.fragments.ProfileFragment;
import tr.edu.boun.hrperformance.models.Employee;


public class EmployeeItemRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeItemRecyclerViewAdapter.ViewHolder>
{
    private final List<Employee> mValues;
    private Context mContext;


    public EmployeeItemRecyclerViewAdapter(List<Employee> items)
    {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.listitem_employees, parent, false);
        return new EmployeeItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).name);
        holder.mContentView.setText(mValues.get(position).email);

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // open employee profile page
                String employeeID = holder.mItem.uid;
//                Intent i = new Intent(mContext.getApplicationContext(), ShowOverviewActivity.class);
//                i.putExtra("traktID", traktID);
//                mContext.getApplicationContext().startActivity(i);

                FragmentTransaction ft = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, ProfileFragment.newInstance(employeeID), "EmployeeProfile")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mContentView;
        public Employee mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.employee_name);
            mContentView = (TextView) view.findViewById(R.id.employee_email);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
