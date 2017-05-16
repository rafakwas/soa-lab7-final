import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;

/**
 * Created on 02.05.17.
 */
@Remote(BusinessLookUpInterface.class)
@Singleton
public class BusinessLookUp implements BusinessLookUpInterface {
    @EJB
    private RegistrationSummaryInterface summaryInterface;

    public RegistrationSummaryInterface getBusinessService(String serviceType) {
        if (serviceType.equalsIgnoreCase("EJB")) {
            return this.summaryInterface;
        } else
            return null;
    }
}
