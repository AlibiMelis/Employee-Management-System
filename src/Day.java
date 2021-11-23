package src;
import src.Exceptions.ExInvalidDate;

public class Day implements Cloneable {
    private int day;
    private int month;
    private int year;
    private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

    public Day(String day) throws ExInvalidDate {
        set(day);
    }

    public void set(String sDay) throws ExInvalidDate {
        String[] sDayParts = sDay.split("-");
        int day = Integer.parseInt(sDayParts[0]);
        int year = Integer.parseInt(sDayParts[2]);
        int month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
        if (valid(year, month, day)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            throw new ExInvalidDate(sDay);
        }
    }

    static public boolean valid(int y, int m, int d) {
        if (m < 1 || m > 12 || d < 1)
            return false;
        switch (m) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            return d <= 31;
        case 4:
        case 6:
        case 9:
        case 11:
            return d <= 30;
        case 2:
            if (isLeapYear(y))
                return d <= 29;
            else
                return d <= 28;
        }
        return false;
    }
    static public boolean isLeapYear(int y) {
        if (y % 400 == 0)
            return true;
        else if (y % 100 == 0)
            return false;
        else if (y % 4 == 0)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return day + "-" + MonthNames.substring((month - 1) * 3, month * 3) + "-" + year;
    }
    @Override
    public Day clone() {
        Day copy = null;
        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }
}
