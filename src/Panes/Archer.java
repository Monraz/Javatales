package Panes;

import java.util.ArrayList;

/**
 * Class for Archer character
 *
 * @author paul
 */
public class Archer extends Character {

    /**
     * Constructor that initializes the Archer's x and y starting position, and
     * the idle/walking animations
     *
     * @param startX starting x-position
     * @param startY starting y-position
     */
    public Archer(double startX, double startY, int tileIndex) {
        super("images/Archer_Idle_000.gif");
        super.setWalkRightAnimation("images/Archer_Walking_Right_000.gif");
        super.setWalkLeftAnimation("images/Archer_Walking_Left_000.gif");

        this.setX(startX);
        this.setY(startY);

        this.setXPos(startX);
        this.setYPos(startY);

        this.setTileIndex(tileIndex);

        this.setAttackPoints(20);
        //this.setActionPoints(2);
    }

}
