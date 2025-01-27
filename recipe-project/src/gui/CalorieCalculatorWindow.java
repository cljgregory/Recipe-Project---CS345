package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import recipeBookComponents.Ingredient;
import recipeBookComponents.IngredientCalories;
import recipeBookComponents.MasterIngredientList;
import recipeBookComponents.Meal;
import recipeBookComponents.Recipe;
import recipeBookUtilities.ConvertUtil;
import utilities.MealSerializer;
import utilities.Serializer;

/**
 * Calculates the calories in a recipe or meal.
 * 
 * @author Dylan Marti & Ethan Pae
 * @version 4.0
 *
 */
public class CalorieCalculatorWindow

{
  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  // --==STRING CONSTANTS==--
  private static final String INVALINPUT = STRINGS.getString("INVALINPUT");
  private static final String ERRORAMNT = STRINGS.getString("ERRORAMNT");
  private static final String ERRORUNIT = STRINGS.getString("ERRORUNIT");
  private static final String MEALS = "MEAL";
  private static final String RECIPE = "RECIPE";
  private static final String CALS = "CALS";
  private static final String CALBAD = "BAD_CALS";
  private static final String THREE_OUTPUT = "%s: %s (%s)";
  private static final String CAL_OUTPUT = "%4.0f";
  private static final String TWO_OUTPUT = "%s %s";
  private static final String MOD_OUTPUT = "%s%s";
  private static final Dimension TXT_BOX_STD = new Dimension(100, 25);
  // --==####################==--

  // --==OTHER VARIABLES==--
  private static CalorieCalculatorWindow instance; // Singleton
  private Meal meal;
  private Recipe recipe;
  private JFrame frame;
  private JPanel upperFormat;
  private JPanel lowerFormat;
  private JPanel unitConverter;
  private JPanel buttonPanel;
  private JPanel buttonFrame;
  private JComboBox<String> mealDrop;
  private JComboBox<String> recipeDrop;
  private JComboBox<String> unitDrop; // List of units
  private JComboBox<String> ingredientDrop; // List of ingredients

  // Recipe and Meal Integration
  private DefaultListModel<String> validCalList;
  private DefaultListModel<String> noCalList;
  private JTextField totalCals;

  private JTextField inputTextField; // Amount

  private JTextField outputTextField; // Calories

  private JButton calorieCalc;
  private JButton resetItems;
  private JButton getFile;
  private JButton refreshIngredients;

  private JButton calcRec;

  private ActionListener calcCalActionForIngred;

  private ActionListener resetActionForIngred;

  // private ActionListener calcMealWindow;
  // private ActionListener calcRecipeWindow;
  // private ActionListener calcIngredWindow;

  private ActionListener getCalsForRecMeal;
  // --==###############==--

  /**
   * Constructor for CalorieCalculator window.
   * 
   */
  private CalorieCalculatorWindow()
  {
    try
    {
      createAndShowGUI();
    }
    catch (ClassNotFoundException | IOException e)
    {
      e.printStackTrace();
    }
    show();
  }

  /**
   * Displays calorieCalculator window.
   */
  private void show()
  {
    frame.setVisible(true);
  }

  /**
   * Ensures only one window of CalorieCalculatorWindow is displayed.
   * 
   * @return the CalorieCalculatorWindow window
   */
  public static CalorieCalculatorWindow getInstance()
  {
    if (instance == null) // if there hasn't been an instance
    {
      instance = new CalorieCalculatorWindow();
    }
    return instance;
  }

