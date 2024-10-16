package org.transformation.dataFormat;

import org.apache.camel.model.dataformat.JaxbDataFormat;

public class JAXBDataFormatFactory {
    private JAXBDataFormatFactory() { }

    public static JaxbDataFormat jaxb(String contextPath, String schema) {
        JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
        jaxbDataFormat.setContextPath(contextPath);
        jaxbDataFormat.setSchema(schema);
        jaxbDataFormat.setPrettyPrint(String.valueOf(Boolean.FALSE));
        return jaxbDataFormat;
    }
}
