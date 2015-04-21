/**
 * Created by Julio on 4/12/2015.
 */

import java.util.ArrayList;
import java.util.Scanner;

import twitter4j.*;

import javax.xml.transform.Result;

public class Main {

    public static void main(String[] args) throws TwitterException {
        Scanner in = new Scanner(System.in);

        String pilihan = args[0];
        String twitKeyWord = args[1];
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
        ResultArray resultOne= new ResultArray(topic.getTopic(),topic.getCategory().get(0).getName());
        ResultArray resultTwo= new ResultArray(topic.getTopic(),topic.getCategory().get(1).getName());
        ResultArray resultThree= new ResultArray(topic.getTopic(),topic.getCategory().get(2).getName());
        ResultArray resultUnknown= new ResultArray("Unknown");
        topic.getCategory().get(0).setKeyWord(keyWords.get(0));
        //System.out.println("TOPIK : "+topic.getCategory().get(0).getKeyWord().get(0));
        topic.getCategory().get(1).setKeyWord(keyWords.get(1));
        topic.getCategory().get(2).setKeyWord(keyWords.get(2));
        DataUjiTwitter Streamer = new DataUjiTwitter();
        Streamer.Stream("Twitters.txt", twitKeyWord);
        Category cat1 = topic.getCategory().get(0);
        Category cat2 = topic.getCategory().get(1);
        Category cat3 = topic.getCategory().get(2);

        for (int i=0; i<Streamer.dataUjiStatuses.size(); i++){
            String status = Streamer.dataUjiStatuses.get(i).getText();
            String tweetString = "@"+Streamer.dataUjiStatuses.get(i).getUser().getScreenName()+" : "+Streamer.dataUjiStatuses.get(i).getText();
            int pos1=-1,pos2=-1,pos3=-1;
            for (int j=0; j < cat1.getKeyWord().size(); j++){
                //Lakukan pencarian KMP/Boyer untuk category 1
                String keyWordCat1=cat1.getKeyWord().get(j);
                if (whatAlgorithm.equals("KMP")){
                    KMP searcher1 = new KMP();
                    if (pos1==-1) {
                        pos1 = searcher1.kmpMatch(status, keyWordCat1);
                    }
                } else {//Boyer-Moore
                    BoyerMoore searcher1 = new BoyerMoore();
                    if (pos1==-1) {
                        pos1 = searcher1.bmMatch(status, keyWordCat1);
                    }
                }
            }
            for (int j=0; j < cat2.getKeyWord().size(); j++){
                //Lakukan pencarian KMP/Boyer untuk category 2
                String keyWordCat2=cat2.getKeyWord().get(j);
                if (whatAlgorithm.equals("KMP")){
                    KMP searcher2 = new KMP();
                    if (pos2==-1) {
                        pos2 = searcher2.kmpMatch(status, keyWordCat2);
                    }
                } else {//Boyer-Moore
                    BoyerMoore searcher2 = new BoyerMoore();
                    if (pos2==-1) {
                        pos2 = searcher2.bmMatch(status, keyWordCat2);
                    }
                }
            }
            for (int j=0; j < cat3.getKeyWord().size(); j++){
                //Lakukan pencarian KMP/Boyer untuk category 3
                String keyWordCat3=cat3.getKeyWord().get(j);
                if (whatAlgorithm.equals("KMP")){
                    KMP searcher3 = new KMP();
                    if (pos3==-1) {
                        pos3 = searcher3.kmpMatch(status, keyWordCat3);
                    }
                } else {//Boyer-Moore
                    BoyerMoore searcher3 = new BoyerMoore();
                    if (pos3==-1) {
                        pos3 = searcher3.bmMatch(status, keyWordCat3);
                    }
                }
            }
            if ((pos1 == -1)&&(pos2 == -1)&&(pos3 == -1)){ //unknown
                resultUnknown.add(tweetString);
            } else if ((pos1 != -1)&&(pos2 == -1 )&&(pos3 == -1)){
                resultOne.add(tweetString);
            } else if ((pos1 == -1)&&(pos2 != -1 )&&(pos3 == -1)){
                resultTwo.add(tweetString);
            } else if ((pos1 == -1)&&(pos2 == -1 )&&(pos3 != -1)){
                resultThree.add(tweetString);
            } else if ((pos1 != -1)&&(pos2 != -1 )&&(pos3 == -1)){
                if ((pos1)<(pos2)){
                    resultOne.add(tweetString);
                } else {
                    resultTwo.add(tweetString);
                }
            } else if ((pos1 != -1)&&(pos2 == -1 )&&(pos3 != -1)){
                if ((pos1)<(pos3)){
                    resultOne.add(tweetString);
                } else {
                    resultThree.add(tweetString);
                }
            } else if ((pos1 == -1)&&(pos2 != -1 )&&(pos3 != -1)){
                if ((pos2)<(pos3)){
                    resultTwo.add(tweetString);
                } else {
                    resultThree.add(tweetString);
                }
            } else if ((pos1 != -1)&&(pos2 != -1 )&&(pos3 != -1)) {
                if (((pos1) < (pos2)) && ((pos1) < (pos3))) {
                    resultOne.add(tweetString);
                } else if (((pos2) < (pos1)) && ((pos2) < (pos3))) {
                    resultTwo.add(tweetString);
                } else {
                    resultThree.add(tweetString);
                }
            }

        }
        System.out.println("Kategori "+resultOne.getTopic()+" - "+resultOne.getCategory());
        for (int i=0;i<resultOne.getResult().size();i++){
            String tweets = resultOne.getResult().get(i);
            System.out.println(tweets);
        }
        System.out.println();
        System.out.println("Kategori "+resultTwo.getTopic()+" - "+resultTwo.getCategory());
        for (int i=0;i<resultTwo.getResult().size();i++){
            String tweets = resultTwo.getResult().get(i);
            System.out.println(tweets);
        }
        System.out.println();
        System.out.println("Kategori "+resultThree.getTopic()+" - "+resultThree.getCategory());
        for (int i=0;i<resultThree.getResult().size();i++){
            String tweets = resultThree.getResult().get(i);
            System.out.println(tweets);
        }
        System.out.println();
        System.out.println("Kategori Unknown");
        for (int i=0;i<resultUnknown.getResult().size();i++){
            String tweets = resultUnknown.getResult().get(i);
            System.out.println(tweets);
        }

    }
}
