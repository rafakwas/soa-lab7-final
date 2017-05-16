import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@ManagedBean(name = "SummaryViewer", eager = true)
public class SummaryViewer {
    @EJB
    private BusinessDelegateInterface delegateInterface;

    private String text;

    List<String> companies = new ArrayList<>();

    public ArrayList<String> getCompanies() {
        this.delegateInterface.setServiceType("EJB");
        return this.delegateInterface.doTask();
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
