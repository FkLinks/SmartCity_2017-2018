package com.henallux.smartcity.DAO;

import com.henallux.smartcity.Exceptions.GetEventsException;
import com.henallux.smartcity.Model.Event;

import org.json.JSONException;

import java.util.ArrayList;

public interface EventDAO {
    ArrayList<Event> getAllEvents() throws GetEventsException, JSONException;
}
