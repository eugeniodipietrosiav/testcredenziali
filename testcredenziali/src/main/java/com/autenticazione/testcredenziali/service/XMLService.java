//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.service;

import com.autenticazione.testcredenziali.controller.LoginController;
import com.autenticazione.testcredenziali.repository.LoginAttemptsRepository;
import com.autenticazione.testcredenziali.repository.UserRepository;
import java.io.File;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

@Service
public class XMLService {
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    LoginAttemptsRepository loginAttemptsRepository;
    @Autowired
    UserRepository userRepository;
    @Value("${export.path}")
    private String path;

    public XMLService() {
    }

    public <T, U> void exportXML(List<T> inputSet, U instance, String transactionId) throws Exception {
        try {
            System.out.println("exportxml");
            System.out.println(inputSet.toString());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document originalDoc = builder.parse(this.path + "/tabella/word/document.xml");
            Document document = builder.newDocument();
            NodeList elements = originalDoc.getChildNodes();

            Node tableNode;
            for(int i = 0; i < elements.getLength(); ++i) {
                tableNode = elements.item(i);
                if (!tableNode.getNodeName().equals("w:tbl")) {
                    document.appendChild(document.importNode(tableNode, true));
                }
            }

            NodeList tableNodeList = document.getElementsByTagName("w:tbl");
            tableNode = tableNodeList.item(0);
//            int counterDelete = false;
            NodeList rows = document.getElementsByTagName("w:tr");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while(rows.getLength() > 0) {
                Node row = rows.item(0);
                tableNode.removeChild(row);
                System.out.println(rows.getLength());
            }

            Field[] fields = instance.getClass().getDeclaredFields();
            System.out.println(fields.toString());
            Element firstRow = document.createElement("w:tr");
            Field[] var16 = fields;
            int var17 = fields.length;

            Element cell;
            Element tcPr;
            Element tcW;
            Element pPr;
            for(int var18 = 0; var18 < var17; ++var18) {
                Field field = var16[var18];
                System.out.println(field.getName());
                cell = document.createElement("w:tc");
                tcPr = document.createElement("w:p");
                tcW = document.createElement("w:r");
                pPr = document.createElement("w:t");
                pPr.setTextContent(field.getName().toUpperCase());
                tcW.appendChild(pPr);
                tcPr.appendChild(tcW);
                cell.appendChild(tcPr);
                firstRow.appendChild(cell);
            }

            tableNode.appendChild(firstRow);
            System.out.println(inputSet.toString());
            Iterator var36 = inputSet.iterator();

            while(var36.hasNext()) {
                Object element = var36.next();
                Element row = document.createElement("w:tr");
                row.setAttribute("w:rsidTr", "00355873");
                row.setAttribute("w:rsidR", "00355873");
                row.setAttribute("w14:textId", "77777777");
                row.setAttribute("w14:paraId", "54E70444");

                for(int i = 0; i < fields.length; ++i) {
                    cell = document.createElement("w:tc");
                    tcPr = document.createElement("w:tcPr");
                    tcW = document.createElement("w:tcW");
                    pPr = document.createElement("w:pPr");
                    Element jc = document.createElement("w:jc");
                    tcW.setAttribute("w:w", "2000");
                    tcW.setAttribute("w:type", "dxa");
                    tcW.setAttribute("w:min", "2000");
                    tcW.setAttribute("w:max", "3000");
                    jc.setAttribute("w:val", "center");
                    tcPr.appendChild(tcW);
                    pPr.appendChild(jc);
                    cell.appendChild(tcPr);
                    Element par = document.createElement("w:p");
                    List<Text> valueList = new ArrayList();
                    Field[] var27 = fields;
                    int var28 = fields.length;

                    for(int var29 = 0; var29 < var28; ++var29) {
                        Field field = var27[var29];
                        field.setAccessible(true);
                        System.out.println(field.getName());
                        String elementValue;
                        if (field.get(element) == null) {
                            System.out.println("field null");
                            elementValue = "null";
                        } else {
                            System.out.println("field not null");
                            elementValue = field.get(element).toString();
                        }

                        Text txt = document.createTextNode(elementValue);
                        valueList.add(txt);
                        System.out.println(txt);
                    }

                    Element run = document.createElement("w:r");
                    Element test = document.createElement("w:t");
                    test.appendChild((Node)valueList.get(i));
                    run.appendChild(test);
                    par.appendChild(run);
                    cell.appendChild(par);
                    row.appendChild(cell);
                    tableNode.appendChild(row);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "no");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(this.path + "/tabella/word/document.xml"));
            transformer.transform(source, result);
            logger.info(transactionId + "xml file creation success");
        } catch (Exception var33) {
            logger.error(transactionId + "xml file creation failed");
        }

    }
}
