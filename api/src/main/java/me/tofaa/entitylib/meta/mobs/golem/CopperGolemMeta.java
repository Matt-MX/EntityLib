package me.tofaa.entitylib.meta.mobs.golem;

import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.data.struct.CopperGolemState;
import com.github.retrooper.packetevents.protocol.entity.data.struct.WeatheringCopperState;
import me.tofaa.entitylib.meta.Metadata;
import me.tofaa.entitylib.meta.types.MobMeta;

public class CopperGolemMeta extends MobMeta {

    public static final byte OFFSET = MobMeta.MAX_OFFSET;
    public static final byte MAX_OFFSET = OFFSET + 1;

    public CopperGolemMeta(int entityId, Metadata metadata) {
        super(entityId, metadata);
    }

    public WeatheringCopperState getWeatheringState() {
        return super.metadata.getIndex(OFFSET, WeatheringCopperState.UNAFFECTED); // 16 offset
    }

    public void setWeatheringCopperState(WeatheringCopperState state) {
        super.metadata.setIndex(OFFSET, EntityDataTypes.WEATHERING_COPPER_STATE, state);
    }

    public CopperGolemState getState() {
        return super.metadata.getIndex(offset(OFFSET, 1), CopperGolemState.IDLE);
    }

    public void setState(CopperGolemState state) {
        super.metadata.setIndex(offset(OFFSET, 1), EntityDataTypes.COPPER_GOLEM_STATE, state);
    }

}
