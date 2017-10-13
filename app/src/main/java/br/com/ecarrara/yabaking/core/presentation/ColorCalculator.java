package br.com.ecarrara.yabaking.core.presentation;

public class ColorCalculator {

    private int[] colorIds;

    public ColorCalculator(int[] colorIds) {
        this.colorIds = colorIds;
    }

    public int getColorIdForIndex(int index) {
        final int numberOfAvailableColors = colorIds.length;
        final int indexColorProportion = index / numberOfAvailableColors;
        int colorIndex = index - (numberOfAvailableColors * indexColorProportion);
        return colorIds[colorIndex];
    }

}
