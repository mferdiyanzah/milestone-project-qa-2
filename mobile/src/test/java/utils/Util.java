package utils;

public class Util {
  public Util() {
  }

  public static String uppercaseFirstWord(String input) {
    String[] words = input.split(" ");

    words[0] = words[0].toUpperCase();

    return String.join(" ", words);
  }

  public static String capitalizeFirstLetter(String word) {
    if (word == null || word.isEmpty()) {
      return word; // return the word as-is if it's null or empty
    }
    return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
  }
}
