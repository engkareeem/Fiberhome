package paltel.fiber.fiberhome.testing.utils;

import animatefx.animation.AnimationFX;
import javafx.animation.Animation;
import javafx.scene.Node;
import javafx.util.Duration;


public class Animator {

    public static void chainAnimator(AnimationFX...Animations){
        for(int i = 0; i < Animations.length - 1; i++){
            int finalI = i;
            Animations[i].setDelay(Duration.seconds(0.01));
            Animations[i].setOnFinished(actionEvent ->
                Animations[finalI +1].play()
            );
        }
       Animations[0].play(); // start the chain

    }

    public static void chainOneAnimation(AnimationFX animation, Node...Nodes){
        for(int i = 0; i < Nodes.length; i++){
            animation.setNode(Nodes[i]);

            animation.setOnFinished(actionEvent -> {

                //nextAnimation.play();
            });
        }
        //((AnimationFX) Animations[0]).play(); // start the chain

    }
}
