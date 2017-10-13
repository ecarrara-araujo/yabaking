package br.com.ecarrara.yabaking.ingredients.presentation;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.text.NumberFormat;
import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.ingredients.domain.entity.Ingredient;
import br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.MeasureType;

import static br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.CUP;
import static br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.GRAM;
import static br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.KILOGRAM;
import static br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.NONE;
import static br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.OZ;
import static br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.TABLE_SPOON;
import static br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.TEA_SPOON;
import static br.com.ecarrara.yabaking.ingredients.domain.entity.Measure.UNIT;

public class IngredientFormatter {

    private Context context;
    private NumberFormat numberFormat;

    @Inject
    public IngredientFormatter(Context context) {
        this.context = context;
        this.numberFormat = NumberFormat.getInstance();
    }

    public List<String> formatIngredients(List<Ingredient> inputData) {
        return Stream.of(inputData).map(this::formatIngredient).collect(Collectors.toList());
    }

    public String formatIngredient(Ingredient ingredient) {
        return context.getString(R.string.ingredient_description,
                formatQuantity(ingredient.quantity()),
                formatMeasure(ingredient.measure()),
                ingredient.name());
    }

    private String formatQuantity(Float quantity) {
        return this.numberFormat.format(quantity);
    }

    private String formatMeasure(@MeasureType String measure) {
        switch (measure) {
            case CUP:
                return context.getString(R.string.measure_cup);
            case TABLE_SPOON:
                return context.getString(R.string.measure_table_spoon);
            case TEA_SPOON:
                return context.getString(R.string.measure_tea_spoon);
            case KILOGRAM:
                return context.getString(R.string.measure_kilogram);
            case GRAM:
                return context.getString(R.string.measure_gram);
            case OZ:
                return context.getString(R.string.measure_oz);
            case UNIT:
                return context.getString(R.string.measure_unit);
            case NONE:
            default:
                return NONE;
        }
    }

}
