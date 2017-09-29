package br.com.ecarrara.yabaking.ingredients.domain.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.MeasureType;

@AutoValue
public abstract class Ingredient implements Parcelable {

    public abstract String name();

    public abstract Float quantity();

    @MeasureType
    public abstract String measure();

    public static Builder builder() {
        return new AutoValue_Ingredient.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setName(String name);

        public abstract Builder setQuantity(Float quantity);

        public abstract Builder setMeasure(@MeasureType String measure);

        public abstract Ingredient build();

    }

}
