package is.ru.stringcalculator;

import java.util.Arrays;
import java.util.ArrayList;

public class Calculator {

    public static int add(String numbers){
        if(numbers.isEmpty()){
           return 0;        
        }
        if(numbers.contains("//")){
           String delimeter;
           String delString;
           String[] delArray;
           int cutoff;
           if(numbers.contains("[")){
              cutoff = findCutoff(numbers);
              String temp = buildString(numbers);    
              delimeter = temp.substring(0, temp.length()-1);
              delString = numbers.substring(cutoff+1);
           }
           else{
              delimeter = numbers.substring(2,3);
              delString = numbers.substring(4);
           }
           delArray = delString.split(delimeter);
           checkForNegatives(delArray);
           return findSum(delArray);   
        }
        if(numbers.contains(",") || numbers.contains("\n")){
           String[] array = numbers.split("(,)|(\n)");
           checkForNegatives(array);
           return findSum(array);
        }
        if(Integer.parseInt(numbers) > 1000){
           return 0;
        }
        return Integer.parseInt(numbers);   
    }

    private static int findSum(String[] array){
       int sum = 0;
       for(int i = 0; i < array.length; i++){
          if(Integer.parseInt(array[i]) <= 1000){
             sum = sum + Integer.parseInt(array[i]);
          }
       }
       return sum;
    }  
  
    private static void negativesNotAllowed(String[] array){
       ArrayList<String> negatives = new ArrayList<String>();
       for(int i = 0; i < array.length; i++){
           if(array[i].startsWith("-")){
               negatives.add(array[i]);
           }
       }
       String temp = negatives.toString();
       String neg = temp.substring(1, temp.length()-1).replaceAll("\\s","");
       throw new IllegalArgumentException("Negatives not allowed: " + neg);
    }

    private static void checkForNegatives(String[] array){
       for(int i = 0; i < array.length; i++){
           if(array[i].startsWith("-")){
               negativesNotAllowed(array);
           }
       }
    }
    
    private static int findCutoff(String numbers){
       int i = 0;
       while(true){
          Character ch = numbers.charAt(i);
          if(ch == '\n'){
             break;
          }
          i++;
       }
       return i;   
    }

    private static String buildString(String numbers){
       StringBuilder builder = new StringBuilder();
       int i = 0;
       while(true){
          Character ch = numbers.charAt(i);
          if(ch == '\n'){
             break;
          }
          if(ch == '['){
             builder.append("(");
             while(true){
                builder.append(numbers.charAt(i+1));
                i++;
                Character cha = numbers.charAt(i+1);
                if(cha == ']'){
                   builder.append(")|");
                   break;
                }
             }
          }
          i++;
       }
       return builder.toString();
    }
}
