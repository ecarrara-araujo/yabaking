package br.com.ecarrara.yabaking.steps.domain.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Step implements Parcelable {

    public abstract Integer id();

    public abstract String shortDescription();

    public abstract String description();

    public abstract String videoPath();

    public abstract String thumbnailPath();

    public static Builder builder() {
        return new AutoValue_Step.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(Integer id);

        public abstract Builder setShortDescription(String shortDescription);

        public abstract Builder setDescription(String description);

        public abstract Builder setVideoPath(String videoPath);

        public abstract Builder setThumbnailPath(String thumbnailPath);

        public abstract Step build();

    }

}
