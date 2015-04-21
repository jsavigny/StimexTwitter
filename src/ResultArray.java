import java.util.ArrayList;

/**
 * Created by Julio on 4/21/2015.
 */
public class ResultArray {
    private ArrayList<String> result;
    private String topic;
    private String category;
    public ResultArray(String T, String C ){
        topic = T;
        category = C;
        result = new ArrayList<>();
    }
    public ResultArray(String T){
        topic = T;
        result = new ArrayList<>();
    }
    public String getTopic(){
        return topic;
    }
    public String getCategory(){
        return category;
    }
    public ArrayList<String> getResult(){
        return result;
    }
    public void add(String S){
        result.add(S);
    }
}
