package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.animal.walking.PolarBear;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.autospawn.SpawnResult;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.utils.Utils;

/**
 * @author PikyCZ
 */
public class PolarBearSpawner extends AbstractEntitySpawner {

    public PolarBearSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public SpawnResult spawn(Player player, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (Block.transparent[blockId]) {
            result = SpawnResult.WRONG_BLOCK;
        } else if (biomeId != 12) {
            result = SpawnResult.WRONG_BIOME;
        } else if (pos.y > 255 || pos.y < 1 || blockId == Block.AIR) {
            result = SpawnResult.POSITION_MISMATCH;
        } else if (MobPlugin.getInstance().isAnimalSpawningAllowedByTime(level)) {
            BaseEntity entity = this.spawnTask.createEntity("PolarBear", pos.add(0, 1, 0));
            if (Utils.rand(1, 20) == 1) {
                entity.setBaby(true);
            }
        }

        return result;
    }

    @Override
    public int getEntityNetworkId() {
        return PolarBear.NETWORK_ID;
    }
}
