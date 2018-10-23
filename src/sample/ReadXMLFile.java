package sample;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ReadXMLFile {

    List<Currency> currencyList = new ArrayList<>();
    private String dateData = "";
    // static variable single_instance of type Singleton
    private static ReadXMLFile single_instance = null;

    // static method to create instance of Singleton class
    public static ReadXMLFile getInstance()
    {
        if (single_instance == null)
            single_instance = new ReadXMLFile();

        return single_instance;
    }

    public void readXML() throws ParserConfigurationException, IOException, SAXException, ParseException {

        //http://www.nbp.pl/kursy/xml/lasta.xml

        String fformat="xml";
        String url = "http://www.nbp.pl/kursy/xml/lasta.xml";
        System.out.println(url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        //in.close();

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

//Build Document
        //Document document = builder.parse(new File("lasta.xml"));
        Document document = builder.parse(new InputSource(new StringReader(response.toString())));

//Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

//Here comes the root node
        Element root = document.getDocumentElement();
        //System.out.println(root.getNodeName());

//Get all currency
        //List<Currency> currencyList = new ArrayList<>();
        String rootNode = document.getDocumentElement().getNodeName();
        NodeList date = document.getElementsByTagName(rootNode);
        dateData = (String) ((Element) date.item(0)).getElementsByTagName("data_publikacji")
                .item(0).getChildNodes().item(0).getNodeValue();
        //Node n = date.item(0);
        //dateData = n.getNodeValue();
        System.out.println("dd"+dateData);
//        Element d = (Element) n;
//        dateData = d.getElementsByTagName("data_publikacji");

        NodeList nList = document.getElementsByTagName("pozycja");

        //System.out.println("============================");
        currencyList.add(new Currency("polski_zloty", 1, "PLN", 1.0));

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            //System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                //Print each currency's detail
                Element eElement = (Element) node;
                //System.out.println("nazwa_waluty: "    + eElement.getElementsByTagName("nazwa_waluty").item(0).getTextContent());
                String nazwa_waluty = eElement.getElementsByTagName("nazwa_waluty").item(0).getTextContent();
                //System.out.println("przelicznik: "  + eElement.getElementsByTagName("przelicznik").item(0).getTextContent());
                Integer przelicznik = Integer.valueOf(eElement.getElementsByTagName("przelicznik").item(0).getTextContent());
                //System.out.println("kod_waluty: "   + eElement.getElementsByTagName("kod_waluty").item(0).getTextContent());
                String kod_waluty = eElement.getElementsByTagName("kod_waluty").item(0).getTextContent();
                //System.out.println("kurs_sredni: "    + eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent());
                NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                Number number = format.parse(eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent());
                Double kurs_sredni = number.doubleValue();
                currencyList.add(new Currency(nazwa_waluty, przelicznik, kod_waluty, kurs_sredni));
            }
        }
    }
    public List<Currency> getCurrencyList(){
        return currencyList;
    }

    public List<String> getCurrencyCodeList(){
        List<String> currencyCodeList = new ArrayList<>();
        for(Currency currency : currencyList){
            currencyCodeList.add(currency.getCode());
        }
        return currencyCodeList;
    }

    public String getdateData(){
        return dateData;
    }

}
//        {
//            String filePath = "lasta.xml";
//            File xmlFile = new File(filePath);
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder;
//            try {
//                dBuilder = dbFactory.newDocumentBuilder();
//                Document doc = builder.parse(xmlFile);
//                doc.getDocu
//                List<Currency> currencyList = new ArrayList<Currency>();
//                currencyList = doc.getRootElements();
//                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//                NodeList nodeList = doc.getElementsByTagName("Currency");
//                //now XML is loaded as Document in memory, lets convert it to Object List
//                for (int i = 0; i < nodeList.getLength(); i++) {
//                    currencyList.add(getCurrency(nodeList.item(i)));
//                }
//                //lets print Employee list information
//                for (Currency currency : currencyList) {
//                    System.out.println(currency.toString());
//                }
//            } catch (SAXException | ParserConfigurationException | IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//    private static Currency getCurrency(Node node) {
//        //XMLReaderDOM domReader = new XMLReaderDOM();
//        Currency cur = new Currency();
//        if (node.getNodeType() == Node.ELEMENT_NODE) {
//            Element element = (Element) node;
//            cur.setName(getTagValue("nazwa_waluty", element));
//            cur.setMultipler(Integer.parseInt(getTagValue("przelicznik", element)));
//            cur.setCode(getTagValue("kod_waluty", element));
//            cur.setRate(Float.parseFloat(getTagValue("kurs_sredni", element)));
//        }
//
//        return cur;
//    }
//
//
//    private static String getTagValue(String tag, Element element) {
//        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
//        Node node = (Node) nodeList.item(0);
//        return node.getNodeValue();
//    }
//
//
////   // XMLReader myReader = XMLReaderFactory.createXMLReader();
////
////    URL xmlURL = new URL("http://www.nbp.pl/kursy/xml/lasta.xml");
////    URLConnection conn = xmlURL.openConnection();
////    //InputStream stream = xmlURL.openStream();
////    //URLConnection connection = xmlURL.openConnection();
////    //Document doc = parseX
////    //DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
////    //DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
////    Document doc = parseXML(conn.getInputStream());
////    DOMSource domSource = new DOMSource((Node) doc);
////    StringWriter writer = new StringWriter();
////    StreamResult result = new StreamResult(writer);
////    TransformerFactory tf = TransformerFactory.newInstance();
////    Transformer transformer = tf.newTransformer();
////    //transformer.transform(domSource, result);
////    //System.out.println("XML IN String format is: \n" + writer.toString());
//
//
//    private Document parseXML(InputStream inputStream) throws Exception {
//
//        DocumentBuilderFactory objDocumentBuilderFactory = null;
//        DocumentBuilder objDocumentBuilder = null;
//        Document doc = null;
//        try
//        {
//            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
//            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
//
//            doc = (Document) objDocumentBuilder.parse(inputStream);
//        }
//        catch(Exception ex)
//        {
//            throw ex;
//        }
//
//        return doc;
//    }
//
//    public ReadXMLFile() throws Exception {
//    }
//}
