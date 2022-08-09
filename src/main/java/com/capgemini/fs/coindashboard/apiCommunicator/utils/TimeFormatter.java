package com.capgemini.fs.coindashboard.apiCommunicator.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {

  public static Timestamp convertStringToTimestamp(String strDate, String format)
      throws ParseException {
    DateFormat formatter = new SimpleDateFormat(format);
    Date date = formatter.parse(strDate);

    return new Timestamp(date.getTime());
  }
}
