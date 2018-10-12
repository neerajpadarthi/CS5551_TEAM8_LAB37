package com.example.dell.lab2androidapplication;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_login;
    private EditText txt_uname;
    private EditText txt_Pwd;
    private TextView lbl_Header;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        btn_login= (Button) findViewById(R.id.btn_login);
        txt_uname= (EditText) findViewById(R.id.txt_uname);
        txt_Pwd= (EditText) findViewById(R.id.txt_Pwd);
        lbl_Header= (TextView) findViewById(R.id.lbl_Header);

        progressDialog=new ProgressDialog(this);
        btn_login.setOnClickListener(this);
        lbl_Header.setOnClickListener(this);
    }

   private void registeruser()
   {
       String userName = txt_uname.getText().toString();
       String password = txt_Pwd.getText().toString();
       TextView errorText = (TextView)findViewById(R.id.lbl_Error);
       boolean validationFlag = false;
       if(!userName.isEmpty() && !password.isEmpty()) {
           validationFlag = true;
           //progressDialog.setMessage(userName);
           //progressDialog.show();
           //progressDialog.setMessage("Registerd");
           firebaseAuth.createUserWithEmailAndPassword(userName,password).
                   addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                           {
                               Intent redirect = new Intent(MainActivity.this, LoginActivity.class);
                               startActivity(redirect);
                           }

                           else
                           {
                               progressDialog.setMessage("Registration is still in progress");
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

        if(v == btn_login)
        {
            registeruser();
        }

        if(v == lbl_Header)
        {
            Intent redirect = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(redirect);
        }

    }
}
