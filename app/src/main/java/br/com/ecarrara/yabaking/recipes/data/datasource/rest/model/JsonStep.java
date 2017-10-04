package br.com.ecarrara.yabaking.recipes.data.datasource.rest.model;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.ecarrara.yabaking.core.utils.MediaUrlUtils;
import br.com.ecarrara.yabaking.steps.domain.entity.Step;

@AutoValue
public abstract class JsonStep {

    @SerializedName("id")
    public abstract Integer id();

    @SerializedName("shortDescription")
    public abstract String shortDescription();

    @SerializedName("description")
    public abstract String description();

    @SerializedName("videoURL")
    public abstract String videoUrl();

    @SerializedName("thumbnailURL")
    public abstract String thumbnailUrl();

    public static TypeAdapter<JsonStep> typeAdapter(Gson gson) {
        return new AutoValue_JsonStep.GsonTypeAdapter(gson);
    }

    public static final class Mapper {

        private Mapper() { /* no construction allowed */ }

        public static Step toDomain(JsonStep jsonStep) {
            return Step.builder()
                    .setId(jsonStep.id())
                    .setShortDescription(jsonStep.shortDescription())
                    .setDescription(jsonStep.description())
                    .setThumbnailPath(MediaUrlUtils.filterValidImageUrl(jsonStep.thumbnailUrl()))
                    .setVideoPath(MediaUrlUtils.filterValidVideoUrl(jsonStep.videoUrl()))
                    .build();
        }

        public static List<Step> toDomain(List<JsonStep> jsonSteps) {
            return Stream.of(jsonSteps).map(Mapper::toDomain).collect(Collectors.toList());
        }

    }

}
