package br.com.ecarrara.yabaking.recipes.data.datasource.rest.model;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.ecarrara.yabaking.ingredients.domain.entity.Ingredient;
import br.com.ecarrara.yabaking.ingredients.domain.entity.Measure;

@AutoValue
public abstract class JsonIngredient {

    @SerializedName("quantity")
    public abstract Float quantity();

    @SerializedName("measure")
    @Measure.MeasureType
    public abstract String measure();

    @SerializedName("ingredient")
    public abstract String name();

    public static TypeAdapter<JsonIngredient> typeAdapter(Gson gson) {
        return new AutoValue_JsonIngredient.GsonTypeAdapter(gson);
    }

    public static final class Mapper {

        private Mapper() { /* no construction allowed */ }

        public static Ingredient toDomain(JsonIngredient jsonIngredient) {
            return Ingredient.builder()
                    .setName(jsonIngredient.name())
                    .setMeasure(jsonIngredient.measure())
                    .setQuantity(jsonIngredient.quantity())
                    .build();
        }

        public static List<Ingredient> toDomain(List<JsonIngredient> jsonIngredients) {
            return Stream.of(jsonIngredients).map(Mapper::toDomain).collect(Collectors.toList());
        }

    }

}
