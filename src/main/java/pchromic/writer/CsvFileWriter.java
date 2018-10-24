package pchromic.writer;

import pchromic.domain.Report;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CsvFileWriter {
    private String[] header = new String[1];
    private String[] entry = new String[1];

    public CsvFileWriter() {
    }

    /**
     * @param report
     * @return
     */
    public boolean writeCsv(Report report) {

        try {
            Writer writer = new FileWriter("report.csv");
            // set name of report
            setHeaderName(report);
            // set value of report
            setEntry(report);
            // write to file
            writeToFile(writer);
            return true;

        } catch (IOException e) {
            System.err.println("Error writing the CSV file: " + e);
            return false;
        }

    }

    /**
     * Sets name of report basing on report type
     * @param report report to be saved to file
     */
    private void setHeaderName(Report report){
        String reportTypeName = report.getReportType().toString().toLowerCase().replace("_"," ");
        this.header[0] = reportTypeName;
    }

    /**
     * Sets value of report basing on report type
     * @param report report to be saved to file
     */
    private void setEntry(Report report){
        switch (report.getReportType()){
            default:
                break;
            case AVERAGE_VALUE:
                this.entry[0] = report.getOrdersAvgValue();
                break;
            case AMOUNT:
                this.entry[0] = report.getOrdersAmount();
                break;
            case TOTAL_VALUE:
                this.entry[0] = report.getOrdersValue();
                break;
        }
    }

    /**
     * Writes report to file
     * @param writer utility for saving file
     * @throws IOException - thrown if file cannot be saved
     */
    private void writeToFile(Writer writer) throws IOException {
        writer.write(header[0] + "\n");
        writer.write(entry[0]);

        writer.close();
    }
}