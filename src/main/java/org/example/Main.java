package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1

public class Main {
    public Main() throws SQLException {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //Initializer:
        //Scanner
        Scanner console = new Scanner(System.in);
        System.out.println("Please select an option:" +
                "\n 1- Search for new cases in a country" +
                "\n 2- Search in the DataBase the information" +
                "\n 3- Enter the data searched in point 1 to the DB");
        int input = Integer.parseInt(console.nextLine());

        if (input == 1){
            String[] countriesList = new String[]{"Afghanistan", "Albania", "Algeria","Andorra","Angola","Anguilla","Antigua-and-Barbuda","Argentina", "Armenia", "Aruba", "Austra,lia","Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia-and-Herzegovina", "Botswana", "Brazil", "British-Virgin-Islands", "Brunei", "Bulgaria", "Burkina-Faso", "Burundi", "Cabo-Verde", "Cambodia", "Cameroon", "Canada", "CAR", "Caribbean-Netherlands", "Cayman-Islands", "Chad", "Channel-Islands", "Chile", "China", "Colombia", "Comoros", "Congo", "Cook-Islands", "Costa-Rica", "Croatia", "Cuba", "Cura&ccedil;ao", "Cyprus", "Czechia", "Denmark", "Diamond-Princess", "Diamond-Princess-", "Djibouti", "Dominica", "Dominican-Republic", "DPRK", "DRC", "Ecuador", "Egypt", "El-Salvador", "Equatorial-Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Faeroe-Islands", "Falkland-Islands", "Fiji", "Finland", "France", "French-Guiana", "French-Polynesia", "Gabon","Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hong-Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Isle-of-Man", "Israel", "Italy", "Ivory-Coast", "Jamaica","Japan", "Jordan","Kazakhstan","Kenya", "Kiribati","Kuwait", "Kyrgyzstan", "Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macao","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall-Islands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","MS-Zaandam","MS-Zaandam-","Myanmar","Namibia","Nauru","Nepal","Netherlands","New-Caledonia","New-Zealand","Nicaragua","Niger","Nigeria","Niue","North-Macedonia","Norway","Oman","Pakistan","Palestine","Panama","Papua-New-Guinea","Paraguay","Peru","Philippines", "Poland","Portugal", "Puerto-Rico","Qatar", "R&eacute;union","Romania", "Russia","Rwanda", "S-Korea","Saint-Helena", "Saint-Kitts-and-Nevis","Saint-Lucia", "Saint-Martin","Saint-Pierre-Miquelon","Samoa", "San-Marino","Sao-Tome-and-Principe","Saudi-Arabia","Senegal", "Serbia","Seychelles", "Sierra-Leone","Singapore", "Sint-Maarten","Slovakia", "Slovenia","Solomon-Islands","Somalia", "South-Africa","South-Sudan", "Spain","Sudan","Suriname","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Timor-Leste","Togo","Tonga","Trinidad-and-Tobago","Tunisia","Turkey","Turks-and-Caicos","Tuvalu","UAE","Uganda","UK","Ukraine","Uruguay","US-Virgin-Islands","USA","Uzbekistan","Vanuatu","Vatican-City", "Venezuela", "Vietnam", "Wallis-and-Futuna", "Western-Sahara", "Yemen", "Zambia", "Zimbabwe"};
            System.out.println("Please enter a valid country name ");
            String country = console.nextLine();

            while (!Arrays.stream(countriesList).toList().contains(country)){
                System.out.println(country + " is not a valid country, please enter a valid country name again: ");
                country = console.nextLine();
            }
            StringBuilder uriHttp = new StringBuilder();
            uriHttp.append("https://covid-193.p.rapidapi.com/statistics?country=");
            uriHttp.append(country);
            System.out.println(newCasesCountry(uriHttp.toString()));
        }

        else if(input ==2){
            System.out.println(dbResult());
        }
        else if(input ==3){
            dbInput();
        }

        else{
            System.out.println("You have selected a non available option, please pick it again");
        }
    }
    public static String dbInput(){
        StringBuilder sb = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "rootroot");
            Statement statement = connection.createStatement();
            //Yet to be modified so the user can enter new fetched data, and in case it already exists, update it.
            statement.execute("INSERT INTO java.country (name) VALUES ('Andorra');");

        }catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static String dbResult(){
        StringBuilder sb = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "rootroot");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Country");
            while (resultSet.next()) {
                sb.append(resultSet.getString("Name")).append(", ");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    return sb.toString();
    }

    public static String newCasesCountry(String uriHttp) throws IOException, InterruptedException {

        //HTTP Request to covid API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriHttp))
                .header("X-RapidAPI-Key", "2485024da0mshac7e7509945a67ep1735eajsna56b007928ad")
                .header("X-RapidAPI-Host", "covid-193.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        //Parse JSON into object
        Statistics statistics = null;
        try {
            ObjectMapper om = new ObjectMapper();
            statistics = om.readValue(response.body(), Statistics.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statistics.response.get(0).cases.mynew;
    }

}