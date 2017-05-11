package com.appdirect.microsoft.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.testng.Reporter;
import com.appdirect.microsoft.googlesheetread.EditionPriceDomain;
public class CSVGenerator {
    private static final String CommaSeparator = ",";
    private static final String NewLineSeparator = "\n";
    private static final String Space = "";
    private static final String FILE_HEADER = "SrNo,OfferId,EditionName,USD,,EUR,,AUD,,GBP,";
    private static final String SUB_FILE_HEADER = ",,,OLD,NEW,OLD,NEW,OLD,NEW,OLD,NEW";
    public CSVGenerator csvgenerator(List<EditionPriceDomain> priceForFoundEditionsList) {
        FileWriter fileWriter = null;
        int count=0;
        
        try {
            fileWriter = new FileWriter("EditionSuccessfulOnFinding.csv");
            fileWriter.append(FILE_HEADER.toString());
            fileWriter.append(NewLineSeparator);
            fileWriter.append(SUB_FILE_HEADER.toString());
            fileWriter.append(NewLineSeparator);
            
            for (EditionPriceDomain editions : priceForFoundEditionsList) {
                fileWriter.append(String.valueOf(++count));
                fileWriter.append(CommaSeparator);
                fileWriter.append(String.valueOf(editions.getOfferid()));
                fileWriter.append(CommaSeparator);
                fileWriter.append(editions.getEditionName().replaceAll(CommaSeparator, Space));
                fileWriter.append(CommaSeparator);
                fileWriter.append(editions.getOldusdPrice());
                fileWriter.append(CommaSeparator);
                fileWriter.append(editions.getNewusdPrice());
                fileWriter.append(CommaSeparator);
                fileWriter.append(editions.getOldeurPrice());
                fileWriter.append(CommaSeparator);
                fileWriter.append(editions.getNeweurPrice());
                fileWriter.append(CommaSeparator);
                fileWriter.append(editions.getOldgbpPrice());
                fileWriter.append(CommaSeparator);
                fileWriter.append(editions.getNewgbpPrice());
                fileWriter.append(CommaSeparator);
                fileWriter.append(editions.getOldaudPrice());
                fileWriter.append(CommaSeparator);
                fileWriter.append(editions.getNewaudPrice());
                fileWriter.append(NewLineSeparator);
            }
            Reporter.log("CSV file was created successfully !!!", true);
        } catch (Exception e) {
            Reporter.log("Error in CsvFileWriter !!!", true);
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                Reporter.log("Error while flushing/closing fileWriter !!!", true);
                e.printStackTrace();
            }
        }
        return new CSVGenerator();
    }
}