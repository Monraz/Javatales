package Panes;

import java.util.ArrayList;

public class Player {
    ArrayList<Character> characters;
    int actionPoints;
    private boolean isHoster;

    public Player(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public Player() {
        this.actionPoints=30;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }



    public boolean isHoster() {
        return isHoster;
    }

    public void setHoster(boolean hoster) {
        isHoster = hoster;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public  void reduceActionPoints(int x){
        this.actionPoints=this.actionPoints-x;

    }

    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }
}
