

import controller.MainController;
import util.Config;
import view.View;

/**
 * Main class of the project that handles the startup procedure.
 * @version 2016-03-05
 */

public class SpaceSnake {
	public static void main(String[] args) {
		
		//Load configuration
		String configfile = "config.txt";
		if (args.length>0)
			configfile = args[0];
		Config c = new Config(configfile);
		if (!c.fileLoaded()) 
		{
			System.out.println("could not load config file: "+configfile);
			System.exit(1); //exit with error
		}
		
		// Start the GUI:
		View view = new View();
		
		//Attach a controller to the GUI
		@SuppressWarnings("unused")
		MainController mainController = new MainController(view);
		
	}
}
