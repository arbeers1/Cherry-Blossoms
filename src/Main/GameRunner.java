package Main;

import Scenery.GameElement;
import javafx.application.Platform;

import java.util.ArrayList;

/**
 *  Manager for game
 */
public class GameRunner {
    private static ArrayList<GameElement> gameElements; // A list of updatable game play objects

    /**
     * Loop that updates game logic
     */
    public static void loop(){
        long startTime = System.currentTimeMillis(); //Tracks time

        while(true) {
            System.out.print(""); // Synchorizes running
            long currentTime = System.currentTimeMillis();
            //Updates all game elements every 9 ms
            if(currentTime - startTime > 9) {
                startTime = currentTime;
                for (GameElement element : gameElements) {
                    Platform.runLater(() -> element.update());
                }
            }
        }
    }

    /**
     * Sets the elements which need to update
     * @param list list of GameElements
     */
    public static void load(ArrayList<GameElement> list){
        gameElements = list;
    }

    /**
     * Starts program and GUI
     * @param args
     */
    public static void main(String[] args){
        Graphics.main(null);
    }
}
