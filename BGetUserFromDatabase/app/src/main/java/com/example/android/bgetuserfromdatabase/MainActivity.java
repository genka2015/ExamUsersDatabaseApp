package com.example.android.bgetuserfromdatabase;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.bgetuserfromdatabase.Entities.User;
import com.example.android.bgetuserfromdatabase.SingleUser.ViewUserActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UserRecyclerAdapter.ItemClickListener, UserListContract.View{

    RecyclerView recyclerView;
    ArrayList<User> users;
    UserRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUser();
        displayList(users);
    }

    @Override
    protected void onStop() {
        super.onStop();
        users.clear();
    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void displayList(ArrayList<User> list) {
        users = list;
        //Log.d(TAG, "onCreate: My user List " + list.size());
        adapter = new UserRecyclerAdapter(users,this);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
    }


    private User mapUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndexOrThrow(UserContract.UserEntry._ID)));
        user.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME)));
        user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_ADDRESS)));
        user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_EMAIL)));
        user.setImage(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PICTURE)));
        return user;
    }

    // Will need it in App B
    private void loadUser() {
        Cursor cursor = getContentResolver().query(UserContract.UserEntry.CONTENT_URI, null, null, null, null);
        if(cursor != null) {
            while (cursor.moveToNext()){

                User user = mapUser(cursor);
                users.add(user);
                //Toast.makeText(this, user.getName(), Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Cursor is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent myIntent = new Intent(MainActivity.this,ViewUserActivity.class);
        myIntent.putExtra("user",users.get(position));
        startActivity(myIntent);
    }
}
