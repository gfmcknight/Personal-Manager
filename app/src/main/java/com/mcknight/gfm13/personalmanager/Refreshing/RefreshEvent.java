package com.mcknight.gfm13.personalmanager.Refreshing;

import com.mcknight.gfm13.personalmanager.WorkItems.Project;
import com.mcknight.gfm13.personalmanager.WorkItems.Task;

/**
 * Created by gfm13 on 10/29/2016.
 */

public class RefreshEvent {
    private RefreshEventType type;

    boolean affectsTask;
    boolean affectsProject;

    private int itemID;

    private Task affectedTask;
    private Project affectedProject;

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
        return (affectsTask && affectedTask.isPriority()) || (affectsProject && affectedProject.isPriority());
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

    public Project getAffectedProject() throws IllegalAccessException {
        if (affectsProject) {
            return affectedProject;
        } else {
            throw new IllegalAccessException(
                    "getAffectedProject() can only be called if RefreshEvent.affectsProject() returns true.");
        }
    }

    public RefreshEvent(RefreshEventType type, Task affectedTask) {
        this.affectedTask = affectedTask;
        this.type = type;
        affectsTask = true;
        affectsProject = false;
        itemID = affectedTask.getId();
    }

    public RefreshEvent(RefreshEventType type, Project affectedProject) {
        this.affectedProject = affectedProject;
        this.type = type;
        affectsTask = false;
        affectsProject = true;
        itemID = affectedProject.getId();
    }

    public RefreshEvent(RefreshEventType type, int affectedItemID) {
        itemID = affectedItemID;
        this.type = type;
    }

}
