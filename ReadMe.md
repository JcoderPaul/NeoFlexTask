### "Калькулятор отпускных"
________________________________________________________________________________________________________________________
Микросервис на SpringBoot + Java 11 c одним API: GET "/calculacte"

Приложение принимает среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, 
которые придут сотруднику.

Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет 
отпускных с учётом праздников и выходных.

| Стек:      | Тестирование:  | Документирование: |
|------------|----------------|-------------------|
| Java       | [Testcontainers](https://github.com/JcoderPaul/NeoFlexTask/blob/master/src/test/java/me/oldboy/vaccalc/CalcAppTest.java) | Swagger           |
| SpringBoot | [JUnit ](https://github.com/JcoderPaul/NeoFlexTask/blob/master/src/test/java/me/oldboy/vaccalc/service/CalcAmountServiceTest.java)         |                   |
|            | [AssertJ](https://github.com/JcoderPaul/NeoFlexTask/blob/master/src/test/java/me/oldboy/vaccalc/service/CalcAmountServiceTest.java)        |                   |
|            | [Mockito](https://github.com/JcoderPaul/NeoFlexTask/blob/master/src/test/java/me/oldboy/vaccalc/controler/CalcControllerWithMockTest.java)        |                   |
|            | [REST Assured](https://github.com/JcoderPaul/NeoFlexTask/blob/master/src/test/java/me/oldboy/vaccalc/CalcAppTest.java)   |                   |

________________________________________________________________________________________________________________________                                                                                                                        
#### Код покрыт тестами на: Class 100% (16/16), Method 90% (40/44), Line 98% (240/244)
________________________________________________________________________________________________________________________
Примеры запросов:

- С указанием двух параметров: средней зарплаты и количества дней отпуска:
            
        http://localhost:8080/api/calculacte?avgAmount=50000&vacationDays=14

- С указанием трех параметров: средней зарплаты, количества дней отпуска и даты ухода в отпуск:

        http://localhost:8080/api/calculacte?avgAmount=50000&vacationDays=14&firstDate=2024-11-11 
                                                                                                                        
________________________________________________________________________________________________________________________
Для работы со Swagger: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
________________________________________________________________________________________________________________________
Материалы для ознакомления с сутью вопроса:
- [Как рассчитать отпускные в 2024 году: примеры и калькулятор.](https://kontur.ru/extern/spravka/50486-raschet_otpusknyh)
- [Как рассчитываются отпускные. Формулы с примерами. И в какие месяцы выгоднее всего отдыхать от работы.](https://www.rbc.ru/life/news/63a363a59a79472ed995e39c)
- [Калькулятор отпускных.](https://www.kontur-extern.ru/info/calculator-holiday)
- [Производственный календарь на 2024 год.](https://www.consultant.ru/law/ref/calendar/proizvodstvennye/2024/)

Пример: [Калькулятор от "Контур.Бухгалтерия"](https://vacation-calc.kontur.ru/Calculator/Properties?sessionId=fe89ca2d-e188-4802-b009-f7359c74d7dc&reason=forbidden)
