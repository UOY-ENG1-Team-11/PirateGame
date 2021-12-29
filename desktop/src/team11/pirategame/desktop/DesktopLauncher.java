package team11.pirategame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import team11.pirategame.PirateGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pirate Game";
		//if these values are changed, change in main class too
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new PirateGame(), config);
	}
}
