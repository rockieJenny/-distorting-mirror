package com.inmobi.commons.analytics.iat.impl;

import com.inmobi.commons.analytics.iat.impl.Goal.State;
import com.inmobi.commons.analytics.iat.impl.config.AdTrackerEventType;
import com.inmobi.commons.analytics.iat.impl.config.AdTrackerInitializer;
import com.inmobi.commons.internal.FileOperations;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import java.util.Iterator;
import java.util.Vector;

public class GoalList extends Vector<Goal> {
    public static GoalList getLoggedGoals() {
        GoalList goalList = null;
        if (FileOperations.isFileExist(InternalSDKUtil.getContext(), AdTrackerConstants.IMGOAL_FILE)) {
            goalList = (GoalList) FileOperations.readFromFile(InternalSDKUtil.getContext(), AdTrackerConstants.IMGOAL_FILE);
        }
        if (goalList == null) {
            return new GoalList();
        }
        return goalList;
    }

    public void saveGoals() {
        FileOperations.saveToFile(InternalSDKUtil.getContext(), AdTrackerConstants.IMGOAL_FILE, this);
    }

    public Goal getGoal(String str) {
        if (str == null || "".equals(str.trim())) {
            Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "GoalName is null");
            return null;
        }
        try {
            Iterator it = iterator();
            while (it.hasNext()) {
                Goal goal = (Goal) it.next();
                if (goal.name.equals(str)) {
                    return goal;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean removeGoal(String str, int i) {
        if (str == null || "".equals(str.trim())) {
            Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "GoalName is null");
            return false;
        } else if (i < 1) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "GoalCount cannot be 0 or negative");
            return false;
        } else {
            Iterator it = iterator();
            while (it.hasNext()) {
                Goal goal = (Goal) it.next();
                if (goal.name.equals(str)) {
                    int i2 = goal.count - i;
                    if (str.equals(AdTrackerConstants.GOAL_DOWNLOAD)) {
                        remove(goal);
                    } else if (i2 <= 0) {
                        remove(goal);
                    } else {
                        goal.count = i2;
                    }
                    return true;
                }
            }
            return true;
        }
    }

    public boolean addGoal(String str, int i, long j, int i2, boolean z) {
        if (str == null || "".equals(str.trim())) {
            Log.debug(AdTrackerConstants.IAT_LOGGING_TAG, "Goal name is null");
            return false;
        } else if (i < 1 || j < 0) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "GoalCount cant be 0 or RetryTime cannot be negative");
            return false;
        } else {
            try {
                Object obj;
                Iterator it = iterator();
                while (it.hasNext()) {
                    Goal goal = (Goal) it.next();
                    if (goal.name.equals(str)) {
                        if (State.REPORTING_REQUESTED == goal.state) {
                            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Attempt to update goal (" + goal.name + ") while it is being reported to the server! Ignoring ... ");
                            return false;
                        }
                        if (!AdTrackerConstants.GOAL_DOWNLOAD.equals(str)) {
                            goal.count += i;
                        }
                        goal.retryCount = i2;
                        goal.retryTime = j;
                        goal.isDuplicate = z;
                        obj = 1;
                        if (obj == null) {
                            add(new Goal(str, i, j, i2, z));
                        }
                        return true;
                    }
                }
                obj = null;
                if (obj == null) {
                    add(new Goal(str, i, j, i2, z));
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public boolean increaseRetryTime(String str, int i, boolean z) {
        if (str == null || "".equals(str.trim())) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "GoalName cannot be null");
            return false;
        }
        Goal goal = getGoal(str);
        if (goal == null) {
            return false;
        }
        int maxWaitTime = AdTrackerInitializer.getConfigParams().getRetryParams().getMaxWaitTime();
        int maxRetry = AdTrackerInitializer.getConfigParams().getRetryParams().getMaxRetry();
        long j = goal.retryTime;
        int i2 = goal.retryCount;
        if (j < ((long) maxWaitTime)) {
            j = (j * 2) + 30000;
            if (j > ((long) maxWaitTime)) {
                j = (long) maxWaitTime;
            }
        } else {
            j = (long) maxWaitTime;
        }
        maxWaitTime = i2 + 1;
        goal.retryTime = j;
        goal.retryCount = maxWaitTime;
        if (maxWaitTime >= maxRetry) {
            AdTrackerUtils.reportMetric(AdTrackerEventType.GOAL_DUMPED, goal, 0, 0, 0, null);
        }
        return true;
    }
}
