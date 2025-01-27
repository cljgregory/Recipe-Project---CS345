/**
 * This class provides methods for serializing, deserializing, and re-serializing Recipe objects.
 */
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

import recipeBookComponents.Recipe;

/**
 * Serializes recipes for file IO of recipe variables.
 * 
 * @author Colin Gregory
 * @version 4.0
 *
 */
public class Serializer
{
  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();

  private static final String RECIPE_FILES = "RECIPE_FILES";
  private static final String RCP_PARENTHESIS = " (*.rcp)";
  private static final String RCP = "rcp";

  /**
   * The file path of the last saved or opened file.
   */
  private static String currentFilePath;

  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  /**
   * Serializes the given recipe object to a file selected by the user.
   *
   * @param recipe
   *          the recipe object to be serialized
   * @throws IOException
   *           if an error occurs while writing to the file
   */
  public static void serializeRecipe(final Recipe recipe) throws IOException
  {
    // Shows a file save dialog and returns the absolute path of the selected file.
    String filePath = showSaveDialog(recipe.getName() + ".rcp");
    if (filePath != null)
    {
      // Updates the current file path with the selected file path.
      currentFilePath = filePath;
      // Creates a new output stream to write objects to the specified file.
      FileOutputStream fileOut = new FileOutputStream(currentFilePath);
      // Writes the recipe object to the output stream.
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(recipe);
      // Closes the output stream.
      out.close();
      // Closes the file output stream.
      fileOut.close();
    }
  }

  /**
   * Deserializes a recipe object from the last saved or opened file.
   *
   * @return the deserialized recipe object
   * @throws IOException
   *           if an error occurs while reading from the file
   * @throws ClassNotFoundException
   *           if the class of the serialized object cannot be found
   * @throws FileNotFoundException
   *           if no file was selected for opening
   */
  public static Recipe deserializeRecipe() throws IOException, ClassNotFoundException
  {
    // Shows a file open dialog and returns the absolute path of the selected file.
    if (currentFilePath == null)
    {
      currentFilePath = showOpenDialog();
    }

    if (currentFilePath != null)
    {
      // Creates a new input stream to read objects from the specified file.
      FileInputStream fileIn = new FileInputStream(currentFilePath);
      // Reads the recipe object from the input stream.
      ObjectInputStream in = new ObjectInputStream(fileIn);
      Recipe recipe = (Recipe) in.readObject();
      // Closes the input stream.
      in.close();
      // Closes the file input stream.
      fileIn.close();
      // Returns the deserialized recipe object.
      return recipe;
    }
    // Throws a FileNotFoundException if no file was selected for opening.
    throw new FileNotFoundException(STRINGS.getString("NO_RECIPE"));
  }

  /**
   * Re-serializes the given recipe object to the last saved or opened file.
   *
   * @param recipe
   *          the recipe object to be re-serialized
   * @throws IOException
   *           if an error occurs while writing to the file
   */
  public static void reSerializeRecipe(final Recipe recipe) throws IOException
  {
    if (currentFilePath != null)
    {
      // Creates a new output stream to write objects to the specified file.
      FileOutputStream fileOut = new FileOutputStream(currentFilePath);
      // Writes the recipe object to the output stream.
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(recipe);
      // Closes the output stream.
      out.close();
      // Closes the file output stream.
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
    // Sets the file filter to display only .rcp files.
    fileChooser.setFileFilter(
        new FileNameExtensionFilter(STRINGS.getString(RECIPE_FILES) + RCP_PARENTHESIS, RCP));

    // Shows the save dialog and returns the selected file path.
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.APPROVE_OPTION)
    {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    // Returns null if no file was selected.
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
    // Sets the file filter to display only .rcp files.
    fileChooser.setFileFilter(
        new FileNameExtensionFilter(STRINGS.getString(RECIPE_FILES) + RCP_PARENTHESIS, RCP));

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
