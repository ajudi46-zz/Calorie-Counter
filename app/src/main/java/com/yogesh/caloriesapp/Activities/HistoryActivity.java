package com.yogesh.caloriesapp.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yogesh.caloriesapp.HistoryCalorieAdapter;
import com.yogesh.caloriesapp.Model.CaloriesModel;
import com.yogesh.caloriesapp.R;
import com.yogesh.caloriesapp.SessionManager;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    ArrayList<CaloriesModel> modelArrayList;
    private HistoryCalorieAdapter adapter;
    private ListView listView;
    private SessionManager sessionManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        sessionManager = new SessionManager(getApplicationContext());


        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("UserCalories").child(sessionManager.getSharedPrefItem(SessionManager.KEY_ID));
        listView = (ListView) findViewById(R.id.history_listView);
        modelArrayList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot " + dataSnapshot.getValue());

                modelArrayList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    System.out.println(" postSnapshot " + postSnapshot.getValue());
                    //getting item
                    CaloriesModel artist = postSnapshot.getValue(CaloriesModel.class);
//                    //adding item to the list
                    modelArrayList.add(artist);
                }
                adapter = new HistoryCalorieAdapter(HistoryActivity.this, modelArrayList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
