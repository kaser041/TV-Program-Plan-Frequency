package pro.sieben.sat.tv.program.plan.frequency.model;

import lombok.*;

import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Epg {

    private List<Item> items;

}
