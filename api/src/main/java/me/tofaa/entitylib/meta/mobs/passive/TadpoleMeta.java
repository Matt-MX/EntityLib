package me.tofaa.entitylib.meta.mobs.passive;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import me.tofaa.entitylib.meta.Metadata;
import me.tofaa.entitylib.meta.types.MobMeta;

public class TadpoleMeta extends MobMeta {

    public static final byte OFFSET = MobMeta.MAX_OFFSET;
    public static final byte MAX_OFFSET = OFFSET;

    public TadpoleMeta(int entityId, Metadata metadata) {
        super(entityId, metadata);
    }

    public boolean isFromBucket() {
        return metadata.getIndex(OFFSET, false);
    }

    public void setFromBucket(boolean isFromBucket) {
        metadata.setIndex(OFFSET, EntityDataTypes.BOOLEAN, isFromBucket);
    }
}
