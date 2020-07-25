package threadpools.mycustom.pool.common4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class XmlUtil {

	public static Element getTopElement(String xmlPath) {
		SAXReader reader = new SAXReader();
		Document document1 = null;
		try {
			document1 = reader.read(XmlUtil.class.getResource(xmlPath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element node = document1.getRootElement();
		return node;
	}
}