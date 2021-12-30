package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;

public class YYCraftingManagerAnvil {
	private static final YYCraftingManagerAnvil instance = new YYCraftingManagerAnvil();
	private ArrayList recipes;
	
	public static final YYCraftingManagerAnvil getInstance()
	{
		return instance;
	}
	
	private YYCraftingManagerAnvil()
	{
		recipes = new ArrayList();  
	}
	
	void addRecipe(ItemStack itemstack, Object aobj[])
	{
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		if(aobj[i] instanceof String[])
		{
			String as[] = (String[])aobj[i++];
			for(int l = 0; l < as.length; l++)
			{
				String s2 = as[l];
				k++;
				j = s2.length();
				s = (new StringBuilder()).append(s).append(s2).toString();
			}

		} else
		{
			while(aobj[i] instanceof String) 
			{
				String s1 = (String)aobj[i++];
				k++;
				j = s1.length();
				s = (new StringBuilder()).append(s).append(s1).toString();
			}
		}
		HashMap hashmap = new HashMap();
		for(; i < aobj.length; i += 2)
		{
			Character character = (Character)aobj[i];
			ItemStack itemstack1 = null;
			if(aobj[i + 1] instanceof Item)
			{
				itemstack1 = new ItemStack((Item)aobj[i + 1]);
			} else
			if(aobj[i + 1] instanceof Block)
			{
				itemstack1 = new ItemStack((Block)aobj[i + 1], 1, FCUtilsInventory.m_iIgnoreMetadata );
			} else
			if(aobj[i + 1] instanceof ItemStack)
			{
				itemstack1 = (ItemStack)aobj[i + 1];
			}
			hashmap.put(character, itemstack1);
		}

		ItemStack aitemstack[] = new ItemStack[j * k];
		for(int i1 = 0; i1 < j * k; i1++)
		{
			char c = s.charAt(i1);
			if(hashmap.containsKey(Character.valueOf(c)))
			{
				aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c))).copy();
			} else
			{
				aitemstack[i1] = null;
			}
		}

		recipes.add(new ShapedRecipes(j, k, aitemstack, itemstack));
	}
	
	public ItemStack findMatchingRecipe( InventoryCrafting inventorycrafting, World world )
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			IRecipe irecipe = (IRecipe)recipes.get(i);
			if(irecipe.matches(inventorycrafting, world))
			{
				return irecipe.getCraftingResult(inventorycrafting);
			}
		}

		return null;
	}
	
	public IRecipe FindMatchingRecipe( InventoryCrafting inventory, World world )
	{
		for ( int iTempIndex = 0; iTempIndex < this.recipes.size(); ++iTempIndex)
		{
			IRecipe tempRecipe = (IRecipe)this.recipes.get(iTempIndex);

			if ( tempRecipe.matches( inventory, world ) )
			{
				return tempRecipe;
			}
		}

		return null;
	}
}
