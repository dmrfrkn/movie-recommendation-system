import java.util.*;
import java.util.stream.Collectors;

// Film sınıfı
class Film {
    private Long id;
    private Integer movieId;
    private String movieName;
    private String year;
    private String certificate;
    private String runtime;
    private String genre;
    private Double rating;
    private String description;
    private List<String> directors;
    private Integer votes; // Oy sayısı
    private Integer gross; // Hasılat

    // Film sınıfı yapıcı metodu
    public Film(Long id, Integer movieId, String movieName, String year, String certificate, String runtime, String genre,
                Double rating, String description, String directors, Integer votes, Integer gross) {
        this.id = id;
        this.movieId = movieId;
        this.movieName = movieName;
        this.year = year;
        this.certificate = certificate;
        this.runtime = runtime;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.directors = Arrays.asList(directors.split(",\\s*")); // Virgülle ayrılmış isimleri listeye çevir
        this.votes = votes;
        this.gross = gross;
    }

    // Getter metodları
    public Long getId() {
        return id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getYear() {
        return year;
    }

    public String getCertificate() {
        return certificate;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public Double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public Integer getVotes() {
        return votes;
    }

    public Integer getGross() {
        return gross;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                ", year='" + year + '\'' +
                ", certificate='" + certificate + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", directors=" + directors +
                ", votes=" + votes +
                ", gross=" + gross +
                '}';
    }
}

// Graph sınıfı
class Graph {
    private Map<Film, List<Film>> adjacencyList;

    // Graph yapıcı metodu
    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // Filme düğüm ekle
    public void addNode(Film film) {
        adjacencyList.putIfAbsent(film, new ArrayList<>());
    }

    // İki film arasında kenar ekle
    public void addEdge(Film film1, Film film2) {
        adjacencyList.get(film1).add(film2);
        adjacencyList.get(film2).add(film1); // Undirected graph için iki yönlü ekleme
    }

    // Belirli bir film ile aynı kategoriye ait diğer filmleri bul
    public List<Film> getSimilarFilms(Film film) {
        return adjacencyList.getOrDefault(film, new ArrayList<>());
    }
}

class FilmRecommendationSystem {
    private Graph filmGraph;
    private List<Film> filmler;

    // FilmRecommendationSystem yapıcı metodu
    public FilmRecommendationSystem() {
        filmGraph = new Graph();
        filmler = new ArrayList<>();
        initializeFilms(); // Filmleri başlat
        buildGraph(); // Graf yapısını oluştur
    }

    // Örnek filmleri ekle
    private void initializeFilms() {
        filmler.add(new Film(1L, 101, "Avengers", "2020", "PG-13", "2h 30m", "Action", 8.1, "An action-packed thriller", "Furkan Demir, Buğra Özgen", 1500, 5000000));
        filmler.add(new Film(2L, 102, "Iron Man 3", "2019", "R", "2h 10m", "Action", 7.9, "A sequel to the thrilling action series", "Furkan Demir, Ahmet Yılmaz", 2000, 6000000));
        filmler.add(new Film(3L, 103, "Ölümlü Dünya", "2018", "PG", "1h 50m", "Comedy", 7.2, "A hilarious comedy", "Mehmet Can, Buğra Özgen", 1800, 4000000));
        filmler.add(new Film(4L, 104, "Kolpaçino", "2021", "PG", "1h 40m", "Comedy", 7.5, "Another hilarious comedy", "Mehmet Can, Ahmet Yılmaz", 1700, 4500000));
        filmler.add(new Film(5L, 105, "The Pianist", "2020", "PG-13", "2h 20m", "Drama", 8.3, "A touching drama", "Furkan Demir, Mehmet Can", 1900, 7000000));
        filmler.add(new Film(6L, 106, "I.S.S.", "2019", "R", "2h 15m", "Drama", 8.0, "Another touching drama", "Ahmet Yılmaz, Buğra Özgen", 1600, 5500000));
        filmler.add(new Film(7L, 107, "Annebelle", "2021", "R", "1h 45m", "Horror", 6.8, "A scary horror film", "Mehmet Can, Buğra Özgen", 1400, 3000000));
        filmler.add(new Film(8L, 108, "The Conjuring", "2020", "R", "1h 50m", "Horror", 7.0, "Another scary horror film", "James Wan", 1300, 3500000));
        filmler.add(new Film(9L, 109, "Inception", "2010", "PG-13", "2h 28m", "Sci-Fi", 8.8, "A mind-bending thriller", "Christopher Nolan", 2000000, 83000000));
        filmler.add(new Film(10L, 110, "Interstellar", "2014", "PG-13", "2h 49m", "Sci-Fi", 8.6, "A journey beyond the stars", "Christopher Nolan", 1800000, 67700000));
        filmler.add(new Film(11L, 111, "The Dark Knight", "2008", "PG-13", "2h 32m", "Action", 9.0, "The rise of the Dark Knight", "Christopher Nolan", 2300000, 100500000));
        filmler.add(new Film(12L, 112, "The Shawshank Redemption", "1994", "R", "2h 22m", "Drama", 9.3, "Hope can set you free", "Frank Darabont", 2200000, 58500000));
        filmler.add(new Film(13L, 113, "Pulp Fiction", "1994", "R", "2h 34m", "Crime", 8.9, "A twisted crime saga", "Quentin Tarantino", 1900000, 71000000));
        filmler.add(new Film(14L, 114, "The Godfather", "1972", "R", "2h 55m", "Crime", 9.2, "An offer you can't refuse", "Francis Ford Coppola", 3000000, 135000000));
        filmler.add(new Film(15L, 115, "The Matrix", "1999", "R", "2h 16m", "Sci-Fi", 8.7, "Welcome to the real world", "The Wachowskis", 1700000, 93000000));
        filmler.add(new Film(16L, 116, "Fight Club", "1999", "R", "2h 19m", "Drama", 8.8, "First rule: don't talk about it", "David Fincher", 1600000, 100000000));
        filmler.add(new Film(17L, 117, "Forrest Gump", "1994", "PG-13", "2h 22m", "Drama", 8.8, "Life is like a box of chocolates", "Robert Zemeckis", 1800000, 67800000));
        filmler.add(new Film(18L, 118, "The Lion King", "1994", "G", "1h 28m", "Animation", 8.5, "The circle of life", "Roger Allers, Rob Minkoff", 2100000, 96800000));
        filmler.add(new Film(19L, 119, "Gladiator", "2000", "R", "2h 35m", "Action", 8.5, "A hero will rise", "Ridley Scott", 1500000, 46000000));
        filmler.add(new Film(20L, 120, "Schindler's List", "1993", "R", "3h 15m", "Drama", 8.9, "Whoever saves one life, saves the world entire", "Steven Spielberg", 2000000, 75000000));
        filmler.add(new Film(21L, 121, "Avatar", "2009", "PG-13", "2h 42m", "Sci-Fi", 7.8, "Enter the world of Pandora", "James Cameron", 1500000, 279000000));
        filmler.add(new Film(22L, 122, "Titanic", "1997", "PG-13", "3h 14m", "Drama", 7.8, "Nothing on Earth could come between them", "James Cameron", 2500000, 184000000));
        filmler.add(new Film(23L, 123, "Jurassic Park", "1993", "PG-13", "2h 7m", "Sci-Fi", 8.1, "An adventure 65 million years in the making", "Steven Spielberg", 1200000, 92000000));
        filmler.add(new Film(24L, 124, "The Lord of the Rings: The Fellowship of the Ring", "2001", "PG-13", "2h 58m", "Fantasy", 8.8, "The journey begins", "Peter Jackson", 2100000, 87000000));
        filmler.add(new Film(25L, 125, "The Lord of the Rings: The Two Towers", "2002", "PG-13", "2h 59m", "Fantasy", 8.7, "The battle for Middle-earth begins", "Peter Jackson", 2000000, 80000000));
        filmler.add(new Film(26L, 126, "The Lord of the Rings: The Return of the King", "2003", "PG-13", "3h 21m", "Fantasy", 8.9, "The journey ends", "Peter Jackson", 2500000, 90000000));
        filmler.add(new Film(27L, 127, "Harry Potter and the Sorcerer's Stone", "2001", "PG", "2h 32m", "Fantasy", 7.6, "The magic begins", "Chris Columbus", 1500000, 31700000));
        filmler.add(new Film(28L, 128, "Harry Potter and the Chamber of Secrets", "2002", "PG", "2h 41m", "Fantasy", 7.4, "The magic continues", "Chris Columbus", 1400000, 31700000));
        filmler.add(new Film(29L, 129, "Harry Potter and the Prisoner of Azkaban", "2004", "PG", "2h 22m", "Fantasy", 7.9, "The magic intensifies", "Alfonso Cuarón", 1300000, 31700000));
        filmler.add(new Film(30L, 130, "Harry Potter and the Goblet of Fire", "2005", "PG-13", "2h 37m", "Fantasy", 7.7, "The magic is put to the test", "Mike Newell", 1400000, 31700000));
        filmler.add(new Film(31L, 131, "Harry Potter and the Order of the Phoenix", "2007", "PG-13", "2h 18m", "Fantasy", 7.5, "The rebellion begins", "David Yates", 1300000, 31700000));
        filmler.add(new Film(32L, 132, "Harry Potter and the Half-Blood Prince", "2009", "PG", "2h 33m", "Fantasy", 7.6, "The dark secrets are revealed", "David Yates", 1200000, 31700000));
        filmler.add(new Film(33L, 133, "Harry Potter and the Deathly Hallows: Part 1", "2010", "PG-13", "2h 26m", "Fantasy", 7.7, "The end begins", "David Yates", 1100000, 31700000));
        filmler.add(new Film(34L, 134, "Harry Potter and the Deathly Hallows: Part 2", "2011", "PG-13", "2h 10m", "Fantasy", 8.1, "It all ends here", "David Yates", 1500000, 31700000));
        filmler.add(new Film(35L, 135, "Star Wars: Episode IV - A New Hope", "1977", "PG", "2h 1m", "Sci-Fi", 8.6, "The Force is strong with this one", "George Lucas", 1700000, 77500000));
        filmler.add(new Film(36L, 136, "Star Wars: Episode V - The Empire Strikes Back", "1980", "PG", "2h 4m", "Sci-Fi", 8.7, "The saga continues", "Irvin Kershner", 1500000, 65000000));
        filmler.add(new Film(37L, 137, "Star Wars: Episode VI - Return of the Jedi", "1983", "PG", "2h 11m", "Sci-Fi", 8.3, "The saga concludes", "Richard Marquand", 1300000, 57000000));
        filmler.add(new Film(38L, 138, "Star Wars: Episode I - The Phantom Menace", "1999", "PG", "2h 16m", "Sci-Fi", 6.5, "Every generation has a legend", "George Lucas", 1600000, 92000000));
        filmler.add(new Film(39L, 139, "Star Wars: Episode II - Attack of the Clones", "2002", "PG", "2h 22m", "Sci-Fi", 6.6, "A Jedi shall not know anger", "George Lucas", 1400000, 65000000));
        filmler.add(new Film(40L, 140, "Star Wars: Episode III - Revenge of the Sith", "2005", "PG-13", "2h 20m", "Sci-Fi", 7.5, "The saga is complete", "George Lucas", 1500000, 72000000));
        filmler.add(new Film(41L, 141, "Star Wars: Episode VII - The Force Awakens", "2015", "PG-13", "2h 18m", "Sci-Fi", 7.9, "Every generation has a story", "J.J. Abrams", 2000000, 93600000));
        filmler.add(new Film(42L, 142, "Star Wars: Episode VIII - The Last Jedi", "2017", "PG-13", "2h 32m", "Sci-Fi", 7.0, "The saga continues", "Rian Johnson", 1900000, 93900000));
        filmler.add(new Film(43L, 143, "Star Wars: Episode IX - The Rise of Skywalker", "2019", "PG-13", "2h 22m", "Sci-Fi", 6.6, "The saga will end", "J.J. Abrams", 1800000, 92000000));
        filmler.add(new Film(44L, 144, "Indiana Jones and the Raiders of the Lost Ark", "1981", "PG", "1h 55m", "Adventure", 8.4, "The adventure begins", "Steven Spielberg", 1400000, 50000000));
        filmler.add(new Film(45L, 145, "Indiana Jones and the Temple of Doom", "1984", "PG", "1h 58m", "Adventure", 7.6, "If adventure has a name", "Steven Spielberg", 1300000, 45000000));
        filmler.add(new Film(46L, 146, "Indiana Jones and the Last Crusade", "1989", "PG-13", "2h 7m", "Adventure", 8.2, "The man with the hat is back", "Steven Spielberg", 1600000, 48000000));
        filmler.add(new Film(47L, 147, "Indiana Jones and the Kingdom of the Crystal Skull", "2008", "PG-13", "2h 2m", "Adventure", 6.1, "The adventure continues", "Steven Spielberg", 1200000, 45000000));
        filmler.add(new Film(48L, 148, "Back to the Future", "1985", "PG", "1h 56m", "Sci-Fi", 8.5, "He's the only kid ever to get into trouble before he was born", "Robert Zemeckis", 1400000, 50000000));
        filmler.add(new Film(49L, 149, "Back to the Future Part II", "1989", "PG", "1h 48m", "Sci-Fi", 7.8, "Getting back was only the beginning", "Robert Zemeckis", 1200000, 45000000));
        filmler.add(new Film(50L, 150, "Back to the Future Part III", "1990", "PG", "1h 58m", "Sci-Fi", 7.4, "They've saved the best trip for last", "Robert Zemeckis", 1100000, 40000000));
        filmler.add(new Film(51L, 151, "The Silence of the Lambs", "1991", "R", "1h 58m", "Thriller", 8.6, "To enter the mind of a killer she must challenge the mind of a madman", "Jonathan Demme", 1800000, 95000000));
        filmler.add(new Film(52L, 152, "Se7en", "1995", "R", "2h 7m", "Thriller", 8.6, "Gluttony. Greed. Sloth. Wrath. Pride. Lust. Envy. Seven deadly sins. Seven ways to die", "David Fincher", 1700000, 87000000));
        filmler.add(new Film(53L, 153, "The Usual Suspects", "1995", "R", "1h 46m", "Thriller", 8.5, "The greatest trick the devil ever pulled was convincing the world he didn't exist", "Bryan Singer", 1600000, 23000000));
        filmler.add(new Film(54L, 154, "Goodfellas", "1990", "R", "2h 26m", "Crime", 8.7, "As far back as I can remember, I always wanted to be a gangster", "Martin Scorsese", 2000000, 46000000));
        filmler.add(new Film(55L, 155, "The Departed", "2006", "R", "2h 31m", "Crime", 8.5, "Cops or criminals, when you're facing a loaded gun what's the difference", "Martin Scorsese", 1800000, 90000000));
        filmler.add(new Film(56L, 156, "Django Unchained", "2012", "R", "2h 45m", "Western", 8.4, "Life, liberty and the pursuit of vengeance", "Quentin Tarantino", 2000000, 162000000));
        filmler.add(new Film(57L, 157, "Inglourious Basterds", "2009", "R", "2h 33m", "War", 8.3, "Once upon a time in Nazi-occupied France", "Quentin Tarantino", 1700000, 120000000));
        filmler.add(new Film(58L, 158, "Saving Private Ryan", "1998", "R", "2h 49m", "War", 8.6, "The mission is a man", "Steven Spielberg", 2000000, 70000000));
        filmler.add(new Film(59L, 159, "The Green Mile", "1999", "R", "3h 9m", "Drama", 8.6, "Miracles do happen", "Frank Darabont", 1700000, 58000000));
        filmler.add(new Film(60L, 160, "The Wolf of Wall Street", "2013", "R", "3h", "Biography", 8.2, "Earn. Spend. Party.", "Martin Scorsese", 1600000, 116000000));
        filmler.add(new Film(61L, 161, "Mad Max: Fury Road", "2015", "R", "2h", "Action", 8.1, "What a lovely day", "George Miller", 1400000, 150000000));
        filmler.add(new Film(62L, 162, "The Godfather: Part II", "1974", "R", "3h 22m", "Crime", 9.0, "I know it was you, Fredo. You broke my heart", "Francis Ford Coppola", 2500000, 57000000));
        filmler.add(new Film(63L, 163, "The Godfather: Part III", "1990", "R", "2h 50m", "Crime", 7.6, "All the power on earth can't change destiny", "Francis Ford Coppola", 2000000, 66000000));
        filmler.add(new Film(64L, 164, "The Good, the Bad and the Ugly", "1966", "R", "2h 41m", "Western", 8.8, "For three men the Civil War wasn't hell. It was practice", "Sergio Leone", 1800000, 25000000));
        filmler.add(new Film(65L, 165, "The Shawshank Redemption", "1994", "R", "2h 22m", "Drama", 9.3, "Hope can set you free", "Frank Darabont", 2200000, 58500000));
        filmler.add(new Film(66L, 166, "The Lord of the Rings: The Fellowship of the Ring", "2001", "PG-13", "2h 58m", "Fantasy", 8.8, "The journey begins", "Peter Jackson", 2100000, 87000000));
        filmler.add(new Film(67L, 167, "The Lord of the Rings: The Two Towers", "2002", "PG-13", "2h 59m", "Fantasy", 8.7, "The battle for Middle-earth begins", "Peter Jackson", 2000000, 80000000));
        filmler.add(new Film(68L, 168, "The Lord of the Rings: The Return of the King", "2003", "PG-13", "3h 21m", "Fantasy", 8.9, "The journey ends", "Peter Jackson", 2500000, 90000000));
        filmler.add(new Film(69L, 169, "Inception", "2010", "PG-13", "2h 28m", "Sci-Fi", 8.8, "A mind-bending thriller", "Christopher Nolan", 2000000, 83000000));
        filmler.add(new Film(70L, 170, "Interstellar", "2014", "PG-13", "2h 49m", "Sci-Fi", 8.6, "A journey beyond the stars", "Christopher Nolan", 1800000, 67700000));
        filmler.add(new Film(71L, 171, "The Dark Knight", "2008", "PG-13", "2h 32m", "Action", 9.0, "The rise of the Dark Knight", "Christopher Nolan", 2300000, 100500000));
        filmler.add(new Film(72L, 172, "The Dark Knight Rises", "2012", "PG-13", "2h 44m", "Action", 8.4, "The legend ends", "Christopher Nolan", 2100000, 108000000));
        filmler.add(new Film(73L, 173, "Forrest Gump", "1994", "PG-13", "2h 22m", "Drama", 8.8, "Life is like a box of chocolates", "Robert Zemeckis", 1800000, 67800000));
        filmler.add(new Film(74L, 174, "The Green Mile", "1999", "R", "3h 9m", "Drama", 8.6, "Miracles do happen", "Frank Darabont", 1700000, 58000000));
        filmler.add(new Film(75L, 175, "The Lion King", "1994", "G", "1h 28m", "Animation", 8.5, "The circle of life", "Roger Allers, Rob Minkoff", 2100000, 96800000));
        filmler.add(new Film(76L, 176, "Toy Story", "1995", "G", "1h 21m", "Animation", 8.3, "The adventure takes off", "John Lasseter", 1900000, 37300000));
        filmler.add(new Film(77L, 177, "Finding Nemo", "2003", "G", "1h 40m", "Animation", 8.1, "There are 3.7 trillion fish in the ocean. They're looking for one", "Andrew Stanton", 2200000, 94000000));
        filmler.add(new Film(78L, 178, "Cars", "2006", "G", "1h 57m", "Animation", 7.1, "Ahh... it's got that new movie smell", "John Lasseter", 2100000, 47000000));
        filmler.add(new Film(79L, 179, "Monsters, Inc.", "2001", "G", "1h 32m", "Animation", 8.0, "We scare because we care", "Pete Docter, David Silverman", 2000000, 53000000));
        filmler.add(new Film(80L, 180, "The Incredibles", "2004", "PG", "1h 56m", "Animation", 8.0, "Saving the world has never been so incredible", "Brad Bird", 1800000, 63100000));
        filmler.add(new Film(81L, 181, "Frozen", "2013", "PG", "1h 42m", "Animation", 7.4, "Only an act of true love can thaw a frozen heart", "Chris Buck, Jennifer Lee", 1900000, 40000000));
        filmler.add(new Film(82L, 182, "Moana", "2016", "PG", "1h 47m", "Animation", 7.6, "The ocean is calling", "Ron Clements, John Musker", 2100000, 64500000));
        filmler.add(new Film(83L, 183, "Shrek", "2001", "PG", "1h 35m", "Animation", 7.8, "The greatest fairy tale never told", "Andrew Adamson, Vicky Jenson", 2200000, 48700000));
        filmler.add(new Film(84L, 184, "Kung Fu Panda", "2008", "PG", "1h 32m", "Animation", 7.5, "Prepare for awesomeness", "Mark Osborne, John Jenson", 2200000, 48700000));
        filmler.add(new Film(86L, 186, "The Little Mermaid", "1989", "G", "1h 23m", "Animation", 7.6, "Part of your world", "Ron Clements, John Musker", 2400000, 233000000));
        filmler.add(new Film(87L, 187, "Beauty and the Beast", "1991", "G", "1h 24m", "Animation", 8.0, "The tale as old as time", "Gary Trousdale, Kirk Wise", 2500000, 425000000));
        filmler.add(new Film(88L, 188, "Aladdin", "1992", "G", "1h 31m", "Animation", 8.0, "Choose wisely", "Ron Clements, John Musker", 2600000, 504000000));
        filmler.add(new Film(89L, 189, "Mulan", "1998", "G", "1h 28m", "Animation", 7.6, "The flower that blooms in adversity is the most rare and beautiful of all", "Tony Bancroft, Barry Cook", 2700000, 304000000));
        filmler.add(new Film(90L, 190, "Tangled", "2010", "PG", "1h 40m", "Animation", 7.7, "They're taking adventure to new lengths", "Nathan Greno, Byron Howard", 2800000, 59200000));
        filmler.add(new Film(91L, 191, "Brave", "2012", "PG", "1h 33m", "Animation", 7.1, "Change your fate", "Mark Andrews, Brenda Chapman", 2900000, 53900000));
        filmler.add(new Film(92L, 192, "Coco", "2017", "PG", "1h 45m", "Animation", 8.4, "The celebration of a lifetime", "Lee Unkrich, Adrian Molina", 3000000, 80700000));
        filmler.add(new Film(93L, 193, "Toy Story 4", "2019", "G", "1h 40m", "Animation", 7.8, "Get ready to hit the road", "Josh Cooley", 3100000, 80700000));
        filmler.add(new Film(94L, 194, "The Jungle Book", "1967", "G", "1h 18m", "Animation", 7.6, "The bare necessities of life will come to you", "Wolfgang Reitherman", 3200000, 38300000));
        filmler.add(new Film(95L, 195, "Tarzan", "1999", "G", "1h 28m", "Animation", 7.3, "An immortal legend", "Chris Buck, Kevin Lima", 3300000, 44800000));
        filmler.add(new Film(96L, 196, "Finding Dory", "2016", "PG", "1h 37m", "Animation", 7.3, "An unforgettable journey she probably won't remember", "Andrew Stanton, Angus MacLane", 3400000, 85700000));
        filmler.add(new Film(97L, 197, "Monsters University", "2013", "G", "1h 44m", "Animation", 7.3, "School never looked this scary", "Dan Scanlon", 3500000, 74300000));
        filmler.add(new Film(98L, 198, "Cars 2", "2011", "G", "1h 46m", "Animation", 6.2, "Going where no car has gone before", "John Lasseter, Brad Lewis", 3600000, 56200000));
        filmler.add(new Film(99L, 199, "Inside Out", "2015", "PG", "1h 42m", "Animation", 8.1, "Meet the little voices inside your head", "Pete Docter, Ronnie Del Carmen", 3700000, 85700000));
        filmler.add(new Film(100L, 200, "Up", "2009", "PG", "1h 36m", "Animation", 8.2, "Fly to a new adventure!", "Pete Docter, Bob Peterson", 3800000, 73500000));


    }

    // Graf yapısını oluştur
    private void buildGraph() {
        // Filmleri düğüm olarak ekle
        for (Film film : filmler) {
            filmGraph.addNode(film);
        }

        // Aynı kategoriye ait filmler arasında kenarlar ekle
        for (int i = 0; i < filmler.size(); i++) {
            for (int j = i + 1; j < filmler.size(); j++) {
                if (filmler.get(i).getGenre().equalsIgnoreCase(filmler.get(j).getGenre())) {
                    filmGraph.addEdge(filmler.get(i), filmler.get(j));
                }
            }
        }
    }

    // Levenshtein mesafesi hesaplama metodu
    public int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    // En yakın filmi bulma metodu
    public Film findClosestFilm(String filmIsmi) {
        Film closestFilm = null;
        int minDistance = Integer.MAX_VALUE;

        for (Film film : filmler) {
            int distance = levenshteinDistance(filmIsmi.toLowerCase(), film.getMovieName().toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestFilm = film;
            }
        }

        return closestFilm;
    }

    // Kullanıcının girdiği filme göre öneri sun
    public void suggestFilms(String filmIsmi) {
        Film closestFilm = findClosestFilm(filmIsmi);

        if (closestFilm == null) {
            System.out.println("Bu isimde bir film bulunamadı.");
            return;
        }

        // Kullanıcının girdiği film ismini doğrulama
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bunu mu demek istediniz: " + closestFilm.getMovieName() + "? (E/H)");
        String cevap = scanner.nextLine();

        if (!cevap.equalsIgnoreCase("E")) {
            System.out.println("Aradığınız film bulunamadı.");
            return;
        }

        // Kullanıcının girdiği filme göre aynı kategorideki diğer filmleri öner
        List<Film> onerilenFilmler = filmGraph.getSimilarFilms(closestFilm);

        // Kullanıcının girdiği filmi öneri listesinden çıkar
        onerilenFilmler = onerilenFilmler.stream()
                .filter(film -> !film.getMovieName().equalsIgnoreCase(closestFilm.getMovieName()))
                .collect(Collectors.toList());

        System.out.println(closestFilm.getMovieName() + " filmine göre önerilen filmler:");
        if (onerilenFilmler.isEmpty()) {
            System.out.println("Önerilecek başka film bulunamadı.");
        } else {
            onerilenFilmler.forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        FilmRecommendationSystem system = new FilmRecommendationSystem();
        Scanner scanner = new Scanner(System.in);

        // Kullanıcıdan film ismi alınır
        System.out.println("Lütfen bir film ismi giriniz:");
        String filmIsmi = scanner.nextLine();

        // Kullanıcının girdiği filme göre öneri sun
        system.suggestFilms(filmIsmi);

        scanner.close();
    }
}
