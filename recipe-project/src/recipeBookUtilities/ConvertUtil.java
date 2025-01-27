package recipeBookUtilities;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Helper class containing conversion methods for GUI output.
 * 
 * @author Nick Walla
 * @version 4/25/2023
 */
public class ConvertUtil
{
  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  // --==CONVERTER ITEMS==--
  public static final String BLANK = "";
  public static final String CUPSS = STRINGS.getString("CUPS");
  public static final String DRAMSS = STRINGS.getString("DRAMS");
  public static final String FLOS = STRINGS.getString("FL_OZ");
  public static final String GALS = STRINGS.getString("GALLONS");
  public static final String GRAMSS = STRINGS.getString("GRAMS");
  public static final String MLS = STRINGS.getString("ML");
  public static final String OZS = STRINGS.getString("OZ");
  public static final String PINTSS = STRINGS.getString("PINTS");
  public static final String PINCHESS = STRINGS.getString("PINCHES");
  public static final String LBSS = STRINGS.getString("POUNDS");
  public static final String TBSPSS = STRINGS.getString("TB");
  public static final String TSPSS = STRINGS.getString("TSP");
  public static final String QUARTSS = STRINGS.getString("QUARTS");
  public static final String TYPE_VOLUME = "Volume";
  public static final String TYPE_MASS = "Mass";
  public static final String TYPE_INVALID = "Invalid";
  // --==###############==--

  // --==LIST OF UNITS==--
  private static final String[] UNITS = {BLANK, CUPSS, DRAMSS, FLOS, GALS, GRAMSS, MLS, OZS, PINTSS,
      PINCHESS, LBSS, TBSPSS, TSPSS, QUARTSS};
  // --==#############==--

  private static String typeTo = new String();
  private static String typeFrom = new String();

  /**
   * Gets the list of units of measurement from GuiHelp.
   * 
   * @return the list of units
   */
  public static String[] getUnits()
  {
    return UNITS; // Removes repeating code
  }

  /**
   * Calls the calculators for each entry.
   * 
   * @param inputValue
   *          the value entered
   * @param inputUnit
   *          the unit of the value entered
   * @param outputUnit
   *          the unit the value is being converted to
   * @param density
   *          the density to convert mass to volume & vice versa
   * @return the value of the converted unit
   */
  public static double convert(final double inputValue, final String inputUnit,
      final String outputUnit, final double density)
  {
    typeFrom = getType(outputUnit);
    Double valueInGrams = convertToGramsOrML(inputValue, inputUnit);
    valueInGrams = weighDensity(valueInGrams, outputUnit, density);
    return convertFromGramsOrML(valueInGrams, outputUnit);
  }

  /**
   * Converts the input value to either grams or ml depending on the unit that is passed.
   * 
   * @param value
   *          the value to be converted to grams
   * @param unit
   *          the type of unit it was originally
   * @return the value converted to grams
   */
  public static Double convertToGramsOrML(final double value, final String unit)
  {

    Double newVal = 0.0;
    if (GRAMSS.equals(unit))
    {
      newVal = value;
      typeTo = TYPE_MASS;
    }
    else if (MLS.equals(unit))
    {
      newVal = value;
      typeTo = TYPE_VOLUME;
    }
    else if (DRAMSS.equals(unit))
    {
      newVal = value * 1.7718452;
      typeTo = TYPE_MASS;
    }
    else if (CUPSS.equals(unit))
    {
      newVal = value * 236.5882365;
      typeTo = TYPE_VOLUME;
    }
    else if (PINTSS.equals(unit))
    {
      newVal = value * 473.176473;
      typeTo = TYPE_VOLUME;
    }
    else if (FLOS.equals(unit))
    {
      newVal = value * 28.4130625;
      typeTo = TYPE_VOLUME;
    }
    else if (GALS.equals(unit))
    {
      newVal = value * 3785.411784;
      typeTo = TYPE_VOLUME;
    }
    else if (OZS.equals(unit))
    {
      newVal = value * 28.3495231;
      typeTo = TYPE_MASS;
    }
    else if (PINCHESS.equals(unit))
    {
      newVal = value * 0.3589235;
      typeTo = TYPE_VOLUME;
    }
    else if (LBSS.equals(unit))
    {
      newVal = value * 453.59237;
      typeTo = TYPE_MASS;
    }
    else if (TBSPSS.equals(unit))
    {
      newVal = value * 14.7867648;
      typeTo = TYPE_VOLUME;
    }
    else if (TSPSS.equals(unit))
    {
      newVal = value * 4.92892159;
      typeTo = TYPE_VOLUME;
    }
    else if (QUARTSS.equals(unit))
    {
      newVal = value * 946.352946;
      typeTo = TYPE_VOLUME;
    }
    else
    {
      newVal = Double.NaN;
      typeTo = TYPE_INVALID;
    }
    return newVal;
  }

