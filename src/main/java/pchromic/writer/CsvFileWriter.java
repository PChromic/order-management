package pchromic.writer;

import pchromic.domain.Report;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class CsvFileWriter {

    public CsvFileWriter() {
    }

    public void writeCsv (Report report) {

        ICsvBeanWriter beanWriter = null;
        String csvFileName = "report.csv";
        CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // ordersAmount

        };

        try {
            beanWriter = new CsvBeanWriter(new FileWriter(csvFileName),
                    CsvPreference.STANDARD_PREFERENCE);

            String[] header = new String[1];
            if(!Objects.isNull(report.getOrdersAmount())){
                header[0] = "ordersAmount";
            }
            if(!Objects.isNull(report.getOrdersValue())){
                header[0] = "ordersValue";
            }
            if(!Objects.isNull(report.getOrdersAvgValue())){
                header[0] = "ordersAvgValue";
            }

            beanWriter.writeHeader(header);

            beanWriter.write(report, header, processors);


        } catch (
                IOException ex) {
            System.err.println("Error writing the CSV file: " + ex);
        } finally {

            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the writer: " + ex);
                }
            }
        }
    }
}
