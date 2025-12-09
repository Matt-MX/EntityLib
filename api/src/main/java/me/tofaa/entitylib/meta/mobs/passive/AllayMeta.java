package me.tofaa.entitylib.meta.mobs.passive;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import me.tofaa.entitylib.meta.Metadata;
import me.tofaa.entitylib.meta.types.MobMeta;

public class AllayMeta extends MobMeta {

    public static final byte OFFSET = MobMeta.MAX_OFFSET;
    public static final byte MAX_OFFSET = OFFSET + 1;

    public AllayMeta(int entityId, Metadata metadata) {
        super(entityId, metadata);
    }

    public boolean isDancing() {
        return metadata.getIndex(OFFSET, false);
    }

    public void setDancing(boolean isDancing) {
        metadata.setIndex(OFFSET, EntityDataTypes.BOOLEAN, isDancing);
    }

    public boolean canDuplicate() {
        return metadata.getIndex(offset(OFFSET, 1), true);
    }

    public void setCanDuplicate(boolean canDuplicate) {
        metadata.setIndex(offset(OFFSET, 1), EntityDataTypes.BOOLEAN, canDuplicate);
    }

}
