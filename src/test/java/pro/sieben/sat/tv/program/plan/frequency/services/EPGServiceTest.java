package pro.sieben.sat.tv.program.plan.frequency.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sieben.sat.tv.program.plan.frequency.model.FrequencyResponse;
import pro.sieben.sat.tv.program.plan.frequency.model.Item;
import pro.sieben.sat.tv.program.plan.frequency.model.TvShow;
import pro.sieben.sat.tv.program.plan.frequency.utils.EPGItemsParser;

@ExtendWith(MockitoExtension.class)
public class EPGServiceTest {

    @InjectMocks
    private EPGService epgService;

    @Mock
    private EPGItemsParser epgItemsParser;

    @BeforeEach
    void setUp() {
        List<Item> mockItems = Arrays.asList(
                new Item(new TvShow("Show1", "id1")),
                new Item(new TvShow("Show2", "id2")),
                new Item(new TvShow("Show1", "id1")),
                new Item(new TvShow("Show3", "id3")),
                new Item(new TvShow("Show2", "id2")),
                new Item(new TvShow("Show1", "id1")),
                new Item(new TvShow("Show4", "id4"))
        );

        // Customize parser response
        when(epgItemsParser.parseItemsFromEPG("")).thenReturn(mockItems);
    }

    @Test
    void testGetShowsFrequencies() {
        // Expected result
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Show1", 3);
        expected.put("Show2", 2);
        expected.put("Show3", 1);
        expected.put("Show4", 1);

        // Test
        assertThat(epgService.getShowsFrequencies("")).isEqualTo(expected);
    }

    @Test
    void testGetMostFrequentShows() {
        // Test
        assertThat(epgService.getMostFrequentShows("")).allMatch(frequencyResponse -> frequencyResponse.getTitle().equals("Show1"));
    }

    @Test
    void testGetOrderedShowsByFrequency() {
        // Test
        List<FrequencyResponse> frequencyResponses = epgService.getOrderedShowsByFrequency("");
        assertThat(frequencyResponses.get(0).getTitle()).isEqualTo("Show1");
        assertThat(frequencyResponses.get(1).getTitle()).isEqualTo("Show2");
    }
}
