package com.aspirephile.shared.timming;

@SuppressWarnings("UnusedDeclaration")
public class Time {
    short hours, minutes, seconds, milliSeconds;
    int days;
    static String defaultFormat = "Day:%DD %HH:%MM:%SS.%ss";
    String format;

    public enum Unit {
        DAYS, HOURS, MINUTES, SECONDS, MILLISECONDS

    }

    public Time(Time time) {
        this.setTime(time.getMilliSeconds());
    }

    public static long getTimeInMilliseconds(long day, int hour, int minute,
                                             int second, int milliSecond) {
        return milliSecond
                + (second + (minute + (hour + (day) * 24) * 60) * 60) * 1000;
    }

    public void setTime(long timeInMilliSeconds) {
        milliSeconds = (short) (timeInMilliSeconds % 1000);
        seconds = (short) ((timeInMilliSeconds / 1000) % 60);
        minutes = (short) ((timeInMilliSeconds / 1000 / 60) % (60));
        hours = (short) ((timeInMilliSeconds / 1000 / 60 / 60) % (24));
        days = (short) ((timeInMilliSeconds / 1000 / 60 / 60 / 24));
    }

    public void setTime(long day, int hour, int minute, int second,
                        int milliSecond) {
        setTime(Time.getTimeInMilliseconds(day, hour, minute, second,
                milliSecond));
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public static void setDefaultFormat(String defaultFormat) {
        Time.defaultFormat = defaultFormat;
    }

    public Time(long milliseconds) {
        setTime(milliseconds);
    }

    public Time(long day, int hour, int minute, int second, int milliSecond) {
        setTime(day, hour, minute, second, milliSecond);
    }

    public Time(long timeInMilliSeconds, String format) {
        setTime(timeInMilliSeconds);
        setFormat(format);
    }

    public short getMilliSeconds() {
        return milliSeconds;
    }

    public short getSeconds() {
        return seconds;
    }

    public short getMinutes() {
        return minutes;
    }

    public short getHours() {
        return hours;
    }

    public int getDays() {
        return days;
    }

    public Unit getNearestApproximatedUnit() {
        Time approx = new Time(0);
        if (getDays() != 0)
            return Unit.DAYS;
        else if (getHours() != 0)
            return Unit.HOURS;
        else if (getMinutes() != 0)
            return Unit.MINUTES;
        else if (getSeconds() != 0)
            return Unit.SECONDS;
        else if (getMilliSeconds() != 0)
            return Unit.MILLISECONDS;
        else
            return null;
    }

    public long getTimeInUnit(Unit unit) {
        switch (unit) {
            case DAYS:
                return getDays();
            case HOURS:
                return getHours();
            case MINUTES:
                return getMinutes();
            case SECONDS:
                return getSeconds();
            case MILLISECONDS:
                return getMilliSeconds();
        }
        return 0;
    }

    public static short getMilliSeconds(long timeInMilliSeconds) {
        return (short) (timeInMilliSeconds % 1000);
    }

    public static short getSeconds(long timeInMilliSeconds) {
        return (short) ((timeInMilliSeconds / 1000) % 60);
    }

    public static short getMinutes(long timeInMilliSeconds) {
        return (short) ((timeInMilliSeconds / 1000 / 60) % (60));
    }

    public static short getHours(long timeInMilliSeconds) {
        return (short) ((timeInMilliSeconds / 1000 / 60 / 60) % (24));
    }

    public static int getDays(long timeInMilliSeconds) {
        return (short) ((timeInMilliSeconds / 1000 / 60 / 60 / 24));
    }

    private static boolean substituteVariable(StringBuilder builder,
                                              String representation, int substitution) {
        int index = builder.indexOf(representation);
        if (index != -1) {
            builder.delete(index, index + representation.length());
            builder.insert(index, String.format(
                    "%0" + Integer.toString(representation.length() - 1) + "d",
                    substitution));
            return true;
        } else
            return false;
    }

    private static String substituteVariables(String raw, long milliSeconds) {
        StringBuilder builder = new StringBuilder(raw);
        substituteVariable(builder, "%HH", getHours(milliSeconds));
        substituteVariable(builder, "%MM", getMinutes(milliSeconds));
        substituteVariable(builder, "%SS", getSeconds(milliSeconds));
        if (!substituteVariable(builder, "%sss", getMilliSeconds(milliSeconds)))
            if (!substituteVariable(builder, "%ss",
                    (getMilliSeconds(milliSeconds) / 100)))
                substituteVariable(builder, "%s", getMilliSeconds(milliSeconds) / 10);
        return builder.toString();
        /*
         * return String.format("%02d:%02d:%02d.%03d", getHours(milliSeconds),
		 * getMinutes(milliSeconds), getSeconds(milliSeconds),
		 * getMilliSeconds(milliSeconds));
		 */
    }

    public static String getFormattedTime(long milliseconds) {
        return substituteVariables(defaultFormat, milliseconds);
    }

    public static String getFormattedTime(String format, long milliseconds) {
        if (format == null)
            return substituteVariables(defaultFormat, milliseconds);
        else

            return substituteVariables(format, milliseconds);
    }

}
