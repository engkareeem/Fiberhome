package paltel.fiber.fiberhome.testing;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.*;


public class Navigator {

    private static volatile  Map< Object,  Object > arguments = new HashMap<>();
    private static Stack<Scene> screens = new Stack<>(); // reacted with or thumb
    private static Stage currentStage = null;


    private static FXMLLoader getFXMLFile(String sceneName){
        URL filePath = Navigator.class.getResource(sceneName + ".fxml");
        FXMLLoader loader = null;
        if (filePath != null){
            loader = new FXMLLoader(filePath);
        }
        return loader;
    }
    private static void getCurrentStage(){
        List<Window> open =  Stage.getWindows().stream().filter(Window::isShowing).toList(); // getting all active stages
        currentStage = (Stage) open.toArray()[0];
    }

    public void push(){} // implement if needed

    public static void pushNamedWithArgs(String sceneName, Object... Keys_Values){

        if(Keys_Values.length == 0) return;
        arguments.clear();
        for(int i = 0; i < Keys_Values.length-1; i+=2) {
            arguments.put(Keys_Values[i], Keys_Values[i+1]);
        }

        pushNamed(sceneName);
    }
    public static void pushNamed(String sceneName)  {

        try {
            FXMLLoader loader = getFXMLFile(sceneName);
            if (loader == null) return;
            getCurrentStage();
            Scene newScene = new Scene(loader.load());
            newScene.setFill(Color.TRANSPARENT);
            screens.push(currentStage.getScene()); // saving current scene
            screens.push(newScene); // saving the new scene
            newScene.getStylesheets().addAll(currentStage.getScene().getStylesheets()); // sending all styles sheets for new scene
            currentStage.setScene(newScene);
            currentStage.show();

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void pushNamedReplacement(String sceneName){
        try {
            FXMLLoader loader = getFXMLFile(sceneName );
            if (loader == null) return;
            getCurrentStage();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(currentStage.getScene().getStylesheets()); // sending all styles sheets for new scene
            if(!screens.isEmpty() && screens.peek() == currentStage.getScene()) screens.pop(); // checks if current screen is stored in the stack
            screens.push(newScene); // saving the new scene
            currentStage.setScene(newScene);
            currentStage.show();

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void pushNamedReplacementWithArgs(String sceneName,Object... Keys_Values){

        if(Keys_Values.length == 0) return;
        arguments.clear();
        for(int i = 0; i < Keys_Values.length-1; i+=2) {
            arguments.put(Keys_Values[i], Keys_Values[i+1]);
        }

        pushNamedReplacement(sceneName);
    }
    public static void pop(){
        arguments.clear();
        if(screens.size() > 1){
            screens.pop();
            currentStage.setScene(screens.peek());
        }else {
            currentStage.close();
        }
    }

    public static void popCurrent(){
        getCurrentStage();
        if(screens.peek() != currentStage.getScene()){
            currentStage.setScene(screens.peek());
            currentStage.show();
        }else {
            pop();
        }
    }

    public static void popWithArgs(Object... Keys_Values){
        arguments.clear();
        if(screens.size() > 1){

            if(Keys_Values.length == 0) return;
            for(int i = 0; i < Keys_Values.length-1; i+=2) {
                arguments.put(Keys_Values[i], Keys_Values[i+1]);
            }

            screens.pop();
            currentStage.setScene(screens.peek());

        }else {
            currentStage.close();
        }
    }

    public static void popAll(){
        getCurrentStage();
        if(currentStage != null){
            currentStage.close();
        }
    }

    //  ====  Arguments Functions === \\

    public static Object getValue(Object key){
        if(arguments.isEmpty()) return null;
        return arguments.get(key);
    }

    public static Object[] getKeys(){
        if(arguments.isEmpty()) return null;
        return arguments.entrySet().toArray();
    }

    public static Object[] getValues(){
        if(arguments.isEmpty()) return null;
        return arguments.values().toArray();
    }

    public static Stage getStage(){
        if(currentStage == null) getCurrentStage();
        return currentStage;
    }


}
