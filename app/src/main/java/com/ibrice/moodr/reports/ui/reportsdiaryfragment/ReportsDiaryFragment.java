package com.ibrice.moodr.reports.ui.reportsDiaryFragment;

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
import com.ibrice.moodr.databinding.FragmentReportsDiaryBinding;
import com.ibrice.moodr.reports.ReportsActivity;
import com.ibrice.moodr.reports.items.DiariesItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ReportsDiaryFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number"; // where the user is

    private FragmentReportsDiaryBinding binding;
    private PageViewModel model;

    // main function
    public static ReportsDiaryFragment newInstance(int index) {
        ReportsDiaryFragment fragment = new ReportsDiaryFragment();
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

        // instantiate an instance of the database here
        DBManager dbManager = new DBManager(getContext());
        dbManager.open();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // set the viewmodel and its contents
        binding = FragmentReportsDiaryBinding.inflate(inflater, container, false);
        binding.setViewmodelReportDiary(model);
        binding.listReportsDiaries.setAdapter(new ReportsDiaryFragmentRecyclerAdapter());

        return binding.getRoot();
    }

    // get data from corresponding XML page and bind to this view
    @BindingAdapter("diariesList")
    public static void setReportsDiariesListProperties(RecyclerView view, List<DiariesItem> data) {
        if (data != null) {
            ((ReportsDiaryFragmentRecyclerAdapter) Objects.requireNonNull(view.getAdapter())).setData(data);
        }
    }

    class ReportsDiaryFragmentRecyclerAdapter extends RecyclerView.Adapter<ReportsDiaryFragmentRecyclerAdapter.ViewHolder> {
        private List<DiariesItem> localDataset = new ArrayList<>();

        public ReportsDiaryFragmentRecyclerAdapter() {
            // empty
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setData(List<DiariesItem> items) {
            localDataset = items;
            notifyDataSetChanged();
        }

        // get and set the relevant views
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView DiaryID;
            private final TextView DiaryTitle;
            private final TextView DiaryMood;
            private final TextView DiaryDescription;

            public ViewHolder(View view) {
                super(view);

                // Define click listener for ViewHolder's view
                DiaryID = view.findViewById(R.id.txtReportsDiaryID);
                DiaryTitle = view.findViewById(R.id.txtReportsDiaryTitle);
                DiaryMood = view.findViewById(R.id.txtReportsDiaryMood);
                DiaryDescription = view.findViewById(R.id.txtReportsDiaryDescription);
            }

            public TextView getDiaryID() {
                return DiaryID;
            }
            public TextView getDiaryTitle() {
                return DiaryTitle;
            }
            public TextView getDiaryMood() {
                return DiaryMood;
            }
            public TextView getDiaryDescription() {
                return DiaryDescription;
            }
        }

        @NonNull // put layout in a ViewHolder element
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow_reports_diary, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            if (viewHolder.getDiaryID() != null) {
                viewHolder.getDiaryID().setText(Integer.toString(localDataset.get(position).ID));
                viewHolder.getDiaryTitle().setText(localDataset.get(position).Title);
                viewHolder.getDiaryMood().setText(localDataset.get(position).Mood);
                viewHolder.getDiaryDescription().setText(localDataset.get(position).Description);

                // delete items from the list on click via a dialog/alert
                viewHolder.itemView.setOnClickListener(v -> {
                    DBManager dbDeleteManager = new DBManager(getContext());
                    dbDeleteManager.open();
                    DiariesItem currentItem = localDataset.get(position);

                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Delete Diary Entry?");
                    alert.setMessage("Are you sure you want to delete this diary entry?");

                    alert.setPositiveButton("Yes", (dialog, which) -> {
                        dbDeleteManager.deleteDiary(currentItem.ID, false);

                        dbDeleteManager.close();

                        Toast.makeText(v.getContext(), "Diary Entry Deleted", Toast.LENGTH_SHORT).show();

                        Intent reportsIntent = new Intent(v.getContext(), ReportsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(reportsIntent);
                    });

                    alert.setNegativeButton("No", (dialog, which) -> dialog.cancel());

                    alert.show();
                });
            }
        }

        public int getItemCount() {
            if (localDataset != null) {
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