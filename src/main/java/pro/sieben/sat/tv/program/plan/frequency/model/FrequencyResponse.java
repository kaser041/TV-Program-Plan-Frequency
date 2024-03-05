package pro.sieben.sat.tv.program.plan.frequency.model;

import lombok.*;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrequencyResponse {
    private String title;
    private int frequency;
}
