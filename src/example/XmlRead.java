package example;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlRead {
    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {

        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("data.xml"));

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        // Получаем корневой элемент
        Node root = doc.getDocumentElement();
        System.out.println("---------------");


        NodeList children = root.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            // дочерний узел
            Node node = children.item(i);
            // Если нода не текст, то это - заходим внутрь
            if (node.getNodeType() != Node.TEXT_NODE) {
                NodeList chilOfChild = node.getChildNodes();

                for (int j = 0; j < chilOfChild.getLength(); j++) {
                    Node chilOfChildProp = chilOfChild.item(j);
                    // Если нода не текст, то это один из параметров  - печатаем
                    if (chilOfChildProp.getNodeType() != Node.TEXT_NODE) {
                        System.out.println(chilOfChildProp.getNodeName() + ":" + chilOfChildProp.getChildNodes().item(0).getTextContent());
                    }
                }
                System.out.println("******************");
            }


        }

    }

}

/*
For xml type:
/*
<?xml version="1.0" encoding="UTF-8"?>
<phonebook>
<person>
<name>Остап Бендер</name>
<email>ostap@12.com</email>
<phone>999-987-6543</phone>
</person>
<person>
<name>Киса Воробьянинов</name>
<email>kisa@12.com</email>
<phone>999-986-5432</phone>
</person>
<person>
<name>Мадам Грицацуева</name>
<email>madam@12.com</email>
<phone>999-985-4321</phone>
</person>
</phonebook>
*/