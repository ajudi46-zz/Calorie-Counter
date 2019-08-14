package com.yogesh.caloriesapp.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yogesh.caloriesapp.CalorieItemAdapter;
import com.yogesh.caloriesapp.Model.CaloriesModel;
import com.yogesh.caloriesapp.R;
import com.yogesh.caloriesapp.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.yogesh.caloriesapp.CalorieItemAdapter.caloriesModelList;

public class FoodTrackerFragment extends AppCompatActivity implements View.OnClickListener, CalorieItemAdapter.OnValueChanged {

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private ListView itemListView;

    ArrayList<CaloriesModel> modelArrayList;
    private CalorieItemAdapter adapter;
    private SessionManager sessionManager;
    private TextView cal_total;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_food_fragment);

        sessionManager = new SessionManager(getApplicationContext());

        modelArrayList = new ArrayList<>();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Calories");

        Button save_button = (Button) findViewById(R.id.save_button);
        itemListView = (ListView) findViewById(R.id.listView);
        cal_total = (TextView) findViewById(R.id.cal_total);
        save_button.setOnClickListener(this);

        // get reference to 'users' node
//        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        System.out.println(" getKey " + mFirebaseDatabase.getKey());
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

                adapter = new CalorieItemAdapter(FoodTrackerFragment.this, modelArrayList);
                itemListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_button:
                writeUserCalorie(sessionManager.getSharedPrefItem(SessionManager.KEY_ID), "", Long.parseLong(cal_total.getText().toString()));
                break;
        }
    }

    private void writeUserCalorie(String id, String itemName, long val) {

        DatabaseReference userCalDataBase = mFirebaseInstance.getReference("UserCalories");
        long dateInMillisecond = Calendar.getInstance().getTimeInMillis();
        Date date = new Date(dateInMillisecond);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        String dateText = df2.format(date);


        CaloriesModel model = new CaloriesModel(dateText, val);

        userCalDataBase.child(id).child(dateText).setValue(model);
    }


    @Override
    public void onValueChange() {
        long loopValue = 0;
        long loop[] = new long[caloriesModelList.size()];
        for (int i = 0; i < caloriesModelList.size(); i++) {
            System.out.println(" int " + i);
            loop[i] = caloriesModelList.get(i).getFinalCalorieValue();
            loopValue = loopValue + loop[i];
        }
        cal_total.setText(String.valueOf(loopValue));
    }

}
