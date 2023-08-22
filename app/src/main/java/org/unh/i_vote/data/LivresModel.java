package org.unh.i_vote.data;

public class LivresModel {
    private String titre;
    private String page;
    private int logo;

    public String getTitre() {
        return titre;
    }

    public String getPage() {
        return page;
    }

    public int getLogo() {
        return logo;
    }

    public LivresModel(String titre, String page, int logo) {
        this.titre = titre;
        this.page = page;
        this.logo = logo;

    }
    /** public LivresModel(String titre, String page) {
        this.titre = titre;
        this.page = page;
    }

    /*public String getTitre() {
        return titre;
    }

    public String getPage() {
        return page;
    }*/
}
