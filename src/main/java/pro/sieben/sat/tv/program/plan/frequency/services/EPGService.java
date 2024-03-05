package pro.sieben.sat.tv.program.plan.frequency.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pro.sieben.sat.tv.program.plan.frequency.model.FrequencyResponse;
import pro.sieben.sat.tv.program.plan.frequency.model.Item;
import pro.sieben.sat.tv.program.plan.frequency.model.Status;
import pro.sieben.sat.tv.program.plan.frequency.model.StatusResponse;
import pro.sieben.sat.tv.program.plan.frequency.utils.EPGItemsParser;

import java.util.*;

@Service
public class EPGService {

    private EPGItemsParser epgItemsParser = new EPGItemsParser();

    // A test data map containing series with their status (RUNNING, ENDED).
    private Map<String, Status> seriesStatusMap = new HashMap<>();

    @PostConstruct
    public void setUp() {
        seriesStatusMap.put("The Big Bang Theory",Status.ENDED);
        seriesStatusMap.put("Galileo",Status.RUNNING);
        seriesStatusMap.put("Die Simpsons",Status.RUNNING);
        seriesStatusMap.put("Modern Family",Status.ENDED);
        seriesStatusMap.put("Scrubs - Die Anfänger",Status.ENDED);
        seriesStatusMap.put("Die Goldbergs",Status.ENDED);
        seriesStatusMap.put("Will & Grace",Status.ENDED);
    }

    /**
     * Retrieves the frequencies of TV shows from parsed EPG items.
     *
     * @return A map containing TV show titles as keys and their frequencies as values.
     */
    Map<String, Integer> getShowsFrequencies(String date) {
        List<Item> items = epgItemsParser.parseItemsFromEPG(date);

        Map<String, Integer> showFrequencyMap = new HashMap<>();

        for (Item item : items) {
            String showTitle = item.getTvShow().getTitle();
            if (showFrequencyMap.containsKey(showTitle)) {
                showFrequencyMap.put(showTitle, showFrequencyMap.get(showTitle) + 1);
            } else {
                showFrequencyMap.put(showTitle, 1);
            }
        }

        return showFrequencyMap;
    }

    /**
     * Retrieves the most frequent TV show(s) along with their frequencies.
     *
     * @return A list of FrequencyResponse objects representing the most frequent TV show(s).
     */
    public List<FrequencyResponse> getMostFrequentShows(String date) {
        return getFrequencyResponses(getShowsFrequencies(date), new ArrayList<>());
    }

    /**
     * Retrieves the ordered list of TV shows by frequency for a given date.
     *
     * @param date The date for which the TV shows are to be retrieved.
     * @return A list of FrequencyResponse objects representing the ordered TV shows by frequency.
     */
    public List<FrequencyResponse> getOrderedShowsByFrequency(String date) {
        return convertMapToOrderedFrequencyResponsesList(getShowsFrequencies(date));
    }

    /**
     * Retrieves the ordered list of TV shows by frequency for a given date and type.
     *
     * @param date The date for which the TV shows are to be retrieved.
     * @param type The type of TV shows to retrieve. Possible values are "TvShow", "Series", and "Movie".
     * @return A list of FrequencyResponse objects representing the ordered TV shows by frequency for the specified type.
     */
    public List<FrequencyResponse> getOrderedShowsByFrequencyByType(String date, String type) {
        Map<String, Integer> showFrequencyMap = getShowsMapByType(date, type);
        return convertMapToOrderedFrequencyResponsesList(showFrequencyMap);
    }

    /**
     * Converts a map of TV show frequencies to an ordered list of FrequencyResponse objects.
     *
     * @param showFrequencyMap A map containing TV show titles as keys and their frequencies as values.
     * @return An ordered list of FrequencyResponse objects representing the TV shows by frequency.
     */
    private List<FrequencyResponse> convertMapToOrderedFrequencyResponsesList(Map<String, Integer> showFrequencyMap) {
        List<FrequencyResponse> frequencyResponses = new ArrayList<>();

        // Convert the HashMap to a list of FrequencyResponse objects
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(showFrequencyMap.entrySet());

        // Sort the list based on frequency in descending order
        Collections.sort(entryList, (a, b) -> b.getValue().compareTo(a.getValue()));

        // Convert the sorted list to a list of ShowFrequency objects
        for (Map.Entry<String, Integer> entry : entryList) {
            frequencyResponses.add(new FrequencyResponse(entry.getKey(), entry.getValue()));
        }

        return frequencyResponses;
    }

