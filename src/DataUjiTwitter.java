import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
        FileWriter file = null;
        try {
            file = new FileWriter(new File(fileName));
        }catch(IOException e){
            e.printStackTrace();
        }
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("zWLzh04AonhHlD1KGBpMnMnOR")
                .setOAuthConsumerSecret("7BgtsE1IT6LBnwf7vXvfr7rQYRBqoN37F4DYiDdOdWfDnfU4fF")
                .setOAuthAccessToken("57534993-NC8ZX32cDbcjAMPkbbEEw3ho803xUVlijd2YKp2Gn")
                .setOAuthAccessTokenSecret("ZTrxBLIrK5lmDFTyVltQk9bUYorGBcjiF2NROaEbn7E25");
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        final FileWriter finalFile = file;
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                try {
                    String StatusString = "@" + status.getUser().getScreenName() + " - " + status.getText();
                    System.out.println(StatusString);
                    dataUjiStatuses.add(status);
                    dataUjiString.add(StatusString);
                    finalFile.write(StatusString);
                    finalFile.write("\n");
                    finalFile.flush();
                    if (dataUjiString.size() > 100) {
                        synchronized (lock) {
                            lock.notify();
                        }
                        System.out.println("unlocked");
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
            }

            @Override
            public void onStallWarning(StallWarning warning) {
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        double lat = -6.9147444;
        double longitude = 107.6098111;
        double lat1 = lat - 4;
        double longitude1 = longitude - 8;
        double lat2 = lat + 4;
        double longitude2 = longitude + 8;

        twitterStream.addListener(listener);
        //double[][] bb = {{longitude1, lat1}, {longitude2, lat2}};
        String [] keywords = new String [] {keyWord};
        FilterQuery fq = new FilterQuery();

        fq.track(keywords);
        //fq.locations(bb);
        twitterStream.filter(fq);
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        twitterStream.shutdown();

    }
}
