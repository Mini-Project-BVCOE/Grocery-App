package m.example.groceryapp.PhoneLoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;

import m.example.groceryapp.EmailLoginRegister.EmailLoginActivity;
import m.example.groceryapp.MainActivity;
import m.example.groceryapp.R;

public class PhoneLoginActivity extends AppCompatActivity {

    private EditText phone,otp;
    private Button btnLogin,otpButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        //////////////hide status bar ends//////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w= getWindow ();
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        ///////////////hide status bar ends/////////////
        mAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {
        phone=(EditText)findViewById(R.id.phone);
        otp=(EditText)findViewById(R.id.otp);
        btnLogin=(Button)findViewById(R.id.button);
        otpButton=(Button)findViewById(R.id.otp_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.getText().toString().equals("")){
                    Toast.makeText(PhoneLoginActivity.this,"Please enter your 6 digit OTP", Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });
    }

    private void Login() {
        String user_phone=phone.getText().toString().trim();

                if(TextUtils.isEmpty(user_phone)){
                    phone.setError("Phone Number is required!!!");
                }
                else{
                    ProgressDialog dialog=new ProgressDialog(this);
                    dialog.setTitle("Logging you in....");
                    dialog.setMessage("Please wait! We are checking your credentials.");
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                }
    }

    public void goToRegister(View view) {

        Intent intent =  new Intent(PhoneLoginActivity.this, PhoneRegisterActivity.class);
        startActivity(intent);
        Animatoo.animateSlideUp(this);
      finish();
    }
    public void backToMainPage(View view) {

        Intent intent=new Intent(PhoneLoginActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(this);
        finish();

    }
}