# ITCS-6114_Project-1_LZW_Compression


## Description
This project is done by using the Lempel–Ziv–Welch (LZW) algorithm with Java 17. It contains two java files, one does the encoding and the other does the decoding. A Hashmap was used to store the key and value pairs. The Hashmap was first initialized with an ASCII set of 256 characters. As encoding or decoding goes, more character strings will be stored in the Hashmap. For encoding, the key is the character string and the value is the index of that string in the Hashmap. However, decoding was set up another way around, the key is the index of the character string and the value is that string. Each java file contains two functions, main and compress (decompress). The main function takes the command line argument, initializes the Hashmap, calculates the maximum Hashmap size, and reads the input from a text or .lzw file. The compress (decompress) function applies the LZW algorithm to finish the compression (decompression) process and the results were written into a new text or .lzw file.

## Thought about this project
The Lempel–Ziv–Welch (LZW) algorithm is quite straightforward. Having a fixed bit length also makes the implementation easier. My code passes all the tests with the two input files provided. I had a problem with how to write the results in 16-bit format to a file. My first solution was using a two byte array to store each encoded output, then write it to a file. I got the correct size for the compressed file. But the result was slightly off when I tried to read the content from the compressed file during decompression. Using buffer writer for compression and buffer reader for decompression solved this problem.

## How to run the code (Windows User)


```

```
