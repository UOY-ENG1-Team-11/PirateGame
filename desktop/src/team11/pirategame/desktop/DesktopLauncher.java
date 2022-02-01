package team11.pirategame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.Files;
import team11.pirategame.PirateGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pirate Game";
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = true;
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new PirateGame(), config);
	}
}
