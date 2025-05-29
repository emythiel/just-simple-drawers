package dev.emythiel.justsimpledrawers.block.base;

import dev.emythiel.justsimpledrawers.block.entity.StorageBlockEntity;
import net.minecraft.world.level.block.EntityBlock;

public abstract class StorageBlock<T extends StorageBlockEntity> extends BaseBlock implements EntityBlock {
    public StorageBlock(Properties properties) {
        super(properties);
    }
}
