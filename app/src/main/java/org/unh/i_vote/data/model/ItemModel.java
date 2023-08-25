package org.unh.i_vote.data.model;

public class ItemModel {
    private String titre;
    private String subTitle;

    private String about;
    private String author;
    private Integer logo;

    public String getTitle() {
        return titre;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Integer getLogo() {
        return logo;
    }

    public String getAbout() {
        return about;
    }

    public String getAuthor() {
        return author;
    }

    public ItemModel(String titre, String subTitle, int logo) {
        this.titre = titre;
        this.subTitle = subTitle;
        this.logo = logo;

    }

    public ItemModel(String titre, String subTitle) {
        this.titre = titre;
        this.subTitle = subTitle;
        this.logo = null;

    }

    public ItemModel(String titre, String subTitle, String about, String author) {
        this.titre = titre;
        this.subTitle = subTitle;
        this.about = about;
        this.author = author;
        this.logo = null;
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
