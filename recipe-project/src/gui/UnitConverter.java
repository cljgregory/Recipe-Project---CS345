package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import recipeBookComponents.Ingredient;
import recipeBookComponents.IngredientCalories;
import recipeBookComponents.MasterIngredientList;
import recipeBookUtilities.ConvertUtil;

import java.awt.*;
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

/**
 * Converts units of measure with one another.
 * 
 * @author Ethan Pae
 * @version 4.0
 */
public class UnitConverter
{
  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  // --==CONSTANT ITEMS==--
  private static final Dimension TXT_BOX_STD = new Dimension(200, 25);
  private static final String ERRORNUM = STRINGS.getString("INPUT_NUMBER_PLZ");
  private static final String INVALINPUT = STRINGS.getString("INVALINPUT");
  private static final String VOLUME = "Volume"; // Internal, they don't
  private static final String MASS = "Mass"; // need translation
  private static final String INGRED_COLON = "INGRED_COLON"; // need translation
  // --==##############==--

  private static UnitConverter instance; // Only one UnitConverter window can be opened (Spec 5.1)

  JFrame frame;
  JPanel upperFormat;
  JPanel lowerFormat;
  JPanel flowFormat; // Median panel for alignment
  JPanel unitConverter;
  JPanel buttonPanel;
  JPanel buttonFrame;
  JComboBox<String> inputDrop;
  JComboBox<String> outputDrop;
  JComboBox<String> ingredientDrop;
  JTextField inputTextField;
  JTextField outputTextField;
  JTextField densityTextField; // Shows ingredient density
  JButton calcItems;
  JButton resetItems;
  JButton refreshIngredients;
  JButton doSwap;

  /**
   * Constructor for UnitConverter window.
   */
  private UnitConverter()
  {
    show();
  }

  /**
   * Ensures only one window of unitConverter is displayed.
   * 
   * @return the UnitConverter window
   */
  public static UnitConverter getInstance()
  {
    if (instance == null) // if there hasn't been an instance
    {
      instance = new UnitConverter();
    }
    return instance;
  }

  /**
   * Constructs the UnitConverter window.
   */
  private void createAndShowGUI()
  {
    // --==JCOMPONENT VARIABLE DECLARATION==--
    frame = new JFrame(STRINGS.getString("UNIT_CONVERTER"));
    upperFormat = new JPanel();
    lowerFormat = new JPanel();
    flowFormat = new JPanel(); // Median panel for alignment
    unitConverter = new JPanel();
    buttonPanel = new JPanel();
    buttonFrame = new JPanel();
    inputDrop = new JComboBox<>(ConvertUtil.getUnits());
    outputDrop = new JComboBox<>(ConvertUtil.getUnits());
    ingredientDrop = new JComboBox<>();
    inputTextField = new JTextField(8);
    outputTextField = new JTextField(25);
    densityTextField = new JTextField(5); // Shows ingredient density
    calcItems = new JButton();
    resetItems = new JButton();
    refreshIngredients = new JButton();
    doSwap = new JButton();
    ingredientDrop.setEnabled(false);
    doSwap.setEnabled(false);
    calcItems.setEnabled(false);
    // --==###############################==--

    // ActionListener fetchAction = GuiHelp.fetchRecipe(ingredientDrop, refreshIngredients); //
    // Option to add
    // // recipe
    ActionListener calcAction = calculateAction(); // Functionality for GUI
    ActionListener resetAction = GuiHelp.resetAction(inputTextField, outputTextField, inputDrop,
        outputDrop, ingredientDrop, true, true, true, refreshIngredients); // Functionality for GUI
    ActionListener swapAction = swapper(inputDrop, outputDrop);
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
    try
    {
      updateIngredientsList(ingredientDrop);
    }
    catch (ClassNotFoundException | IOException e)
    {
      e.printStackTrace();
    } // Add ingredients to list
    // --==###############==--

    // --==RESIZE==--
    inputTextField.setMaximumSize(TXT_BOX_STD);
    inputTextField.setPreferredSize(TXT_BOX_STD);
    // --==######==--

    // --==TEXTFIELD & DROP RULES==--
    inputTextField.addActionListener(calcAction); // Reads user input for conversion
    outputTextField.setEditable(false); // Can't tweak output
    outputTextField.setText("_________"); // goes away after calculation
    outputTextField.setBorder(null); // Conforming to IDD
    densityTextField.setEditable(false); // Technically not even on the display but might be
    // --==######################==--

    // --==CONSTRUCT FRAME==--
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(690, 240);
    frame.setResizable(true);
    frame.setMinimumSize(new Dimension(690, 240)); // Set minimum width
    frame.addKeyListener(GuiHelp.addHotKey(KeyEvent.VK_ENTER, calcAction));
    frame.setFocusable(true);
    frame.addWindowFocusListener(GuiHelp.keepTextSelected(frame, inputTextField));
    frame.addWindowListener(whenWindowCloses());

    frame.add(buttonFrame, BorderLayout.NORTH);
    unitConverter.add(upperFormat);
    unitConverter.add(lowerFormat);
    frame.add(unitConverter, BorderLayout.CENTER);
    // --==###############==--

    // --==DISABLE FOCUSING==--
    inputDrop.setFocusable(false); // This sacrifices keybindings for navigating dropdowns
    outputDrop.setFocusable(false); // in order to have a consistent listening for when the user
    ingredientDrop.setFocusable(false); // hits enter.
    // --==################==--

    // --==ADD DROP DOWN MENUS==--
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5, 30, 5, 5);
    c.anchor = GridBagConstraints.WEST;

