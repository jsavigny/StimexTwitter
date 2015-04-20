import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Julio on 4/20/2015.
 */
public class Category {
    private String name; //Contoh : Sepakbola
    private Set<String> keyWord; //Contoh : persib, gol
    public Category(String n){
        name=n;
    }
    public void setKeyWord(String keyWordInput){
        String [] catKeyTemp = keyWordInput.split("\\s*,\\s*");
        keyWord = new HashSet<>(Arrays.asList(catKeyTemp));
    }
    public Set<String> getKeyWord(){
        return keyWord;
    }
    public String getName(){
        return name;
    }

}
