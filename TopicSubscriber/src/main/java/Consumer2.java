import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
@MessageDriven(name = "MDBconsumer2", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/myJmsTest/MyTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "topic = 'consumer2' OR topic = ''")})
public class Consumer2 implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(Consumer1.class.toString());

    @Override
    public void onMessage(final Message msg) {
        if (msg instanceof TextMessage) {
            try {
                final String text = ((TextMessage) msg).getText();
                System.out.println("Received: " + text);
                LOGGER.info(() -> "Received: " + text);
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
}