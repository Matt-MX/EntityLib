package me.tofaa.entitylib.wrapper;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import me.tofaa.entitylib.EntityLib;
import me.tofaa.entitylib.meta.EntityMeta;
import me.tofaa.entitylib.wrapper.ai.AIGroup;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a {@link WrapperEntity} with goals, AI and pathfinding.
 * <p>
 *     <br>
 *     Creature entities require some sort of ticking mechanism on your server to work properly. They need to be dynamically updated every tick.
 *     Goal and Target selectors are grouped into AIGroups, which are then added to the entity. The AIGroups are then updated every tick.
 *     <br>
 *     The {@link WrapperEntityCreature} can be inherited to create custom entities.
 * </p>
 */
public class WrapperEntityCreature extends WrapperLivingEntity {

    private final Set<AIGroup> aiGroups;

    public WrapperEntityCreature(int entityId, @NotNull UUID uuid, EntityType entityType, EntityMeta meta) {
        super(entityId, uuid, entityType, meta);
        this.aiGroups = new HashSet<>();
    }

    public WrapperEntityCreature(int entityId, @NotNull UUID uuid, EntityType entityType) {
        this(entityId, uuid, entityType, EntityMeta.createMeta(entityId, entityType));
    }

    public WrapperEntityCreature(int entityId, EntityType entityType) {
        this(entityId, EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
    }

    public WrapperEntityCreature(UUID uuid, EntityType entityType) {
        this(EntityLib.getPlatform().getEntityIdProvider().provide(uuid, entityType), uuid, entityType);
    }
    public WrapperEntityCreature(EntityType entityType) {
        this(EntityLib.getPlatform().getEntityUuidProvider().provide(entityType), entityType);
    }

    @Override
    public void tick(long time) {
        super.tick(time);
        aiGroups.forEach(aiGroup -> aiGroup.tick(time));
    }

    /**
     * Adds an {@link AIGroup} to the entity.
     * <p>
     * The AIGroup will be updated every tick.
     * </p>
     *
     * @param aiGroup The AIGroup to add.
     */
    public void addAIGroup(AIGroup aiGroup) {
        aiGroups.add(aiGroup);
    }

    /**
     * Removes an {@link AIGroup} from the entity.
     *
     * @param aiGroup The AIGroup to remove.
     */
    public void removeAIGroup(AIGroup aiGroup) {
        aiGroups.remove(aiGroup);
    }

    /**
     * Removes all {@link AIGroup}s from the entity.
     */
    public void clearAIGroups() {
        aiGroups.clear();
    }

    /**
     * Gets the {@link AIGroup}s of the entity.
     *
     * @return The AIGroups of the entity.
     */
    public Set<AIGroup> getAIGroups() {
        return Collections.unmodifiableSet(aiGroups);
    }

    @Override
    public WrapperEntityCreature copy() {
        WrapperEntityCreature copy = new WrapperEntityCreature(this.getEntityId(), this.getUuid(), this.getEntityType(), this.getEntityMeta().copy());

        copy.aiGroups.addAll(this.aiGroups);
        getAttributes().copyTo(copy.getAttributes());
        getEquipment().copyTo(copy.getEquipment());

        return copy;
    }
}
