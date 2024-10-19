package com.team5.sparcs.pico.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogbackFilter extends Filter<ILoggingEvent> {
    private static final String[] FILTERED_STRINGS = {"SPRING_SESSION", "SELECT 1"};

    @Override
    public FilterReply decide(ILoggingEvent event) {
        String message = event.getMessage();
        for (String filter : FILTERED_STRINGS) {
            if (message.contains(filter)) {
                return FilterReply.DENY;
            }
        }
        return FilterReply.ACCEPT;
    }
}