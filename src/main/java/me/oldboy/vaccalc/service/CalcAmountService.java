package me.oldboy.vaccalc.service;

import lombok.extern.slf4j.Slf4j;
import me.oldboy.vaccalc.dto.RequestDto;
import me.oldboy.vaccalc.dto.ResponseDto;
import me.oldboy.vaccalc.util.CalculateParam;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for calculating the amount of vacation pay.
 * Contains methods for calculating payments taking into account two and three parameters.
 * Contains a method for calculating average daily income.
 * Contains the method for calculating daily tax.
 */
@Slf4j
@Service
public class CalcAmountService {

    private Set<LocalDate> vacDaysBase;

    /**
     * Main vacation amount calculate method.
     *
     * @param requestDtoToCalc entity contains parameters for calculate
     *
     * @return amount of vacation pay
     */
    public ResponseDto calcVacAmount(RequestDto requestDtoToCalc){
        log.info("Service level: method calcVacAmount is start");

        if(requestDtoToCalc.firstVacationDay().isEmpty()){
            log.info("Service level: method calcVacAmount - calculation with two parameters finished!");
            return new ResponseDto(simpleCalc(requestDtoToCalc.avgMonthAmount(),
                                              requestDtoToCalc.vacationDays()));
        } else {
            log.info("Service level: method calcVacAmount - calculation with three parameters finished!");
            return new ResponseDto(withHolidaysCalc(requestDtoToCalc.avgMonthAmount(),
                                                    requestDtoToCalc.vacationDays(),
                                                    requestDtoToCalc.firstVacationDay().get()));
        }
    }

    /**
     * Simple calculate method by two parameters.
     *
     * @param avgMonthAmount average monthly earnings
     * @param numberVacDays number of vacation days
     *
     * @return amount of vacation pay
     */
    private BigDecimal simpleCalc(BigDecimal avgMonthAmount, Integer numberVacDays){
        return BigDecimal.valueOf(numberVacDays)
                         .multiply((avgDayAmount(avgMonthAmount)
                                 .subtract(incomeTaxPerDay(avgDayAmount(avgMonthAmount)))))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate method by three parameters.
     *
     * @param avgMonthAmount average monthly earnings
     * @param vacationDays number of vacation days
     * @param firstVacationDay date of first day of vacation
     *
     * @return amount of vacation pay
     */
    private BigDecimal withHolidaysCalc(BigDecimal avgMonthAmount,
                                        Integer vacationDays,
                                        LocalDate firstVacationDay) {
        loadVacDaysBase(firstVacationDay, vacationDays)
                .removeAll(CalculateParam.getInstance().getVocationDays());
        Integer totalVacDays = vacDaysBase.size();

        return BigDecimal.valueOf(totalVacDays)
                .multiply((avgDayAmount(avgMonthAmount)
                        .subtract(incomeTaxPerDay(avgDayAmount(avgMonthAmount)))))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculation of average daily income
     *
     * @param avgMonthAmount average monthly earnings
     *
     * @return average daily income
     */
    private BigDecimal avgDayAmount(BigDecimal avgMonthAmount){
        return avgMonthAmount.divide(CalculateParam.getInstance().getAvgDay(),2, RoundingMode.HALF_UP);
    }

    /**
     * Calculation of daily tax
     *
     * @param avgDayAmount average daily earnings
     *
     * @return daily tax
     */
    private BigDecimal incomeTaxPerDay(BigDecimal avgDayAmount){
        return avgDayAmount.multiply(CalculateParam.getInstance().getIncomeTax());
    }

    /**
     * Create set of calendar vacation days set for calculating vacation pay taking into account holidays
     *
     * @param firstDay date of first vacations day
     * @param numberOfDays number of vacation days
     *
     * @return set of calendar vacation days
     */
    private Set<LocalDate> loadVacDaysBase(LocalDate firstDay, Integer numberOfDays){
        vacDaysBase = new HashSet<>();
        vacDaysBase.add(firstDay);
        for (long i = 1; i < numberOfDays; i++){
                vacDaysBase.add(firstDay.plusDays(i));
        }
        return vacDaysBase;
    }
}