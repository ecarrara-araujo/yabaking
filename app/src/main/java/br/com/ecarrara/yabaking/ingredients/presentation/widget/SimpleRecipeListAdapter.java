package br.com.ecarrara.yabaking.ingredients.presentation.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ecarrara.yabaking.R;
import butterknife.BindView;
import butterknife.ButterKnife;

class SimpleRecipeListAdapter extends RecyclerView.Adapter<SimpleRecipeListAdapter.ViewHolder> {

    private List<SimpleRecipeListItemViewModel> recipesData;
    private RecipeSelectedForWidgetListener recipeSelectedForWidgetListener;

    public SimpleRecipeListAdapter(RecipeSelectedForWidgetListener recipeSelectedForWidgetListener) {
        this.recipeSelectedForWidgetListener = recipeSelectedForWidgetListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_widget_configuration_recipe_list_item,
                        parent, shouldAttachToParentImmediately);
        return new SimpleRecipeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.recipeNameTextView.setText(recipesData.get(position).name());
    }

    @Override
    public int getItemCount() {
        return recipesData == null ? 0 : recipesData.size();
    }

    public void setRecipes(List<SimpleRecipeListItemViewModel> recipesData) {
        this.recipesData = recipesData;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ingredient_widget_configuration_list_item_recipe_name_text_view)
        TextView recipeNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(ViewHolder.this);
        }

        @Override
        public void onClick(View view) {
            if (recipeSelectedForWidgetListener != null) {
                recipeSelectedForWidgetListener.onRecipeSelectedForWidget(
                        recipesData.get(getAdapterPosition()).recipeId()
                );
            }
        }
    }

}
