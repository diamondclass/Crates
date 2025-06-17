package me.diamondclass.Crates.listeners;

import me.diamondclass.Crates.crate.Crate;
import me.diamondclass.Crates.crate.CrateManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

class InventoryClickListener implements Listener {
  private final CrateManager crateManager;
  
  InventoryClickListener(CrateManager crateManager) {
    this.crateManager = crateManager;
  }
  
  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
  public void onInventoryClick(InventoryClickEvent event) {
    HumanEntity humanEntity = event.getWhoClicked();
    Inventory inventory = event.getView().getTopInventory();
    if (inventory != null && !humanEntity.hasPermission("ulises.admin")) {
      final Crate crate = crateManager.getCrate(inventory);

      if (crate != null) {
        event.setCancelled(true);
      }
    }
  }
}
