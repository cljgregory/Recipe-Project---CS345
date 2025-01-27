package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.swing.JFileChooser;

import org.junit.jupiter.api.Test;

import recipeBookUtilities.Alphabetizer;
import recipeBookUtilities.ConvertUtil;
import recipeBookUtilities.DocumentState;
import recipeBookComponents.*;

class TestRecipeBookUtilities
{

  @Test
  public void testGetUnits()
  {
    ConvertUtil cu = new ConvertUtil(); 
    
    String[] units = ConvertUtil.getUnits();
    String[] stringUnits = {"", "Cups", "Drams", "Fluid Ounces", 
        "Gallons", "Grams", "Mililiters", "Ounces", "Pints", 
        "Pinches", "Pounds", "Tablespoons", "Teaspoons", "Quarts"};
    System.out.print(units[0]);
    
    for (int i = 0; i < stringUnits.length; i++) {
      assertEquals(units[i], stringUnits[i]);
    }
  }
  
  
  @Test
  public void testGetConversionToGrams()
  { 
    // valid types
    assertEquals(1, ConvertUtil.convertToGramsOrML(1, "Grams"), 0.01);
    assertEquals(1, ConvertUtil.convertToGramsOrML(1, "Mililiters"), 0.01);
    assertEquals(1.7718, ConvertUtil.convertToGramsOrML(1, "Drams"), 0.01);
    assertEquals(236.588, ConvertUtil.convertToGramsOrML(1, "Cups"), 0.01);
    assertEquals(473.17648, ConvertUtil.convertToGramsOrML(1, "Pints"), 0.01);
    assertEquals(28.4103, ConvertUtil.convertToGramsOrML(1, "Fluid Ounces"), 0.01); // unsure
    assertEquals(3785.4118, ConvertUtil.convertToGramsOrML(1, "Gallons"), 0.01);
    assertEquals(28.35, ConvertUtil.convertToGramsOrML(1, "Ounces"), 0.01);
    assertEquals(0.355625, ConvertUtil.convertToGramsOrML(1, "Pinches"), 0.01);
    assertEquals(453.592, ConvertUtil.convertToGramsOrML(1, "Pounds"), 0.01);
    assertEquals(473.18, ConvertUtil.convertToGramsOrML(1, "Pints"), 0.01);
    assertEquals(4.928, ConvertUtil.convertToGramsOrML(1, "Teaspoons"), 0.01);
    assertEquals(14.7867, ConvertUtil.convertToGramsOrML(1, "Tablespoons"), 0.01);
    assertEquals(946.352946, ConvertUtil.convertToGramsOrML(1, "Quarts"), 0.01);
    
    // invalid type
    assertEquals(Double.NaN, ConvertUtil.convertToGramsOrML(1, "Invalid Type"), 0.01);
    
    // valid types
    assertEquals(1, ConvertUtil.getConversionToGrams("Grams"), 0.01);
    assertEquals(1, ConvertUtil.getConversionToGrams("Mililiters"), 0.01);
    assertEquals(1.7718, ConvertUtil.getConversionToGrams("Drams"), 0.01);
    assertEquals(236.588, ConvertUtil.getConversionToGrams("Cups"), 0.01);
    assertEquals(473.176, ConvertUtil.getConversionToGrams("Pints"), 0.01);
    assertEquals(28.41, ConvertUtil.getConversionToGrams("Fluid Ounces"), 0.01);
    assertEquals(3785.411, ConvertUtil.getConversionToGrams("Gallons"), 0.01);
    assertEquals(28.3495, ConvertUtil.getConversionToGrams("Ounces"), 0.01);
    assertEquals(0.3589, ConvertUtil.getConversionToGrams("Pinches"), 0.01);
    assertEquals(453.592, ConvertUtil.getConversionToGrams("Pounds"), 0.01);
    assertEquals(473.176, ConvertUtil.getConversionToGrams("Pints"), 0.01);
    assertEquals(4.9289, ConvertUtil.getConversionToGrams("Teaspoons"), 0.01);
    assertEquals(14.786, ConvertUtil.getConversionToGrams("Tablespoons"), 0.01);
    assertEquals(946.3529, ConvertUtil.getConversionToGrams("Quarts"), 0.01);
    
    // invalid type
    assertEquals(Double.NaN, ConvertUtil.getConversionToGrams("Invalid Type"), 0.01);
  }
  