    /**
     * Retrieves a map of TV shows frequencies by type for a given date.
     *
     * @param date The date for which the TV shows frequencies are to be retrieved.
     * @param type The type of TV shows to retrieve. Possible values are "TvShow", "Series", and "Movie".
     * @return A map containing TV show titles as keys and their frequencies as values for the specified type.
     */
    private Map<String, Integer> getShowsMapByType(String date, String type) {
        List<Item> items = epgItemsParser.parseItemsFromEPG(date);

        Map<String, Integer> showFrequencyMap = new HashMap<>();

        for (Item item : items) {

            switch (type) {
                case "TvShow":
                    if (item.getTitle() == null && item.getTvShow().getId() != null)
                        addShowToMap(item, showFrequencyMap);
                    break;
                case "Series":
                    if (item.getTitle() != null && item.getTvShow().getId() != null)
                        addShowToMap(item, showFrequencyMap);
                    break;
                case "Movie":
                    if (item.getTitle() == null && item.getTvShow().getId() == null)
                        addShowToMap(item, showFrequencyMap);
                    break;
                default:
                    break;
            }
        }

        return showFrequencyMap;
    }

    /**
     * Retrieves the most frequent TV shows of a specific type airing on a given date.
     *
     * @param date The date for which the TV shows are to be retrieved.
     * @param type The type of TV shows to retrieve. Possible values are "TvShow", "Series", and "Movie".
     * @return A list of FrequencyResponse objects representing the most frequent TV shows of the specified type.
     */
    public List<FrequencyResponse> getMostFrequentShowsByShowType(String date, String type) {
        Map<String, Integer> showFrequencyMap = getShowsMapByType(date, type);

        List<FrequencyResponse> frequencyResponses = new ArrayList<>();
        return getFrequencyResponses(showFrequencyMap, frequencyResponses);
    }

    /**
     * Calculates the frequency responses for TV shows based on the provided show frequency map.
     *
     * @param showFrequencyMap   A map containing TV show titles as keys and their frequencies as values.
     * @param frequencyResponses A list of FrequencyResponse objects to which the calculated responses will be added.
     * @return The updated list of FrequencyResponse objects containing the most frequent TV show(s) and their frequencies.
     */
    private List<FrequencyResponse> getFrequencyResponses(Map<String, Integer> showFrequencyMap, List<FrequencyResponse> frequencyResponses) {
        List<String> mostfrequentShows = new ArrayList<>();
        int maxFrequency = 0;

        for (Map.Entry<String, Integer> entry : showFrequencyMap.entrySet()) {
            String show = entry.getKey();
            int frequency = entry.getValue();

            if (showFrequencyMap.get(show) > maxFrequency) {
                // Update maxFrequency
                maxFrequency = frequency;
                // Clear the previous most frequent shows list
                mostfrequentShows.clear();
                // Add the current show to the list
                mostfrequentShows.add(show);
            } else if (frequency == maxFrequency) {
                // If the frequency is equal to maxFrequency, add the show to the list
                mostfrequentShows.add(show);
            }
        }

        for (String mostFrequentShow : mostfrequentShows) {
            frequencyResponses.add(new FrequencyResponse(mostFrequentShow, maxFrequency));
        }

        return frequencyResponses;
    }

    /**
     * Adds the TV show from the given item to the show frequency map.
     *
     * @param item             The item containing information about the TV show.
     * @param showFrequencyMap A map containing TV show titles as keys and their frequencies as values.
     */
    private void addShowToMap(Item item, Map<String, Integer> showFrequencyMap) {
        String showTitle = item.getTvShow().getTitle();
        if (showFrequencyMap.containsKey(showTitle)) {
            showFrequencyMap.put(showTitle, showFrequencyMap.get(showTitle) + 1);
        } else {
            showFrequencyMap.put(showTitle, 1);
        }
    }

    /**
     * Retrieves a list of running series based on the provided date.
     *
     * @param date The date for which the running series are to be retrieved.
     * @return A list of StatusResponse objects representing the running series.
     */
    public List<StatusResponse> getSeriesByStatus(String date) {
        // Retrieves a map of series titles and their respective statuses based on the provided date
        Map<String, Integer> seriesMap = getShowsMapByType(date,"Series");

        // Initializes a list to store StatusResponse objects
        List<StatusResponse> statusResponses = new ArrayList<>();

        // Iterates through the seriesMap to create StatusResponse objects and add them to the list
        for (String serie: seriesMap.keySet()) {
            statusResponses.add(new StatusResponse(serie,seriesStatusMap.get(serie)));
        }

        // Returns the list of StatusResponse objects representing the running series
        return statusResponses;
    }
}
