package Panes;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * @author paul
 * @since 4/14
 */
public abstract class Character extends ImageView {

    private double xPos, yPos;                  //Character's position in the game window
    private double phyDefMod, magDefMod;        //Modifies how much physical/magical damage the character takes
    private int hitPoints;                      //Character's Health. Character dies if <0
    private int magicPoints;                    //Character's Magic. Used to cast spells
    //private int actionPoints;                 //How far the character can move each turn
    private int attackPoints;                   //How much damage the character deals with attacks
    private boolean hasMoved, hasActed;         //Whether the character has moved/acted this turn or not
    private Image idle, walkLeft, walkRight;    //Character's idle and walking animations
    private int tileIndex;
    private boolean selectedCharacter;

    private Timeline timeline;

    /**
     * Default constructor
     *
     * @param idleImageFilepath path to the idle animation
     */
    public Character(String idleImageFilepath) {
        //Initialize stats
        hitPoints = 100;
        magicPoints = 100;
        //actionPoints = 30;
        attackPoints = 20;
        phyDefMod = 1;
        magDefMod = 1;

        //Initialize flags
        hasMoved = false;
        hasActed = false;

        //Set idle animation
        this.idle = new Image(idleImageFilepath);
        this.setImage(idle);

        //Create timeline
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        //Set Character Selection to False
        selectedCharacter = false;
    }

    public void attack(Character enemy, double tileDistance, double range) {
        double enemyX = enemy.getX();
        double enemyY = enemy.getY();

        if (enemyX >= this.getX() - (tileDistance * 2) && enemyX <= this.getX() + (tileDistance * 2) && (enemyY >= this.getY() - 10 && enemyY <= this.getY() + 10)) {
            enemy.setHitPoints(enemy.getHitPoints() - this.attackPoints);
            //this.actionPoints -= 2;
        }
        //else if or if?
        else if (enemyY >= this.getY() - (tileDistance * 2) && enemyY <= this.getY() + (tileDistance * 2) && (enemyX >= this.getX() - 10 && enemyX <= this.getX() + 10)) {
            enemy.setHitPoints(enemy.getHitPoints() - this.attackPoints);
            //this.actionPoints -= 2;
        }
    }

    //SETTERS
    public void isSelectedCharacter(boolean selection) {
        this.selectedCharacter = selection;
    }

    public void setTileIndex(int index) {
        this.tileIndex = index;
    }

    /**
     * Set x-coordinate position
     *
     * @param xPos position to be set
     */
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * Set y-coordinate position
     *
     * @param yPos position to be set
     */
    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    /**
     * Set Hit Points Limit to >= 0
     *
     * @param hitPoints hit points or health points to be set
     */
    public void setHitPoints(int hitPoints) {
        if (hitPoints < 0) {
            this.hitPoints = 0;
        } else {
            this.hitPoints = hitPoints;
        }
    }

    /**
     * Set Magic Points Limit to >= 0
     *
     * @param magicPoints magic points to be set
     */
    public void setMagicPoints(int magicPoints) {
        if (magicPoints < 0) {
            this.magicPoints = 0;
        } else {
            this.magicPoints = magicPoints;
        }
    }

    /**
     * Set Movement Points Limit to >= 0
     *
     * @param actionPoints movement points to be set
     */
    /*public void setActionPoints(int actionPoints) {
        if (actionPoints < 0) {
            this.actionPoints = 0;
        } else {
            this.actionPoints = actionPoints;
        }
    }*/

    /**
     * Set Attack Points Limit to >= 0
     *
     * @param attackPoints Attack points to be set
     */
    public void setAttackPoints(int attackPoints) {
        if (attackPoints < 0) {
            this.attackPoints = 0;
        } else {
            this.attackPoints = attackPoints;
        }
    }

    /**
     * Set Physical Defense Modifier Limited to between 0 and 1
     *
     * @param phyDefMod Physical Defense Modifier to be set
     */
    public void setPhyDefMod(double phyDefMod) {
        if (phyDefMod < 0) {
            this.phyDefMod = 0;
        } else if (phyDefMod > 1) {
            this.phyDefMod = 1;
        } else {
            this.phyDefMod = phyDefMod;
        }
    }

    /**
     * Set Magical Defense Modifier Limited to between 0 and 1
     *
     * @param magDefMod Magical Defense Modifier to be set
     */
    public void setMagDefMod(double magDefMod) {
        if (magDefMod < 0) {
            this.magDefMod = 0;
        } else if (magDefMod > 1) {
            this.magDefMod = 1;
        } else {
            this.magDefMod = magDefMod;
        }
    }

    /**
     * Set the hasMoved flag
     *
     * @param hasMoved Whether the character has moved or not
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Set the hasActed flag
     *
     * @param hasActed Whether the character has acted or not
     */
    public void setHasActed(boolean hasActed) {
        this.hasActed = hasActed;
    }

    /**
     * Set idle animation
     *
     * @param filePath Path to the gif
     */
    public void setIdleAnimation(String filePath) {
        this.idle = new Image(filePath);
    }

    /**
     * Set walk right animation
     *
     * @param filePath Path to the gif
     */
    public void setWalkRightAnimation(String filePath) {
        this.walkRight = new Image(filePath);
    }

    /**
     * Set walk left animation
     *
     * @param filePath Path to the gif
     */
    public void setWalkLeftAnimation(String filePath) {
        this.walkLeft = new Image(filePath);
    }

    //Getters
    public boolean getSelectedCharacter() {
        return this.selectedCharacter;
    }

    public Image getIdleImage() {
        return this.idle;
    }

    public int getTileIndex() {
        return this.tileIndex;
    }

