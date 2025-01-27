package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import recipeBookComponents.Meal;

/**
 * Saves meal information to a file and also allows for opening files.
 * 
 * @author Colin Gregory
 * @version 4.0
 *
 */
public class MealSerializer
{
  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  
  private static final String MEAL_FILES = "MEAL_FILES";
  private static final String MEL_PARENTHESIS = " (*.mel)";
  private static final String MEL = "mel";
  
  private static String currentFilePath;

  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  /**
   * Serializes a Meal object and saves it to a file.
   *
   * @param meal
   *          the Meal object to be serialized and saved
   * @throws IOException
   *           if there is an error writing to the file
   */
  public static void serializeMeal(final Meal meal) throws IOException
  {
    // Creates a file save dialog and saves the serialized Meal object to a file.
    String filePath = showSaveDialog(meal.getName() + ".mel");
    if (filePath != null)
    {
      currentFilePath = filePath; // Update currentFilePath with the selected file path
      FileOutputStream fileOut = new FileOutputStream(currentFilePath);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(meal);
      out.close();
      fileOut.close();
    }
  }

  /**
   * Deserializes a Meal object from a file.
   *
   * @return the deserialized Meal object
   * @throws IOException
   *           if there is an error reading the file
   * @throws ClassNotFoundException
   *           if the Meal class is not found
   * @throws FileNotFoundException
   *           if no file was selected
   */
  public static Meal deserializeMeal() throws IOException, ClassNotFoundException
  {
    // If no file path is currently set, shows a file open dialog.
    if (currentFilePath == null)
    {
      currentFilePath = showOpenDialog();
    }

    // If a file path is set, deserializes the Meal object from the file.
    if (currentFilePath != null)
    {
      FileInputStream fileIn = new FileInputStream(currentFilePath);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      Meal meal = (Meal) in.readObject();
      in.close();
      fileIn.close();
      return meal;
    }
    // If no file was selected, throws a FileNotFoundException.
    throw new FileNotFoundException(STRINGS.getString("NO_MEAL"));
  }

  /**
   * Re-serializes a Meal object and saves it to the same file that was previously opened or saved.
   *
   * @param meal
   *          the Meal object to be re-serialized and saved
   * @throws IOException
   *           if there is an error writing to the file
   */
  public static void reSerializeMeal(final Meal meal) throws IOException
  {
    // If a file path is set, re-serializes the Meal object and saves it to the same file.
    if (currentFilePath != null)
    {
      FileOutputStream fileOut = new FileOutputStream(currentFilePath);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(meal);
      out.close();
      fileOut.close();
    }
  }

  /**
   * Shows a file save dialog and returns the absolute path of the selected file.
   *
   * @param defaultFileName
   *          the default name of the file to be saved
   * @return the absolute path of the selected file or null if no file was selected
   */
  private static String showSaveDialog(final String defaultFileName)
  {
    // Creates a new file chooser object.
    JFileChooser fileChooser = new JFileChooser();
    // Sets the default file name for the save dialog.
    fileChooser.setSelectedFile(new File(defaultFileName));
    // Sets the file filter to display only .mel files.
    fileChooser.setFileFilter(
        new FileNameExtensionFilter(STRINGS.getString(MEAL_FILES) + MEL_PARENTHESIS, MEL));

    int result = fileChooser.showSaveDialog(null);
    // Shows the save dialog and returns the selected file path.
    if (result == JFileChooser.APPROVE_OPTION)
    {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    return null;
  }

  /**
   * Shows a file open dialog and returns the absolute path of the selected file.
   *
   * @return the absolute path of the selected file or null if no file was selected
   */
  public static String showOpenDialog()
  {
    // Creates a new file chooser object.
    JFileChooser fileChooser = new JFileChooser();
    // Sets the file filter to display only .mel files.
    fileChooser.setFileFilter(
        new FileNameExtensionFilter(STRINGS.getString(MEAL_FILES) + MEL_PARENTHESIS, MEL));

    // Shows the open dialog and returns the selected file path.
    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION)
    {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    // Returns null if no file was selected.
    return null;
  }

  /**
   * Returns the file path of the last saved or opened file.
   *
   * @return the file path of the last saved or opened file
   */
  public static String getCurrentFilePath()
  {
    return currentFilePath;
  }

  /**
   * Resets the file path of the last saved or opened file to null.
   */
  public static void resetFilePath()
  {
    currentFilePath = null;
  }
}
