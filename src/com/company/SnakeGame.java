package com.company;

import javax.swing.*;

public class SnakeGame extends JFrame {
    SnakeGame(){
        setTitle("Snake Game by Kaushik");
        add(new Board());
        pack();

        setLocationRelativeTo(null);        /** open the frame from center in every device */
        setResizable(false);
        setVisible(true);
    }



    public static void main(String[] args) {
        new SnakeGame();
    }

}
