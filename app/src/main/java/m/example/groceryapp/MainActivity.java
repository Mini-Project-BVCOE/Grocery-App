package m.example.groceryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.AutoScrollHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

import m.example.groceryapp.Adapters.PlateAdapter;
import m.example.groceryapp.EmailLoginRegister.EmailLoginActivity;
import m.example.groceryapp.EmailLoginRegister.EmailRegisterActivity;
import m.example.groceryapp.Models.PlateModel;
import m.example.groceryapp.PhoneLoginRegister.PhoneLoginActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PlateModel> plateModellist;
    private PlateAdapter plateAdapter;
    private LinearLayout emailContinue,phoneContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////hide status bar ends//////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w= getWindow ();
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        ///////////////hide status bar ends/////////////


        emailContinue= (LinearLayout) findViewById(R.id.linear2);
        phoneContinue= (LinearLayout) findViewById(R.id.linear1);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager= new LinearLayoutManager( this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);

        plateModellist= new ArrayList<>();
        plateModellist.add(new PlateModel(R.drawable.bread1));
        plateModellist.add(new PlateModel(R.drawable.bread1));
        plateModellist.add(new PlateModel(R.drawable.bread1));
        plateModellist.add(new PlateModel(R.drawable.bread1));
        plateModellist.add(new PlateModel(R.drawable.bread1));
        plateModellist.add(new PlateModel(R.drawable.bread1));
        plateModellist.add(new PlateModel(R.drawable.bread1));
        plateModellist.add(new PlateModel(R.drawable.bread1));

        plateAdapter= new PlateAdapter(plateModellist, this);
        recyclerView.setAdapter(plateAdapter);
        plateAdapter.notifyDataSetChanged();

      //////////autoscroll starts//////////
        autoScroll();
        ///////////autoscroll ends///////////

        ///////////start with email//////////////

        emailContinue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, EmailLoginActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(MainActivity.this);
            }
        });
        //////////////////email ends////////////////

        ////////////////start with phone///////////////
        phoneContinue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(MainActivity.this);
            }
        });

        ////////////////end with phone////////////////////



    }

    public void autoScroll()
    {
        final int speedScroll=0;
        final Handler handler= new Handler();
        final Runnable runnable= new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == plateAdapter.getItemCount())

                    count = 0;

                if (count < plateAdapter.getItemCount())
                {
                    recyclerView.smoothScrollToPosition(++count);
                handler.postDelayed(this, speedScroll);


            }
        }
        };
        handler.postDelayed(runnable,speedScroll);

    }

    public void goToHomePage(View view) {
        Intent intent=new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
        finish();
    }
}