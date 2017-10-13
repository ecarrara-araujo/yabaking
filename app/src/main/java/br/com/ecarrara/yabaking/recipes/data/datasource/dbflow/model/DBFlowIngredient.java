package br.com.ecarrara.yabaking.recipes.data.datasource.dbflow.model;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.List;

import br.com.ecarrara.yabaking.core.data.DBFlowRecipeDatabase;
import br.com.ecarrara.yabaking.ingredients.domain.entity.Ingredient;
import br.com.ecarrara.yabaking.ingredients.domain.entity.Measure;

@Table(name = DBFlowIngredient.TABLE_NAME, database = DBFlowRecipeDatabase.class)
public class DBFlowIngredient {

    public static final String TABLE_NAME = "ingredient";

    @PrimaryKey(autoincrement = true)
    Integer id;

    @ForeignKey(tableClass = DBFlowRecipe.class)
    Integer recipe;

    @Column
    String name;

    @Column
    Float quantity;

    @Measure.MeasureType
    @Column
    String measure;

    public static class Mapper {

        private Mapper() { /* no construction allowed */ }

        public static Ingredient toDomain(DBFlowIngredient dbFlowIngredient) {
            return Ingredient.builder()
                    .setName(dbFlowIngredient.name)
                    .setMeasure(dbFlowIngredient.measure)
                    .setQuantity(dbFlowIngredient.quantity)
                    .build();
        }

        public static List<Ingredient> toDomain(List<DBFlowIngredient> dbFlowIngredients) {
            return Stream.of(dbFlowIngredients).map(Mapper::toDomain).collect(Collectors.toList());
        }

        public static DBFlowIngredient fromDomain(Ingredient ingredient, Integer recipeId) {
            DBFlowIngredient dbFlowIngredient = new DBFlowIngredient();
            dbFlowIngredient.id = 0;
            dbFlowIngredient.measure = ingredient.measure();
            dbFlowIngredient.name = ingredient.name();
            dbFlowIngredient.quantity = ingredient.quantity();
            dbFlowIngredient.recipe = recipeId;
            return dbFlowIngredient;
        }

        public static List<DBFlowIngredient> fromDomain(List<Ingredient> steps, Integer recipeId) {
            return Stream.of(steps)
                    .map(ingredient -> fromDomain(ingredient, recipeId))
                    .collect(Collectors.toList());
        }

    }

}
