package com.twitter.JTDTwitter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TweetsControlador {

    public ArrayList<Tweet> AuxiTweet = new ArrayList<>();

    @PostMapping(path = "/Tweets")
    public ResponseEntity<ArrayList<Tweet>> nuevoTweet() {

        LectorJson Leer = new LectorJson();
        this.AuxiTweet = Leer.LecturaJSONsimple();

        return new ResponseEntity<ArrayList<Tweet>>(this.AuxiTweet, HttpStatus.CREATED);
    }

    @PostMapping(path = "/Tweets/Tweet",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<Tweet>> nuevoTweetUnico(@RequestBody Tweet nuevoTweet) {

        this.AuxiTweet.add(nuevoTweet);

        return new ResponseEntity<ArrayList<Tweet>>(this.AuxiTweet, HttpStatus.CREATED);
    }

    @GetMapping("/Tweets")
    public ArrayList<Tweet> GetTweet(){return this.AuxiTweet;}

    @GetMapping("/Tweets/{id}")
    public  ResponseEntity<Tweet> GetTweet(@PathVariable int id){

        Tweet auxDatos = new Tweet();

        for (int i = 0; i < AuxiTweet.size(); i++) {
            if (AuxiTweet.get(i).getId() == id) {
                auxDatos = AuxiTweet.get(i);
                return new ResponseEntity<Tweet>(auxDatos, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/Tweets/{id}")
    public ResponseEntity<ArrayList<Tweet>> PutTweet(@RequestBody Tweet nuevoTweet, @PathVariable int id) {
        for (int i = 0; i < AuxiTweet.size(); i++) {
            if (AuxiTweet.get(i).getId() == id) {

                AuxiTweet.set(i, nuevoTweet);

                return new ResponseEntity<ArrayList<Tweet>>(this.AuxiTweet, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/Tweets/{id}")
    public ResponseEntity<ArrayList<Tweet>> DeleteTweet(@PathVariable int id){
        for (int i = 0; i < AuxiTweet.size(); i++){
            if(AuxiTweet.get(i).getId() == id){
                AuxiTweet.remove(i);
                return new ResponseEntity<ArrayList<Tweet>>(this.AuxiTweet, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
