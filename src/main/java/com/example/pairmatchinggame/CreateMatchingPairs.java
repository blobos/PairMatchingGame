package com.example.pairmatchinggame;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Random;


public class CreateMatchingPairs {
    private ArrayList<Character> pairList;
    private int row;
    private int col;

    /**
     * Constructor creates an arraylist, with size = number of squares if even & size > number of squares if odd.
     * array list contains 2 of each letter up to the size of arraylist.
     * col & row not hard coded for multiple size
     */
    public CreateMatchingPairs(int col, int row){
        this.col = col;
        this.row = row;
        int totalPairs = col * row;
        //if odd, add extra pair
        if (totalPairs % 2 != 0){
            totalPairs += 1;
        }
        char letter = 'A';
        this.pairList = new ArrayList();
        for (int i = 0; i < totalPairs/2; i++){
            pairList.add(letter);
            pairList.add(letter);
            letter++;
        }
    }

    /**
     * returns random letter and removes one from arraylist
     */
    public String randomLetter(){
        Random random = new Random();
        int r = random.nextInt(pairList.size());
        char letter = pairList.get(r);
        pairList.remove(r);
        return String.valueOf(letter);
    }

    /**
     * returns columns and rows for creating grid
     */
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
