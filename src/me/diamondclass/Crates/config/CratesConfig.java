package me.diamondclass.Crates.config;

import java.util.*;
import org.bukkit.configuration.Configuration;
import me.diamondclass.Crates.util.ConfigWrapper;
import me.diamondclass.Crates.util.Placeholder;
import me.diamondclass.Crates.util.StringUtil;

public class CratesConfig {
    private ConfigWrapper settingsWrapper;
    private ConfigWrapper langWrapper;
    private final Map<String, List<String>> listCache = new HashMap<>();
    private final Map<String, String> stringCache = new HashMap<>();

    public CratesConfig(Configuration settings, Configuration lang) {
        this.settingsWrapper = new ConfigWrapper(settings);
        this.langWrapper = new ConfigWrapper(lang);
    }

    public void reload(Configuration settings, Configuration lang) {
        this.settingsWrapper = new ConfigWrapper(settings);
        this.langWrapper = new ConfigWrapper(lang);
        listCache.clear();
        stringCache.clear();
    }

    private String getRawLangString(String key) {
        if (stringCache.containsKey(key)) return stringCache.get(key);
        String value = langWrapper.getOrDefault(key, "undefined");
        stringCache.put(key, value);
        return value;
    }

    private List<String> getRawLangList(String key) {
        if (listCache.containsKey(key)) return listCache.get(key);
        List<String> value = langWrapper.getOrDefault(key, new ArrayList<>());
        listCache.put(key, value);
        return value;
    }

    private String applyPrefix(String message) {
        String prefix = getRawLangString("prefix");
        if (prefix == null || prefix.isEmpty() || "undefined".equals(prefix)) return message;
        return prefix + " " + message;
    }

    public String getRawPluginMainCommand() {
        return settingsWrapper.getOrDefault("plugin_main_command", "ulises");
    }

    public String getString(String key) {
        return applyPrefix(getRawLangString(key));
    }

    public List<String> getStringList(String key) {
        List<String> raw = getRawLangList(key);
        List<String> prefixed = new ArrayList<>();
        for (String line : raw) {
            prefixed.add(applyPrefix(line));
        }
        return prefixed;
    }

    public List<String> getHologramLines(String displayName) {
        return StringUtil.replace(getRawLangList("hologram_lines"), new Placeholder("%displayname%", displayName));
    }

    public String getItemName(String displayName) {
        return StringUtil.replace(getRawLangString("item.name"), new Placeholder("%displayname%", displayName));
    }

    public List<String> getItemLore(String displayName) {
        return StringUtil.replace(getRawLangList("item.lore"), new Placeholder("%displayname%", displayName));
    }

    public String getInventoryTitle(String displayName) {
        return StringUtil.replace(getRawLangString("inventory.title"), new Placeholder("%displayname%", displayName));
    }

    public String getCommandUsage(String label, String cmd, String args) {
        return applyPrefix(StringUtil.replace(getRawLangString("command_usage"),
                new Placeholder("%label%", label),
                new Placeholder("%cmd%", cmd),
                new Placeholder("%args%", args)));
    }

    public String getPluginMainCommand() {
        return getString("plugin_main_command");
    }

