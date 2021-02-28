package Scenery;

import Main.Graphics;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * The class for the user controllable character
 */
public class Player implements GameElement{
    private Group character; //Group of shapes forming character
    private static final Color FOX = Color.rgb(248, 125, 37); //Color
    private boolean right; //Determines if player is moving right
    private boolean left; //Determines if player is moving left
    private Polygon leg; //The leg triangle
    private Polygon leg2; //The other leg triangle
    private Polygon head; //The head triangle
    private Rotate headRotate; //Rotation logic for head
    private Rotate legRotate; //Rotation logic for legs
    private Rotate legRotate2;
    private boolean animateDirectionHead; //Determines which direction head should move
    private boolean animateDirectionLegs; //Determines which direction legs should move

    /**
     * Creates new player
     */
    public Player(){
        left = false;
        right = false;
        //Creates body
        Rectangle body = new Rectangle(Graphics.getScreenWidth() / 2 - 40, Graphics.getScreenHeight() - 70,
                80, 25);
        body.setFill(FOX);
        head = new Polygon(); //Creates head
        head.getPoints().addAll(
            body.getX() + 80, body.getY(),
            body.getX() + 80, body.getY() - 30,
            body.getX() + 110, body.getY()
        );
        head.setFill(FOX);
        leg = new Polygon(); //Creates leg 1
        leg.getPoints().addAll(
                body.getX(), body.getY() + 25,
                body.getX() + 15, body.getY() + 25,
                body.getX() + 7.5, body.getY() + 45
        );
        leg.setFill(FOX);
        leg2 = new Polygon(); //Creates leg 2
        leg2.getPoints().addAll(
          body.getX() + 65, body.getY() + 25,
          body.getX() + 80, body.getY() + 25,
          body.getX() + 72.5, body.getY() + 45
        );
        leg2.setFill(FOX);
        Polygon tail = new Polygon(); //Creates tail
        tail.getPoints().addAll(
          body.getX(), body.getY(),
          body.getX() - 25, body.getY(),
          body.getX() - 25, body.getY() + 5
        );
        tail.setFill(FOX);
        character = new Group();
        character.setTranslateY(Graphics.getScreenHeight() / 2 - 70);
        character.getChildren().addAll(body, head, leg, leg2, tail);

        //Sets pivot axis for head & both legs
        headRotate = new Rotate();
        headRotate.setPivotY(body.getY());
        headRotate.setPivotX(body.getX() + 80);
        head.getTransforms().add(headRotate);
        animateDirectionHead = true;

        legRotate = new Rotate();
        legRotate.setPivotX(body.getX() + 7.5);
        legRotate.setPivotY(body.getY() + 25);
        leg.getTransforms().add(legRotate);

        legRotate2 = new Rotate();
        legRotate2.setPivotX(body.getX() + 72.5);
        legRotate2.setPivotY(body.getY() + 25);
        leg2.getTransforms().add(legRotate2);

        animateDirectionLegs = true;
    }

    /**
     * @return The character Group
     */
    public Group getCharacter(){
        return character;
    }

    /**
     * Updates player logic
     */
    @Override
    public void update() {
        if(right){ //Moves character right if true
            character.setTranslateX(character.getTranslateX() + 2);
            character.setScaleX(1);
            animate();
        }else if(left){ //Moves character left if true
            character.setTranslateX(character.getTranslateX() - 2);
            character.setScaleX(-1);
            animate();
        }else{ //Cancels all animations and resets pos of animated objects to 0
            headRotate.setAngle(0);
            animateDirectionHead = true;

            legRotate.setAngle(0);
            legRotate2.setAngle(0);
            animateDirectionLegs = true;
        }
    }

    /**
     * Handles key press
     * @param e - the KeyEvent when a key is pressed
     */
    public void setDirection(KeyEvent e){
        switch(e.getCode()){
            case D: right = true; break;
            case A: left = true; break;
        }
    }

    /**
     * Handles key release
     * @param e - The KeyEvent when a key is released
     */
    public void release(KeyEvent e){
        switch(e.getCode()){
            case D: right = false; break;
            case A: left = false; break;
        }
    }

    /**
     * Animates the head/legs when moving
     */
    private void animate(){
        //Moves head between 6 & -6 degrees
       if(animateDirectionHead && headRotate.getAngle() < 6){
           headRotate.setAngle(headRotate.getAngle() + .5);
       }else if(!animateDirectionHead && headRotate.getAngle() > -6){
           headRotate.setAngle(headRotate.getAngle() - .5);
       }else{
           animateDirectionHead = !animateDirectionHead; //Changes rotation direction
       }

       //Moves legs between 40 & -40 degrees
       if(animateDirectionLegs && legRotate.getAngle() < 40){
           legRotate.setAngle(legRotate.getAngle() + 2);
           legRotate2.setAngle(legRotate2.getAngle() - 2);
       }else if(!animateDirectionLegs && legRotate.getAngle() > -40){
           legRotate.setAngle(legRotate.getAngle() - 2);
           legRotate2.setAngle(legRotate2.getAngle() + 2);
       }else{
           animateDirectionLegs = !animateDirectionLegs; //Changes rotation direction
       }
    }
}
