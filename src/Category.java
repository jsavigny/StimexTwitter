import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Julio on 4/20/2015.
 */
public class Category {
    private String name; //Contoh : Sepakbola
    private ArrayList<String> keyWord; //Contoh : persib, gol
    public Category(String n){
        name=n;
    }
    public void setKeyWord(String keyWordInput){
        String [] catKeyTemp = keyWordInput.split("\\s*,\\s*");
        keyWord = new ArrayList<>(Arrays.asList(catKeyTemp));
    }
    public ArrayList<String> getKeyWord(){
        return keyWord;
    }
    public String getName(){
        return name;
    }

}
