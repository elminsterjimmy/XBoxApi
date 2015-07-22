package com.elminster.retrieve.parser;

import com.elminster.retrieve.exception.ParseException;

public interface IParser<T> {

  public T parseResponse(String response) throws ParseException;
}
