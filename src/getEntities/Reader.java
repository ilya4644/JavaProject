package getEntities;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;

public class Reader {
    public static CSVReader readData(String file) {
        try {
            FileReader fileReader = new FileReader(file);

            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

            return new CSVReaderBuilder(fileReader).withCSVParser(parser).build();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
