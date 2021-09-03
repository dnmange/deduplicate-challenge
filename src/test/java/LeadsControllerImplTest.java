import com.adobe.coding.challenge.deduplicate.Lead;
import com.adobe.coding.challenge.deduplicate.LeadLogger;
import com.adobe.coding.challenge.deduplicate.LeadsController;
import com.adobe.coding.challenge.deduplicate.LeadsControllerImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeadsControllerImplTest {

    @Test
    public void deduplicate() {
        List<Lead> leads = new ArrayList<>();
        String lead1 = "{\"email\":\"foo@foo.com\", \"name\":\"joo\", \"entryDate\":\"2001\", \"_id\":\"some\"}";
        String lead2 = "{\"email\":\"foo1@foo.com\", \"name\":\"joo\", \"entryDate\":\"2002\", \"_id\":\"some\"}";
        String lead3 = "{\"email\":\"foo@foo.com\", \"name\":\"aoo\", \"entryDate\":\"2000\",\"_id\":\"some\"}";
        String lead4 = "{\"email\":\"foo@foo.com\", \"name\":\"coo\", \"entryDate\":\"2004\", \"_id\":\"some\"}";
        leads.add(new Lead(JsonParser.parseString(lead1).getAsJsonObject()));
        leads.add(new Lead(JsonParser.parseString(lead2).getAsJsonObject()));
        leads.add(new Lead(JsonParser.parseString(lead3).getAsJsonObject()));
        leads.add(new Lead(JsonParser.parseString(lead4).getAsJsonObject()));

        List<Lead> expectedEmailLead = new ArrayList<>();
        expectedEmailLead.add(new Lead(JsonParser.parseString(lead2).getAsJsonObject()));
        expectedEmailLead.add(new Lead(JsonParser.parseString(lead4).getAsJsonObject()));

        LeadsController leadsController = new LeadsControllerImpl(Mockito.mock(LeadLogger.class));
        List<Lead> deduplicateEmail = leadsController.deduplicate(leads, "email");

        assertEquals(expectedEmailLead, deduplicateEmail);

        List<Lead> deduplicateId = leadsController.deduplicate(deduplicateEmail, "_id");
        List<Lead> expectedIdLead = new ArrayList<>();
        expectedIdLead.add(new Lead(JsonParser.parseString(lead4).getAsJsonObject()));

        assertEquals(expectedIdLead, deduplicateId);
    }
}
