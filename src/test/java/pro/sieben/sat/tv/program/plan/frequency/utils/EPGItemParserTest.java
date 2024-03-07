package pro.sieben.sat.tv.program.plan.frequency.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pro.sieben.sat.tv.program.plan.frequency.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EPGItemParserTest {

    private EPGItemsParser epgItemsParser;

    @Mock
    private RestTemplate restTemplate;


    @BeforeEach
    void setUp() {
        epgItemsParser = new EPGItemsParser();
        epgItemsParser.setRestTemplate(restTemplate);
        when(restTemplate.getForEntity(any(), any(), any(), any(), any())).thenReturn(new ResponseEntity<>
                (new ApiResponse(new Data(new Site(new Epg(List.of
                        (new Item(new TvShow("Show1", "id1")))))), null),
                        HttpStatusCode.valueOf(200)));
    }

    @Test
    void testparseItemsFromEPG() {
        assertThat(epgItemsParser.parseItemsFromEPG("")).hasSize(1);
        assertThat(epgItemsParser.parseItemsFromEPG("").get(0).getTvShow().getTitle()).isEqualTo("Show1");
    }

    @Test
    void testparseItemsFromEPGDateNull() {
        assertThat(epgItemsParser.parseItemsFromEPG(null)).hasSize(1);
        assertThat(epgItemsParser.parseItemsFromEPG("").get(0).getTvShow().getTitle()).isEqualTo("Show1");
    }

    @Test
     void testparseItemsFromEPGResponseNull() {
        when(restTemplate.getForEntity(any(), any(), any(), any(), any())).thenReturn(new ResponseEntity<>
                (null, HttpStatusCode.valueOf(200)));
        assertThat(epgItemsParser.parseItemsFromEPG("")).isEmpty();
    }
}
