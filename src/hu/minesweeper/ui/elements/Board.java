package hu.minesweeper.ui.elements;

import hu.minesweeper.ui.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Board extends JPanel {
    private GameFrame gf;
    public Board(GameFrame gf) {
        super();
        this.gf = gf;
    }

    public void paintComponent(Graphics g) {

        paintBoard(g);

        paintSmiley(g, (int)((gf.getFrameWidth()*gf.getSquare()-70)/2), gf.getHead()-75);

        paintTimeCounter(g, (int)(gf.getFrameWidth()*gf.getSquare()-140), gf.getHead()-75);

        paintMineCounter(g, 5, gf.getHead()-75, gf.getMineCount()-gf.countFlagged(gf.getFlagged()));

    }

    public void paintBoard(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, gf.getFrameWidth()*gf.getSquare(), gf.getFrameHeight()*gf.getSquare()+gf.getHead());
        for(int i=0; i<gf.getFrameWidth(); i++) {
            for(int j=0; j<gf.getFrameHeight(); j++) {
                g.setColor(Color.DARK_GRAY);
                if(gf.isDefeat() && gf.getMines()[i][j]) {
                    gf.getRevealed()[i][j]=true;
                }
                if(gf.getRevealed()[i][j]) {
                    if(!gf.getMines()[i][j]) {
                        g.setColor(Color.LIGHT_GRAY);
                    }else {
                        g.setColor(Color.RED);
                    }
                }
                if(gf.getMx()>=2*gf.getSpacing()+i*gf.getSquare() && gf.getMx()<=i*gf.getSquare()+gf.getSquare() && gf.getMy()>=gf.getSpacing()+j*gf.getSquare()+gf.getHead()+gf.getWindowTopBar()+gf.getMenuBarHeight() && gf.getMy() <= j*gf.getSquare()+gf.getHead()+gf.getSquare()-gf.getSpacing()+gf.getWindowTopBar()+gf.getMenuBarHeight() ) {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(gf.getSpacing()+i*gf.getSquare(), gf.getSpacing()+j*gf.getSquare()+gf.getHead(), gf.getSquare()-2*gf.getSpacing(), gf.getSquare()-2*gf.getSpacing());

                if(gf.getRevealed()[i][j]) {
                    if(!gf.getMines()[i][j] && gf.getNeighbours()[i][j]!=0) {
                        switch(gf.getNeighbours()[i][j]) {
                            case 1:
                                g.setColor(Color.blue);
                                break;
                            case 2:
                                g.setColor(Color.GREEN);
                                break;
                            case 3:
                                g.setColor(Color.red);
                                break;
                            case 4:
                                g.setColor(new Color(0,0,128));
                                break;
                            case 5:
                                g.setColor(new Color(178,34,34));
                                break;
                            case 6:
                                g.setColor(new Color(72,209,204));
                                break;
                            case 7:
                                g.setColor(Color.black);
                                break;
                            case 8:
                                g.setColor(Color.darkGray);
                                break;
                            default:
                                g.setColor(Color.black);
                        }
                        g.setFont(new Font("Courier", Font.BOLD, (int)gf.getSquare()/2));
                        g.drawString(Integer.toString(gf.getNeighbours()[i][j]), (int)(i*gf.getSquare()+0.35*gf.getSquare()), (int)(j*gf.getSquare()+gf.getHead()+0.7*gf.getSquare()));
                    }else if(gf.getMines()[i][j]){
                        g.setColor(Color.BLACK);
                        g.fillRect(i*gf.getSquare()+(int)(gf.getSquare()/2.6), j*gf.getSquare()+gf.getHead()+(int)gf.getSquare()/4, (int)(gf.getSquare()/4), (int)(gf.getSquare()/2));
                        g.fillRect(i*gf.getSquare()+(int)gf.getSquare()/4, j*gf.getSquare()+gf.getHead()+(int)(gf.getSquare()/2.6), (int)gf.getSquare()/2, (int)gf.getSquare()/4);
                        g.fillRect(i*gf.getSquare()+(int)(gf.getSquare()/3.2), j*gf.getSquare()+gf.getHead()+(int)(gf.getSquare()/3.2), (int)(gf.getSquare()/2.6), (int)(gf.getSquare()/2.6));
                        g.fillRect(i*gf.getSquare()+(int)(gf.getSquare()/2.1), j*gf.getSquare()+gf.getHead()+(int)(gf.getSquare()/5.3), (int)(gf.getSquare()/20), (int)(gf.getSquare()/1.6));
                        g.fillRect(i*gf.getSquare()+(int)(gf.getSquare()/5.3), j*gf.getSquare()+gf.getHead()+(int)(gf.getSquare()/2.1), (int)(gf.getSquare()/1.6), (int)(gf.getSquare()/20));
                    }
                }
                //flags
                if(gf.getFlagged()[i][j]) {
                    g.setColor(Color.black);
                    g.fillRect(i*gf.getSquare()+(int)(gf.getSquare()/2.16), j*gf.getSquare()+gf.getHead()+(int)(gf.getSquare()/4), (int)(gf.getSquare()/16), (int)(gf.getSquare()/2));
                    g.fillRect(i*gf.getSquare()+(int)(gf.getSquare()/3.2), j*gf.getSquare()+gf.getHead()+(int)(gf.getSquare()/1.45), (int)(gf.getSquare()/2.6), (int)(gf.getSquare()/8));
                    g.fillRect(i*gf.getSquare()+(int)(gf.getSquare()/4.2), j*gf.getSquare()+gf.getHead()+(int)(gf.getSquare()/4.44), (int)(gf.getSquare()/3.48), (int)(gf.getSquare()/4.2));

                    g.setColor(Color.red);
                    g.fillRect(i*gf.getSquare()+(int)(gf.getSquare()/3.81), j*gf.getSquare()+gf.getHead()+(int)(gf.getSquare()/4), (int)(gf.getSquare()/4), (int)(gf.getSquare()/5.33));
                }
            }
        }
    }

    public void paintSmiley(Graphics g, int smileyX, int smileyY) {
        g.setColor(Color.yellow);
        g.fillOval(smileyX, smileyY, 70, 70);
        g.setColor(Color.black);
        g.fillOval(smileyX+15, smileyY+20, 10, 10);
        g.fillOval(smileyX+45, smileyY+20, 10, 10);
        if(gf.isHappy()) {
            g.fillRect(smileyX+20, smileyY+50,30 ,5 );
            g.fillRect(smileyX+18, smileyY+45,3 ,7 );
            g.fillRect(smileyX+49, smileyY+45,3 ,7 );
        }else {
            g.fillRect(smileyX+20, smileyY+50,30 ,5 );
            g.fillRect(smileyX+18, smileyY+53,3 ,7 );
            g.fillRect(smileyX+49, smileyY+53,3 ,7 );

            g.fillRect(smileyX+13, smileyY+20, 15, 12);
            g.fillRect(smileyX+44, smileyY+20, 15, 12);
        }
        if(gf.isVictory()) {
            g.fillRect(smileyX+13, smileyY+20, 15, 12);
            g.fillRect(smileyX+44, smileyY+20, 15, 12);
            g.fillRect(smileyX+3, smileyY+20, 65, 3);
        }
    }

    public void paintMineCounter(Graphics g ,int counterX, int counterY, int flagCount) {
        g.setColor(Color.gray);
        g.fillRect(counterX, counterY, 135, 70);
        g.setColor(Color.white);
        if(flagCount<=-99){
            g.drawString("-99", counterX+18, counterY+65);
        }
        else if(flagCount<=-10) {
            g.drawString("-"+Integer.toString(flagCount*(-1)), counterX+18, counterY+65);
        }else if(flagCount<0) {
            g.drawString("-0"+Integer.toString(flagCount*(-1)), counterX+18, counterY+65);
        }else if(flagCount<10) {
            g.drawString("00"+Integer.toString(flagCount), counterX, counterY+65);
        }else if(flagCount<100){
            g.drawString("0"+Integer.toString(flagCount), counterX, counterY+65);
        }else {
            g.drawString(Integer.toString(flagCount), counterX, counterY+65);
        }
    }

    public void paintTimeCounter(Graphics g, int timeX, int timeY) {
        g.setColor(Color.gray);
        g.fillRect(timeX, timeY, 135, 70);
        if(!gf.isVictory() && !gf.isDefeat()) {
            gf.setSeconds((int)((new Date().getTime()-gf.getStartDate().getTime())/1000));
        }
        if(gf.getSeconds()>999) {
            gf.setSeconds(999);
        }

        g.setColor(Color.white);
        if(gf.isVictory()){
            g.setColor(Color.green);
        }else if(gf.isDefeat()) {
            g.setColor(Color.red);
        }

        g.setFont(new Font("Courier", Font.BOLD, 80));
        if(gf.isDefeat() && gf.getClicks()==1) {
            g.drawString("000", timeX, timeY+65);
        }else if(gf.getClicks()>0) {
            if(gf.getSeconds()<10) {
                g.drawString("00"+Integer.toString(gf.getSeconds()), timeX, timeY+65);
            }else if(gf.getSeconds()<100){
                g.drawString("0"+Integer.toString(gf.getSeconds()), timeX, timeY+65);
            }else {
                g.drawString(Integer.toString(gf.getSeconds()), timeX, timeY+65);
            }

        }else {
            g.drawString("000", timeX, timeY+65);
        }
    }

}
