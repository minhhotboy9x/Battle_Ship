package sample;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.model.data.Data;
import sample.model.data.DataControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HighScore extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private TableView<Data> table = new TableView();

    private ObservableList<Data> danhSachKetQua;


    @Override
    public void start(Stage stage)throws IOException {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add("sample/css/style.css");

        // tao label
//        final Label label = new Label("High Score");
//        label.setId("highScoreLabel");


        // Nhan du lieu vao bang
        var dataFileName = "DATA.txt";

        ArrayList<Data> datas = new ArrayList<>();
        DataControl dataControl = new DataControl();

        datas = dataControl.readReaderFromFile(dataFileName);
        // doc du lieu vao observableArray
        danhSachKetQua= FXCollections.observableList(datas);

        // tao bang table
        table.setEditable(true);
        table.setId("table");
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(250);
        nameCol.setMaxWidth(250);
        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setMinWidth(120);
        scoreCol.setMaxWidth(120);
        TableColumn rankCol = new TableColumn("Ranking");
        rankCol.setMinWidth(120);
        rankCol.setMaxWidth(120);
        // nhan gia tri cho cac cot trong table
        nameCol.setCellValueFactory( new PropertyValueFactory<Data, String>("userName"));
        scoreCol.setCellValueFactory( new PropertyValueFactory<Data, Integer>("highScore"));
        rankCol.setCellValueFactory( new PropertyValueFactory<Data, Integer>("stt"));

        table.setItems(danhSachKetQua);
        table.getColumns().addAll(rankCol , nameCol, scoreCol);

        table.setLayoutX(400);
        table.setLayoutY(200);
        table.setMinWidth(490);
        table.setMaxHeight(500);

        table.setFixedCellSize(25);
       // table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(20));
        //----------------


        //set return button
        Image returnImage = new Image("resource/image/button/return1.png");
        ImageView returnImageView = new ImageView(returnImage);
        returnImageView.setFitWidth(100);
        returnImageView.setFitHeight(100);
        Button returnButton = new Button("",returnImageView);

       // Button returnButton = new Button();
        returnButton.setId("returnButton");
        returnButton.setLayoutX(310);
        returnButton.setLayoutY(170);
        returnButton.setBackground(null);
        returnButton.setOnAction(e->{
            //change scene
            Intro introScene = new Intro();
            try {

                introScene.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        // set button reset diem
        Image resetImage = new Image("resource/image/button/reset1.png");
        ImageView resetImageView = new ImageView(resetImage);
        resetImageView.setFitWidth(50);
        resetImageView.setFitHeight(50);

        Button resetButton = new Button("", resetImageView);
        resetButton.setId("resetButton");
        resetButton.setLayoutX(335);
        resetButton.setLayoutY(250);
        resetButton.setBackground(null);
        resetButton.setOnAction(e->{
            try {
                dataControl.resetFile(dataFileName);
                // thuc hien xoa cac noi dung cua bang
                for ( int i = 0; i<table.getItems().size(); i++) {
                    table.getItems().clear();
                }


            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });




        root.getChildren().addAll( table, returnButton, resetButton);

        stage.setResizable(false);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();

    }

}
