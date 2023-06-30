package com.twitter.JTDTwitter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tweet {
    //Creacion de variables
    @JsonProperty
    public int id;
    @JsonProperty
    public String tweet;
    @JsonProperty
    public String usuario;
    @JsonProperty
    public String fecha;

    public Tweet() {
    }

    public Tweet(int id, String tweet, String usuario, String fecha) {
        this.id = id;
        this.tweet = tweet;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", tweet='" + tweet + '\'' +
                ", usuario='" + usuario + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
