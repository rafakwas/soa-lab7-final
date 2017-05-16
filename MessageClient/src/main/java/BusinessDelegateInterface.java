import javax.ejb.Remote;
import java.util.ArrayList;

/**
 * Created on 03.05.17.
 */
@Remote
public interface BusinessDelegateInterface {
    void setServiceType(String serviceType);

    ArrayList<String> doTask();
}
