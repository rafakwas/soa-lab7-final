import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import java.util.ArrayList;

/**
 * Created on 02.05.17.
 */
@Remote(BusinessDelegateInterface.class)
@Singleton
public class BusinessDelegate implements BusinessDelegateInterface {
    @EJB
    private BusinessLookUpInterface lookUpInterface;
    private RegistrationSummaryInterface service;
    private String serviceType;

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public ArrayList<String> doTask() {
        this.service = this.lookUpInterface.getBusinessService(this.serviceType);
        return this.service.getCompanies();
    }
}
