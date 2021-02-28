package Main;

import Scenery.Foreground;
import Scenery.GameElement;
import Scenery.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Handles GUI and display window
 */
public class Graphics extends Application {
    private Scene gameScene; //Scene of game
    private static StackPane gameContainer; //Container pane containg all game objects
    private static double screen_width; //Width of screen
    private static double screen_height; //Height of screen
    private ArrayList<GameElement> gameElements; //List of GameElements
    private Foreground foreground; //Foreground container for scenery

    /**
     * Starts GUI
     * @param primaryStage - ignore
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        gameContainer = new StackPane();
        gameContainer.setStyle("-fx-background-color: #222222");
        gameScene = new Scene(gameContainer, 0, 0);
        Stage stage = new Stage();
        stage.setScene(gameScene);
        stage.setFullScreen(true);
        stage.setOnCloseRequest(e -> System.exit(0));
        //Displays screen
        stage.show();
        //Retrieves width + height
        screen_width = stage.getWidth();
        screen_height = stage.getHeight();

        //Loads all GameElements
        gameElements = new ArrayList<GameElement>();
        loadGameWindow();
        //Starts a new thread which updates game loop logic
        new Thread(() -> GameRunner.loop()).start();
    }

    /**
     * Builds all necesary display components for GUI
     */
    private void loadGameWindow(){
        System.out.println("Loading Scenery");
        foreground = new Foreground();
        System.out.println("Scenery Loading finished");
        System.out.println("Loading other assets");
        Player player = new Player();
        System.out.println("Finishing startup...");
        gameElements.add(foreground);
        gameElements.add(player);
        gameContainer.getChildren().addAll(foreground.getForeground(), player.getCharacter());
        GameRunner.load(gameElements);

        //Handlers for when key is pressed
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                player.setDirection(event);
            }
        });
        //Handles key release
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                player.release(event);
            }
        });
    }

    /**
     * @return screen height
     */
    public static double getScreenHeight(){
        return screen_height;
    }

    /**
     * @return screen width
     */
    public static double getScreenWidth(){
        return screen_width;
    }

    /**
     * Calls start
     * @param args
     */
    public static void main(String[] args){
        launch();
    }
}
