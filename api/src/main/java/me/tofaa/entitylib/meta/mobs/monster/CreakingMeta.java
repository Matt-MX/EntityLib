package me.tofaa.entitylib.meta.mobs.monster;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3i;
import me.tofaa.entitylib.meta.Metadata;
import me.tofaa.entitylib.meta.types.MobMeta;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class CreakingMeta extends MobMeta {

    public static final byte OFFSET = MobMeta.MAX_OFFSET;
    public static final byte MAX_OFFSET = OFFSET + 3;

    public CreakingMeta(int entityId, Metadata metadata) {
        super(entityId, metadata);
    }

    public boolean canMove() {
        return metadata.getIndex(OFFSET, true);
    }

    public void setCanMove(boolean canMove) {
        metadata.setIndex(OFFSET, EntityDataTypes.BOOLEAN, canMove);
    }

    public boolean isActive() {
        return metadata.getIndex(offset(OFFSET, 1), false);
    }

    public void setActive(boolean isActive) {
        metadata.setIndex(offset(OFFSET, 1), EntityDataTypes.BOOLEAN, isActive);
    }

    public boolean isTearingDown() {
        return metadata.getIndex(offset(OFFSET, 2), false);
    }

    public void setTearingDown(boolean isTearingDown) {
        metadata.setIndex(offset(OFFSET, 2), EntityDataTypes.BOOLEAN, isTearingDown);
    }

    public Optional<Vector3i> getHomePosition() {
        return metadata.getIndex(offset(OFFSET, 3), Optional.empty());
    }

    public void setHomePosition(@Nullable Vector3i homePosition) {
        metadata.setIndex(offset(OFFSET, 3), EntityDataTypes.OPTIONAL_BLOCK_POSITION, Optional.ofNullable(homePosition));
    }

}
