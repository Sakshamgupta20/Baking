package com.example.saksham.baking;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.saksham.baking.Data.BakeContract;
import com.example.saksham.baking.ingredients.Ingredients;
import com.example.saksham.baking.ingredients.IngredientsAll;
import com.example.saksham.baking.steps.Steps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saksham on 22-03-2018.
 */

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
