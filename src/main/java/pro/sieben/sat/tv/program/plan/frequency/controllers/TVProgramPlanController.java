package pro.sieben.sat.tv.program.plan.frequency.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sieben.sat.tv.program.plan.frequency.model.FrequencyResponse;
import pro.sieben.sat.tv.program.plan.frequency.model.ShowType;
import pro.sieben.sat.tv.program.plan.frequency.model.StatusResponse;
import pro.sieben.sat.tv.program.plan.frequency.services.EPGService;

import java.util.List;

/**
 * Controller class for handling TV program plan frequency-related HTTP requests.
 * This class defines endpoints for retrieving most frequent TV shows and frequency-ordered TV shows.
 */
@RestController
@RequestMapping(value = "/api")
public class TVProgramPlanController {


    private final EPGService epgService;

    @Autowired
    public TVProgramPlanController(EPGService epgService) {
        this.epgService = epgService;
    }

    /**
     * Retrieves the most frequent TV shows airing on the specified date.
     *
     * @param date The date for which the most frequent TV shows are to be retrieved.
     * @return A list of FrequencyResponse objects representing the most frequent TV shows.
     */
    @GetMapping("/mostFrequantShows/{date}")
    public List<FrequencyResponse> getMostFrequentShows( @PathVariable String date) {
        return epgService.getMostFrequentShows(date);
    }

    /**
     * Retrieves the most frequent TV shows airing without specifying the date.
     *
     * @return A list of FrequencyResponse objects representing the most frequent TV shows.
     */
    @GetMapping("/mostFrequantShows")
    public List<FrequencyResponse> getMostFrequentShowsWithoutDate() {
        return epgService.getMostFrequentShows(null);
    }

    /**
     * Retrieves the TV shows ordered by frequency for the specified date.
     *
     * @param date The date for which the TV shows are to be retrieved.
     * @return A list of FrequencyResponse objects representing the TV shows ordered by frequency.
     */
    @GetMapping("/frequencyOrderedShows/{date}")
    public List<FrequencyResponse> getFrequencyOrderedShows(@PathVariable String date) {
        return epgService.getOrderedShowsByFrequency(date);
    }

    /**
     * Retrieves the TV shows ordered by frequency without specifying the date.
     *
     * @return A list of FrequencyResponse objects representing the TV shows ordered by frequency.
     */
    @GetMapping("/frequencyOrderedShows")
    public List<FrequencyResponse> getFrequencyOrderedShows() {
        return epgService.getOrderedShowsByFrequency(null);
    }

    /**
     * Retrieves the most frequent TV shows of a specific type airing on the specified date.
     *
     * @param date The date for which the most frequent TV shows are to be retrieved.
     * @param type The type of TV shows to retrieve. Possible values are "TvShow", "Series", and "Movie".
     * @return A list of FrequencyResponse objects representing the most frequent TV shows of the specified type.
     */
    @GetMapping("/mostFrequantShowsOrderedShowsByType/{date}/{type}")
    public List<FrequencyResponse> getMostFrequentShowsByShowType(@PathVariable String date, @PathVariable ShowType type) {
        return epgService.getMostFrequentShowsByShowType(date,type);
    }

    /**
     * Retrieves the most frequent TV shows of a specific type without specifying the date.
     *
     * @param type The type of TV shows to retrieve. Possible values are "TvShow", "Series", and "Movie".
     * @return A list of FrequencyResponse objects representing the most frequent TV shows of the specified type.
     */
    @GetMapping("/mostFrequantShowsOrderedShowsByType/{type}")
    public List<FrequencyResponse> getMostFrequentShowsByShowType(@PathVariable ShowType type) {
        return epgService.getMostFrequentShowsByShowType(null,type);
    }

    /**
     * Retrieves the TV shows of a specific type ordered by frequency for the specified date.
     *
     * @param date The date for which the TV shows are to be retrieved.
     * @param type The type of TV shows to retrieve. Possible values are "TvShow", "Series", and "Movie".
     * @return A list of FrequencyResponse objects representing the TV shows ordered by frequency for the specified type.
     */
    @GetMapping("/frequencyOrderedShowsByType/{date}/{type}")
    public List<FrequencyResponse> getFrequencyOrderedShowsByType(@PathVariable String date, @PathVariable ShowType type) {
        return epgService.getOrderedShowsByFrequencyByType(date,type);
    }

    /**
     * Retrieves the TV shows of a specific type ordered by frequency without specifying the date.
     *
     * @param type The type of TV shows to retrieve. Possible values are "TvShow", "Series", and "Movie".
     * @return A list of FrequencyResponse objects representing the TV shows ordered by frequency for the specified type.
     */
    @GetMapping("/frequencyOrderedShowsByType/{type}")
    public List<FrequencyResponse> getFrequencyOrderedShowsByTypeWithoutDate(@PathVariable ShowType type) {
        return epgService.getOrderedShowsByFrequencyByType(null,type);
    }

    /**
     * Retrieves a list of series with their statuses based on the provided date.
     *
     * @param date The date for which series statuses are to be retrieved.
     * @return A list of StatusResponse objects representing series and their statuses.
     */
    @GetMapping("/SeriesByStatus/{date}")
    public List<StatusResponse> getSeriesByStatus(@PathVariable String date) {
        return epgService.getSeriesByStatus(date);
    }

    /**
     * Retrieves a list of series with their statuses for the current date.
     * If no date is provided, the method retrieves series statuses for the current date.
     *
     * @return A list of StatusResponse objects representing series and their statuses.
     */
    @GetMapping("/SeriesByStatus/")
    public List<StatusResponse> getSeriesByStatus() {
        return epgService.getSeriesByStatus(null);
    }

}
