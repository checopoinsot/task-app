package com.towa.sergiomaldonado.task_app.Adapters;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.towa.sergiomaldonado.task_app.DAO.DBUtil;
import com.towa.sergiomaldonado.task_app.DAO.TaskDB;
import com.towa.sergiomaldonado.task_app.Fragments.DetailDialogFragment;
import com.towa.sergiomaldonado.task_app.Models.Task;
import com.towa.sergiomaldonado.task_app.R;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> {

    private List<Task> listOfTasks;
    private Context context;

    public TaskListAdapter(List<Task> listOfTasks, Context context) {
        this.listOfTasks = listOfTasks;
        this.context = context;
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_task, parent,false);

        TaskHolder taskHolder = new TaskHolder(view);
        return taskHolder;
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvstrShortDesc;
        TextView tvintPercentage;
        ImageButton imgbtnDelete;

        public TaskHolder(View itemView) {
            super(itemView);

            tvstrShortDesc = itemView.findViewById(R.id.TextView_ShortDesc);
            tvintPercentage = itemView.findViewById(R.id.TextView_IdPercentage);
            imgbtnDelete = itemView.findViewById(R.id.imgbtnDelete);
        }

        @Override
        public void onClick(View v) {
            DialogFragment newFragment = DetailDialogFragment.newInstance(
                    1);
            //newFragment.show((Activity)context.get, "dialog");
        }
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {

        final Task task = listOfTasks.get(position);
        String strTask = task.getStrShortDesc();

        String intPercentage =  String.valueOf(task.getIntPercentage())+ "%";
        holder.tvstrShortDesc.setText(strTask);
        holder.tvintPercentage.setText(intPercentage);
        holder.imgbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDB taskDBInstance = TaskDB.getTaskDB(context);
                DBUtil.DBDeleteNewTask(taskDBInstance, task, TaskListAdapter.this);

                TaskDB.destroyInstance();

                listOfTasks.remove(task);
            }
        });
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public int getItemCount() {
        return this.listOfTasks.size();
    }

    public void RemoveItem(Task task){
        listOfTasks.remove(task);
        notifyDataSetChanged();
    }
}
