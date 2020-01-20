package XMLParsing;

import java.io.*;
import java.util.List;

import javax.xml.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.sql.SQLXML;

import org.xml.sax.SAXException;


public class XMLReader {

        public XMLReader(InputStream data){
            try
            {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                SAXHandler saxHandler = new SAXHandler();
                System.out.println(data.read());
                saxParser.parse(data, saxHandler);

                List<Weatherstation> weatherstations = saxHandler.getWeatherstations();
                for(Weatherstation weatherstation : weatherstations)
                {
                    System.out.println("Weatherstation Id = " + weatherstation.getStn());
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
