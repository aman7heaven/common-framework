package com.autopilot.models.payload;

import com.autopilot.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueuePayload<T> {
    private EventType eventType;
    private OffsetDateTime eventTime;
    private T payload;
}
