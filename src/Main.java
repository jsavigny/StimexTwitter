/**
 * Created by Julio on 4/12/2015.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

    public static void main(String[] args) throws TwitterException {
       DataUjiTwitter Streamer = new DataUjiTwitter();
        Streamer.Stream("Twitters.txt");

    }
}
