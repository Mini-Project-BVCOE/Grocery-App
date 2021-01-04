package m.example.groceryapp.EmailLoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import m.example.groceryapp.HomeActivity;
import m.example.groceryapp.MainActivity;
import m.example.groceryapp.R;

public class EmailRegisterActivity extends AppCompatActivity {

    private EditText name,email,password;
    private Button regBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

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

        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        regBtn=(Button) findViewById(R.id.button);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Registration();
            }
        });
    }

    private void Registration() {

        String user_name=name.getText().toString().trim();
        String user_email=email.getText().toString().trim();
        String user_password=password.getText().toString().trim();

        if(TextUtils.isEmpty(user_name)) {
            name.setError("Name is required!!!");

        }
        else if(TextUtils.isEmpty(user_email)) {
            email.setError("Email is required!!!");

        }
        else if(!user_email.matches(emailPattern)) {
            email.setError("Invalid Email!!!");

        }
        else if(TextUtils.isEmpty(user_password)) {
            password.setError("Password is required!!!");

        }
        else if(user_password.length()<8){
            password.setError("Password should be of min 8 chars!!!");

        }
        else
        {


            ProgressDialog dialog=new ProgressDialog(this);
            dialog.setTitle("Registering....");
            dialog.setMessage("Please wait! We are adding your credentials.");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);


            firebaseAuth.createUserWithEmailAndPassword(user_email,user_password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Map<Object,String> userData=new HashMap<>();
                        userData.put("UserName",user_name);

                        firebaseFirestore.collection("USERS")
                                .add(userData)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    Intent mainIntent=new Intent(EmailRegisterActivity.this, HomeActivity.class);
                                    startActivity(mainIntent);
                                    EmailRegisterActivity.this.finish();
                                    Toast.makeText(EmailRegisterActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                }else{

                                    String error=task.getException().getMessage();
                                    Toast.makeText(EmailRegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{

                        String error=task.getException().getMessage();
                        Toast.makeText(EmailRegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    public void goToLogin(View view) {

        Intent intent= new Intent(EmailRegisterActivity.this, EmailLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();

    }
    public void backToMainPage(View view) {

        Intent intent=new Intent(EmailRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(this);
        finish();
    }
}