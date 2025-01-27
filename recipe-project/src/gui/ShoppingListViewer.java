package gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import recipeBookComponents.Ingredient;
import recipeBookComponents.Recipe;
import recipeBookComponents.ShoppingList;
import recipeBookUtilities.ConvertUtil;

/**
 * GUI class that creates the Shopping List Viewer.
 * 
 * @author Nick Walla
 * @version 4.0
 */
public class ShoppingListViewer
{
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  private static final String SHOPPING_LIST_VIEWER = "K_SHOPPING_LIST_VIEWER";
  private static final String PRINT = "print.png";
  private static final String NUM_PPL = "NUM_PPL";
  private static final String SHPPING_LIST = "SHPPNG_LIST";
  private static final String GRAMS = "Grams";
  private static final String NLINE = "\n";
  private static final String FLOAT_STR = "%.2f %s";
  private static final String TWO_STR = "%s %s";
  
  private JFrame viewer;
  private JButton printButton;
  private JTextField textField;
  private JTextArea textArea;

  // added for internationalization
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  /**
   * Creates the shopping list viewer window and associated features.
   * 
   * @param list The ShoppingList in question
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public ShoppingListViewer(final ShoppingList list) throws IOException, ClassNotFoundException
  {
    // Set up main window
    viewer = new JFrame();
    viewer.setSize(800, 500);
    viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    viewer.setTitle(
        String.format(LOCALE, STRINGS.getString(SHOPPING_LIST_VIEWER) + " %s", list.getName()));
    viewer.setLocationRelativeTo(null);
    viewer.setResizable(false);
    viewer.setLayout(new BorderLayout());

    // Set up the button and text panels
    JPanel buttonPanel = new JPanel();
    JPanel textPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    textPanel.setLayout(new BorderLayout());
    buttonPanel.setBackground(Color.white);

    // Create and add button to button panel
    printButton = new JButton();
    ImageIcon printIcon = new ImageIcon(PRINT);
    printButton.setIcon(printIcon);

    // Create and add text field to a flow layout which will go in the north of the textPanel
    JLabel fieldLabel = new JLabel(STRINGS.getString(NUM_PPL));
    JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    textField = new JTextField();
    textField.setColumns(5);

    fieldPanel.add(fieldLabel);
    fieldPanel.add(textField);
    textPanel.add(fieldPanel, BorderLayout.NORTH);

    // Create and add text area and label to the center of the textPanel
    JPanel shoppingPanel = new JPanel(new BorderLayout());
    // JLabel areaLabel = new JLabel("Shopping List");
    textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scroll = new JScrollPane(textArea);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Create border around text area. The label will be the north border
    shoppingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
        STRINGS.getString(SHPPING_LIST)));

    // adding the text area and scroll bar to the panel
    shoppingPanel.add(scroll, BorderLayout.CENTER);
    textPanel.add(shoppingPanel, BorderLayout.CENTER);

    // add the textPanel and buttonPanel to the viewer and set visible
    viewer.add(buttonPanel, BorderLayout.NORTH);
    viewer.add(textPanel, BorderLayout.CENTER);
    viewer.setVisible(true);

    // Make the text area react to input in the text field
    textField.getDocument().addDocumentListener(new DocumentListener()
    {

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        String newL = NLINE;
        textArea.setText("");

        try
        {
          int input = Integer.parseInt(textField.getText());

          textArea.append(NLINE);
          textArea.append(newL);

          for (Ingredient ingredient : list.getShoppingList())
          {
            double multiplier = (double) input / list.getRecipes().get(0).getServings();
            double amount = ingredient.getAmount() * multiplier;

            double gramAmount = ConvertUtil.convert(ingredient.getAmount(), ingredient.getUnit(),
                GRAMS, ingredient.getGramsPerMl());

            if (ingredient.getHasCalInfo())
            {
              @SuppressWarnings("unused")
              double cals = gramAmount * ingredient.getCalsPerGram();

              textArea
                  .append(String.format(LOCALE, FLOAT_STR, amount, ingredient.toStringShopping()));
              textArea.append(newL);

            }
            else
            {
              textArea.append(String.format(FLOAT_STR, amount, ingredient.toStringShopping()));
              textArea.append(newL);
            }

          }

        }
        catch (NumberFormatException nfe)
        {
          // Should catch invalid input in the text field
        }

      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        textArea.setText(null);

      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        // TODO Auto-generated method stub

      }

    });
  }

  /**
   * Creates the shoppingListViewer.
   * @param isMeal Checks to see if what is passed in is a meal
   * @param list The list of ingredients and values to be used
   * @throws IOException If the I/O is invalid
   * @throws ClassNotFoundException If the class is invalid
   */
  public ShoppingListViewer(final boolean isMeal, final ShoppingList list)
      throws IOException, ClassNotFoundException
  {

    // Set up main window
    viewer = new JFrame();
    viewer.setSize(800, 500);
    viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    viewer.setTitle(
        String.format(TWO_STR, STRINGS.getString(SHOPPING_LIST_VIEWER), list.getName()));
    viewer.setLocationRelativeTo(null);
    viewer.setResizable(false);
    viewer.setLayout(new BorderLayout());

    // Set up the button and text panels
    JPanel buttonPanel = new JPanel();
    JPanel textPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    textPanel.setLayout(new BorderLayout());
    buttonPanel.setBackground(Color.white);

    // Create and add button to button panel
    printButton = new JButton();
    ImageIcon printIcon = new ImageIcon(PRINT);
    printButton.setIcon(printIcon);

    // Create and add text field to a flow layout which will go in the north of the textPanel
    JLabel fieldLabel = new JLabel(STRINGS.getString(NUM_PPL));
    JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    textField = new JTextField();
    textField.setColumns(5);

    fieldPanel.add(fieldLabel);
    fieldPanel.add(textField);
    textPanel.add(fieldPanel, BorderLayout.NORTH);

    // Create and add text area and label to the center of the textPanel
    JPanel shoppingPanel = new JPanel(new BorderLayout());
    // JLabel areaLabel = new JLabel("Shopping List");
    textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scroll = new JScrollPane(textArea);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Create border around text area. The label will be the north border
    shoppingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
        STRINGS.getString(SHPPING_LIST)));

    // adding the text area and scroll bar to the panel
    shoppingPanel.add(scroll, BorderLayout.CENTER);
    textPanel.add(shoppingPanel, BorderLayout.CENTER);

    // add the textPanel and buttonPanel to the viewer and set visible
    viewer.add(buttonPanel, BorderLayout.NORTH);
    viewer.add(textPanel, BorderLayout.CENTER);
    viewer.setVisible(true);

    // Make the text area react to input in the text field
    textField.getDocument().addDocumentListener(new DocumentListener()
    {

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        String newL = NLINE;
        textArea.setText("");

        try
        {
          int input = Integer.parseInt(textField.getText());

          textArea.append(newL);

          for (Recipe recipe : list.getRecipes())
          {
            for (Ingredient ingredient : recipe.getIngredients())
            {
              double multiplier = (double) input / recipe.getServings();
              double amount = ingredient.getAmount() * multiplier;
              double gramAmount = ConvertUtil.convert(ingredient.getAmount(), ingredient.getUnit(),
                  GRAMS, ingredient.getGramsPerMl());
              @SuppressWarnings("unused")
              double cals = gramAmount * ingredient.getCalsPerGram();

              textArea.append(String.format(FLOAT_STR, amount, ingredient.toStringShopping()));
              textArea.append(newL);

            }
          }

        }
        catch (NumberFormatException nfe)
        {
          // Should catch invalid input in the text field
        }

      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        textArea.setText(null);

      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        // TODO Auto-generated method stub

      }

    });
  }

  /**
   * Updates the title of the ShoppingListViewer to contain the new title passed in.
   * @param newTitle A String to be added to the title of the ShoppingListViewer window
   */
  public void updateTitle(final String newTitle)
  {
    viewer.setTitle(String.format(TWO_STR, STRINGS.getString(SHOPPING_LIST_VIEWER), newTitle));
  }

}
