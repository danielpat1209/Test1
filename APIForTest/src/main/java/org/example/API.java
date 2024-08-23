package org.example;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class API {
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
            int averageBirthYear = sum / count;
            System.out.println("Average birth year of females: " + averageBirthYear);

            sendAnswer(String.valueOf(averageBirthYear), 1, MAGIC); // שליחת התשובה לשאלה 1
        }

        // ב. השם הנפוץ ביותר אצל גברים
        List<String> maleNames = new ArrayList<>();
        for (Person line : lines) {
            if (line.getGender().equals("Male")) {
                maleNames.add(line.getFirstName());
            }
        }

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

        if (!mostCommonName.isEmpty()) {
            System.out.println("Most common male name: " + mostCommonName);
            sendAnswer(mostCommonName, 2, MAGIC); // שליחת התשובה לשאלה 2
        }

        // ג. ממוצע מחיר הרכישה של לקוחות עם שם פרטי שמתחיל באות "A" או "a"
        int sum1 = 0;
        int count1 = 0;
        for (Person line : lines) {
            if (line.getFirstName().contains("A") || line.getFirstName().contains("a")) {
                count1++;
                sum1 += line.getPrice();
            }
        }
        if (count1 > 0) {
            int averagePrice = sum1 / count1;
            System.out.println("the average is:" + " " + averagePrice);
            sendAnswer(String.valueOf(averagePrice), 3, MAGIC); // שליחת התשובה לשאלה 3
        }

        // ד. ספירת לקוחות מעיר שמתחילה ב-"Lake" ושילמו מעל 400$
        int count2 = 0;
        for (Person line : lines) {
            if (line.getCity().startsWith("Lake")) {
                if (line.getPrice() > 400) {
                    count2++;
                }
            }
        }
        System.out.println(count2 + " " + "costumers paid more than 400$");
        sendAnswer(String.valueOf(count2), 4, MAGIC); // שליחת התשובה לשאלה 4

        // ה. איזו עיר היא בעלת סכום הרכישה הכולל הגבוה ביותר
        String topCity = "";
        int maxPurchase = 0;
        for (int i = 0; i < lines.size(); i++) {
            String currentCity1 = lines.get(i).getCity();
            int currentTotal = 0;
            for (int j = 0; j < lines.size(); j++) {
                String currentCity2 = lines.get(j).getCity();
                if (currentCity1.equals(currentCity2)) {
                    currentTotal += lines.get(j).getPrice();
                }
            }
            if (currentTotal > maxPurchase) {
                maxPurchase = currentTotal;
                topCity = currentCity1;
            }
        }
        if (!topCity.isEmpty()) {
            System.out.println("City with the highest total purchase amount: " + topCity + "," + " with total of " + maxPurchase);
            sendAnswer(topCity, 5, MAGIC); // שליחת התשובה לשאלה 5
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
    }

    public static List<Person> readFile() throws FileNotFoundException {
        List<Person> lines = new ArrayList<>();
        try {
            File file = new File("C:\\Users\\danie\\Downloads\\data (1).csv");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] tokens = line.split(",");
                    Person person = new Person(
                            Integer.parseInt(tokens[0]),
                            tokens[1], tokens[2],
                            tokens[3], Integer.parseInt(tokens[4]),
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
        CloseableHttpClient client = HttpClients.createDefault();
        try {


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
