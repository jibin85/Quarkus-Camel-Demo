package org.transformation.processor;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.transformation.model.request.Customer;
import org.transformation.model.request.CustomerDetails;
import org.transformation.model.request.Insurance;
import org.transformation.model.request.Premium;
import org.transformation.model.response.ClaimResponse;
import org.transformation.model.response.CustomerDetailsResponse;
import org.transformation.model.response.CustomerResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@ApplicationScoped
public class InsuranceMappingFL implements Processor {

    private static final String EMPTY_STRING = "";
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final String DDMMYYYY = "dd-MM-yyyy";
    
    @Override
    public void process(Exchange exchange) throws Exception {
        try {
            Insurance insurance = exchange.getIn().getBody(Insurance.class);
            CustomerDetails customerDetails = insurance.getCustomerDetails();
            Customer customer = customerDetails.getCustomer();
            Premium premium = customerDetails.getPremium();
            CustomerDetailsResponse customerDetailsValue = new CustomerDetailsResponse();
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
        } catch (Exception e) {
            System.out.println("Exception Caught :-( " +e.getMessage());
        }
    }

    private void setEmptyData(CustomerDetailsResponse customerDetailsValue) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomerName(EMPTY_STRING);
        customerResponse.setCustomerId(EMPTY_STRING);
        customerResponse.setCustomerAge(EMPTY_STRING);
        ClaimResponse claimResponse = new ClaimResponse();
        claimResponse.setClaimAmt(BigDecimal.ZERO);
        customerDetailsValue.setCustomer(customerResponse);
        customerDetailsValue.setClaim(claimResponse);
    }

    public static LocalDate parseDateOfBirth(String dobStr) {
        return LocalDate.parse(dobStr, DateTimeFormatter.ofPattern(DDMMYYYY));
    }

    public static String calculateAge(LocalDate dob) {
        return String.valueOf(Period.between(dob, LocalDate.now()).getYears());
    }

    private static void customerDetails(Customer customer, Integer age, CustomerDetailsResponse customerDetailsValue) {
        CustomerResponse customerResponse = new CustomerResponse();
        if(Objects.nonNull(customer)) {
            if (StringUtils.isNotBlank(customer.getCustomerName())) {
                customerResponse.setCustomerName(customer.getCustomerName().toUpperCase());
            }
            if (StringUtils.isNotBlank(customer.getCustomerId())) {
                customerResponse.setCustomerId(customer.getCustomerId());
            }
            customerResponse.setCustomerAge(age.toString());
            customerDetailsValue.setCustomer(customerResponse);
        }
    }

    private static void claimDetails(Premium premium, CustomerDetailsResponse customerDetailsValue) {
        ClaimResponse claimResponse = new ClaimResponse();
        if (Objects.nonNull(premium) && Objects.nonNull(premium.getPremiumAmount()) && premium.getPremiumAmount().compareTo(new BigDecimal(3000.00)) >= 0 && StringUtils.isNotBlank(premium.getPremiumScheme()) && "mediclaim".equalsIgnoreCase(premium.getPremiumScheme())) {
            claimResponse.setClaimAmt(new BigDecimal("300000.00").setScale(2, RoundingMode.FLOOR));
        } else {
            claimResponse.setClaimAmt(new BigDecimal("150000.00").setScale(2, RoundingMode.FLOOR));
        }
        customerDetailsValue.setClaim(claimResponse);
    }
}
