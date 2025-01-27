package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import recipeBookComponents.*;

class StepTest
{

  @Test
  void testConstructorAndGetters()
  {
    // set up
    String action = "cook";
    Ingredient ingredient = new Ingredient("sugar", "fried", 2.5, "pinch");
    Utensil source = new Utensil("name", "detail");
    Utensil destination = new Utensil("name", "detail");
    String detail = "this is some detail";
    double time = 3.5;
    Step step = new Step(action, ingredient, source, destination, detail, time);
    
    // test
    assertEquals(step.getAction(), action);
    assertEquals(step.getIngredient(), ingredient);
    assertEquals(step.getSourceUtensil(), source);
    assertEquals(step.getDestinationUtensil(), destination);
    assertEquals(step.getTime(), time, 0.00001);
  }
  
  @Test
  void testToStringIngredient()
  {
    Ingredient milk = new Ingredient("milk", "blah", 2.5, "bleh");
    Utensil utensil = new Utensil("milk", "blah");
    Utensil bowl = new Utensil("small bowl", "small");
    Utensil nullUtensil = null;
    Utensil saucepan = new Utensil("saucepan", "medium");
    double time = 3.5;
    
    
    // ingredient step
    Step step = new Step("put", milk, nullUtensil, bowl, "", time);
    assertEquals("Put the milk in the small bowl", step.toString());

  }
  
  @Test
  void testToStringSameUtensil()
  {
    Utensil saucepan = new Utensil("medium saucepan", "actually its huge");
    double time = 3.5;
    
    Step step = new Step("saute", null, saucepan, saucepan, "until tender but not brown", time);
    assertEquals("Saute the contents of the medium "
        + "saucepan until tender but not brown", step.toString());
  }
  
  @Test
  void testToStringDiffUtensil()
  {
    Ingredient nullIngredient = null;
    Utensil strainer = new Utensil("strainer", "");
    Utensil casserole = new Utensil("1-quart casserole", "giant");
    double time = 3.5;
    
    Step step = new Step("put", null, strainer, casserole, "", time);
    assertEquals("Put the contents of the strainer in the 1-quart casserole", step.toString());
  }
  
  @Test
  void testSetDetailsAndSetTime()
  {
    Ingredient nullIngredient = null;
    Utensil strainer = new Utensil("strainer", "");
    Utensil casserole = new Utensil("1-quart casserole", "giant");
    double time = 3.5;
    
    Step step = new Step("put", null, strainer, casserole, "", time);
    
    // test set detail
    step.setDetail("detail");
    assertEquals("detail", step.getDetail());
    
    step.setTime("20.2");
    assertEquals(20.2, step.getTime(), 0.001);
    

  }
 

}
