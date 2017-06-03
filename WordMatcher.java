
import java.util.*;
import java.io.*;

/**
 *
 * @author Darshan Shetty, Ankita Mohapatra
 */
public class WordMatcher {
    
    public static final int MIN_CHARACTER = 'A';
    
    private Scanner dictionaryScanner;
    private TrieNode[][] words;
    
    public WordMatcher(String sourceFile) //dictionary source file
    {
        try
        {
            dictionaryScanner = new Scanner(new File(sourceFile));
        }
        catch(IOException ioe)
        {
            System.err.println("Could not open file " + sourceFile);
        }
        
        words = new TrieNode[26][26];
        while(dictionaryScanner.hasNext())
        {
            String[] wordParts = dictionaryScanner.nextLine().split(",");
            String word = wordParts[0].toUpperCase();
            int value = Integer.parseInt(wordParts[wordParts.length - 1]);
            char first = word.charAt(0);
            char last = word.charAt(word.length() - 1);
            int firstIndex = first - MIN_CHARACTER;//difference between A and first letter
            int lastIndex = last - MIN_CHARACTER;//difference between A and last letter
            
            if(words[firstIndex][lastIndex] == null)
            {
                words[firstIndex][lastIndex] = new TrieNode(0);
            }
            
            words[firstIndex][lastIndex].insert(word, value);
        }
    }
    
    //look up the word with first and last letter
    public int lookup(String word)
    {
        if(word == null || word.equals(""))
        {
            return 0;
        }
        
        word = word.trim().toUpperCase();
        
        int first = word.charAt(0) - MIN_CHARACTER;
        int last = word.charAt(word.length() - 1) - MIN_CHARACTER;
        TrieNode root = words[first][last];
        if(root == null)
        {
            return 0;
        }
        
        return root.lookup(word);
    }
    
    //look up with first character
    public ArrayList<WordResult> lookupPrefix(String prefix, int maxResults)
    {
        WordResultList results = new WordResultList(maxResults);
        if(prefix == null || prefix.equals(""))
        {
            return results.toArrayList();
        }
        
        prefix = prefix.toUpperCase();
        int first = prefix.charAt(0) - MIN_CHARACTER;
        for(int i = 0; i < 26; i++)
        {
            if(words[first][i] != null)
            {
                words[first][i].lookupPrefix(prefix, results);
            }
        }
        
        return results.toArrayList();
    }
    
    //search criteria contains all the character swiped..?
    public ArrayList<WordResult> lookupUnknownPrefix(ArrayList<WordSearchCharacter> searchCriteria, int maxResults)
    {
        WordResultList results = new WordResultList(5);
        return lookupUnknownPrefix(searchCriteria, results, false);
    }
    
    //search all the results with first and last letter
    private ArrayList<WordResult> lookupUnknownPrefix(ArrayList<WordSearchCharacter> searchCriteria, WordResultList results, boolean didCrop)
    {
        char firstCharacter = Character.toUpperCase(searchCriteria.get(0).getLetter());
        char lastCharacter = Character.toUpperCase(searchCriteria.get(searchCriteria.size() - 1).getLetter());
        TrieNode nodeToSearchIn = words[firstCharacter - MIN_CHARACTER][lastCharacter - MIN_CHARACTER];
        if(nodeToSearchIn != null)
        {
            nodeToSearchIn.lookupUnknownPrefix(searchCriteria, results, 0, "", 0);
        }
        
        if(searchCriteria.size() >= 3 && !didCrop)
        {
            // Fault tolerance--consider the case where the second-to-last character is actually the last one of the word
            ArrayList<WordSearchCharacter> croppedList = new ArrayList<WordSearchCharacter>(searchCriteria);
            croppedList.remove(croppedList.size() - 1);
            croppedList.remove(croppedList.size() - 1);
            this.lookupUnknownPrefix(croppedList, results, true);
        }
        return results.toArrayList();
    }
    
    //
    class TrieNode
    {
        private int value; // Stores the frequency value of the word ending at this node
        private TrieNode[] children; // The character a child of this node represents
        // is determined by its position in the array.
        
