package hu.minesweeper;


import hu.minesweeper.ui.GameFrame;

public class Minesweeper implements Runnable {
    private GameFrame gf = new GameFrame();

    public static void main(String[] args) {
        new Thread(new Minesweeper()).start();
    }

    @Override
    public void run() {
        while (true) {
                gf.repaint();
        }
    }
}
