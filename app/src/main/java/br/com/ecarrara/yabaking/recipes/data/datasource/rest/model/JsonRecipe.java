package br.com.ecarrara.yabaking.recipes.data.datasource.rest.model;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import br.com.ecarrara.yabaking.core.utils.MediaUrlUtils;
import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;

@AutoValue
public abstract class JsonRecipe {

    @SerializedName("id")
    public abstract Integer id();

    @SerializedName("name")
    public abstract String name();

    @SerializedName("servings")
    public abstract Integer servings();

    @SerializedName("image")
    public abstract String image();

    @SerializedName("ingredients")
    public abstract List<JsonIngredient> ingredients();

    @SerializedName("steps")
    public abstract List<JsonStep> steps();

    public static TypeAdapter<JsonRecipe> typeAdapter(Gson gson) {
        return new AutoValue_JsonRecipe.GsonTypeAdapter(gson)
                .setDefaultIngredients(Collections.emptyList())
                .setDefaultSteps(Collections.emptyList());
    }

    public static final class Mapper {

        private Mapper() { /* no construction allowed */ }

        public static Recipe toDomain(JsonRecipe jsonRecipe) {
            return Recipe.builder()
                    .setId(jsonRecipe.id())
                    .setImageUrl(MediaUrlUtils.filterValidImageUrl(jsonRecipe.image()))
                    .setName(jsonRecipe.name())
                    .setIngredients(JsonIngredient.Mapper.toDomain(jsonRecipe.ingredients()))
                    .setSteps(JsonStep.Mapper.toDomain(jsonRecipe.steps()))
                    .build();
        }

        public static List<Recipe> toDomain(List<JsonRecipe> jsonRecipes) {
            return Stream.of(jsonRecipes).map(Mapper::toDomain).collect(Collectors.toList());
        }

    }

}
