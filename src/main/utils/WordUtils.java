package main.utils;

import java.util.stream.Collectors;

public class WordUtils {

  public static boolean doesTextContainRealWord(String textToCheck) {
    return doesTextContainRealWord(textToCheck, 5);
  }

  /*
    Checks a scrolling window of 4 to 6 characters (avg length of top 5000 chars is 6.32) throughout the first 100 characters of the text
    If 3 real words are found, the text has (probably) been decoded
    If less than 3 real words found, text (almost certainly) has not been decoded
   */
  public static boolean doesTextContainRealWord(String textToCheck, int wordsRequired) {
    int wordsFound = 0;
    for (int i = 4; i <= 6; i++) { //the size of the char window to check
      for (int currentCharStart = 0; currentCharStart < 101; currentCharStart++) { //loop through the first 100 characters of the text to check (to save time)
        if (isStringRealWord(textToCheck.substring(currentCharStart, currentCharStart + i).toLowerCase())) {
          if (wordsFound++ > wordsRequired) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static boolean doesTextContainPartialWords(String textToCheck) {
    int wordsFound = 0;
    for (int i = 4; i <= 6; i++) { //the size of the char window to check
      for (int currentCharStart = 0; currentCharStart < 101; currentCharStart++) { //loop through the first 100 characters of the text to check (to save time)
        if (isStringPartOfRealWord(textToCheck.substring(currentCharStart, currentCharStart + i).toLowerCase())) {
          if (wordsFound++ > 5) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public static boolean isStringPartOfRealWord(String stringToCheck) {
    //too many 2 letter words where being found and making the count a bit too incorrect
    for (String s : CommonWordsUtils.commonWords.stream().filter(s -> s.length() > 2).collect(Collectors.toList())) {
      if (s.contains(stringToCheck)) {
        return true;
      }
    }

    return false;
  }

  /*
    Checks if given string exists in top 10000 most common words
    String must be single word
   */
  public static boolean isStringRealWord(String stringToCheck) {
    for (String s : CommonWordsUtils.commonWords) {
      if (stringToCheck.equals(s)) {
        return true;
      }
    }

    return false;
  }


  public static int getAmountOfPartialAndRealWords(String textToCheck) {
    int wordsFound = 0;
    for (int i = 3; i <= 6; i++) { //the size of the char window to check
      for (int currentCharStart = 0; currentCharStart < 101; currentCharStart++) { //loop through the first 100 characters of the text to check (to save time)
        String currentWordToCheck = textToCheck.substring(currentCharStart, currentCharStart + i).toLowerCase();
        if (WordUtils.isStringRealWord(currentWordToCheck)) {
          wordsFound++;
        } else if (WordUtils.isStringPartOfRealWord(currentWordToCheck)) {
          wordsFound++;
        }
      }
    }
    return wordsFound;
  }

}