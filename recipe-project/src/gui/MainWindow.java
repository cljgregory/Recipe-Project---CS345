package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import recipeBookComponents.Meal;
import recipeBookComponents.Recipe;
import recipeBookComponents.ShoppingList;
import utilities.MealSerializer;
import utilities.Serializer;

/**
 * Main Window Display for KILowBites.
 * 
 * @author Ethan Pae & Colin Gregory
 * @version 4.0
 */
public class MainWindow
{
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();

  private static MainWindow instance;
  private static Boolean onlyOne = true;

  private JFrame main;
  private JMenuBar menu;
  // private ImageIcon logo;
  private JLabel displayImage;

  // added for internationalization
  // this maybe should be public??

  // modify the String.format() calls so that the first parameter is now LOCALE
  // Example
  // results.add(String.format(LOCALE, STRINGS.getString("MARGINAL_TAX_RATE") + ": %%%5.2f",
  // marginalRate));
  // test to ensure no missing string literals

  // static means this is executed when the class is loaded
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  /**
   * Initialize the GUI interface.
   * 
   * @throws IOException
   *           invalid I/O
   */
  public void initialize() throws IOException
  {
    main = new JFrame();
    main.setSize(550, 300);
    main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    main.setTitle(STRINGS.getString("KILOBITES_MAIN_TITLE"));
    main.setLocationRelativeTo(null);
    main.setLayout(new BorderLayout());
    main.setResizable(false);

    ImageIcon mainLogo = loadImageIcon("KILowBites_Logo.png");
    displayImage = new JLabel(mainLogo);
    main.add(displayImage);

    // main.add(BorderLayout.CENTER);

    menu = new JMenuBar();
    menu.setBackground(Color.GRAY);
    main.setJMenuBar(menu);
    JMenu file = new JMenu(STRINGS.getString("FILE"));
    JMenu edit = new JMenu(STRINGS.getString("EDIT"));
    // JMenu search = new JMenu("Search");
    JMenu view = new JMenu(STRINGS.getString("VIEW"));
    JMenu tools = new JMenu(STRINGS.getString("TOOLS"));
    // JMenu help = new JMenu(STRINGS.getString("HELP"));

    // For a later sprint
    // JMenu config = new JMenu("Configure");
    // JMenu help = new JMenu("Help");

    JMenuItem exit = new JMenuItem(STRINGS.getString("EXIT"));
    JMenuItem meal = new JMenuItem(STRINGS.getString("MEAL"));
    JMenuItem recipe = new JMenuItem(STRINGS.getString("RECIPE"));
    JMenuItem convert = new JMenuItem(STRINGS.getString("UNIT_CONVERTER"));
    JMenuItem calories = new JMenuItem(STRINGS.getString("CALORIE_CALC"));
    // JMenuItem recipes = new JMenuItem("Recipes");
    // JMenuItem meals = new JMenuItem("Meals");
    JMenuItem slistMeal = new JMenuItem(STRINGS.getString("SHP_LIST_MEL"));
    JMenuItem slistRecipe = new JMenuItem(STRINGS.getString("SHP_LIST_RCP"));
    // JMenuItem process = new JMenuItem("Process");
    JMenuItem about = new JMenuItem(STRINGS.getString("ABOUT"));
    // JMenuItem user_guide = new JMenuItem(STRINGS.getString("UG"));

    file.add(exit);
    edit.add(meal);
    edit.add(recipe);
    // help.add(about);
    // help.add(user_guide);
    // search.add(recipes);
    // search.add(meals);
    view.add(slistRecipe);
    view.add(slistMeal);
    // view.add(process);
    tools.add(calories);
    tools.add(convert);

    exit.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        System.exit(0);
      }
    });

    meal.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {
          @SuppressWarnings("unused")
          MealEditor mealgui = new MealEditor();
        }
        catch (IOException e1)
        {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });

    recipe.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {
          @SuppressWarnings("unused")
          RecipeEditor editor = new RecipeEditor();
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
      }
    });

    slistRecipe.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {

          // Meal meal;
          // String fileName = FileOpener.readFile(true, true);

          Serializer.resetFilePath();
          Recipe openedRecipe = Serializer.deserializeRecipe();
          ShoppingList list = new ShoppingList(openedRecipe);
          @SuppressWarnings("unused")
          ShoppingListViewer shoppingListViewer = new ShoppingListViewer(list);

          // if (fileName.contains(".mel"))
          // {
          // meal = Meal.open(fileName);
          // ShoppingListViewer slv = new ShoppingListViewer(meal);
          // }

        }
        catch (FileNotFoundException | ClassNotFoundException eDeezNuts)
        {
          return;
        }
        catch (IOException e1)
        {
          // TODO Auto-generated catch block
          return;
        }
      }
    });

    slistMeal.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        try
        {
          MealSerializer.resetFilePath();
          Meal openedMeal = MealSerializer.deserializeMeal();
          ShoppingList list = new ShoppingList(openedMeal);
          @SuppressWarnings("unused")
          ShoppingListViewer shoppingListViewer = new ShoppingListViewer(true, list);
        }
        catch (IOException | ClassNotFoundException eDeezNuts)
        {
          return;
        }

      }

    });

    convert.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        UnitConverter.getInstance(); // Spec 5.1
      }
    });

    calories.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        @SuppressWarnings("unused")
        Recipe recipe = new Recipe("Test", 123);
        CalorieCalculatorWindow.getInstance();
      }
    });

    about.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        JTextArea label = new JTextArea();

        label.setText(STRINGS.getString("TEXT"));
        label.setLineWrap(true);
        label.setBackground(new JButton().getBackground());
        label.setPreferredSize(new Dimension(300, 120));
        JOptionPane.showMessageDialog(null, label, null, JOptionPane.PLAIN_MESSAGE);
      }
    });

    menu.add(file);
    menu.add(edit);
    // menu.add(search);
    menu.add(view);
    menu.add(tools);
    // menu.add(help);

  }

  /**
   * Singleton-Pattern method for restricting more than one allocation of MainWindow.
   * 
   * @return instance if it hasn't been already initialized.
   */
  public static MainWindow getInstance()
  {
    if (instance == null)
    {
      instance = new MainWindow();
    }
    return instance;
  }

  /**
   * Sets the Main Window Visible.
   */
  public void show()
  {
    if (onlyOne)
    {
      main.setVisible(true);
      onlyOne = false;
    }
  }

  /**
   * Hides the Main Window. Might not be necessary.
   */
  public void hide()
  {
    main.setVisible(false);
    onlyOne = true;
  }

  /**
   * Creates an ImageIcon so that Images will be able to be seen within a jar file.
   * 
   * @param name
   *          name of the image you want to add
   * @return the image in the form of an Image Icon
   */
  private ImageIcon loadImageIcon(final String name)
  {
    URL url = this.getClass().getResource("/iconsAndResources/" + name);
    ImageIcon icon = new ImageIcon(url);
    return icon;
  }

}
