package com.twitter.JTDTwitter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TweetsControlador {

    public ArrayList<Tweet> AuxiTweet = new ArrayList<>();

    @PostMapping(path = "/Tweet")
    public ResponseEntity<ArrayList<Tweet>> nuevoTweet() {

        LectorJson Leer = new LectorJson();
        ArrayList<Tweet> AuxTweet = new ArrayList<>();
        AuxTweet=Leer.LecturaJSONsimple();
        this.AuxiTweet = AuxTweet;

        return new ResponseEntity<ArrayList<Tweet>>(this.AuxiTweet, HttpStatus.CREATED);
    }

    @GetMapping("/Tweet")
    public ArrayList<Tweet> GetTweet(){return this.AuxiTweet;}

    @GetMapping("/Tweet/{id}")
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

    @PutMapping("/Tweet/{id}")
    public ResponseEntity<ArrayList<Tweet>> PutTweet(@RequestBody Tweet nuevoTweet, @PathVariable int id) {
        for (int i = 0; i < AuxiTweet.size(); i++) {
            if (AuxiTweet.get(i).getId() == id) {

                AuxiTweet.set(i, nuevoTweet);

                return new ResponseEntity<ArrayList<Tweet>>(this.AuxiTweet, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/Tweet/{id}")
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
