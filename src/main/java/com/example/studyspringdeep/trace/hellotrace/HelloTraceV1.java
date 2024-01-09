package com.example.studyspringdeep.trace.hellotrace;

import com.example.studyspringdeep.trace.TraceId;
import com.example.studyspringdeep.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV1 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X--";

    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }
    public void end(TraceStatus status) {
        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTImeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if ( e == null ) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTImeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTImeMs, e.toString());
        }
    }

    // level = 0
    // level = 1  :  |-->
    // level = 2  :  |   |-->
    // level = 1  : ex | <X--
    // level = 2  : ex |    | <X--
    private static String addSpace(String prefix, int level) {
        return ((1 == level - 1) ? "|" + prefix : "|   ").repeat(Math.max(0, level));
    }

}
