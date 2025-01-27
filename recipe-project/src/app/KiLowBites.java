package app;

import java.io.IOException;

import gui.MainWindow;

/**
 * Launches the MainWindow for KILowBites.
 * 
 * @author Ethan Pae
 * @version 1.0
 */
public class KiLowBites
{
  
  /**
   * (Maybe) Temporary main project for testing GUI.
   * 
   * @param args
   *          not going to be used
   * @throws IOException
   */
  public static void main(final String args[]) throws IOException
  {
    MainWindow launch = MainWindow.getInstance();
    @SuppressWarnings("unused")
    MainWindow shouldntShow = MainWindow.getInstance();
    launch.initialize();
    launch.show();
    
    // shouldntShow.initialize();
    // shouldntShow.show();
    
    // How to set up the different language
    // Click the dropdown near the green run arrow,
    // Select run configurations
    // Click on the arguments tab
    // Copy and paste one of the following into VM arguments
      // -Duser.language=fr -Duser.country=FR
      // -Duser.language=es -Duser.country=ES

    
    
    
    

  }
}
