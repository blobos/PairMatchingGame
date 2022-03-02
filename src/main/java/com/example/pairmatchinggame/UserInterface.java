package com.example.pairmatchinggame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.HashMap;

import java.util.concurrent.atomic.AtomicInteger;

public class UserInterface extends Application {
    private CountdownTimer timer;

    @Override
    public void start(Stage stage) throws IOException {

        //Menu
        GridPane menubar = new GridPane();
        menubar.setAlignment(Pos.CENTER);

        final Menu gridSizeMenu = new Menu("New Game");
        MenuItem menuGrid4x3 = new MenuItem("Easy: 4 x 3");
        MenuItem menuGrid5x4 = new MenuItem("Medium: 5 x 4");
        MenuItem menuGrid7x5 = new MenuItem("Hard: 7 x 5");
        gridSizeMenu.getItems().addAll(menuGrid4x3, menuGrid5x4, menuGrid7x5);


        Menu levelIndicator = new Menu("Level 1");


        MenuBar gridSizeMenuBar = new MenuBar();
        gridSizeMenuBar.getMenus().addAll(gridSizeMenu, levelIndicator);

        Button timeAttackMode = new Button("Time Attack Mode");

        timer = new CountdownTimer(timeAttackMode);

        HBox topBar = new HBox();
        topBar.getChildren().addAll(gridSizeMenuBar, timeAttackMode);



        //Scene
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout);
        stage.setTitle("Pair Matching");


        layout.setTop(topBar);


        //grid and level initiation
        AtomicInteger col = new AtomicInteger(2);
        AtomicInteger row = new AtomicInteger(2);
        AtomicInteger level = new AtomicInteger(1);
        CountdownTimer timer = new CountdownTimer(timeAttackMode);
        //Menu Button Function (Gridsize)
        {
            gridSizeMenu.setOnAction(actionEvent -> gridSizeMenu.onShowingProperty().set(event -> gridSizeMenu.setText("Grid Size")));
            //4x3
            menuGrid4x3.setOnAction(actionEvent -> {
                col.set(4);
                row.set(3);
                level.set(1);
                timeAttackMode.setDisable(false);
                timeAttackMode.setText("Time Attack Mode");
                timer.timerReset();
                newMatchGrid(col.get(), row.get(), layout, stage, level,levelIndicator, timer);

            });
            //5x4
            menuGrid5x4.setOnAction(actionEvent -> {
                col.set(5);
                row.set(4);
                level.set(1);
                timeAttackMode.setDisable(false);
                timeAttackMode.setText("Time Attack Mode");
                timer.timerReset();
                newMatchGrid(col.get(), row.get(), layout, stage, level,levelIndicator, timer);

            });
            //7x5
            menuGrid7x5.setOnAction(actionEvent -> {
                //as per instructions grid is 7x5 however this leaves an unmatchable letter
                col.set(7);
                row.set(5);
                level.set(1);
                timeAttackMode.setDisable(false);
                timeAttackMode.setText("Time Attack Mode");
                timer.timerReset();
                newMatchGrid(col.get(), row.get(), layout, stage, level,levelIndicator, timer);
                timer.timerReset();
            });


            timeAttackMode.setOnAction(actionEvent -> {
                //new game with current dimensions
                newMatchGrid(col.get(), row.get(), layout, stage, level,levelIndicator, timer);
                timer.doTime();
                level.set(1);
            });
        }

        //Match Area  intitation
        newMatchGrid(col.get(), row.get(), layout, stage, level, levelIndicator, timer);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void makeGrid(CreateMatchingPairs createMatchingPairs, GridPane matchArea, PairChecker pairChecker,
                                BorderPane layout, Stage stage, AtomicInteger level, Menu levelIndicator, CountdownTimer timer) {
        HashMap<String, String> LetterID = new HashMap<>();
        //original idea was to use Letter ID pairs to prevent clicking button twice to match itself
        //  while this worked, due to the scope, matching the second would not mark the first as matched (adding both to arraylist)
        // It is now only used to retrieve the letter. There may be more efficient ways, but if it ain't broke, don't fix it.
        for (int row = 0; row < createMatchingPairs.getRow(); row++) {
            for (int col = 0; col < createMatchingPairs.getCol(); col++) {
                //creating blank buttons with Unique ID
                Button button = new Button(" ");
                button.setFont(Font.font("Monospaced", 40));
                //Unique ID corresponding to col and row
                button.setId("" + col + row);
                //ID paired with Random letter
                LetterID.put(button.getId(), createMatchingPairs.randomLetter());
                matchArea.add(button, col, row);


                button.setOnMouseClicked(mouseEvent -> {
                    //first click
                    if (pairChecker.getButton() == null) {
                        button.setText(LetterID.get(button.getId()));
                        pairChecker.setButton(button);

                    } else {
                        //second click
                        button.setText(LetterID.get(button.getId()));
                        //match
                        if (pairChecker.check(button)) {
                            pairChecker.disableButton(button);
                            if(pairChecker.matchComplete()){
                                //next Level screen
                                level.incrementAndGet();
                                nextLevelScreen(createMatchingPairs.getCol(), createMatchingPairs.getRow(), layout, stage, level, levelIndicator, timer);
                            }

                        } else {
                            //not match
                            //pause for 500ms before hiding
                            new Timeline(new KeyFrame(
                                    Duration.millis(300),
                                    ae -> pairChecker.disableMatched(button)))
                                    .play();

                        }
                    }


                });
            }
        }
    }

    public static void newMatchGrid(int col, int row, BorderPane layout, Stage stage, AtomicInteger level, Menu levelIndicator, CountdownTimer timer) {
        GridPane matchArea = new GridPane();
        matchArea.setPadding(new Insets(10, 10, 10, 10));
        layout.setCenter(matchArea);
        CreateMatchingPairs createMatchingPairs = new CreateMatchingPairs(col, row);
        PairChecker pairChecker = new PairChecker(col, row, timer);
        makeGrid(createMatchingPairs, matchArea, pairChecker, layout, stage, level, levelIndicator, timer);
        levelIndicator.setText("Level " + level);
        stage.sizeToScene();
    }

    public static void nextLevelScreen(int col, int row, BorderPane layout, Stage stage, AtomicInteger level, Menu levelIndicator, CountdownTimer timer){
        StackPane nextLevelPane = new StackPane();
//        Label levelLabel = new Label("Level " + level);
        Button continueButton = new Button("Continue to level " + level);
        continueButton.setFont(Font.font("Arial", 20));
        continueButton.setOnAction(actionEvent -> {
            newMatchGrid(col, row, layout, stage, level, levelIndicator, timer);
            if (timer.isAlive()){
            timer.timerReset();
            timer.doTime();}
        });
        nextLevelPane.getChildren().addAll(continueButton);
        nextLevelPane.setAlignment(Pos.CENTER);
        layout.setCenter(nextLevelPane);


        //check current grid size
    }

    public static void main(String[] args) {
        launch(UserInterface.class);
    }
}