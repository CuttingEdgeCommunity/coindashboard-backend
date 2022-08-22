package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeFormatter {

  public static Timestamp convertStringToTimestamp(String strDate, String format)
      throws ParseException {
    DateFormat formatter = new SimpleDateFormat(format);
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date date = formatter.parse(strDate);

    return new Timestamp(date.getTime());
  }
}
