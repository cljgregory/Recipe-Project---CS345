package recipeBookComponents;

import java.util.ArrayList;

/**
 * Holds known ingredient calorie information.
 * @author Dylan Marti
 * @version 4/19/23
 */
public enum IngredientCalories
{
  ALCOHOL("Alcohol", 275, 0.79),
  ALMOND("Almond", 601, 0.46),
  AMERICAN_CHEESE("American cheese", 440, 0.34),
  APPLE("Apple", 44, 0.56),
  APPLE_JUICE("Apple juice", 48, 1.04),
  BANANA("Banana", 65, 0.56),
  BEAN("Bean", 130, 0.77),
  BEEF("Beef", 280, 1.05),
  BLACKBERRY("Blackberry", 25, 0.53),
  BLACK_PEPPER("Black pepper", 255, 1.01),
  BREAD("Bread", 240, 0.42),
  BROCCOLI("Broccoli", 32, 0.37),
  BROWN_SUGAR("Brown sugar", 380, 1.5),
  BUTTER("Butter", 750, 0.91),
  CABBAGE("Cabbage", 28, 0.36),
  CARROT("Carrot", 41, 0.64),
  CASHEW("Cashew", 553, 0.5),
  CAULIFLOWER("Cauliflower", 25, 0.27),
  CELERY("Celery", 14, 0.61),
  CHEDDAR_CHEESE("Cheddar cheese", 440, 0.34),
  CHERRY("Cherry", 50, 1.02),
  CHICKEN("Chicken", 200, 1.04),
  CHOCOLATE("Chocolate", 500, 1.33),
  CINNAMON("Cinnamon", 261, 0.45),
  COD("Cod", 100, 0.58),
  CORN("Corn", 130, 0.72),
  CORNFLAKE("Cornflake", 370, 0.12),
  COTTAGE_CHEESE("Cottage cheese", 98, 0.96),
  CRAB("Crab", 110, 0.61),
  CREME_DE_CACAO("Creme de cacao", 275, 0.79),
  CUCUMBER("Cucumber", 10, 0.67),
  EGG("Egg", 150, 0.6),
  FLOUR("Flour", 364, 0.45),
  GARLIC("Garlic", 111, 0.32),
  GRAPEFRUIT("Grapefruit", 32, 0.33),
  GRAPE("Grape", 62, 0.37),
  GRAPE_JUICE("Grape juice", 60, 1.04),
  GREEN_BEAN("Green bean", 31, 0.53),
  HADDOCK("Haddock", 110, 0.58),
  HAM("Ham", 240, 1.4),
  HONEY("Honey", 280, 1.5),
  ICE_CREAM("Ice cream", 180, 0.55),
  KIDNEY_BEAN("Kidney bean", 333, 0.79),
  LAMB("Lamb", 200, 1.3),
  LEMON("Lemon", 29, 0.77),
  LENTIL("Lentil", 116, 0.85),
  LETTUCE("Lettuce", 15, 0.06),
  MACARONI("Macaroni", 371, 1.31),
  MILK("Milk", 70, 1.04),
  MUSHROOM("Mushroom", 15, 1.17),
  OIL("Oil", 900, 0.88),
  OLIVE("Olive", 80, 0.65),
  ONION("Onion", 22, 0.74),
  ORANGE("Orange", 30, 0.77),
  PAPRIKA("Paprika", 282, 0.46),
  PASTA("Pasta", 371, 1.31),
  PEACH("Peach", 30, 0.61),
  PEANUT("Peanut", 567, 0.53),
  PEAR("Pear", 16, 0.61),
  PEAS("Peas", 148, 0.73),
  PEPPER("Pepper", 20, 0.51),
  PINEAPPLE("Pineapple", 40, 0.54),
  PLUM("Plum", 39, 0.58),
  PORK("Pork", 290, 0.7),
  RUM("Rum", 275, 0.79),
  SALMON("Salmon", 180, 0.58),
  SALT("Salt", 0, 1.38),
  SALTINE_CRACKERS("Saltine crackers", 421, 0.43),
  SPAGHETTI("Spaghetti", 371, 1.31),
  SPINACH("Spinach", 8, 0.08),
  STRAWBERRIES("Strawberries", 30, 0.58),
  SUGAR("Sugar", 400, 0.95),
  SWEET_POTATO("Sweet potato", 86, 0.65),
  SYRUP("Syrup", 260, 1.38),
  THYME("Thyme", 101, 0.46),
  TOMATO("Tomato", 20, 0.67),
  WINE("Wine", 83, 0.99);
  
