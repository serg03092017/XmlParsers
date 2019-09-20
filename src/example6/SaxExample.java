package example6;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxExample {
    public static void main(String args[]) {


        //имя файла
        final String fileName = "Phonebook.xml";

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                //Поле для указания, ято тэг NAME начался
                boolean name = false;

                //Метод вызывается когда  SAXParser "натыкается" на начало тэга
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    //Если тэг имеет имя NAME, то мы этот момент отмечыаем - начался тэг NAME
                    if (qName.equalsIgnoreCase("NAME")) {
                        name = true;
                    }
                }

                //Метод вызывается когда SAXParser считывает текст между тэгами
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    //Если мы перед этим отметили, что имя тэга NAME - значит нам надо текст использовать.
                    if (name) {
                        System.out.println("Name: " + new String(ch, start, length));
                        name = false;
                    }
                }
            };
            //Стартуем разбор методом parse, которому передаём наследника от DefaultHandler, который будет вызываться в нужные моменты
            saxParser.parse(fileName, handler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
