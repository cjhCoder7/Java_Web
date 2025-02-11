import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xml解析 {
    public static void main(String[] args) {
        // 创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("file:test.xml");
            // 每一个标签都作为一个节点
            NodeList nodeList = d.getElementsByTagName("outer"); // 可能有很多个名字为test的标签
            Node rootNode = nodeList.item(0); // 获取首个

            NodeList childNodes = rootNode.getChildNodes(); // 一个节点下可能会有很多个节点，比如根节点下就囊括了所有的节点
            // 节点可以是一个带有内容的标签（它内部就还有子节点），也可以是一段文本内容

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) { // 节点类型为标签
                    System.out.println(node.getNodeName() + " : " + node.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
