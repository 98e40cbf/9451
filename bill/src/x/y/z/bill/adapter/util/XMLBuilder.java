package x.y.z.bill.adapter.util;

import java.io.*;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLBuilder {

    private Charset CHARSET = Charset.forName("UTF-8");
    private boolean FORMATTED_OUTPUT = true;

    private XMLBuilder() {

    }

    public static final XMLBuilder build() {
        XMLBuilder parser = new XMLBuilder();
        return parser;
    }

    public XMLBuilder setCharset(final Charset charset) {
        this.CHARSET = charset;
        return this;
    }

    public XMLBuilder setFormatted(final boolean formattedOutput) {
        this.FORMATTED_OUTPUT = formattedOutput;
        return this;
    }

    public String toXml(final Object model) throws JAXBException, IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        marshal(model, output);
        output.flush();
        return new String(output.toByteArray(), CHARSET);
    }

    public void marshal(final Object model, final OutputStream output) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(model.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, FORMATTED_OUTPUT);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, CHARSET.name());
        jaxbMarshaller.marshal(model, output);
    }

    public <T> T parseXml(final Class<T> clazz, final String xml) throws JAXBException, IOException {
        byte[] buf = xml.getBytes(CHARSET);
        ByteArrayInputStream input = new ByteArrayInputStream(buf, 0, buf.length);
        return unmarshal(clazz, input);
    }

    @SuppressWarnings("unchecked")
    public <T> T unmarshal(final Class<T> clazz, final InputStream input) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (T) jaxbUnmarshaller.unmarshal(input);
    }

    public void saveXmlToFile(final Object model, final String filename) throws FileNotFoundException, JAXBException {
        FileOutputStream fos = new FileOutputStream(filename);
        marshal(model, fos);
    }

    public void saveXmlToFile(final Object model, final File file) throws FileNotFoundException, JAXBException {
        FileOutputStream fos = new FileOutputStream(file);
        marshal(model, fos);
    }

    public <T> T loadXmlFromFile(final Class<T> clazz, final String filename)
            throws FileNotFoundException, JAXBException {
        return unmarshal(clazz, new FileInputStream(filename));
    }

    public <T> T loadXmlFromFile(final Class<T> clazz, final File file) throws FileNotFoundException, JAXBException {
        return unmarshal(clazz, new FileInputStream(file));
    }

    public <T> T loadXmlFromFile(final Class<T> clazz, final InputStream is) throws JAXBException {
        return unmarshal(clazz, is);
    }
}
