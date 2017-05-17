import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;

@RequestScoped
@ManagedBean(name = "SummaryViewer", eager = true)
public class SummaryViewer {
//    @EJB
//    private RegistrationSummaryInterface bookHandler;
//
//    public ArrayList<String> getCompanies() {
//        return bookHandler.getCompanies();
//    }

//    private BusinessDelegate businessDelegate = new BusinessDelegate();
//
//    public ArrayList<String> getCompanies() {
//        this.businessDelegate.setServiceType("EJB");
//        return this.businessDelegate.doTask();
//    }

    @EJB
    private BusinessDelegateInterface delegateInterface;

    public ArrayList<String> getCompanies() {
        this.delegateInterface.setServiceType("EJB");
        return this.delegateInterface.doTask();
    }
}
