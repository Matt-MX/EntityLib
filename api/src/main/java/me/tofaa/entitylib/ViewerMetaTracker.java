package me.tofaa.entitylib;

import me.tofaa.entitylib.meta.EntityMeta;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

// TODO(matt): Should handle attributes as well e.g. should use WrapperEntity not EntityMeta
public class ViewerMetaTracker {
    private final WrapperEntity parent;
    private final HashMap<UUID, WrapperEntity> viewerMap = new HashMap<>();
    private boolean autoRefresh = true;

    public ViewerMetaTracker(@NotNull WrapperEntity parent) {
        this.parent = parent;
    }

    public boolean remove(@NotNull UUID uniqueId) {
        boolean wasRemoved = this.viewerMap.remove(uniqueId) != null;

        if (autoRefresh && this.parent.hasViewer(uniqueId)) {
            WrapperEntity.sendPacket(uniqueId, parent.getEntityMeta().createPacket());
        }

        return wasRemoved;
    }

    public void clear() {
        synchronized (this.viewerMap) {
            Iterator<UUID> it = this.viewerMap.keySet().iterator();

            while (it.hasNext()) {
                UUID user = it.next();

                remove(user);

                it.remove();
            }
        }
    }

    public void consume(UUID viewer, Consumer<WrapperEntity> consumer) {
        this.viewerMap.putIfAbsent(viewer, parent.copy());

        consumer.accept(getOrCreateEntity(viewer));

        tryAutoRefresh(viewer);
    }

    public <E extends WrapperEntity> void consumeEntity(UUID viewer, Consumer<E> consumer) {
        this.viewerMap.putIfAbsent(viewer, parent.copy());

        consumer.accept((E) getOrCreateEntity(viewer));

        tryAutoRefresh(viewer);
    }

    public <E extends WrapperEntity> E getOrCreateEntityAs(UUID viewer) {
        this.viewerMap.putIfAbsent(viewer, parent.copy());
        return (E) this.viewerMap.get(viewer);
    }

    public WrapperEntity getOrCreateEntity(UUID viewer) {
        this.viewerMap.putIfAbsent(viewer, parent.copy());
        return this.viewerMap.get(viewer);
    }

    public Optional<WrapperEntity> getCustomEntity(UUID viewer) {
        return Optional.ofNullable(this.viewerMap.get(viewer));
    }

    public WrapperEntity getCustomEntityOrDefault(UUID viewer) {
        return getCustomEntity(viewer).orElse(this.parent);
    }

    public void consumeEntityMeta(UUID viewer, Consumer<EntityMeta> consumer) {
        consumeEntityMeta(viewer, EntityMeta.class, consumer);
    }

    public <T extends EntityMeta> void consumeEntityMeta(UUID viewer, Class<T> clazz, Consumer<T> consumer) {
        WrapperEntity entity = getOrCreateEntity(viewer);
        consumer.accept(clazz.cast(entity.getEntityMeta()));

        tryAutoRefresh(viewer);
    }

    public void tryAutoRefresh(UUID viewer) {
        if (this.autoRefresh && this.parent.hasViewer(viewer)) {
            this.parent.refresh(viewer);
        }
    }

    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public boolean hasCustomViewerMeta(@NotNull UUID viewer) {
        return this.viewerMap.containsKey(viewer);
    }
}
