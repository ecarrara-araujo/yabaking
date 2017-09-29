package br.com.ecarrara.yabaking.ingredients.domain.entity;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Measure {

    public static final String NONE = "";
    public static final String CUP = "CUP";
    public static final String TABLE_SPOON= "TBLSP";
    public static final String TEA_SPOON = "TSP";
    public static final String KILOGRAM= "K";
    public static final String GRAM = "G";
    public static final String OZ = "OZ";
    public static final String UNIT = "UNIT";

    @StringDef({NONE, CUP, TABLE_SPOON, TEA_SPOON, KILOGRAM, GRAM, OZ, UNIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MeasureType { }

}
