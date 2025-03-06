package frc.robot.util;

import edu.wpi.first.wpilibj.XboxController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class XboxControllerBindings {
    //bindings

    //BUTTONS
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> A_DOWN = (controller) -> controller.getAButton();
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> B_DOWN = (controller) -> controller.getBButton();
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> X_DOWN = (controller) -> controller.getXButton();
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> Y_DOWN = (controller) -> controller.getYButton();
    //DPAD
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> D_PAD_UP = (controller) -> new XboxControllerDPad(controller).getDPadDirection().equals(XboxControllerDPad.Direction.UP);
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> D_PAD_DOWN = (controller) -> new XboxControllerDPad(controller).getDPadDirection().equals(XboxControllerDPad.Direction.DOWN);
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> D_PAD_LEFT = (controller) -> new XboxControllerDPad(controller).getDPadDirection().equals(XboxControllerDPad.Direction.LEFT);
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> D_PAD_RIGHT = (controller) -> new XboxControllerDPad(controller).getDPadDirection().equals(XboxControllerDPad.Direction.RIGHT);
    //BUMPERS
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> LEFT_BUMPER_DOWN = (controller) -> controller.getLeftBumperButton();
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> RIGHT_BUMPER_DOWN = (controller) -> controller.getRightBumperButton();
    //TRIGGERS
    //TODO: Test trigger values
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> LEFT_TRIGGER_DOWN = (controller) -> controller.getLeftTriggerAxis() == 0;
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> RIGHT_TRIGGER_DOWN = (controller) -> controller.getRightTriggerAxis() == 0;

    private final XboxController m_controller;
    private final List<ControllerBinding> m_bindings;

    public XboxControllerBindings(XboxController controller) {
        this.m_controller = controller;
        this.m_bindings = new LinkedList<>();
    }
    
    public void update() {
        for(ControllerBinding binding : m_bindings) {
            binding.update(m_controller);
        }
    }
    public void addBinding(Function<XboxController, Boolean> checkPressed, Consumer<XboxController> onPressed) {
        m_bindings.add(new ControllerBinding(checkPressed, onPressed));
    }





    private static class ControllerBinding {
        private Function<XboxController,Boolean> checkPressed;
        private Consumer<XboxController> onPressed;

        public ControllerBinding(Function<XboxController,Boolean> checkPressed, Consumer<XboxController> onPressed) {
            this.checkPressed = checkPressed;
            this.onPressed = onPressed;
        }

        public void update(XboxController controller) {
            if (checkPressed.apply(controller)) {
                onPressed.accept(controller);
            }
        }
    }
}
