package org.unh.i_vote.ui.organization;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.unh.i_vote.VoteActivity;
import org.unh.i_vote.data.controller.OrgItemAdapter;
import org.unh.i_vote.data.database.FirebaseRef;
import org.unh.i_vote.data.database.model.Organization;
import org.unh.i_vote.data.model.ItemModel;
import org.unh.i_vote.databinding.FragmentOrganizationBinding;

import java.util.ArrayList;
import java.util.List;

public class OrganizationFragment extends Fragment {

    private FragmentOrganizationBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OrganizationViewModel organizationViewModel =
                new ViewModelProvider(this).get(OrganizationViewModel.class);

        binding = FragmentOrganizationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.titleOrg;
        organizationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        List<ItemModel> organizations = new ArrayList<>();

        organizations.add(new ItemModel("Master Ingenieurie Logiciel","4 Membres","Aucune", "Hassan K"));
        organizations.add(new ItemModel("Étudiants UNH","1425 Membres","Aucune", "Hassan K"));
        organizations.add(new ItemModel("Assistants UNH","32 Membres","Aucune", "Hassan K"));
        organizations.add(new ItemModel("Fac Info UNH","402 Membres","Aucune", "Hassan K"));
        organizations.add(new ItemModel("Sécurité","204 membres","Aucune", "Hassan K"));
        organizations.add(new ItemModel("Lecture","200 membres","Aucune", "Hassan K"));

        FirebaseRef.orgCollection.get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                //Organization org = Organization.Companion.fromMap(doc.getData());
                                Organization org = doc.toObject(Organization.class);
                                Log.d(TAG, doc.getId() + " => " + doc.getData());

                                organizations.add(
                                        new ItemModel(org.getName(),
                                                task.getResult().size()+ "Membres"));

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                }
        );






        RecyclerView recyclerView = binding.orgRecyclerView; //findViewById(R.id.voteRecyclerView);
        //recyclerView.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        OrgItemAdapter orgItemAdapter = new OrgItemAdapter(organizations);
        recyclerView.setAdapter(orgItemAdapter);

        //organizations.add(new LivresModel("Livre F de lecture 7","722 pages",logoDefault));

        orgItemAdapter.setOnItemClickListener(new OrgItemAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(v.getContext(), "onItemClick = " + position, Toast.LENGTH_SHORT).show();
                int id  = v.getId();

                switch (position){
                    case 0:
                        Intent intent = new Intent(getContext(), VoteActivity.class);
                        intent.putExtra("listKey",1);
                        intent.putExtra("title","Mon livres");
                        startActivity(intent);
                        break;
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
}