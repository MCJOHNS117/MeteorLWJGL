package Application;

import static org.lwjgl.system.MemoryUtil.*;

public class App {
	public static void main(String[] args) throws Exception {
		Display display = new Display(1280, 720, "Hello LWJGL", NULL, NULL);
		display.initialize();
		display.run();
	}
}
