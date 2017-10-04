package br.com.ecarrara.yabaking.recipes.domain.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

import br.com.ecarrara.yabaking.ingredients.domain.entity.Ingredient;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;

@AutoValue
public abstract class Recipe implements Parcelable {

    public abstract Integer id();

    public abstract String name();

    public abstract String imageUrl();

    public abstract List<Ingredient> ingredients();

    public abstract List<Step> steps();

    public static Builder builder() {
        return new AutoValue_Recipe.Builder()
                .setId(-1)
                .setName("")
                .setImageUrl("")
                .setIngredients(Collections.emptyList())
                .setSteps(Collections.emptyList());
    }

    public boolean isValid() {
        return ( id() >= 0 && !name().isEmpty() && !ingredients().isEmpty() && !steps().isEmpty() );
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(Integer id);

        public abstract Builder setName(String name);

        public abstract Builder setImageUrl(String imageUrl);

        public abstract Builder setIngredients(List<Ingredient> ingredients);

        public abstract Builder setSteps(List<Step> steps);

        public abstract Recipe build();

    }

}