  final String name;
  final int caloriesPer100g;
  final double gramsPerMl;
  
  /**
   * construct ingredient enum.
   * @param name
   * @param caloriesPer100g
   * @param gramsPerMl
   */
  private IngredientCalories(final String name, final int caloriesPer100g, final double gramsPerMl) 
  {
    this.name = name;
    this.caloriesPer100g = caloriesPer100g;
    this.gramsPerMl = gramsPerMl;
  }
  
  /**
   * check if calorie info available.
   * @param str ingredient name
   * @return hasInfo if available
   */
  public static Boolean hasCalorieInfo(final String str) 
  {
    String ingredient = formatInput(str);
    Boolean hasInfo = false;
    IngredientCalories[] list = IngredientCalories.values();
    
    for (int i = 0; i < list.length; i++) 
    {
      if (list[i].getName().equalsIgnoreCase(ingredient)) 
      {
        hasInfo = true;
      }
    }
    return hasInfo;
  }
  
  /**
   * gets grams per ml.
   * @param str Ingredient name passed in
   * @return hasInfo if available
   */
  public static double getGrams(final String str) 
  {
    String ingredient = formatInput(str);
    Double grams = 0.0;
    IngredientCalories[] list = IngredientCalories.values();
    for (int i = 0; i < list.length; i++) 
    {
      if (list[i].getName().equals(ingredient)) 
      {
        grams = list[i].getGramsPerMl();
      }
    }
    return grams;
  }
  
  
  /**
   * gets grams per ml.
   * @param str the passed Ingredient sent in
   * @return hasInfo if available
   */
  public static int getCalories(final String str) 
  {
    String ingredient = formatInput(str);
    int cals = 0;
    IngredientCalories[] list = IngredientCalories.values();
    for (int i = 0; i < list.length; i++) 
    {
      if (list[i].getName().equals(ingredient)) 
      {
        cals = list[i].getCaloriesPer100Grams();
      }
    }
    return cals;
  }
  
  
  /**
   * format the input str to ignore case.
   * @param str the name of the Ingredient passed in
   * @return string
   */
  public static String formatInput(final String str) 
  {
    String ingredient = str.toLowerCase();
    ingredient = ingredient.substring(0, 1).toUpperCase() + ingredient.substring(1);
    return ingredient;
  }
  
  
  /**
   * get the calories/100g of the ingredient enum.
   * @return calories int
   */
  public int getCaloriesPer100Grams() 
  {
    return this.caloriesPer100g;
  }

  
  /**
   * get the grams/ml of the ingredient enum.
   * @return grams double
   */
  public double getGramsPerMl() 
  {
    return this.gramsPerMl;
  }

  /**
   * Gets the ingredients in the enum.
   * @return the ingredients in the enum.
   */
  public static IngredientCalories[] getIngredients()
  {
    return IngredientCalories.values();
  }
  
  /**
   * Gets the ingredients in the enum casted to the Ingredient class.
   * @return the ingredients in the enum.
   */
  public static ArrayList<Ingredient> getIngredientsToIngredients()
  {
    ArrayList<Ingredient> ingredList = new ArrayList<Ingredient>();
    for ( IngredientCalories each : IngredientCalories.values() )
    {
      Ingredient converted = new Ingredient(each.getName(), "FromIngredientCalories", 0.0, "NA");
      converted.setGramsPerMl(each.getGramsPerMl());
      ingredList.add(converted);
    }
    return ingredList;
  }
  
  /**
   * Gets the ingredients in the enum converted to string.
   * @return the ingredients in the enum converted to string.
   */
  public static String[] getIngredientsToString()
  {
    IngredientCalories values[] = getIngredients();
    String valToString[] = new String[values.length];
    for (int i = 0; i < values.length; i++)
    {
      valToString[i] = values[i].getName();
    }
    return valToString;
  }
  
  /**
   * get the name of the ingredient enum.
   * @return name
   */
  public String getName() 
  {
    return this.name;
  }
}
 