  /**
   * Converts the value from grams (or ml depending on the unit) to it's desired unit of
   * measurement.
   * 
   * @param value
   *          the value in grams
   * @param unit
   *          what the value in grams will be converted to
   * @return the value converted to the new unit
   */
  public static Double convertFromGramsOrML(final double value, final String unit)
  {
    if (Double.isNaN(value)) // edit for coverage
      return Double.NaN;
    double newVal = 0;
    if (GRAMSS.equals(unit))
    {
      newVal = value;
    }
    else if (MLS.equals(unit))
    {
      newVal = value;
    }
    else if (DRAMSS.equals(unit))
    {
      newVal = value / 1.7718452;
    }
    else if (CUPSS.equals(unit))
    {
      newVal = value / 236.5882365;
    }
    else if (PINTSS.equals(unit))
    {
      newVal = value / 473.176473;
    }
    else if (FLOS.equals(unit))
    {
      newVal = value / 28.4130625;
    }
    else if (GALS.equals(unit))
    {
      newVal = value / 3785.411784;
    }
    else if (OZS.equals(unit))
    {
      newVal = value / 28.3495231;
    }
    else if (PINCHESS.equals(unit))
    {
      newVal = value / 0.3589235;
    }
    else if (LBSS.equals(unit))
    {
      newVal = value / 453.59237;
    }
    else if (TBSPSS.equals(unit))
    {
      newVal = value / 14.7867648;
    }
    else if (TSPSS.equals(unit))
    {
      newVal = value / 4.92892159;
    }
    else if (QUARTSS.equals(unit))
    {
      newVal = value / 946.352946;
    }
    else
    {
      newVal = Double.NaN;
    }

    return newVal;
  }

  /**
   * Weighs the density appropriately.
   * 
   * @param value
   *          the value to be weighed
   * @param unit
   *          the unit that the value is being converted to
   * @param density
   *          the density for value to be weighed against
   * @return the correct representation of value weight against density
   */
  public static Double weighDensity(final double value, final String unit, final double density)
  {
    double gramsPerMl; // For density argument modification
    if (density < 0)
    {
      gramsPerMl = 1; // Assumes 1ml is 1 gram
    }
    else
    {
      gramsPerMl = density;
    }

    typeFrom = getType(unit);

    Double newVal = 0.0;
    if (typeTo.equals(TYPE_MASS) && typeFrom.equals(TYPE_MASS))
    {
      newVal = value;
    }
    else if (typeTo.equals(TYPE_MASS) && typeFrom.equals(TYPE_VOLUME))
    {
      newVal = value / gramsPerMl;
    }
    else if (typeTo.equals(TYPE_VOLUME) && typeFrom.equals(TYPE_VOLUME))
    {
      newVal = value;
    }
    else if (typeTo.equals(TYPE_VOLUME) && typeFrom.equals(TYPE_MASS))
    {
      newVal = value * gramsPerMl;
    }
    else
    {
      return Double.NaN;
    }
    return newVal;
  }

