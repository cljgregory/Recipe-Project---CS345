package recipeBookComponents;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import recipeBookUtilities.Alphabetizer;
import recipeBookUtilities.ConvertUtil;

/**
 * 
 * Recipe stores name, servings, and arraylists of ingredients, utensils, and steps. Corresponds
 * #1915 Recipe Story in Information Representation Epic
 * 
 * @author Dylan Marti
 * @version 3/28/23
 */
public class Recipe implements Serializable
{

  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  private static final long serialVersionUID = 1L;
  private static final String INGREDIENTS = "ingredients";
  private static final String UTENSILS = "utensils";
  // Were public but set to private for CheckStyle reasons
  private ArrayList<Ingredient> ingredients;
  private ArrayList<Utensil> utensils;
  private ArrayList<Step> steps;
  // ****

  private PropertyChangeSupport support = new PropertyChangeSupport(this);
  private String name;
  private int servings;

  /**
   * Constructor for Recipe, initializes the ingredient, utensils, and steps ArrayList.
   * 
   * @param name
   *          of the recipe.
   * @param servings
   *          the serving size of the recipe.
   **/
  public Recipe(final String name, final int servings)
  {
    this.name = name;
    this.servings = servings;
    this.ingredients = new ArrayList<Ingredient>();
    this.utensils = new ArrayList<Utensil>();
    this.steps = new ArrayList<Step>();

    System.out.println(this.name);
  }

  /**
   * getter for recipe name.
   * 
   * @return name
   */
  public String getName()
  {
    if (name == ConvertUtil.BLANK)
    {
      return ConvertUtil.BLANK;
    }
    return name;
  }

  /**
   * getter for servings.
   * 
   * @return servings
   */
  public int getServings()
  {
    return servings;
  }

  /**
   * getter for ingredients arraylist.
   * 
   * @return ingredient arraylist
   */
  public ArrayList<Ingredient> getIngredients()
  {
    return ingredients;
  }

  /**
   * getter for ingredients names String arraylist.
   * 
   * @return ingredient names arraylist
   */

  public ArrayList<String> getIngredientNames()
  {
    ArrayList<String> names = new ArrayList<String>();
    for (int i = 0; i < this.ingredients.size(); i++)
    {
      names.add(ingredients.get(i).getName());
    }
    return names;
  }

  /**
   * getter for utensils arraylist.
   * 
   * @return utensils arraylist
   */
  public ArrayList<Utensil> getUtensils()
  {
    return utensils;
  }

  /**
   * getter for utensil names String arraylist.
   * 
   * @return utensil names arraylist
   */

  public ArrayList<String> getUtensilNames()
  {
    ArrayList<String> names = new ArrayList<String>();
    for (int i = 0; i < this.utensils.size(); i++)
    {
      names.add(utensils.get(i).getName());
    }
    return names;
  }

  /**
   * getter for steps arraylist.
   * 
   * @return steps
   */
  public ArrayList<Step> getSteps()
  {
    return steps;
  }

  /**
   * add ingredient to recipe, no duplicates allowed.
   * 
   * @param ingredient
   *          to be added
   * @return boolean true if added
   */
  public boolean addIngredient(final Ingredient ingredient)
  {
    for (Ingredient inIngredient : ingredients)
    {
      if (ingredient.getName().equalsIgnoreCase(inIngredient.getName()))
        return false;
    }
    ArrayList<Ingredient> oldIngredients = ingredients;
    this.ingredients.add(ingredient);
    // use alphabetizer (which implements comparator) to sort alphabetically
    Collections.sort(ingredients, new Alphabetizer());
    firePropertyChange(INGREDIENTS, oldIngredients, ingredient);
    return true;

  }

  /**
   * add step to recipe, no duplicates allowed.
   * 
   * @param step
   *          the step to be added
   * @return boolean true if added
   */
  public boolean addStep(final Step step)
  {
    for (Step inStep : steps)
    {
      if (step.equals(inStep))
        return false;
    }
    this.steps.add(step);
    return true;
    // TODO add time order method
  }

