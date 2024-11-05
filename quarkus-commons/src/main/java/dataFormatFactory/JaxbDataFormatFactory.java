package dataFormatFactory;

import lombok.NoArgsConstructor;
import org.apache.camel.model.dataformat.JaxbDataFormat;

@NoArgsConstructor
public class JaxbDataFormatFactory {
    public static JaxbDataFormat jaxb(String contextPath, String schema){
        JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
        jaxbDataFormat.setContextPath(contextPath);
        jaxbDataFormat.setSchema(schema);
        jaxbDataFormat.setPrettyPrint(String.valueOf(Boolean.FALSE));
        return jaxbDataFormat;
    }

    public static JaxbDataFormat jaxbPojo(Class<?> pojoClass){
        JaxbDataFormat jaxbDataFormat = new JaxbDataFormat();
        jaxbDataFormat.setContextPath(pojoClass.getPackage().getName());
        return jaxbDataFormat;
    }
}