        private TrieNode(int value)
        {
            children = new TrieNode[26];
            this.value = value;
        }
        
        private TrieNode()
        {
            this(0);
        }
        
        // Adds or updates a node, given it the given value
        public TrieNode addChild(char position, int value)
        {
            position = Character.toUpperCase(position);
            int arrayIndex = position - MIN_CHARACTER;
            
            //Only allow characters in the range 'A' to 'Z'.
            if(arrayIndex < 0 || arrayIndex >= 26)
            {
                throw new IllegalArgumentException("Character " + position + " is not in the legal range of 'A' to 'Z'.");
            }
            
            // See if the trie already has a child for this character.
            TrieNode currentChild = children[arrayIndex];
            if(currentChild == null)
            {
                // If not, create one.
                currentChild = new TrieNode(value);
                children[arrayIndex] = currentChild;
            }
            else
            {
                // Don't overwrite a non-zero value with 0.
                if(value != 0)
                {
                    currentChild.setValue(value);
                }
            }
            
            return currentChild;
        }
        
        public TrieNode getChild(char position)
        {
            position = Character.toUpperCase(position);
            int arrayIndex = position - MIN_CHARACTER;
            
            //Only allow characters in the range 'A' to 'Z'.
            if(arrayIndex < 0 || arrayIndex >= 26)
            {
                throw new IllegalArgumentException("Character " + position + " is not in the legal range of 'A' to 'Z'.");
            }
            
            return children[arrayIndex];
        }
        
        public void setValue(int value)
        {
            this.value = value;
        }
        
        public int getValue()
        {
            return this.value;
        }
        
        public void insert(String word, int value)
        {
            if(word == null || word.equals(""))
            {
                this.setValue(value);
            }
            
            else
            {
                TrieNode nextNode = this.addChild(word.charAt(0), 0);
                nextNode.insert(word.substring(1), value);
            }
        }
        
        public int lookup(String word)
        {
            if(word == null || word.equals(""))
            {
                return this.value;
            }
            
            TrieNode matchingChild = this.getChild(word.charAt(0));//matching with first character
            if(matchingChild == null)
            {
                return 0;
            }
            
            return matchingChild.lookup(word.substring(1));
        }
        
        public void lookupPrefix(String prefix, WordResultList results)
        {
            lookupPrefix(prefix, "", results, Math.max(prefix.length() * 2, 10));
        }
        
        public void lookupPrefix(String prefix, String charactersSoFar, WordResultList results, int remainingDepth)
        {
            if(prefix == null || prefix.equals(""))
            {
                if(this.value > 0)
                {
                    results.add(new WordResult(charactersSoFar, this.value));
                }
                
                if(remainingDepth == 0)
                {
                    return;
                }
                
                // Search all words that continue the part of a word we have built so far
                for(char c = 'A'; c <= 'Z'; c++)
                {
                    TrieNode child = this.getChild(c);
                    if(child != null)
                    {
                        child.lookupPrefix("", charactersSoFar + c, results, remainingDepth - 1);
                    }
                }
                
                return;
            }
            
            if(remainingDepth == 0)
            {
                return;
            }
            
            char c = prefix.charAt(0);
            TrieNode nextLetter = this.getChild(c);
            if(nextLetter == null)
            {
                // If there's no letter that matches the given prefix, there's nothing to do here.
                return;
            }
            
            nextLetter.lookupPrefix(prefix.substring(1), charactersSoFar + c, results, remainingDepth - 1);
        }
        
