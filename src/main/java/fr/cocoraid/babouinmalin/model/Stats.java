package fr.cocoraid.babouinmalin.model;

import lombok.Data;

@Data
public class Stats {
    private Integer userAmount;
    private Integer barterAmount;

    public Stats(Integer userAmount, Integer barterAmount) {
        this.userAmount = userAmount;
        this.barterAmount = barterAmount;
    }
}