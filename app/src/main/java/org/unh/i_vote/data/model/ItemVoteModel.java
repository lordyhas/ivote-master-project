package org.unh.i_vote.data.model;

import org.unh.i_vote.data.database.model.Vote;

public class ItemVoteModel extends ItemModel {

    private String orgName;
    public Vote vote;

    public ItemVoteModel(Vote vote){
        super(vote.getTitle(), "Public", vote.getSubject(), vote.getAuthorName());
        this.vote = vote;
    }

    public ItemVoteModel(Vote vote, String orgName){
        super(vote.getTitle(), orgName, vote.getSubject(), vote.getAuthorName());
        this.vote = vote;
        this.orgName = orgName;
    }

    public String getSubject(){
        return  this.getAbout();
    }

    public String getOrgName(){
        return this.orgName;
    }


}
