package br.com.ecarrara.yabaking.recipes.presentation.listing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.core.presentation.ColorCalculator;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.ViewHolder> {

    private Context context;
    private List<RecipeListItemViewModel> recipeListItemViewModels;
    private RecipeSelectedListener recipeSelectedListener;
    private ColorCalculator colorCalculator;

    public RecipesListAdapter(@NonNull Context context, @NonNull RecipeSelectedListener recipeSelectedListener) {
        this.context = context;
        this.colorCalculator = new ColorCalculator(context.getResources().getIntArray(R.array.recipes_list_colors));
        this.recipeSelectedListener = recipeSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_list_item, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RecipeListItemViewModel recipeListItemViewModel =
                recipeListItemViewModels.get(position);
        holder.recipeNameTextView.setText(recipeListItemViewModel.name());
        if (!recipeListItemViewModel.imageUrl().isEmpty()) {
            Picasso.with(context)
                    .load(recipeListItemViewModel.imageUrl())
                    .fit()
                    .placeholder(getBackgroundColorForItem(position))
                    .into(holder.recipeImageView);
        } else {
            holder.recipeImageView.setBackgroundColor(getBackgroundColorForItem(position));
        }
    }

    private int getBackgroundColorForItem(int itemPosition) {
        return colorCalculator.getColorIdForIndex(itemPosition);
    }

    @Override
    public int getItemCount() {
        return recipeListItemViewModels == null ? 0 : recipeListItemViewModels.size();
    }

    public void setRecipeListItemViewModels(List<RecipeListItemViewModel> recipeListItemViewModels) {
        this.recipeListItemViewModels = recipeListItemViewModels;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipes_list_item_image_view)
        ImageView recipeImageView;

        @BindView(R.id.recipes_list_item_name_text_view)
        TextView recipeNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            RecipesListAdapter.this.recipeSelectedListener.onRecipeSelected(
                    recipeListItemViewModels.get(getAdapterPosition()).recipeId()
            );
        }
    }

}
