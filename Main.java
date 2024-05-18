import java.util.*;

// Trie düğüm sınıfı
class TrieNode {
    Map<Character, TrieNode> children; // Karakterlerle ilişkili çocuk düğümler
    boolean isEndOfWord; // Kelimenin sonu mu kontrolü

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

// Trie sınıfı
class Trie {
    TrieNode root; // Kök düğüm

    public Trie() {
        root = new TrieNode();
    }

    // Trie'ye kelime ekleme metodu
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true; // Kelimenin sonunu işaretle
    }

    // Kelime arama metodu
    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        return current.isEndOfWord; // Kelime tam olarak eşleşiyorsa true döndürür
    }

    // Verilen önekle eşleşen filmleri öneren metot
    public List<String> suggestMovies(String prefix) {
        List<String> suggestions = new ArrayList<>();
        TrieNode current = root;
        // Önek boyunca ilerle
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return suggestions; // Önekle eşleşen bir kelime yoksa boş liste döndür
            }
            current = current.children.get(c);
        }
        // Eşleşen filmleri bulan yardımcı metotu çağır
        suggestMoviesHelper(prefix, current, suggestions);
        return suggestions;
    }

    // Önerileri bulan yardımcı metot
    private void suggestMoviesHelper(String prefix, TrieNode node, List<String> suggestions) {
        // Düğüm kelimenin sonunu gösteriyorsa, o kelimeyi önerilere ekle
        if (node.isEndOfWord) {
            suggestions.add(prefix);
        }
        // Düğümün çocuklarını dolaşarak önerileri bul
        for (char c : node.children.keySet()) {
            suggestMoviesHelper(prefix + c, node.children.get(c), suggestions);
        }
    }
}

// Film önerme sınıfı
class MovieRecommendation {

    private static Trie movieTrie = new Trie(); // Trie nesnesi oluştur

    public static void main(String[] args) {
        // Film veritabanı
        String[] movies = {"Inception", "The Dark Knight", "Interstellar", "The Shawshank Redemption", "The Godfather"};

        // Trie'ye film isimlerini ekle
        for (String movie : movies) {
            movieTrie.insert(movie);
        }

        // Kullanıcının girdiği önek
        String userInput ="The";

        // Önerileri al ve ekrana yazdır
        List<String> recommendations = movieTrie.suggestMovies(userInput);
        System.out.println("Öneriler:");
        for (String recommendation : recommendations) {
            System.out.println(recommendation);
        }
    }
}
