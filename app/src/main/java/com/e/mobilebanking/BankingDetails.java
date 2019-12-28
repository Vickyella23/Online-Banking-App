package com.e.mobilebanking;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class BankingDetails extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etReset;
    Button btnReg;
    private TextView tvLoad;
    private View login_form;

    public static final String APPLICATION_ID = "1B318336-3978-19F1-FF92-C04EC1B11C00";
    public static final String API_KEY = "6FA245E9-13D0-4961-81EF-3DED01603C55";
    public static final String SERVER_URL = "https://api.backendless.com";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking_details);

        Backendless.setUrl(SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
        tvLoad = findViewById(R.id.tvLoad);
        login_form = findViewById(R.id.login_form);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etReset = findViewById(R.id.etReset);
        btnReg = findViewById(R.id.btnReg1);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(etName.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() || etReset.getText().toString().isEmpty()){
                        Toast.makeText(BankingDetails.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    }else{
                        if(etPassword.getText().toString().trim().equals(etReset.getText().toString().trim())){
                            String name = etName.getText().toString().trim();
                            String email = etEmail.getText().toString().trim();
                            String password = etPassword.getText().toString().trim();

                            BackendlessUser in = new BackendlessUser();
                            in.setEmail(email);
                            in.setPassword(password);
                            in.setProperty("name", name);

                            showProgress(true);
                            tvLoad.setText("Busy registering user...please wait...");
                            Backendless.UserService.register(in, new AsyncCallback<BackendlessUser>() {
                                @Override
                                public void handleResponse(BackendlessUser response) {

                                    showProgress(false);
                                    Toast.makeText(BankingDetails.this, "Account Successfully created", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(BankingDetails.this, "Error: "+ fault.getMessage(), Toast.LENGTH_SHORT).show();

                                    showProgress(false);

                                }
                            });



                        }else{
                            Toast.makeText(BankingDetails.this, "Please make sure that your password and re-enter password are the same", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        private  void showProgress(final boolean show){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                login_form.setVisibility(show ? View.GONE : View.VISIBLE);
                login_form.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        login_form.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });

                tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                tvLoad.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            }else{
                login_form.setVisibility(show ? View.VISIBLE : View.GONE);
                tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);

            }



    }
}