  /**
   * Gets the appropriate type of weight according to the unit that is passed.
   * 
   * @param unit
   *          Is the value that is checked to see if it is of type mass or volume
   * @return the type of weight the unit is
   */
  public static String getType(final String unit)
  {
    String type;
    if (GRAMSS.equals(unit))
    {
      type = TYPE_MASS;
    }
    else if (MLS.equals(unit))
    {
      type = TYPE_VOLUME;
    }
    else if (DRAMSS.equals(unit))
    {
      type = TYPE_MASS;
    }
    else if (CUPSS.equals(unit))
    {
      type = TYPE_VOLUME;
    }
    else if (PINTSS.equals(unit))
    {
      type = TYPE_VOLUME;
    }
    else if (FLOS.equals(unit))
    {
      type = TYPE_VOLUME;
    }
    else if (GALS.equals(unit))
    {
      type = TYPE_VOLUME;
    }
    else if (OZS.equals(unit))
    {
      type = TYPE_MASS;
    }
    else if (PINCHESS.equals(unit))
    {
      type = TYPE_VOLUME;
    }
    else if (LBSS.equals(unit))
    {
      type = TYPE_MASS;
    }
    else if (TBSPSS.equals(unit))
    {
      type = TYPE_VOLUME;
    }
    else if (TSPSS.equals(unit))
    {
      type = TYPE_VOLUME;
    }
    else if (QUARTSS.equals(unit))
    {
      type = TYPE_VOLUME;
    }
    else
    {
      type = TYPE_INVALID;
    }
    return type;
  }

  /**
   * Gets the conversion value for each unit of measurement to converting to grams.
   * 
   * @param unit
   *          Is the value that is checked to see if it is of type mass or volume
   * @return the conversion value
   */
  public static Double getConversionToGrams(final String unit)
  {
    Double unitToGrams = 0.0;
    if (GRAMSS.equals(unit))
    {
      unitToGrams = 1.0; // Grams * 1 = itself
    }
    else if (MLS.equals(unit))
    {
      unitToGrams = 1.0; // Assuming 1 mL is 1g
    }
    else if (DRAMSS.equals(unit))
    {
      unitToGrams = 1.7718452;
    }
    else if (CUPSS.equals(unit))
    {
      unitToGrams = 236.5882365;
    }
    else if (PINTSS.equals(unit))
    {
      unitToGrams = 473.176473;
    }
    else if (FLOS.equals(unit))
    {
      unitToGrams = 28.4130625;
    }
    else if (GALS.equals(unit))
    {
      unitToGrams = 3785.411784;
    }
    else if (OZS.equals(unit))
    {
      unitToGrams = 28.3495231;
    }
    else if (PINCHESS.equals(unit))
    {
      unitToGrams = 0.3589235;
    }
    else if (LBSS.equals(unit))
    {
      unitToGrams = 453.59237;
    }
    else if (TBSPSS.equals(unit))
    {
      unitToGrams = 14.7867648;
    }
    else if (TSPSS.equals(unit))
    {
      unitToGrams = 4.92892159;
    }
    else if (QUARTSS.equals(unit))
    {
      unitToGrams = 946.352946;
    }
    else
    {
      unitToGrams = Double.NaN;
    }
    return unitToGrams;

  }

  // /**
  // * Does the valid conversions to get the grams of an ingredient.
  // * @param value the amount of the ingredient needed.
  // * @param unit the unit it is measured in
  // * @param density the density for conversions that require it
  // * @return the value of the ingredient converted to grams
  // */
  // public static Double convertToGrams(final double value, final String unit, final double
  // density)
  // {
  // Double toGrams = 0.0;
  // String tempType = new String();
  // tempType = typeTo; // for bringing type back to where it was before this executed
  // toGrams = convertToGramsOrML(value, unit);
  // typeTo = TYPE_MASS;
  // toGrams = weighDensity(toGrams, unit, density);
  // typeTo = tempType;
  // return toGrams;
  // }
}
