package me.oldboy.vaccalc.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Represents a request object for transfer data between control and service layers.
 */
@Builder
public record RequestDto(BigDecimal avgMonthAmount, Integer vacationDays, Optional<LocalDate> firstVacationDay) {
}
