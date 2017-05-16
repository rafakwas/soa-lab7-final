import javax.ejb.Remote;

/**
 * Created on 03.05.17.
 */
@Remote
public interface BusinessLookUpInterface {
    RegistrationSummaryInterface getBusinessService(String serviceType);
}
