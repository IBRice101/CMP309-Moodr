package com.ibrice.moodr.reports.ui.reportsHabitsFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ibrice.moodr.R;
import com.ibrice.moodr.database.DBManager;
import com.ibrice.moodr.databinding.FragmentReportsHabitsBinding;
import com.ibrice.moodr.reports.ReportsActivity;
import com.ibrice.moodr.reports.items.HabitsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ReportsHabitsFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private FragmentReportsHabitsBinding binding;
    PageViewModel model;

    public static ReportsHabitsFragment newInstance(int index) {
        ReportsHabitsFragment fragment = new ReportsHabitsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // generated stuff
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        model.setIndex(index);

        DBManager db = new DBManager(getContext());
        db.open();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentReportsHabitsBinding.inflate(getLayoutInflater());
        binding.setViewmodelReportHabits(model);
        binding.listReportsHabits.setAdapter(new ReportsHabitsFragmentRecyclerAdapter());

        return binding.getRoot();
    }

    @BindingAdapter("habitsList")
    public static void setReportsHabitsListProperties(RecyclerView view, List<HabitsItem> data){
        if (data != null) {
            ((ReportsHabitsFragmentRecyclerAdapter) Objects.requireNonNull(view.getAdapter())).setData(data);
        }
    }

    class ReportsHabitsFragmentRecyclerAdapter extends RecyclerView.Adapter<ReportsHabitsFragmentRecyclerAdapter.ViewHolder>{
        private List<HabitsItem> localDataset = new ArrayList<>();

        public ReportsHabitsFragmentRecyclerAdapter() {
            //empty
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setData(List<HabitsItem> items) {
            localDataset = items;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView habitsID;
            private final TextView habitsTitle;
            private final TextView habitsDesc;
            private final TextView habitsTime;

            public ViewHolder(View view) {
                super(view);

                // Define click listener for ViewHolder's view
                habitsID = view.findViewById(R.id.txtReportsHabitsID);
                habitsTitle = view.findViewById(R.id.txtReportsHabitsTitle);
                habitsDesc = view.findViewById(R.id.txtReportsHabitsDescription);
                habitsTime = view.findViewById(R.id.txtReportsHabitsTime);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow_reports_habits, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            if (viewHolder.getHabitsID() != null) {
                viewHolder.getHabitsID().setText(Integer.toString(localDataset.get(position).ID));
                viewHolder.getHabitsTitle().setText(localDataset.get(position).Title);
                viewHolder.getHabitsDesc().setText(localDataset.get(position).Description);
                viewHolder.getHabitsTime().setText(localDataset.get(position).Time);

                viewHolder.itemView.setOnClickListener(v -> {
                    DBManager db = new DBManager(getContext());
                    db.open();
                    HabitsItem currentItem = localDataset.get(position);

                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Delete Habit?");
                    alert.setMessage("Are you sure you want to delete this habit?");

                    alert.setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteHabits(currentItem.ID, false);

                        Toast.makeText(v.getContext(), "Habit deleted", Toast.LENGTH_SHORT).show();

                        Intent reportsIntent = new Intent(v.getContext(), ReportsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(reportsIntent);
                    });

                    alert.setNegativeButton("No", (dialog, which) -> dialog.cancel());

                    alert.show();
                });
            }
        }

        @Override
        public int getItemCount() {
            if (localDataset!=null) {
                return localDataset.size();
            } else {
                return 0;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
