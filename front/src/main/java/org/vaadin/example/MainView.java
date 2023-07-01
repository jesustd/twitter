package org.vaadin.example;

import com.nimbusds.jose.shaded.gson.Gson;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {



    //HttpClient client = HttpClient.newBuilder().build();
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest request;;
    HttpResponse response;


    final Gson gson = new Gson();

    ArrayList<Tweet> listaTweets = new ArrayList<>();
    Tweet AuxTweet = new Tweet();

    Grid<Tweet> tweetGrid = new Grid<>(Tweet.class);

    Button buttonAnadirTweet = new Button("Añadir Tweet");

    Dialog dialog =new Dialog();

    private static final String api = "http://localhost:8085";

    public Tweet TweetApi(){

        Tweet TweetJSON;


        String StringTweet = getData("Tweet", null);

        TweetJSON = gson.fromJson(StringTweet, Tweet.class);

        return TweetJSON;
    }

    private void postData(String url1, String url2, Tweet AuxTweet) {
        try {

            String resource;

            if (url2 == null){

                resource = String.format(api + url1);

                request = HttpRequest
                        .newBuilder(new URI(resource))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(""))
                        .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            else{

                resource = String.format(api + url1 + "/" + url2);

                request = HttpRequest
                        .newBuilder(new URI(resource))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(AuxTweet.toString()))
                        .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }


    private String getData(String url1, String url2){
        try {
            String resource;

            if (url2 == null){
                resource = String.format(api + url1);
            }
            else{
                resource = String.format(api + url1 + "/" + url2);
            }

            request= HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type","application/json")
                    .GET()
                    .build();
            response=client.send(request,HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return (String) response.body();
    }

    private void putData(String url1, String url2, int Id, Tweet tweets){
        try {
            String resource = String.format(api + url1 + "/" + url2 + "/" + Id);
            request= HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type","application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(tweets.toString()))
                    .build();
            response=client.send(request,HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void deleteData(String url1, String url2, int Id, Tweet tweets){
        try {
            String resource = String.format(api + url1 + "/" + url2 + "/" + Id);
            request= HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type","application/json")
                    .DELETE()
                    .build();
            response=client.send(request,HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired GreetService service) {

        // Use TextField for standard text input
        TextField textField = new TextField("Your name");
        textField.addThemeName("bordered");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello",
                e -> Notification.show(service.greet(textField.getValue())));

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button has a more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(textField, button);
    }

}
