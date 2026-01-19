package pharmacie.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();

    private SceneManager() {
    }

    public static synchronized SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchScene(String name, Scene scene) {
        scenes.put(name, scene);
        if (primaryStage != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
