package frc.robot.util;

import edu.wpi.first.wpilibj.XboxController;

public class XboxControllerDPad {
    private final XboxController controller;

    public XboxControllerDPad(XboxController controller) {
        this.controller = controller;
    }

    public Direction getDPadDirection() {
        int pov = controller.getPOV();
        return switch (pov) {
            case 0 -> Direction.UP;
            case 90 -> Direction.RIGHT;
            case 180 -> Direction.DOWN;
            case 270 -> Direction.LEFT;
            default -> Direction.NOT_PRESSED;
        };
    }
    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NOT_PRESSED
    }
}