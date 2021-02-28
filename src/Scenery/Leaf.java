package Scenery;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * The leaf class handles animated leaves
 * NOTE: This does not represent those leaves which are static on trees.
 *       Those are drawn in the Tree class.
 */
public class Leaf implements GameElement{
    private static ArrayList<Leaf> leafList = new ArrayList<Leaf>(); //List of leaves
    private Rectangle leaf; //The leaf

    /**
     * Creates new leaf
     * @param length - length of branch. Used to determine leaf size
     * @param endX - End X
     * @param endY - End Y
     */
    public Leaf(double length, double endX, double endY){
        //Creates a new random number. This is used to limit the size of falling leaves.
        //If random number does not equal 1 then no leaf is created.
        Random r = new Random();
        int n = r.nextInt(3200);
        if(n == 1){
            leaf = new Rectangle(endX, endY, 5, 5);
            leaf.setFill(Color.rgb(230, 141,182));
            leaf.setVisible(false);
            leafList.add(this);
        }
    }

    /**
     * @return - the new leaf
     */
    public Rectangle getLeaf(){ return leaf; }

    /**
     * @return - the list of leaves
     */
    public static ArrayList<Leaf> getLeafList(){
        return leafList;
    }

    /**
     * Makes the leaf fall.
     */
    @Override
    public void update() {
        leaf.setTranslateY(leaf.getTranslateY() + 1);
        if(leaf.getTranslateY() > 475){
            leaf.setTranslateY(0);
        }
    }
}
