package Scenery;

import Main.Graphics;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Class which handles the building of trees
 */
public class Tree {
    private Pane tree; // Container
    //Colors
    private static final String TRUNK_COLOR_HEX = "#5a343a";
    private static final String CHERRY_COLOR_HEX = "#e68db6";
    private static final Color CHERRY_COLOR = Color.rgb(230,141,182);

    /**
     * Creates a new tree with desired offset
     * @param offset - the degree to set the outer branches. Recommended 0-90
     */
    public Tree(double offset){
        tree = new Pane();
        int angle = 90;
        //Builds trunks
        for(double i = 1; i < 4; i ++) {
            Line trunk = new Line(Graphics.getScreenWidth() * (i/4), Graphics.getScreenHeight() - 30,
                    Graphics.getScreenWidth() * (i/4), Graphics.getScreenHeight() - 180);
            trunk.setStrokeWidth(10);
            trunk.setStyle("-fx-stroke: " + TRUNK_COLOR_HEX);
            //Adds branches
            if(i == 2){
                branch(tree, angle + offset, 125, trunk);
                branch(tree, angle - offset, 125, trunk);
                branch(tree, angle, 125, trunk);
            }else {
                branch(tree, angle + offset, 100, trunk);
                branch(tree, angle - offset, 100, trunk);
                branch(tree, angle, 100, trunk);
            }
            tree.getChildren().add(trunk);
        }
    }

    /**
     * Recursive method which adds a branch ontop of the previous branch
     * @param pane - pane to add branch to
     * @param angle - angle offset
     * @param length - length of branch
     * @param previous - previous drawn branch
     */
    private void branch(Pane pane, double angle, int length, Line previous){
        if(length < 4) { return; }
        else{
            //Builds a branch
            double startX = previous.getEndX();
            double startY = previous.getEndY();
            double endX = startX + Math.cos(Math.toRadians(angle)) * length;
            double endY = startY - Math.sin(Math.toRadians(angle)) * length;
            Line line = new Line(startX, startY, endX, endY);
            line.setStrokeWidth(length * .05);
            //Shadow of tree
            Rectangle shadow = getShadow(startX, endX);
            shadow.setFill(Color.rgb(10, 10, 10));
            //Adds leaves
            if(length < 20){
                addLeaf(pane, endX, endY, length);
            }
            //Determines color of branch
            if(length < 100){
                line.setStyle("-fx-stroke: " + CHERRY_COLOR_HEX);
                new Leaf(length, endX, endY);
            }else{
                line.setStyle("-fx-stroke: " + TRUNK_COLOR_HEX);
            }

            //Adds elements to pane
            pane.getChildren().add(shadow);
            pane.getChildren().add(line);

            //recursive call
            branch(pane, angle - 15, (int) (length * .66), line);
            branch(pane, angle + 15, (int) (length * .66), line);
        }
    }

    /**
     * Draws a shadow below the tree
     * @param startX - X to start shadow
     * @param endX - X to end shadow
     * @return - shadow rectangle
     */
    private Rectangle getShadow(double startX, double endX){
        if(startX > endX){
            return new Rectangle(endX, Graphics.getScreenHeight() - 30, Math.abs(endX - startX), 5);
        }else{
            return new Rectangle(startX, Graphics.getScreenHeight() - 30, endX - startX, 5);
        }
    }

    /**
     * Adds a leaf at the desired position
     * @param pane - pane to draw to
     * @param endX - endX
     * @param endY - endY
     * @param length - length of branch (determines size of leaf)
     */
    private void addLeaf(Pane pane, double endX, double endY, double length){
        Rectangle leaf = new Rectangle(endX - length/4, endY, length/2, length/2);
        leaf.setFill(CHERRY_COLOR);
        pane.getChildren().add(leaf);
    }

    /**
     * @return - pane containing tree
     */
    public Pane getTree(){
        return tree;
    }
}
