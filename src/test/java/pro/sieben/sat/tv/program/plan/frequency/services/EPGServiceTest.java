package pro.sieben.sat.tv.program.plan.frequency.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sieben.sat.tv.program.plan.frequency.model.*;
import pro.sieben.sat.tv.program.plan.frequency.utils.EPGItemsParser;

@ExtendWith(MockitoExtension.class)
class EPGServiceTest {

    @InjectMocks
    private EPGService epgService;

    @Mock
    private EPGItemsParser epgItemsParser;

    @BeforeEach
    void setUp() {
        List<Item> mockItems = Arrays.asList(
                new Item(new TvShow("Show1", "id1"),"Ep1"),
                new Item(new TvShow("Show2", "id2")),
                new Item(new TvShow("Show1", "id1"),"Ep1"),
                new Item(new TvShow("Show3", null)),
                new Item(new TvShow("Show2", "id2")),
                new Item(new TvShow("Show1", "id1"),"Ep1"),
                new Item(new TvShow("Show4", "id4"))
        );
        epgService.setUp();
        when(epgItemsParser.parseItemsFromEPG(any())).thenReturn(mockItems);
    }

    @Test
    void testGetShowsFrequencies() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Show1", 3);
        expected.put("Show2", 2);
        expected.put("Show3", 1);
        expected.put("Show4", 1);

        assertThat(epgService.getShowsFrequencies("")).isEqualTo(expected);
    }

    @Test
    void testGetShowsFrequenciesDateNull() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Show1", 3);
        expected.put("Show2", 2);
        expected.put("Show3", 1);
        expected.put("Show4", 1);

        assertThat(epgService.getShowsFrequencies(null)).isEqualTo(expected);
    }

    @Test
    void testGetMostFrequentShows() {
        assertThat(epgService.getMostFrequentShows("")).allMatch(frequencyResponse -> frequencyResponse.getTitle().equals("Show1"));
    }

    @Test
    void testGetOrderedShowsByFrequency() {
        List<FrequencyResponse> frequencyResponses = epgService.getOrderedShowsByFrequency("");
        assertThat(frequencyResponses.get(0).getTitle()).isEqualTo("Show1");
        assertThat(frequencyResponses.get(1).getTitle()).isEqualTo("Show2");
    }

    @Test
    void testGetOrderedShowsByFrequencyByTypSeries() {
        List<FrequencyResponse> frequencyResponses = epgService.getOrderedShowsByFrequencyByType("", ShowType.SERIES);
        assertThat(frequencyResponses).hasSize(1);
        assertThat(frequencyResponses.get(0).getTitle()).isEqualTo("Show1");
    }

    @Test
    void testGetOrderedShowsByFrequencyByTypeTvShow() {
        List<FrequencyResponse> frequencyResponses = epgService.getOrderedShowsByFrequencyByType("", ShowType.TVSHOW);
        assertThat(frequencyResponses).hasSize(2);
        assertThat(frequencyResponses.get(0).getTitle()).isEqualTo("Show2");
        assertThat(frequencyResponses.get(1).getTitle()).isEqualTo("Show4");
    }

    @Test
    void testGetOrderedShowsByFrequencyByTypeMovie() {
        List<FrequencyResponse> frequencyResponses = epgService.getOrderedShowsByFrequencyByType("", ShowType.MOVIE);
        assertThat(frequencyResponses).hasSize(1);
        assertThat(frequencyResponses.get(0).getTitle()).isEqualTo("Show3");
    }

    @Test
    void testGetMostFrequentShowsByShowTypeSeries() {
        List<FrequencyResponse> frequencyResponses = epgService.getMostFrequentShowsByShowType("", ShowType.SERIES);
        assertThat(frequencyResponses).hasSize(1);
        assertThat(frequencyResponses.get(0).getTitle()).isEqualTo("Show1");
    }

    @Test
    void testGetMostFrequentShowsByShowTypeTvShow() {
        List<FrequencyResponse> frequencyResponses = epgService.getMostFrequentShowsByShowType("", ShowType.TVSHOW);
        assertThat(frequencyResponses).hasSize(1);
        assertThat(frequencyResponses.get(0).getTitle()).isEqualTo("Show2");
    }

    @Test
    void testGetMostFrequentShowsByShowTypeMovie() {
        List<FrequencyResponse> frequencyResponses = epgService.getMostFrequentShowsByShowType("", ShowType.MOVIE);
        assertThat(frequencyResponses).hasSize(1);
        assertThat(frequencyResponses.get(0).getTitle()).isEqualTo("Show3");
    }

    @Test
    void testGetSeriesByStatus() {
        List<StatusResponse> frequencyResponses = epgService.getSeriesByStatus("");
        assertThat(frequencyResponses).hasSize(1);
        assertThat(frequencyResponses.get(0).getTitle()).isEqualTo("Show1");
    }
}
