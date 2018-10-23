package pchromic.writer;

import pchromic.domain.Report;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Objects;

public class XmlFileWriter {

    public boolean writeXml(Report report) {


        try

        {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("report");
            doc.appendChild(rootElement);

            if(!Objects.isNull(report.getOrdersAmount())){
                Element xmlOrdersAmount = doc.createElement("ordersAmount");
                xmlOrdersAmount.appendChild(doc.createTextNode(report.getOrdersAmount()));
                rootElement.appendChild(xmlOrdersAmount);
            }
            if(!Objects.isNull(report.getOrdersValue())){
                Element xmlOrdersVal = doc.createElement("ordersValue");
                xmlOrdersVal.appendChild(doc.createTextNode(report.getOrdersValue()));
                rootElement.appendChild(xmlOrdersVal);

            }
            if(!Objects.isNull(report.getOrdersAvgValue())){
                Element xmlOrdersAvgVal = doc.createElement("ordersAvgValue");
                xmlOrdersAvgVal.appendChild(doc.createTextNode(report.getOrdersAvgValue()));
                rootElement.appendChild(xmlOrdersAvgVal);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("reports/report.xml"));

            // Output to console for testing
            //StreamResult result2 = new StreamResult(System.out);
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);

            System.out.println("File saved!");
            return true;

        } catch(
                ParserConfigurationException | TransformerException pce)

        {
            pce.printStackTrace();
            return false;
        }
    }

}
