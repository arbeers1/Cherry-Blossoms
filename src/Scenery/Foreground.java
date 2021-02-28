package Scenery;

import Main.Graphics;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Contains all scenery elements
 */
public class Foreground implements GameElement{
    private Pane foreground; //container
    private ArrayList<Pane> frameList; //This holds the animation for trees
    private ArrayList<Leaf> leafList; //The list of all randomly created leaves
    private ArrayList<Leaf> activeList; //The list of leaves to animate
    //NOTE: Leaves from leafList are added to the activeList when they are ready to fall
    private boolean add; //Determines if the trees are going outbound or inbound on the animation
    private int index; //Current frame in animation

    /**
     * Creates new foreground
     */
    public Foreground(){
        add = true;
        //Sets the grass
        Rectangle rect = new Rectangle(0, Graphics.getScreenHeight() - 30, Graphics.getScreenWidth(), 30);
        rect.setFill(Color.rgb(230,141,182));
        foreground = new Pane();
        foreground.setStyle("-fx-background-color: rgba(0, 0, 0, .0)"); //Background sky
        foreground.getChildren().add(rect);
        //Creates the animation for trees
        frameList = new ArrayList<Pane>(60);
        for(int i = 0; i < 60; i++){
            frameList.add(new Tree(30 + i * .02).getTree());
            frameList.get(i).setVisible(false);
            foreground.getChildren().add(frameList.get(i));
        }

        //Creates falling leaves
        leafList = Leaf.getLeafList();
        for(int i = 0; i < leafList.size(); i++){
            foreground.getChildren().add(leafList.get(i).getLeaf());
        }
        activeList = new ArrayList<Leaf>();
        index = 1;
    }

    /**
     * @return falling leaves
     */
    public Pane getForeground(){
        return foreground;
    }

    /**
     * Updates the foreground elements
     */
    @Override
    public void update() {
        //switches frame
        Pane previousFrame = frameList.get(index);
        if(add && index < 59){
            index++;
        }else if(!add && index > 0){
            index--;
        }else{
            add = !add;
        }
        previousFrame.setVisible(false);
        frameList.get(index).setVisible(true);

        //sets the list of animated leafs to active list. This is done randomly to allow spacing between the leaves.
        if(activeList.size() != Leaf.getLeafList().size()){
            Random r = new Random();
            if(r.nextInt(80) == 1 && leafList.size() != 0){
                activeList.add(leafList.remove(leafList.size() - 1));
                activeList.get(activeList.size() - 1).getLeaf().setVisible(true);
            }
        }

        //Updates falling leaves
        for(Leaf leaf : activeList){
            leaf.update();
        }
    }
}
