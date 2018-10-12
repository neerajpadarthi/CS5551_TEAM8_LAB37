package com.example.dell.lab2androidapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private Button Lbtn_login;
    private EditText Ltxt_uname;
    private EditText Ltxt_Pwd;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        Lbtn_login= (Button) findViewById(R.id.Lbtn_login);
        Ltxt_uname= (EditText) findViewById(R.id.Ltxt_uname);
        Ltxt_Pwd= (EditText) findViewById(R.id.Ltxt_Pwd);

        progressDialog=new ProgressDialog(this);
        Lbtn_login.setOnClickListener(this);
    }

    private void Loginuser()
    {
        String userName = Ltxt_uname.getText().toString();
        String password = Ltxt_Pwd.getText().toString();
        TextView errorText = (TextView)findViewById(R.id.Llbl_Error);
        boolean validationFlag = false;
        if(!userName.isEmpty() && !password.isEmpty()) {

            validationFlag=true;
            firebaseAuth.signInWithEmailAndPassword(userName,password).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Intent redirect = new Intent(LoginActivity.this, MainPage.class);
                                startActivity(redirect);
                            }
                            else
                            {
                                progressDialog.setMessage("Not success");
                                progressDialog.show();
                            }

                        }
                    });
        }

        if(!validationFlag)
        {
            errorText.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onClick(View v) {

        if(v == Lbtn_login)
        {
            Loginuser();
        }


    }
}