        public void lookupUnknownPrefix(ArrayList<WordSearchCharacter> searchCriteria, WordResultList results, int currentIndex, String charactersSoFar, int valueSoFar)
        {
            // Base case--there are no characters left in the search.  This means we have found a word.
            if(currentIndex >= searchCriteria.size())
            {
                if(this.value > 0)
                {
                    results.add(new WordResult(charactersSoFar, valueSoFar, this.value));
                }
                
                return;
            }
            
            WordSearchCharacter currentCriteriaToMatch = searchCriteria.get(currentIndex);
            char currentLetter = currentCriteriaToMatch.getLetter();
            int currentNecessity = currentCriteriaToMatch.getNecessity();
            
            // First, consider the case where the current character is in the word.
            TrieNode matchingChild = getChild(currentLetter);
            if(matchingChild != null)
            {
                matchingChild.lookupUnknownPrefix(searchCriteria, results, currentIndex + 1, charactersSoFar + currentLetter, valueSoFar + currentNecessity);
            }
            
            // Then, consider the case where the current character is not in the word.
            lookupUnknownPrefix(searchCriteria, results, currentIndex + 1, charactersSoFar, valueSoFar);
        }
    }
    
}

class WordResult implements Comparable<WordResult>
{
    private int tiebreaker; // This is another, secondary measure of likelihood that the searched word is the desired word.
    private int value; // This is a measure of likelihood that the searched word is the desired word.
    private String word; // This is the English word that may be the desired word.
    
    public WordResult(String word, int value, int tiebreaker)
    {
        this.word = word;
        this.value = value;
        this.tiebreaker = tiebreaker;
    }
    
    public WordResult(String word, int value)
    {
        this(word, value, 0);
    }
    
    public String getWord()
    {
        return word;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public int getTiebreaker()
    {
        return tiebreaker;
    }
    
    // Compares WordResults so that those with the greatest value go to the start of the list
    public int compareTo(WordResult other)
    {
        int valueComparison = other.value - this.value;
        if(valueComparison != 0)
        {
            return valueComparison;
        }
        
        return other.tiebreaker - this.tiebreaker;
    }
}

// Used for specifying search criteria when we are not sure which of the letters should be in the actual word
class WordSearchCharacter
{
    char letter; // The letter we are searching on
    int necessity; // The likelihood that this letter is what the user meant to include in the word
    
    public WordSearchCharacter(char letter, int necessity)
    {
        this.letter = letter;
        this.necessity = necessity;
    }
    
    public char getLetter()
    {
        return this.letter;
    }
    
    public int getNecessity()
    {
        return this.necessity;
    }
    
    public void setLetter(char letter)
    {
        this.letter = letter;
    }
    
    public void setNecessity(int necessity)
    {
        this.necessity = necessity;
    }
}

class WordResultList
{
    int maxEntries;
    ArrayList<WordResult> words;
    
    public WordResultList(int maxEntries)
    {
        this.maxEntries = maxEntries;
        words = new ArrayList<WordResult>(maxEntries + 1);
    }
    
    public ArrayList<WordResult> toArrayList()
    {
        return words;
    }
    
    public void add(WordResult newEntry)
    {
        boolean inserted = false;
        for(int i = 0; i < words.size(); i++)
        {
            WordResult oldEntry = words.get(i);
            if(newEntry.compareTo(oldEntry) < 0)
            {
                // This new entry comes before the old one
                if(oldEntry.getWord().equals(newEntry.getWord()))
                {
                    // If both of these entries are for the same word, and this word has not been inserted, overwrite the old one since the new one is better.
                    if(!inserted)
                    {
                        words.set(i, newEntry);
                        return;
                    }
                    
                    else
                    {
                        words.remove(i);
                        return;
                    }
                }
                else if(!inserted)
                {
                    words.add(i, newEntry);
                    i++; // Add one to i to skip the already-viewed entry that moved down
                    inserted = true;
                }
            }
            
            // If this entry is inferior to an existing one with the same word, don't insert it.
            else if(newEntry.getWord().equals(oldEntry.getWord()) && newEntry.compareTo(oldEntry)  >= 0)
            {
                return;
            }
        }
        
        if(!inserted && words.size() < maxEntries)
        {
            words.add(newEntry);
        }
        
        // If this insertion pushed some words down past the last-place position to keep...
        if(words.size() > maxEntries)
        {
            //...remove that word so the list contains only the maximum number again.
            words.remove(words.size() - 1);
        }
    }
}
