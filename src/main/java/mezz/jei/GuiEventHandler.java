package mezz.jei;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import mezz.jei.gui.GuiItemListOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;

public class GuiEventHandler {

	private GuiItemListOverlay itemListOverlay = new GuiItemListOverlay();

	@SubscribeEvent
	public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
		GuiContainer guiContainer = asGuiContainer(event.gui);
		if (guiContainer == null)
			return;
		itemListOverlay.initGui(guiContainer);
	}

	@SubscribeEvent
	public void onDrawScreenEvent(GuiScreenEvent.DrawScreenEvent.Post event) {
		GuiContainer guiContainer = asGuiContainer(event.gui);
		if (guiContainer == null)
			return;

		itemListOverlay.drawScreen(guiContainer.mc, event.mouseX, event.mouseY);
		itemListOverlay.handleMouseEvent(guiContainer.mc, event.mouseX, event.mouseY);

		/**
		 * There is no way to render between the existing inventory tooltip and the dark background layer,
		 * so we have to re-render the inventory tooltip over the item list.
		 **/
		Slot theSlot = guiContainer.theSlot;
		if (theSlot != null && theSlot.getHasStack()) {
			ItemStack itemStack = theSlot.getStack();
			guiContainer.renderToolTip(itemStack, event.mouseX, event.mouseY);
		}
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END)
			return;

		Minecraft minecraft = Minecraft.getMinecraft();
		if (asGuiContainer(minecraft.currentScreen) == null)
			return;

		itemListOverlay.handleTick();
	}

	private GuiContainer asGuiContainer(GuiScreen guiScreen) {
		if (!(guiScreen instanceof GuiContainer) || (guiScreen instanceof GuiContainerCreative))
			return null;
		return (GuiContainer)guiScreen;
	}
}
