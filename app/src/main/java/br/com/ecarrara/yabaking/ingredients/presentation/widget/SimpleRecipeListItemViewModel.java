package br.com.ecarrara.yabaking.ingredients.presentation.widget;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.auto.value.AutoValue;

import java.util.List;

import br.com.ecarrara.yabaking.recipes.domain.entity.Recipe;

@AutoValue
abstract class SimpleRecipeListItemViewModel {

    public abstract Integer recipeId();

    public abstract String name();

    public static Builder builder() {
        return new AutoValue_SimpleRecipeListItemViewModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setRecipeId(Integer recipeId);

        public abstract Builder setName(String name);

        public abstract SimpleRecipeListItemViewModel build();

    }

    static class Mapper {

        public static SimpleRecipeListItemViewModel fromRecipe(Recipe recipe) {
            return SimpleRecipeListItemViewModel.builder()
                    .setRecipeId(recipe.id())
                    .setName(recipe.name())
                    .build();
        }

        public static List<SimpleRecipeListItemViewModel> fromRecipeList(List<Recipe> recipes) {
            return Stream.of(recipes).map(Mapper::fromRecipe).collect(Collectors.toList());
        }

    }

}
