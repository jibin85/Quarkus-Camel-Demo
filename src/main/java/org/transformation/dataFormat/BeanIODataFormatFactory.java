package org.transformation.dataFormat;

import org.apache.camel.model.dataformat.BeanioDataFormat;


public class BeanIODataFormatFactory {
    BeanIODataFormatFactory(){}
    public static BeanioDataFormat beanio(String streamName, String mapping) {
        BeanioDataFormat beanioDataFormat = new BeanioDataFormat();
        beanioDataFormat.setStreamName(streamName);
        beanioDataFormat.setMapping(mapping);
        return beanioDataFormat;
    }
}
