package Panes;

/**
 * Class for Barbarian character
 *
 * @author paul
 */
public class Barbarian extends Character {

    /**
     * Constructor that initialize the Barbarian's x and y starting position,
     * and the idle/walking animations
     *
     * @param startX starting x-position
     * @param startY starting y-position
     */
    public Barbarian(double startX, double startY, int tileIndex) {
        super("images/Barbarian_Idle_000.gif");
        super.setWalkRightAnimation("images/Barbarian_Walking_Right_000.gif");
        super.setWalkLeftAnimation("images/Barbarian_Walking_Left_000.gif");

        this.setX(startX);
        this.setY(startY);

        this.setXPos(startX);
        this.setYPos(startY);

        this.setTileIndex(tileIndex);
    }
}
