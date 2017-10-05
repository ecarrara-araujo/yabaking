package br.com.ecarrara.yabaking.recipes.data.datasource.dbflow.model;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.List;

import br.com.ecarrara.yabaking.core.data.DBFlowRecipeDatabase;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;

@Table(name = DBFlowStep.TABLE_NAME, database = DBFlowRecipeDatabase.class)
public class DBFlowStep {

    public static final String TABLE_NAME = "step";

    @PrimaryKey(autoincrement = true)
    Integer id;

    @ForeignKey(tableClass = DBFlowRecipe.class)
    Integer recipe;

    @Column
    Integer order;

    @Column
    String shortDescription;

    @Column
    String description;

    @Column
    String videoPath;

    @Column
    String thumbnailPath;

    public static class Mapper {

        private Mapper() { /* no construction allowed */ }

        public static Step toDomain(DBFlowStep dbFlowStep) {
            return Step.builder()
                    .setId(dbFlowStep.order)
                    .setShortDescription(dbFlowStep.shortDescription)
                    .setDescription(dbFlowStep.description)
                    .setThumbnailPath(dbFlowStep.thumbnailPath)
                    .setVideoPath(dbFlowStep.videoPath)
                    .build();
        }

        public static List<Step> toDomain(List<DBFlowStep> dbFlowSteps) {
            return Stream.of(dbFlowSteps).map(Mapper::toDomain).collect(Collectors.toList());
        }

        public static DBFlowStep fromDomain(Step step, Integer recipeId) {
            DBFlowStep dbFlowStep = new DBFlowStep();
            dbFlowStep.id = 0;
            dbFlowStep.order = step.id();
            dbFlowStep.shortDescription = step.shortDescription();
            dbFlowStep.description = step.description();
            dbFlowStep.videoPath = step.videoPath();
            dbFlowStep.thumbnailPath = step.thumbnailPath();
            dbFlowStep.recipe = recipeId;
            return dbFlowStep;
        }

        public static List<DBFlowStep> fromDomain(List<Step> steps, Integer recipeId) {
            return Stream.of(steps)
                    .map(step -> fromDomain(step, recipeId))
                    .collect(Collectors.toList());
        }

    }

}
