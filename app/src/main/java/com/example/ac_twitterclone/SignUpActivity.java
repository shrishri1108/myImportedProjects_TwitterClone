package com.example.ac_twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtusername, edtenteremail, edtpassword;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        setTitle("Sign Up");

        edtusername = findViewById(R.id.edtusername);
        edtenteremail = findViewById(R.id.edtenteremail);
        edtpassword = findViewById(R.id.edtpassword);

        edtpassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    onClick(btnSignUp);

                }

                return false;
            }
        });

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        if(ParseUser.getCurrentUser()!=null){
            //ParseUser.getCurrentUser().logOut();
            transitiontoSocialMediaActivity();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignUp:
                if (edtenteremail.getText().toString().equals("") || edtusername.getText().toString().equals("") || edtpassword.getText().toString().equals("")) {
                    FancyToast.makeText(SignUpActivity.this, "Email,  Username , Password is required", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                }

                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtusername.getText().toString());
                appUser.setEmail(edtenteremail.getText().toString());
                appUser.setPassword(edtpassword.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing Up...");
                progressDialog.show();


                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            FancyToast.makeText(SignUpActivity.this, appUser.getUsername() + "signed up successfully",
                                    Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            transitiontoSocialMediaActivity();


                        } else {
                            FancyToast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();

                        }
                        progressDialog.dismiss();
                    }
                });


                break;

            case R.id.btnLogin:

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

                break;


        }
    }

    public void rootelementisclicked(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void transitiontoSocialMediaActivity(){
        Intent intent = new Intent(SignUpActivity.this, TwitterUsers.class);
        startActivity(intent);
        finish();

    }
}