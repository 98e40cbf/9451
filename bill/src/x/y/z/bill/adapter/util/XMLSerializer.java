/*******************************************************************************
 * Create on 2016年1月14日 下午4:12:33
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 *
 */
public class XMLSerializer {

    private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

    private static final XStream xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));

    static {
        xstream.autodetectAnnotations(true);
        xstream.ignoreUnknownElements();
    }

    public static String toXML(Object o) {
        return toXML(o, true, XML_HEAD);
    }

    public static String toXML(Object o, String head) {
        return toXML(o, true, head);
    }

    public static String toXML(Object o, boolean format) {
        return toXML(o, format, XML_HEAD);
    }

    public static String toXML(Object o, boolean format, String head) {
        if (!format) {
            Writer writer = new StringWriter();
            xstream.marshal(o, new CompactWriter(writer, new XmlFriendlyNameCoder("_-", "_")));
            return head + writer.toString();
        }
        return head + "\n" + xstream.toXML(o);
    }

    /**
     * @param xml
     * @param clazz
     * @return
     * @author yuanxw
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseXml(final String xml, final Class<T> clazz) {
        xstream.processAnnotations(clazz);
        return (T) xstream.fromXML(xml);
    }

    public static Map<String, String> parseXml(final String xml, String rootNode) {
        XStream magicApi = new XStream();
        magicApi.registerConverter(new MapEntryConverter());
        magicApi.alias(rootNode, Map.class);
        return (Map<String, String>) magicApi.fromXML(xml);
    }

    static class MapEntryConverter implements Converter {

        public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                writer.startNode(entry.getKey().toString());
                Object val = entry.getValue();
                if (null != val) {
                    writer.setValue(val.toString());
                }
                writer.endNode();
            }

        }

        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

            Map<String, String> map = new TreeMap<String, String>();
            while (reader.hasMoreChildren()) {
                reader.moveDown();

                String key = reader.getNodeName(); // nodeName aka element's name
                String value = reader.getValue();
                map.put(key, value);

                reader.moveUp();
            }

            return map;
        }

    }
}
