package com.petbuddy.api.event;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventExceptionHandler implements SubscriberExceptionHandler {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void handleException(Throwable exception, SubscriberExceptionContext context) {
    log.error("Unexpected exception occurred [{} > {} with {}]: {}",
        context.getSubscriber(), context.getSubscriberMethod(), context.getEvent(), exception.getMessage(), exception);
  }

}