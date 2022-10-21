package Application;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    public static void KeyCallback(long windowHandle, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(windowHandle, true);
    }
}
