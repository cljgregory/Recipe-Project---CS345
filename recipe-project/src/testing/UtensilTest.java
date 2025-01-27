package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import recipeBookComponents.Utensil;

class UtensilTest
{

  
  @Test
  void testGetters()
  {
    
    Utensil utensil = new Utensil("Spoon", "Wooden");
    
    assertEquals("Spoon", utensil.getName());
    assertEquals("Wooden", utensil.getDetails());
    
  }
  
  @Test 
  void testToString() {
    
    Utensil utensil2 = new Utensil("Baking Sheet", "Ungreased");
    assertEquals("Ungreased Baking Sheet", utensil2.toString());
  }

}
