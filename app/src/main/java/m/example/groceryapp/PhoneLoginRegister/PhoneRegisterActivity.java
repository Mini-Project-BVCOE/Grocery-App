package m.example.groceryapp.PhoneLoginRegister;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import m.example.groceryapp.EmailLoginRegister.EmailLoginActivity;
import m.example.groceryapp.EmailLoginRegister.EmailRegisterActivity;
import m.example.groceryapp.HomeActivity;
import m.example.groceryapp.MainActivity;
import m.example.groceryapp.R;

public class PhoneRegisterActivity extends AppCompatActivity {

    private EditText phone,otp;
    private Button btnReg,otpButton;

    private CountryCodePicker countryCodePicker;

    //////////////////////////////phone OTP//////////////////
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
/////////////////////////////
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);

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
        otpButton=(Button)findViewById(R.id.otp_button);
        btnReg= (Button) findViewById(R.id.button);
        countryCodePicker = findViewById(R.id.ccp);
        ///////////////////////////progressDialog//////////////////
         dialog=new ProgressDialog(this);
        dialog.setTitle("Registering....");
        dialog.setMessage("Please wait! We are adding your credentials.");
        dialog.setCanceledOnTouchOutside(false);
        //////////////////////////////////////////////////////////

        ///////////////phone otp callback start////////////////////
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
               dialog.dismiss();
                otp.setVisibility(View.GONE);
                phone.setVisibility(View.VISIBLE);
                otpButton.setVisibility(View.GONE);
                btnReg.setVisibility(View.VISIBLE);
                Toast.makeText(PhoneRegisterActivity.this, "Invalid Phone Number"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);

                //save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = forceResendingToken;
                Toast.makeText(PhoneRegisterActivity.this, "OTP has been sent, please check and verify", Toast.LENGTH_SHORT).show();
                otp.setVisibility(View.VISIBLE);
                phone.setVisibility(View.GONE);
                otpButton.setVisibility(View.VISIBLE);
                btnReg.setVisibility(View.GONE);
                dialog.dismiss();
            }
        };


        ///////////////phone otp callback ends/////////////////////
        
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_phone=phone.getText().toString().trim();
                if(TextUtils.isEmpty(user_phone)){
                    phone.setError("Phone Number is required!!!");
                }
                else if(user_phone.length()!=10){
                    phone.setError("Phone number should contain 10 digits");
                }
                else {
                    dialog.show();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+user_phone,60,TimeUnit.SECONDS,PhoneRegisterActivity.this,callbacks);
//                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                              user_phone, //PhoneNumbertoverify
//                            60,         //Timeout duration
//                             TimeUnit.SECONDS,  //unit of timeout
//                            PhoneRegisterActivity.this, //Activity for callback binding
//                            callbacks);//onVerification state changed callbacks
                    //Register();
                }
            }
        });

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(otp.getText().toString().equals("")){
                    Toast.makeText(PhoneRegisterActivity.this,"Please enter your 6 digit OTP", Toast.LENGTH_SHORT).show();
                }
                else if(otp.getText().toString().length()!=6){
                    Toast.makeText(PhoneRegisterActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,otp.getText().toString().trim());
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        String user_phone="+91"+phone.getText().toString().trim();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Map<Object,String> userData=new HashMap<>();
                            userData.put("UserPhone",user_phone);

                            firebaseFirestore.collection("USERS")
                                    .add(userData)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful()){
                                                Intent mainIntent=new Intent(PhoneRegisterActivity.this, HomeActivity.class);
                                                startActivity(mainIntent);
                                                PhoneRegisterActivity.this.finish();
                                                Toast.makeText(PhoneRegisterActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                            }else{

                                                String error=task.getException().getMessage();
                                                Toast.makeText(PhoneRegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(PhoneRegisterActivity.this,"Something went wrong, Please try again!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Register() {
        String user_phone=phone.getText().toString().trim();
        if(TextUtils.isEmpty(user_phone)){
            phone.setError("Phone Number is required!!!");
        }
        else{
            ProgressDialog dialog=new ProgressDialog(this);
            dialog.setTitle("Registering....");
            dialog.setMessage("Please wait! We are adding your credentials.");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToLogin(View view) {

        Intent intent =  new Intent(PhoneRegisterActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSlideUp(this);
        finish();


    }
    public void backToMainPage(View view) {

        Intent intent=new Intent(PhoneRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(this);
        finish();
    }
}