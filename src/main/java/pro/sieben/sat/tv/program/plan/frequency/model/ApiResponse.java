package pro.sieben.sat.tv.program.plan.frequency.model;

import lombok.*;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private Data data;
    private Object extensions;

}
