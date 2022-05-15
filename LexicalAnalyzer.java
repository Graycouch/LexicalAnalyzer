package lexicalanalyzer;
import java.io.IOException;
import java.util.Scanner;

public class LexicalAnalyzer 
{
    public static void main(String[] args) throws IOException 
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a file name: ");
        String fileName = in.next();
        
        Lexer lexer = new Lexer();
        lexer.Tokenize(fileName);
        
        in.close();
    }
    
}
