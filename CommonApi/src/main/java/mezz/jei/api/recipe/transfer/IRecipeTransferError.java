package mezz.jei.api.recipe.transfer;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import net.minecraft.client.gui.GuiGraphics;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.List;

/**
 * A reason that a recipe transfer couldn't happen.
 *
 * Recipe transfer errors can be created with {@link IRecipeTransferHandlerHelper} or you can implement your own.
 * These errors are returned from {@link IRecipeTransferHandler#transferRecipe(AbstractContainerMenu, Object, IRecipeSlotsView, Player, boolean, boolean)}.
 */
public interface IRecipeTransferError {
	enum Type {
		/**
		 * Errors where the Transfer handler is broken or does not work.
		 * These errors will hide the recipe transfer button, and do not display anything to the user.
		 */
		INTERNAL(false),

		/**
		 * Errors that the player can fix. Missing items, inventory full, etc.
		 * Something informative will be shown to the player.
		 */
		USER_FACING(false),

		/**
		 * Errors that still allow the usage of the recipe transfer button.
		 * Hovering over the button will display the error, however the button is active and can be used.
		 * @since 6.0.2
		 */
		COSMETIC(true);

		/**
		 * Returns true if this type of error will allow users to do the transfer, despite the error.
		 * @since 11.5.0
		 */
		public final boolean allowsTransfer;

		Type(boolean allowsTransfer) {
			this.allowsTransfer = allowsTransfer;
		}
	}

	Type getType();

	/**
	 * Return the ARGB color of the additional button highlight for {@link Type#COSMETIC}.
	 * For example, return 0 to disable the colored highlight. Default color is orange.
	 *
	 * @since 11.2.1
	 */
	default int getButtonHighlightColor() {
		return 0x80FFA500;
	}

	/**
	 * Called on {@link Type#USER_FACING} and {@link Type#COSMETIC} errors.
	 *
	 * @since 9.3.0
	 */
	default void showError(GuiGraphics guiGraphics, int mouseX, int mouseY, IRecipeSlotsView recipeSlotsView, int recipeX, int recipeY) {

	}

	/**
	 * Called on {@link Type#USER_FACING} and {@link Type#COSMETIC} errors.
	 *
	 * @since 19.4.0
	 * @deprecated use {@link #getTooltip(ITooltipBuilder)}
	 */
	@Deprecated(since = "19.5.4", forRemoval = true)
	default List<Component> getTooltip() {
		return List.of();
	}

	/**
	 * Called on {@link Type#USER_FACING} and {@link Type#COSMETIC} errors.
	 *
	 * @since 19.5.4
	 */
	default void getTooltip(ITooltipBuilder tooltip) {
		tooltip.addAll(getTooltip());
	}

	/**
	 * Get the estimated number of inputs of the recipe that cannot be found in the container.
	 *
	 * This is used to help sort recipes with more matches first, so that if a player
	 * has many (or all) of the items required for a recipe in their inventory, it is shown first.
	 *
	 * @return the number of input recipes slots are missing ingredient's in the player's inventory.
	 *         Return -1 by default to avoid sorting
	 *
	 * @since 19.2.0
	 */
	default int getMissingCountHint() {
		return -1;
	}
}
