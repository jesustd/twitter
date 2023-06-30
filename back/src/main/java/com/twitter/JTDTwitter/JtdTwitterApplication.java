package com.twitter.JTDTwitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.crypto.Data;
import java.util.ArrayList;

@SpringBootApplication
public class JtdTwitterApplication {

	public static void main(String[] args) {

		ArrayList<Tweet> Datas = new ArrayList<>();
		LectorJson lector = new LectorJson();
		Datas = lector.LecturaJSONsimple();

		System.out.println(Datas);

		SpringApplication.run(JtdTwitterApplication.class, args);
	}

}
