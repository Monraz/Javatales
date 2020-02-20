package Panes;

//Michael Anderson
//CIT-282
//Professor Miller
//2/26/19
public class Tile{
    
    int defBonus, elevation;
    boolean filled;
    Character entity;

    private double xStart, yStart, xEnd, yEnd, tileWidth;

    //Constructors
    protected Tile() { //Default
        defBonus = 0;
        elevation = 0;
        filled = false;
        this.xStart = 0;
        this.yStart = 0;
    }

    protected Tile(int defBonus, int elevation, boolean filled, double xStart, double yStart, double tileWidth) { //Empty tile
        this.defBonus = defBonus;
        this.elevation = elevation;
        this.filled = filled;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xStart + tileWidth;
        this.yEnd = yStart + tileWidth;
    }

    protected Tile(int defBonus, int elevation, boolean filled, double xStart, double yStart, double tileWidth, Character entity) { //Filled tile
        this.defBonus = defBonus;
        this.elevation = elevation;
        this.entity = entity;
        this.filled = filled;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xStart + tileWidth;
        this.yEnd = yStart + tileWidth;
    }

    //Getters and Setters

    public int getDefBonus() {
        return defBonus;
    }

    public void setDefBonus(int defBonus) {
        this.defBonus = defBonus;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public Character getEntity() {
        return entity;
    }

    public void setEntity(Character entity) {
        this.entity = entity;
        
    }

    public double getStartX() {
        return this.xStart;
    }

    public double getStartY() {
        return this.yStart;
    }
    
    public double getEndX(){
        return this.xEnd;
    }
    
    public double getEndY(){
        return this.yEnd;
    }
    
    //Methods
}

/*
Change History:

 */
