package me.oldboy.vaccalc.controler;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.oldboy.vaccalc.dto.RequestDto;
import me.oldboy.vaccalc.dto.ResponseDto;
import me.oldboy.vaccalc.service.CalcAmountService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Class for processing requests from the user.
 * Implement a method for receiving data and returning it.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class CalcController {

    private final CalcAmountService calcAmountService;

    /**
     * Get data for transfer to service level and return calculate result.
     *
     * @param avgMonthAmount average monthly earnings
     * @param vacationDays number of vacation days
     * @param firstDate date of first day of vacation
     *
     * @return OK - if first and second param not null, ResponseDto containing amount of vacation pay;
     *         BAD_REQUEST code - if one of them is not correct.
     */
    @GetMapping(path = "/calculacte")
    public ResponseEntity<ResponseDto> calcVacationAmount(@RequestParam(value = "avgAmount")
                                                          @PositiveOrZero(message = "Average amount can not be negative")
                                                          BigDecimal avgMonthAmount,
                                                          @RequestParam(value= "vacationDays")
                                                          @Positive(message = "Vacation days can not be negative")
                                                          @Min(1)
                                                          @Max(28)
                                                          Integer vacationDays,
                                                          @RequestParam(value = "firstDate", required = false)
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                          LocalDate firstDate) {
        log.info("Controller level: method calcVacationAmount - is start!");

        RequestDto requestDto =
                new RequestDto(avgMonthAmount, vacationDays, Optional.ofNullable(firstDate));
        ResponseEntity<ResponseDto> response =
                ResponseEntity.ok().body(calcAmountService.calcVacAmount(requestDto));

        log.info("Controller level: method calcVacationAmount answer - " + response.getBody());

        return response;
    }
}
