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
import com.google.type.Date;
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
import java.util.List;
import java.util.Map;

public class VoteFragment extends Fragment {

    private FragmentVoteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VoteViewModel voteViewModel =
                new ViewModelProvider(this).get(VoteViewModel.class);

        binding = FragmentVoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textGallery;
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        ArrayList<ItemVoteModel> votes = new ArrayList<>(Arrays.asList(itemVoteModels));

        FirebaseRef.publicVoteCollection.get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                votes.add(new ItemVoteModel(Vote.Companion.fromMap(doc.getData())));
                            }
                        } else {
                            Log.d(TAG, "Error getting public votes documents: ", task.getException());
                        }
                    }
                }
        );

        FirebaseRef.userCollection.document("__").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                assert user != null;
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
        });

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






        RecyclerView recyclerView = binding.voteRecyclerView; //findViewById(R.id.voteRecyclerView);
        //recyclerView.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        VoteItemAdapter voteItemAdapter = new VoteItemAdapter(votes);
        recyclerView.setAdapter(voteItemAdapter);

        //votes.add(new LivresModel("Livre F de lecture 7","722 pages",logoDefault));

        voteItemAdapter.setOnItemClickListener(new VoteItemAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(v.getContext(), "onItemClick = " + position, Toast.LENGTH_SHORT).show();
                int index  = v.getId();

                Intent intent = new Intent(getContext(), VoteActivity.class);
                intent.putExtra("listKey",index);
                intent.putExtra("title",votes.get(index).getTitle());
                intent.putExtra("org",votes.get(index).getSubTitle());
                intent.putExtra("myObject", (Serializable) votes.get(index));
                startActivity(intent);

                switch (position){
                    case 1:
                        Intent intent1 = new Intent(getContext(), VoteActivity.class);
                        intent1.putExtra("listKey",2);
                        intent1.putExtra("title","Mon livres");
                        startActivity(intent1);
                        break;

                    case 2:
                        Intent intent2 = new Intent(getContext(), VoteActivity.class);
                        intent2.putExtra("listKey",3);
                        intent2.putExtra("title","Mon livres");
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getContext(), VoteActivity.class);
                        intent3.putExtra("listKey",4);
                        intent3.putExtra("title","Mon livres");
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(getContext(),VoteActivity.class);
                        intent4.putExtra("listKey",5);
                        intent4.putExtra("title","Mon livres");
                        startActivity(intent4);
                        break;

                    default:
                        break;

                }
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Toast.makeText(v.getContext(), "onItemLongClick = " + position, Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "onItemLongClick pos = " + position);
            }
        });





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    ItemVoteModel[] itemVoteModels = new ItemVoteModel[]{
        new ItemVoteModel(new Vote("1","","",false,false,"Vote des délegués","Public",new ArrayList<Choice>(), Date.getDefaultInstance())),
        new ItemVoteModel(new Vote("2","","",false,false,"Theme : legende of classic","Public",new ArrayList<Choice>(), Date.getDefaultInstance())),
        new ItemVoteModel(new Vote("2","","",false,false,"Vote représentant club info","Adminstn UNH",new ArrayList<Choice>(), Date.getDefaultInstance())),
        new ItemVoteModel(new Vote("2","","",false,false,"Date ouverture bibliothèques","Public",new ArrayList<Choice>(), Date.getDefaultInstance())),
    };
}

