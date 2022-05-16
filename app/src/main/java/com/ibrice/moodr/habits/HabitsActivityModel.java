package com.ibrice.moodr.habits;

import android.app.Application;
import android.database.Cursor;

import com.ibrice.moodr.database.DBManager;
import com.ibrice.moodr.reports.items.HabitsItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

// NOTE: comments in this file apply to all other PageViewModels in reports.ui.reports*Fragment, also
public class HabitsActivityModel extends AndroidViewModel implements Observable {

    public ObservableField<String> headerString = new ObservableField<>();
    public ObservableField<List<HabitsItem>> habitsList = new ObservableField<>();

    // manages observable callbacks
    private final PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    // main function
    public HabitsActivityModel(@NonNull Application application) {
        super(application);
        headerString.set("View your Habits!");
        notifyChange();
        refreshListContents();
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    private void refreshListContents() {
        new Thread(() -> {
            DBManager dbManager = new DBManager(getApplication().getApplicationContext());
            dbManager.open();
            Cursor cursorHabits = dbManager.fetchHabits(); // get habits from DB
            List<HabitsItem> habitsItems = new ArrayList<>(); // put them in an array
            if (cursorHabits.moveToFirst()) {
                do {
                    HabitsItem item = parseItem(cursorHabits);
                    habitsItems.add(item);
                } while (cursorHabits.moveToNext());
            }
            cursorHabits.close();
            habitsList.set(habitsItems);
            notifyChange();
        }).start();
    }

    // parse each item in the array to an item in the HabitsItem class
    public HabitsItem parseItem(Cursor cursor) {
        HabitsItem item = new HabitsItem();
        item.ID = cursor.getInt(0);
        // index 1 is for the date and time the thing was created
        item.Title = cursor.getString(2);
        item.Description = cursor.getString(3);
        item.Time = cursor.getString(4);
        return item;
    }
}
