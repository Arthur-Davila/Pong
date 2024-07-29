import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
    public static int WIDTH = 160;
    public static int HEIGHT = 120;
    private static int SCALE = 3;
   
    //Estanciado
    public static Player player;
    public static Enemy enemy;
    public static Ball ball;
    //Thread Parte
    public static Thread thread;
    private boolean isRunning;
  
    public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
  
    public Game(){
 
        this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        this.addKeyListener(this);
        player = new Player(60,HEIGHT-5);
        enemy = new Enemy(60, 0);
        ball = new Ball(80, HEIGHT/2);
        initFrame();
       
        }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();

    }
    public void initFrame(){
        JFrame frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
    public void stop(){
        isRunning=false;
        try {
            thread.join();
        } catch (InterruptedException e) {
        
            e.printStackTrace();
        }
    }
    
    public void start(){
        isRunning =true;
        thread = new Thread(this);
        thread.start();
    }
    public void tick(){
        if(Ball.delay<=0){
        player.tick();
        enemy.tick();
        ball.tick();
        }
    }
    
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs==null){
           this.createBufferStrategy(3);
           return;
        }
        Graphics g = layer.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        player.render(g);
        enemy.render(g);
        ball.render(g);
    
        g = bs.getDrawGraphics();
        g.drawImage(layer, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
        
        bs.show();
        }
    @Override
    public void run() {
      long lastTime = System.nanoTime();
      double amountOfTicks = 60;
      double ns = 1000000000/amountOfTicks;
      double delta = 0;
      int frame =0;
      double timer = System.currentTimeMillis();
      while(isRunning){
        long now = System.nanoTime();
        delta +=(now-lastTime)/ns;
        lastTime=now;
        if(delta>=1){
            tick();
            render();
            delta --;
            frame++;
        
        }
        if(System.currentTimeMillis()-timer>=1000){
          //  System.out.println("FPS: "+frame);
            timer += 1000;
            frame = 0;
            Ball.delay--;
        }
        
        
    }
    }

    @Override
    public void keyTyped(KeyEvent e) {  
  
    }

    @Override
    public void keyPressed(KeyEvent e) {
      
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            player.right = true;
        
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            player.right = false;
        
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left = false;
        }
    }
    
}
