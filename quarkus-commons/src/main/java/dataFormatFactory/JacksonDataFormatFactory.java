package dataFormatFactory;

import lombok.NoArgsConstructor;
import org.apache.camel.component.jackson.JacksonDataFormat;

@NoArgsConstructor
public class JacksonDataFormatFactory {
    public static JacksonDataFormat json(Class<?> formatClass){
        return new JacksonDataFormat(formatClass);
    }
}
