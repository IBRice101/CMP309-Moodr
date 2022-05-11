package com.ibrice.moodr.habits;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibrice.moodr.R;
import com.ibrice.moodr.database.DBManager;
import com.ibrice.moodr.databinding.ActivityViewHabitsBinding;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHabitsActivity extends AppCompatActivity {

    ActivityViewHabitsBinding binding;
    HabitsActivityModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBManager db = new DBManager(ViewHabitsActivity.this);
        db.open();

        binding = ActivityViewHabitsBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(this).get(HabitsActivityModel.class);
        binding.setViewmodelViewHabits(model);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("View Your Habits");

        FloatingActionButton fab = binding.fabAddHabit;
        fab.setOnClickListener(view ->  {
            Intent fabIntent = new Intent(this, CreateHabitsActivity.class);
            fabIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(fabIntent);
        });

        binding.listHabits.setAdapter(new HabitsActivityRecyclerAdapter());

        View view = binding.getRoot();
        setContentView(view);

    }

    @BindingAdapter("data")
    public static void setTestListProperties(RecyclerView view, List<HabitsItem> data){
        ((HabitsActivityRecyclerAdapter) Objects.requireNonNull(view.getAdapter())).setData(data);
    }

    static class HabitsActivityRecyclerAdapter extends RecyclerView.Adapter<HabitsActivityRecyclerAdapter.ViewHolder>{
        private List<HabitsItem> localDataset;

        public HabitsActivityRecyclerAdapter() {
            //empty
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setData(List<HabitsItem> items) {
            localDataset = items;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView habitsID;
            private final TextView habitsTitle;
            private final TextView habitsDesc;
            private final TextView habitsTime;

            public ViewHolder(View view) {
                super(view);

                // Define click listener for ViewHolder's view
                habitsID = view.findViewById(R.id.txtHabitsID);
                habitsTitle = view.findViewById(R.id.txtHabitsTitle);
                habitsDesc = view.findViewById(R.id.txtHabitsDescription);
                habitsTime = view.findViewById(R.id.txtHabitsTime);
            }

            public TextView getHabitsID() {
                return habitsID;
            }
            public TextView getHabitsTitle() {
                return habitsTitle;
            }
            public TextView getHabitsDesc() {
                return habitsDesc;
            }
            public TextView getHabitsTime() {
                return habitsTime;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_habits_text, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.getHabitsID().setText(Integer.toString(localDataset.get(position).ID));
            viewHolder.getHabitsTitle().setText(localDataset.get(position).Title);
            viewHolder.getHabitsDesc().setText(localDataset.get(position).Description);
            viewHolder.getHabitsTime().setText(localDataset.get(position).Time);
        }

        @Override
        public int getItemCount() {
            return localDataset.size();
        }
    }
}