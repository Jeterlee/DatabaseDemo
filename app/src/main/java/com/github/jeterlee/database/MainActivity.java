package com.github.jeterlee.database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            DaoHelper<User> userDaoHelper = new DaoHelper<>(MyApplication.getInstances(), User.class);
            userDaoHelper.insert(new User(null, "ddddd", 22));

            List<User> userList = userDaoHelper.queryBuilder();
            for (User user : userList) {
                Log.d("MainActivity", "onCreate: " + user.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
