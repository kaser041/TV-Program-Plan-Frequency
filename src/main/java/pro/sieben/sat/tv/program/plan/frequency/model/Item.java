package pro.sieben.sat.tv.program.plan.frequency.model;

import lombok.*;

import java.util.Date;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    private String id;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private Season season;
    private TvShow tvShow;

    public Item (TvShow tvShow) {
        this.tvShow = tvShow;
    }

    public Item (TvShow tvShow,String title) {
        this.title = title;
        this.tvShow = tvShow;
    }
}