  /**
   * add utensil to recipe, no duplicates allowed.
   * 
   * @param utensil
   *          to be added
   * @return boolean true if added
   */
  public boolean addUtensil(final Utensil utensil)
  {
    if (!utensils.contains(utensil))
    {
      utensils.add(utensil);
      // use alphabetizer (which implements comparator) to sort alphabetically
      Collections.sort(utensils, new Alphabetizer());
      ArrayList<Utensil> oldUtensils = utensils;
      firePropertyChange(UTENSILS, oldUtensils, utensil);
      return true;
    }
    return false;
  }

  /**
   * remove ingredient to recipe.
   * 
   * @param ingredient
   *          to be added
   * @return boolean, true if removed
   */
  public boolean removeIngredient(final Ingredient ingredient)
  {
    ArrayList<Ingredient> oldIngredients = ingredients;
    boolean result = this.ingredients.remove(ingredient);
    firePropertyChange(INGREDIENTS, oldIngredients, ingredient);
    return result;
  }

  /**
   * Gets an ingredient by searching for a matching name to the String passed in. If not found,
   * returns null.
   * 
   * @param ingredName
   *          the ingredient name for the desired ingredient
   * @return The corresponding ingredient if found
   */
  public Ingredient getIngredientsFromName(final String ingredName)
  {
    for (Ingredient ingred : ingredients) // Every ingredient
    {
      if (ingredName.equalsIgnoreCase(ingred.getName()))
      {
        return ingred; // Ingredient found
      }
    }
    // Ingredient not found
    return null;
  }

  /**
   * remove step to recipe.
   * 
   * @param step
   *          to be removed
   * @return boolean, true if removed
   */
  public boolean removeStep(final Step step)
  {
    return this.steps.remove(step);
  }

  /**
   * remove utensil to recipe.
   * 
   * @param utensil
   *          to be removed
   * @return boolean, true if removed
   */
  public boolean removeUtensil(final Utensil utensil)
  {
    ArrayList<Utensil> oldUtensils = utensils;
    boolean result = this.utensils.remove(utensil);
    firePropertyChange(UTENSILS, oldUtensils, utensil);
    return result;
  }

  /**
   * Creates a propertyChangeListener for Recipe objects.
   * 
   * @param listener
   *          actively listens for changes.
   */
  public void addPropertyChangeListener(final PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  /**
   * Removes a propertyChangeListener for Recipe objects.
   * 
   * @param listener
   *          the listener that is to be removed.
   */
  public void removePropertyChangeListener(final PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  /**
   * Sets off the propertyChangeListener when called.
   * 
   * @param propertyName
   *          identification for property changed
   * @param oldValue
   *          for comparing with new value
   * @param newValue
   *          for comparing with old value
   */
  public void firePropertyChange(final String propertyName, final Object oldValue,
      final Object newValue)
  {
    support.firePropertyChange(propertyName, oldValue, newValue);
  }

  /**
   * Sets the variable name to the value of the String passed in.
   * 
   * @param recipeName
   *          the String to set the value of name
   */
  public void setName(final String recipeName)
  {
    // TODO Auto-generated method stub
    this.name = recipeName;

  }

  /**
   * Sets the variable servings to the value of the Int passed in.
   * 
   * @param recipeServes
   *          the value to modify the value of servings
   */
  public void setServings(final int recipeServes)
  {
    // TODO Auto-generated method stub
    this.servings = recipeServes;

  }

  /**
   * Clears the ustensil list.
   */
  public void clearUtensils()
  {
    this.utensils.clear();
  }

  /**
   * Clears the ingredients list.
   */
  public void clearIngredients()
  {
    this.ingredients.clear();
  }

  /**
   * Clears the steps list.
   */
  public void clearSteps()
  {
    this.steps.clear();
  }

  /**
   * Clears the name variable.
   */
  public void clearName()
  {
    this.name = "";
  }

}