    /**
     * Get x-position
     *
     * @return Character's x-position
     */
    public double getXPos() {
        return this.xPos;
    }

    /**
     * Get y-position
     *
     * @return Character's y-position
     */
    public double getYPos() {
        return this.yPos;
    }

    /**
     * Get Hit Points
     *
     * @return Character's hit points
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Get Magic Points
     *
     * @return Character's magic points
     */
    public int getMagicPoints() {
        return this.magicPoints;
    }

    /**
     * Get Movement Points
     *
     * @return Character's movement points
     */
   /* public int getActionPoints() {
        return this.actionPoints;
    }*/

    /**
     * Get Attack Points
     *
     * @return Character's attack points
     */
    public int getAttackPoints() {
        return this.attackPoints;
    }

    /**
     * Get Physical Defense Modifier
     *
     * @return Character's Physical Defense Modifier
     */
    public double getPhyDefMod() {
        return this.phyDefMod;
    }

    /**
     * Get Magical Defense Modifier
     *
     * @return Character's Magical Defense Modifier
     */
    public double getMagDefMod() {
        return this.magDefMod;
    }

    /**
     * Get hasMoved
     *
     * @return Whether the character has moved this turn or not
     */
    public boolean hasMoved() {
        return this.hasMoved;
    }

    /**
     * Get hasActed
     *
     * @return Whether the character has acted this turn or not
     */
    public boolean hasActed() {
        return this.hasActed;
    }

    /**
     * Determine if the character's turn is over or not
     */
    /*public void isTurnOver() {
        if (this.actionPoints == 0) {
            nextTurn();
        }
    }*/
    
    /*public void nextTurn() {
        this.actionPoints = 3;
    }*/

    //MOVEMENT/ANIMATION METHODS
    /**
     * Makes the character move right to a destination
     *
     * @param destination Where to move
     */
    public void walkRight(double destination, double limit, ArrayList<Tile> tiles) {
        timeline.stop();
        timeline.getKeyFrames().clear();

        //Find the X-value of the tile to the right of the current tile. Then check the data structure to see if any of the "is filled" tiles correspond to that value.
        //(tileDistance * 6) - tileDistance);
        this.setImage(this.walkRight);

        KeyFrame kFRight = new KeyFrame(Duration.millis(5), e -> {

            if ((this.tileIndex < 35 && tiles.get(tileIndex + 1).isFilled()) || destination > limit) {
                timeline.stop();
                this.setImage(this.idle);
            } else if (this.getX() >= destination) {
                timeline.stop();
                this.setImage(this.idle);

                tiles.get(tileIndex).setFilled(false);
                tileIndex += 1;
                tiles.get(tileIndex).setFilled(true);
            } else {
                this.setX(this.getX() + 1);
            }
        });
        timeline.getKeyFrames().add(kFRight);
        timeline.play();
    }

    /**
     * Makes the character move left to a destination
     *
     * @param destination Where to move
     */
    public void walkLeft(double destination, double limit, ArrayList<Tile> tiles) {
        timeline.stop();
        timeline.getKeyFrames().clear();

        this.setImage(this.walkLeft);

        KeyFrame kFLeft = new KeyFrame(Duration.millis(5), e -> {
            if ((this.tileIndex > 0 && tiles.get(tileIndex - 1).isFilled()) || destination < limit) {

                this.setImage(this.idle);
            } else if (this.getX() <= destination) {

                this.setImage(this.idle);

                tiles.get(tileIndex).setFilled(false);
                this.tileIndex -= 1;
                tiles.get(tileIndex).setFilled(true);
            } else {
                this.setX(this.getX() - 1);
            }
        });
        timeline.getKeyFrames().add(kFLeft);
        timeline.play();
    }

    /**
     * Makes the character move up to the destination
     *
     * @param destination Where to move
     */
    public void walkUp(double destination, double limit, ArrayList<Tile> tiles) {
        timeline.stop();
        timeline.getKeyFrames().clear();

        this.setImage(this.walkLeft);

        KeyFrame kFLeft = new KeyFrame(Duration.millis(5), e -> {
            if ((this.tileIndex > 5 && tiles.get(tileIndex - 6).isFilled()) || destination < limit) {
                timeline.stop();
                this.setImage(this.idle);
            } else if (this.getY() <= destination) {
                timeline.stop();
                this.setImage(this.idle);

                tiles.get(tileIndex).setFilled(false);
                this.tileIndex -= 6;
                tiles.get(tileIndex).setFilled(true);
            } else {
                this.setY(this.getY() - 1);
            }
        });
        timeline.getKeyFrames().add(kFLeft);
        timeline.play();
    }

    /**
     * Makes the character move down to the destination
     *
     * @param destination Where to move
     */
    public void walkDown(double destination, double limit, ArrayList<Tile> tiles) {
        timeline.stop();
        timeline.getKeyFrames().clear();

        this.setImage(this.walkRight);

        KeyFrame kFLeft = new KeyFrame(Duration.millis(5), e -> {
            if ((this.tileIndex < 30 && tiles.get(tileIndex + 6).isFilled()) || destination > limit) {
                timeline.stop();
                this.setImage(this.idle);
            }
            if (this.getY() >= destination) {
                timeline.stop();
                this.setImage(this.idle);

                tiles.get(tileIndex).setFilled(false);
                this.tileIndex += 6;
                tiles.get(tileIndex).setFilled(true);
            } else {
                this.setY(this.getY() + 1);
            }
        });
        timeline.getKeyFrames().add(kFLeft);
        timeline.play();
    }

    /**
     * Begins character's idle animation
     */
    public void idle() {
        timeline.stop();
        this.setImage(this.idle);
    }
}
