package me.oldboy.vaccalc.dto;

import java.math.BigDecimal;

/**
 * Represents a response object for transfer data between service and control layers.
 */
public record ResponseDto(BigDecimal vacAmount){
}
