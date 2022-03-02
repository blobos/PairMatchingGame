package com.example.pairmatchinggame;


import javafx.animation.Timeline;
import javafx.scene.control.Button;


public class PairChecker {
    private Button button;
    private int matchedCount;
    private int totalpairs;
    private CountdownTimer timer;

    public PairChecker(int col, int row, CountdownTimer timer) {
        this.button = null;
        this.matchedCount = 0;
        this.totalpairs = col * row;
        this.timer = timer;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    public boolean check(Button button) {
        if (this.button != button && this.button.getText().equals(button.getText())) {
            return true;
        }
        return false;
    }

    public void disableButton(Button button) {
        this.button.setDisable(true);
        button.setDisable(true);
        this.button = null;
    }

    public void disableMatched(Button button) {
        this.button.setText(" ");
        button.setText(" ");
        this.button = null;
    }

    public boolean matchComplete() {
        matchedCount += 2;
        if (totalpairs % 2 == 0 && matchedCount == totalpairs || totalpairs % 2 != 0 && matchedCount == totalpairs - 1) {
            if (timer.isAlive() == true)   {
            timer.timerReset();
            timer.timerStop();
                return true;}
            return true;
            }

        return false;
        }
    }
