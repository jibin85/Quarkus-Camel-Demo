package org.transformation.processor;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.transformation.model.request.*;

@ApplicationScoped
public class PojoChecker implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("-----------POJO CHEKER------------STARTS---------");
        Insurance insurance = exchange.getIn().getBody(Insurance.class);
        System.out.println("insurance Value from Body: " +insurance);
        CustomerDetails customerDetails = insurance.getCustomerDetails();
        System.out.println("customerDetails value from insurance: "+customerDetails);
        Customer customer = customerDetails.getCustomer();
        System.out.println("customer values from insurance: "+customer);
        Premium premium = customerDetails.getPremium();
        System.out.println("premium values from insurance: "+premium);
        Claim claim = customerDetails.getClaim();
        System.out.println("claim values from insurance: "+claim);
        exchange.getIn().setBody(insurance);
        System.out.println("-----------POJO CHECKER-----------ENDS---------");
    }
}
