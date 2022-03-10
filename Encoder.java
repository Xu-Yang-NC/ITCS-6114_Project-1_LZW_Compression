import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Encoder {

    public static void main(String[] args) throws FileNotFoundException {
        // Initialize variables for bit length, file name and input content.
	    int bitLength = 0;
        File fileName = null;
        StringBuilder input = new StringBuilder();

        // Read the values from the command line, print an error message,
        // and exit the program if the argument length is not equal to 2.
        if (args.length == 2){
            fileName = new File(args[0]);
            bitLength = Integer.parseInt(args[1]);
        } else {
            System.out.println("Please enter file name and bit length");
            System.exit(0);
        }

        // Create output file name
        String[] temp = String.valueOf(args[0]).split("\\.",2);
        String outputName = temp[0] + ".lzw";


        // Read the context from the text file.
        Scanner scanner = new Scanner(fileName);
        while(scanner.hasNextLine()){
            input.append(scanner.nextLine());
        }

        // Calculate the maximum table size and initialize the table as HashMap.
        int max_table_size = (int) Math.pow(2,bitLength);
        Map<String,Integer> table = new HashMap<String,Integer> ();
        for(int i=0; i<=255; i++){
            table.put(String.valueOf((char)i),i);
        }


        // Convert string to a character array.
        char [] input_char = input.toString().toCharArray();
        // Call compress function.
        compress(table, input_char,max_table_size,outputName);


    }

    // Function for compression.
    public static void compress(Map<String,Integer> table, char [] input_char, int max_size,
                                String outputName){
        // Declare variables.
        File outputFile = new File(outputName); // Initialize output file.
        String pre_str = ""; // Declare a string variable to hold previous string.
        int outputContent; // Encoded output integer.

        // Try and catch handles the input and output exception
        try {
            // Initialize buffer writer with file output stream and file output writer.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile),"UTF_16BE"));
            // Loop through the character array.
            for (char c : input_char) {
                String symbol = String.valueOf(c); // Hold the character from array.
                // Current string = previous string + symbol.
                String cur_str = pre_str + symbol;
                // If the table contains a key of the current string value set the
                // previous string to the current string.
                if (table.containsKey(cur_str)) {
                    pre_str = cur_str;
                } else {
                    // Else read the value from the table with the key value of
                    // the previous string.
                    outputContent = table.get(pre_str);
                    // Write the encoded output to the file
                    writer.write(outputContent);
                    // Make sure the table size is not exceeding the maximum
                    // size before adding the new string to the table.
                    if (table.size() < max_size) {
                        table.put(cur_str, table.size());
                    }
                    // Set the previous string to the symbol before next iteration.
                    pre_str = symbol;
                }
            }
            // Get the output for the last iteration and write it to the file.
            outputContent = table.get(pre_str);
            writer.write(outputContent);
            // Make sure all the data from buffer is written and close writer.
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
