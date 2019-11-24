package com.jmsilla.gildedrose;

class GildedRose {
    Item[] items;

    private static class ItemNames {
        private static final String AGED_BRIE = "Aged Brie";
        private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
        private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    }
    
    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;
    private static final int BACKSTAGE_DOUBLE_QUALITY_DAYS_THRESHOLD = 10;
    private static final int BACKSTAGE_TRIPLE_QUALITY_DAYS_THRESHOLD = 5;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items)
            updateItem(item);
    }

    private void updateItem(Item item) {
        switch(item.name) {
        case ItemNames.AGED_BRIE:
            decrementItemSellIn(item);
            updateAgedBrieQuality(item);
            break;
        case ItemNames.BACKSTAGE:
            decrementItemSellIn(item);
            updateBackstageQuality(item);
            break;
        case ItemNames.SULFURAS:
            break;
        default:
            decrementItemSellIn(item);
            updateNormalItemQuality(item);
        }
    }

    private void updateAgedBrieQuality(Item item) {
        incrementItemQuality(item);
        
        if (sellInDayHasPassed(item))
            incrementItemQuality(item);
    }
    
    private void updateBackstageQuality(Item item) {
        incrementItemQuality(item);
        
        if (daysToItemSellAreLessThan(item, BACKSTAGE_DOUBLE_QUALITY_DAYS_THRESHOLD))
            incrementItemQuality(item);
        
        if (daysToItemSellAreLessThan(item, BACKSTAGE_TRIPLE_QUALITY_DAYS_THRESHOLD))
            incrementItemQuality(item);
        
        if (sellInDayHasPassed(item))
            removeItemQuality(item);
    }

    private void updateNormalItemQuality(Item item) {
        decrementItemQuality(item);
        
        if (sellInDayHasPassed(item))
            decrementItemQuality(item);
    }
    
    private void incrementItemQuality(Item item) {
        if (item.quality < MAX_QUALITY)
            item.quality = item.quality + 1;
    }

    private void decrementItemSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private boolean sellInDayHasPassed(Item item) {
        return item.sellIn < 0;
    }
    
    private boolean daysToItemSellAreLessThan(Item item, int days) {
        return item.sellIn < days;
    }

    private void removeItemQuality(Item item) {
        item.quality = MIN_QUALITY;
    }

    private void decrementItemQuality(Item item) {
        if (item.quality > MIN_QUALITY)
            item.quality = item.quality - 1;
    }
}