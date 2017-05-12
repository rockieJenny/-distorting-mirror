package com.inmobi.monetization.internal.imai;

import com.inmobi.commons.internal.Log;
import com.inmobi.monetization.internal.Constants;
import com.inmobi.monetization.internal.configs.Initializer;
import com.inmobi.monetization.internal.imai.db.ClickData;
import com.inmobi.monetization.internal.imai.db.ClickDatabaseManager;
import java.util.ArrayList;
import java.util.Iterator;

public class IMAIClickEventList extends ArrayList<ClickData> {
    private static final long serialVersionUID = -211778664111073467L;

    public static synchronized IMAIClickEventList getLoggedClickEvents() {
        IMAIClickEventList iMAIClickEventList;
        synchronized (IMAIClickEventList.class) {
            iMAIClickEventList = null;
            if (ClickDatabaseManager.getInstance().getNoOfEvents() != 0) {
                int i = Initializer.getConfigParams().getImai().getmDefaultEventsBatch();
                ClickDatabaseManager.getInstance().setDBLimit(Initializer.getConfigParams().getImai().getmMaxDb());
                IMAIClickEventList clickEvents = ClickDatabaseManager.getInstance().getClickEvents(i);
                ArrayList arrayList = new ArrayList();
                Iterator it = clickEvents.iterator();
                while (it.hasNext()) {
                    arrayList.add(Long.valueOf(((ClickData) it.next()).getClickId()));
                }
                ClickDatabaseManager.getInstance().deleteClickEvents(arrayList);
                iMAIClickEventList = clickEvents;
            }
            if (iMAIClickEventList == null) {
                iMAIClickEventList = new IMAIClickEventList();
            }
        }
        return iMAIClickEventList;
    }

    public void saveClickEvents() {
        Log.internal(Constants.LOG_TAG, "Save ping events");
        if (RequestResponseManager.mDBWriterQueue != null && !RequestResponseManager.mDBWriterQueue.isEmpty()) {
            Iterator it = RequestResponseManager.mDBWriterQueue.iterator();
            while (it.hasNext()) {
                ClickDatabaseManager.getInstance().insertClick((ClickData) it.next());
            }
        }
    }

    public ClickData getClickEvent(long j) {
        try {
            Iterator it = iterator();
            while (it.hasNext()) {
                ClickData clickData = (ClickData) it.next();
                if (clickData.getClickId() == j) {
                    return clickData;
                }
            }
            return null;
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Cant get click event", e);
            return null;
        }
    }

    public synchronized boolean removeClickEvent(long j) {
        boolean z = false;
        synchronized (this) {
            try {
                RequestResponseManager.isSynced.set(false);
                remove(getClickEvent(j));
                z = true;
            } catch (Throwable e) {
                Log.internal(Constants.LOG_TAG, "Cant remove click event", e);
            }
        }
        return z;
    }

    public synchronized void reduceRetryCount(int i) {
        try {
            RequestResponseManager.isSynced.set(false);
            ClickData clickEvent = getClickEvent((long) i);
            int retryCount = clickEvent.getRetryCount();
            removeClickEvent((long) i);
            if (retryCount > 1) {
                clickEvent.setRetryCount(clickEvent.getRetryCount() - 1);
                add(clickEvent);
            }
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Cant reduce retry count", e);
        }
    }
}
