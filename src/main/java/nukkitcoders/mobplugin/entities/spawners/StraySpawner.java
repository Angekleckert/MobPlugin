package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.monster.walking.Stray;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.autospawn.SpawnResult;

/**
 * @author PikyCZ
 */
public class StraySpawner extends AbstractEntitySpawner {

    public StraySpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public SpawnResult spawn(Player player, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (Block.transparent[blockId]) {
            result = SpawnResult.WRONG_BLOCK;
        }else if (blockLightLevel > 7) {
            result = SpawnResult.WRONG_LIGHTLEVEL;
        } else if (pos.y > 255 || pos.y < 1 || blockId == Block.AIR) {
            result = SpawnResult.POSITION_MISMATCH;
        } else if (biomeId != 12) {
            result = SpawnResult.WRONG_BIOME;
        } else if (MobPlugin.getInstance().isMobSpawningAllowedByTime(level)) {
            this.spawnTask.createEntity("Stray", pos.add(0, 1, 0));
        }

        return result;
    }

    @Override
    public int getEntityNetworkId() {
        return Stray.NETWORK_ID;
    }
}
