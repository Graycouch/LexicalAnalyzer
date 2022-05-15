//Abdel Rahman Mansour
//Christopher Bowen
package lexicalanalyzer;

import java.io.*;
import java.util.*;
import java.lang.Character;

enum Keywords {
    IF, FOR, WHILE, FUNCTION, RETURN, INT, ELSE, DO, BREAK, END
}

public class Lexer {

    static String lexem = "";
    static Map<String, String> tokens = new HashMap<>();

    public static void Tokenize(String fileName) throws FileNotFoundException, IOException {
        createMap(tokens);

        File inputFile = new File(fileName);
        FileReader fileReader = new FileReader(inputFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = "";
        int pos = 0;

        while ((line = bufferedReader.readLine()) != null) {
            pos = 0;

            while (pos < line.length()) {
                while (pos < line.length() - 1 && (line.charAt(pos) == ' ' || line.charAt(pos) == '\t')) {
                    pos++;
                }

                if (line.charAt(pos) == '\n' || line.charAt(pos) == ' ') {
                    break;
                }
                if (Character.isLetter(line.charAt(pos))) {
                    pos = checkId(line, pos);
                } else if (Character.isDigit(line.charAt(pos))) {
                    pos = checkNum(line, pos);
                } else {
                    pos = checkUkn(line, pos);
                }

                String output = tokens.containsKey(lexem) ? tokens.get(lexem)
                        : CheckEnum(lexem.toUpperCase()) ? lexem.toUpperCase()
                        : isNumeric(lexem) ? "INT_LIT:" + lexem
                        : isNumeric(lexem.substring(0, 1)) ? errorOccured()
                        : "IDENT:" + lexem;
                System.out.println(output);
                lexem = "";
            }
        }
    }

    private static int checkId(String line, int pos) {
        while (pos < line.length() && (Character.isLetter(line.charAt(pos)) || Character.isDigit(line.charAt(pos)))) {
            lexem += line.charAt(pos);
            pos++;
        }
        return pos;
    }

    private static int checkUkn(String line, int pos) {
        String temp = "";

        if (pos < line.length() - 1 && tokens.containsKey(Character.toString(line.charAt(pos + 1)))) {
            temp += line.charAt(pos);
            temp += line.charAt(pos + 1);

            if (tokens.containsKey(temp)) {
                lexem += line.charAt(pos);
                pos++;
                lexem += line.charAt(pos);
                pos++;
                return pos;
            }
        }
        lexem += line.charAt(pos);
        pos++;

        return pos;
    }

    private static String errorOccured() {
        System.out.println("SYNTAX ERROR: INVALID IDENTIFIER NAME");
        System.exit(0);
        return "-1";
    }

    private static int checkNum(String line, int pos) {
        while (pos < line.length() && (Character.isLetter(line.charAt(pos)) || Character.isDigit(line.charAt(pos)))) {
            lexem += line.charAt(pos);
            pos++;
        }
        return pos;
    }

    private static boolean CheckEnum(String lexeme) {
        for (Keywords restrictedKeyword : Keywords.values()) {
            if (restrictedKeyword.name().equals(lexeme)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumeric(String s) {
        if (s == null) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static void createMap(Map tokens) {
        tokens.put("=", "ASSIGN");
        tokens.put("+", "ADD");
        tokens.put("-", "SUB");
        tokens.put("*", "MUL");
        tokens.put("/", "DIV");
        tokens.put("%", "MOD");
        tokens.put(">", "GT");
        tokens.put("<", "LT");
        tokens.put(">=", "GE");
        tokens.put("<=", "LE");
        tokens.put("++", "INC");
        tokens.put("(", "LP");
        tokens.put(")", "RP");
        tokens.put("{", "LB");
        tokens.put("}", "RB");
        tokens.put("|", "OR");
        tokens.put("&", "AND");
        tokens.put("==", "EE");
        tokens.put("!", "NEG");
        tokens.put(",", "COMMA");
        tokens.put(";", "SEMI");
    }
}
