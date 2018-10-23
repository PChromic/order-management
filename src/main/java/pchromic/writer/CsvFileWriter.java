package pchromic.writer;

import pchromic.domain.Report;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public class CsvFileWriter {

    public CsvFileWriter() {
    }

    public boolean writeCsv(Report report) {

        try {
            Writer writer = new FileWriter("reports/report.csv");

            String[] header = new String[1];
            String[] entry = new String[1];

            if(!Objects.isNull(report.getOrdersAmount())){
                header[0] = "ordersAmount";
                entry[0] = report.getOrdersAmount();
            }
            if(!Objects.isNull(report.getOrdersValue())){
                header[0] = "ordersValue";
                entry[0] = report.getOrdersValue();
            }
            if(!Objects.isNull(report.getOrdersAvgValue())){
                header[0] = "ordersAvgValue";
                entry[0] = report.getOrdersAvgValue();
            }
            writer.write(header[0] + "\n");
            writer.write(entry[0]);

            writer.close();
            return true;

        } catch (IOException e) {
            System.err.println("Error writing the CSV file: " + e);
            return false;
        }

    }
}