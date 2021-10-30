package android.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class regActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        ProgressDialog loader = new ProgressDialog(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Button regBtn = findViewById(R.id.RegistrationButton);
        EditText regPass = findViewById(R.id.RegistrationPassword);
        EditText regEmail = findViewById(R.id.RegistrationEmail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.RegistrationToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration Page");


        TextView regQn = findViewById(R.id.RegPageQuestion);
        regQn.setOnClickListener(view -> {
            Intent intent = new Intent(regActivity.this, loginActivity.class);
            startActivity(intent);
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString().trim();
                String password = regPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    regEmail.setError("Email is required");
                }
                if(TextUtils.isEmpty(password)){
                    regPass.setError("Password is required");
                }
                else{
                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent intent = new Intent(regActivity.this, homeActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }
                            else{
                                String error = task.getException().toString();
                                Toast.makeText(regActivity.this, "Login failed"+error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }

                        }
                    });
                }

            }
        });
    }
}