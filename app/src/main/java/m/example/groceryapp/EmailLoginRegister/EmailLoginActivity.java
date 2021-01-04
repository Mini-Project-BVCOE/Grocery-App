package m.example.groceryapp.EmailLoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import m.example.groceryapp.HomeActivity;
import m.example.groceryapp.MainActivity;
import m.example.groceryapp.R;

public class EmailLoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        //////////////hide status bar ends//////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w= getWindow ();
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        ///////////////hide status bar ends/////////////
        init();
    }

    private void init() {

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        btnLogin=(Button)findViewById(R.id.button);

        firebaseAuth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });


    }

    private void Login() {
        String user_email=email.getText().toString().trim();
        String user_password=password.getText().toString().trim();
        
        if(TextUtils.isEmpty(user_email)){
            email.setError("Email is required!!!");
        }
        else if(!user_email.matches(emailPattern)){
            email.setError("Invalid email!!!");

        }
        else if(TextUtils.isEmpty(user_password)){
            password.setError("Password is required!!!");

        }
        else if(user_password.length()<8){
            password.setError("Password should be of min 8 chars!!!");

        }
        else{

            ProgressDialog dialog=new ProgressDialog(this);
            dialog.setTitle("Logging you in....");
            dialog.setMessage("Please wait! We are checking your credentials.");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            firebaseAuth.signInWithEmailAndPassword(user_email,user_password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent mainIntent=new Intent(EmailLoginActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        EmailLoginActivity.this.finish();
                        Toast.makeText(EmailLoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }else{

                        String error=task.getException().getMessage();
                        Toast.makeText(EmailLoginActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }

    public void goToRegister(View view) {


        Intent intent= new  Intent (EmailLoginActivity.this, EmailRegisterActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();
    }

    public void backToMainPage(View view) {

        Intent intent=new Intent(EmailLoginActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(this);
        finish();
    }
}