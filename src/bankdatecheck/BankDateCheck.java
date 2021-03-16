package bankdatecheck;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class BankDateCheck {

  static List<String> weekends = Arrays.asList("saturday", "sunday");
  static List<String> us_holidays_2021 = Arrays.asList("2021-01-01", "2021-01-18", "2021-02-15",
      "2021-04-02", "2021-04-19", "2021-05-31", "2021-07-05", "2021-09-06", "2021-10-11",
      "2021-11-11", "2021-11-25", "2021-11-26", "2021-12-24");
  static List<String> uk_holidays_2021 = Arrays.asList("2021-01-01", "2021-04-02", "2021-04-05", "2021-05-03", "2021-05-31", "2021-08-30", "2021-12-27", "2021-12-28");
  static List<String> hk_holidays_2021 = Arrays.asList("2021-01-01", "2021-02-12", "2021-02-15", "2021-04-02", "2021-04-05", "2021-04-06", "2021-05-19", "2021-06-14", "2021-07-01", "2021-09-22", "2021-10-01", "2021-10-14", "2021-12-27");
  static LocalDate date;
  static LocalTime time;
  static LocalTime openingTime = LocalTime.parse("09:00:00");
  static LocalTime closingTime = LocalTime.parse("16:00:00");

  public static void main(String[] args) {
    getTime();
//    time = LocalTime.parse("17:00:00");
//    date = LocalDate.of(2021, 12, 23);
    checkTime();
    checkDate();
    System.out.println(nextOpenDateTime());
  }

  public static void getTime() {
    date = LocalDate.now();
    time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
  }

  public static void checkTime() {
    if(time.isAfter(closingTime)){
      date = date.plusDays(1L);
      updateTime();
    } else if(time.isBefore(openingTime)){
      updateTime();
    }
  }

  public static void checkDate() {
    while(us_holidays_2021.contains(date.toString()) || weekends.contains(date.getDayOfWeek().toString().toLowerCase())){
      date = date.plusDays(1L);
      updateTime();
    }
  }

  public static void updateTime() {
    time = openingTime;
  }

  public static String nextOpenDateTime() {
    String nextDate = date.toString();
    String nextTime = time.toString();
    String nextDay = date.getDayOfWeek().toString().toLowerCase();
    return String.format("{banking_date: %s %s %s}", nextDate, nextTime, nextDay);
  }

}
