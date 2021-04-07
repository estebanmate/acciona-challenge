package com.acciona.challenge.logging;

import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersistingErrorHandler<T> implements Consumer<Throwable> {

  private Class<T> clazz;

  public PersistingErrorHandler(Class<T> clazz) {
    super();
    this.clazz = clazz;
  }

  @Override
  public void accept(Throwable fault) {
    log.error(
        "Exception during processing {} {}",
        clazz.getSimpleName(),
        fault == null ? "null" : fault.getMessage());
  }
}
