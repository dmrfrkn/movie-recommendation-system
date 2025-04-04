import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children; 
    boolean isEndOfWord; 

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

class Trie {
    TrieNode root; 

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true; 
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        return current.isEndOfWord; 
    }

    public List<String> suggestMovies(String prefix) {
        List<String> suggestions = new ArrayList<>();
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return suggestions; 
            }
            current = current.children.get(c);
        }
        suggestMoviesHelper(prefix, current, suggestions);
        return suggestions;
    }

    private void suggestMoviesHelper(String prefix, TrieNode node, List<String> suggestions) {
        if (node.isEndOfWord) {
            suggestions.add(prefix);
        }
        for (char c : node.children.keySet()) {
            suggestMoviesHelper(prefix + c, node.children.get(c), suggestions);
        }
    }
}

class MovieRecommendation {

    private static Trie movieTrie = new Trie(); 

    public static void main(String[] args) {
        String[] movies = {"Inception", "The Dark Knight", "Interstellar", "The Shawshank Redemption", "The Godfather"};

        for (String movie : movies) {
            movieTrie.insert(movie);
        }

        String userInput ="The";

        List<String> recommendations = movieTrie.suggestMovies(userInput);
        System.out.println("Öneriler:");
        for (String recommendation : recommendations) {
            System.out.println(recommendation);
        }
    }
}
