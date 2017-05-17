import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.ArrayList;

@Remote(RegistrationSummaryInterface.class)
@Singleton
@Startup
public class RegistartionEJB implements RegistrationSummaryInterface {
    private ArrayList<String> companies = new ArrayList<>();

    @Override
    public ArrayList<String> getCompanies() {
        return this.companies;
    }

    @Override
    public boolean addCompany(String company) {
        return this.companies.add(company);
    }
}
