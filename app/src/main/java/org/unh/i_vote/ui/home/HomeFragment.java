package org.unh.i_vote.ui.home;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.unh.i_vote.CreateVoteActivity;
import org.unh.i_vote.MainActivity;
import org.unh.i_vote.databinding.FragmentHomeBinding;
import org.w3c.dom.Text;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final Button createVoteButton = binding.createVoteButton;
        final TextView userName = binding.homeUserName;
        final TextView userEmail = binding.homeUserEmail;

        Intent dataIntent = requireActivity().getIntent();
        String email = dataIntent.getStringExtra("email");
        String name = dataIntent.getStringExtra("name");

        userName.setText(name);
        userEmail.setText(email);

        createVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateVoteActivity.class);

                intent.putExtra("email", email);
                intent.putExtra("name", name);
               startActivity(intent);
            }
        });
        //final TextView textView = binding.textHome;
       // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}