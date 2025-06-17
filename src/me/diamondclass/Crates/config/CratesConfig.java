package me.diamondclass.Crates.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.Configuration;
import me.diamondclass.Crates.util.ConfigWrapper;
import me.diamondclass.Crates.util.Placeholder;
import me.diamondclass.Crates.util.StringUtil;

public class CratesConfig {
    private final ConfigWrapper configWrapper;
    private final Map<String, List<String>> listCache = new HashMap<>();
    private final Map<String, String> stringCache = new HashMap<>();

    public CratesConfig(Configuration config) {
        configWrapper = new ConfigWrapper(config);
    }

    private String getRawString(String key) {
        if (stringCache.containsKey(key)) {
            return stringCache.get(key);
        }
        String value = configWrapper.getOrDefault(key, "undefined");
        stringCache.put(key, value);
        return value;
    }

    private List<String> getRawStringList(String key) {
        if (listCache.containsKey(key)) {
            return listCache.get(key);
        }
        List<String> value = configWrapper.getOrDefault(key, new ArrayList<>());
        listCache.put(key, value);
        return value;
    }

    private String applyPrefix(String message) {
        String prefix = getRawString("prefix");
        if (prefix == null || prefix.isEmpty() || "undefined".equals(prefix)) {
            return message;
        }
        return prefix + " " + message;
    }

    public String getRawPluginMainCommand() {
        return getRawString("plugin_main_command");
    }

    public String getString(String key) {
        return applyPrefix(getRawString(key));
    }

    public List<String> getStringList(String key) {
        List<String> raw = getRawStringList(key);
        List<String> prefixed = new ArrayList<>();
        for (String line : raw) {
            prefixed.add(applyPrefix(line));
        }
        return prefixed;
    }

    public List<String> getHologramLines(String displayName) {
        return StringUtil.replace(getRawStringList("hologram_lines"), new Placeholder("%displayname%", displayName));
    }

    public String getItemName(String displayName) {
        return StringUtil.replace(getRawString("item.name"), new Placeholder("%displayname%", displayName));
    }

    public List<String> getItemLore(String displayName) {
        return StringUtil.replace(getRawStringList("item.lore"), new Placeholder("%displayname%", displayName));
    }

    public String getInventoryTitle(String displayName) {
        return StringUtil.replace(getRawString("inventory.title"), new Placeholder("%displayname%", displayName));
    }

    public String getCommandUsage(String label, String cmd, String args) {
        return applyPrefix(StringUtil.replace(getRawString("command_usage"),
                new Placeholder("%label%", label),
                new Placeholder("%cmd%", cmd),
                new Placeholder("%args%", args)));
    }

    public String getPluginMainCommand() {
        return getString("plugin_main_command");
    }

    public String getError() {
        return getString("error");
    }

    public String getNoConsole() {
        return getString("no_console");
    }

    public String getNoPermission() {
        return getString("no_permission");
    }

    public String getNoBlock() {
        return getString("no_block");
    }

    public String getNoKeys() {
        return getString("no_keys");
    }

    public String getNoKeysPending() {
        return getString("no_keys_pending");
    }

    public String getNoSpace() {
        return getString("no_space");
    }

    public String getNoCrate() {
        return getString("no_crate");
    }

    public String getNoInteract() {
        return getString("no_interact");
    }

    public String getInvalidKey(String crateName, String keyName) {
        return applyPrefix(StringUtil.replace(getRawString("invalid_key"),
                new Placeholder("%crate_name%", crateName),
                new Placeholder("%key_name%", keyName)));
    }

    public String getInvalidNumber() {
        return getString("invalid_number");
    }

    public String getValidKey(String crateName) {
        return applyPrefix(StringUtil.replace(getRawString("valid_key"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getReceivedKeys(String giverName, int amount, String crateName) {
        return applyPrefix(StringUtil.replace(getRawString("received_keys"),
                new Placeholder("%giver_name%", giverName),
                new Placeholder("%amount%", amount),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getHelpTitle(String version) {
        return applyPrefix(StringUtil.replace(getRawString("help.title"),
                new Placeholder("%version%", version)));
    }

    public String getHelpCommand(String label, String cmd, String args, String description) {
        return applyPrefix(StringUtil.replace(getRawString("help.command"),
                new Placeholder("%label%", label),
                new Placeholder("%cmd%", cmd),
                new Placeholder("%args%", args),
                new Placeholder("%description%", description)));
    }

    public String getHelpSubtitle() {
        return getString("help.subtitle");
    }

    public String getAddLocationSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawString("add_location.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getAddLocationDescription() {
        return getString("add_location.description");
    }

    public String getCheckSuccess(int amount) {
        return applyPrefix(StringUtil.replace(getRawString("check.success"),
                new Placeholder("%amount%", amount)));
    }

    public String getCheckDescription() {
        return getString("check.description");
    }

    public String getClaimSuccess(int amount) {
        return applyPrefix(StringUtil.replace(getRawString("claim.success"),
                new Placeholder("%amount%", amount)));
    }

    public String getClaimDescription() {
        return getString("claim.description");
    }

    public String getContentsSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawString("contents.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getContentsDescription() {
        return getString("contents.description");
    }

    public String getCreateSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawString("create.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getCreateDescription() {
        return getString("create.description");
    }

    public String getDisplaynameSuccess(String crateName, String newName) {
        return applyPrefix(StringUtil.replace(getRawString("displayname.success"),
                new Placeholder("%crate_name%", crateName),
                new Placeholder("%new_name%", newName)));
    }

    public String getDisplaynameDescription() {
        return getString("displayname.description");
    }

    public String getKeyallSuccess(int amount, String crateName) {
        return applyPrefix(StringUtil.replace(getRawString("keyall.success"),
                new Placeholder("%amount%", amount),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getKeyallDescription() {
        return getString("keyall.description");
    }

    public String getKeySuccess(int amount, String crateName, String playerName) {
        return applyPrefix(StringUtil.replace(getRawString("key.success"),
                new Placeholder("%amount%", amount),
                new Placeholder("%crate_name%", crateName),
                new Placeholder("%player_name%", playerName)));
    }

    public String getKeyDescription() {
        return getString("key.description");
    }

    public String getListSuccess(String cratesList) {
        return applyPrefix(StringUtil.replace(getRawString("list.success"),
                new Placeholder("%crates_list%", cratesList)));
    }

    public String getListDescription() {
        return getString("list.description");
    }

    public String getRemoveSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawString("remove.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getRemoveDescription() {
        return getString("remove.description");
    }

    public String getRemoveLocationSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawString("remove_location.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getRemoveLocationDescription() {
        return getString("remove_location.description");
    }

    public String getAddLocationAlreadySet() {
        return getString("add_location.already_set");
    }

    public String getRemoveLocationNoCrateAt(String crateName) {
        return applyPrefix(StringUtil.replace(getRawString("remove_location.no_crate_at_location"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getRowsSuccess(String crateName, int slots) {
        return applyPrefix(StringUtil.replace(getRawString("rows.success"),
                new Placeholder("%crate_name%", crateName),
                new Placeholder("%rows%", slots)));
    }

    public String getRowsDescription() {
        return getString("rows.description");
    }
}
