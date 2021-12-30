// FCMOD

package net.minecraft.src;

public class FCItemFishingRod extends ItemFishingRod
{
	public FCItemFishingRod( int iItemID )
	{
		super( iItemID );

		setMaxDamage( 32 );

		SetBuoyant();
		SetFilterableProperties( m_iFilterable_Narrow );

		setUnlocalizedName( "fishingRod" );
	}

	public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player )
	{
		boolean appliedBait = false;
		
		if (player.fishEntity == null) {
			for (int i = 0; i < 9; i++) {
				ItemStack stackInSlot = player.inventory.getStackInSlot(i);

				if (stackInSlot != null && stackInSlot.itemID == YYStuffAndThings.yyItemBait.itemID) {
					world.playSoundAtEntity(player, "mob.slime.attack", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

					player.inventory.consumeInventoryItem(stackInSlot.itemID);
					ItemStack baitedRodStack = player.getCurrentEquippedItem().copy();
					baitedRodStack.itemID = FCBetterThanWolves.fcItemFishingRodBaited.itemID;
					player.inventory.setInventorySlotContents(player.inventory.currentItem, baitedRodStack);

					appliedBait = true;
					break;
				}
			}

			if (appliedBait) {
				return stack;
			}
		}
		
		player.swingItem();
		return super.onItemRightClick(stack, world, player);
	}
}