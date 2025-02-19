package mezz.jei.gui.ingredients;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.common.util.StringUtil;
import mezz.jei.common.util.Translator;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListElementInfoTooltip implements ITooltipBuilder {
	private final List<FormattedText> tooltip = new ArrayList<>();

	@Override
	public void add(FormattedText component) {
		tooltip.add(component);
	}

	@Override
	public void addAll(Collection<? extends FormattedText> components) {
		tooltip.addAll(components);
	}

	@Override
	public void add(TooltipComponent component) {
		// ignored for the purposes of searching tooltips
	}

	@Override
	public void setIngredient(ITypedIngredient<?> typedIngredient) {
		// ignored for the purposes of searching tooltips
	}

	public Set<String> getStrings() {
		Set<String> result = new HashSet<>();
		for (FormattedText component : tooltip) {
			String string = component.getString();
			string = StringUtil.removeChatFormatting(string);
			string = Translator.toLowercaseWithLocale(string);
			// Split tooltip strings into words to keep them from being too long.
			// Longer strings are more expensive for the suffix tree to handle.
			String[] strings = string.split(" ");
			Collections.addAll(result, strings);
		}
		return result;
	}
}
