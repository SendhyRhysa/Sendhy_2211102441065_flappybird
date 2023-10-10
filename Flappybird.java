import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Flappybird here.
 * setRotation((int)(1 * 16)); Greenfoot.playSound("flay.mp3");
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Flappybird extends Actor
{
    private double g = 1; 
    private int y = 300;
    private boolean haspressed = false;
    private boolean isalive = true;
    private boolean isacross = false;
    private boolean hasaddscore = false;
    
    private GreenfootImage[] birdImages;
    private int currentImageIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed = 10;
    
    public Flappybird(){
        birdImages = new GreenfootImage[2];
        birdImages[0] = new GreenfootImage("bird1.png");
        birdImages[1] = new GreenfootImage("bird2.png");
        
        for (GreenfootImage image : birdImages) {
            image.scale(100, 90);
        }
        
        setImage(birdImages[0]); 
    }
    /**
     * Act - do whatever the Flappybird wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        animationCounter++;
        
        if (animationCounter % animationSpeed == 0) {
        if (currentImageIndex == 0) {
            setImage(birdImages[1]);
            currentImageIndex = 1;
        } else {
            setImage(birdImages[0]);
            currentImageIndex = 0;
        }
    }

        if(spacePressed()){
            g=-2;
        }
        g +=0.1;
        y += g;
        setLocation(getX(), (int)(y));
        
        if(isTouchpipe()){
            isalive = false;
        }
        
        if(isAtEdge()){
            isalive = false;
        }
        
        if(!isalive){
            getWorld().addObject(new Gameover(), 300, 200);
            getWorld().removeObject(this);
        }
        
        if(!hasaddscore && isacross && isalive){
            Score.add(1);
        }
        hasaddscore = isacross;
        
    }
    
    public boolean spacePressed(){
        boolean pressed = false;
        if(Greenfoot.isKeyDown("space")){
            if(!haspressed){
                pressed = true;
            }
            haspressed = true;
        }else{
            haspressed = false;
        }
        return pressed;
    }
    
    public boolean isTouchpipe(){
        isacross = false;
        for(Pipe pipe : getWorld().getObjects(Pipe.class)){
            if(Math.abs(pipe.getX() - getX()) < 60 ){
                if(Math.abs(pipe.getY() + 30 - getY()) > 37){
                    isalive = false;
                }
                isacross = true; 
            }
        }
        return !isalive;
    }
}
