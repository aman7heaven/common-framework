package com.autopilot.messaging;

import com.autopilot.config.exception.ApplicationException;
import com.autopilot.config.exception.ApplicationExceptionTypes;
import com.autopilot.config.logging.AppLogger;
import com.autopilot.models.payload.QueuePayload;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.io.IOException;

public interface IBaseQueueConsumer<T> extends ChannelAwareMessageListener {

    AppLogger log = new AppLogger(LoggerFactory.getLogger(IBaseQueueConsumer.class));

    void onMessageReceived(QueuePayload<T> payload);

    void onProcessingFailed(QueuePayload<T> payload, Exception exception);

    @Override
    default void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        boolean acked = false;

        try {
            QueuePayload<T> payload = deserialize(message.getBody());
            log.info("Received message: DeliveryTag={}, Payload={}", deliveryTag, payload);

            onMessageReceived(payload);

            // ACK only after successful processing
            // false indicates we are acknowledging a single message
            channel.basicAck(deliveryTag, false);
            acked = true;
        } catch (Exception e) {
            log.error("Failed to process message with DeliveryTag {}: {}", deliveryTag, e.getMessage(), e);
            channel.basicNack(deliveryTag, false, false); // do not requeue and false for single message
            onProcessingFailed(null, e); // Notify failure handler
        } finally {
            if (!acked) {
                try {
                    // Fallback: NACK and do not requeue the message
                    channel.basicNack(deliveryTag, false, false); // false = Do not requeue
                    log.warn("Message was not acked, removing it from queue. DeliveryTag={}", deliveryTag);
                } catch (IOException e) {
                    log.error("Failed to send fallback NACK for DeliveryTag {}: {}", deliveryTag, e.getMessage(), e);
                    // At this point, message may remain unacknowledged until consumer/channel dies
                }
            }
        }
    }

    default QueuePayload<T> deserialize(byte[] body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return mapper.readValue(body,
                    mapper.getTypeFactory().constructParametricType(QueuePayload.class, getPayloadClass()));
        } catch (Exception e) {
            log.error("Failed to deserialize queue payload: {}", new String(body), e);
            throw new ApplicationException(ApplicationExceptionTypes.FAILED_TO_DESERIALIZE_QUEUE_PAYLOAD, e);
        }
    }

    // Each consumer must provide the actual class of T
    Class<T> getPayloadClass();
}