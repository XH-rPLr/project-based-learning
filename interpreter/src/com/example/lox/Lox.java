package src.com.example.lox;

import java.io.BufferedReader;
// Read text from input stream
import java.io.IOException;
// Handle input/output exception
import java.io.InputStreamReader;
// Read bytes from ionput stream and convert to char
import java.nio.charset.Charset;
// Handle char encoding
import java.nio.file.Files;
import java.nio.file.Paths;
// Read contents of a file/path
import java.util.List;
import java.util.Scanner;
// Parse/tokenize input

public class Lox {
    static boolean hadError = false;
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
        // if there is one arg, runfile. if there is 0 arg, runpromt.
    }

    private static void runFile(String path) throws IOException {
        // Reads the entire content of the file specified by path and converts it to a String. 
        // Then it calls run with this String to process it.
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        // Reads all bytes from the file at the given path.
        run(new String(bytes, Charset.defaultCharset()));
        // Converts bytes to a String using the default character encoding.
        if (hadError) System.exit(65);
    }

    private static void runPrompt() throws IOException {
        // Starts an interactive prompt for user input
        InputStreamReader input = new InputStreamReader(System.in);
        // Creates an InputStreamReader to read from standard input (System.in).
        // InputStreamReader : Converts bytes read from an underlying InputStream (like System.in) into char.
        // System.in : A predefined InputStream in Java that represents standard input.
        // It reads raw bytes from the standard input stream.
        BufferedReader reader = new BufferedReader(input);
        // Wraps it in a BufferedReader for efficient reading.

        for (;;) {
        // infinite loop
            System.out.print("> ");
            // Prints a prompt (> )
            String line = reader.readLine();
            // Reads a line of input
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source) {
    // Processes a String source by tokenizing it and printing each token
        Scanner scanner = new Scanner(source);
        // Creates a Scanner instance to parse the source
        List<Token> tokens = scanner.scanTokens();
        // Tokenizes the source
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    static void error(int line, String message) {
        report (line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
            "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
