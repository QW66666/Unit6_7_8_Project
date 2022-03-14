import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private String answer;
    ArrayList<String> dictionary;

    private void importDictionary()
    {
        try
        {
            FileReader filereader = new FileReader("src\\words.txt");
            BufferedReader reader = new BufferedReader(new FileReader(filereader));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
