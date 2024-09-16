package me.oldboy.vaccalc.service;

import me.oldboy.vaccalc.dto.RequestDto;
import me.oldboy.vaccalc.util.CalculateParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CalcAmountServiceTest {

    private CalcAmountService calcAmountService;
    private static List<LocalDate> sortedHolidayList = CalculateParam.getInstance()
                                                               .getVocationDays()
                                                               .stream()
                                                               .sorted()
                                                               .toList();

    @BeforeEach
    void prepareBeforeEachTest(){
        calcAmountService = new CalcAmountService();
    }

    @ParameterizedTest
    @MethodSource("me.oldboy.vaccalc.service.CalcAmountServiceTest#getArgsForTwoParamTest")
    void shouldReturnEqualTrue_calculateByTwoParameters_ParametrizedTest(BigDecimal avgAmount,
                                                                         Integer vacDays,
                                                                         BigDecimal vacIncome){
            BigDecimal answer =
                    calcAmountService.calcVacAmount(RequestDto.builder()
                            .avgMonthAmount(avgAmount)
                            .vacationDays(vacDays)
                            .firstVacationDay(Optional.empty())
                            .build()).vacAmount();

            assertThat(answer).isEqualTo(vacIncome);
    }

    @ParameterizedTest
    @MethodSource("me.oldboy.vaccalc.service.CalcAmountServiceTest#getArgsForThreeParamTest")
    void shouldReturnEqualTrue_calculateByThreeParameters_ParametrizedTest(BigDecimal avgAmount,
                                                                           Integer vacDays,
                                                                           Optional<LocalDate> startVacationDay,
                                                                           BigDecimal vacIncome){

        BigDecimal answer = calcAmountService.calcVacAmount(RequestDto.builder()
                                                                      .avgMonthAmount(avgAmount)
                                                                      .vacationDays(vacDays)
                                                                      .firstVacationDay(startVacationDay)
                                                                      .build()).vacAmount();

        assertThat(answer).isEqualTo(vacIncome);
    }

    static Stream<Arguments> getArgsForTwoParamTest(){
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(50000), 14, BigDecimal.valueOf(20784.93)),
                Arguments.of(BigDecimal.valueOf(25000), 14, BigDecimal.valueOf(10392.46)),
                Arguments.of(BigDecimal.valueOf(50000), 7, BigDecimal.valueOf(10392.46)),
                Arguments.of(BigDecimal.valueOf(25000), 7, BigDecimal.valueOf(5196.23)),
                Arguments.of(BigDecimal.valueOf(50000), 28, BigDecimal.valueOf(41569.85)),
                Arguments.of(BigDecimal.valueOf(25000), 28, BigDecimal.valueOf(20784.93)),
                Arguments.of(BigDecimal.valueOf(25000), 0, BigDecimal.valueOf(0.00)
                        .setScale(2, RoundingMode.HALF_UP))
        );
    }

    static Stream<Arguments> getArgsForThreeParamTest(){
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(0)), BigDecimal.valueOf(4453.91)),
                Arguments.of(BigDecimal.valueOf(25000), 8, Optional.of(sortedHolidayList.get(0)), BigDecimal.valueOf(0.0)
                        .setScale(2,RoundingMode.HALF_UP)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(1)), BigDecimal.valueOf(5196.23)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(2)), BigDecimal.valueOf(5938.55)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(3)), BigDecimal.valueOf(6680.87)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(4)), BigDecimal.valueOf(7423.19)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(5)), BigDecimal.valueOf(8165.51)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(6)), BigDecimal.valueOf(8907.83)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(7)), BigDecimal.valueOf(9650.14)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(8)), BigDecimal.valueOf(9650.14)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(9)), BigDecimal.valueOf(9650.14)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(10)), BigDecimal.valueOf(8907.83)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(11)), BigDecimal.valueOf(9650.14)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(12)), BigDecimal.valueOf(9650.14)),
                Arguments.of(BigDecimal.valueOf(25000), 14, Optional.of(sortedHolidayList.get(13)), BigDecimal.valueOf(9650.14))
        );
    }
}