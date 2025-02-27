import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private ArrayList<String> actors;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        actors = new ArrayList<String>();
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
            System.out.println();
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();
            if (movieTitle.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        returnMovies(results);
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String[] cast = (movies.get(i).getCast()).split("\\|");

            for (String name : cast) {
                if (actors == null) {
                    actors.add(name);
                } else if (!actors.contains(name)){
                    actors.add(name);
                }
            }
        }

        Collections.sort(actors);

        for (int i = 0; i < actors.size(); i++){
            System.out.println(i+1 + ". " + actors.get(i));
        }

        System.out.println();
        System.out.println("Which actor would you like to see?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String actor = actors.get(choice - 1);

        ArrayList<Movie> results = new ArrayList<Movie>();
        for (Movie m : movies){
            if (m.getCast().contains(actor)) {
                results.add(m);
            }
        }

        returnMovies(results);
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String keyword = (movies.get(i).getKeywords()).toLowerCase();
            if (keyword.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by keywords
        sortResults(results);

        returnMovies(results);
    }

    private void listGenres()
    {
        // Getting list of genres from the movie collection
        ArrayList<String> genres = new ArrayList<String>(); // new arraylist
        genres.add(" ");
        for (Movie m : movies){
            String mGen = m.getGenres();    // genre of each movie
            String[] mGenArr = mGen.split("\\|");   // arr of each gen in the mov
            for (String g : mGenArr) {
                if (!(genres.contains(g))) {
                    genres.addFirst(g);
                }
            }
        }
        genres.removeLast();

        Collections.sort(genres);
        System.out.println();
        for (int i = 0; i < genres.size(); i++) {
            System.out.println((i+1) + ". " + genres.get(i));
        }

        System.out.println();
        System.out.println("Which genre would you like to see?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Movie> byGenre = new ArrayList<Movie>();
        for (Movie m : movies) {
            if (m.getGenres().contains(genres.get(choice - 1))) {
                byGenre.add(m);
            }
        }

        returnMovies(byGenre);
    }

    private void listHighestRated()
    {
        double[] ratingArr = new double[movies.size()];
        int j = 0;
        for (Movie m : movies){
            ratingArr[j] = m.getUserRating();
            j++;
        }

        Arrays.sort(ratingArr);
        double[] revRatingArr = new double [50];

        int b = 0;
        for (int i = ratingArr.length; i > (ratingArr.length) - 50; i--){
            revRatingArr[b] = ratingArr[i-1];
            b ++;
        }

        Movie[] top50 = new Movie[50];
        for (int i = 0; i < 50; i++){
            for (Movie m : movies){
                if (m.getUserRating() == revRatingArr[i]){
                    top50[i] = m;
                    Movie temp = movies.getLast();
                    movies.set(movies.size() - 1, m);
                    movies.set(movies.indexOf(m), temp);
                }
            }
        }

        int k = 0;
        for (Movie m : top50){
            System.out.println((k+1) + ". " + m.getTitle() + " | rating: " + m.getUserRating());
            k++;
        }

        System.out.println();
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top50[choice - 1];

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listHighestRevenue()
    {
        double[] revenueArr = new double[movies.size()];
        int j = 0;
        for (Movie m : movies){
            revenueArr[j] = m.getRevenue();
            j++;
        }

        Arrays.sort(revenueArr);
        double[] revRevenueArr = new double [50];

        int b = 0;
        for (int i = revenueArr.length; i > (revenueArr.length) - 50; i--){
            revRevenueArr[b] = revenueArr[i-1];
            b ++;
        }

        Movie[] top50 = new Movie[50];
        for (int i = 0; i < 50; i++){
            for (Movie m : movies){
                if (m.getRevenue() == revRevenueArr[i]){
                    top50[i] = m;
                    Movie temp = movies.getLast();
                    movies.set(movies.size() - 1, m);
                    movies.set(movies.indexOf(m), temp);
                }
            }
        }

        int k = 0;
        for (Movie m : top50){
            System.out.println((k+1) + ". " + m.getTitle() + " | Generated revenue: $" + m.getRevenue());
            k++;
        }

        System.out.println();
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top50[choice - 1];

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void returnMovies(ArrayList<Movie> results){
        System.out.println();
        for (int i = 0; i < results.size(); i++){
            System.out.println((i+1) + ". " + results.get(i).getTitle());
        }

        System.out.println();
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}