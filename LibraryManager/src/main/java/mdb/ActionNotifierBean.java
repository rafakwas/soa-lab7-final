package mdb;

import data.BookRepositoryRemote;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageDriven(name = "ActionNotifierBean", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "library/NotificationTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class ActionNotifierBean implements MessageListener {
    private final static Logger LOGGER = Logger.getLogger(ActionNotifierBean.class.toString());

    @EJB
    BookRepositoryRemote bookRepositoryRemote;

    public ActionNotifierBean() {
    }

    @Override
    public void onMessage(final Message msg) {
        if (msg instanceof TextMessage) {
        }
        try {
            final String text = ((TextMessage) msg).getText();
            bookRepositoryRemote.addNotification(text);
            LOGGER.info(() -> "ActionNotifierBean notifications:" + bookRepositoryRemote.getNotifications());
            LOGGER.info(() -> "ActionNotifierBean: " + text);

        } catch (final JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
