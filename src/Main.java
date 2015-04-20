/**
 * Created by Julio on 4/12/2015.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

    public static void main(String[] args) throws TwitterException {
       DataUjiTwitter Streamer = new DataUjiTwitter();
        Streamer.Stream("Twitters.txt","#happy");
        for (int i=0; i<Streamer.dataUjiString.size(); i++){
            KMP KMPSearcher = new KMP("happy");
            ArrayList <Integer> matches = new ArrayList<>(100);
            matches.add(KMPSearcher.search(Streamer.dataUjiString.get(i)));
            //List<Integer> matches = new BoyerMoore().match("puasa", Streamer.dataUjiString.get(i));
            for (Integer integer : matches)
                System.out.println("Match at : " + integer);
        }

    }
}
