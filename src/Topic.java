import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by Julio on 4/20/2015.
 */
public class Topic {
    private String topic;
    private ArrayList<Category> category;
    public Topic(String T, String C){
        topic = T;
        category=new ArrayList<>();
        String [] catTemp = C.split("\\s*,\\s*");
        ArrayList<String> catString;
        catString = new ArrayList<>(Arrays.asList(catTemp));
        for (int i=0; i<catString.size();i++){
            Category temp = new Category(catString.get(i));
            category.add(temp);
        }
    }
    public ArrayList<Category> getCategory(){
        return category;
    }
    public String getTopic(){
        return topic;
    }

}
