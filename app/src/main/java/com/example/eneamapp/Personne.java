package com.example.eneamapp;

public class Personne {
    private long id;
    private String prenom;

    public long getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Personne(long id, String prenom)
    {
        setId(id);
        setPrenom(prenom);
    }
}
