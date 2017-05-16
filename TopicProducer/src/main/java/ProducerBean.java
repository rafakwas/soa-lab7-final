import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.Topic;

@ManagedBean
@RequestScoped
public class ProducerBean {
    @Resource(mappedName = "java:/myJmsTest/MyTopic")
    Topic topic;
    @Inject
    JMSContext jmsContext;

    private String name;
    private String message;

    public void sendMessage() {
        jmsContext.createProducer().setProperty("topic", name).send(topic, message);
//        System.out.println("Published -> " + topic + " : " + message + "\n");
    }

    // GETTERS && SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
