
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherMain {
    public static void main(String[] args) {
        String url = Credentials.getUrl();
        File xmlFile = new File("input.xml");

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
        try {
            URL urlBuilt = new URL("http://api.wunderground.com/api/"+Credentials.getApiKey()+"/conditions/q/"+stateTarget+"/"+cityTarget+".xml");
            URLConnection conn= urlBuilt.openConnection();
            InputStream inputStream=conn.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String line=null;
            PrintWriter pw = new PrintWriter(xmlFile);
            while ((line=bufferedReader.readLine())!= null){
                pw.println(line);
            }
            pw.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        XMLInputFactory xmlInputFactory= XMLInputFactory.newInstance();
        try{
            String temp,weather,wind,dewpoint,windchill,precip;
            XMLEventReader xmlEventReader=xmlInputFactory.createXMLEventReader(new FileInputStream(xmlFile));
            System.out.printf("====Weather for %s in %s====\n",cityTarget,stateTarget);
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                //System.out.println(xmlEvent);
                if (xmlEvent.isStartElement()) {
                    //System.out.println("Start Element: " + xmlEvent);

                    StartElement it = xmlEvent.asStartElement();
                    QName tag = it.getName();
                    String name = tag.getLocalPart().trim();
                    //System.out.println("Qname is " + tag);
                    if (name.equalsIgnoreCase("weather")){
                        weather= xmlEventReader.getElementText();
                        System.out.println("Weather="+weather);
                    }else if (name.equalsIgnoreCase("temp_f")){
                        temp=xmlEventReader.getElementText();
                        System.out.println("Temp(F) = "+ temp);
                    }else if (name.equalsIgnoreCase("wind_string")){
                        wind=xmlEventReader.getElementText();
                        System.out.println("Wind "+wind);
                    }else if (name.equalsIgnoreCase("windchill_f")){
                        windchill=xmlEventReader.getElementText();
                        System.out.println("Windchill of "+windchill+"(F)");
                    }else if (name.equalsIgnoreCase("dewpoint_f")){
                        dewpoint=xmlEventReader.getElementText();
                        System.out.println("Dewpoint of "+dewpoint+"(F)");
                    }else if (name.equalsIgnoreCase("precip_today_in")){
                        precip=xmlEventReader.getElementText();
                        System.out.println("Precipitation for today: "+precip+" in inches");
                    }
                }
            }
        }catch (Exception e){

        }
//        xmlFile.deleteOnExit();

    }

    public static String parseCity(String cityTarget){//this takes the city entered and parses all spaces into underscores
        String result=cityTarget;
        if (result.trim().contains(" ")){
            result = result.replaceAll("\\s+","_");
        }
        return result;
    }


}
