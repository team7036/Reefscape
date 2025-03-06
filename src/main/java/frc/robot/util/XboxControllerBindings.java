package frc.robot.util;

import edu.wpi.first.wpilibj.XboxController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class XboxControllerBindings {
    //bindings
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> A_DOWN = (controller) -> controller.getAButton();
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> B_DOWN = (controller) -> controller.getBButton();
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> X_DOWN = (controller) -> controller.getXButton();
    @SuppressWarnings({"unused", "LambdaBodyCanBeCodeBlock"})
    public static final Function<XboxController, Boolean> Y_DOWN = (controller) -> controller.getYButton();

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
    public void addBinding(Function<XboxController, Boolean> checkPressed, Runnable onPressed) {
        m_bindings.add(new ControllerBinding(checkPressed, onPressed));
    }





    private static class ControllerBinding {
        private Function<XboxController,Boolean> checkPressed;
        private Runnable onPressed;

        public ControllerBinding(Function<XboxController,Boolean> checkPressed, Runnable onPressed) {
            this.checkPressed = checkPressed;
            this.onPressed = onPressed;
        }

        public void update(XboxController controller) {
            if (checkPressed.apply(controller)) {
                onPressed.run();
            }
        }
    }
}
