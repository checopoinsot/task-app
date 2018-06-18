package com.towa.sergiomaldonado.task_app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.towa.sergiomaldonado.task_app.DAO.DBUtil;
import com.towa.sergiomaldonado.task_app.DAO.TaskDB;
import com.towa.sergiomaldonado.task_app.Models.Task;
import com.towa.sergiomaldonado.task_app.R;

import java.util.List;

public class NewTaskFormActivity extends AppCompatActivity {
    final static String TAG = "NewTaskFormActivity";
    BroadcastReceiver showTaskReciever = new ShowTaskReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_new_task_form);
        addListenerToTheSwitchDone();
        addListenerToTheSeekbarPercentage();
        subAddListenerToSaveButton();
    }

    public void subReturn(View view){
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.SMP.CUSTOM_INTENT.TasksReady");
        this.registerReceiver(showTaskReciever, intentFilter);
    }

    @Override
    protected void onResume() {
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

    public void addListenerToTheSwitchDone(){
        Switch switchDone = findViewById(R.id.switch1);
        switchDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView textViewPercentage = findViewById(R.id.intPercentage);
                SeekBar seekbarPercentage = findViewById(R.id.seekBar);

                if (isChecked){
                    textViewPercentage.setText("100");
                    seekbarPercentage.setEnabled(false);
                }
                else {
                    int TaskPercentage = seekbarPercentage.getProgress();
                    textViewPercentage.setText("" + TaskPercentage);
                    seekbarPercentage.setEnabled(true);
                }
            }
        });
    }
    public void addListenerToTheSeekbarPercentage(){
        SeekBar seekbar = findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textViewPercentage = findViewById(R.id.intPercentage);

                textViewPercentage.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void subAddListenerToSaveButton(){

        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                                          //Le pasamos los parametros al metodo
                EditText eShortDesc = findViewById(R.id.editText);
                EditText eLongDesc = findViewById(R.id.editText2);
                TextView intPer = findViewById(R.id.intPercentage);
                Switch switchDone = findViewById(R.id.switch1);
                SeekBar seekbar = findViewById(R.id.seekBar);

                Task NewTask = new Task ();
                //                                          //Guardamos la informacion
                NewTask.setStrShortDesc(eShortDesc.getText().toString());
                NewTask.setStrLongDesc(eLongDesc.getText().toString());
                NewTask.setIntPercentage(Integer.parseInt(intPer.getText().toString()));

                //                                          //Imprimimos en la consola
                Log.d(TAG,"Short Desc" + " " + NewTask.getStrShortDesc());
                Log.d(TAG,"Long Desc" + " " +  NewTask.getStrLongDesc());
                Log.d(TAG,"Percentage" + " " +  NewTask.getIntPercentage());

                TaskDB taskDBInstance = TaskDB.getTaskDB(getApplicationContext());
                DBUtil.DBSaveNewTask(taskDBInstance, NewTask);

                TaskDB.destroyInstance();

                //                                          //Le damos un refresh para guardar una
                //                                          //      nueva tarea
                eShortDesc.setText("");
                eLongDesc.setText("");
                intPer.setText("0");
                switchDone.setChecked(false);
                seekbar.setProgress(0);
            }
        });
    }
    private class ShowTaskReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            List<Task> listOfTask = DBUtil.getTasks();
            for (Task task: listOfTask){
                Log.d(TAG, task.getStrShortDesc() + ", " +
                        String.valueOf(task.getIntPercentage()));
            }

        }
    }
}
