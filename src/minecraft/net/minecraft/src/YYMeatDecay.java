package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class YYMeatDecay
{
	
	public static HashMap<Integer, YYMeatDecay> stats;
	
	static
	{
		stats = new HashMap<Integer, YYMeatDecay>();
		stats.put(Item.beefRaw.itemID, new YYMeatDecay(YYStuffAndThings.yyItemBeefDecaying, 200000));
		stats.put(Item.porkRaw.itemID, new YYMeatDecay(YYStuffAndThings.yyItemPorkDecaying, 100000));
		stats.put(Item.chickenRaw.itemID, new YYMeatDecay(YYStuffAndThings.yyItemChickenDecaying, 150000));
		stats.put(FCBetterThanWolves.fcItemMuttonRaw.itemID, new YYMeatDecay(YYStuffAndThings.yyItemMuttonDecaying, 150000));
		stats.put(Item.fishRaw.itemID, new YYMeatDecay(YYStuffAndThings.yyItemFishDecaying, 100000));
		stats.put(YYStuffAndThings.yyItemBeefDecaying.itemID, new YYMeatDecay(YYStuffAndThings.yyItemSpoiledMeat, 70000));
		stats.put(YYStuffAndThings.yyItemPorkDecaying.itemID, new YYMeatDecay(YYStuffAndThings.yyItemSpoiledMeat, 70000));
		stats.put(YYStuffAndThings.yyItemChickenDecaying.itemID, new YYMeatDecay(YYStuffAndThings.yyItemSpoiledMeat, 70000));
		stats.put(YYStuffAndThings.yyItemFishDecaying.itemID, new YYMeatDecay(YYStuffAndThings.yyItemSpoiledMeat, 70000));
		stats.put(YYStuffAndThings.yyItemMuttonDecaying.itemID, new YYMeatDecay(YYStuffAndThings.yyItemSpoiledMeat, 70000));
		stats.put(YYStuffAndThings.yyItemSpoiledMeat.itemID, new YYMeatDecay(null, 30000));
	}
	
	public Item to;
	public int probability;
	
	public YYMeatDecay(Item to, int probability)
	{
		this.to = to;
		this.probability = probability;
	}
	
	public int getDecayNumber(ItemStack stack, Random random)
	{
		int result = 0;
		for(int i=0; i<stack.stackSize; i++)
		{
			if(random.nextInt(probability)==0)
			{
				result++;
			}
		}
		return Math.min(result, stack.stackSize);
	}
	
	public static YYMeatDecay getDecay(ItemStack stack)
	{
		YYMeatDecay result = null;
		if(stack!=null)
		{
			result = stats.get(stack.itemID);
		}
		return result;
	}
	
	public static ArrayList<ItemStack> decayMeatInInventory(IInventory inventory, Random rand, int minSlotIndex, int maxSlotIndex)
	{
		ArrayList<ItemStack> overflow = new ArrayList<ItemStack>();
		for(int slot=0; slot<inventory.getSizeInventory(); slot++)
		{
			ItemStack stack = inventory.getStackInSlot(slot);
			YYMeatDecay decay = getDecay(stack);
			if(decay == null) continue;
			int decayNumber = decay.getDecayNumber(stack, rand);
			if(decayNumber==0) continue;
			
			ItemStack resultStack = decay.to == null ? null : new ItemStack(decay.to, decayNumber);
			if(decayNumber==stack.stackSize)
			{
				inventory.setInventorySlotContents(slot, resultStack);
			}
			else
			{
				stack.stackSize -= decayNumber;
				if(resultStack != null && !FCUtilsInventory.AddItemStackToInventoryInSlotRange(inventory, resultStack, minSlotIndex, maxSlotIndex))
				{
					overflow.add(resultStack);
				}
			}
		}
		return overflow;
	}
	
}
