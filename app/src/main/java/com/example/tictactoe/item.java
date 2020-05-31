package com.example.tictactoe;

public class item {
    String name;
    int win;
    int draw;
    int loss;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public item(String name, int win, int draw, int loss) {
        this.name = name;
        this.win = win;
        this.draw = draw;
        this.loss = loss;
    }
    public item(){};

    @Override
    public String toString() {
        return "item{" +
                "name='" + name + '\'' +
                ", win='" + win + '\'' +
                ", draw='" + draw + '\'' +
                ", loss='" + loss + '\'' +
                '}';
    }
}
