package tr.edu.boun.hrperformance.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.models.EmployeeTask;

/**
 * Created by haluks on 17.12.2017.
 */

public class EmployeeTasksAdapter extends RecyclerView.Adapter<EmployeeTasksAdapter.ViewHolder>
{
    private final List<EmployeeTask> mValues;

    public EmployeeTasksAdapter(List<EmployeeTask> items)
    {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_employee_tasks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).title);
        holder.mDuetime.setText(mValues.get(position).dueTime);

        String startDate = mValues.get(position).startTime;
        if (startDate != null && !startDate.isEmpty())
        {
            holder.mStartDate.setText(mValues.get(position).startTime);
            holder.mStartButton.setEnabled(false);
        }
        else
        {
            holder.mStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    // start task
                }
            });
        }

        String finishDate = mValues.get(position).finishTime;
        if (finishDate != null && !finishDate.isEmpty())
        {
            holder.mFinishDate.setText(mValues.get(position).finishTime);
            holder.mFinishButton.setEnabled(false);
        }
        else
        {
            holder.mFinishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    // finish task
                }
            });
        }

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // go task detail
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
        public final TextView mTitle;
        public final TextView mDuetime;
        public final Button mStartButton;
        public final TextView mStartDate;
        public final Button mFinishButton;
        public final TextView mFinishDate;
        public EmployeeTask mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.task_title);
            mDuetime = (TextView) view.findViewById(R.id.task_duetime);
            mStartButton = (Button) view.findViewById(R.id.task_begin_btn);
            mStartDate = (TextView) view.findViewById(R.id.task_begin_date);
            mFinishButton = (Button) view.findViewById(R.id.task_finish_btn);
            mFinishDate = (TextView) view.findViewById(R.id.task_finish_date);

        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }
}
