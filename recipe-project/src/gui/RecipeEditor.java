package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import recipeBookComponents.Ingredient;
import recipeBookComponents.MasterIngredientList;
import recipeBookComponents.Recipe;
import recipeBookComponents.Step;
import recipeBookComponents.Utensil;
import recipeBookUtilities.DocumentState;
import utilities.Serializer;

/**
 * Recipe editor class represents the RecipeEditor GUI along with the action listeners.
 * 
 * @author Colin Gregory & Ethan Pae
 * @version 4.0
 */
public class RecipeEditor
{

  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  private static final String ERROR = "ERROR";
  private static final String SPACE = " ";
  private static final String NO_SPACE = "NO_SPACES";

  private Recipe recipe;

  private JTextField nameField;
  private JTextField servesField;

  private JFrame main;
  private DocumentState documentState;
  private boolean isNewRecipe = true;

  // Buttons
  private JButton newItem = new JButton();
  private JButton openItem = new JButton();
  private JButton saveItem = new JButton();
  private JButton saveAsItem = new JButton();
  private JButton closeItem = new JButton();

  private REIngredients ingredPanel;
  private REUtensil utensilPanel;
  private RESteps stepPanel;

  private Recipe openedRecipe;
  private int serving;

  /**
   * Recipe Editor constructor that will construct the GUI window. Also includes the action
   * listeners.
   * 
   * @throws IOException
   */
  public RecipeEditor() throws IOException
  {

    this.recipe = new Recipe("  ", 0);

    this.documentState = DocumentState.NULL;
    updateState(documentState);

    // Creating the Recipe Editor Window
    main = new JFrame();
    main.setSize(600, 900);
    main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    main.setTitle(STRINGS.getString("K_RCP_EDITOR"));
    main.setLocationRelativeTo(null);
    main.getContentPane().setLayout(new BorderLayout(5, 5));
    main.setResizable(false);

    // Create a custom layout manager for the menu bar with smaller gaps
    FlowLayout iconLayout = new FlowLayout(FlowLayout.LEFT, 1, 0);
    JPanel menuBar = new JPanel();
    JPanel innerMenuBar = new JPanel();
    menuBar.setLayout(iconLayout);

    // Add menu items to the menu bar with icons

    GuiHelp.makeTopButtonComponents(menuBar, innerMenuBar);
    GuiHelp.addButtonToTop(menuBar, innerMenuBar, newItem, "new2.png",
        STRINGS.getString("CREATE_RECIPE"), null);
    GuiHelp.addButtonToTop(menuBar, innerMenuBar, openItem, "open2.png",
        STRINGS.getString("OPEN_RECIPE"), null);
    GuiHelp.addButtonToTop(menuBar, innerMenuBar, saveItem, "save2.png",
        STRINGS.getString("SAVE_RECIPE"), null);
    GuiHelp.addButtonToTop(menuBar, innerMenuBar, saveAsItem, "saveas2.png",
        STRINGS.getString("SAVEAS_RECIPE"), null);
    GuiHelp.addButtonToTop(menuBar, innerMenuBar, closeItem, "close2.png",
        STRINGS.getString("CLOSE_RECIPE"), null);

    // Set the menu bar for the main window
    main.add(menuBar, BorderLayout.NORTH);

    /* STUFF */
    // Create main window
    JPanel menuWindow = new JPanel();
    menuWindow.setLayout(new BoxLayout(menuWindow, BoxLayout.Y_AXIS));

    // Create window for displaying Utensil Panel (Required for spacing)
    JPanel windowUT = new JPanel();
    windowUT.setLayout(new BoxLayout(windowUT, BoxLayout.Y_AXIS)); // **Add explanation**
    windowUT.add(Box.createRigidArea(new Dimension(10, 0))); // Spacing

    // Create name and serves
    JPanel recipePanel = new JPanel();
    recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.X_AXIS));
    recipePanel.add(new JLabel(STRINGS.getString("NAME_COLON")));
    nameField = new JTextField(10);
    nameField.setMaximumSize(new Dimension(350, 25));
    nameField.setPreferredSize(new Dimension(400, 25)); // Maximum size fixes the horizontal sizes
    nameField.setEnabled(false); // Disables the text until the user hits new
    recipePanel.add(nameField);
    recipePanel.add(Box.createRigidArea(new Dimension(10, 0)));
    recipePanel.add(new JLabel(STRINGS.getString("SERVES_COLON")));
    servesField = new JTextField(5);
    servesField.setMaximumSize(new Dimension(100, 25)); // while preferred size seems to fix
    servesField.setPreferredSize(new Dimension(100, 25)); // the vertical sides. Not sure why
    servesField.setEnabled(false); // Disables the text until the user hits new
    recipePanel.add(servesField);
    recipePanel.add(Box.createRigidArea(new Dimension(10, 0)));
    windowUT.add(recipePanel);

    // Create Utensil Panel & add to windowUT
    utensilPanel = new REUtensil(recipe, this);
    windowUT.add(utensilPanel);
    windowUT.add(Box.createRigidArea(new Dimension(10, 0)));

    // Create Ingredients Panel & add to windowUT
    ingredPanel = new REIngredients(recipe, this);
    windowUT.add(ingredPanel);
    windowUT.add(Box.createRigidArea(new Dimension(5, 0)));

    // Create Step Panel & add to windowUT
    stepPanel = new RESteps(recipe, this);
    windowUT.add(stepPanel);
    windowUT.add(Box.createRigidArea(new Dimension(5, 0)));

    // Add to main window
    menuWindow.add(windowUT);

    // Add main window to main frame

    main.getContentPane().add(menuWindow, BorderLayout.CENTER);
    main.setVisible(true);

    // Disable buttons until a recipe is loaded

    // Action Listener for close, will exit program with pressed.
    closeItem.addActionListener(new ActionListener()
    {
      @SuppressWarnings("static-access")
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        ingredPanel.resetPanel();
        stepPanel.resetPanel();
        utensilPanel.resetPanel();
        utensilPanel.clearUtensils();
        nameField.setEnabled(false);
        servesField.setEnabled(false);
        stepPanel.stepsDisableButtons();
        utensilPanel.utensilDisableButtons();
        ingredPanel.ingredientDisableButtons();
        recipe = getRecipe();
        recipe.clearSteps();
        recipe.clearIngredients();
        recipe.clearName();
        servesField.setText("");
        nameField.setText("");
        isNewRecipe = true;
        updateState(documentState.NULL);

      }
    });

    saveItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {

          if (nameField.getText().isBlank())
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString("RECIPE_NAME"),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
            return;
          }

          if (nameField.getText().contains(SPACE))
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString(NO_SPACE),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
            return;
          }
          // Adding the ingredients to the master list
          List<Ingredient> addingToMaster = ingredPanel.getIngredients();
          System.out.println("Ingredients from ingredPanel: " + addingToMaster.toString()); // Add
                                                                                            // this
                                                                                            // line

          MasterIngredientList masterIngredientList = new MasterIngredientList();

          for (Ingredient ing : addingToMaster)
          {
            if (!masterIngredientList.getIngredients().contains(ing))
            {
              masterIngredientList.addIngredient(ing);
            }

          }

          recipe.setName(nameField.getText());
          recipe.setServings(Integer.parseInt(servesField.getText()));
          // Update an existing recipe
          Serializer.reSerializeRecipe(recipe);
          System.out.println(STRINGS.getString("RCP_SAVED") + Serializer.getCurrentFilePath());

          updateState(DocumentState.UNCHANGED);
          isNewRecipe = false; // Set this to false after saving, so further saves will update the
                               // existing file
        }
        catch (IOException e1)
        {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });

    saveAsItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {
          if (nameField.getText().contains(SPACE))
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString(NO_SPACE),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
            return;
          }

          try
          {
            serving = Integer.parseInt(servesField.getText());
          }
          catch (NumberFormatException nfe)
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString("SERVES_NUMBER"),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
            return;
          }
          if (nameField.getText().isBlank())
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString(NO_SPACE),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
            return;
          }

          // Create a new Recipe object
          Recipe newRecipe = new Recipe(nameField.getText(), serving);

          // Get the utensils from the utensils panel
          List<Utensil> utensils = utensilPanel.getUtensils();
          for (Utensil utensil : utensils)
          {
            newRecipe.addUtensil(utensil);
          }

          List<Step> steps = stepPanel.getSteps();
          for (Step step : steps)
          {
            newRecipe.addStep(step);
          }

          List<Ingredient> ing = ingredPanel.getIngredients();

          // adding the ingredients to the recipe object and the master list.
          MasterIngredientList masterIngredientList = new MasterIngredientList();
          for (Ingredient ings : ing)
          {
            newRecipe.addIngredient(ings);
            masterIngredientList.addIngredient(ings);
          }

          // Serialize the newRecipe object
          System.out.println(newRecipe.getName());

          Serializer.serializeRecipe(newRecipe);
          isNewRecipe = false;
          updateState(DocumentState.UNCHANGED);
        }
        catch (IOException e1)
        {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });

    openItem.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {
          Serializer.resetFilePath();

          utensilPanel.clearUtensils();

          openedRecipe = Serializer.deserializeRecipe();

          // String filePath = Serializer.getCurrentFilePath();
          // System.out.println(STRINGS.getString("RCP_DESERIAL") + filePath);

          // System.out.println(openedRecipe.getName());

          // Update the nameField and servesField with the new recipe's data
          nameField.setText(openedRecipe.getName());
          servesField.setText(String.valueOf(openedRecipe.getServings()));

          // Resets the panels
          utensilPanel.resetPanel();
          ingredPanel.resetPanel();
          stepPanel.resetPanel();

          // Update the utensils, ingredients, and steps panels
          utensilPanel.updateUtensilsList(openedRecipe.getUtensils());
          ingredPanel.updateIngredientsList(openedRecipe.getIngredients());
          stepPanel.updateStepsList(openedRecipe.getSteps());
          isNewRecipe = false;
          updateState(DocumentState.UNCHANGED);

        }
        catch (ClassNotFoundException | IOException e1)
        {
          // TODO Auto-generated catch block
          JOptionPane.showMessageDialog(null, e1.getMessage(), STRINGS.getString(ERROR),
              JOptionPane.ERROR_MESSAGE);

        }
      }
    });

    newItem.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {

        nameField.setEnabled(true);
        servesField.setEnabled(true);
        ingredPanel.ingredientEnableButtons();
        ingredPanel.resetPanel();
        utensilPanel.utensilEnableButton();
        utensilPanel.resetPanel();
        // stepPanel.stepEnableButtons();
        stepPanel.resetPanel();
        isNewRecipe = true;
        updateState(DocumentState.UNCHANGED);
      }

    });

    // The next 2 document listeners are responsible for changing the state of the document
    // once the name field or serves field is changed in any way.
    nameField.getDocument().addDocumentListener(new DocumentListener()
    {

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        // TODO Auto-generated method stub

      }

      @SuppressWarnings("static-access")
      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        updateState(documentState.CHANGED);

      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        // TODO Auto-generated method stub

      }
    });

    servesField.getDocument().addDocumentListener(new DocumentListener()
    {

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        // TODO Auto-generated method stub

      }

      @SuppressWarnings("static-access")
      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        updateState(documentState.CHANGED);

      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        // TODO Auto-generated method stub

      }
    });
  }

  /**
   * Gets the current recipe.
   * 
   * @return the recipe
   */
  public Recipe getRecipe()
  {
    if (!(recipe == null))
    {
      return recipe;
    }
    return openedRecipe;
  }

  /**
   * gets the steps in a recipe.
   * 
   * @return the lisr
   */
  public ArrayList<Step> getRecipeSteps()
  {
    Recipe r = getRecipe();
    return r.getSteps();
  }

  /**
   * sets the recipe.
   * 
   * @param r
   *          the recipe
   */
  public void setRecipe(final Recipe r)
  {
    recipe = r;
  }

  /**
   * Method that will update the state of the document.
   * 
   * @param newState
   *          NULL, Changed, or Unchanged
   */
  public void updateState(final DocumentState newState)
  {
    documentState = newState;

    if (documentState == DocumentState.NULL || documentState == DocumentState.UNCHANGED)
    {
      saveAsItem.setEnabled(false);
      saveItem.setEnabled(false);
      newItem.setEnabled(true);
      openItem.setEnabled(false);
      closeItem.setEnabled(false);
    }

    if (documentState == DocumentState.CHANGED && !isNewRecipe)
    {
      saveItem.setEnabled(true);
    }
    else
    {
      saveItem.setEnabled(false);
    }

    if (documentState == DocumentState.CHANGED)
    {
      saveAsItem.setEnabled(true);
      openItem.setEnabled(false);
      newItem.setEnabled(false);
    }
    else
    {
      saveAsItem.setEnabled(false);
    }

    if (documentState == DocumentState.UNCHANGED)
    {
      closeItem.setEnabled(true);
      openItem.setEnabled(true);
      newItem.setEnabled(false);
    }
    else
    {
      closeItem.setEnabled(false);
    }
  }

  /**
   * Sets the Document state to null once the editor is opened.
   */
  public void setDocumentChanged()
  {
    updateState(DocumentState.CHANGED);
  }

}
