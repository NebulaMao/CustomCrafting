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

package me.wolfyscript.customcrafting.data;

import com.wolfyscript.utilities.KeyedStaticId;
import com.wolfyscript.utilities.bukkit.persistent.player.CustomPlayerData;
import me.wolfyscript.customcrafting.utils.NamespacedKeyUtils;
import me.wolfyscript.utilities.util.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

@KeyedStaticId(CCPlayerData.ID)
public class CCPlayerData extends CustomPlayerData {

    protected static final String ID = NamespacedKeyUtils.NAMESPACE + ":" + "common_player_data";

    private boolean darkMode;
    private int totalCrafts;
    private int advancedCrafts;
    private int normalCrafts;

    private Map<NamespacedKey, Integer> crafts;

    private CCPlayerData() {
        super(NamespacedKey.of(ID));
        this.darkMode = false;
        this.totalCrafts = 0;
        this.advancedCrafts = 0;
        this.normalCrafts = 0;
        this.crafts = new HashMap<>();
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public NamespacedKey getDarkBackground() {
        return new NamespacedKey("none", isDarkMode() ? "glass_black" : "glass_gray");
    }

    public NamespacedKey getLightBackground() {
        return new NamespacedKey("none", isDarkMode() ? "glass_gray" : "glass_white");
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public int getTotalCrafts() {
        return totalCrafts;
    }

    public void setTotalCrafts(int totalCrafts) {
        this.totalCrafts = totalCrafts;
    }

    public int getAdvancedCrafts() {
        return advancedCrafts;
    }

    public void setAdvancedCrafts(int advancedCrafts) {
        this.advancedCrafts = advancedCrafts;
    }

    public int getNormalCrafts() {
        return normalCrafts;
    }

    public void setNormalCrafts(int normalCrafts) {
        this.normalCrafts = normalCrafts;
    }

    public void increaseTotalCrafts(int increase) {
        this.totalCrafts += increase;
    }

    public void increaseAdvancedCrafts(int increase) {
        this.advancedCrafts += increase;
    }

    public void increaseNormalCrafts(int increase) {
        this.normalCrafts += increase;
    }

    public synchronized int getRecipeCrafts(NamespacedKey recipeKey) {
        return crafts.getOrDefault(recipeKey, 0);
    }

    public synchronized void increaseRecipeCrafts(NamespacedKey recipeKey, int increase) {
        crafts.put(recipeKey, getRecipeCrafts(recipeKey) + increase);
    }

    @Override
    public String toString() {
        return "CCPlayerData{" +
                "darkMode=" + darkMode +
                ", totalCrafts=" + totalCrafts +
                ", advancedCrafts=" + advancedCrafts +
                ", normalCrafts=" + normalCrafts +
                ", crafts=" + crafts +
                "} ";
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }

    @Override
    public CustomPlayerData copy() {
        return null;
    }

}
