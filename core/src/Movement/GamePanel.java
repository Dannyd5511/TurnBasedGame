package Movement;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTilesSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTilesSize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; //576 pixels

    //FPS

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        //improves rendering performance
        this.setDoubleBuffered(true);
        //recognize key input
        this.addKeyListener(keyH);
        //GamePanel is focused to receive key input
        this.setFocusable(true);
    }

    public void startGameThread(){
        //passing game panel
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/FPS;

        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            //every draw interval
            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                //FPS
                drawCount = 0;
                timer = 0;
            }

        }
    }
    public void update(){
        player.update();
        player.changeSpriteNum();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
