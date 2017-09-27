package br.com.ecarrara.yabaking.recipes.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Recipe {

    public abstract Integer id();

    public abstract String name();

    public abstract String imageUrl();

    public static Builder builder() {
        return new AutoValue_Recipe.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(Integer id);

        public abstract Builder setName(String name);

        public abstract Builder setImageUrl(String imageUrl);

        public abstract Recipe build();

    }

}
