package com.stepanov.democonverter.data;

import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.repositories.CurrencyRepository;
import com.stepanov.democonverter.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class DataBaseInit {

    @Value("${currencies.service.url}")
    protected String currenciesServiceUrl;

    private CurrencyRepository currencyRepository;

    @Autowired
    public DataBaseInit(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @PostConstruct
    private void postConstruct() throws IOException, SAXException, ParserConfigurationException, ParseException {

        Document document;
        Date date = new Date();

        List<Currency> currencies = currencyRepository.findAll();

        if ((Utils.beforeToday(date)) | (currencies.isEmpty())) {
            document = loadDocument(currenciesServiceUrl);

            Element currenciesElement = (Element) document.getElementsByTagName("Valcurs").item(0);
            NodeList currenciesNodeList = document.getElementsByTagName("Valute");
            List<Currency> currencyList = new ArrayList<>();

            for (int i = 0; i < currenciesNodeList.getLength(); i++) {
                if (currenciesNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element currencyElement = (Element) currenciesNodeList.item(i);

                    Currency currency = new Currency();
                    currency.setIdentity(currencyElement.getAttribute("ID"));
                    NodeList childNodes = currencyElement.getChildNodes();

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element childElement = (Element) childNodes.item(j);

                            switch (childElement.getNodeName()) {
                                case "CharCode": {
                                    currency.setCharCode(childElement.getTextContent());
                                }
                                break;
                                case "Nominal": {
                                    currency.setNominal(Integer.parseInt(childElement.getTextContent()));
                                }
                                break;
                                case "Name": {
                                    currency.setName(childElement.getTextContent());
                                }
                                break;
                                case "Value": {
                                    Number d = NumberFormat.getNumberInstance(new Locale("RU")).parse(childElement.getTextContent());
                                    currency.setValue(d.doubleValue());
                                }
                                break;
                            }
                        }
                    }
                    currencyRepository.save(currency);
                }
            }
        }
    }


    private Document loadDocument(String url) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        URL xmlUrl = new URL((url));
        InputStream in = xmlUrl.openStream();
        Document document = builder.parse(in);
        return document;
    }
}