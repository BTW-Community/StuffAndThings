// FCMOD

package net.minecraft.src;

import java.util.List;
import java.util.Iterator;

public class FCEntityPigZombie extends EntityPigZombie
{
	public FCEntityPigZombie( World world )
	{
		super( world );
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if(this.angerLevel>0)
		{
			this.angerLevel--;
		}
		if(this.angerLevel==0)
		{
			this.entityToAttack = null;
		}
		
		int x = MathHelper.floor_double(posX) + rand.nextInt(10) - 5;
		int y = MathHelper.floor_double(posY) + rand.nextInt(10) - 5;
		int z = MathHelper.floor_double(posZ) + rand.nextInt(10) - 5;
		if(worldObj.getBlockId(x, y, z) == Block.fire.blockID)
		{
			List nearbyEntityList = worldObj.getEntitiesWithinAABBExcludingEntity( this, boundingBox.expand( 32D, 32D, 32D ) );
				
			Iterator nearbyEntityIterator = nearbyEntityList.iterator();
			
			while ( nearbyEntityIterator.hasNext() )
			{
				Entity tempEntity = (Entity)nearbyEntityIterator.next();
				
				if ( tempEntity instanceof EntityPlayer )
				{
					EntityPlayer player = (EntityPlayer)tempEntity;
					
					int blockBelow = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(player.posY)-1, MathHelper.floor_double(player.posZ));
					if(blockBelow == Block.netherrack.blockID)
					{
						becomeAngryAt(player);
					}
				}
			}
		}
	}
	
	@Override
	public boolean attackEntityFrom( DamageSource source, int iDamage )
	{
		// need to do super call first so that pigmen getting angry at ghasts will be overridden 
		// by getting angry at nearby players
		
		boolean bReturnValue = EntityMobAttackEntityFrom( source, iDamage );
		
		if ( !isEntityInvulnerable() )
		{
			Entity attacker = source.getEntity();
			
			if ( attacker instanceof EntityPlayer)
			{
				becomeAngryAt(attacker );
				
				AngerNearbyPigmen( attacker );
			}
		}
		
		return bReturnValue;
	}
	
	@Override
	public void CheckForScrollDrop()
	{    	
		if ( rand.nextInt( 1000 ) == 0 )
		{
			ItemStack itemstack = new ItemStack( FCBetterThanWolves.fcItemArcaneScroll, 1, 
				Enchantment.fireProtection.effectId );
			
			entityDropItem( itemstack, 0F );
		}
	}
	
	@Override
	protected void dropHead()
	{
		// Empty override to prevent dropping zombie heads
	}
	
	@Override
	protected void addRandomArmor()
	{
		if ( rand.nextFloat() < 0.05F )
		{
			setCurrentItemOrArmor( 0, new ItemStack(Item.swordGold ) );
			
			equipmentDropChances[0] = 0.99F;
		}
	}
	
	@Override
	protected void dropFewItems( boolean bKilledByPlayer, int iFortuneModifier )
	{
		int iFleshCount = rand.nextInt( 2 + iFortuneModifier );

		for ( int iTempCount = 0; iTempCount < iFleshCount; iTempCount++ )
		{
			dropItem( Item.rottenFlesh.itemID, 1 );
		}

		if ( rand.nextFloat() <= 0.1F || worldObj.provider.dimensionId == -1 )
		{
			int iNuggetCount = rand.nextInt( 2 + iFortuneModifier );
	
			for ( int iTempCount = 0; iTempCount < iNuggetCount; iTempCount++ )
			{
				dropItem( FCBetterThanWolves.fcItemChunkGoldOre.itemID, 1 );
			}
		}
	}
	
	@Override
	protected void dropRareDrop( int iBonusDrop )
	{
		// override gold ingot drop in parent
	}
	
	//------------- Class Specific Methods ------------//
	
	public void BecomeAngryWhenPigAttacked( Entity attackingEntity )
	{
		becomeAngryAt( attackingEntity );
	}
	
	protected void AngerNearbyPigmen( Entity target )
	{
		List nearbyEntityList = worldObj.getEntitiesWithinAABBExcludingEntity( this, 
			boundingBox.expand( 32D, 32D, 32D ) );
		
		Iterator nearbyEntityIterator = nearbyEntityList.iterator();
		
		while ( nearbyEntityIterator.hasNext() )
		{
			Entity tempEntity = (Entity)nearbyEntityIterator.next();
			
			if ( tempEntity instanceof FCEntityPigZombie )
			{
				FCEntityPigZombie tempPigmen = (FCEntityPigZombie)tempEntity;
				
				tempPigmen.becomeAngryAt( target );
			}
		}
	}
}