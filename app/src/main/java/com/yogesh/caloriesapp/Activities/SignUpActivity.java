package com.yogesh.caloriesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yogesh.caloriesapp.Model.Users;
import com.yogesh.caloriesapp.R;
import com.yogesh.caloriesapp.SessionManager;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email, password, userName, weight, height;
    private Button signUpButton;
    private EditText sign_up_name;
    private EditText sign_up_email;
    private EditText sign_up_password;
    private EditText sign_up_height;
    private EditText sign_up_weight;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        initViews();
    }

    private void initViews() {
        sign_up_name = (EditText) findViewById(R.id.sign_up_name);
        sign_up_email = (EditText) findViewById(R.id.sign_up_email);
        sign_up_password = (EditText) findViewById(R.id.sign_up_password);
        sign_up_weight = (EditText) findViewById(R.id.sign_up_weight);
        sign_up_height = (EditText) findViewById(R.id.sign_up_height);
//        TextView signUpTv = (TextView) findViewById(R.id.signUpTextView);
//        signUpTv.setOnClickListener(this);
        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_button:
                final String email = sign_up_email.getText().toString();
                final String password = sign_up_password.getText().toString();
                final String name = sign_up_name.getText().toString();
                final String weight = sign_up_weight.getText().toString();
                final String height = sign_up_height.getText().toString();


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println( " task " + task);
                        System.out.println( " task suces " + task.isSuccessful());
                        System.out.println( " task comp" + task.isComplete());
                        if (task.isSuccessful()) {
                            String key_id = databaseReference.push().getKey();
                            Users ufo = new Users(key_id,email,password,name,weight,height);
                            databaseReference.child(key_id).setValue(ufo);


                            SessionManager sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.createLoginSession(key_id,email, password);

                            Toast.makeText(SignUpActivity.this, "Registered Successfully.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, email + " \n " + password, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                break;
        }
    }
}
