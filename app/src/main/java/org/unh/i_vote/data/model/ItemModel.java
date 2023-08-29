package org.unh.i_vote.data.model;

public class ItemModel {
    private String titre;
    private String subtitle;

    private String about;
    private String author;
    private Integer logo;

    public String getTitle() {
        return titre;
    }

    public String getSubtitle() {
        return subtitle;
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

    public ItemModel(String titre, String subtitle, int logo) {
        this.titre = titre;
        this.subtitle = subtitle;
        this.logo = logo;

    }

    public ItemModel(String titre, String subtitle) {
        this.titre = titre;
        this.subtitle = subtitle;
        this.logo = null;

    }

    public ItemModel(String titre, String subtitle, String about, String author) {
        this.titre = titre;
        this.subtitle = subtitle;
        this.about = about;
        this.author = author;
        this.logo = null;
    }

}
