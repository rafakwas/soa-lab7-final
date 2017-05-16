import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageDriven(name = "MyMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "myJmsTest/MyQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class Consumer implements MessageListener {
    private final static Logger LOGGER = Logger.getLogger(Consumer.class.toString());
    @EJB
    private RegistrationSummaryInterface registrationHandler;

    @Override
    public void onMessage(final Message msg) {
        if (msg instanceof TextMessage) {
            try {
                final String company = ((TextMessage) msg).getText();
                LOGGER.info(() -> "New company registered: " + company);
                registrationHandler.addCompany(company);
            } catch (final JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
}