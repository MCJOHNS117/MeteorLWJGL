package Application;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46C.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Display {
    private long WINDOW_HANDLE;

    private int windowWidth;
    private int windowHeight;
    private String windowName;
    private long monitorHandle;
    private long shareHandle;

    public Display(int width, int height, String windowName, long monitor, long share) {
        this.windowWidth = width;
        this.windowHeight = height;
        this.windowName = windowName;
        this.monitorHandle = monitor;
        this.shareHandle = share;
    }

    public void initialize() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW.");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        WINDOW_HANDLE = glfwCreateWindow(windowWidth, windowHeight, windowName, monitorHandle, shareHandle);
        if (WINDOW_HANDLE == NULL)
            throw new RuntimeException("Failed to create GLFW window.");

        glfwSetKeyCallback(WINDOW_HANDLE, (window, key, scancode, action, mods) -> {
            Input.KeyCallback(window, key, scancode, action, mods);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(WINDOW_HANDLE, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(WINDOW_HANDLE, (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2);
        }

        glfwMakeContextCurrent(WINDOW_HANDLE);
        glfwSwapInterval(1);
        glfwShowWindow(WINDOW_HANDLE);
    }

    public void run() {
        GL.createCapabilities();

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(WINDOW_HANDLE)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(WINDOW_HANDLE);

            glfwPollEvents();
        }

        dispose();
    }

    public long getHandle() {
        return WINDOW_HANDLE;
    }

    public void exit() {

    }

    private void dispose() {
        glfwFreeCallbacks(WINDOW_HANDLE);
        glfwDestroyWindow(WINDOW_HANDLE);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}