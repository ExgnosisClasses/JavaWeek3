package lab6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ITGuru implements Consultant {

    private Manual myManual;

    @Autowired
    ITGuru(Manual m) {
        this.myManual = m;
    }

    @Override
    public String getAdvice() {
        return this.myManual.lookup();
    }
}