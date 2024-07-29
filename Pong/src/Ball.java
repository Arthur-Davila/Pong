import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
public class Ball {
    public double x,y;
    public int width,height;
    public double dx,dy;
    public final double speed = 1.7;
    public static int delay = 5;

    public boolean playerWin;
    public boolean enemyWin;
    public Ball(int x,int y){
        this.x=x;
        this.y=y;
        this.width =4;
        this.height=4;
        
        int angle = new Random().nextInt(120-45)+46;
        dx = Math.cos(Math.toRadians(angle));
        dy = Math.sin(Math.toRadians(angle));
        
    }

    public void tick(){
        if(x+(dx*speed)+width>=Game.WIDTH){
            dx*=-1;
        }
        else if(x+(dx*speed)<0){
            dx*=-1;
        }
        if(y>Game.HEIGHT){
            //PONTO DO INIMIGO
            enemyWin = true;
        }
        else if(y<0){
            playerWin = true;
        }
        Rectangle bounds = new Rectangle((int) (x+(dx*speed)),(int)(y+(dy*speed)),width,height);
        Rectangle boundsPlayer = new Rectangle( Game.player.x,Game.player.y,Game.player.width,Game.player.height);
        Rectangle boundsEnemy = new Rectangle((int) Game.enemy.x,(int)Game.enemy.y,Game.enemy.width,Game.enemy.height);

        if(bounds.intersects(boundsPlayer)){
                          
         int angle = new Random().nextInt(120-45)+46;
         dx = Math.cos(Math.toRadians(angle));
         dy = Math.sin(Math.toRadians(angle));
            if(dy>0){
   
         dy*=-1;
            }
      
        }
        else if(bounds.intersects(boundsEnemy)){
            int angle = new Random().nextInt(120-45)+46;
            dx = Math.cos(Math.toRadians(angle));
            dy = Math.sin(Math.toRadians(angle));
            if(dy<0){
                 
            
                dy*=-1;
                   }
        
        }
        if(delay<=0){
        x+=dx*speed;
        y+=dy*speed;
    }
    }
    public void render(Graphics g){
        g.setColor(Color.yellow);
        if(delay<=0){
        g.fillRect((int)x,(int) y, width, height);
        }else{
        g.drawString(Integer.toString(delay), 75, Game.HEIGHT/2);
        }
        if(enemyWin){
            g.drawString("P2 WINS", 60, Game.HEIGHT/2);
        }
        else if(playerWin){
            g.drawString("P1 WINS", 60,Game.HEIGHT/2);
        }
    }
}