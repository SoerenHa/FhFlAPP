package com.streich.todo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.JsonWriter;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by basti on 26.10.2016.
 * Holds a Collection of TodoModels, synchronises itself with a LocalStorage.
 */
public class TodoListHolder extends ArrayList<com.streich.todo.TodoModel>{
    final static String sharedPrefKey ="com.streich.TodoList";
    final static String key = "TodoListHolder";





    public TodoListHolder() {

    }

    public void fetch(Context c){
        Log.v(key,"Fetching Todos from Shared Prefrences");
        SharedPreferences sharedPref = c.getSharedPreferences(sharedPrefKey,Context.MODE_PRIVATE);

        String json = sharedPref.getString("List","[]");
        Log.v(key,"Fetched: "+json);
        Gson gson = new Gson();

        Type t = new TypeToken<ArrayList<com.streich.todo.TodoModel>>(){}.getType();
        ArrayList<com.streich.todo.TodoModel> x = gson.fromJson(json,t);

        for(int i=0; i < x.size(); i++){
            this.add(x.get(i));
        }

        Log.v(key,"Fetched "+x.size()+" Todos from Storage");

    }

    public void commmit(Context c){
        Log.v(key,"Saving Todos into Shared Prefrences");
        SharedPreferences sharedPref = c.getSharedPreferences(sharedPrefKey,Context.MODE_PRIVATE);

        SharedPreferences.Editor edit= sharedPref.edit();

        Gson gson = new Gson();
        //Creating a JSON Representation of the list

        String json = gson.toJson(this);
        Log.v(key,"Generated JSON: "+json);
        edit.putString("List",json);
        edit.commit();
    }

}
