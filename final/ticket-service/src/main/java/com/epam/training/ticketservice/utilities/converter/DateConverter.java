package com.epam.training.ticketservice.utilities.converter;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverter {

    public Date convertStringToDate(String date) throws Exception {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        } catch (ParseException e) {
            throw new Exception("\u001B[31m Wrong date format! Use format 'yyyy-MM-dd HH:mm'!\u001B[0m");
        }
    }

    public String convertDateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }
}
