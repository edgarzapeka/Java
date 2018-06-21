package edz;

import org.w3c.dom.Document;

import javax.swing.*;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class XMLWriteFrame extends JFrame {
    private RectangleComponent comp;
    private JFileChooser chooser;

    public XMLWriteFrame(){
        chooser = new JFileChooser();

        comp = new RectangleComponent();
        add(comp);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem newItem = new JMenuItem("New");
        menu.add(newItem);
        newItem.addActionListener(event -> comp.newDrawing());

        JMenuItem saveItem = new JMenuItem("Save with DOM/XSLT");
        menu.add(saveItem);
        saveItem.addActionListener(event -> saveDocument());

        JMenuItem saveStAXItem = new JMenuItem("SAve with StAX");
        menu.add(saveStAXItem);
        saveStAXItem.addActionListener(event -> saveStAX());

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(event -> System.exit(0));
        pack();
    }

    public void saveDocument(){
        try{
            if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
            File file  = chooser.getSelectedFile();
            Document doc = comp.buildDocument();
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.w3.org/TR/2000/CR-SVG/20000802/DTD/svg-20000802.dtd");
            t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 20000802//EN");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amout", "2");
            t.transform(new DOMSource(doc), new StreamResult(Files.newOutputStream(file.toPath())));

        } catch (TransformerException | IOException e){
            e.printStackTrace();
        }
    }

    public void saveStAX(){
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file  = chooser.getSelectedFile();
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        try{
            XMLStreamWriter writer = factory.createXMLStreamWriter(Files.newOutputStream(file.toPath()));
            try{
                comp.writeDocument(writer);
            }finally {
                writer.close();
            }
        } catch (XMLStreamException | IOException e){
            e.printStackTrace();
        }
    }
}
