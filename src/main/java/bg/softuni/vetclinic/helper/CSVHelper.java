package bg.softuni.vetclinic.helper;

import bg.softuni.vetclinic.model.entities.DiagnosisEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CSVHelper {
    public static ByteArrayInputStream historyToCSV(Set<DiagnosisEntity> historySet) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (DiagnosisEntity diagnosis : historySet) {
                List<String> data = Arrays.asList(
                        diagnosis.getDoctorName(),
                        diagnosis.getDoctorCommentary(),
                       String.valueOf(diagnosis.getMedications()),
                       String.valueOf(diagnosis.getDiagnoseDate())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
