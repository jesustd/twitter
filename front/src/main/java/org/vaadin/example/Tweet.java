package org.vaadin.example;

public class Tweet {

    public int id;
    public String tweet;
    public String usuario;
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


    public String toJson() {
        return "{\"id\":\"" + id + "\",\"tweet\":\"" + tweet + "\",\"usuario\":\"" + usuario + "\",\"fecha\":\"" + fecha + "\"}";
    }
}
