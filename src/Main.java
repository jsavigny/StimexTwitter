/**
 * Created by Julio on 4/12/2015.
 */

import java.util.ArrayList;
import java.util.Scanner;

import twitter4j.*;

public class Main {

    public static void main(String[] args) throws TwitterException {
        Scanner in = new Scanner(System.in);
        for (String s: args) {
            System.out.println(s);
        }

        String pilihan = args[0];
        String hashKeyWord = args[1];
        ArrayList<String> keyWords = new ArrayList<>();
        keyWords.add(args[2]);
        keyWords.add(args[3]);
        keyWords.add(args[4]);
        String whatAlgorithm = args[5];
        int whatTopic = Integer.parseInt(pilihan);
        Topic topic;
        if (whatTopic==1) { //Olahraga
            topic = new Topic("Olahraga", "Sepakbola,Basket,Renang");
        } else if (whatTopic==2) { //Entertainment
            topic = new Topic("Entertainment", "Film,Game,Musik");
        } else if (whatTopic==3) { //Otomotif
            topic = new Topic("Otomotif", "Mobil,Motor,Balapan");
        } else if (whatTopic==4) { //Topik lainnya (Mau Travelling/Technology nih?)
            topic = new Topic("Travelling","Gunung,Pantai,Kota");
        } else {
            topic = new Topic("DUMMY","1,2,3");
        }
        topic.getCategory().get(0).setKeyWord(keyWords.get(0));
        topic.getCategory().get(1).setKeyWord(keyWords.get(1));
        topic.getCategory().get(2).setKeyWord(keyWords.get(2));
        System.out.println("Masukkan Keyword Pencarian Twitter :");
        String twitKeyWord = in.nextLine();
        DataUjiTwitter Streamer = new DataUjiTwitter();
        Streamer.Stream("Twitters.txt", twitKeyWord);
        for (int i=0; i<Streamer.dataUjiString.size(); i++){
            Category cat1 = topic.getCategory().get(0);
            Category cat2 = topic.getCategory().get(1);
            Category cat3 = topic.getCategory().get(2);
            for (int keyWordKe=0; keyWordKe < cat1.getKeyWord().size(); keyWordKe++){
                //Lakukan pencarian KMP/Boyer untuk category 1
            }
            for (int keyWordKe=0; keyWordKe < cat2.getKeyWord().size(); keyWordKe++){
                //Lakukan pencarian KMP/Boyer untuk category 2
            }
            for (int keyWordKe=0; keyWordKe < cat3.getKeyWord().size(); keyWordKe++){
                //Lakukan pencarian KMP/Boyer untuk category 3
            }
        }

    }
}
