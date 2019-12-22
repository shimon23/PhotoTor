package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();


    Button login;
    Button register;
    EditText enterMail;
    EditText enterPass;
    private TextView mStatusTextView;
    private static final String TAG = "MainActivity";

    public ProgressDialog progressDialog;
    String type;



    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        login = (Button) findViewById(R.id.loginBtn);
        register = (Button) findViewById(R.id.registerBtn);

        enterMail = (EditText) findViewById(R.id.enterMail);
        enterPass = (EditText) findViewById(R.id.enterPass);


        if (mAuth.getCurrentUser() != null)
        {

            progressDialog = new ProgressDialog(MainActivity.this,
                    R.style.AppTheme);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("מזהה משתמש..");
            progressDialog.show();


            getUserType(new MyCallback() {
                @Override
                public void onCallback(String value) {
                    Log.d("USER", value);
                    startMenu(value);
                }
            });




        }


    }

    private void startMenu(String type){
//        progressDialog.dismiss();
        if(type.equals("client")){
            progressDialog.dismiss();
                startActivity(new Intent(this, clientMenu.class));
                finish();
            }

        else{
            Log.d("photographer",type);

//            progressDialog.dismiss();
            startActivity(new Intent(this, photographerMenu.class));
            progressDialog.dismiss();
                finish();
            }
    }

    private void login(){

        String email = enterMail.getText().toString();
        String password = enterPass.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "התחברת בהצלחה!",
                                        Toast.LENGTH_SHORT).show();

                                progressDialog = new ProgressDialog(MainActivity.this,
                                        R.style.AppTheme);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("מזהה משתמש..");
                                progressDialog.show();

                                getUserType(new MyCallback() {
                                    @Override
                                    public void onCallback(String value) {
                                        Log.d("USER", value);
                                        startMenu(value);
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "ההתחברות נכשלה!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }



    }


    @Override
    public void onClick(View view) {

        if(view==login){
            login();



//            if(mAuth.getCurrentUser() != null){
//
//                Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getEmail(),
//                        Toast.LENGTH_SHORT).show();
//
//
//                startActivity(new Intent(this, photographerMenu.class));
//                finish();
//            }


        }
        if(view==register){
            startActivity(new Intent(this, register.class));
            finish();
        }


    }



    public void getUserType(final MyCallback call){

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String ans = "";

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                if (dataSnapshot == null){
                    call.onCallback("null");
                }

//              This is awesome! You don't have to know the data structure of the database.
                Object fieldsObj = new Object();
                HashMap fldObj;

                for (DataSnapshot shot : dataSnapshot.getChildren()){

                    shot = shot.child(mAuth.getUid());

                    try{

                        fldObj = (HashMap)shot.getValue(fieldsObj.getClass());

                    }catch (Exception ex){

                        // My custom error handler. See 'ErrorHandler' in Gist
//                ErrorHandler.logError(ex);

                        continue;
                    }

                    // Include the primary key of this Firebase data record. Named it 'recKeyID'
                    fldObj.put("recKeyID", shot.getKey());
//                    ans = shot.child("user type").getValue().toString();
                    list.add(fldObj);

                    ans = list.get(0).get("userType").toString();
                    call.onCallback(ans);

                }



//                String value = dataSnapshot.getValue(String.class);
//                call.onCallback(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }






}
