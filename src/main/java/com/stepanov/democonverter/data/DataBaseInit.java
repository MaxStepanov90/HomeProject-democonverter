package com.stepanov.democonverter.data;

import com.stepanov.democonverter.entities.Currency;
import com.stepanov.democonverter.repositories.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

@Slf4j
@Component
@PropertySource("classpath:exchange.properties")
public class DataBaseInit {

    @Value("${currencies.service.url}")
    protected String currenciesServiceUrl;

    private CurrencyRepository currencyRepository;

    @Autowired
    public DataBaseInit(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @PostConstruct
    public void postConstruct() throws IOException, SAXException, ParserConfigurationException, ParseException {

        Document document = loadDocument(currenciesServiceUrl);

        NodeList currenciesNodeList = document.getElementsByTagName("Valute");
        for (int i = 0; i < currenciesNodeList.getLength(); i++) {
            if (currenciesNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element currencyElement = (Element) currenciesNodeList.item(i);

                Currency currency = new Currency();
                currency.setLoadDate(LocalDate.now());
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


    private Document loadDocument(String url) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        URL xmlUrl = new URL((url));
        InputStream in = xmlUrl.openStream();
        log.info("Loading xml by url: {}", xmlUrl);
        return builder.parse(in);
    }
}
