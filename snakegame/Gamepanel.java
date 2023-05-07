package snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gamepanel extends JPanel implements ActionListener,KeyListener{

  private int[] snakexlength = new int[750];
  private int[] snakeylength = new int[750];
  private int lengthOfSnake = 3;

  //set intial snake direction
  private boolean left = false;
  private boolean right = true;
  private boolean up = false;
  private boolean down = false;

  private int moves=0;
  private int score=0;
  private boolean gameover=false;

  private int[] xPos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
  private int[] yPos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625}; 
  
  private Random random=new Random();
  private int enemyX,enemyY;
  //set imageIcon 

  private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
  private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
  private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
  private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
  private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
  private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
  private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));
  //movement vavriable
  private javax.swing.Timer timer;
  private int delay=100;

  Gamepanel() {
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(true);

    timer=new javax.swing.Timer(delay, this);
    timer.start();

    newenemy();
  }
  private void newenemy() {
    enemyX = xPos[random.nextInt(xPos.length)];
    enemyY = yPos[random.nextInt(yPos.length)];
    //this is done, because at the time of collision enemy not show on snake;
    for(int i=lengthOfSnake-1;i>=0;i--){
        if(snakexlength[0]==enemyX && snakeylength[0]==enemyY){
            newenemy();
        }
    }
}
@Override
  public void paint(Graphics g) {
    
    super.paint(g);
    //Set inner boundaries
    g.setColor(Color.WHITE);
    //title boundary
    g.drawRect(24, 10, 851, 55);
    //gaming boundary
    g.drawRect(24, 74, 851, 576);
    //fill image in title box and give width height
    snaketitle.paintIcon(this, g, 25, 11);

    //gaming box fill with color  and giving size
    g.setColor(Color.BLACK);
    g.fillRect(25, 75, 850, 575);

    if(moves==0){
        snakexlength[0]=100;
        snakexlength[1]=75;
        snakexlength[2]=50;

        snakeylength[0]=100;
        snakeylength[1]=100;;
        snakeylength[2]=100;
    }
    //draw snake head
    if(left){
        leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
    }
    if(right){
        rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
    }
    if(up){
        upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
    }
    if(down){
        downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
    }

    //draw snake body
    for(int i=1;i<lengthOfSnake;i++){
        snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
    }

    enemy.paintIcon(this, g, enemyX, enemyY);
    //show game over in gaming window 
    if(gameover){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("GAME OVER", 300, 300);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press SPACE to Restart", 350, 350);
    }
    //score and lengthof snake showing on gaming window
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.PLAIN, 14));
    g.drawString("Score : "+score, 730, 30);
    g.drawString("Length of Snake : "+lengthOfSnake, 730, 50);

    g.dispose();
  }
@Override
public void actionPerformed(ActionEvent e) {
    //body+head position movement 
    for(int i=lengthOfSnake-1;i>0;i--){
        snakexlength[i]=snakexlength[i-1];
        snakeylength[i]=snakeylength[i-1];
    }
    //change head position again and again 
    if(left){
        snakexlength[0]=snakexlength[0]-25;
    }
    if(right){
        snakexlength[0]=snakexlength[0]+25;
    }
    if(up){
        snakeylength[0]=snakeylength[0]-25;
    }
    if(down){
        snakeylength[0]=snakeylength[0]+25;
    }
    //if right boundary touch by the snake,then arrive from left to right again.
    if(snakexlength[0]>850) snakexlength[0]=25;
    if(snakexlength[0]<25) snakexlength[0]=850;
    //if top boundary touch by the snake,then arrive from bootom to top again.
    if(snakeylength[0]>625) snakeylength[0]=75;
    if(snakeylength[0]<75) snakeylength[0]=625;

    collidesWithEnenmy();
    collidesWithBody();
    repaint();
}
@Override
public void keyPressed(KeyEvent e) {
    //when press the key space then restart the game
    if(e.getKeyCode()==KeyEvent.VK_SPACE){
        restart();
    }
    //left arrow key pressed then movement change by snake
    if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)){
        left=true;
        right=false;
        up=false;
        down=false;
        moves++;
    }
    //right arrow key pressed then movement change by snake
    if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){
        left=false;
        right=true;
        up=false;
        down=false;
        moves++;
    }
    //up arrow key pressed then movement change by snake
    if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){
        left=false;
        right=false;
        up=true;
        down=false;
        moves++;
    }
    //ldown arrow key pressed then movement change by snake
    if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
        left=false;
        right=false;
        up=false;
        down=true;
        moves++;
    }
}
@Override
public void keyReleased(KeyEvent e) {
}
public void keyTyped(KeyEvent e) {   
}
//when collision happens then do increase of snake and scor also.
public void collidesWithEnenmy(){
    if(snakexlength[0]==enemyX && snakeylength[0]==enemyY){
        newenemy();
        lengthOfSnake++;
        score++;
    }
}
//when head of snake touch with body then game over
public void collidesWithBody(){
    for(int i=lengthOfSnake-1;i>0;i--){
        if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0]){
            timer.stop();
            gameover=true;

        }
    }
}
public void restart(){
    gameover=false;
    moves=0;
    score=0;
    lengthOfSnake=3;
    left=false;
    right=true;
    up=false;
    down=false;
    timer.start();
    repaint();
}

  public static void main(String[] args) {
    //Framing
    JFrame frame = new JFrame("Snake Game");
    //Set Framing
    frame.setBounds(10, 10, 905, 700);
    //Doesn't change size by user
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Gamepanel panel = new Gamepanel();
    //Background color fill
    panel.setBackground(Color.DARK_GRAY);
    frame.add(panel);

    frame.setVisible(true);
  }
}
