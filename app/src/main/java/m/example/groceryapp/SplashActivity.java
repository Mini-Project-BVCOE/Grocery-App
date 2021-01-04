package m.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth=FirebaseAuth.getInstance();
      ///////////STATUS BAR HIDE STARTS////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w= getWindow ();
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
       ///////////STATUS BAR HIDE ENDS//////////////

        ////////////ADD HANDLER STARTS/////////////////

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                   // finish();
                }
            },3000);
        }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
//        if(currentUser!=null){
//            Handler handler=new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    //finish();
//                }
//            },3000);
//        }else{
//            Handler handler=new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    // finish();
//                }
//            },3000);
 //       }

//    }
}
