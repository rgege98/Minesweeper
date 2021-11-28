package hu.minesweeper.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Record implements Serializable {

    private String name;
    private String difficulty;
    private int time;

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Integer getTime() {
        return time;
    }

    public Record(String name, String difficulty, Integer time) {
        this.name = name;
        this.difficulty = difficulty;
        this.time = time;
    }

}
