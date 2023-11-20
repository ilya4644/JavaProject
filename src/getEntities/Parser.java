package getEntities;

import com.opencsv.CSVReader;

public class Parser {
    public static void parser(CSVReader reader) {
        String[] next;

        try {
            while ((next = reader.readNext()) != null) {
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
