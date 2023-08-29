package org.unh.i_vote.ui.vote;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.type.DateTime;

import org.unh.i_vote.VoteActivity;
import org.unh.i_vote.data.database.FirebaseRef;
import org.unh.i_vote.data.database.model.Choice;
import org.unh.i_vote.data.database.model.Organization;
import org.unh.i_vote.data.database.model.User;
import org.unh.i_vote.data.database.model.Vote;
import org.unh.i_vote.data.model.ItemModel;
import org.unh.i_vote.data.controller.VoteItemAdapter;
import org.unh.i_vote.data.model.ItemVoteModel;
import org.unh.i_vote.databinding.FragmentVoteBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VoteFragment extends Fragment {

    private FragmentVoteBinding binding;

    static ArrayList<ItemVoteModel> votes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VoteViewModel voteViewModel =
                new ViewModelProvider(this).get(VoteViewModel.class);

        binding = FragmentVoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textGallery;
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        votes = new ArrayList<>();

        RecyclerView recyclerView = binding.voteRecyclerView; //findViewById(R.id.voteRecyclerView);
        //recyclerView.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRef.publicVoteCollection.get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            votes.add(new ItemVoteModel(Vote.Companion.fromMap(doc.getData())));
                            Log.d(TAG, "=== Vote found : "+doc.getData(), task.getException());
                        }
                    } else {
                        Log.d(TAG, "=== Error getting public votes documents: ", task.getException());
                    }
                    //votes.addAll(Arrays.asList(itemVoteModels));


                    VoteItemAdapter voteItemAdapter = new VoteItemAdapter(votes);
                    recyclerView.setAdapter(voteItemAdapter);

                    //votes.add(new LivresModel("Livre F de lecture 7","722 pages",logoDefault));

                    voteItemAdapter.setOnItemClickListener(new VoteItemAdapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {

                            Intent intent = new Intent(getContext(), VoteActivity.class);
                            intent.putExtra("index", position);
                            votes.get(position).vote.toMap().forEach((key, value) ->
                                intent.putExtra(key,value.toString())
                            );


                            Intent dataIntent = requireActivity().getIntent();
                            String email = dataIntent.getStringExtra("email");
                            //String orgName = dataIntent.getStringExtra("orgName");
                            String voteId = votes.get(position).vote.getId();

                            intent.putExtra("voteId",voteId);
                            intent.putExtra("email",email);
                            intent.putExtra("orgName",votes.get(position).getOrgName());

                            Toast.makeText(v.getContext(), "vote data is loading... ", Toast.LENGTH_SHORT).show();

                            startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(int position, View v) {
                            Toast.makeText(v.getContext(), "onItemLongClick = " + position, Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, "onItemLongClick pos = " + position);
                        }
                    });
                }
        );

        /*FirebaseRef.userCollection.document("__").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if(user == null) return;
                List<String> orgs = user.getOrgList();
                for (String o : user.getOrgList()) {
                    FirebaseRef.orgCollection.document(o).collection("votes").get()
                            .addOnCompleteListener(
                            new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            //votes.add(new ItemVoteModel(Vote.Companion.fromMap(doc.getData())));
                                            votes.add(new ItemVoteModel(doc.toObject(Vote.class)));
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting org votes documents: ", task.getException());
                                    }
                                }
                            }
                    );
                }
            }
        });*/

        /*FirebaseRef.orgCollection.whereEqualTo("us", true)
                .get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {

                                Organization org = Organization.Companion.fromMap(doc.getData());

                                org.getUserIdList()


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                }
        );*/








        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    ItemVoteModel[] itemVoteModels = new ItemVoteModel[]{
        new ItemVoteModel(new Vote("1","","",false,false,"Vote des délegués","Public",new ArrayList<Choice>(), new Date())),
        new ItemVoteModel(new Vote("2","","",false,false,"Theme : legende of classic","Public",new ArrayList<Choice>(), new Date())),
        new ItemVoteModel(new Vote("2","","",false,false,"Vote représentant club info","Adminstn UNH",new ArrayList<Choice>(), new Date())),
        new ItemVoteModel(new Vote("2","","",false,false,"Date ouverture bibliothèques","Public",new ArrayList<Choice>(), new Date())),
    };
}

