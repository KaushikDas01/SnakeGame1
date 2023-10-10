package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    private int dots;

    private Image apple;
    private Image dot;
    private Image head;

    private final int all_dots=900;
    private final int dotsize=10;
    private int points=0;
    private final int randomposition=29;

    private  int apple_x;
    private  int apple_y;

    private final  int x[]=new int[all_dots];       //horizontal
    private final  int y[]=new int[all_dots];       //vertical

    private Timer timer;

    private boolean leftdirection=false;
    private boolean rightdirection=true;
    private boolean updirection=false;
    private boolean downdirection=false;


    private boolean ingame=true;


    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500,500));
        setFocusable(true);

        loadImage();
        initgame();

    }
    public void loadImage(){
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("com/company/icons/apple.png"));
        apple=i1.getImage();


        ImageIcon i2= new ImageIcon(ClassLoader.getSystemResource("com/company/icons/dot.png"));
        dot= i2.getImage();



        ImageIcon i3= new ImageIcon(ClassLoader.getSystemResource("com/company/icons/head.png"));
        head= i3.getImage();


    }
    public void initgame(){
        dots=3;

        for (int i=0;i<dots;i++){
            y[i] = 50;
            x[i] = 50 - i*dotsize;
        }
        locateapple();

       timer=new Timer(140,this);
       timer.start();
    }
    public void locateapple(){
        int r=(int)(Math.random()*randomposition);
        apple_x=r*dotsize;

        r=(int)(Math.random()*randomposition);
        apple_y=r*dotsize;

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        draw(g);
    }
    public void draw(Graphics g){
        if(ingame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else {
            gameover(g);
        }
    }
    public void gameover(Graphics g){
        String msg="GAME OVER YOUR SCORE " +points ;
        Font font= new Font("SAN SERIF",Font.BOLD,16);
        FontMetrics metrices=getFontMetrics(font);

        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(msg,((500-metrices.stringWidth(msg))/2),500/2);
    }
    public void move(){
        for (int i=dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftdirection){
            x[0]=x[0]-dotsize;
        }
        if(rightdirection){
            x[0]=x[0]+dotsize;
        }
        if(updirection){
            y[0]=y[0]-dotsize;
        }
        if(downdirection){
            y[0]=y[0]+dotsize;
        }
    }
    public void checkapple(){
        if ((x[0]==apple_x) && (y[0]==apple_y)){
            dots++;
            points=10*(dots-3);
            locateapple();
        }
    }
    public void checkcollision(){
        for (int i=dots;i>0;i--){
            if((i>4)&&(x[0]==x[i]) && y[0]==y[i]){
                ingame=false;
            }
        }
        if(y[0]>=500){
            ingame=false;
        }if(x[0]>=500){
            ingame=false;
        }if(y[0]<0){
            ingame=false;
        }if(x[0]<0){
            ingame=false;
        }
        if(!ingame){
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ingame) {
            move();
            checkapple();
            checkcollision();
        }
        repaint();
    }
    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key= e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && (!rightdirection)){
                leftdirection=true;
                updirection=false;
                downdirection=false;
            }if (key == KeyEvent.VK_RIGHT && (!leftdirection)){
                rightdirection=true;
                updirection=false;
                downdirection=false;
            }if (key == KeyEvent.VK_UP && (!downdirection)){
                updirection=true;
                leftdirection=false;
                rightdirection=false;
            }if (key == KeyEvent.VK_DOWN && (!updirection)){
                downdirection=true;
                rightdirection=false;
                leftdirection=false;
            }
        }
    }
}
