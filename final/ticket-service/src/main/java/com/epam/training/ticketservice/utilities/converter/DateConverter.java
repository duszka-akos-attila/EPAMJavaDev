package com.epam.training.ticketservice.utilities.converter;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverter {

    @SneakyThrows
    public Date ConvertStringToDate(String date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
    }

    public String ConvertDateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }
}
