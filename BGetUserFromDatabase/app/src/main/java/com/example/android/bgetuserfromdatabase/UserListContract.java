package com.example.android.bgetuserfromdatabase;


import com.example.android.bgetuserfromdatabase.Entities.User;

import java.util.ArrayList;

public interface UserListContract {
    interface View{

        void showErrorMessage();

        void displayList(ArrayList<User> list);
    }

    interface Presenter{

        void downloadList();
        ArrayList<User> getMyList();
    }
}
