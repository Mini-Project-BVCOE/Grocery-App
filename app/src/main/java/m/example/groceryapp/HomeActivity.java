package m.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigation=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.orders:
                    Toast.makeText(HomeActivity.this,"Orders",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.offers:
                    Toast.makeText(HomeActivity.this,"Offers and Deals",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.directions:
                    Toast.makeText(HomeActivity.this,"Directions",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.list:
                    Toast.makeText(HomeActivity.this,"Shopping List",Toast.LENGTH_SHORT).show();
                    break;
            }

            return true;
        }
    };
}