package tub.ods.mediator.controller;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;

@Component
public class AddressConverter implements Converter<String, Address>, Formatter<Address> {
    @Override
    public Address convert(String source) {
        return new Address(source);
    }

    @Override
    public Address parse(String text, Locale locale) throws ParseException {
        return convert(text);
    }

    @Override
    public String print(Address object, Locale locale) {
        return object.toString();
    }
}
