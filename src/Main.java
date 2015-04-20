/**
 * Created by Julio on 4/12/2015.
 */

import java.util.ArrayList;
import java.util.Scanner;

import twitter4j.*;

public class Main {

    public static void main(String[] args) throws TwitterException {
        Scanner in = new Scanner(System.in);
        System.out.println("Masukkan Pilihan Topik :"); //sekarang masukin manual, nanti tergantung dari pilihan
        System.out.println("1.Olahraga");
        System.out.println("2.Entertainment");
        System.out.println("3.Otomotif");
        String pilihan = in.nextLine();
        int whatTopic = Integer.parseInt(pilihan);
        Topic topic;
        if (whatTopic==1) { //Olahraga
            topic = new Topic("Olahraga", "Sepakbola,Basket,Renang");
            topic.getCategory().get(0).setKeyWord("sepakbola,persib,gol"); //keyword manual, nanti dari inputan user di web
            topic.getCategory().get(1).setKeyWord("dunk,miami heat");
            topic.getCategory().get(2).setKeyWord("kolam,tenggelam");
        } else if (whatTopic==2) { //Entertainment
            topic = new Topic("Entertainment", "Film,Game,Musik");
            topic.getCategory().get(0).setKeyWord("ff7, star wars, fifty shades of grey");
            topic.getCategory().get(1).setKeyWord("dota,warcraft,skyrim");
            topic.getCategory().get(2).setKeyWord("k-pop,rock,afgan");
        } else if (whatTopic==3) { //Otomotif
            topic = new Topic("Otomotif", "Mobil,Motor,F1");
            topic.getCategory().get(0).setKeyWord("lamborghini,tune up,mesin");
            topic.getCategory().get(1).setKeyWord("yamaha,honda");
            topic.getCategory().get(2).setKeyWord("ferrari,menang,sirkuit");
        } else if (whatTopic==4) { //Topik lainnya (Mau Travelling/Technology nih?)
            topic = new Topic("DUMMY","1,2,3");
        } else {
            topic = new Topic("DUMMY","1,2,3");
        }
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
