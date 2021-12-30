package net.minecraft.src;

public class YYContainerAnvil extends FCContainerWorkbench{
	
	public YYContainerAnvil(InventoryPlayer inventory, World world, int i, int j, int k) {
		super(inventory, world, i, j, k);
	}
	
	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		ItemStack craftedStack = CraftingManager.getInstance().findMatchingRecipe( craftMatrix, m_world );
		IRecipe recipe = CraftingManager.getInstance().FindMatchingRecipe(this.craftMatrix, m_world);

		if ( craftedStack == null )
		{
			craftedStack = YYCraftingManagerAnvil.getInstance().findMatchingRecipe( craftMatrix, m_world );
			recipe = YYCraftingManagerAnvil.getInstance().FindMatchingRecipe(this.craftMatrix, m_world);
		}

		craftResult.setInventorySlotContents( 0, craftedStack );

		((SlotCrafting) this.getSlot(0)).setRecipe(recipe);
	}
}
