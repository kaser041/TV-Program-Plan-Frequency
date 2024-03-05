package pro.sieben.sat.tv.program.plan.frequency.utils;

import org.springframework.web.client.RestTemplate;
import pro.sieben.sat.tv.program.plan.frequency.model.ApiResponse;
import pro.sieben.sat.tv.program.plan.frequency.model.Item;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A utility class for parsing Electronic Programming Guide (EPG) items from a remote API.
 * This class retrieves EPG data from a specified URL and converts it into a list of Item objects.
 */
public class EPGItemsParser {
    private RestTemplate restTemplate = new RestTemplate();

    private static final String EPG_URL_BASE = "https://magellan-api.p7s1.io/epg-broadcast/prosieben.de/graphql?";
    private static final String EPG_VARIABLES = "&variables={variables}";

    private static final String EPG_QUERY = "&query={QUERY}";
    private static final String QUERY = "query EpgQuery($domain: String!," + "$type: EpgType!, $date: DateTime) { site(domain: $domain) { epg(type: $type, date: $date) { items {" + "...fEpgItem } } } } fragment fEpgItem on EpgItem { id title description startTime endTime episode {" + "number } season { number } tvShow { title id } }";
    private static final String EPG_QUERY_HASH = "&queryhash={hashCounter}";

    private int hashCounter = 0;

    /**
     * Parses EPG items from a remote API for the specified date.
     *
     * @param date The date for which EPG items are to be retrieved. If null, the current date and time will be used.
     * @return A list of Item objects representing the EPG items retrieved from the API.
     */
    public List<Item> parseItemsFromEPG(String date) {
        hashCounter++;
        String variables;
        if (date == null) {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date());
            variables = "{\"date\":\"" + date + "\",\"domain\":\"prosieben.de\",\"type\":\"FULL\"}";
        } else {
            variables = "{\"date\":\"" + date + "T00:00:00.000Z\",\"domain\":\"prosieben.de\",\"type\":\"FULL\"}";
        }

        ApiResponse response = restTemplate.getForEntity(EPG_URL_BASE + EPG_VARIABLES + EPG_QUERY + EPG_QUERY_HASH,
                ApiResponse.class, variables, QUERY,hashCounter).getBody();
        if (response != null) {
            return response.getData().getSite().getEpg().getItems();
        }
        return Collections.emptyList();
    }

    // Package-private Setter for testing
    void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
