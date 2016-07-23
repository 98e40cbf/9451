package x.y.z.bill.service.message.actor;

import java.util.concurrent.atomic.AtomicReference;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import io.alpha.akka.Actors;
import io.alpha.core.config.ProcessorHelper;
import io.alpha.core.util.ApplicationContextHolder;
import x.y.z.bill.constant.message.SmsPartnerEnum;
import x.y.z.bill.model.message.SmsRecord;
import x.y.z.bill.service.message.SmsRoutingService;
import x.y.z.bill.service.message.handler.BaseSmsHandler;

public class SmsSendActor extends UntypedActor {
    private static final ActorRef actorRef = Actors.actorOf(ProcessorHelper.triple(), Props.create(SmsSendActor.class),
            "message:smsSend-actor");
    private static final AtomicReference<SmsRoutingService> smsRoutingServiceReference = new AtomicReference<>();

    public static void sendSms(SmsRecord smsRecord) {
        actorRef.tell(smsRecord, ActorRef.noSender());
    }

    public void onReceive(final Object arg0) throws Exception {
        if (arg0 instanceof SmsRecord) {
            handleSmsRecord((SmsRecord) arg0);
        } else {
            unhandled(arg0);
        }
    }

    private void handleSmsRecord(SmsRecord smsRecord) {
        SmsPartnerEnum smsPartnerEnum = getSmsRoutingService().getSmsPartner();
        String beanId = smsPartnerEnum.getHandlerBeanId();
        BaseSmsHandler baseSmsHandler = ApplicationContextHolder.getBean(beanId, BaseSmsHandler.class);
        baseSmsHandler.sendSms(smsPartnerEnum, smsRecord);
    }

    private SmsRoutingService getSmsRoutingService() {
        for (;;) {
            SmsRoutingService smsRoutingService = smsRoutingServiceReference.get();
            if (smsRoutingService != null) {
                return smsRoutingService;
            }
            smsRoutingService = ApplicationContextHolder.getBean(SmsRoutingService.class);
            if (smsRoutingServiceReference.compareAndSet(null, smsRoutingService)) {
                return smsRoutingService;
            }
        }
    }
}
