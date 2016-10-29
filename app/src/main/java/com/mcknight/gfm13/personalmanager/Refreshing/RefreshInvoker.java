package com.mcknight.gfm13.personalmanager.Refreshing;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gfm13 on 10/29/2016.
 */
public class RefreshInvoker {
    private static RefreshInvoker ourInstance = new RefreshInvoker();

    public static RefreshInvoker getInstance() {
        return ourInstance;
    }

    List<IRefreshListener> listeners;

    public void addRefreshListener(IRefreshListener listener){
        listeners.add(listener);
    }

    public void invokeRefreshEvent(RefreshEvent e) {
        for (IRefreshListener listener : listeners) {
            listener.onRefresh(e);
        }
    }

    private RefreshInvoker() {
        listeners = new LinkedList<IRefreshListener>();
    }
}
