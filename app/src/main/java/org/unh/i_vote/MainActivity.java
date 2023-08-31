package org.unh.i_vote;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.unh.i_vote.data.database.FirebaseRef;
import org.unh.i_vote.data.database.model.User;
import org.unh.i_vote.data.database.model.Vote;
import org.unh.i_vote.data.model.ItemVoteModel;
import org.unh.i_vote.databinding.ActivityMainBinding;
import org.unh.i_vote.ui.login.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/*
*
* @author lordyhas (Hassan K.)
*
*/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavigationView mNavigationView;
    private User user;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRef.publicVoteCollection.get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Vote vote = Vote.Companion.fromMap(doc.getData());
                            Log.d(TAG, "Votes found : "+doc.getData());

                            Date currentDate = new Date();
                            Date startDate = vote.getEndDate();
                            long diffInMillies = Math.abs(startDate.getTime() - currentDate.getTime());
                            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                            System.out.println("diffInDays = "+diffInDays); // prints 8

                            /*if(diffInDays >= 2){
                                FirebaseRef.publicVoteCollection.document(vote.getId()).delete();
                            }*/

                        }
                    } else {
                        Log.d(TAG, "=== Error getting votes documents: ", task.getException());
                    }
                }
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");

        // Inside your onCreate function

        //TextView profileName = findViewById(R.id.profileUsername);
        //profileName.setText(name);
        //TextView profileEmail = findViewById(R.id.profileEmail);
        //profileEmail.setText(email);

        FirebaseRef.userCollection.document(email).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot doc) {
                        user = User.Companion.fromMap(Objects.requireNonNull(doc.getData()));
                    }

                });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(this);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerView = navigationView.getHeaderView(0);

        TextView profileName = (TextView) headerView.findViewById(R.id.profileUsername);
        TextView profileEmail = (TextView) headerView.findViewById(R.id.profileEmail);
        profileName.setText(name);
        profileEmail.setText(email);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == binding.appBarMain.fab.getId()){
            Intent intent = new Intent(MainActivity.this, CreateVoteActivity.class);
            intent.putExtra("email", user.getEmail());
            intent.putExtra("name", user.getName());
            startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
        }

    }
}