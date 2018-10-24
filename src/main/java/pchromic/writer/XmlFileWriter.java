package pchromic.writer;

import pchromic.domain.Report;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlFileWriter {

    public boolean writeXml(Report report) {
        try

        {
            // create XML document
            Document doc = createDocument();

            // create root elements
            Element rootElement = addRootElement(doc, "report");
            // create child elements
            Element childElement = addXmlElement(doc, report);
            // add child to root
            rootElement.appendChild(childElement);
            // write the content into xml file
            writeToFile(doc);

            System.out.println("File saved!");
            return true;

        } catch(
                ParserConfigurationException | TransformerException pce)

        {
            pce.printStackTrace();
            return false;
        }
    }

    private Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        return docBuilder.newDocument();
    }

    private Element addRootElement(Document doc, String rootName){
        Element rootElement = doc.createElement(rootName);
        doc.appendChild(rootElement);
        return rootElement;
    }

    private Element addXmlElement(Document doc, Report report){
        String reportTypeName = report.getReportType().toString().toLowerCase().replace("_"," ");
        Element xmlReportName = doc.createElement(reportTypeName);

        switch (report.getReportType()){
            default:
                break;
            case AVERAGE_VALUE:
                xmlReportName.appendChild(doc.createTextNode(report.getOrdersAvgValue()));
                break;
            case AMOUNT:
                xmlReportName.appendChild(doc.createTextNode(report.getOrdersAmount()));
                break;
            case TOTAL_VALUE:
                xmlReportName.appendChild(doc.createTextNode(report.getOrdersValue()));
                break;
        }
        return xmlReportName;
    }

    private void writeToFile(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("reports/report.xml"));

        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(source, result);
    }

}