  @Test
  public void testGetConversionFromGrams()
  { 
    // valid types
    assertEquals(1, ConvertUtil.convertFromGramsOrML(1.0, "Grams"), 0.01);
    assertEquals(1, ConvertUtil.convertFromGramsOrML(1.0, "Mililiters"), 0.01);
    assertEquals(0.564, ConvertUtil.convertFromGramsOrML(1.0, "Drams"), 0.001);
    assertEquals(0.0042, ConvertUtil.convertFromGramsOrML(1.0, "Cups"), 0.001);
    assertEquals(0.00211, ConvertUtil.convertFromGramsOrML(1.0, "Pints"), 0.001);
    assertEquals(0.0351, ConvertUtil.convertFromGramsOrML(1.0, "Fluid Ounces"), 0.001); 
    assertEquals(0.792516, ConvertUtil.convertFromGramsOrML(3000.0, "Gallons"), 0.01); // unsure
    assertEquals(0.03527, ConvertUtil.convertFromGramsOrML(1.0, "Ounces"), 0.01);
    assertEquals(2.7861, ConvertUtil.convertFromGramsOrML(1.0, "Pinches"), 0.01);
    assertEquals(0.002204, ConvertUtil.convertFromGramsOrML(1.0, "Pounds"), 0.01);
    assertEquals(0.002113, ConvertUtil.convertFromGramsOrML(1.0, "Pints"), 0.01);
    assertEquals(0.2088, ConvertUtil.convertFromGramsOrML(1.0, "Teaspoons"), 0.01);
    assertEquals(0.0676, ConvertUtil.convertFromGramsOrML(1.0, "Tablespoons"), 0.01);
    assertEquals(0.001056, ConvertUtil.convertFromGramsOrML(1.0, "Quarts"), 0.01);
    
    // invalid type
    assertEquals(Double.NaN, ConvertUtil.convertFromGramsOrML(1.0, "Invalid Type"), 0.01);
    
    // NaN tests
    assertEquals(Double.NaN, ConvertUtil.convertFromGramsOrML(Double.NaN, "Invalid Type"), 0.01);
    assertEquals(Double.NaN, ConvertUtil.convertFromGramsOrML(Double.NaN, "Grams"), 0.01);
  }
  
  @Test
  public void testGetType()
  { 
    // Mass boys
    assertEquals("Mass", ConvertUtil.getType("Grams"));
    assertEquals("Mass", ConvertUtil.getType("Drams"));
    assertEquals("Mass", ConvertUtil.getType("Ounces"));
    assertEquals("Mass", ConvertUtil.getType("Pounds"));
    
    // Volume boys
    assertEquals("Volume", ConvertUtil.getType("Mililiters"));
    assertEquals("Volume", ConvertUtil.getType("Cups"));
    assertEquals("Volume", ConvertUtil.getType("Pints"));
    assertEquals("Volume", ConvertUtil.getType("Fluid Ounces"));
    assertEquals("Volume", ConvertUtil.getType("Gallons"));
    assertEquals("Volume", ConvertUtil.getType("Pinches"));
    assertEquals("Volume", ConvertUtil.getType("Pints"));
    assertEquals("Volume", ConvertUtil.getType("Teaspoons"));
    assertEquals("Volume", ConvertUtil.getType("Tablespoons"));
    assertEquals("Volume", ConvertUtil.getType("Quarts"));
    
    // Invalid
    assertEquals("Invalid", ConvertUtil.getType("Invalid"));
    
  }
  
  @Test
  public void testConvert()
  { 
    // Mass to Mass
    assertEquals(0.5643, ConvertUtil.convert(1, "Grams", "Drams", 2), 0.01);
    
    // Mass to Volume
    assertEquals(226.796, ConvertUtil.convert(1, "Pounds", "Mililiters", 2), 0.01);
    
    // Volume to Mass
    assertEquals(0.004409, ConvertUtil.convert(1, "Mililiters", "Pounds", 2), 0.01);
    
    // Volume to Volume
    assertEquals(0.0156, ConvertUtil.convert(1, "Tablespoons", "Quarts", 2), 0.01);
    
    // Density less than 1
    assertEquals(0.0156, ConvertUtil.convert(1, "Tablespoons", "Quarts", -1), 0.01);
    
    // Wrong types
    // To is wrong
    assertEquals(Double.NaN, ConvertUtil.convert(1, "PouAAAnds", "Mililiters", 2), 0.01);
    assertEquals(Double.NaN, ConvertUtil.convert(1, "MililAAAiters", "Pounds", 2), 0.01);
    // From is wrong
    assertEquals(Double.NaN, ConvertUtil.convert(1, "Pounds", "MililAAAiters", 2), 0.01);
    assertEquals(Double.NaN, ConvertUtil.convert(1, "Mililiters", "PouAAAnds", 2), 0.01);
  }

  @Test
  void testDocumentState()
  {
    assertEquals(0, DocumentState.NULL.ordinal());
    assertEquals(1, DocumentState.UNCHANGED.ordinal());
    assertEquals(2, DocumentState.CHANGED.ordinal());
  }
  
  @Test
  void testAlphabetizer()
  {
    Alphabetizer alpha = new Alphabetizer();
    
    // Ingredients
    Ingredient ingredientA = new Ingredient("Aaa", "detail", 0, "soemthing");
    Ingredient ingredientB = new Ingredient("Bbb", "detail", 0, "soemthing");
    Ingredient ingredientC = new Ingredient("Ccc", "detail", 0, "soemthing");
    assertEquals(-1, alpha.compare(ingredientA, ingredientB));
    assertEquals(1, alpha.compare(ingredientC, ingredientB));
    assertEquals(0, alpha.compare(ingredientB, ingredientB));
    
    // Utensils
    Utensil utensilA = new Utensil("Aaa", "detail");
    Utensil utensilB = new Utensil("Bbb", "detail");
    Utensil utensilC = new Utensil("Ccc", "detail");
    assertEquals(-1, alpha.compare(utensilA, utensilB));
    assertEquals(1, alpha.compare(utensilC, utensilB));
    assertEquals(0, alpha.compare(utensilB, utensilB));
    
    // Sending wrong objects
    assertEquals(-1, alpha.compare(utensilA, ingredientB));
    assertEquals(-1, alpha.compare(ingredientA, utensilB));
  }
  

}
