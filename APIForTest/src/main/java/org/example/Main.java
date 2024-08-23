package org.example;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.*;


public class Main {


    public static final String PATH = "https://app.seker.live/fm1/answer-file";
    public static final String MAGIC = "rItBHPN";
    public static final String DATA = "data.csv";

    public static void main(String[] args) throws FileNotFoundException {


        List<Person> lines = readFile();


        // א. ממוצע שנת הלידה של נשים
        int sum = 0;
        int count = 0;
        for (Person line : lines) {
            if (line.getGender().equals("Female")) {
                count++;
                sum += line.getBirthYear();
            }
        }
        if (count > 0) {
            System.out.println("Average birth year of females: " + (sum / count));

        }
        //ב.

        List<String> maleNames = new ArrayList<>();
        for (Person line : lines) {
            if (line.getGender().equals("Male")) {
                maleNames.add(line.getFirstName());

            }
        }

        // מציאת השם הנפוץ ביותר בשיטה פשוטה
        String mostCommonName = "";
        int maxCount = 0;
        for (int i = 0; i < maleNames.size(); i++) {
            String currentName1 = maleNames.get(i);
            int counting = 0;
            for (String currentName2 : maleNames) {
                if (currentName2.equals(currentName1)) {
                    counting++;
                }
            }
            if (counting > maxCount) {
                maxCount = counting;
                mostCommonName = currentName1;
            }
        }

        // הדפסת השם הנפוץ ביותר
        if (!mostCommonName.isEmpty()) {
            System.out.println("Most common male name: " + mostCommonName);
        } else {
            System.out.println("No male names found.");
        }


        //Write your code here!


        //ג.


        int sum1 = 0;
        int count1 = 0;
        for (Person line : lines) {
            if (line.getFirstName().toLowerCase().contains("A")){
                count1++;
                sum1 += line.getPrice();
            }
        }
        if (count1 > 0) {
            System.out.println("the average is:" + " " + (sum1 / count1));
        }
        //ד./
        int count2 = 0;
        for (Person line : lines) {
            if (line.getCity().startsWith("Lake")) {
                if (line.getPrice() > 400) {

                    count2++;
                }
            }
        }


        System.out.println(count2 + " " + "costumers paid more than 400$");


        String topCity = "";
        int maxPurchase = 0;

        // מעבר על כל הערים
        for (int i = 0; i < lines.size(); i++) {
            String currentCity1 = lines.get(i).getCity();
            int currentTotal = 0;

            // חישוב הסכום הכולל של רכישות בעיר הנוכחית
            for (int j = 0; j < lines.size(); j++) {
                String currentCity2 = lines.get(j).getCity();

                if (currentCity1.equals(currentCity2)) {
                    currentTotal += lines.get(j).getPrice();
                }
            }

            // בדיקה אם העיר הנוכחית היא בעלת הסכום הגבוה ביותר
            if (currentTotal > maxPurchase) {
                maxPurchase = currentTotal;
                topCity = currentCity1;
            }
        }

        // הדפסת התוצאה
        if (!topCity.isEmpty()) {
            System.out.println("City with the highest total purchase amount: " + topCity + "," + " with total of " + maxPurchase);
        } else {
            System.out.println("No cities found.");
        }

        // ו. מציאת תעודת הזהות שמופיעה יותר מפעם אחת
        int duplicatedID = -1;
        for (int i = 0; i < lines.size(); i++) {
            String currentId = lines.get(i).getId();
            for (Person person : lines) {
                if (person != lines.get(i) && person.getId().equals(currentId)) {
                    duplicatedID = Integer.parseInt(currentId);
                    break;
                }
            }
            if (duplicatedID != -1) {
                break;
            }
        }
        if (duplicatedID != -1) {
            System.out.println("Duplicated ID found: " + duplicatedID);
            sendAnswer(String.valueOf(duplicatedID), 6, MAGIC); // שליחת התשובה לשאלה 6
        } else {
            System.out.println("Duplicated ID not found");
        }


        Map<Color, Integer> colors = new HashMap<>();
        Color color1 = null;
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\danie\\AppData\\Local\\Temp\\c8696884-7825-4246-ab0e-860cce590ed8_daniel-test (3).zip.ed8\\ws_2024_a_helper\\images\\10.png"));
            for (int i=0;i<image.getWidth();i++){
                for (int j=0;j<image.getHeight();j++){
                    int color=image.getRGB(i,j);
                    color1=new Color(color);
                    if (colors.containsKey(color1)){
                        int count5=colors.get(color1)+1;
                        colors.put(color1,count5);
                    }else{
                        colors.put(color1,1);
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.print(colors);
        List<Map.Entry<Color,Integer>> colorList=new ArrayList<>((colors.entrySet()));
        System.out.println(colorList.get(0).getKey());
        System.out.println(colorList.get(3).getKey());
    }




    public static List<Person> readFile() {
        List<Person> lines = new ArrayList<>();
        try {
            File file = new File("C:\\Users\\danie\\Downloads\\data (1).csv");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                // Skip header if present
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] tokens = line.split(",");
                    Person person = new Person(
                            Integer.parseInt(tokens[0]),
                            tokens[1], tokens[2],
                            tokens[3],Integer.parseInt(tokens[4]),
                            tokens[5], tokens[6], Integer.parseInt(tokens[7])
                    );
                    lines.add(person);
                }
            } else {
                System.out.println("File not found.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;




    }
    public static void sendAnswer(String answer, int questionNumber, String magicValue) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URI uri = new URIBuilder(PATH)
                    .setParameter("magic", magicValue)
                    .setParameter("question", String.valueOf(questionNumber))
                    .setParameter("answer", answer)
                    .build();

            HttpPost post = new HttpPost(uri);
            post.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(post)) {
                int responseCode = response.getStatusLine().getStatusCode();
                System.out.println("Response Code: " + responseCode);

                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Response: " + responseBody);

                if (responseCode == 200) {
                    System.out.println("Answer sent successfully!");
                } else {
                    System.out.println("Error code: " + responseCode);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}




