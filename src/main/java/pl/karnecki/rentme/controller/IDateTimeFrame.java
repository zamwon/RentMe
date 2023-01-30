package pl.karnecki.rentme.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public interface IDateTimeFrame {
    LocalDate dateFrom();

    LocalDate dateTo();

    default LocalDate parseToLocalDate(String date) {
        try {
            return date != null ? LocalDate.parse(date) : null;
        } catch (final DateTimeParseException e) {
            throw new DateTimeParseException("Wrong data format", date, e.getErrorIndex());
        }
    }
}