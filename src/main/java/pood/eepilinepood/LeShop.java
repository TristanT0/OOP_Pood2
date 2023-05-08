package pood.eepilinepood;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LeShop extends Application {

    double summa = 0;


    @Override
    public void start(Stage stage) throws Exception {

        HashMap<Integer, Toode> tootedMüügil = loebFailist("tooted.txt");

        // loome asju ja seame stage suuruse
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        Group root = new Group();
        Scene scene = new Scene(root);
        Pane pane = new Pane();
        Pane product = new Pane();
        Pane valjumine = new Pane();

        // consume the event to prevent window from closing to protect the customer 🤓
        stage.setOnCloseRequest(Event::consume);

        valjumine.setPrefSize(800, 400);

        product.setVisible(false);
        valjumine.setVisible(false);

        // loome 3 labelit, sest põhjused
        Label shopName = new Label("RiMO");
        Label shopName1 = new Label("Jussi Pood");
        Label shopName2 = new Label("RiMO");

        shopName.setFont(new Font(55));
        shopName1.setFont(new Font(55));
        shopName2.setFont(new Font(55));

        //vbucks, mille nimi on nimekast!
        VBox nimekast = new VBox(shopName1);
        HBox karp = new HBox();
        VBox teineKarp = new VBox();

        Button buttonx = new Button("Sisene poodi!");
        Button button1 = new Button("Välju poest!");

        nimekast.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getWidth() - nimekast.getWidth()) / 2,
                scene.widthProperty(), nimekast.widthProperty()));
        nimekast.setAlignment(Pos.CENTER);


        buttonx.setOnAction(event -> {
            pane.setVisible(!pane.isVisible());
            product.setVisible(true);

        });
        button1.setOnAction(event -> {
            stage.hide();
        });


        teineKarp.getChildren().add(shopName);
        karp.getChildren().add(buttonx);
        karp.getChildren().add(button1);

        karp.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getWidth() - karp.getWidth()) / 2,
                scene.widthProperty(), karp.widthProperty()));
        karp.layoutYProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getHeight() - karp.getHeight()) / 2,
                scene.heightProperty(), karp.heightProperty()));
        teineKarp.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getWidth() - teineKarp.getWidth()) / 2,
                scene.widthProperty(), teineKarp.widthProperty()));

        karp.setAlignment(Pos.CENTER);
        teineKarp.setAlignment(Pos.CENTER);

        teineKarp.setLayoutY(100);
        karp.setSpacing(5);

        Button  valju = new Button("Välju poest!");
        VBox valjunimi = new VBox();
        valjunimi.getChildren().add(shopName2);
        valjunimi.setAlignment(Pos.CENTER);
        valju.setOnAction(event -> {
            stage.hide();
        });
        valju.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getWidth() - valju.getWidth()) / 2,
                scene.widthProperty(), valju.widthProperty()));
        valju.layoutYProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getHeight() - valju.getHeight()) / 2,
                scene.heightProperty(), valju.heightProperty()));
        valjunimi.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getWidth() - valjunimi.getWidth()) / 2,
                scene.widthProperty(), valjunimi.widthProperty()));
        valju.setAlignment(Pos.CENTER);

        GridPane esimeneLeht = new GridPane();
        GridPane teineLeht = new GridPane();
        GridPane kolmasLeht = new GridPane();
        teineLeht.setVisible(false);
        kolmasLeht.setVisible(false);


        for (int i = 0; i < 3; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100/3.0);
            esimeneLeht.getColumnConstraints().add(col);
            teineLeht.getColumnConstraints().add(col);
            kolmasLeht.getColumnConstraints().add(col);
        }
        for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100/4.0);
            esimeneLeht.getRowConstraints().add(row);
            teineLeht.getRowConstraints().add(row);
            kolmasLeht.getRowConstraints().add(row);

        }
        esimeneLeht.prefWidthProperty().bind(scene.widthProperty());
        esimeneLeht.prefHeightProperty().bind(scene.heightProperty());
        teineLeht.prefWidthProperty().bind(scene.widthProperty());
        teineLeht.prefHeightProperty().bind(scene.heightProperty());
        kolmasLeht.prefWidthProperty().bind(scene.widthProperty());
        kolmasLeht.prefHeightProperty().bind(scene.heightProperty());

        esimeneLeht.setHgap(20);
        esimeneLeht.setVgap(20);
        esimeneLeht.setPadding(new Insets(100, 20, 20, 20));

        teineLeht.setHgap(20);
        teineLeht.setVgap(20);
        teineLeht.setPadding(new Insets(100, 20, 20, 20));

        kolmasLeht.setHgap(20);
        kolmasLeht.setVgap(20);
        kolmasLeht.setPadding(new Insets(100, 20, 20, 20));


        int abi = 1; //aitamaks meid
        for (int i = 0; i < 3; i++) {
            if (i == 0){
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        Toode toode = tootedMüügil.get(abi++);
                        Button button = new Button(toode.nimi + "\n" + "Hind on " + toode.hind);
                        esimeneLeht.add(button, j, k);
                        button.setOnMouseClicked(e -> {
                            summa += toode.hind;
                            System.out.println(summa);
                        });
                        GridPane.setValignment(button, VPos.CENTER);
                        GridPane.setHalignment(button, HPos.CENTER);
                    }
                }
                Button tagasi1 = new Button("<--");
                Button stop1 = new Button("Lõpeta poodlemine");
                Button edasi1 = new Button("-->");
                tagasi1.setOnAction(event -> {
                    esimeneLeht.setVisible(false);
                    kolmasLeht.setVisible(true);

                });
                stop1.setOnMouseClicked(e -> {
                    product.setVisible(false);
                    valjumine.setVisible(true);
                });

                edasi1.setOnAction(event -> {
                    esimeneLeht.setVisible(false);
                    teineLeht.setVisible(true);
                });
                esimeneLeht.add(tagasi1, 0, 3);
                esimeneLeht.add(stop1, 1   , 3);
                esimeneLeht.add(edasi1, 2, 3);

                GridPane.setHalignment(tagasi1, HPos.LEFT);
                GridPane.setHalignment(stop1, HPos.CENTER);
                GridPane.setHalignment(edasi1, HPos.RIGHT);

                GridPane.setValignment(tagasi1, VPos.BOTTOM);
                GridPane.setValignment(stop1, VPos.BOTTOM);
                GridPane.setValignment(edasi1, VPos.BOTTOM);

            } else if(i == 2){
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        Toode toode = tootedMüügil.get(abi++);
                        Button button = new Button(toode.nimi + "\n" + "Hind on " + toode.hind);
                        teineLeht.add(button, j, k);
                        button.setOnMouseClicked(e -> {
                            summa += toode.hind;
                            System.out.println(summa);
                        });
                        GridPane.setValignment(button, VPos.CENTER);
                        GridPane.setHalignment(button, HPos.CENTER);
                    }
                }
                Button tagasi2 = new Button("<--");
                Button stop2 = new Button("Lõpeta poodlemine");
                Button edasi2 = new Button("-->");
                tagasi2.setOnAction(event -> {
                    teineLeht.setVisible(false);
                    esimeneLeht.setVisible(true);

                });
                stop2.setOnMouseClicked(e -> {
                    product.setVisible(false);
                    valjumine.setVisible(true);
                });

                edasi2.setOnAction(event -> {
                    teineLeht.setVisible(false);
                    kolmasLeht.setVisible(true);
                });
                teineLeht.add(tagasi2, 0, 3);
                teineLeht.add(stop2, 1   , 3);
                teineLeht.add(edasi2, 2, 3);

                GridPane.setHalignment(tagasi2, HPos.LEFT);
                GridPane.setHalignment(stop2, HPos.CENTER);
                GridPane.setHalignment(edasi2, HPos.RIGHT);

                GridPane.setValignment(tagasi2, VPos.BOTTOM);
                GridPane.setValignment(stop2, VPos.BOTTOM);
                GridPane.setValignment(edasi2, VPos.BOTTOM);

            }else{
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        Toode toode = tootedMüügil.get(abi++);
                        Button button = new Button(toode.nimi + "\n" + "Hind on " + toode.hind);
                        kolmasLeht.add(button, j, k);
                        button.setOnMouseClicked(e -> {
                            summa += toode.hind;
                            System.out.println("Ostisid " + toode.nimi);
                            System.out.println(summa);
                        });
                        GridPane.setValignment(button, VPos.CENTER);
                        GridPane.setHalignment(button, HPos.CENTER);
                    }
                }
                Button tagasi3 = new Button("<--");
                Button stop3 = new Button("Lõpeta poodlemine");
                Button edasi3 = new Button("-->");
                tagasi3.setOnAction(event -> {
                    kolmasLeht.setVisible(false);
                    teineLeht.setVisible(true);

                });
                stop3.setOnMouseClicked(e -> {
                    product.setVisible(false);
                    valjumine.setVisible(true);
                });

                edasi3.setOnAction(event -> {
                    kolmasLeht.setVisible(false);
                    esimeneLeht.setVisible(true);
                });
                kolmasLeht.add(tagasi3, 0, 3);
                kolmasLeht.add(stop3, 1   , 3);
                kolmasLeht.add(edasi3, 2, 3);

                GridPane.setHalignment(tagasi3, HPos.LEFT);
                GridPane.setHalignment(stop3, HPos.CENTER);
                GridPane.setHalignment(edasi3, HPos.RIGHT);

                GridPane.setValignment(tagasi3, VPos.BOTTOM);
                GridPane.setValignment(stop3, VPos.BOTTOM);
                GridPane.setValignment(edasi3, VPos.BOTTOM);
            }
        }
        valjumine.getChildren().add(valjunimi);
        valjumine.getChildren().add(valju);
        product.getChildren().add(nimekast);
        product.getChildren().add(esimeneLeht);
        product.getChildren().add(teineLeht);
        product.getChildren().add(kolmasLeht);

        pane.getChildren().add(teineKarp);
        pane.getChildren().add(karp);
        pane.setPadding(new Insets(50, 50, 50, 50));
        root.getChildren().add(valjumine);
        root.getChildren().add(product);
        root.getChildren().add(pane);
        stage.setTitle("Le shop!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    static HashMap<Integer, Toode> loebFailist (String failiNimi) throws FileNotFoundException {
        // Loeme failist andmed, määrame juhuslikult osadele allahindlused, teeme neist klassi Toode isendid ja loome neist HashMapi
        java.io.File fail = new java.io.File(failiNimi);
        HashMap<Integer, Toode> kõikTooted = new HashMap<>();
        try (Scanner sc = new Scanner(fail, "UTF-8")) {
            int järjekorraNr = 1;
            while (sc.hasNextLine()) {
                String[] rida = sc.nextLine().split("_");
                if (kasOnAllahindlus()) {
                    Toode toode = new Toode(Double.parseDouble(rida[0]), rida[1], allahindluseProtsent());
                    kõikTooted.put(järjekorraNr, toode);
                    järjekorraNr++;
                } else {
                    Toode toode = new Toode(Double.parseDouble(rida[0]), rida[1]);
                    kõikTooted.put(järjekorraNr, toode);
                    järjekorraNr++;
                }
            }
        }
        return kõikTooted;
    }

    static boolean kasOnAllahindlus() {
        // Tootel on 1/3 võimalus, et on parajasti allahindlus
        double arv = Math.random()*100;
        return arv <= 33;
    }
    static double allahindluseProtsent() {
        // Allahindluse protsendi leiame suvaliselt Random abil
        double protsent = Math.random()*100;
        return protsent;
    }

}
