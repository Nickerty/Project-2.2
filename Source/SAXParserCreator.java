import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXParserCreator
{
    public static void main(String[] args)
    {
        try
        {
            SAXParserFactory fact = SAXParserFactory.newInstance();
            SAXParser saxParser = fact.newSAXParser();

            DefaultHandler handler = new DefaultHandler()
            {
                // yeet hier booleans

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
                {
                    System.out.println("Start Element: " + qName);
                    //ifs voor "if qName.equals xbooleannaam, booleannaam = true" hier
                }

                public void endElement(String uri, String localName String qName)
                {
                    System.out.println("Start Element: " + qName);
                }

                public void characters(char[] ch, int start, int length) throws SAXException
                {
                    //hierin if boolean -> shit die je er mee wil doen
                }
            };
            saxParser.parse("students.xml", handle);
        }
        catch(Exception ex){}
    }
}
