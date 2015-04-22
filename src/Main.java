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
        String proxArg = args[6];
        int whatTopic = Integer.parseInt(pilihan);
        int proxy = Integer.parseInt(proxArg);
        Topic topic;
        if (whatTopic==1) { //Olahraga
            topic = new Topic("Olahraga", "Sepakbola,Basket,Renang");
        } else if (whatTopic==2) { //Entertainment
            topic = new Topic("Entertainment", "Film,Game,Musik");
        } else if (whatTopic==3) { //Otomotif
            topic = new Topic("Otomotif", "Mobil,Motor,Balapan");
        } else if (whatTopic==4) { //Travelling
            topic = new Topic("Travelling","Gunung,Pantai,Kota");
        } else {
            topic = new Topic("DUMMY","1,2,3");
        }
        ResultArray resultOne= new ResultArray(topic.getTopic(),topic.getCategory().get(0).getName()); //Result Array, hasil pencarian tweet masuk kategori mana
        ResultArray resultTwo= new ResultArray(topic.getTopic(),topic.getCategory().get(1).getName());
        ResultArray resultThree= new ResultArray(topic.getTopic(),topic.getCategory().get(2).getName());
        ResultArray resultUnknown= new ResultArray("Unknown");
        topic.getCategory().get(0).setKeyWord(keyWords.get(0));
        topic.getCategory().get(1).setKeyWord(keyWords.get(1));
        topic.getCategory().get(2).setKeyWord(keyWords.get(2));

        DataUjiTwitter Streamer = new DataUjiTwitter();
        Streamer.Stream("Twitters.txt", twitKeyWord, proxy);

        Category cat1 = topic.getCategory().get(0);
        Category cat2 = topic.getCategory().get(1);
        Category cat3 = topic.getCategory().get(2);

        for (int i=0; i<Streamer.dataUjiStatuses.size(); i++){
            Status status = Streamer.dataUjiStatuses.get(i);
            String statusString = status.getText();
            String url= "https://twitter.com/" + status.getUser().getScreenName()
                    + "/status/" + status.getId();
            String tweetString = "@"+status.getUser().getScreenName()+" : "+status.getText()+" "+url;
            int pos1=-1,pos2=-1,pos3=-1;
            int keyWord1Ke=0;
            int keyWord2Ke=0;
            int keyWord3Ke=0;
            for (int j=0; j < cat1.getKeyWord().size(); j++){
                //Lakukan pencarian KMP/Boyer untuk category 1
                String keyWordCat1=cat1.getKeyWord().get(j);
                if (whatAlgorithm.equals("KMP")){
                    KMP searcher1 = new KMP();
                    if (pos1==-1) {
                        pos1 = searcher1.kmpMatch(statusString, keyWordCat1);
                        keyWord1Ke=j;
                    }
                } else {//Boyer-Moore
                    BoyerMoore searcher1 = new BoyerMoore();
                    if (pos1==-1) {
                        pos1 = searcher1.bmMatch(statusString, keyWordCat1);
                        keyWord1Ke=j;
                    }
                }
            }
            for (int j=0; j < cat2.getKeyWord().size(); j++){
                //Lakukan pencarian KMP/Boyer untuk category 2
                String keyWordCat2=cat2.getKeyWord().get(j);
                if (whatAlgorithm.equals("KMP")){
                    KMP searcher2 = new KMP();
                    if (pos2==-1) {
                        pos2 = searcher2.kmpMatch(statusString, keyWordCat2);
                        keyWord2Ke=j;
                    }
                } else {//Boyer-Moore
                    BoyerMoore searcher2 = new BoyerMoore();
                    if (pos2==-1) {
                        pos2 = searcher2.bmMatch(statusString, keyWordCat2);
                        keyWord2Ke=j;
                    }
                }
            }
            for (int j=0; j < cat3.getKeyWord().size(); j++){
                //Lakukan pencarian KMP/Boyer untuk category 3
                String keyWordCat3=cat3.getKeyWord().get(j);
                if (whatAlgorithm.equals("KMP")){
                    KMP searcher3 = new KMP();
                    if (pos3==-1) {
                        pos3 = searcher3.kmpMatch(statusString, keyWordCat3);
                        keyWord3Ke=j;
                    }
                } else {//Boyer-Moore
                    BoyerMoore searcher3 = new BoyerMoore();
                    if (pos3==-1) {
                        pos3 = searcher3.bmMatch(statusString, keyWordCat3);
                        keyWord3Ke=j;
                    }
                }
            }
            if ((pos1 == -1)&&(pos2 == -1)&&(pos3 == -1)){ //unknown
                resultUnknown.add(tweetString);
            } else if ((pos1 != -1)&&(pos2 == -1 )&&(pos3 == -1)){
                String preStatus = statusString.substring(0,pos1);
                String theKey = statusString.substring(pos1,pos1+cat1.getKeyWord().get(keyWord1Ke).length());
                String postStatus = statusString.substring(pos1+cat1.getKeyWord().get(keyWord1Ke).length());
                String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                resultOne.add(tweetString);
            } else if ((pos1 == -1)&&(pos2 != -1 )&&(pos3 == -1)){
                String preStatus = statusString.substring(0,pos2);
                String theKey = statusString.substring(pos2,pos2+cat2.getKeyWord().get(keyWord2Ke).length());
                String postStatus = statusString.substring(pos2+cat2.getKeyWord().get(keyWord2Ke).length());
                String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                resultTwo.add(tweetString);
            } else if ((pos1 == -1)&&(pos2 == -1 )&&(pos3 != -1)){
                String preStatus = statusString.substring(0,pos3);
                String theKey = statusString.substring(pos3,pos3+cat2.getKeyWord().get(keyWord3Ke).length());
                String postStatus = statusString.substring(pos3+cat2.getKeyWord().get(keyWord3Ke).length());
                String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                resultThree.add(tweetString);
            } else if ((pos1 != -1)&&(pos2 != -1 )&&(pos3 == -1)){
                if ((pos1)<(pos2)){
                    String preStatus = statusString.substring(0,pos1);
                    String theKey = statusString.substring(pos1,pos1+cat1.getKeyWord().get(keyWord1Ke).length());
                    String postStatus = statusString.substring(pos1+cat1.getKeyWord().get(keyWord1Ke).length());
                    String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                    tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                    resultOne.add(tweetString);
                } else {
                    String preStatus = statusString.substring(0,pos2);
                    String theKey = statusString.substring(pos2,pos2+cat2.getKeyWord().get(keyWord2Ke).length());
                    String postStatus = statusString.substring(pos2+cat2.getKeyWord().get(keyWord2Ke).length());
                    String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                    tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                    resultTwo.add(tweetString);
                }
            } else if ((pos1 != -1)&&(pos2 == -1 )&&(pos3 != -1)){
                if ((pos1)<(pos3)){
                    String preStatus = statusString.substring(0,pos1);
                    String theKey = statusString.substring(pos1,pos1+cat1.getKeyWord().get(keyWord1Ke).length());
                    String postStatus = statusString.substring(pos1+cat1.getKeyWord().get(keyWord1Ke).length());
                    String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                    tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                    resultOne.add(tweetString);
                } else {
                    String preStatus = statusString.substring(0,pos3);
                    String theKey = statusString.substring(pos3,pos3+cat2.getKeyWord().get(keyWord3Ke).length());
                    String postStatus = statusString.substring(pos3+cat2.getKeyWord().get(keyWord3Ke).length());
                    String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                    tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                    resultThree.add(tweetString);
                }
            } else if ((pos1 == -1)&&(pos2 != -1 )&&(pos3 != -1)){
                if ((pos2)<(pos3)){
                    String preStatus = statusString.substring(0,pos2);
                    String theKey = statusString.substring(pos2,pos2+cat2.getKeyWord().get(keyWord2Ke).length());
                    String postStatus = statusString.substring(pos2+cat2.getKeyWord().get(keyWord2Ke).length());
                    String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                    tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                    resultTwo.add(tweetString);
                } else {
                    String preStatus = statusString.substring(0,pos3);
                    String theKey = statusString.substring(pos3,pos3+cat2.getKeyWord().get(keyWord3Ke).length());
                    String postStatus = statusString.substring(pos3+cat2.getKeyWord().get(keyWord3Ke).length());
                    String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                    tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                    resultThree.add(tweetString);
                }
            } else if ((pos1 != -1)&&(pos2 != -1 )&&(pos3 != -1)) {
                if (((pos1) < (pos2)) && ((pos1) < (pos3))) {
                    String preStatus = statusString.substring(0,pos1);
                    String theKey = statusString.substring(pos1,pos1+cat1.getKeyWord().get(keyWord1Ke).length());
                    String postStatus = statusString.substring(pos1+cat1.getKeyWord().get(keyWord1Ke).length());
                    String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                    tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                    resultOne.add(tweetString);
                } else if (((pos2) < (pos1)) && ((pos2) < (pos3))) {
                    String preStatus = statusString.substring(0,pos2);
                    String theKey = statusString.substring(pos2,pos2+cat2.getKeyWord().get(keyWord2Ke).length());
                    String postStatus = statusString.substring(pos2+cat2.getKeyWord().get(keyWord2Ke).length());
                    String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                    tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
                    resultTwo.add(tweetString);
                } else {
                    String preStatus = statusString.substring(0,pos3);
                    String theKey = statusString.substring(pos3,pos3+cat2.getKeyWord().get(keyWord3Ke).length());
                    String postStatus = statusString.substring(pos3+cat2.getKeyWord().get(keyWord3Ke).length());
                    String tweet=preStatus+"<b>"+theKey+"</b>"+postStatus;
                    tweetString = "@"+status.getUser().getScreenName()+" : "+tweet+" "+url;
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
