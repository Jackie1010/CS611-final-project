package Parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParser implements Parser {

    private String path;
    private ArrayList<List<String>> data;

    /*
     * Constructor:
     */
    public CSVParser(String path) {
        this.path = path;
    }

    /**
     * Method to parse the csv file
     */
    @Override
    public void parse() {
        ArrayList<List<String>> final_data = new ArrayList<List<String>>();
        String line = "";
        String cvsSplitBy = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            int i = 0;
            br.readLine();
            while ((line = br.readLine()) != null && i < 50) {
                String[] data = line.split(cvsSplitBy);
                List<String> input = Arrays.asList(data);
                // only keep the first two columns - symbol and equity name
                input = input.subList(0, 2);
                final_data.add(input);
                i++;
            }
            br.close();
            this.data = final_data;
            return;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        this.data = null;
        return;
    }

    /*
     * Getter methods:
     */
    public ArrayList<List<String>> getData() {
        return data;
    }


    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser("src/Data/snp_constituents.csv");
        csvParser.parse();
    }
}
