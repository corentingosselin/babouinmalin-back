package fr.cocoraid.babouinmalin.model.barter;

import lombok.Data;

@Data
public class BarterDetail {

    private String url;
    private Barter barter;

    public BarterDetail(Barter barter, String url) {
        this.url = url;
        this.barter = barter;
    }
}
