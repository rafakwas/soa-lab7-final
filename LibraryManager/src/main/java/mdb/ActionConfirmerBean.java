package mdb;

import controller.BookController;
import controller.BookControllerInterface;
import data.BookRepositoryRemote;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@MessageDriven(name = "ActionConfirmerBean", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "library/ConfirmationQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class ActionConfirmerBean implements MessageListener {
    private final static Logger LOGGER = Logger.getLogger(ActionConfirmerBean.class.toString());

    @EJB
    BookRepositoryRemote bookRepositoryRemote;

    public ActionConfirmerBean() {
    }

    @Override
    public void onMessage(final Message msg) {
        if (msg instanceof ObjectMessage) {
            try {
                Map.Entry<String,String> entry = (Map.Entry<String,String>)((ObjectMessage) msg).getObject();
                String user = entry.getKey();
                String message = entry.getValue();
                LOGGER.info(() -> "ActionConfirmerBean: " + user + ":" + message );
                bookRepositoryRemote.addConfirmation(user,message);
            } catch (JMSException e) {
                e.printStackTrace();
            };
        }
    }
}
