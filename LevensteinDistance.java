/*
 * 
 */
import java.util.ArrayList;
/**
 * @author 
 *
 */
public class LevensteinDistance {
    
    // Given two strings, finds the edit distance between them, returning the edits needed to transform expected into current.
    public static ArrayList<Edit> getDistance(String current, String expected)
    {
        int currentLength = current.length();
        int expectedLength = expected.length();
        ArrayList<Edit> edits = new ArrayList<Edit>();
        int[][] editDistances = new int[currentLength + 1][expectedLength + 1];
        for(int i = 0; i <= currentLength; i++)
        {
            for(int j = 0; j <= expectedLength; j++)
            {
                if(i == 0 && j == 0)
                {
                    editDistances[i][j] = 0;
                }
                else if(i == 0)
                {
                    // Edited has no characters
                    // The only possible edit is to delete a character from expected
                    editDistances[i][j] = 1 + editDistances[i][j-1];
                }
                else if(j == 0)
                {
                    // Original has no characters
                    // The only possible edit is to add a character to expected
                    editDistances[i][j] = 1 + editDistances[i-1][j];
                }
                else
                {
                    int conversionCost = expected.charAt(j-1) == current.charAt(i-1) ? 0 : 1;
                    editDistances[i][j] = Math.min(editDistances[i-1][j-1] + conversionCost, Math.min(editDistances[i-1][j] + 1, editDistances[i][j-1] + 1));
                }
            }
        }
        
        int i = currentLength;
        int j = expectedLength;
        while(i > 0 || j > 0)
        {
            int currentDistance = editDistances[i][j];
            if(i > 0 && editDistances[i-1][j] == currentDistance - 1)
            {
                // The edit distance was obtained by an insertion of the ith character of current into expected
                edits.add(0, new Edit(current.charAt(i-1), '\0', EditType.INSERTION));
                i--;
            }
            else if(j > 0 && editDistances[i][j-1] == currentDistance - 1)
            {
                // The edit distance was obtained by a deletion of the jth character of expected
                edits.add(0, new Edit('\0', expected.charAt(j-1), EditType.DELETION));
                j--;
            }
            else
            {
                char oldChar = expected.charAt(j-1);
                char newChar = current.charAt(i-1);
                if(newChar == oldChar)
                {
                    edits.add(0, new Edit(newChar, oldChar, EditType.MATCH));
                }
                else
                {
                    edits.add(0, new Edit(newChar, oldChar, EditType.MISMATCH));
                }
                i--;
                j--;
            }
        }
        
        return edits;
    }
    
    
}

enum EditType { MATCH, DELETION, INSERTION, MISMATCH }

class Edit
{
    private char newChar;
    private char oldChar;
    private EditType editType;
    
    public Edit(char newChar, char oldChar, EditType editType)
    {
        this.newChar = newChar;
        this.oldChar = oldChar;
        this.editType = editType;
    }
    
    public EditType getEditType()
    {
        return editType;
    }
    
    public char getNewChar()
    {
        return newChar;
    }
    
    public char getOldChar()
    {
        return oldChar;
    }
    
}