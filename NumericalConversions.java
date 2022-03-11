
// Date:     September 21, 2019
// Class:    COP 3502
//           University of Florida
// Semester: Fall 2018
//
// Implements:  
//              This program will decode hexidecimal values to decimal, binary values to decimal and convert binary to hexidecimal
//              The user is prompted to enter an option tha and the perform the operation.
//
//              The output result is printed to the user.
//
//
import java.util.NoSuchElementException;
import java.util.Scanner;                         // Need this to get access to the scanner class for user input
public class NumericConversion {                  // Created the required named "Blackjack" public class for this program

    public static void main(String[] args) {

        int menuChoice = 0;                       // User input menu termination
        int userChoice1;                          // user's first input is the menu item number
        String userChoice2 = " ";                 // user's second input is the string value to convert
        long decodeToHex;                         // decode to hex
        long decodeToBinary;                      // decode to binary
        String binToHex;                          // convert binary to hex

        Scanner scnr = new Scanner(System.in);    // input from user


        while (menuChoice == 0) {                          // runs the menu input loop until set to a 1
            System.out.println("Decoding Menu");           // prints the required menu to the user
            System.out.println("-------------");
            System.out.println("1. Decode hexadecimal");
            System.out.println("2. Decode binary");
            System.out.println("3. Convert binary to hexadecimal");
            System.out.println("4. Quit");
            System.out.println();
            System.out.print("Please enter an option: ");
            userChoice1 = scnr.nextInt();                  // takes the user input

            if (userChoice1 != 4) {                        // if it is to terminate, we don't ask for the input
                System.out.print("Please enter the numeric string to convert: ");
                userChoice2 = scnr.next();                 // we ask the user for the value to be converted
                switch (userChoice1) {                     // based on the user's menu choices

                    case 1:                                               // hex conversion
                        decodeToHex = hexStringDecode(userChoice2);       // call the method to convert hex values
                        System.out.println("Result: " + decodeToHex);     // print out the result
                        System.out.println();
                        break;

                    case 2:                                               // binary conversion
                        decodeToBinary = binaryStringDecode(userChoice2); // call the method to convert binary values
                        System.out.println("Result: " + decodeToBinary);  // print out the result
                        System.out.println();
                        break;

                    case 3:
                        binToHex = binaryToHex(userChoice2);              // binary to hex direct conversion
                        System.out.println("Result: " + binToHex);     // convert from binary to hex
                        System.out.println();
                        break;
                }
            } else {
                System.out.println("Goodbye!");                           // end the program
                menuChoice = 1;
            }
        }
    }

    public static long hexStringDecode(String hex) {                      // Decodes an entire hexadecimal string and returns its decimal value.
        int y = 0;                                                        // keeps track of the power value we are at base ^ power
        long calculation;                                                 // running total of the converted value
        long charTotal = 0;                                               // the individual character total
        short specificChar;                                               // pick off the specific character to convert
        int charIndex;                                                    // index into the character string
        char charTest1;                           // for testing the first of two characters  for 0x or ob (hex or binary)
        char charTest2;                           // for testing the second of two characters for 0x or 0b (hex or binary)

        hex = hex.toLowerCase();                  // force everything to lower case
        if (hex.length() >= 2) {                  // check for the string being long enough to search for 0x or 0b
            charTest1 = hex.charAt(0);
            charTest2 = hex.charAt(1);
            if (charTest1 == '0' && charTest2 == 'x') {  // if the string has a 0x in the front, remove it
                hex = hex.substring(2, hex.length());
            }
        }
        for(charIndex = hex.length()-1; charIndex >= 0; charIndex--){    // converts the hex value with a power function
            specificChar = hexCharDecode(hex.charAt(charIndex));         // one character at a time from the right to the left
            calculation = (long)Math.pow(16,y++ ) * (long)specificChar ; // creates the digit at the base ^ power value
            charTotal += calculation;                                    // adds to the total
        }

        return charTotal;  // return the result
    }


    public static short hexCharDecode(char digit){           // Decodes a single hexadecimal digit and returns its value.
        short retValue;                                      // holds the return value
        retValue = (short)digit;     // '0' => 48, '1' => 49, '9' => 57, 'A' => 65
        if (retValue >= 48 && retValue <= 57) {      // character 0..9 conversion
            retValue -= 48;                          // scale back to 0..9 as a value
        }
        if (retValue >= 97 && retValue <= 102) {     // character a..f all lower case here because it was pushed there above
            retValue -= 87;                          // scale back to 10..15 as a value
        }
        return retValue;
    }

    public static short binaryStringDecode(String binary) {
        int y = 0;                                                        // keeps track of the power value we are at base ^ power
        int calculation;                                                 // running total of the converted value
        short charTotal = 0;                                               // the individual character total
        short specificChar;                                               // pick off the specific character to convert
        int charIndex;                                                    // index into the character string
        char charTest1;                           // for testing the first of two characters  for 0x or ob (hex or binary)
        char charTest2;                           // for testing the second of two characters for 0x or 0b (hex or binary)

        binary = binary.toLowerCase();            // converts everything to lower case
        if (binary.length() >= 2) {               // checks for the string greater than in length
            charTest1 = binary.charAt(0);         // computes the header value
            charTest2 = binary.charAt(1);
            if (charTest1 == '0' && charTest2 == 'b') {        // tests for 0b in the front
                binary = binary.substring(2, binary.length()); // removes it if it is there
            }
        }
        for(charIndex = binary.length()-1; charIndex >= 0; charIndex--){   // runs from right to left in the string
            specificChar = hexCharDecode(binary.charAt(charIndex));        // converts one digit at a time
            calculation = (int)Math.pow(2,y++ ) * (int)specificChar ;      // calculates the base ^ power digit
            charTotal += calculation;                                      // adds to the total
        }

        return charTotal; // returns the result
    }

    public static String binaryToHex (String binary){  // Decodes a binary string, re-encodees it as hexadecimal
        String retValue = "";
        String fourBits = "";
        char hexDigit;

        if ((binary.charAt(0) == '0' && binary.charAt(1) == 'b')) {  // if there is a 0b at the beginning of the number
            binary = binary.substring(2, binary.length());           // remove it and reduce string up front by 2 characters
        }
        while (((binary.length() % 4) != 0)){                        // make it a lengt of four to pull off four at a time
            binary = '0' + binary;
        }

        while(binary.length() > 0) {                                 // convert until no more left
            fourBits = binary.substring(0, 4);                       // takes off four bits at a time
            hexDigit = binaryToHexFourBits(fourBits);         // decodes the single hex digit
            retValue = retValue + hexDigit;                   // add result to the result string
            binary = binary.substring(4, binary.length());           // remove four bits from the being decoded string
        }
        return retValue;                                             // return the string that was created
    }  // end of binaryToHex

    public static char binaryToHexFourBits(String bitsInput){   // decode four bits
        char retValue;                              // reteurn value
        short y;                            // short value of the decoded four bits
        y = binaryStringDecode(bitsInput);  // put the string into the decoder and get the value out
        if ((y >=0) && (y <= 9)) {                      // if its between 0..9
            retValue = (char) ((char) y + '0');         // add the number 0..9 to char '0'
        } else {                                                            // this was a character value A..F
            y -= 10;                                    // scale back 10..15 to 0..5
            retValue = (char) ((char) y + 'A');         // add 0..5 to character 'A' (ascii 65) to get characters 'A'..'F'
        }
        return retValue;                                                    // returns the single digit
    }  // end binaryToHexFourBits
}