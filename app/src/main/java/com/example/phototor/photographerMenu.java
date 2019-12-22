package com.example.phototor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class photographerMenu extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener ,View.OnClickListener {

    Button logOut;
    private FirebaseAuth mAuth;
    Button Area;
    TextView tvArea;
    String[] listArea;
    boolean[]checkArea;
    ArrayList<Integer> mSlecterdArea =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Area =(Button) findViewById(R.id.btnArea);
        tvArea = (TextView)findViewById(R.id.tvAreaSelected);

        listArea = getResources().getStringArray(R.array.Area);
        checkArea = new boolean[listArea.length];
        Area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbulder = new AlertDialog.Builder(photographerMenu.this);
                mbulder.setTitle(getString(R.string.dialog_title));
                mbulder.setMultiChoiceItems(listArea, checkArea, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b){
                            if(! mSlecterdArea.contains(i)){
                                mSlecterdArea.add(i);
                            }else {
                                mSlecterdArea.remove(i);
                            }
                        }
                    }
                });

                mbulder.setCancelable(false);
                mbulder.setPositiveButton(getString(R.string.ok_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String area ="";
                        for(int i=0; i<mSlecterdArea.size();i++) {
                            area = area + listArea[mSlecterdArea.get(i)];
                            if(i != mSlecterdArea.size()-1){
                                area =area +",";
                            }

                        }
                        tvArea.setText(area);
                    }
                });
                mbulder.setNegativeButton(getString(R.string.dismiss_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mbulder.setNeutralButton(getString(R.string.clear_all_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for(int i=0;i<checkArea.length;i++){
                            checkArea[i] =false;
                            mSlecterdArea.clear();
                            tvArea.setText("");
                        }
                    }
                });
                AlertDialog mDialog =mbulder.create();
                mDialog.show();

            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_menu);
        Spinner spinner = findViewById(R.id.Area);
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.Area ,R.layout.activity_photographer_menu );
        adapter.setDropDownViewResource((R.layout.support_simple_spinner_dropdown_item ));
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    public void onClick(View view) {

        if(view==logOut){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext() , text,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
