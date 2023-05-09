package pood.eepilinepood;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;

public class LeShop extends Application {

    private static final String FILE_NAME = "log.txt";
    private Stage popupStage;

    @Override
    public void start(Stage stage) throws Exception {

        HashMap<Integer, Toode> tootedMüügil = loebFailist();
        Ostukorv stolenItems = new Ostukorv();

        // loome asju ja seame stage suuruse
        stage.setMinWidth(778);
        stage.setMinHeight(500);
        Group root = new Group();
        Scene scene = new Scene(root);
        Pane valjumine = new Pane();
        Pane product = new Pane();
        Pane pane = new Pane();

        valjumine.setPrefSize(800, 400);

        product.setVisible(false);
        valjumine.setVisible(false);

        // loome 3 labelit, sest meil on 3 erinevat lehte
        Label shopName1 = new Label("Jussi Pood");
        Label shopName2 = new Label("Jussi Pood");
        Label shopName = new Label("Jussi Pood");

        shopName1.setFont(new Font(55));
        shopName2.setFont(new Font(55));
        shopName.setFont(new Font(55));

        //vbucks, mille nimi on nimekast!
        VBox nimekast = new VBox(shopName1);
        VBox teineKarp = new VBox();
        HBox karp = new HBox();
        VBox input = new VBox();

        Button buttonx = new Button("Sisene poodi!");
        Button button1 = new Button("Välju poest!");

        nimekast.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getWidth() - nimekast.getWidth()) / 2,
                scene.widthProperty(), nimekast.widthProperty()));
        nimekast.setAlignment(Pos.CENTER);

        //Nupp poodi sisenemiseks
        buttonx.setOnAction(event -> {
            pane.setVisible(!pane.isVisible());
            product.setVisible(true);

        });
        //Paneb programmi kinni
        button1.setOnAction(event -> stage.hide());

        teineKarp.getChildren().add(shopName);
        karp.getChildren().add(buttonx);
        karp.getChildren().add(button1);
        //Samamoodi labeli keskel hoidmiseks olenemata akna suurusest
        teineKarp.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getWidth() - teineKarp.getWidth()) / 2,
                scene.widthProperty(), teineKarp.widthProperty()));
        seadistaLayoutBind(scene, null, karp);
        karp.setAlignment(Pos.CENTER);
        teineKarp.setAlignment(Pos.CENTER);

        teineKarp.setLayoutY(100);
        karp.setSpacing(5);



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
        } for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100/4.0);
            esimeneLeht.getRowConstraints().add(row);
            teineLeht.getRowConstraints().add(row);
            kolmasLeht.getRowConstraints().add(row);
        }

        //Seadistame lehtede suurusi
        esimeneLeht.setPadding(new Insets(100, 20, 20, 20));
        kolmasLeht.setPadding(new Insets(100, 20, 20, 20));
        teineLeht.setPadding(new Insets(100, 20, 20, 20));
        esimeneLeht.prefHeightProperty().bind(scene.heightProperty());
        kolmasLeht.prefHeightProperty().bind(scene.heightProperty());
        esimeneLeht.prefWidthProperty().bind(scene.widthProperty());
        teineLeht.prefHeightProperty().bind(scene.heightProperty());
        kolmasLeht.prefWidthProperty().bind(scene.widthProperty());
        teineLeht.prefWidthProperty().bind(scene.widthProperty());
        esimeneLeht.setHgap(20);
        esimeneLeht.setVgap(20);
        kolmasLeht.setHgap(20);
        kolmasLeht.setVgap(20);
        teineLeht.setHgap(20);
        teineLeht.setVgap(20);

        int abi = 1; //aitamaks meid
        for (int i = 0; i < 3; i++) {
            if (i == 0){
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        Toode toode = tootedMüügil.get(abi++);
                        Button button = new Button(toode.toString());
                        button.setStyle("-fx-min-width: 170px; -fx-min-height: 50px;");
                        esimeneLeht.add(button, j, k);
                        button.setOnMouseClicked(e -> {
                            showPopup("Ostsid toote " + toode.nimi);
                            stolenItems.lisaToode(toode);
                        });
                        GridPane.setValignment(button, VPos.CENTER);
                        GridPane.setHalignment(button, HPos.CENTER);
                    }
                }
                Button tagasi1 = new Button("<--");
                Button stop1 = new Button("Lõpeta poodlemine");
                Button edasi1 = new Button("-->");

                makeButtonGreatAgain(tagasi1, stop1, edasi1);
                //3 alumise nupu lisamine millega saab poes ringi liikuda, Tagasi et liikuda eelmisele lehele, Stop et lõpetada poodlemine ja edasi et minna edasi järgmisele lehele
                tagasi1.setOnAction(event -> {
                    esimeneLeht.setVisible(false);
                    kolmasLeht.setVisible(true);

                });
                stop1.setOnMouseClicked(e -> {
                    product.setVisible(false);
                    valjumine.setVisible(true);
                    Text sisesta = new Text("Teie ostukorvi summa on kokku " + ((int)((stolenItems.ostukorviMaksumus() * 100.0)))/100.0
                            + " peesot! \nSisestage oma nimi ja väljuge poest");
                    input.getChildren().add(sisesta);
                });

                edasi1.setOnAction(event -> {
                    esimeneLeht.setVisible(false);
                    teineLeht.setVisible(true);
                });
                esimeneLeht.add(tagasi1, 0, 3);
                esimeneLeht.add(stop1, 1   , 3);
                esimeneLeht.add(edasi1, 2, 3);

            } else if(i == 2){
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        Toode toode = tootedMüügil.get(abi++);
                        Button button = new Button(toode.toString());
                        button.setStyle("-fx-min-width: 170px; -fx-min-height: 50px;");
                        teineLeht.add(button, j, k);
                        button.setOnMouseClicked(e -> {
                            showPopup("Ostsid toote " + toode.nimi);
                            stolenItems.lisaToode(toode);
                        });
                        GridPane.setValignment(button, VPos.CENTER);
                        GridPane.setHalignment(button, HPos.CENTER);
                    }
                }
                Button tagasi2 = new Button("<--");
                Button stop2 = new Button("Lõpeta poodlemine");
                Button edasi2 = new Button("-->");

                makeButtonGreatAgain(tagasi2, stop2, edasi2);

                tagasi2.setOnAction(event -> {
                    teineLeht.setVisible(false);
                    esimeneLeht.setVisible(true);

                });
                stop2.setOnMouseClicked(e -> {
                    product.setVisible(false);
                    valjumine.setVisible(true);
                    Text sisesta = new Text("Teie ostukorvi summa on kokku " + ((int)((stolenItems.ostukorviMaksumus() * 100.0)))/100.0
                            + " peesot! \nSisestage oma nimi ja väljuge poest");
                    input.getChildren().add(sisesta);
                });

                edasi2.setOnAction(event -> {
                    teineLeht.setVisible(false);
                    kolmasLeht.setVisible(true);
                });
                teineLeht.add(tagasi2, 0, 3);
                teineLeht.add(stop2, 1   , 3);
                teineLeht.add(edasi2, 2, 3);


            }else{
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        Toode toode = tootedMüügil.get(abi++);
                        Button button = new Button(toode.toString());
                        button.setStyle("-fx-min-width: 170px; -fx-min-height: 50px;");
                        kolmasLeht.add(button, j, k);
                        button.setOnMouseClicked(e -> {
                            showPopup("Ostsid toote " + toode.nimi);
                            stolenItems.lisaToode(toode);
                        });
                        GridPane.setValignment(button, VPos.CENTER);
                        GridPane.setHalignment(button, HPos.CENTER);
                    }
                }
                Button tagasi3 = new Button("<--");
                Button stop3 = new Button("Lõpeta poodlemine");
                Button edasi3 = new Button("-->");

                makeButtonGreatAgain(tagasi3, stop3, edasi3);

                tagasi3.setOnAction(event -> {
                    kolmasLeht.setVisible(false);
                    teineLeht.setVisible(true);

                });
                stop3.setOnMouseClicked(e -> {
                    product.setVisible(false);
                    valjumine.setVisible(true);
                    Text sisesta = new Text("Teie ostukorvi summa on kokku " + ((int)((stolenItems.ostukorviMaksumus() * 100.0)))/100.0
                            + " peesot! \nSisestage oma nimi ja väljuge poest");
                    input.getChildren().add(sisesta);
                });

                edasi3.setOnAction(event -> {
                    kolmasLeht.setVisible(false);
                    esimeneLeht.setVisible(true);
                });

                kolmasLeht.add(tagasi3, 0, 3);
                kolmasLeht.add(stop3, 1   , 3);
                kolmasLeht.add(edasi3, 2, 3);
            }
        }
        //Tekitab elemendid viimasel lehe toimimiseks
        Button  valju = new Button("Välju poest!");
        VBox valjunimi = new VBox();

        TextField tekstiRida = new TextField();
        tekstiRida.setAlignment(Pos.CENTER);
        input.getChildren().add(tekstiRida);
        input.setAlignment(Pos.CENTER);
        valjunimi.setAlignment(Pos.TOP_CENTER);
        //Kui koodist väljuda siis salvestab see log.txt faili ostja nime,mida ta ostis ja kui palju need tooted maksid
        valju.setOnAction(event -> {
            if (tekstiRida.getText().length() > 2 && stolenItems.toodeteArv() > 0)
                log(tekstiRida.getText() + stolenItems);
            stage.hide();
        });
        seadistaLayoutBind(scene, valju, null);
        //samamoodi hoiab labeli akna keskel olenemata suurusest
        valjunimi.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                        (scene.getWidth() - valjunimi.getWidth()) / 2,
                scene.widthProperty(), valjunimi.widthProperty()));
        valju.setAlignment(Pos.BOTTOM_CENTER);

        //lisame kõik vajalikud elemendid nendega seotud kohtadesse
        valjunimi.getChildren().add(shopName2);
        product.getChildren().add(esimeneLeht);
        valjumine.getChildren().add(valjunimi);
        product.getChildren().add(kolmasLeht);
        product.getChildren().add(teineLeht);
        product.getChildren().add(nimekast);
        valjumine.getChildren().add(valju);
        valjunimi.getChildren().add(input);

        pane.setPadding(new Insets(50, 50, 50, 50));
        pane.getChildren().add(teineKarp);
        pane.getChildren().add(karp);
        root.getChildren().add(valjumine);
        root.getChildren().add(product);
        root.getChildren().add(pane);
        stage.setTitle("Le shop!");
        stage.setScene(scene);
        stage.show();
    }

    //log.txt faili kirjutamiseks meetod mida kasutatakse kui poest väljutakse
    public static void log(String message) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME, true);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Meetod toob esile uue akna kui kasutaja ostab midagi poest mis ütleb mida ta ostis ja kui palju see maksis
    private void showPopup(String message) {
        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setResizable(false);

        Label label = new Label(message);
        Button closeButton = new Button("Sulge");
        closeButton.setOnAction(event -> popupStage.close());

        VBox popupContent = new VBox(label, closeButton);
        popupContent.setSpacing(10);
        popupContent.setPadding(new Insets(10));

        StackPane popupRoot = new StackPane(popupContent);
        popupRoot.setPadding(new Insets(10));

        Scene popupScene = new Scene(popupRoot, 250, 80);

        popupStage.setScene(popupScene);
        popupStage.show();
    }

    //Gridpanel asenevate navigeerimis nuppude positsioneerimine
    private void makeButtonGreatAgain(Button tagasi, Button stop, Button edasi) {
        GridPane.setHalignment(tagasi, HPos.LEFT);
        GridPane.setHalignment(stop, HPos.CENTER);
        GridPane.setHalignment(edasi, HPos.RIGHT);

        GridPane.setValignment(tagasi, VPos.BOTTOM);
        GridPane.setValignment(stop, VPos.BOTTOM);
        GridPane.setValignment(edasi, VPos.BOTTOM);
    }

    //Olenevalt millega on tegu meetod lisab nupule, et need on alati keskel olenevalt akna suurusest
    private void seadistaLayoutBind(Scene scene, Button nupp, HBox karp) {
        if(nupp != null){
            nupp.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                            (scene.getWidth() - nupp.getWidth()) / 2,
                    scene.widthProperty(), nupp.widthProperty()));
            nupp.layoutYProperty().bind(Bindings.createDoubleBinding(() ->
                            (scene.getHeight() - nupp.getHeight()) / 2,
                    scene.heightProperty(), nupp.heightProperty()));
        } else {
            karp.layoutXProperty().bind(Bindings.createDoubleBinding(() ->
                            (scene.getWidth() - karp.getWidth()) / 2,
                    scene.widthProperty(), karp.widthProperty()));
            karp.layoutYProperty().bind(Bindings.createDoubleBinding(() ->
                            (scene.getHeight() - karp.getHeight()) / 2,
                    scene.heightProperty(), karp.heightProperty()));
        }
    }

    public static void main(String[] args) {
        launch();
    }

    static HashMap<Integer, Toode> loebFailist () throws IOException {
        // Loeme failist andmed, määrame juhuslikult osadele allahindlused, teeme neist klassi Toode isendid ja loome neist HashMapi
        java.io.File fail = new java.io.File("tooted.txt");
        HashMap<Integer, Toode> kõikTooted = new HashMap<>();
        try (Scanner sc = new Scanner(fail, StandardCharsets.UTF_8)) {
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
        return Math.random()*100;
    }

}
