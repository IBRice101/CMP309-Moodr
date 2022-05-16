package com.ibrice.moodr.reports.ui.reportsDiaryFragment;

import android.app.Application;
import android.database.Cursor;

import com.ibrice.moodr.database.DBManager;
import com.ibrice.moodr.reports.items.DiariesItem;

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
import androidx.lifecycle.ViewModel;

public class PageViewModel extends AndroidViewModel implements Observable {

    private final MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private final LiveData<String> mText = Transformations.map(mIndex, input -> "Diary Reports: " + input);

    public ObservableField<String> headerString = new ObservableField<>();
    public ObservableField<List<DiariesItem>> diariesList = new ObservableField<>();

    private final PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    public PageViewModel(@NonNull Application application) {
        super(application);
        headerString.set("Diaries Report");
        notifyChange();
        refreshListContents();
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

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
            Cursor cursorDiaries = dbManager.fetchDiary();
            List<DiariesItem> DiariesItems = new ArrayList<>();
            if (cursorDiaries.moveToFirst()) {
                do {
                    DiariesItem item = parseItem(cursorDiaries);
                    DiariesItems.add(item);
                } while (cursorDiaries.moveToNext());
            }
            cursorDiaries.close();
            diariesList.set(DiariesItems);
            notifyChange();
        }).start();
    }

    public DiariesItem parseItem(Cursor cursor) {
        DiariesItem item = new DiariesItem();
        item.ID = cursor.getInt(0);
        item.Title = cursor.getString(2);
        item.Description = cursor.getString(4);
        return item;
    }
}