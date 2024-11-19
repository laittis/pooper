package com.voronoi.pooper.manager;

import com.voronoi.pooper.annotation.Save;
import com.voronoi.pooper.bean.Settings;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SettingsManager {

    private static final SettingsManager INSTANCE = new SettingsManager();
    private final List<Settings> registeredSettings = new ArrayList<>();

    private SettingsManager() {}

    public static SettingsManager getInstance() {
        return INSTANCE;
    }

    public void register(Settings settings) {
        registeredSettings.add(settings);
    }

    public void save(String savePath) {
        ensureDirectoryExists(savePath);
        for (Settings settings : registeredSettings) {
            saveSettings(savePath, settings);
        }
    }

    public void load(String loadPath) {
        ensureDirectoryExists(loadPath);
        for (Settings settings : registeredSettings) {
            loadSettings(loadPath, settings);
        }
    }

    private void saveSettings(String basePath, Settings settings) {
        try {
            File file = settings.getSaveFile(basePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement(settings.getClass().getSimpleName());
            doc.appendChild(root);

            for (Field field : settings.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Save.class)) {
                    field.setAccessible(true);
                    Element fieldElement = doc.createElement(field.getName());
                    Object value = field.get(settings);

                    if (value instanceof List) {
                        List<?> list = (List<?>) value;
                        for (Object item : list) {
                            Element itemElement = doc.createElement("item");
                            itemElement.setTextContent(item.toString());
                            fieldElement.appendChild(itemElement);
                        }
                    } else {
                        fieldElement.setTextContent(value != null ? value.toString() : "null");
                    }

                    root.appendChild(fieldElement);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (Exception e) {
            System.err.println("Error saving settings for " + settings.getClass().getSimpleName());
            e.printStackTrace();
        }
    }

    private void loadSettings(String basePath, Settings settings) {
        try {
            File file = settings.getSaveFile(basePath);
            if (!file.exists()) return;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList fieldNodes = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < fieldNodes.getLength(); i++) {
                Node fieldNode = fieldNodes.item(i);
                if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fieldElement = (Element) fieldNode;
                    String fieldName = fieldElement.getNodeName();

                    Field field = settings.getClass().getDeclaredField(fieldName);
                    if (field.isAnnotationPresent(Save.class)) {
                        field.setAccessible(true);
                        String value = fieldElement.getTextContent();
                        Class<?> fieldType = field.getType();

                        if (fieldType == int.class) {
                            field.setInt(settings, Integer.parseInt(value));
                        } else if (fieldType == long.class) {
                            field.setLong(settings, Long.parseLong(value));
                        } else if (fieldType == boolean.class) {
                            field.setBoolean(settings, Boolean.parseBoolean(value));
                        } else if (List.class.isAssignableFrom(fieldType)) {
                            NodeList items = fieldElement.getElementsByTagName("item");
                            List<String> list = new ArrayList<>();
                            for (int j = 0; j < items.getLength(); j++) {
                                list.add(items.item(j).getTextContent());
                            }
                            field.set(settings, list);
                        } else {
                            field.set(settings, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading settings for " + settings.getClass().getSimpleName());
            e.printStackTrace();
        }
    }

    private void ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("Failed to create directory: " + directoryPath);
            }
        }
    }
}