    c.gridx = 0;
    c.gridy = 0;
    upperFormat.add(new JLabel(STRINGS.getString("FROM_UNIT")), c);
    c.gridx = 1;
    c.gridy = 0;
    c.insets = new Insets(5, 10, 5, 5);
    upperFormat.add(inputDrop, c);
    c.insets = new Insets(5, 15, 5, 5);
    c.gridx = 2;
    c.gridy = 0;
    upperFormat.add(new JLabel(STRINGS.getString("TO_UNIT")), c);
    c.gridx = 3;
    c.gridy = 0;
    c.insets = new Insets(5, 0, 5, 15);
    upperFormat.add(outputDrop, c);

    c.gridx = 4;
    c.gridy = 0;
    upperFormat.add(new JLabel(STRINGS.getString(INGRED_COLON)), c);
    c.gridx = 5;
    c.gridy = 0;
    upperFormat.add(ingredientDrop, c);
    c.insets = new Insets(5, 0, 5, 5);
    // ---==#####################==---

    // --==ADD INPUT AND OUTPUT TEXT==--

    c.gridx = 0;
    c.gridy = 0;
    lowerFormat.add(new JLabel(STRINGS.getString("FROM_AMNT")), c);
    c.gridx = 1;
    c.gridy = 0;
    c.insets = new Insets(5, 2, 5, 20);
    lowerFormat.add(inputTextField, c);

    c.gridx = 2;
    c.gridy = 0;
    c.insets = new Insets(5, 5, 5, 5);
    lowerFormat.add(new JLabel(STRINGS.getString("TO_AMNT")), c);
    c.gridx = 3;
    c.gridy = 0;
    lowerFormat.add(outputTextField, c);
    // ---==#####################==---

