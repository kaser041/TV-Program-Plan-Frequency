package pro.sieben.sat.tv.program.plan.frequency.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusResponse {
    private String title;
    private Status status;
}
