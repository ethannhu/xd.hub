/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliCalendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author 20692
 */
public class App {

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.printf("What do u want?\nInput 1 for year calendar.\nInput 2 for weekday lookup.\n");
            int option = scan.nextInt();
            if (option == 1) {
                System.out.printf("Which year to display?\n");
                int year = scan.nextInt();
                YearCalendarPrinter.printWholeYear(year);
            } else if (option == 2) {
                System.out.printf("Input format: yyyy-mm-dd\n");
                //scan.reset();
                String ymdString = scan.next();
                LocalDate ymd = LocalDate.parse(ymdString);
                String weekday = WeekdayGetter.getWeekday(ymd);
                System.out.println("It's " + weekday + "!");
            } else {
                System.out.println("Invalid option! Please try again.");
            }
        }

    }
}

class YearCalendarPrinter {

    public static void printWholeYear(int year) {
        for (int month = 1; month <= 12; month++) {
            printMonth(year, month);
        }
    }

    private static void printMonth(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate firstDay = ym.atDay(1);
        DayOfWeek firstWeekday = firstDay.getDayOfWeek();
        System.out.printf("--- %d %d ---\n", year, month);
        System.out.println("Sun Mon Tue Wed Thu Fri Sat");
        int indent = firstWeekday.getValue() % 7;
        for (int i = 0; i < indent; i++) {
            System.out.print("    ");
        }

        for (int day = 1; day <= ym.lengthOfMonth(); day++) {
            System.out.printf("%3d ", day);
            if ((day + indent) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
}

class WeekdayGetter {

    public static String getWeekday(LocalDate ymd) {
        return ymd.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
