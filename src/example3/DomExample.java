package example3;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DomExample {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // Ридер для считывания имени тега из консоли
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Получение фабрики, чтобы после получить билдер документов.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам нужно.
        Document document = builder.parse(new File("resource/xml_file3.xml"));

        // Считывание имени тега для поиска его в файле
        String element = reader.readLine();

        // Получение списка элементов, однако для удобства будем рассматривать только первое совпадение в документе.
        // Так же заметьте, что мы ищем элемент внутри документа, а не рут элемента. Это сделано для того, чтобы рут элемент тоже искался.
        NodeList matchedElementsList = document.getElementsByTagName(element);

        // Даже если элемента нет, всегда будет возвращаться список, просто он будет пустым.
        // Потому, чтобы утверждать, что элемента нет в файле, достаточно проверить размер списка.
        if (matchedElementsList.getLength() == 0) {
            System.out.println("Тег " + element + " не был найден в файле.");
        } else {
            // Получение первого элемента.
            Node foundedElement = matchedElementsList.item(0);

            System.out.println("Элемент был найден!");

            // Если есть данные внутри, вызов метода для вывода всей информации
            if (foundedElement.hasChildNodes())
                printInfoAboutAllChildNodes(foundedElement.getChildNodes());
        }
    }

    /**
     * Рекурсивный метод, который будет выводить информацию про все узлы внутри всех узлов, которые пришли параметром, пока не будут перебраны все узлы.
     * @param list Список узлов.
     */
    private static void printInfoAboutAllChildNodes(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            // У элементов есть два вида узлов - другие элементы или текстовая информация. Потому нужно разбираться две ситуации отдельно.
            if (node.getNodeType() == Node.TEXT_NODE) {
                // Фильтрация информации, так как пробелы и переносы строчек нам не нужны. Это не информация.
                String textInformation = node.getNodeValue().replace("\n", "").trim();

                if(!textInformation.isEmpty())
                    System.out.println("Внутри элемента найден текст: " + node.getNodeValue());
            }
            // Если это не текст, а элемент, то обрабатываем его как элемент.
            else {
                System.out.println("Найден элемент: " + node.getNodeName() + ", его атрибуты:");

                // Получение атрибутов
                NamedNodeMap attributes = node.getAttributes();

                // Вывод информации про все атрибуты
                for (int k = 0; k < attributes.getLength(); k++)
                    System.out.println("Имя атрибута: " + attributes.item(k).getNodeName() + ", его значение: " + attributes.item(k).getNodeValue());
            }

            // Если у данного элемента еще остались узлы, то вывести всю информацию про все его узлы.
            if (node.hasChildNodes())
                printInfoAboutAllChildNodes(node.getChildNodes());
        }
    }
    }