package br.com.ecarrara.yabaking.steps.presentation.listing;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.core.di.Injector;
import br.com.ecarrara.yabaking.core.utils.RxEventBus;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.ViewHolder> {

    @Inject
    @Named("stepSelectedEventBus")
    RxEventBus<Integer> stepSelectedEventBus;

    private static final int NO_ITEM_SELECTED = -1;

    private Context context;
    private List<String> steps;

    private boolean highlightSelected;
    private int selectedItemPosition;

    private int selectedItemBackgroundColor;
    private int ordinaryItemBackgroundColor;

    public StepsListAdapter(@NonNull Context context, int initialSelectedPosition) {
        this(context);
        this.selectedItemPosition = initialSelectedPosition;
    }

    public StepsListAdapter(@NonNull Context context) {
        Injector.applicationComponent().inject(this);
        this.context = context;
        this.highlightSelected = false;
        this.selectedItemPosition = NO_ITEM_SELECTED;
        selectedItemBackgroundColor = resolveColor(R.color.steps_list_selected_item_background_color);
        ordinaryItemBackgroundColor = resolveColor(R.color.steps_list_ordinary_item_background_color);
    }

    private int resolveColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorId, null);
        } else {
            return context.getResources().getColor(colorId);
        }
    }

    public void setHighlightSelected(boolean highlightSelected) {
        this.highlightSelected = highlightSelected;
    }

    public void setSelectedPosition(int position) {
        if (position < getItemCount()) {
            this.selectedItemPosition = position;
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_list_item, parent, shouldAttachToParentImmediately);
        return new StepsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.stepTextView.setText(steps.get(position));
        if (highlightSelected && (position == selectedItemPosition)) {
            holder.rootView.setBackgroundColor(selectedItemBackgroundColor);
        } else {
            holder.rootView.setBackgroundColor(ordinaryItemBackgroundColor);
        }
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View rootView;

        @BindView(R.id.step_list_item_text_view)
        TextView stepTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView = itemView;
            itemView.setOnClickListener(ViewHolder.this);
        }

        @Override
        public void onClick(View view) {
            int selectedPosition = getAdapterPosition();
            stepSelectedEventBus.publish(selectedPosition);
            StepsListAdapter.this.selectedItemPosition = selectedPosition;
            notifyDataSetChanged();
        }
    }

}
