import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julio on 4/16/2015.
 */
public class DataUjiTwitter {
    public ArrayList<String> dataUjiString;
    public ArrayList<Status> dataUjiStatuses;
    DataUjiTwitter(){
        dataUjiStatuses= new ArrayList<>(100);
        dataUjiString= new ArrayList<>(100);
    }
    public void Stream(String fileName,String keyWord){
        Object lock = new Object();
        PrintWriter tweetWriter = null;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("zWLzh04AonhHlD1KGBpMnMnOR")
                .setOAuthConsumerSecret("7BgtsE1IT6LBnwf7vXvfr7rQYRBqoN37F4DYiDdOdWfDnfU4fF")
                .setOAuthAccessToken("57534993-NC8ZX32cDbcjAMPkbbEEw3ho803xUVlijd2YKp2Gn")
                .setOAuthAccessTokenSecret("ZTrxBLIrK5lmDFTyVltQk9bUYorGBcjiF2NROaEbn7E25");
        try
        {
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            Query query = new Query(keyWord);
            query.setCount(100);
            
            QueryResult result = twitter.search(query);

            tweetWriter = new PrintWriter(new File(fileName));

            List<Status> dataUjiStatuses = result.getTweets();
            System.out.println("Number of tweets:"+ dataUjiStatuses.size());
            for (int tweet=0;tweet<dataUjiStatuses.size();tweet++ )
            {
                String tempTweet = "@"+dataUjiStatuses.get(tweet).getUser().getScreenName()+" : "+dataUjiStatuses.get(tweet).getText();
                dataUjiString.add(tempTweet);
                System.out.println(tempTweet);
            }
            if(query != null)
            {
                result = twitter.search(query);
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(tweetWriter != null )
            {
                tweetWriter.close();
            }
        }
    }
}
