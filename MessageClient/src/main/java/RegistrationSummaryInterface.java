import javax.ejb.Remote;
import java.util.ArrayList;

@Remote
public interface RegistrationSummaryInterface {
    ArrayList<String> getCompanies();

    boolean addCompany(String company);
}