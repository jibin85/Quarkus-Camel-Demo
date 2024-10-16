/*
package org.transformation.processor;

import com.example.fileComponent.transformation.model.request.*;
import com.example.fileComponent.transformation.model.request.CustomerDetails;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Configuration("insuranceMappingXML")
public class InsuranceMappingXML implements Processor {

    private static final String EMPTY_STRING = "";
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final String DDMMYYYY = "dd-MM-yyyy";
    
    @Override
    public void process(Exchange exchange) throws Exception {
        Insurance insurance = exchange.getIn().getBody(Insurance.class);
        CustomerDetails customerDetails = insurance.getCustomerDetails();
        Customer customer = customerDetails.getCustomer();
        Premium premium = customerDetails.getPremium();
        Claim claim = customerDetails.getClaim();
        
        com.insurance.xmlendpoint.CustomerDetails customerDetailsValue = new com.insurance.xmlendpoint.CustomerDetails();
        if (StringUtils.isNotBlank(customer.getCustomerDOB())) {
            Integer age = Integer.parseInt(calculateAge(parseDateOfBirth(customer.getCustomerDOB())));
            if (age >= 18) {
                customerDetailsValue.setValidClaim(YES);
                customerDetails(customer, age, customerDetailsValue);
                claimDetails(premium, customerDetailsValue);
            } else {
                customerDetailsValue.setValidClaim(NO);
                setEmptyData(customerDetailsValue);
            }
        }
        exchange.getIn().setBody(customerDetailsValue);
    }

    private void setEmptyData(com.insurance.xmlendpoint.CustomerDetails customerDetailsValue) {
        com.insurance.xmlendpoint.CustomerDetails.Customer customer = new com.insurance.xmlendpoint.CustomerDetails.Customer();
        customer.setCustomerName(EMPTY_STRING);
        customer.setCustomerId(EMPTY_STRING);
        customer.setCustomerAge(EMPTY_STRING);
        com.insurance.xmlendpoint.CustomerDetails.Claim claim = new com.insurance.xmlendpoint.CustomerDetails.Claim();
        claim.setClaimAmt(BigDecimal.ZERO);
        customerDetailsValue.setCustomer(customer);
        customerDetailsValue.setClaim(claim);
    }

    public static LocalDate parseDateOfBirth(String dobStr) {
        return LocalDate.parse(dobStr, DateTimeFormatter.ofPattern(DDMMYYYY));
    }

    public static String calculateAge(LocalDate dob) {
        return String.valueOf(Period.between(dob, LocalDate.now()).getYears());
    }

    private static void customerDetails(Customer customer, Integer age, com.insurance.xmlendpoint.CustomerDetails customerDetailsValue) {
        com.insurance.xmlendpoint.CustomerDetails.Customer customerValue = new com.insurance.xmlendpoint.CustomerDetails.Customer();
        if(Objects.nonNull(customer)) {
            if (StringUtils.isNotBlank(customer.getCustomerName())) {
                customerValue.setCustomerName(customer.getCustomerName().toUpperCase());
            }
            if (StringUtils.isNotBlank(customer.getCustomerId())) {
                customerValue.setCustomerId(customer.getCustomerId());
            }
            customerValue.setCustomerAge(age.toString());
            customerDetailsValue.setCustomer(customerValue);
        }
    }

    private static void claimDetails(Premium premium, com.insurance.xmlendpoint.CustomerDetails customerDetailsValue) {
        com.insurance.xmlendpoint.CustomerDetails.Claim claimValue = new com.insurance.xmlendpoint.CustomerDetails.Claim();
        if (Objects.nonNull(premium) && Objects.nonNull(premium.getPremiumAmount()) && premium.getPremiumAmount().compareTo(new BigDecimal(3000.00)) >= 0 && StringUtils.isNotBlank(premium.getPremiumScheme()) && "mediclaim".equalsIgnoreCase(premium.getPremiumScheme())) {
            claimValue.setClaimAmt(new BigDecimal(300000.00).setScale(2));
        } else {
            claimValue.setClaimAmt(new BigDecimal(150000.00).setScale(2));
        }
        customerDetailsValue.setClaim(claimValue);
    }
}
*/
