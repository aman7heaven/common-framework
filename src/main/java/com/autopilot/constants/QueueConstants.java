package com.autopilot.constants;

public final class QueueConstants {

    private QueueConstants(){}

    public static final String EXCHANGE_NOTIFICATION = "notification.exchange";

    public static final String QUEUE_SMS = "QUEUE.SMS";
    public static final String QUEUE_EMAIL = "QUEUE.EMAIL";
    public static final String QUEUE_WHATSAPP = "whatsapp.queue";

    public static final String ROUTING_KEY_EMAIL = "email";
    public static final String ROUTING_KEY_SMS = "sms";
    public static final String ROUTING_KEY_WHATSAPP = "whatsapp";
}