    // --==CONSTRUCT FRAME==--
    GuiHelp.addButtonToTop(buttonFrame, buttonPanel, calcItems, "calculate2x.png",
        STRINGS.getString("RUN_CALCS"), calcAction);
    GuiHelp.addButtonToTop(buttonFrame, buttonPanel, resetItems, "restart2x.png",
        STRINGS.getString("RESET_ENTRIES"), resetAction);
    GuiHelp.addButtonToTop(buttonFrame, buttonPanel, doSwap, "swap2x.png",
        STRINGS.getString("SWAP_UNITS"), swapAction);
    GuiHelp.addButtonToTop(buttonFrame, buttonPanel, refreshIngredients, "refresh2x.png",
        STRINGS.getString("REFRESH_INGRED"), refreshAction);
    inputTextField.getDocument().addDocumentListener(checkAction(inputTextField));
    inputDrop.addActionListener(checkForChange(inputTextField));
    outputDrop.addActionListener(checkForChange(inputTextField));
    inputDrop.addActionListener(checkForChange(inputDrop, outputDrop));
    outputDrop.addActionListener(checkForChange(inputDrop, outputDrop));
    ingredientDrop.addActionListener(checkForChange(inputTextField));
    ingredientDrop.addActionListener(checkForChange(inputDrop, outputDrop));
    unitConverter.add(upperFormat);
    unitConverter.add(lowerFormat);
    frame.add(buttonFrame, BorderLayout.NORTH);
    frame.add(unitConverter, BorderLayout.CENTER);
    // --==###############==--

