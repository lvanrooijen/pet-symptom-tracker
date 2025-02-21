package com.laila.pet_symptom_tracker.mainconfig;

public class TerminalColors {
  private static final String RED = "\u001B[38;2;255;0;0m";
  private static final String BLUE = "\u001B[38;2;0;120;255m";
  private static final String GREEN = "\u001B[38;2;0;200;0m";
  private static final String PINK = "\u001B[38;2;255;105;180m";
  private static final String RESET_COLOR = "\u001B[0m";
  private static final String CUSTOM_RESET_COLOR = "\u001B[38;2;0;145;110m";

  public static String setWarningColor(String message) {
    return RED + message + CUSTOM_RESET_COLOR;
  }

  public static String setInfoColor(String message) {
    return BLUE + message + CUSTOM_RESET_COLOR;
  }

  public static String setSuccessColor(String message) {
    return GREEN + message + CUSTOM_RESET_COLOR;
  }

  public static String setGirlyShit(String message) {
    return PINK + message + CUSTOM_RESET_COLOR;
  }
}
