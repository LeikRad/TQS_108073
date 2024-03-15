package leikrad.dev;

import java.time.LocalDate;

public class Utils {
 
    public static LocalDate localDateFromDateParts(String year, String month, String day){
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public static LocalDate isoTextToLocalDate(String isoText){
        return LocalDate.parse(isoText);
    }
}
