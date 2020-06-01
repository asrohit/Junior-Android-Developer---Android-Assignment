package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabbtn;
    RecyclerView recyclerView;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                intent.putExtra("ID", id);
//                intent.putExtra("NAME", name);
//                intent.putExtra("MODEL", model);
//                intent.putExtra("VARENT", varent);
//                intent.putExtra("FUELTYPE", fueltype);
//                intent.putExtra("IMAGE", image);
//                intent.putExtra("ADD_TIMESTAMP", addTimeStamp);
//                intent.putExtra("UPDATE_TIMESTAMP", updateTimeStamp);
                intent.putExtra("edit Mode", false);
                startActivity(intent);
            }
        });


        dataBaseHelper = new DataBaseHelper(this);

        showRecord();

    }

    void initView() {
        fabbtn = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void showRecord() {

//        Log.e("RESULT", " " + dataBaseHelper.getAllInfo(Constant.v_ADD_TIMESTAMP + " DESC").get(0).name);
        if (dataBaseHelper.getAllInfo(Constant.v_ADD_TIMESTAMP + " DESC") != null && !dataBaseHelper.getAllInfo(Constant.v_ADD_TIMESTAMP + " DESC").isEmpty()) {
            Adapter adapter = new Adapter(MainActivity.this, dataBaseHelper.getAllInfo(Constant.v_ADD_TIMESTAMP + " DESC"));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showRecord();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK)
            moveTaskToBack(true);
        return super.onKeyDown(keyCode, event);
    }
}