  private void createAndShowGUI() throws FileNotFoundException, ClassNotFoundException, IOException
  {
    // --==JCOMPONENT VARIABLE DECLARATION==--
    frame = new JFrame(STRINGS.getString("CALORIE_CALC"));
    upperFormat = new JPanel();
    lowerFormat = new JPanel();
    unitConverter = new JPanel();
    buttonPanel = new JPanel();
    buttonFrame = new JPanel();
    recipeDrop = new JComboBox<String>();
    mealDrop = new JComboBox<String>(); // List of units
    unitDrop = new JComboBox<>(ConvertUtil.getUnits()); // List of units
    ingredientDrop = new JComboBox<>(); // List of ingredients
    inputTextField = new JTextField(5); // Amount
    outputTextField = new JTextField(25); // Calories
    calorieCalc = new JButton();
    resetItems = new JButton();
    getFile = new JButton(); // Ingredient, Recipe, Meal
    calcRec = new JButton();
    refreshIngredients = new JButton();

    calorieCalc.setEnabled(false);
    mealDrop.setEnabled(false);
    calcRec.setEnabled(false);
    // --==###############################==--

    // --==#############ACTIONS############==--
    getCalsForRecMeal = openFile();
    calcCalActionForIngred = calculateCalorieAction(); // Functionality for GUI
    resetActionForIngred = GuiHelp.resetAction(inputTextField, outputTextField, ingredientDrop,
        unitDrop, null, true, true, false, getFile);
    // --==###############################==--

    ActionListener refreshAction = refreshInternals();

    // --==LAYOUT DECLARATION==--
    frame.getContentPane().setLayout(new BorderLayout());
    GridBagLayout upperGridBagLayout = new GridBagLayout();
    GridBagLayout lowerGridBagLayout = new GridBagLayout();
    upperFormat.setLayout(upperGridBagLayout);
    lowerFormat.setLayout(lowerGridBagLayout);
    unitConverter.setLayout(new BoxLayout(unitConverter, BoxLayout.Y_AXIS));
    buttonFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
    GuiHelp.makeTopButtonComponents(buttonFrame, buttonPanel);
    // --==##################==--

    // --==ADD INGREDIENTS==--
    updateIngredientsList(ingredientDrop); // Add ingredients to list
    // --==###############==--

    // --==TEXTFIELD & DROP RULES==--
    inputTextField.addActionListener(calcCalActionForIngred); // Reads user input for conversion
    outputTextField.setEditable(false); // Can't tweak output
    outputTextField.setText("_________"); // goes away after calculation
    outputTextField.setBorder(null); // Conforming to IDD
    // --==######################==--

    // --==CONSTRUCT FRAME==--
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(690, 240);
    frame.setResizable(false);
    frame.setMinimumSize(new Dimension(690, 240)); // Set minimum width
    frame.setMaximumSize(new Dimension(Short.MAX_VALUE, 240)); // Set maximum width and fixed height
    frame.addKeyListener(GuiHelp.addHotKey(KeyEvent.VK_ENTER, calcCalActionForIngred));
    frame.setFocusable(true);
    frame.addWindowFocusListener(GuiHelp.keepTextSelected(frame, inputTextField));
    frame.addWindowListener(whenWindowCloses());

    frame.add(buttonFrame, BorderLayout.NORTH);
    unitConverter.add(upperFormat);
    unitConverter.add(lowerFormat);
    frame.add(unitConverter, BorderLayout.CENTER);
    // --==###############==--

    // --==RESIZE==--
    inputTextField.setMaximumSize(TXT_BOX_STD);
    inputTextField.setPreferredSize(TXT_BOX_STD);
    // --==######==--

    // --==DISABLE FOCUSING==--
    // This sacrifices keybindings for navigating dropdowns
    // in order to have a consistent listening for when the
    // user hits the key to calculate the calories.
    recipeDrop.setEnabled(false);
    ingredientDrop.setFocusable(false);
    unitDrop.setFocusable(false);
    // --==################==--

    // --==ADD TOP ROW==--
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5, 0, 5, 5);
    c.anchor = GridBagConstraints.WEST;

    c.gridx = 0;
    c.gridy = 0;
    upperFormat.add(new JLabel(STRINGS.getString("INGRED_COLON")), c);
    c.gridx = 1;
    c.gridy = 0;
    c.insets = new Insets(5, 10, 5, 5);
    upperFormat.add(ingredientDrop, c);
    c.insets = new Insets(5, 15, 5, 5);
    c.gridx = 2;
    c.gridy = 0;
    upperFormat.add(new JLabel(STRINGS.getString("AMOUNT_COLON")), c);
    c.gridx = 3;
    c.gridy = 0;
    c.insets = new Insets(5, 0, 5, 15);
    upperFormat.add(inputTextField, c);
    c.gridx = 4;
    c.gridy = 0;
    upperFormat.add(new JLabel(STRINGS.getString("UNIT_COLON")), c);
    c.gridx = 5;
    c.gridy = 0;
    upperFormat.add(unitDrop, c);
    c.insets = new Insets(5, 0, 5, 5);
    // ---==#########==---

