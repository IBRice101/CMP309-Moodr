package com.ibrice.moodr.reports.ui.reportsHabitsFragment;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class PageViewModel extends AndroidViewModel implements Observable {

    private final MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private final LiveData<String> mText = Transformations.map(mIndex, input -> "Habits Reports: " + input);

    public ObservableField<String> headerString = new ObservableField<>();
    public ObservableField<List<HabitsItem>> habitsList = new ObservableField<>();

    private final PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    public PageViewModel(@NonNull Application application) {
        super(application);
        headerString.set("Habits Report");
        notifyChange();
        refreshListContents();
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
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
            Cursor cursorHabits = dbManager.fetchHabits();
            List<HabitsItem> habitsItems = new ArrayList<>();
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

    public HabitsItem parseItem(Cursor cursor) {
        HabitsItem item = new HabitsItem();
        item.ID = cursor.getInt(0);
        item.Title = cursor.getString(2);
        item.Description = cursor.getString(3);
        item.Time = cursor.getString(4);
        return item;
    }
}
