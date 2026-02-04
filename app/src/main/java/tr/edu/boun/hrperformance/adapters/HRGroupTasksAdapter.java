package tr.edu.boun.hrperformance.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tr.edu.boun.hrperformance.R;
import tr.edu.boun.hrperformance.models.EmployeeTask;
import tr.edu.boun.hrperformance.models.HRGroupTask;

/**
 * Created by haluks on 18.12.2017.
 */

public class HRGroupTasksAdapter extends RecyclerView.Adapter<HRGroupTasksAdapter.ViewHolder>
{
    private final List<HRGroupTask> mValues;

    public HRGroupTasksAdapter(List<HRGroupTask> items)
    {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_group_tasks, parent, false);
        return new HRGroupTasksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).title);
        holder.mProgress.setText(mValues.get(position).progress);

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
        public final TextView mProgress;
        public HRGroupTask mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.group_task_title);
            mProgress = (TextView) view.findViewById(R.id.group_task_progress);


        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }
}
