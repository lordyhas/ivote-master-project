package org.unh.i_vote.data.model;

import org.unh.i_vote.data.database.model.Organization;

public class ItemOrgModel extends ItemModel{
    public Organization organization;
    public ItemOrgModel(Organization organization) {
        super(
                organization.getName(),
                organization.getUserIdList().size()+" Membres",
                organization.getAbout(),
                organization.getCreatorName());
        this.organization = organization;
    }

    public int getNumberOfMember(){
        return this.organization.getUserIdList().size();
    }

    public int getNumberOfAdmin(){
        return this.organization.getAdminIdList().size();
    }
}
