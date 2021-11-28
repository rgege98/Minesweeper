package hu.minesweeper.ui;

import hu.minesweeper.data.Difficulty;
import hu.minesweeper.ui.elements.Board;

import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
    private Difficulty difficulty = Difficulty.Easy;
    private int frameWidth = 8;
    private int frameHeight = 8;
    private final int square = 50;
    private final int head = 100;
    private final int windowBorder = 3;
    private final int windowTopBar = 26;
    private final int menuBarHeight = 23;
    private final int spacing = 2;
    private int mineCount = 10;
    private int mx;
    private int my;
    private int[][] neighbours = new int[frameWidth][frameHeight];
    private boolean[][] mines = new boolean[frameWidth][frameHeight];
    private boolean[][] revealed = new boolean[frameWidth][frameHeight];
    private boolean[][] flagged = new boolean[frameWidth][frameHeight];
    private boolean happy = true;
    private Date startDate = new Date();
    private int seconds;
    private boolean victory;
    private boolean defeat;
    private int clicks;

    public GameFrame() {
        this.setTitle("Minesweeper");
        this.setSize(frameWidth *square+2*windowBorder, frameHeight *square+head+windowTopBar+windowBorder+menuBarHeight);
        this.setLocation(200,50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        JMenuBar menubar= new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenu newgame = new JMenu("New Game");
        JMenuItem records =new JMenuItem("Records");
        JMenuItem easy = new JMenuItem("Easy");
        JMenuItem medium= new JMenuItem("Medium");
        JMenuItem hard= new JMenuItem("Hard");

        newgame.add(easy);
        newgame.add(medium);
        newgame.add(hard);
        menu.add(newgame);
        menu.add(records);
        menubar.add(menu);
        this.setJMenuBar(menubar);

        ActionListener mlistener = new MenuListener();
        easy.addActionListener(mlistener);
        medium.addActionListener(mlistener);
        hard.addActionListener(mlistener);
        records.addActionListener(mlistener);

        JPanel board = new Board(this);
        this.setContentPane(board);

        MouseMotionListener move = new MoveListener();
        this.addMouseMotionListener(move);

        MouseListener click = new ClickListener();
        this.addMouseListener(click);

        placeMines(mines);
        countNeighbours(neighbours);


    }

    private class MenuListener implements ActionListener {

        @SuppressWarnings("unused")
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch(ae.getActionCommand()) {
                case "Easy":
                    setGameEasy();
                    break;
                case "Medium":
                    setGameMedium();
                    break;
                case "Hard":
                    setGameHard();
                    break;
                case "Records":
                    RecordFrame rf = new RecordFrame();
                    break;
            }

        }
    }

    private class MoveListener implements MouseMotionListener{
        @Override
        public void mouseDragged(MouseEvent arg0) {}

        @Override
        public void mouseMoved(MouseEvent e) {
            mx=e.getX();
            my=e.getY();
        }
    }

    private class ClickListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                if (inSquareX()!= -1 && inSquareY()!= -1 && !flagged[inSquareX()][inSquareY()] && !defeat && !victory) {
                    revealed[inSquareX()][inSquareY()]=true;
                    clicks++;
                    if(clicks==1) {
                        startDate=new Date();
                    }
                    if(neighbours[inSquareX()][inSquareY()]==0 && !mines[inSquareX()][inSquareY()]) {
                        revealEmptySquares(neighbours, inSquareX(), inSquareY());
                    }
                    checkGameStatus();
                }

                if (inSmiley()) {
                    resetGame();
                }
            }

            if(SwingUtilities.isRightMouseButton(e)){

                if (inSquareX()!= -1 && inSquareY()!= -1 && !revealed[inSquareX()][inSquareY()] && !defeat && !victory) {
                    if(!flagged[inSquareX()][inSquareY()]) {
                        flagged[inSquareX()][inSquareY()]=true;
                    }else if(flagged[inSquareX()][inSquareY()]) {
                        flagged[inSquareX()][inSquareY()]=false;
                    }

                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
    }

    public int inSquareX() {
        return inSquare(true);
    }

    public int inSquareY() {
        return inSquare(false);
    }

    private int inSquare(boolean isX){
        for(int i = 0; i< frameWidth; i++) {
            for(int j = 0; j< frameHeight; j++) {
                if(mx >= 2*spacing+i*square && mx <= i*square+square && my >= spacing+j*square+head+windowTopBar+menuBarHeight && my <= j*square+head+square-spacing+windowTopBar+menuBarHeight ) {
                    return isX ? i : j;
                }
            }
        }
        return -1;
    }

    public boolean inSmiley() {
        int smileyX=(int)((frameWidth *square-70)/2);
        int smileyY=head-75;

        double dif = Math.sqrt(Math.abs(mx-(smileyX+35+3))*Math.abs(mx-(smileyX+35+3))+Math.abs(my-(smileyY+35+windowTopBar+menuBarHeight))*Math.abs(my-(smileyY+35+windowTopBar+menuBarHeight)));
        return dif < 35.0;
    }

    public void setGameEasy() {
        difficulty = Difficulty.Easy;
        neighbours = new int[8][8];
        mines = new boolean[8][8];
        revealed = new boolean[8][8];
        flagged = new boolean[8][8];
        frameWidth =8;
        frameHeight =8;
        mineCount=10;
        this.setSize(frameWidth *square+2*windowBorder, frameHeight *square+head+windowTopBar+windowBorder+menuBarHeight);
        resetGame();
    }

    public void setGameMedium() {
        difficulty = Difficulty.Medium;
        neighbours = new int[16][16];
        mines = new boolean[16][16];
        revealed = new boolean[16][16];
        flagged = new boolean[16][16];
        frameWidth =16;
        frameHeight =16;
        mineCount=40;
        this.setSize(frameWidth *square+2*windowBorder, frameHeight *square+head+windowTopBar+windowBorder+menuBarHeight);
        resetGame();
    }

    public void setGameHard() {
        difficulty = Difficulty.Hard;
        neighbours = new int[30][16];
        mines = new boolean[30][16];
        revealed = new boolean[30][16];
        flagged = new boolean[30][16];
        frameWidth =30;
        frameHeight =16;
        mineCount=99;
        this.setSize(frameWidth *square+2*windowBorder, frameHeight *square+head+windowTopBar+windowBorder+menuBarHeight);
        resetGame();
    }

    public void placeMines(boolean[][] mines) {
        int m=0;
        Random rand = new Random();

        while(m < mineCount) {
            for(int i = 0; i< frameWidth; i++) {
                for(int j = 0; j< frameHeight; j++) {
                    if(m == mineCount) break;
                    if(mines[i][j]==true) continue;
                    if(rand.nextInt(frameWidth * frameHeight)<mineCount) {
                        mines[i][j]=true;
                        m++;
                    }else {
                        mines[i][j]=false;
                    }
                }
            }
        }
    }

    public boolean isNeighbourMine(int x, int y, int nx, int ny) {
        if(mines[nx][ny]==true && x-nx<=1  &&  x-nx>=-1  &&  y-ny<=1  &&  y-ny>=-1  ) {
            return true;
        }
        return false;
    }

    public boolean isNeighbourRevealed(int x, int y, int nx, int ny) {
        if(revealed[nx][ny]==false && x-nx<=1  &&  x-nx>=-1  &&  y-ny<=1  &&  y-ny>=-1  ) {
            return true;
        }
        return false;
    }

    public void countNeighbours(int[][] neighbours) {
        int adjmines;
        for(int i = 0; i< frameWidth; i++) {
            for(int j = 0; j< frameHeight; j++) {
                adjmines=0;
                for(int k = 0; k< frameWidth; k++) {
                    for(int l = 0; l< frameHeight; l++) {
                        if(!(i==k && j==l)) {
                            if(isNeighbourMine(i, j, k, l))
                                adjmines++;
                        }
                    }
                }
                neighbours[i][j]=adjmines;
            }
        }
    }

    public int countFlagged(boolean[][] flagged) {
        int total=0;
        for(int i = 0; i< frameWidth; i++) {
            for(int j = 0; j< frameHeight; j++) {
                if(flagged[i][j]) {
                    total++;
                }
            }
        }
        return total;
    }

    public void revealEmptySquares(int[][] neighbours,int i ,int j) {

        if (neighbours[i][j]==0) {
            for(int k = 0; k< frameWidth; k++) {
                for(int l = 0; l< frameHeight; l++) {
                    if(!(i==k && j==l)) {
                        if(isNeighbourRevealed(i, j, k, l)) {
                            revealed[k][l]=true;
                            flagged[k][l]=false;
                            revealEmptySquares(neighbours, k, l);
                        }

                    }
                }
            }

        }
    }

    public int countRevealed(boolean[][] revealed) {
        int total=0;
        for(int i = 0; i< frameWidth; i++) {
            for(int j = 0; j< frameHeight; j++) {
                if(revealed[i][j]) {
                    total++;
                }
            }
        }
        return total;
    }

    public void resetRevealed(boolean[][] revealed) {
        for(int i = 0; i< frameWidth; i++) {
            for(int j = 0; j< frameHeight; j++) {
                revealed[i][j]=false;
            }
        }
    }

    public void resetFlagged(boolean[][] flagged) {
        for(int i = 0; i< frameWidth; i++) {
            for(int j = 0; j< frameHeight; j++) {
                flagged[i][j]=false;
            }
        }
    }

    public void resetMines(boolean[][] mines) {
        for(int i = 0; i< frameWidth; i++) {
            for(int j = 0; j< frameHeight; j++) {
                mines[i][j]=false;
            }
        }
    }

    @SuppressWarnings("unused")
    public void checkGameStatus() {
        for(int i = 0; i< frameWidth; i++) {
            for(int j = 0; j< frameHeight; j++) {
                if(revealed[i][j] && mines[i][j]) {
                    defeat=true;
                    happy=false;
                    resetFlagged(flagged);
                }
            }
        }

        if(countRevealed(revealed) >= ((frameWidth * frameHeight)-mineCount) && !defeat) {
            victory=true;
            SaveFrame sf = new SaveFrame(seconds, difficulty.name());
        }
    }

    public void resetGame() {
        happy=true;
        victory=false;
        defeat=false;
        clicks=0;
        resetMines(mines);
        resetRevealed(revealed);
        resetFlagged(flagged);
        placeMines(mines);
        countNeighbours(neighbours);
    }

    //Getter / setter
    public int getFrameWidth() {
        return frameWidth;
    }
    public int getFrameHeight() {
        return frameHeight;
    }
    public int getSquare() {
        return square;
    }
    public int getHead() {
        return head;
    }
    public int getWindowTopBar() { return windowTopBar; }
    public int getMenuBarHeight() { return menuBarHeight; }
    public int getSpacing() { return spacing; }
    public int getMineCount() {
        return mineCount;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public int getMx() {
        return mx;
    }
    public int getMy() {
        return my;
    }
    public int[][] getNeighbours() {
        return neighbours;
    }
    public boolean[][] getMines() {
        return mines;
    }
    public boolean[][] getRevealed() {
        return revealed;
    }
    public boolean[][] getFlagged() {
        return flagged;
    }
    public boolean isHappy() {
        return happy;
    }
    public Date getStartDate() {
        return startDate;
    }
    public int getSeconds() {
        return seconds;
    }
    public boolean isVictory() {
        return victory;
    }
    public boolean isDefeat() {
        return defeat;
    }
    public int getClicks() {
        return clicks;
    }
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

}