    // --==ADD OUTPUT==--

    c.gridx = 0;
    c.gridy = 0;
    lowerFormat.add(new JLabel(STRINGS.getString("CALS_COLON")), c);
    c.gridx = 1;
    c.gridy = 0;
    c.insets = new Insets(5, 2, 5, 180);
    lowerFormat.add(outputTextField, c);
    // ---==#########==--

    // --==ADD OUTPUT TEXT==--
    // lowerFormat.add(GuiHelp.spacingX(20)); // Separate from frame borders
    // GuiHelp.addLabelAndDB(lowerFormat, outputTextField, STRINGS.getString("CALS_COLON"), 35, 50);
    // ---==#############==---

    // --==CONSTRUCT FRAME==--
    GuiHelp.addButtonToTop(buttonFrame, buttonPanel, calorieCalc, "calculate2x.png",
        "Run Calculations", calcCalActionForIngred);
    GuiHelp.addButtonToTop(buttonFrame, buttonPanel, resetItems, "restart2x.png",
        STRINGS.getString("RESET_ENTRIES_TT"), resetActionForIngred);
    GuiHelp.addButtonToTop(buttonFrame, buttonPanel, getFile, "meal2x.png",
        STRINGS.getString("OPEN_CCW"), getCalsForRecMeal);
    GuiHelp.addButtonToTop(buttonFrame, buttonPanel, refreshIngredients, "refresh2x.png",
        STRINGS.getString("REFRESH_INGRED"), refreshAction);
    // GuiHelp.addButtonToTop(buttonFrame, buttonPanel, calcIngred, "ingred2x.png",
    // "Calculate Ingredient", calcIngredWindow);
    // GuiHelp.addButtonToTop(buttonFrame, buttonPanel, calcRec, "recipe2x.png",
    // "Calculate Whole Recipe", calcRecipeWindow);
    // GuiHelp.addButtonToTop(buttonFrame, buttonPanel, calcMeal, "meal2x.png",
    // STRINGS.getString("CAL_MEAL_TT"), calcMealWindow);
    inputTextField.getDocument().addDocumentListener(checkAction(inputTextField));
    ingredientDrop.addActionListener(checkForChange(inputTextField));
    unitDrop.addActionListener(checkForChange(inputTextField));
    unitConverter.add(upperFormat);
    unitConverter.add(lowerFormat);
    frame.add(buttonFrame, BorderLayout.NORTH);
    frame.add(unitConverter, BorderLayout.CENTER);
    // --==###############==--
  }

  /**
   * Creates an ActionListener for the calculate action.
   * 
   * @return An ActionListener that handles the calculate action.
   */
  private ActionListener calculateCalorieAction()
  {
    return new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {
          double calorieValue;
          Ingredient ingred = null;
          String ingredientSelected = ingredientDrop.getSelectedItem().toString();
          MasterIngredientList userIngreds = new MasterIngredientList();
          ArrayList<Ingredient> setIngreds = IngredientCalories.getIngredientsToIngredients();
          for (Ingredient eachIngredient : setIngreds)
          {
            if (ingredientSelected.equalsIgnoreCase(eachIngredient.getName()))
            {
              ingred = eachIngredient;
              break;
            }
          }
          for (Ingredient eachIngredient : userIngreds.getIngredients())
          {
            if (ingredientSelected.equalsIgnoreCase(eachIngredient.getName()))
            {
              ingred = eachIngredient;
              break;
            }
          }
          ingred.setUnit(unitDrop.getSelectedItem().toString());
          calorieValue = calcIngredCals(ingred, Double.parseDouble(inputTextField.getText()));

          // Invalid Amount entry
          if (GuiHelp.inputCheck(calorieValue < 0, frame, ERRORAMNT, INVALINPUT))
            return;

          // Invalid Unit Selection
          else if (GuiHelp.inputCheck(unitDrop.getSelectedItem() == ConvertUtil.BLANK, frame,
              ERRORUNIT, INVALINPUT))
            return;

          // Otherwise
          else
            outputTextField.setText(String.format(CAL_OUTPUT, calorieValue));
        }
        catch (NumberFormatException ex)
        {
          JOptionPane.showMessageDialog(frame, ERRORAMNT, INVALINPUT, JOptionPane.ERROR_MESSAGE);
        }
      }
    };
  }

  /**
   * Sets instance back to null when user closes the window.
   * 
   * @return the WindowListener that completes this action.
   */
  private static WindowListener whenWindowCloses()
  {
    return new WindowAdapter()
    {
      @Override
      public void windowClosing(final WindowEvent e)
      {
        instance = null;
      }
    };
  }

  /**
   * Opens either a Recipe or Meal file for calorie summarization.
   * 
   * @return The ActionListener that when triggered runs this method.
   */
  private ActionListener openFile()
  {
    return new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        // Create an array of options for the JOptionPane
        Object[] options = {STRINGS.getString(RECIPE), STRINGS.getString(MEALS)};

        int type = JOptionPane.showOptionDialog(frame, STRINGS.getString("CCW_OPTIONS"),
            STRINGS.getString("CCW_OPENFILE"), JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        try
        {
          if (type == 0) // recipe
          {
            Serializer.resetFilePath();
            try
            {
              recipe = Serializer.deserializeRecipe();
            }
            catch (FileNotFoundException fnf)
            
            {
              return;
            }
            getCals(recipe);
          }
          else if (type == 1)
          {
            MealSerializer.resetFilePath();
            meal = MealSerializer.deserializeMeal();
            getCals(meal);
          }
          else
          {
            System.out.printf("User did nothing\n");
          }
        }
        catch (ClassNotFoundException | IOException e1)
        {
          // Dont print stack
        }

      }
    };
  }

  /**
   * Displays the total calories in a recipe.
   * 
   * @param rec
   *          the recipe to be calculated and displayed
   */
  private void getCals(final Recipe rec)
  {
    double calcSum = 0.0;
    
      
    
    
    createCalWindow(rec.getName());
    calcSum = calcCalsRec(rec, null);

    // Set up total calories
    totalCals.setText(String.format(CAL_OUTPUT, calcSum));
  }

  /**
   * Displays the total calories in a meal.
   * 
   * @param mel
   *          The meal to be calculated and displayed
   */
  private void getCals(final Meal mel)
  {
    double calcSum = 0.0;
    createCalWindow(mel.getName());
    calcSum = calcCalsMel(mel, "     ");

    // Set up total calories
    totalCals.setText(String.format(CAL_OUTPUT, calcSum));
  }

  /**
   * Creates a new window displaying the calories of a meal or recipe.
   * 
   * @param name
   *          The title of the meal or recipe
   */
  private void createCalWindow(final String name)
  {
    JFrame calFrame = new JFrame();
    calFrame.setTitle(String.format("%s Calorie Information", name));
    calFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    calFrame.setSize(800, 400);
    calFrame.setLocationRelativeTo(null);
    calFrame.setResizable(false);

    // Initialize variables
    validCalList = new DefaultListModel<String>();
    noCalList = new DefaultListModel<String>();
    JPanel calPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JList<String> validCalArea = new JList<String>(validCalList);
    JScrollPane scrollPane1 = new JScrollPane(validCalArea);
    JList<String> noCalArea = new JList<String>(noCalList);
    JScrollPane scrollPane2 = new JScrollPane(noCalArea);
    JPanel listPanel = new JPanel(new GridLayout());
    JLabel label = new JLabel("Total Calories: ");
    totalCals = new JTextField("N/A");

    // Set up things
    totalCals.setEditable(false);
    validCalArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    noCalArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Set up panels
    calPanel.add(label);
    calPanel.add(totalCals);
    listPanel.add(scrollPane1);
    listPanel.add(scrollPane2);

    // Set up the layout and add components
    calFrame.setLayout(new BorderLayout());
    calFrame.add(calPanel, BorderLayout.SOUTH);
    calFrame.add(listPanel, BorderLayout.CENTER);
    calFrame.setVisible(true);
  }

  private Double calcCalsMel(final Meal mel, final String modifier)
  {
    String mod = new String();
    if (modifier == null)
    {
      mod = "";
    }
    else
    {
      mod = modifier;
    }
    double calcSum = 0.0;
    ArrayList<Recipe> recipeWithCals = mel.getRecipe();
    for (Recipe each : recipeWithCals)
    {
      Double recipeCals = calcTotalRecCals(each);
      if (!recipeCals.isNaN())
        validCalList.addElement(String.format("%s: %s (%4.0f %s)", STRINGS.getString(RECIPE),
            each.getName(), recipeCals, STRINGS.getString(CALS)));
      else
        noCalList.addElement(String.format(THREE_OUTPUT, STRINGS.getString(RECIPE),
            each.getName(), STRINGS.getString(CALBAD)));
      if (recHasInvalidCals(each))
      {
        noCalList.addElement(String.format(THREE_OUTPUT, STRINGS.getString(RECIPE),
            each.getName(), STRINGS.getString(CALBAD)));
      }
      calcSum += calcCalsRec(each, mod);
    }
    return calcSum;
  }

  private Double calcCalsRec(final Recipe rec, final String modifier)
  {
    String mod = new String();
    if (modifier == null)
    {
      mod = "";
    }
    else
    {
      mod = modifier;
    }
    double calcSum = 0.0;
    ArrayList<Ingredient> ingredWithCals = rec.getIngredients();
    for (Ingredient each : ingredWithCals)
    {
      calcSum += calcCalsIngred(each, mod);
    }
    return calcSum;
  }

  private Double calcCalsIngred(final Ingredient each, final String modifier)
  {
    String mod = new String();
    if (modifier == null)
    {
      mod = "";
    }
    else
    {
      mod = modifier;
    }
    double calcSum = 0.0;
    // Add to the boxes

    // If the ingredient is valid
    if (each.getHasCalInfo() && !each.getUnit().isBlank() && each.getAmount() >= 0)
    {
      Double eachCals = calcIngredCals(each, each.getAmount());

      // Verify integrity of each calorie calculation
      if (eachCals.isNaN() || eachCals < 0.0)
      {
        noCalList.addElement(String.format(TWO_OUTPUT,
            String.format(MOD_OUTPUT, mod, each.getName()), STRINGS.getString(CALS)));
      }
      else
      {
        // Set string format
        String toSum = String.format(CAL_OUTPUT, eachCals);

        // Add valid ingredient with calories to list
        validCalList.addElement(String.format("%s: %s %s",
            String.format(MOD_OUTPUT, mod, each.getName()), toSum, STRINGS.getString(CALS)));

        // Add to total calories
        calcSum += Double.parseDouble(toSum);
      }
    }
    else
    {
      noCalList.addElement(String.format(TWO_OUTPUT, String.format(MOD_OUTPUT, mod, each.getName()),
          STRINGS.getString(CALS)));
    }
    return calcSum;
  }

  private Double calcTotalRecCals(final Recipe rec)
  {
    ArrayList<Ingredient> ingreds = rec.getIngredients();
    double calcSum = 0.0;
    for (Ingredient each : ingreds)
    {
      // If the ingredient is valid
      if (each.getHasCalInfo() && !each.getUnit().isBlank() && each.getAmount() >= 0)
      {
        Double eachCals = calcIngredCals(each, each.getAmount());
        if (!(eachCals.isNaN() || eachCals < 0))
        {
          // Set string format
          String toSum = String.format(CAL_OUTPUT, eachCals);
          // Add to total calories
          calcSum += Double.parseDouble(toSum);
        }
      }
    }
    return calcSum;
  }

  private boolean recHasInvalidCals(final Recipe rec)
  {
    boolean cond = false;
    ArrayList<Ingredient> ingreds = rec.getIngredients();
    for (Ingredient each : ingreds)
    {
      // If the ingredient is valid
      if (each.getHasCalInfo() && !each.getUnit().isBlank() && each.getAmount() >= 0)
      {
        Double eachCals = calcIngredCals(each, each.getAmount());
        if (eachCals.isNaN() || eachCals < 0)
        {
          cond = true;
        }
      }
      else
        cond = true;
    }
    return cond;
  }

  private Double calcIngredCals(final Ingredient ingred, final Double amount)
  {
    double recSum = 0; // Final value
    double calsPer100g = ingred.getCalsPerGram() * 100;
    Double density = ingred.getGramsPerMl();
    Double weight = ConvertUtil.getConversionToGrams(ingred.getUnit()) * amount;
    String type = ConvertUtil.getType(ingred.getUnit());
    if (type.equals(ConvertUtil.TYPE_VOLUME))
      recSum = (weight * density * calsPer100g) / 100;
    else if (type.equals(ConvertUtil.TYPE_MASS))
      recSum = (weight * calsPer100g) / 100;
    else
      recSum = Double.NaN;
    return recSum;
  }

  /**
   * Refreshes the ingredients.
   * 
   * @return the action that refreshes the ingredients
   */
  private ActionListener refreshInternals()
  {
    return new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {
          updateIngredientsList(ingredientDrop);
        }
        catch (ClassNotFoundException | IOException e1)
        {
          e1.printStackTrace();
        }
      }
    };
  }

  /**
   * Updates the list of ingredients.
   * 
   * @param drop
   *          the dropdown containing the ingredients to be updated
   * @throws IOException
   *           invalid IO
   * @throws ClassNotFoundException
   *           class invalid
   * @throws FileNotFoundException
   *           invalid file
   */
  public void updateIngredientsList(final JComboBox<String> drop)
      throws FileNotFoundException, ClassNotFoundException, IOException
  {
    MasterIngredientList list = new MasterIngredientList();
    List<Ingredient> userIngreds = list.getIngredients();
    drop.removeAllItems(); // reset list
    drop.addItem(ConvertUtil.BLANK);
    ArrayList<String> forDrop = new ArrayList<String>(); // To be sorted for the drop display
    for (String ingred : IngredientCalories.getIngredientsToString())
    {
      forDrop.add(ingred);
    }
    for (Ingredient eachIngredient : userIngreds)
    {
      forDrop.add(eachIngredient.getName());
    }
    Collections.sort(forDrop, Comparator.comparing(String::toLowerCase));
    for (String eachIngredAlphabetized : forDrop)
    {
      drop.addItem(eachIngredAlphabetized);
    }
  }

  /**
   * Checks if the textField has had any new text entered into it. If it has been, ensure the
   * conditions are met to enable the calculate button. (This is for JComboBox components)
   * 
   * @param field
   *          the textField to be watched
   * @return the ActionListener that calls the testConditions method if the field gets messed with.
   */
  private ActionListener checkForChange(final JTextField field)
  {
    return new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        testConditions(field);
      }
    };
  }

  /**
   * Sets the calc and swap buttons on and off given the proper conditions are met within the
   * dropboxes and the textfield.
   * 
   * @param field
   *          the textfield to be entered in. Not sure why this is here tbh since the method isn't
   *          versatile.
   */
  private void testConditions(final JTextField field)
  {
    try
    {
      Double tester;
      tester = Double.parseDouble(field.getText().toString());
      if (tester >= 0 && ingredientDrop.getSelectedIndex() != 0 && unitDrop.getSelectedIndex() != 0)
      {
        calorieCalc.setEnabled(true);
      }
      else
      {
        calorieCalc.setEnabled(false);
      }

    }
    catch (NumberFormatException nfe)
    {

    }

  }

  /**
   * This method is called every time the user adds, removes, or modifies text within the JTextField
   * "field" passed in. And calls the valid method based on what action was taken.
   * 
   * @param field
   *          the JTextField that is being watched.
   * @return The DocumentListener to be added to the inputTextField component.
   */
  private DocumentListener checkAction(final JTextField field)
  {
    return new DocumentListener()
    {

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        if (field.getText().length() == 0)
        {
          calorieCalc.setEnabled(false);
        }
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        testConditions(field);
      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        return;
      }
    };
  }

}