    public String getReloadSuccess() { return getString("reload_success"); }
    public String getError() { return getString("error"); }
    public String getNoConsole() { return getString("no_console"); }
    public String getNoPermission() { return getString("no_permission"); }
    public String getNoBlock() { return getString("no_block"); }
    public String getNoKeys() { return getString("no_keys"); }
    public String getNoKeysPending() { return getString("no_keys_pending"); }
    public String getNoSpace() { return getString("no_space"); }
    public String getNoCrate() { return getString("no_crate"); }
    public String getNoInteract() { return getString("no_interact"); }
    public String getInvalidNumber() { return getString("invalid_number"); }
    public String getAddLocationAlreadySet() { return getString("add_location.already_set"); }
    public String getHelpSubtitle() { return getString("help.subtitle"); }
    public String getAddLocationDescription() { return getString("add_location.description"); }
    public String getCheckDescription() { return getString("check.description"); }
    public String getClaimDescription() { return getString("claim.description"); }
    public String getContentsDescription() { return getString("contents.description"); }
    public String getCreateDescription() { return getString("create.description"); }
    public String getDisplaynameDescription() { return getString("displayname.description"); }
    public String getKeyallDescription() { return getString("keyall.description"); }
    public String getKeyDescription() { return getString("key.description"); }
    public String getListDescription() { return getString("list.description"); }
    public String getRemoveDescription() { return getString("remove.description"); }
    public String getRemoveLocationDescription() { return getString("remove_location.description"); }
    public String getRowsDescription() { return getString("rows.description"); }

    public String getInvalidKey(String crateName, String keyName) {
        return applyPrefix(StringUtil.replace(getRawLangString("invalid_key"),
                new Placeholder("%crate_name%", crateName),
                new Placeholder("%key_name%", keyName)));
    }

    public String getValidKey(String crateName) {
        return applyPrefix(StringUtil.replace(getRawLangString("valid_key"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getReceivedKeys(String giverName, int amount, String crateName) {
        return applyPrefix(StringUtil.replace(getRawLangString("received_keys"),
                new Placeholder("%giver_name%", giverName),
                new Placeholder("%amount%", amount),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getHelpTitle(String version) {
        return applyPrefix(StringUtil.replace(getRawLangString("help.title"),
                new Placeholder("%version%", version)));
    }

    public String getHelpCommand(String label, String cmd, String args, String description) {
        return applyPrefix(StringUtil.replace(getRawLangString("help.command"),
                new Placeholder("%label%", label),
                new Placeholder("%cmd%", cmd),
                new Placeholder("%args%", args),
                new Placeholder("%description%", description)));
    }

    public String getAddLocationSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawLangString("add_location.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getCheckSuccess(int amount) {
        return applyPrefix(StringUtil.replace(getRawLangString("check.success"),
                new Placeholder("%amount%", amount)));
    }

    public String getClaimSuccess(int amount) {
        return applyPrefix(StringUtil.replace(getRawLangString("claim.success"),
                new Placeholder("%amount%", amount)));
    }

    public String getContentsSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawLangString("contents.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getCreateSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawLangString("create.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getDisplaynameSuccess(String crateName, String newName) {
        return applyPrefix(StringUtil.replace(getRawLangString("displayname.success"),
                new Placeholder("%crate_name%", crateName),
                new Placeholder("%new_name%", newName)));
    }

    public String getKeyallSuccess(int amount, String crateName) {
        return applyPrefix(StringUtil.replace(getRawLangString("keyall.success"),
                new Placeholder("%amount%", amount),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getKeySuccess(int amount, String crateName, String playerName) {
        return applyPrefix(StringUtil.replace(getRawLangString("key.success"),
                new Placeholder("%amount%", amount),
                new Placeholder("%crate_name%", crateName),
                new Placeholder("%player_name%", playerName)));
    }

    public String getListSuccess(String cratesList) {
        return applyPrefix(StringUtil.replace(getRawLangString("list.success"),
                new Placeholder("%crates_list%", cratesList)));
    }

    public String getRemoveSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawLangString("remove.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getRemoveLocationSuccess(String crateName) {
        return applyPrefix(StringUtil.replace(getRawLangString("remove_location.success"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getRemoveLocationNoCrateAt(String crateName) {
        return applyPrefix(StringUtil.replace(getRawLangString("remove_location.no_crate_at_location"),
                new Placeholder("%crate_name%", crateName)));
    }

    public String getRowsSuccess(String crateName, int slots) {
        return applyPrefix(StringUtil.replace(getRawLangString("rows.success"),
                new Placeholder("%crate_name%", crateName),
                new Placeholder("%rows%", slots)));
    }
}
