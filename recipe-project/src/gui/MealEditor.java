package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import recipeBookComponents.Ingredient;
import recipeBookComponents.Meal;
import recipeBookComponents.Recipe;
import recipeBookUtilities.DocumentState;
import utilities.MealSerializer;
import utilities.Serializer;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Meal Editor class represents the Meal editor gui as well as its buttons.
 * @author Colin Gregory & Gideon
 * @version 4.0
 */
public class MealEditor extends JFrame implements ActionListener, Serializable
{

  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }
  
  private static final String ENTER_MEAL_NAME = "ENTER_MEAL_NAME";
  private static final String ERROR = "ERROR";
  private static final String SPACE = " ";
  private static final String MEAL_NAME_SPACE = "MEAL_NAME_SPACES";
  private static final String MEL_DESERIAL = "MEL_DESERIAL";

  private static final long serialVersionUID = 1L;
  
  private static Meal meal;
//  private static JComboBox dropdown;
//  private static String item;
//  private static String filePath;
  private JComboBox<Recipe> recipeComboBox;
  private JTextArea ingredientTextArea;
  private boolean isNewMeal = true;
  private Recipe newRecipe;

  private JFrame main;
//  private JScrollPane areaPane;
//  private JTextArea area;
  private Meal openedMeal;
  private Meal newMeal;
  private JButton addrecipe;
  private JButton delete;

  private JButton newItem = new JButton();
  private JButton openItem = new JButton();
  private JButton saveItem = new JButton();
  private JButton saveAsItem = new JButton();
  private JButton closeItem = new JButton();

  private DocumentState documentState;

  private DefaultListModel<String> recipeList; // Sprint 2 LIST
  private JList<String> myList; // Sprint 2 LIST

  /**
   * Constructor for MealGUI.
   **/
  public MealEditor() throws IOException
  {

    this.documentState = DocumentState.NULL;
    updateState(documentState);

    main = new JFrame();
    main.setSize(600, 400);
    main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    main.setTitle(STRINGS.getString("K_MEL_EDITOR"));
    main.setLocationRelativeTo(null);
    main.getContentPane().setLayout(new BoxLayout(main.getContentPane(), BoxLayout.Y_AXIS));
    main.setResizable(false);

    // Create a custom layout manager for the menu bar with smaller gaps
    JPanel menuBar = new JPanel();
    JPanel menuBarInner = new JPanel();
    GuiHelp.makeTopButtonComponents(menuBar, menuBarInner);

    // Add menu items to the menu bar with icons
    GuiHelp.addButtonToTop(menuBar, menuBarInner, newItem, "new2.png",
        STRINGS.getString("NEW_MEAL"), null);
    GuiHelp.addButtonToTop(menuBar, menuBarInner, openItem, "open2.png",
        STRINGS.getString("OPEN_MEAL"), null);
    GuiHelp.addButtonToTop(menuBar, menuBarInner, saveItem, "save2.png",
        STRINGS.getString("SAVE_MEAL"), null);
    GuiHelp.addButtonToTop(menuBar, menuBarInner, saveAsItem, "saveas2.png",
        STRINGS.getString("SAVEAS_MEAL"), null);
    GuiHelp.addButtonToTop(menuBar, menuBarInner, closeItem, "close2.png",
        STRINGS.getString("CLOSE_MEAL"), null);
    // Set the menu bar for the main window
    main.add(menuBar);

    // Create main window
    JPanel menuWindow = new JPanel();
    menuWindow.setLayout(new BoxLayout(menuWindow, BoxLayout.Y_AXIS));

    JPanel windowUT = new JPanel();
    windowUT.setLayout(new BoxLayout(windowUT, BoxLayout.Y_AXIS)); // **Add explanation**
    windowUT.add(Box.createRigidArea(new Dimension(10, 0))); // Spacing

    JPanel mealInfo = new JPanel();
    mealInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
    mealInfo.add(Box.createRigidArea(new Dimension(7, 0)));
    mealInfo.add(new JLabel(STRINGS.getString("NAME_COLON")));
    JTextField nameField = new JTextField(26);
    nameField.setEnabled(false);
    nameField.setMaximumSize(new Dimension(350, 25));
    nameField.setPreferredSize(new Dimension(400, 25)); // Maximum size fixes the horizontal sizes
    mealInfo.add(nameField);
    windowUT.add(mealInfo);

    // FlowLayout flows = new FlowLayout(FlowLayout.LEFT);
    JPanel recipePanel = new JPanel();
    recipePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    // recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.Y_AXIS));
    Dimension borderSize = new Dimension(565, 245);
    recipePanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK)));
    recipePanel.setMaximumSize(borderSize);
    recipePanel.setPreferredSize(borderSize);
    recipePanel.setMinimumSize(borderSize);

    JPanel innerPanel = new JPanel();
    innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

    // JTextField nameText = new JTextField(10);
    // nameText.setColumns(2);

    // recipePanel.add(innerPanel);

    addrecipe = new JButton(STRINGS.getString("ADD_RECIPE"));
    addrecipe.setEnabled(false);
    // addrecipe.setVerticalTextPosition(AbstractButton.NORTH_WEST);
    // addrecipe.setHorizontalTextPosition(AbstractButton.NORTH_WEST);
    // addrecipe.setAlignmentY(TOP_ALIGNMENT);
    innerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Spacing
    innerPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Spacing

    recipePanel.add(addrecipe);

    newMeal = new Meal("");

    recipePanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

    // New and improved JList!!
    recipeList = new DefaultListModel<String>();
    myList = new JList<String>(recipeList);
    myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane listPane = new JScrollPane(myList);
    listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    myList.setSize(40, 40);
    delete = new JButton(STRINGS.getString("DELETE"));
    delete.setEnabled(false);

    // Text area for recipes in a meal
    // area = new JTextArea(30, 50);
    // area.setBounds(1000, 1000, 1000, 1000);

    JPanel innerPanel2 = new JPanel();
    innerPanel2.setLayout(new BoxLayout(innerPanel2, BoxLayout.X_AXIS));

    myList.setBorder(new TitledBorder(new LineBorder(Color.BLACK)));
    myList.setMaximumSize(new Dimension(30, 10));
    // area.setBorder(new TitledBorder(new LineBorder(Color.BLACK)));
    // myList.setBorder(new TitledBorder(new LineBorder(Color.BLACK)));
    // myList.setMaximumSize(new Dimension(640, 240));

    // String s1[] = {};
    // String s1[] = { "Jalpaiguri", "Mumbai", "Noida", "Kolkata", "New Delhi" };
    // create checkbox
    // dropdown = new JComboBox(s1);
    // c1.addItem(recipe.getName());
    // innerPanel.add(dropdown);

    // add ItemListener
    // c1.addItemListener(meal);

    // Set up scroll bar
    // areaPane = new JScrollPane(area);
    // areaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    delete.setVerticalTextPosition(AbstractButton.BOTTOM);
    delete.setHorizontalTextPosition(AbstractButton.CENTER);
    delete.setAlignmentY(BOTTOM_ALIGNMENT);
    listPane.setPreferredSize(new Dimension(400, 200));

    // innerPanel2.add(Box.createRigidArea(new Dimension(10, 0)));
    // innerPanel2.add(textPane); old text area
    innerPanel2.add(listPane); // NEW LIST
    // innerPanel2.add(Box.createRigidArea(new Dimension(5, 0)));
    innerPanel2.add(delete);
    // innerPanel2.add(Box.createRigidArea(new Dimension(70, 0)));

    recipePanel.add(innerPanel2);
    recipePanel.add(Box.createRigidArea(new Dimension(0, 10)));

    windowUT.add(recipePanel);

    menuWindow.add(windowUT);
    // area.append(recipe.getName()+"\n");
    main.getContentPane().add(menuWindow, BorderLayout.SOUTH);
    main.setVisible(true);

    this.documentState = DocumentState.NULL;
    updateState(documentState);

    /**
     * Adds a recipe to the recipe panel as well as the meal's recipe list.
     */
    addrecipe.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {
          // Deserialize the Recipe
          Serializer.resetFilePath();
          newRecipe = Serializer.deserializeRecipe();

          // Get the appropriate Meal object
          Meal currentMeal = openedMeal != null ? openedMeal : newMeal;

          // Check for duplicate recipe names
          boolean isDuplicate = false;
          for (Recipe recipe : currentMeal.getRecipe())
          {
            if (newRecipe.getName().equalsIgnoreCase(recipe.getName()))
            {
              isDuplicate = true;
              break;
            }
          }

          // Add the deserialized Recipe to the current Meal if it's not a duplicate
          if (!isDuplicate)
          {
            recipeList.addElement(newRecipe.getName());
            currentMeal.addRecipe(newRecipe);
          }

          updateState(DocumentState.CHANGED);
        }
        catch (ClassNotFoundException | IOException ex)
        {
          JOptionPane.showMessageDialog(null, ex.getMessage(), STRINGS.getString(ERROR),
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    /**
     * Resets all panels and sets the document state to null
     */
    closeItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        resetMealPanel();
        nameField.setEnabled(false);
        nameField.setText("");
        addrecipe.setEnabled(false);
        delete.setEnabled(false);
        updateState(DocumentState.NULL);
      }
    });

    /**
     * Serializes and saved the current opened meal.
     */
    saveItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {

        try
        {
          if (nameField.getText().isBlank())
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString(ENTER_MEAL_NAME),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
            return;
          }

          if (nameField.getText().contains(SPACE))
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString(MEAL_NAME_SPACE),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
            return;
          }
          openedMeal.setName(nameField.getText());
          MealSerializer.reSerializeMeal(openedMeal);
          System.out
              .println(STRINGS.getString(MEL_DESERIAL) + MealSerializer.getCurrentFilePath());
        }
        catch (IOException e2)
        {
          // HI BERNSTEN!!
        }
        updateState(DocumentState.UNCHANGED);
        isNewMeal = false;

      }
    });

    /**
     * Allows the user to save a meal to a specific place. The user can only save a meal as
     * something when it has not been opened.
     */
    saveAsItem.addActionListener(new ActionListener()
    {
      @SuppressWarnings("static-access")
      @Override
      public void actionPerformed(final ActionEvent e)
      {

        try
        {
          if (nameField.getText().isBlank())
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString(ENTER_MEAL_NAME),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
            return;
          }

          if (nameField.getText().contains(SPACE))
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString(MEAL_NAME_SPACE),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
            return;
          }
          newMeal.setName(nameField.getText());
          MealSerializer.serializeMeal(newMeal);
        }
        catch (IOException e1)
        {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        updateState(documentState.UNCHANGED);
      }

    });

    /**
     * Opens a new meal for the user to add or edit. Also updates the panel with the recipes in the
     * meal.
     */
    openItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        MealSerializer.resetFilePath();
        try
        {
          openedMeal = MealSerializer.deserializeMeal();
          System.out.println(STRINGS.getString(MEL_DESERIAL) + openedMeal);
          resetMealPanel(); // Clear the current panel
          for (Recipe recipe : openedMeal.getRecipe())
          {
            recipeList.addElement(recipe.getName());
          }
          nameField.setText(openedMeal.getName());
        }
        catch (ClassNotFoundException | IOException e1)
        {
          // TODO Auto-generated catch block
          JOptionPane.showMessageDialog(null, e1.getMessage(), STRINGS.getString(ERROR),
              JOptionPane.ERROR_MESSAGE);
        }

        // String filePath = MealSerializer.getCurrentFilePath();
        updateState(DocumentState.UNCHANGED);
        isNewMeal = false;
      }
    });

    /**
     * Changes the state of the document so the user can edit or create a new meal.
     */
    newItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        nameField.setEnabled(true);
        addrecipe.setEnabled(true);
        delete.setEnabled(true);

        recipeList.clear(); // Clear the recipeList

        updateState(DocumentState.UNCHANGED);
        isNewMeal = true;

      }
    });

    /**
     * The delete action listener handles removing a recipe from the panel as well as from the meal
     * objects recipe list
     */
    delete.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        Meal currentMeal = openedMeal != null ? openedMeal : newMeal;

        if (currentMeal != null)
        {
          String selectedRecipe = myList.getSelectedValue();

          Iterator<Recipe> iterator = currentMeal.getRecipe().iterator();
          while (iterator.hasNext())
          {
            Recipe recipe = iterator.next();
            if (recipe.getName().equals(selectedRecipe))
            {
              iterator.remove(); // Safely remove the recipe
              break; // Exit the loop since you've found and removed the recipe
            }
          }

          recipeList.removeElement(selectedRecipe);
        }
        updateState(DocumentState.CHANGED);
      }
    });

    nameField.getDocument().addDocumentListener(new DocumentListener()
    {

      @SuppressWarnings("static-access")
      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        if (nameField.getText().length() == 0 && recipeList.isEmpty())
          updateState(documentState.UNCHANGED);

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
   * Handles the recipe selection event.
   **/
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    Recipe recipe = (Recipe) recipeComboBox.getSelectedItem();
    ArrayList<Ingredient> ingredients = meal.ingredientforMeal(recipe);
    StringBuilder stringBuilder = new StringBuilder();
    for (Ingredient ingredient : ingredients)
    {
      stringBuilder.append(ingredient.getName()).append("\n");
    }
    ingredientTextArea.setText(stringBuilder.toString());
  }

  /**
   * Gets the recipes from the current meal.
   * 
   * @param mel
   *          The meal you wish to get the recipes from
   * @return An arrayList of the recipes in the meal given
   */
  public ArrayList<Recipe> getRecipes(final Meal mel)
  {
    return mel.getRecipe();

  }

  /**
   * Handles the document state.
   * 
   * @param newState
   *          NULL, CHANGED, or UNCHANGED
   */
  public void updateState(final DocumentState newState)
  {
    documentState = newState;

    if (documentState == DocumentState.NULL) // || documentState == DocumentState.UNCHANGED
    {
      saveAsItem.setEnabled(false);
      saveItem.setEnabled(false);
      newItem.setEnabled(true);
      openItem.setEnabled(false);
      closeItem.setEnabled(false);

    }

    if (documentState == DocumentState.CHANGED && !isNewMeal)
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
   * Set the document state.
   */
  public void setDocumentChanged()
  {
    updateState(DocumentState.CHANGED);
  }

  /**
   * Resets the recipeList panel.
   */
  public void resetMealPanel()
  {
    recipeList.clear();
  }

}
