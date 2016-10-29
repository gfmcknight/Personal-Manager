package com.mcknight.gfm13.personalmanager.Refreshing;

import com.mcknight.gfm13.personalmanager.Task;

/**
 * Created by gfm13 on 10/29/2016.
 */

public class RefreshEvent {
    private RefreshEventType type;

    boolean affectsTask;
    boolean affectsProject;

    private Task affectedTask;
    //private Project affectedProject;

    public RefreshEventType getEventType() {
        return type;
    }

    public boolean affectsTask() {
        return affectsTask;
    }

    public boolean affectsProject() {
        return affectsProject;
    }

    public boolean isPriorityElement() {
        return (affectsTask && true) || (affectsProject && true);
        // TODO: Replace true in line above with code that queries priority from Task/Project
    }

    public Task getAffectedTask() throws IllegalAccessException {
        if (affectsTask) {
            return affectedTask;
        } else {
            throw new IllegalAccessException(
                    "getAffectedTask() can only be called if RefreshEvent.affectsTask() returns true.");
        }
    }

    public RefreshEvent(RefreshEventType type, Task affectedTask) {
        this.affectedTask = affectedTask;
        this.type = type;
        affectsTask = true;
        affectsProject = false;
    }

}
