package org.vaadin.example;

import com.googlecode.gentyref.TypeToken;
import com.nimbusds.jose.shaded.gson.Gson;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        postData("Tweet", null, null);

        Type listaDataTweet = new TypeToken<ArrayList<Tweet>>() {
        }.getType();
        listaTweets = gson.fromJson(getData("Tweet", null), listaDataTweet);
        tweetGrid.setItems(TweetApi());
        tweetGrid.setColumns("id", "tweet",
                "usuario", "fecha");


        tweetGrid.getColumnByKey("id")
                .setAutoWidth(true);
        tweetGrid.getColumnByKey("tweet")
                .setHeader("Tweet").setAutoWidth(true);
        tweetGrid.getColumnByKey("usuario")
                .setHeader("Usuario").setAutoWidth(true);
        tweetGrid.getColumnByKey("fecha")
                .setHeader("Fecha").setAutoWidth(true);


        buttonAnadirTweet.addClickListener(
                e -> {
                    VerticalLayout dialogLayout = null;
                    try {
                        dialogLayout = createDialogLayoutCrearTweet();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    dialog.add(dialogLayout);
                    dialog.setCloseOnEsc(false);
                    dialog.setCloseOnOutsideClick(false);
                    dialog.setDraggable(true);
                    dialog.setResizable(true);
                    dialog.open();
                    if (!dialog.isOpened()) {
                        dialog.removeAll();
                        listaTweets.clear();
                    }
                }
        );

        Tab tabGeneral = new Tab("Informes Generales");
        Div pageGeneral = new Div();
        pageGeneral.setWidth(90f, Unit.PERCENTAGE);
        pageGeneral.add(buttonAnadirTweet, tweetGrid);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        pageGeneral.setVisible(true);

        Tab tabMayor60 = new Tab("Informes de personas Mayores de 60 años");
        Div pageMayor60 = new Div();
        pageMayor60.setWidth(90f,Unit.PERCENTAGE);
        pageMayor60.add(tweetGrid);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        pageMayor60.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tabGeneral, pageGeneral);
        tabsToPages.put(tabMayor60, pageMayor60);

    }

    private VerticalLayout createDialogLayoutCrearTweet() throws ParseException {

        H2 headline = new H2("Añadir Tweet");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        Button buttonGuardar = new Button("Guardar");
        Button buttonCancelar = new Button("Cancelar");

        FormLayout nameLayout = new FormLayout();

        TextField tweetField = new TextField();
        tweetField.setLabel("tweet");
        tweetField.setRequired(true);
        tweetField.setRequiredIndicatorVisible(true);
        tweetField.setErrorMessage("El Campo es requerido");

        TextField usuarioField = new TextField();
        usuarioField.setLabel("usuario");
        usuarioField.setRequired(true);
        usuarioField.setRequiredIndicatorVisible(true);
        usuarioField.setErrorMessage("El Campo es requerido");

        TextField fechaField = new TextField();
        fechaField.setLabel("fecha");
        fechaField.setRequired(true);
        fechaField.setRequiredIndicatorVisible(true);
        fechaField.setErrorMessage("El Campo es requerido");

/*
        DatePicker fechaField = new DatePicker();
        fechaField.setLabel("fecha_informe");
        fechaField.setRequired(true);
        fechaField.setRequiredIndicatorVisible(true);
        fechaField.setErrorMessage("El Campo es requerido");
        fechaField.setLocale(Locale.FRANCE);
*/
        nameLayout.add(tweetField, usuarioField, fechaField);

        nameLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("1px", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("700px", 3));


        buttonCancelar.addClickListener(e -> {
            dialog.close();
            dialog.removeAll();
        });
        buttonGuardar.addClickListener(e -> {

/*            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            String fechaS;
            fechaS = String.valueOf(fechaField.getValue());
            Date fecha;
            try {
                fecha = formatter.parse(fechaS);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }*/

            Tweet Aux = new Tweet(0 ,tweetField.getValue(), usuarioField.getValue(), fechaField.getValue());

            postData("Tweet", null, Aux);

            tweetGrid.setItems(TweetApi());
            tweetGrid.setColumns("id", "tweet",
                    "usuario", "fecha");

/*            tweetGrid.addColumn(new LocalDateRenderer<>(MainView::getFechaInformeGeneral, "dd/MM/yyyy"))
                    .setHeader("Fecha").setAutoWidth(true);*/

            tweetGrid.getColumnByKey("id")
                    .setAutoWidth(true);
            tweetGrid.getColumnByKey("tweet")
                    .setHeader("Tweet").setAutoWidth(true);
            tweetGrid.getColumnByKey("usuario")
                    .setHeader("Usuario").setAutoWidth(true);
            tweetGrid.getColumnByKey("fecha")
                    .setHeader("Fecha").setAutoWidth(true);

            dialog.close();
            dialog.removeAll();
        });

        VerticalLayout dialogLayout = new VerticalLayout(headline, nameLayout, buttonGuardar, buttonCancelar);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("min-width", "900px")
                .set("max-width", "100%").set("height", "80%");
        return dialogLayout;
    }

}
