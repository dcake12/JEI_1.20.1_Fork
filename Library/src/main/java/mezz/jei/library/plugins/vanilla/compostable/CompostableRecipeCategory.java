package mezz.jei.library.plugins.vanilla.compostable;

import net.minecraft.client.gui.GuiGraphics;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.vanilla.IJeiCompostingRecipe;
import mezz.jei.common.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class CompostableRecipeCategory implements IRecipeCategory<IJeiCompostingRecipe> {
	public static final int width = 120;
	public static final int height = 18;

	private final IDrawable background;
	private final IDrawable slot;
	private final IDrawable icon;
	private final Component localizedName;

	public CompostableRecipeCategory(IGuiHelper guiHelper) {
		background = guiHelper.createBlankDrawable(width, height);
		slot = guiHelper.getSlotDrawable();
		icon = guiHelper.createDrawableItemStack(new ItemStack(Blocks.COMPOSTER));
		localizedName = Component.translatable("gui.jei.category.compostable");
	}

	@Override
	public RecipeType<IJeiCompostingRecipe> getRecipeType() {
		return RecipeTypes.COMPOSTING;
	}

	@Override
	public Component getTitle() {
		return localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, IJeiCompostingRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
			.addItemStacks(recipe.getInputs());
	}

	@Override
	public void draw(IJeiCompostingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		slot.draw(guiGraphics);

		float chance = recipe.getChance();
		int chancePercent = (int) Math.floor(chance * 100);
		String text = Translator.translateToLocalFormatted("gui.jei.category.compostable.chance", chancePercent);

		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;
		guiGraphics.drawString(font, text, 24, 5, 0xFF808080, false);
	}

	@Override
	public ResourceLocation getRegistryName(IJeiCompostingRecipe recipe) {
		return recipe.getUid();
	}
}
