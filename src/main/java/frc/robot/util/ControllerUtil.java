package frc.robot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ControllerUtil {
    private static final BooleanSupplier FALSE = () -> false;
    
    public static boolean isPovNeutral(CommandXboxController controller) {
        return controller.getHID().getPOV() == -1;
    }

    public static boolean areABXYUp(CommandXboxController controller) {
        return !(
            controller.getHID().getAButtonPressed() &&
            controller.getHID().getBButtonPressed() &&
            controller.getHID().getXButtonPressed() &&
            controller.getHID().getYButtonPressed()
            );
    }
    public static boolean areABXYUp(CommandXboxController controller, LetterButtons... excludedButtons) {
        //Turn Excluded Buttons into List for .contains method
        List<LetterButtons> excluded = Arrays.asList(excludedButtons);
        //Stream values of LetterButtons (A, B, X, Y) for easy editing
        List<BooleanSupplier> checks = Arrays.stream(LetterButtons.values())
            //Check if the button is excluded, if so, remove it
            .filter(btn -> !excluded.contains(btn))
            //For all non-excluded buttons, add their checks to see if they're down
            .map(btn -> getButtonPressedSupplier(controller, btn))
            //Convert back to list
            .toList();
            //Turn into stream
            //turn BooleanSupplier -> Boolean by getting the value of the supplier
            //AND all of the booleans using allMatch(b -> b) to return a final boolean
            //b -> b means that if B is true, then it "matches". All match requires all bools to "match" true
        return !checks.stream().map(sup -> sup.getAsBoolean()).allMatch(b -> b);
    }
    private static BooleanSupplier getButtonPressedSupplier(CommandXboxController controller, LetterButtons button) {
        switch (button) {
            //If btn is A, return the method
            case A: return controller.getHID()::getAButtonPressed;
            case B: return controller.getHID()::getBButtonPressed;
            case X: return controller.getHID()::getXButtonPressed;
            case Y: return controller.getHID()::getYButtonPressed;
            default: return FALSE;
        }
    }

    public static enum LetterButtons {
        A,
        B,
        X,
        Y
    }
}
