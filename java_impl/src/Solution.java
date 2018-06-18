import java.io.*;
import java.util.*;
import java.net.*;
import com.google.gson.*;
import pojos.Datum;
import pojos.MovieSearchResults;


public class Solution {

    static String[] getMovieTitles(String substr) {
        Gson gson = new Gson();
        MovieSearchResults firstPageResults = gson.fromJson(getMovieTitles(substr, "1"), MovieSearchResults.class);

        List<MovieSearchResults> totalResults = new ArrayList<>();
        totalResults.add(firstPageResults);

        if(firstPageResults.getTotalPages() > 1){
            for (int i=2; i <= firstPageResults.getTotalPages(); i++){
                totalResults.add(gson.fromJson(getMovieTitles(substr, Integer.toString(i)), MovieSearchResults.class));
            }
        }

        List<String> titles = new ArrayList<>();
        for(MovieSearchResults pageResult : totalResults){
            for(Datum datum : pageResult.getData()){
                titles.add(datum.getTitle());
            }
        }

        Collections.sort(titles);
        String[] sortedTitles = new String[titles.size()];
        sortedTitles = titles.toArray(sortedTitles);

        return sortedTitles;
    }

    static String getMovieTitles(String substr, String page) {
        String urlLink = "https://jsonmock.hackerrank.com/api/movies/search/?Title=" + substr + "&page=" + page;
        String response = "";

        try {
            response = getHTML(urlLink);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        return result.toString();
    }

    public static void main(String[] args) throws IOException{
        String[] titles = getMovieTitles("spiderman");

        for(String string : titles){
            System.out.println(string);
        }

    }
}