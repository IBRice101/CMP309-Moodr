package com.ibrice.moodr.reports.ui.reportsThreeGoodThingsFragment;

import android.app.Application;
import android.database.Cursor;

import com.ibrice.moodr.database.DBManager;
import com.ibrice.moodr.reports.items.TGTsItem;

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
    private final LiveData<String> mText = Transformations.map(mIndex, input -> "Three Good Things Reports: " + input);

    public ObservableField<String> headerString = new ObservableField<>();
    public ObservableField<List<TGTsItem>> tgtsList = new ObservableField<>();

    private final PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    public PageViewModel(@NonNull Application application) {
        super(application);
        headerString.set("Three Good Things Report");
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
            Cursor cursorTGTs = dbManager.fetchTGT();
            List<TGTsItem> TGTsItems = new ArrayList<>();
            if (cursorTGTs.moveToFirst()) {
                do {
                    TGTsItem item = parseItem(cursorTGTs);
                    TGTsItems.add(item);
                } while (cursorTGTs.moveToNext());
            }
            cursorTGTs.close();
            tgtsList.set(TGTsItems);
            notifyChange();
        }).start();
    }

    public TGTsItem parseItem(Cursor cursor) {
        TGTsItem item = new TGTsItem();
        item.ID = cursor.getInt(0);
        item.Thing1 = cursor.getString(2);
        item.Thing2 = cursor.getString(3);
        item.Thing3 = cursor.getString(4);
        return item;
    }
}
