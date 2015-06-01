package com.autelhome.multiroom.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Abstract aggregate root for domain objects.
 *
 * @author xdeclercq
 */
public abstract class AbstractAggregateRoot implements AggregateRoot {

    private final List<Event> changes = new ArrayList<>();

    protected UUID id;
    protected int version;

    @Override
    public List<Event> getUncommittedChanges() {
        return Collections.unmodifiableList(changes);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void applyChange(final Event event) {
        applyChange(event, true);
    }

    private void applyChange(final Event event, final boolean isNew) {
        apply(event);
        if (isNew) {
            changes.add(event);
        }
    }

    protected abstract void apply(Event event);

    @Override
    public void loadFromHistory(final List<Event> history) {
        if (history.isEmpty()) {
            return;
        }
        history.forEach(event -> applyChange(event, false));
        version = history.get(history.size()-1).getVersion();
    }

    @Override
    public void markChangesAsCommited() {
        changes.clear();
    }
}