    // --==SHOW FRAME==--
    frame.setVisible(true);
    // --==##########==--
  }

  /**
   * Creates an ActionListener for the calculate action.
   * 
   * @return An ActionListener that handles the calculate action.
   */
  private ActionListener calculateAction()
  {
    return new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {
          getConvertedValue();
        }
        catch (NumberFormatException | ClassNotFoundException | IOException ex)
        {
          JOptionPane.showMessageDialog(frame, ERRORNUM, INVALINPUT, JOptionPane.ERROR_MESSAGE);
        }
      }
    };
  }

  private void getConvertedValue() throws FileNotFoundException, ClassNotFoundException, IOException
  {
    double inputValue = Double.parseDouble(inputTextField.getText());
    String inputUnit = (String) inputDrop.getSelectedItem();
    String outputUnit = (String) outputDrop.getSelectedItem();
    double density = 0;

    // --==GET DENSITY==--
    if (ingredientDrop.isEnabled())
    {
      String ingredientSelected = ingredientDrop.getSelectedItem().toString();
      MasterIngredientList userIngreds = new MasterIngredientList();
      ArrayList<Ingredient> setIngreds = IngredientCalories.getIngredientsToIngredients();
      for (Ingredient eachIngred : setIngreds)
      {
        if (ingredientSelected.equalsIgnoreCase(eachIngred.getName()))
        {
          density = eachIngred.getGramsPerMl();
          break;
        }
      }
      for (Ingredient eachIngredient : userIngreds.getIngredients())
      {
        if (ingredientSelected.equalsIgnoreCase(eachIngredient.getName()))
        {
          density = eachIngredient.getGramsPerMl();
          break;
        }
      }
    }

    else
    {
      density = 1;
    }

    // --==###########==--
    // --==INVALID DENSITY CHECK==--
    if (GuiHelp.inputCheck(density <= 0, frame, STRINGS.getString("INGRED_INVALID_DENSITY"),
        STRINGS.getString("INVALID_DENSITY")))
    {
      return;
    }
    // --==#####################==--

    // GET CONVERTED VALUE
    Double outputValue = ConvertUtil.convert(inputValue, inputUnit, outputUnit, density);

    // IF VALUE WAS INVALID
    if (outputValue <= 0)
    {
      JOptionPane.showMessageDialog(frame, ERRORNUM, INVALINPUT, JOptionPane.ERROR_MESSAGE);
    }
    // WRONG UNIT ENTERED
    else if (outputValue.isNaN())
    {
      JOptionPane.showMessageDialog(frame, STRINGS.getString("VALID_MEASUREMENT"), INVALINPUT,
          JOptionPane.ERROR_MESSAGE);
    }
    // OTHERWISE PRINT TO GUI
    else
    {
      outputTextField.setText(String.format("%.4f", outputValue));
    }
  }

  /**
   * Swaps the two drop-down menu's by swapping their selected indexes.
   * 
   * @param one
   *          the first drop-down
   * @param two
   *          the second drop-down
   */
  private void swap(final JComboBox<String> one, final JComboBox<String> two)
  {
    if ((one.getItemCount() == two.getItemCount())
        && (one.getSelectedIndex() != 0 && two.getSelectedIndex() != 0))
    {
      // Set the value of two's selected index temporarily
      int selectedTwo = two.getSelectedIndex();

      // Set two's selected index to one's
      two.setSelectedIndex(one.getSelectedIndex());

      // Set one's selected index to two's index before it was changed to one's
      one.setSelectedIndex(selectedTwo);

      // Automatically calculate if one and two are not selecting
      // the blank option and user has put in a value
      if (one.getSelectedIndex() != 0 && two.getSelectedIndex() != 0
          && !(inputTextField.getText().isEmpty()))
      {
        try
        {
          getConvertedValue();
        }
        catch (ClassNotFoundException | IOException e)
        {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Swaps the two drop-down selected values.
   * 
   * @param one
   *          the first drop-down
   * @param two
   *          the second drop-down
   * @return the ActionListener that runs this method each time the Swap button is pressed.
   */
  private ActionListener swapper(final JComboBox<String> one, final JComboBox<String> two)
  {
    return new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        swap(one, two);
      }
    };
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
          calcItems.setEnabled(false);
          doSwap.setEnabled(false);
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

      }
    };
  }

  /**
   * Checks if the textField has had any new text entered into it. If it has been, ensure the
   * conditions are met to enable the calculate and swap buttons. (This is for JComboBox components)
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
   * Checks for change between the two ComboBoxes. If there is a change, ensure the state of the
   * ingredient drop-down menu is enabled or disabled.
   * 
   * @param field
   *          the first field
   * @param field2
   *          the second field
   * @return the ActionListener that will compute the action when the component containing it is
   *         affected
   */
  private ActionListener checkForChange(final JComboBox<String> field,
      final JComboBox<String> field2)
  {
    return new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        testConditions(field, field2);
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
      if (tester >= 0 && inputDrop.getSelectedIndex() != 0 && outputDrop.getSelectedIndex() != 0)
      {
        calcItems.setEnabled(true);
        doSwap.setEnabled(true);
      }
      else
      {
        calcItems.setEnabled(false);
        doSwap.setEnabled(false);
      }
      if (ingredientDrop.isEnabled() && ingredientDrop.getSelectedIndex() == 0)
      {
        calcItems.setEnabled(false);
        doSwap.setEnabled(false);
      }

    }
    catch (NumberFormatException nfe)
    {

    }

  }

  /**
   * Checks if there is a volume to mass or mass to volume conversion.
   * 
   * @param firstField
   *          the first volume to test with the second
   * @param secondField
   *          the second volume to test with the first
   */
  private void testConditions(final JComboBox<String> firstField,
      final JComboBox<String> secondField)
  {
    String type1 = new String();
    String type2 = new String();
    type1 = GuiHelp.getType(firstField);
    type2 = GuiHelp.getType(secondField);

    // Check if Vol-Mass or Mass-Vol
    if ((type1.equals(VOLUME) && type2.equals(MASS))
        || (type1.equals(MASS) && type2.equals(VOLUME)))
    {
      ingredientDrop.setEnabled(true);
    }
    else
    {
      ingredientDrop.setEnabled(false);
    }
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
   *           bad input output
   * @throws ClassNotFoundException
   *           No valid class
   * @throws FileNotFoundException
   *           No valid file
   */
  public void updateIngredientsList(final JComboBox<String> drop)
      throws FileNotFoundException, ClassNotFoundException, IOException
  {
    MasterIngredientList userList = new MasterIngredientList();
    List<Ingredient> userIngreds = userList.getIngredients();
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
   * Displays the menu when this is called.
   */
  public void show()
  {
    createAndShowGUI();
  }
}
