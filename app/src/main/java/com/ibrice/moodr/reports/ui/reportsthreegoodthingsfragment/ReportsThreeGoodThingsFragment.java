package com.ibrice.moodr.reports.ui.reportsThreeGoodThingsFragment;

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
import com.ibrice.moodr.databinding.FragmentReportsThreeGoodThingsBinding;
import com.ibrice.moodr.mainscreen.MainActivity;
import com.ibrice.moodr.reports.items.TGTsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ReportsThreeGoodThingsFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private FragmentReportsThreeGoodThingsBinding binding;
    PageViewModel model;

    public static ReportsThreeGoodThingsFragment newInstance(int index) {
        ReportsThreeGoodThingsFragment fragment = new ReportsThreeGoodThingsFragment();
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

        DBManager dbManager = new DBManager(getContext());
        dbManager.open();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentReportsThreeGoodThingsBinding.inflate(getLayoutInflater());
        binding.setViewmodelReportThreeGoodThings(model);
        binding.listReportsThreeGoodThings.setAdapter(new ReportsTGTsFragmentRecyclerAdapter());

        return binding.getRoot();
    }

    @BindingAdapter("tgtsList")
    public static void setReportsTGTsListProperties(RecyclerView view, List<TGTsItem> data){
        if (data != null) {
            ((ReportsTGTsFragmentRecyclerAdapter) Objects.requireNonNull(view.getAdapter())).setData(data);
        }
    }

    class ReportsTGTsFragmentRecyclerAdapter extends RecyclerView.Adapter<ReportsTGTsFragmentRecyclerAdapter.ViewHolder>{
        private List<TGTsItem> localDataset = new ArrayList<>();

        public ReportsTGTsFragmentRecyclerAdapter() {
            //empty
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setData(List<TGTsItem> items) {
            localDataset = items;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView TGTID;
            private final TextView TGT1;
            private final TextView TGT2;
            private final TextView TGT3;

            public ViewHolder(View view) {
                super(view);

                // Define click listener for ViewHolder's view
                TGTID = view.findViewById(R.id.txtReportsTGTsID);
                TGT1 = view.findViewById(R.id.txtReportsTGTs1);
                TGT2 = view.findViewById(R.id.txtReportsTGTs2);
                TGT3 = view.findViewById(R.id.txtReportsTGTs3);
            }

            public TextView getTGTsID() {
                return TGTID;
            }
            public TextView getTGTsTitle() {
                return TGT1;
            }
            public TextView getTGTsDesc() {
                return TGT2;
            }
            public TextView getTGTsTime() {
                return TGT3;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow_reports_three_good_things, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            if (viewHolder.getTGTsID() != null) {
                viewHolder.getTGTsID().setText(Integer.toString(localDataset.get(position).ID));
                viewHolder.getTGTsTitle().setText(localDataset.get(position).Thing1);
                viewHolder.getTGTsDesc().setText(localDataset.get(position).Thing2);
                viewHolder.getTGTsTime().setText(localDataset.get(position).Thing3);

                viewHolder.itemView.setOnClickListener(v -> {
                    DBManager dbDeleteManager = new DBManager(getContext());
                    dbDeleteManager.open();
                    TGTsItem currentItem = localDataset.get(position);

                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Delete TGT?");
                    alert.setMessage("Are you sure you want to delete this Three Good Things entry?");

                    alert.setPositiveButton("Yes", (dialog, which) -> {
                        dbDeleteManager.deleteTGT(currentItem.ID, false);

                        dbDeleteManager.close();

                        Toast.makeText(v.getContext(), "TGT deleted", Toast.LENGTH_SHORT).show();

                        Intent homeIntent = new Intent(v.getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
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
