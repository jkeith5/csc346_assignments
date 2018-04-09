import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Scanner;

public class WeatherMain {
    public static void main(String[] args) {
        String url = Credentials.getUrl();
        Scanner scan=new Scanner(System.in);
        System.out.println("Enter a state abbreviation(e.g. MO for Missouri): ");
        String stateTarget=scan.nextLine();
        System.out.println("Enter a city in above state: ");
        String cityTarget=scan.nextLine();
        cityTarget= parseCity(cityTarget);
        System.out.println(cityTarget);
        System.out.println("URL: "+ url);
        System.out.println("Table: " + Credentials.getTable());
        System.out.println("User: "+Credentials.getUser());
        System.out.println("Password: "+Credentials.getPassword());
        System.out.println("Port: "+Credentials.getPort());
        System.out.println("API Key: "+ Credentials.getApiKey());
        System.out.println("Done.");
        url = "http://api.wunderground.com/api/"+Credentials.getApiKey()+"/conditions/q/"+stateTarget+"/"+cityTarget+".xml";


        try {//this is to connect to the website and pull the data
            Document doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String parseCity(String cityTarget){//this takes the city entered and parses all spaces into underscores
        String result=cityTarget;
        if (result.trim().contains(" ")){
            result = result.replaceAll("\\s+","_");
            System.out.println("1234444");
        }
        return result;
    }


}
