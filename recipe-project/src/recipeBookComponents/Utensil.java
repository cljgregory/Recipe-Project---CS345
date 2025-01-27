package recipeBookComponents;

import java.io.Serializable;

/**
 * The utensil class will be used to create a new Utensil object. Along this this, it will be able
 * to make a string representation of a utensil object so that it will be able to be shown to the
 * user.
 * 
 * @author Colin Gregory
 * @version 3/ 29 / 2023
 *
 */
public class Utensil implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String name;
  private String details;

  /**
   * Constructor for a utensil.
   * 
   * @param name
   *          The name of the utensil i.e "Baking pan"
   * @param details
   *          The details of a utensil i.e "15 x 10 x 1"
   */
  public Utensil(final String name, final String details)
  {
    this.name = name;
    this.details = details;
  }

  /**
   * To String for a utensil object.
   * 
   * @return a toString representation of the name and details
   */
  @Override
  public String toString()
  {
    String returnString = String.format("%s %s", details, name);
    return returnString;

  }

  /**
   * Getter method for a utensil name.
   * 
   * @return Name of a utensil
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Getter method for the details of a utensil.
   * 
   * @return Details of a utensil
   */
  public String getDetails()
  {
    return this.details;
  }
}
