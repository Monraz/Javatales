package Panes;

/**
 * Class for Mage character
 *
 * @author paul
 */
public class Mage extends Character {

    /**
     * Constructor that initialize the Mage's x and y starting position, and the
     * idle/walking animations
     *
     * @param startX starting x-position
     * @param startY starting y-position
     */
    public Mage(double startX, double startY, int tileIndex) {
        super("images/Mage_Idle_000.gif");
        super.setWalkRightAnimation("images/Mage_Walking_Right_000.gif");
        super.setWalkLeftAnimation("images/Mage_Walking_Left_000.gif");

        this.setX(startX);
        this.setY(startY);

        this.setXPos(startX);
        this.setYPos(startY);

        this.setTileIndex(tileIndex);
    }
}
