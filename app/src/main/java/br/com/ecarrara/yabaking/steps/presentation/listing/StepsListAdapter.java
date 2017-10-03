package br.com.ecarrara.yabaking.steps.presentation.listing;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ecarrara.yabaking.R;
import br.com.ecarrara.yabaking.steps.presentation.navigating.StepSelectedListener;
import butterknife.BindView;
import butterknife.ButterKnife;

class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.ViewHolder> {

    private List<String> steps;
    private StepSelectedListener stepSelectedListener;

    public StepsListAdapter(@NonNull StepSelectedListener stepSelectedListener) {
        this.stepSelectedListener = stepSelectedListener;
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

        @BindView(R.id.step_list_item_text_view)
        TextView stepTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(ViewHolder.this);
        }

        @Override
        public void onClick(View view) {
            if(stepSelectedListener != null) {
                stepSelectedListener.onStepSelected(getAdapterPosition());
            }
        }
    }

}