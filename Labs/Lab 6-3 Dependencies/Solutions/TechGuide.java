package lab6;

import org.springframework.stereotype.Component;

@Component
public class TechGuide  implements Manual{

    @Override
    public String lookup() {
        return "Just a sec.. asking ChatGPT";
    }

}
