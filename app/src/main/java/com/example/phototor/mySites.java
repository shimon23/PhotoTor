package com.example.phototor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class mySites extends AppCompatActivity implements View.OnClickListener {

    Dialog webDialog;
    Button webDialogConfirm;
    EditText webDialogEnterText;

    Dialog instaDialog;
    Button instaDialogConfirm;
    EditText instaDialogEnterText;

    Button webChange;
    TextView webSiteText;

    Button instChange;
    TextView instText;

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sites);

        webChange = (Button)findViewById(R.id.websiteBtn);
        webSiteText = (TextView)findViewById(R.id.webTV);

        instChange = (Button)findViewById(R.id.instBtn);
        instText = (TextView)findViewById(R.id.instaTV);

        try {
            getUserDate(new MyCallback() {
                @Override
                public void onCallback(Object value) {

                    webSiteText.setText(value.toString());

                }

            },"webSite");

            getUserDate(new MyCallback() {
                @Override
                public void onCallback(Object value) {

                    instText.setText(value.toString());

                }

            },"instagram");
        }

        catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void createWebSiteDialog(){

        webDialog = new Dialog(this);
        webDialog.setContentView(R.layout.web_site_dialog);
        webDialog.setTitle("אתר אישי");
        webDialog.setCancelable(true);

        webDialogConfirm = (Button)webDialog.findViewById(R.id.confrimBtn);
        webDialogEnterText = (EditText) webDialog.findViewById(R.id.priceET);
        webDialogConfirm.setOnClickListener(this);

        webDialog.show();

    }

    public void createInstagramDialog(){

        instaDialog = new Dialog(this);
        instaDialog.setContentView(R.layout.instegram_dialog);
        instaDialog.setTitle("חשבון אינסטגרם");
        instaDialog.setCancelable(true);

        instaDialogConfirm = (Button)instaDialog.findViewById(R.id.confrimBtn);
        instaDialogEnterText = (EditText) instaDialog.findViewById(R.id.priceET);
        instaDialogConfirm.setOnClickListener(this);

        instaDialog.show();

    }

    @Override
    public void onClick(View view) {

        if(view == webChange){
            createWebSiteDialog();
        }

        if(view == instChange){
            createInstagramDialog();
        }

        if(view == webDialogConfirm){
            webDialog.dismiss();

            updateProfile(new MyCallback() {
                @Override
                public void onCallback(Object value) {

                }

            },"webSite",webDialogEnterText.getText().toString());


            Intent intent = new Intent(this, photographerMenu.class);
            startActivity(intent);
        }

        if(view == instaDialogConfirm){
            instaDialog.dismiss();

            updateProfile(new MyCallback() {
                @Override
                public void onCallback(Object value) {

                }

            },"instagram",instaDialogEnterText.getText().toString());


            Intent intent = new Intent(this, photographerMenu.class);
            startActivity(intent);
        }

    }

    public void updateProfile(final MyCallback call, final String key, final String value){

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                if (dataSnapshot == null) {
                    call.onCallback("null");
                }

                Object fieldsObj = new Object();
                HashMap fldObj;


                try {

                    fldObj = (HashMap) dataSnapshot.getValue(fieldsObj.getClass());
                    HashMap users = (HashMap) fldObj.get("users");
                    HashMap user = (HashMap) users.get(mAuth.getUid());

                    user.put(key,value);

                    dbRef.child("users").child(mAuth.getUid()).updateChildren(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    call.onCallback(user);


                } catch (Exception ex) {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getUserDate(final MyCallback call, final String key){

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<HashMap<String, Object>> list = new ArrayList<>();
                if (dataSnapshot == null) {
                    call.onCallback("null");
                }

                Object fieldsObj = new Object();
                HashMap fldObj;


                try {

                    fldObj = (HashMap) dataSnapshot.getValue(fieldsObj.getClass());
                    HashMap users = (HashMap) fldObj.get("users");
                    HashMap user = (HashMap) users.get(mAuth.getUid());

                    call.onCallback(user.get(key));


                } catch (Exception ex) {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
