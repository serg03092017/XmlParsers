package example4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XMLSample
{
    private  final  String      FILE_post = "posts.xml";
    private  final  String      FILE_data = "data.xml";
    private         List<Post>  posts;

    /**
     * Процедура сохранения DOM в файл
     */
    private void writeDocument(Document document, final String path) throws TransformerFactoryConfigurationError
    {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(path);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
    private void writeDataXML()
    {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder        db  = null;
        Document               doc = null;
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            doc = db.newDocument();

            Element e_root   = doc.createElement("Posts");
            e_root.setAttribute("lang", "en");
            Element e_users  = doc.createElement("Users");
            Element e_forums = doc.createElement("Forums");
            e_root.appendChild(e_users);
            e_root.appendChild(e_forums);
            doc.appendChild(e_root);
            if (posts.size() == 0)
                return;
            List<String> users  = new ArrayList<String>();
            List<String> forums = new ArrayList<String>();
            for (int i = 0; i < posts.size(); i++){
                if (!users.contains(posts.get(i).getUsername()))
                    users.add(posts.get(i).getUsername());
                if (!forums.contains(posts.get(i).getForum()))
                    forums.add(posts.get(i).getForum());
            }
            System.out.println("    пользователей : " + users.size());
            for (String user : users) {
                Element e = doc.createElement("user");
                e.setTextContent(user);
                e_users.appendChild (e);
            }
            System.out.println("    форумов : " + forums.size());
            for (String forum : forums) {
                Element e = doc.createElement("forum");
                e.setTextContent(forum);
                e_forums.appendChild (e);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            // Сохраняем Document в XML-файл
            if (doc != null)
                writeDocument(doc, FILE_data);
        }
    }
    private boolean fileExists(final String path)
    {
        File file = new File(FILE_post);
        boolean exists = true;
        if (!file.exists()) {
            exists = false;
            System.out.println("FILE NOT EXISTS (" + file.getAbsolutePath() + ")");
        }
        return exists;
    }

    private String getValue(NodeList fields, int index)
    {
        NodeList list = fields.item(index).getChildNodes();
        if (list.getLength() > 0) {
            return list.item(0).getNodeValue();
        } else {
            return "";
        }
    }
    private void readDataXML()
    {
        posts = new ArrayList<Post>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder        db = dbf.newDocumentBuilder();
            Document               doc = null;

            FileInputStream fis = null;
            if (fileExists(FILE_post)) {
                try {
                    fis = new FileInputStream(FILE_post);
                    doc = db.parse(fis);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("row");

            for (int s = 0; s < nodeList.getLength(); s++) {
                Node node = nodeList.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) node;
                    NodeList fields = fstElmnt.getElementsByTagName("field");
                    Post p = new Post();
                    p.setForum   (getValue(fields, 0));
                    p.setDate    (sdf.parse(getValue(fields, 1)));
                    p.setSubject (getValue(fields, 2));
                    p.setUsername(getValue(fields, 4));
                    posts.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public XMLSample()
    {
        // Чтение XML-файла
        readDataXML();
        System.out.println("Количество записей : " + posts.size());
        // Создание XML-файла
        writeDataXML();
    }
    public static void main(String[] args)
    {
        new XMLSample();
        System.exit(0);
    }
}