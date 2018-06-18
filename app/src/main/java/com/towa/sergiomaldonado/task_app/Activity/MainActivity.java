package com.towa.sergiomaldonado.task_app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.towa.sergiomaldonado.task_app.DAO.DBUtil;
import com.towa.sergiomaldonado.task_app.DAO.TaskDB;
import com.towa.sergiomaldonado.task_app.R;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    BroadcastReceiver summaryReciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "The onCreate() event");

        summaryReciver = new MainActivity.UpdateTaskCountReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "The onStart() event");
        IntentFilter intentFilter = new IntentFilter("com.SMP.CUSTOM_INTENT.SummaryReady");
        this.registerReceiver(summaryReciver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"The onPause() event");
        this.unregisterReceiver(this.summaryReciver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "The onResume() event");
        TaskDB taskDBINstance = TaskDB.getTaskDB(getApplicationContext());
        DBUtil.DBGetSummary(taskDBINstance, getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"The onStop() event");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"The onDestroy() event");
    }

    public void ShowNewTaskForm(View view){
        //                                                  //onClick-ButtonNewTask(New Button)
        //                                                  //To verify the click get the method
        Log.d(TAG, "click ButtonNewTask");
        //                                                  //Explicit Intent to start NewTaskFormA
        Intent intent = new Intent(getApplicationContext(),NewTaskFormActivity.class);
        startActivity(intent);
    }

    public void ShowAllTask(View view){
        //                                                  //onClick-ButtonNewTask(New Button)
        //                                                  //To verify the click get the method
        Log.d(TAG, "click ButtonNewTask");
        //                                                  //Explicit Intent to start NewTaskFormA
        Intent intent = new Intent(getApplicationContext(),TaskListActivity.class);
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------
    private class UpdateTaskCountReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){

            int intToDoTask = DBUtil.getToDoTaskCount();
            TextView textViewTaskToDo = findViewById(R.id.txt_TaskToDo);
            textViewTaskToDo.setText(String.valueOf(intToDoTask) + " Task To Do");

            int intDoingTask = DBUtil.getDoingTaskCount();
            TextView textViewTaskDoing = findViewById(R.id.txt_TaskDoing);
            textViewTaskDoing.setText(String.valueOf(intDoingTask) + " Task Doing");

            int intDoneTask = DBUtil.getDoneTaskCount();
            TextView textViewTaskDone = findViewById(R.id.txt_TaskDone);
            textViewTaskDone.setText(String.valueOf(intDoneTask) + " Task Done");

        }
    }
}
