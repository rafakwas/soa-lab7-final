package mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageDriven(name = "ActionConfirmerBean", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "library/ConfirmationQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class ActionConfirmerBean implements MessageListener {
    private final static Logger LOGGER = Logger.getLogger(ActionConfirmerBean.class.toString());


    public ActionConfirmerBean() {
    }

    @Override
    public void onMessage(final Message msg) {
        if (msg instanceof TextMessage) {
            try {
                final String text = ((TextMessage) msg).getText();
                LOGGER.info(() -> "Message from library manager receiver: " + text);
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
