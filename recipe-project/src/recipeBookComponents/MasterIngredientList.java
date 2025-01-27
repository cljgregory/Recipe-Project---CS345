package recipeBookComponents;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class represents a master ingredient list that can be serialized and deserialized to and
 * from a file stored in the user's current directory. The list can be modified by adding new
 * ingredients, and the changes will be persisted across different runs of the application.
 * 
 * @author Colin Gregory
 * @version 4.0
 */
public class MasterIngredientList
{

  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  // This ingredients list will auto save into the users current directory.
  private static String fileName = "ingredients.ser";
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  private List<Ingredient> ingredients;

  /**
   * Constructor: initializes the ingredients list by loading it from the file.
   */
  public MasterIngredientList()
  {
    loadIngredients();
  }

  /**
   * Adds a new ingredient to the list and saves the updated list to the file.
   *
   * @param ingredient
   *          the ingredient to add to the list
   */
  public void addIngredient(final Ingredient ingredient)
  {
    boolean isDuplicate = false;
    for (Ingredient ing : ingredients)
    {
      if (ing.getName().equalsIgnoreCase(ingredient.getName()))
      {
        isDuplicate = true;
        break;
      }
    }

    if (!isDuplicate)
    {
      ingredients.add(ingredient);
      saveIngredients();
    }
  }

  /**
   * Returns the current list of ingredients.
   *
   * @return the list of ingredients
   */
  public List<Ingredient> getIngredients()
  {
    return ingredients;
  }

  /**
   * Loads the ingredients list from the "ingredients.ser" file in the user's home directory.
   * Handles EOFException in case the file is empty or corrupted and initializes the ingredients
   * list as an empty list.
   */
  @SuppressWarnings("unchecked")
  public void loadIngredients()
  {
    File file = new File(fileName);
    if (file.exists())
    {
      try (FileInputStream fis = new FileInputStream(file);
          ObjectInputStream ois = new ObjectInputStream(fis))
      {
        ingredients = (List<Ingredient>) ois.readObject();
      }
      catch (IOException | ClassNotFoundException e)
      {
        e.printStackTrace();
      }
    }
    else // File not found, create new list
    {
      ingredients = new ArrayList<>();
    }
  }

  /**
   * Checks for if string name matches an ingredient name with calorie info.
   * 
   * @param name
   *          The name of desired ingredient
   * @return True if name matches ingredient and if calorie info is valid
   */
  public boolean hasCalorieInfo(final String name)
  {
    for (Ingredient each : ingredients)
    {
      if (each.getName().equalsIgnoreCase(name))
      {
        if (each.getHasCalInfo())
        {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Gets the calories of the ingredient in MasterIngredientList.
   * 
   * @param name
   *          The name of desired ingredient
   * @return The calories in the ingredient if they are found
   */
  public int getCalories(final String name)
  {
    int retVal = 0;
    for (Ingredient each : ingredients)
    {
      if (each.getName().equalsIgnoreCase(name))
      {
        if (each.getHasCalInfo())
        {
          retVal = (int) each.getCalsPerGram();
        }
      }
    }
    return retVal;
  }

  /**
   * Gets the density of the ingredient in g/ml in MasterIngredientList.
   * 
   * @param name
   *          The name of desired ingredient
   * @return The density of the ingredient if they are found
   */
  public double getGrams(final String name)
  {
    double retVal = 0.0;
    for (Ingredient each : ingredients)
    {
      if (each.getName().equalsIgnoreCase(name))
      {
        retVal = each.getGramsPerMl();
      }
    }
    return retVal;
  }

  /**
   * Saves the ingredients list to the "ingredients.ser" file in the user's home directory.
   */
  private void saveIngredients()
  {
    try (FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos))
    {
      oos.writeObject(ingredients);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

}
