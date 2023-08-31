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

import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.unh.i_vote.CreateOrganisationActivity;
import org.unh.i_vote.CreateVoteActivity;
import org.unh.i_vote.JoinOrgActivity;

import org.unh.i_vote.OrgUserListActivity;
import org.unh.i_vote.data.controller.OrgItemAdapter;
import org.unh.i_vote.data.database.FirebaseRef;
import org.unh.i_vote.data.database.model.Organization;
import org.unh.i_vote.data.model.ItemOrgModel;
import org.unh.i_vote.databinding.FragmentOrganizationBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrganizationFragment extends Fragment {

    private FragmentOrganizationBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OrganizationViewModel organizationViewModel =
                new ViewModelProvider(this).get(OrganizationViewModel.class);

        binding = FragmentOrganizationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent dataIntent = requireActivity().getIntent();
        String email = dataIntent.getStringExtra("email");
        String name = dataIntent.getStringExtra("name");

        final Button createOrganization = binding.createOrg;
        final Button joinOrganization = binding.joinOrg;
        final TextView textView = binding.titleOrg;
        organizationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        //textView.setText("Organisations dont vous êtes membres"); // d'appartenance

        createOrganization.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CreateOrganisationActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("name", name);
            startActivity(intent);
        });

        joinOrganization.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), JoinOrgActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("name", name);
            startActivity(intent);
        });


        List<ItemOrgModel> organizations = new ArrayList<>();


        RecyclerView recyclerView = binding.orgRecyclerView; //findViewById(R.id.voteRecyclerView);
        //recyclerView.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);



        FirebaseRef.orgCollection.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            //Organization org = Organization.Companion.fromMap(doc.getData());
                            Organization org = Organization.Companion.fromMap(doc.getData()); //doc.toObject(Organization.class);
                            Log.d(TAG, doc.getId() + " => " + org);
                            organizations.add(new ItemOrgModel(org));
                        }

                        OrgItemAdapter orgItemAdapter = new OrgItemAdapter(organizations);
                        recyclerView.setAdapter(orgItemAdapter);

                        orgItemAdapter.setOnItemClickListener(new OrgItemAdapter.ClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {

                                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                                dialog.setTitle("Organisation").setMessage(

                                        "Nom : "+ organizations.get(position).getTitle()+"\n"+
                                        "--> "+ organizations.get(position).getSubtitle()+"\n"+
                                        "Author : "+ organizations.get(position).getAuthor() +""
                                                //" :"+ organizations.get(position). +"\n"+
                                        ).setPositiveButton("Créer un vote restreint", (dialogInterface, which) -> {
                                            if(organizations.get(position).getNumberOfMember() > 1){
                                                Intent intent = new Intent(getActivity(), CreateVoteActivity.class);
                                                intent.putExtra("email", email);
                                                intent.putExtra("name", name);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(
                                                        v.getContext(),
                                                        "Le nombre des membres de l'organisation est " +
                                                                "insuffusisant pour un vote restreint ",
                                                        Toast.LENGTH_SHORT
                                                ).show();
                                            }
                                        }).setNeutralButton("Voir les membres", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(getActivity(), OrgUserListActivity.class);
                                                intent.putExtra("email", email);
                                                intent.putExtra("name", name);
                                                intent.putExtra("orgId", organizations.get(position).organization.getId());
                                                startActivity(intent);

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
                    } else {
                        Log.e(TAG, "Error getting org documents: ", task.getException());
                    }
                }
        );
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    ItemOrgModel[] sampleOrgs(){
        String userId = "htsheleka@gmail.com";
        String userName = "Hassan K.";

        return new ItemOrgModel[]{
                new ItemOrgModel(new Organization("OG"+ new Date().getTime(),userId,userName,"Master Ingenieurie Logiciel","Aucune",new ArrayList<>(), new ArrayList<>())),
                new ItemOrgModel(new Organization("OG"+ new Date().getTime(),userId,userName,"Étudiants UNH","Aucune",new ArrayList<>(), new ArrayList<>())),
                new ItemOrgModel(new Organization("OG"+ new Date().getTime(),userId,userName,"Assistants UNH","Aucune",new ArrayList<>(), new ArrayList<>())),
                new ItemOrgModel(new Organization("OG"+ new Date().getTime(),userId,userName,"Fac Info UNH","Aucune",new ArrayList<>(), new ArrayList<>())),
                new ItemOrgModel(new Organization("OG"+ new Date().getTime(),userId,userName,"Sécurité","Aucune",new ArrayList<>(), new ArrayList<>())),
        };
    }
}