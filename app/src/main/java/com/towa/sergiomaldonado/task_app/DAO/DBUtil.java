package com.towa.sergiomaldonado.task_app.DAO;

import com.towa.sergiomaldonado.task_app.Adapters.TaskListAdapter;
import com.towa.sergiomaldonado.task_app.Models.Task;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    final static String TAG = "DBUtil";
    static int intTaskToDoCount = 0;
    static int intTaskDoingCount = 0;
    static int intTaskDoneCount = 0;

    public static int getToDoTaskCount(){ return intTaskToDoCount; }
    public static int getDoingTaskCount(){ return intTaskDoingCount; }
    public static int getDoneTaskCount(){ return intTaskDoneCount; }

    static List<Task> tasks = new ArrayList<>();
    public static List<Task> getTasks() {
        return tasks;
    }

    public static List<Task> DBGetAllTask(TaskDB taskDBInstance, Context context){
        GetAllTask getAllTask = new GetAllTask(taskDBInstance, context);
        getAllTask.execute();
        return tasks;
    }

    public static List<Task> DBGetDoneTask(TaskDB taskDBInstance, Context context){
        GetDoneTask getDoneTask = new GetDoneTask(taskDBInstance, context);
        getDoneTask.execute();
        return tasks;
    }
    public static List<Task> DBGetToDoTask(TaskDB taskDBInstance, Context context){
        GetToDoTask getToDoTask = new GetToDoTask(taskDBInstance, context);
        getToDoTask.execute();
        return tasks;
    }
    public static List<Task> DBGetDoingTask(TaskDB taskDBInstance, Context context){
        GetDoingTask getDoingTask = new GetDoingTask(taskDBInstance, context);
        getDoingTask.execute();
        return tasks;
    }

    public static void DBSaveNewTask(TaskDB taskDBInstance, Task task){
        SaveNewTask saveNewTask = new SaveNewTask(taskDBInstance, task);
        saveNewTask.execute();
    }

    public static void DBDeleteNewTask(TaskDB taskDBInstance, Task task, TaskListAdapter adapter){
        DeleteNewTask deleteNewTask = new DeleteNewTask(taskDBInstance, task, adapter);
        deleteNewTask.execute();
    }

    public static List<Task> DBGetSummary(TaskDB taskDBInstance, Context context){
        GetSummaryTask getSummaryTask = new GetSummaryTask(taskDBInstance, context);
        getSummaryTask.execute();
        return tasks;
    }

    //-----------------------------------------------------------------------------------------------------------------
    private static class GetAllTask extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Context context;

        public GetAllTask(TaskDB taskDBInstance, Context context) {
            this.taskDBInstance = taskDBInstance;
            this.context = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            tasks = taskDBInstance.taskDAO().getAll();
            Log.d(TAG, tasks.size() + " Tasks in DB");
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            Log.d(TAG, "TasksReady");
            Intent intent = new Intent();
            intent.setAction("com.SMP.CUSTOM_INTENT.TasksReady");
            context.sendBroadcast(intent);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    private static class GetDoneTask extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Context context;

        public GetDoneTask(TaskDB taskDBInstance, Context context) {
            this.taskDBInstance = taskDBInstance;
            this.context = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            tasks = taskDBInstance.taskDAO().getDone();
            Log.d(TAG, tasks.size() + " Tasks in DB");
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            Log.d(TAG, "TasksReady");
            Intent intent = new Intent();
            intent.setAction("com.SMP.CUSTOM_INTENT.TasksReady");
            context.sendBroadcast(intent);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    private static class GetToDoTask extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Context context;

        public GetToDoTask(TaskDB taskDBInstance, Context context) {
            this.taskDBInstance = taskDBInstance;
            this.context = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            tasks = taskDBInstance.taskDAO().getToDo();
            Log.d(TAG, tasks.size() + " Tasks in DB");
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            Log.d(TAG, "TasksReady");
            Intent intent = new Intent();
            intent.setAction("com.SMP.CUSTOM_INTENT.TasksReady");
            context.sendBroadcast(intent);
        }
    }//-----------------------------------------------------------------------------------------------------------------
    private static class GetDoingTask extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Context context;

        public GetDoingTask(TaskDB taskDBInstance, Context context) {
            this.taskDBInstance = taskDBInstance;
            this.context = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            tasks = taskDBInstance.taskDAO().getDoing();
            Log.d(TAG, tasks.size() + " Tasks in DB");
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            Log.d(TAG, "TasksReady");
            Intent intent = new Intent();
            intent.setAction("com.SMP.CUSTOM_INTENT.TasksReady");
            context.sendBroadcast(intent);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    private static class SaveNewTask extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Task task;

        public SaveNewTask(TaskDB taskDBInstance, Task task) {
            this.taskDBInstance = taskDBInstance;
            this.task = task;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            taskDBInstance.taskDAO().insertTask(task);
            Log.d(TAG, "Saving new task ");
            return null;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private static class DeleteNewTask extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Task task;
        TaskListAdapter adapter;

        public DeleteNewTask(TaskDB taskDBInstance, Task task, TaskListAdapter adapter) {
            this.taskDBInstance = taskDBInstance;
            this.task = task;
            this.adapter = adapter;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            taskDBInstance.taskDAO().deleteTask(task);
            Log.d(TAG, "Deleting new task ");
            return null;
        }
        @Override
        protected void onPostExecute(Void v)
        {
            adapter.RemoveItem(task);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    private static class GetSummaryTask extends AsyncTask<Void, Void, Void> {
        TaskDB taskDBInstance;
        Context context;

        public GetSummaryTask(TaskDB taskDBInstance, Context context) {
            this.taskDBInstance = taskDBInstance;
            this.context = context;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            intTaskToDoCount = taskDBInstance.taskDAO().getToDo().size();
            intTaskDoingCount = taskDBInstance.taskDAO().getDoing().size();
            intTaskDoneCount = taskDBInstance.taskDAO().getDone().size();

            Log.d(TAG, tasks.size() + " Tasks in DB");
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            Log.d(TAG, "TasksReady");
            Intent intent = new Intent();
            intent.setAction("com.SMP.CUSTOM_INTENT.SummaryReady");
            context.sendBroadcast(intent);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
}
