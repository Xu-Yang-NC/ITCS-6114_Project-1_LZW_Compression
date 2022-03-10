import java.io.*;
import java.util.*;


public class Decoder {
    public static void main(String[] args) throws IOException {
        // Initialize variables for bit length, file name and input content.
        int bitLength = 0;
        File fileName = null;
        List<Integer> input = new ArrayList<Integer>();

        // Read the values from the command line, print an error message,
        // and exit the program if the argument length is not equal to 2.
        if (args.length == 2){
            fileName = new File(args[0]);
            bitLength = Integer.parseInt(args[1]);
        } else {
            System.out.println("Please enter file name and bit length");
            System.exit(0);
        }
        // Create output file name.
        String[] temp = String.valueOf(args[0]).split("\\.",2);
        String outputName = temp[0] + "_decoded.txt";

        // Calculate the maximum table size and initialize the table as HashMap.
        int max_table_size = (int) Math.pow(2,bitLength);
        Map<Integer,String> table = new HashMap<Integer,String>();
        for(int i=0; i<=255; i++){
            table.put(i,String.valueOf((char)i));
        }

        // Initialize buffer reader with input stream reader and file input stream.
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF_16BE"));
        int num = 0;
        // Store what has been read to an integer array list.
        while((num = reader.read()) != -1){
            input.add((int)num);
        }

        // Call decompress function.
        decompress(table,input,max_table_size, outputName);


    }

    // Function for decompression.
    public static void decompress(Map<Integer,String> table, List<Integer> input,
                                  int max_size,String outputName) {
        // Declare an int variable to store input array element.
        int code = input.get(0);
        // String variables use to store the string value from hashmap with
        // the corresponding key value.
        String pre_str = table.get(code);
        String new_str = "";
        // Try and catch handles the input and output exception.
        try {
            // Initialize a butter writer with file writer
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
            // Writing the first iteration result to a file.
            writer.write(pre_str);
            // Loop through the input string array list
            for(int i=1; i<input.size(); i++){
                code = input.get(i);
                /* Check if the table contains the key value, overwrite the new
                   string with the entire previous string concatenate with the first
                   character of the previous string.
                */
                if(!table.containsKey(code)){
                    new_str = pre_str + pre_str.charAt(0);
                    // Else, overwrite the new string with the string value from the
                    // table of corresponding key values.
                } else {
                    new_str = table.get(code);
                }
                // Write the string value to the file.
                writer.write(new_str);
                /* Make sure the table size is not exceeding the maximum size before
                   adding the new string value to the table. The new string value is
                   the concatenation of the previous string and the first
                   character of the new string.
                 */
                if(table.size() < max_size){
                    table.put(table.size(), (pre_str + new_str.charAt(0)));
                }
                // Set previous string to new string.
                pre_str = new_str;
            }
            // Make sure all the data from buffer is written and close writer.
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

