package example5;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SAXExample {

    final String fileName = "phonebook.xml";
    final String TAG_NAME = "name";

    DefaultHandler handler = new DefaultHandler() {
        boolean tagOn = false; //флаг начала разбора тега

        /**
         * Метод вызывается, когда SAXParser начинает
         * обработку тега
         */
        @Override
        public void startElement(String uri,
                                 String localName,
                                 String qName,
                                 Attributes attributes)
                throws SAXException {
            //устанавливаем флаг для тега TAG_NAME
            tagOn = (qName.equalsIgnoreCase(TAG_NAME));
            System.out.println("\t<" + qName + ">");
        }

        /**
         * Метод вызывается, когда SAXParser считывает
         * текст между тегами
         */
        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            // Проверка флага
            if (tagOn) {
                // Флаг установлен
                System.out.println("\t\t" +
                        new String(ch, start, length));
                tagOn = false;
            }
        }

        @Override
        public void startDocument() throws SAXException {
            System.out.println("Начало разбора документа!");
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("Разбор документа завершен!");
        }
    };

    public SAXExample() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            // Стартуем разбор XML-документа
            saxParser.parse(fileName, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new SAXExample();
        System.exit(0);
    }
}
