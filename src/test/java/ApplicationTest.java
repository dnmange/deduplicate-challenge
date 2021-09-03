import com.adobe.coding.challenge.deduplicate.Application;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ApplicationTest {

    private String out = "";

    @Test
    void createDeduplicateOutputFile() {
        String input = getClass().getResource("code_challenge_leads.json").getFile();
        out = input.substring(0, input.lastIndexOf('/')) + "/code_challenge_leads_ " + Instant.now() + " .json";

        System.out.println("Writing to file : " + out);
        Application.main(new String[]{"-i", input, "-o", out});

        try {
            FileReader outReader = new FileReader(out);
            JsonObject jsonObject = JsonParser.parseReader(outReader).getAsJsonObject();
            assertNotNull(jsonObject);
        } catch (FileNotFoundException e) {
            fail(e);
        }
    }

    @AfterEach
    void deleteFile() {
        File file = new File(out);
        file.delete();
    }
}
