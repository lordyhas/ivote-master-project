package org.unh.i_vote;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.unh.i_vote.data.controller.VoteItemAdapter;
import org.unh.i_vote.data.model.ItemModel;
import org.unh.i_vote.databinding.ActivityVoteListBinding;

import java.util.ArrayList;
import java.util.List;

public class VoteListActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityVoteListBinding binding;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private VoteItemAdapter voteItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVoteListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_vote_list);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Clicked", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });

    }


}