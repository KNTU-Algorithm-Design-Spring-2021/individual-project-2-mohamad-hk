import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class Jamesband {

    static HashMap<String , Boolean>  DICTIONARY ;
    static List<String> result = new ArrayList<>();

    public static void main(String []args) {
        DICTIONARY = readDictionary();
        Scanner input=new Scanner(System.in);
        System.out.println("Enter number of sentences");
        int m=input.nextInt();
        for (int i=0;i<m;i++) {
            System.out.println();
            System.out.println("Enter sentence");
            Break(input.next());
            System.out.print("Result: ");
            Result();
        }
    }

    public static HashMap<String , Boolean> readDictionary (){
        HashMap<String , Boolean> DICTIONARY = new HashMap<>();
        File file = new File("C:\\Users\\mohammad\\Desktop\\words_alpha.txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String st;
        try {
            while ((st = br.readLine()) != null) {
                DICTIONARY.put(st, true);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return DICTIONARY;
    }

    public static boolean Break(String sentence) {
        sentence = sentence.toLowerCase();
        int size = sentence.length();

        if (size == 0)
            return true;

        if (isValid(sentence)) {
            result.add(sentence);
            return true;
        }

        for (int i = size; i >0 ; i--) {
            if ( isValid ( sentence.substring(0, i) )
                    && Break (sentence.substring(i, size) )) {
                result.add(sentence.substring(0,i));
                return true;
            }
        }

        return false;
    }

    public static boolean isValid(String word){
        if(DICTIONARY.get(word) == null) {
            return false;
        } else if(DICTIONARY.get(word)) {
            return true;
        }
        return false;
    }

    public static void Result(){
        Collections.reverse(result);
        for(String str : result){
            System.out.print(str + " ");
        }
        System.out.println();
        result = new ArrayList<>();
    }
}