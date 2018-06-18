package com.towa.sergiomaldonado.task_app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.towa.sergiomaldonado.task_app.Adapters.TaskListAdapter;
import com.towa.sergiomaldonado.task_app.DAO.DBUtil;
import com.towa.sergiomaldonado.task_app.DAO.TaskDB;
import com.towa.sergiomaldonado.task_app.Models.Task;
import com.towa.sergiomaldonado.task_app.R;

import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private List<Task> ListTask;
    BroadcastReceiver showTaskReciever;
    final static String TAG = "TaskListActivity";
    RecyclerView rvTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        rvTaskList = findViewById(R.id.recyclerView);

        showTaskReciever = new TaskListActivity.ShowTaskReceiver(rvTaskList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.SMP.CUSTOM_INTENT.TasksReady");
        this.registerReceiver(showTaskReciever, intentFilter);
    }

    @Override
    protected void onResume() {
        //                                                  //GetAllTask
        super.onResume();
        TaskDB taskDBINstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBINstance, getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "The onPause() event");

        //                                                  //Hacer unregister
        Log.d(TAG, "Unregistrado......");
        this.unregisterReceiver(this.showTaskReciever);
    }

    public void getAllTask(View view){
        TaskDB taskDBINstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetAllTask(taskDBINstance, getApplicationContext());
    }
    public void getDoneTask(View view){
        TaskDB taskDBINstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetDoneTask(taskDBINstance, getApplicationContext());
    }

    public void getToDoTask(View view){
        TaskDB taskDBINstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetToDoTask(taskDBINstance, getApplicationContext());
    }

    public void getDoingTask(View view){
        TaskDB taskDBINstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetDoingTask(taskDBINstance, getApplicationContext());
    }

    private class ShowTaskReceiver extends BroadcastReceiver {

        RecyclerView rvTaskList;

        public ShowTaskReceiver(RecyclerView rvTaskList) {
            this.rvTaskList = rvTaskList;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            List<Task> listOfTask = DBUtil.getTasks();

            TaskListAdapter adTaskList = new TaskListAdapter(listOfTask, getApplicationContext());
            rvTaskList.setAdapter(adTaskList);

            LinearLayoutManager manager = new LinearLayoutManager(
                    getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                    rvTaskList.setLayoutManager(manager);

            for (Task task : listOfTask) {
                Log.d(TAG, task.getStrShortDesc() + ", " +
                        String.valueOf(task.getIntPercentage()));
            }
        }
    }
}
