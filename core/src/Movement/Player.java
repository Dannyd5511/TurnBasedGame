package Movement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 *
 * @author Libbania Reyes
 * @version 1.3
 */
public class Player extends Entity{
    private final GamePanel gp;
    private final KeyHandler keyH;

    /**
     * Creates a Player object with given game panel and key handler.
     * Based on the key pressed, it picks a sprite
     *
     * @param gp representation of the playing area
     * @param keyH the key input
     */
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets the default coordinates, speed and facing direction of the sprite
     */
    public void setDefaultValues(){
        this.x = 100;
        this.y = 100;
        this.speed = 4;
        this.direction = "down";
    }

    /**
     * Based on the key pressed, it reads the sprite of the direction of the key
     */
    private void getPlayerImage(){
        up1 = readImage("assets/PlayerSprites/boy_up_1.png");
        up2 = readImage("assets/PlayerSprites/boy_up_2.png");
        down1 = readImage("assets/PlayerSprites/boy_down_1.png");
        down2 = readImage("assets/PlayerSprites/boy_down_2.png");
        left1 = readImage("assets/PlayerSprites/boy_left_1.png");
        left2 = readImage("assets/PlayerSprites/boy_left_2.png");
        right1 = readImage("assets/PlayerSprites/boy_right_1.png");
        right2 = readImage("assets/PlayerSprites/boy_right_2.png");
    }

    /**
     * getPlayerImage() helper function
     * Reads the sprite stored at the given path
     * @param filePath the path where the sprite is stored
     * @return sprite
     */

    private BufferedImage readImage(String filePath){
        BufferedImage image;
        try{
            image = ImageIO.read(new FileInputStream(filePath));
        }
        catch (IOException e){
            image = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
            System.out.println("Unable to load sprite");

        }
        return image;
    }

    /**
     * Checks if any of the keys (W A S D) have been pressed. Changes the x and y coordinate based on the key pressed
     */
    public void update(){
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){

            if(keyH.upPressed){
                direction = "up";
                y -= speed;
            }
            else if(keyH.downPressed){
                direction = "down";
                y += speed;
            }
            else if(keyH.leftPressed){
                direction = "left";
                x -= speed;
            }
            else if(keyH.rightPressed){
                direction = "right";
                x += speed;
            }
            spriteCounter++;
        }

    }

    /**
     * Change the leg the sprite is moving so that it looks like its walking
     */
    public void changeSpriteNum(){
        //updates every 15 frames
        if(spriteCounter > 15){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    /**
     * draws the image on the screen
     * @param g2 provides control over the buffered image
     */
    public void draw(Graphics2D g2){

        BufferedImage image = null;

        //the player moved in that direction. check if the right leg or left lef should move.
        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                  image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
            default:
                break;
        }
        //draw image at x and y coordinate, as big as the size of the tiles
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }


}
