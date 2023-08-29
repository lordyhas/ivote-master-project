package org.unh.i_vote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import org.unh.i_vote.databinding.ActivityJoinOrgBinding;
import org.unh.i_vote.databinding.ActivityMainBinding;
import org.unh.i_vote.databinding.FragmentOrganizationBinding;

public class JoinOrgActivity extends AppCompatActivity {
    private ActivityJoinOrgBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityJoinOrgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_join_org);
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("Rejoindre une organisation");
        //toolbar.setNavigationIcon(R.navigation.);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

    }
}