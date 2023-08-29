package org.unh.i_vote.ui.organization;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.unh.i_vote.CreateOrganisationActivity;
import org.unh.i_vote.JoinOrgActivity;
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

        final Button createOrganization = binding.createOrg;
        final Button joinOrganization = binding.joinOrg;
        final TextView textView = binding.titleOrg;
        organizationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        //textView.setText("Organisations dont vous êtes membres"); // d'appartenance

        createOrganization.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), CreateOrganisationActivity.class))
        );

        joinOrganization.setOnClickListener(view ->
                startActivity(new Intent(getActivity(), JoinOrgActivity.class))
        );

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
                                Log.d(TAG, doc.getId() + " => " + org);
                                organizations.add(
                                        new ItemModel(org.getName(),
                                                org.getUserIdList().size()+ "Membres"));

                            }
                        } else {
                            Log.e(TAG, "Error getting org documents: ", task.getException());
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
                //Toast.makeText(v.getContext(), "onItemClick = " + position, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                dialog.setTitle("Organisation")
                        .setMessage(
                                "Nom de l'organistion : "+ organizations.get(position).getTitle()+"\n"+
                                        "Author : "+ organizations.get(position).getAuthor() +"\n"
                                        //" :"+ organizations.get(position). +"\n"+
                        ).setPositiveButton("Créer un vote restreint", (dialogInterface, which) -> {
                            //if(organizations.get(position).getMa)
                            Toast.makeText(
                                    v.getContext(),
                                    "Le nombre des membres de l'organisation est " +
                                            "insuffusisant pour un vote restreint ",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }).setNeutralButton("Voir les membres", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setNegativeButton("Fermer", (dialogInterface, i) -> {

                        }).create()
                        .show();
            }

            @Override
            public void onItemLongClick(int position, View v) {
                //Toast.makeText(v.getContext(), "onItemLongClick = " + position, Toast.LENGTH_SHORT).show();
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