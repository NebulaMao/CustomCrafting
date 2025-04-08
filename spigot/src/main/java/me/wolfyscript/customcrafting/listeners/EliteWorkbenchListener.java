/*
 *       ____ _  _ ____ ___ ____ _  _ ____ ____ ____ ____ ___ _ _  _ ____
 *       |    |  | [__   |  |  | |\/| |    |__/ |__| |___  |  | |\ | | __
 *       |___ |__| ___]  |  |__| |  | |___ |  \ |  | |     |  | | \| |__]
 *
 *       CustomCrafting Recipe creation and management tool for Minecraft
 *                      Copyright (C) 2021  WolfyScript
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.wolfyscript.customcrafting.listeners;

import com.wolfyscript.utilities.bukkit.items.CustomItemBlockData;
import me.wolfyscript.customcrafting.configs.customitem.EliteCraftingTableSettings;
import me.wolfyscript.customcrafting.data.CCCache;
import me.wolfyscript.utilities.api.WolfyUtilities;
import me.wolfyscript.utilities.api.inventory.gui.GuiHandler;
import me.wolfyscript.utilities.util.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EliteWorkbenchListener implements Listener {

    private final WolfyUtilities api;

    public EliteWorkbenchListener(WolfyUtilities api) {
        this.api = api;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.useInteractedBlock().equals(Event.Result.DENY) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getPlayer().isSneaking()) {
            var block = event.getClickedBlock();
            if (block == null) {
                return;
            }
            api.getCore().getPersistentStorage().getOrCreateWorldStorage(block.getWorld()).getBlock(block.getLocation())
                .flatMap(blockData -> blockData.getData(CustomItemBlockData.ID, CustomItemBlockData.class))
                .flatMap(CustomItemBlockData::getCustomItem)
                .ifPresent(customItem -> customItem.getData(EliteCraftingTableSettings.class).ifPresent(settings -> {
                    if (settings.isEnabled()) {
                        event.setCancelled(true);
                        GuiHandler<CCCache> guiHandler = api.getInventoryAPI(CCCache.class).getGuiHandler(event.getPlayer());
                        guiHandler.getCustomCache().getEliteWorkbench().setCustomItem(customItem);
                        guiHandler.getCustomCache().getEliteWorkbench().setSettings(settings);
                        guiHandler.openWindow(new NamespacedKey("crafting", "crafting_grid" + settings.getGridSize()));
                    }
                }));
        }
    }

}
