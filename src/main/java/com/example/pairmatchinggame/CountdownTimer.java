package com.example.pairmatchinggame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.animation.Animation;
import javafx.util.Duration;

public class CountdownTimer {
    private int seconds;
    private Button button;
    private Timeline time;
    private Boolean alive;

    public CountdownTimer(Button button) {
        this.button = button;
        seconds = 3;
        alive = false;


    }


    public void doTime() {
        //Duration duration = Duration.ofSeconds(10000);

        alive = true;
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);

        if (time != null) {//if the time is equal to zero it is finished but if it is not equal to something then it is //doing something
            time.stop();
        }
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            //every one second of the timeline the keyframe will do something (i.e., perform a job and that job //is defined by the event handler)
            @Override
            public void handle(ActionEvent event) {
                seconds--;
                button.setText("Time Left: " + seconds + " seconds");

                if (seconds <= 0) {
                    time.stop();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Click New game to re-enable time attack mode");
                    alert.setTitle("YOU LOSE");
//                    alert.setContentText("Click New game to start New game");
                    alert.show();
                    button.setText("TIME IS UP");
                    button.setDisable(true);
                    alive = false;
                }
            }

        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    public void timerReset() {
        seconds = 3;
    }

    public void timerStop(){
        time.stop();
    }

    public Boolean isAlive(){
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
}