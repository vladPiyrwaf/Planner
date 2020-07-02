package com.example.simbirplanner;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.simbirplanner.models.EventInformation;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String FAKE_STRING = "HELLO_WORLD";
    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void add(){
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

    }
}