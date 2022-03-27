// FCMOD

package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class FCBlockSaplingLegacy extends BlockSapling
{
	public static final String[] SAPLING_TYPES = new String[] {
			"oak", "spruce", "birch", "jungle",
			"oak", "spruce", "birch", "jungle",
			"oak", "spruce", "birch", "jungle",
			"oakMature", "spruceMature", "birchMature", "jungleMature",
	};

	private static final double m_dWidth = 0.8D;
	private static final double m_dHalfWidth = (m_dWidth / 2D);

	protected FCBlockSaplingLegacy(int iBlockID)
	{
		super(iBlockID);

		SetFurnaceBurnTime(FCEnumFurnaceBurnTime.KINDLING);
		SetFilterableProperties(Item.m_iFilterable_NoProperties);

		InitBlockBounds(0.5D - m_dHalfWidth, 0D, 0.5D - m_dHalfWidth, 
				0.5D + m_dHalfWidth, m_dHalfWidth * 2D, 0.5D + m_dHalfWidth);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		checkFlowerChange(world, i, j, k); // replaces call to the super method two levels up in the hierarchy

		if (world.provider.dimensionId != 1 && world.getBlockId(i, j, k) == blockID) // necessary because checkFlowerChange() may destroy the sapling
		{
			if (world.getBlockLightValue(i, j + 1, k) >= 9)
			{
				attemptToGrow(world, i, j, k, random);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.experienceLevel>=2 && player.isUsingSpecialKey())
		{
			boolean canEnchant = true;
			foundBlock:
			for(int i=-YYBlockTotemBottom.blockingRange; i<=YYBlockTotemBottom.blockingRange; i++)
			{
				for(int k=-YYBlockTotemBottom.blockingRange; k<=YYBlockTotemBottom.blockingRange; k++)
				{
					for(int y1=0; y1<256; y1++)
					{
						int x1 = x+i;
						int z1 = z+k;
						int id = world.getBlockId(x1, y1, z1);
						int metadata = world.getBlockMetadata(x1, y1, z1);
						if
						(
							id==YYStuffAndThings.yyBlockTotemBottom.blockID ||
							id==YYStuffAndThings.yyBlockTotemSapling.blockID ||
							(id==YYStuffAndThings.yyBlockTotemCarving.blockID && (metadata&8)==8)
						)
						{
							canEnchant=false;
							if(!world.isRemote)
							{
								int xd = YYBlockTotemBottom.blockingRange - Math.abs(i) +1;
								int zd = YYBlockTotemBottom.blockingRange - Math.abs(k) +1;
								player.addChatMessage
								(
									"Try doing that " + xd + " block" + (xd!=1?"s":"") + " to the " + (i<0 ? "East" : "West") +
									" or " + zd + " block" + (zd!=1?"s":"") + " to the " + (k>0 ? "North" : "South")
								);
							}
							break foundBlock;
						}
					}
				}
			}
			
			if(canEnchant)
			{
				player.experienceLevel-=2;
				world.setBlockWithNotify(x, y, z, YYStuffAndThings.yyBlockTotemSapling.blockID);
				return true;
			}
		}
		return false;
	}

	@Override
	public void growTree(World world, int x, int y, int z, Random random) {
		int treeType = world.getBlockMetadata(x, y, z) & 3;
		boolean success = false;

		int xOffset = 0;
		int zOffset = 0;

		boolean generatedHuge = false;

		boolean planter = Block.blocksList[world.getBlockId(x, y - 1, z)] instanceof FCBlockPlanterBase;

		if (treeType != 3) {
			world.setBlock(x, y, z, 0);            
		}

		if (treeType == 1) {
			success = FCUtilsTrees.GenerateTaiga2(world, random, x, y, z);
		} 
		else if (treeType == 2) {
			success = FCUtilsTrees.GenerateForest(world, random, x, y, z);
		} 
		else if (treeType == 3) {
			do {
				if (xOffset < -1) {
					break;
				}

				zOffset = 0;

				do {
					if (zOffset < -1) {
						break;
					}

					if (isSameSapling(world, x + xOffset, y, z + zOffset, 3) && 
							isSameSapling(world, x + xOffset + 1, y, z + zOffset, 3) && 
							isSameSapling(world, x + xOffset, y, z + zOffset + 1, 3) && 
							isSameSapling(world, x + xOffset + 1, y, z + zOffset + 1, 3))
					{
						if (GetSaplingGrowthStage(world, x + xOffset, y, z + zOffset) == 3 && 
								GetSaplingGrowthStage(world, x + xOffset + 1, y, z + zOffset) == 3 && 
								GetSaplingGrowthStage(world, x + xOffset, y, z + zOffset + 1) == 3 && 
								GetSaplingGrowthStage(world, x + xOffset + 1, y, z + zOffset + 1) == 3)
						{
							// clear all 4 saplings that make up the huge tree

							world.setBlock(x + xOffset, y, z + zOffset, 0);
							world.setBlock(x + xOffset + 1, y, z + zOffset, 0);
							world.setBlock(x + xOffset, y, z + zOffset + 1, 0);
							world.setBlock(x + xOffset + 1, y, z + zOffset + 1, 0);

							FCUtilsGenHugeTree hugeTree = new FCUtilsGenHugeTree(true, 10 + random.nextInt(20), 3, 3);
							success = hugeTree.generate(world, random, x + xOffset, y, z + zOffset);

							generatedHuge = true;
							break;
						}
						else {
							// we have 4 saplings, but they aren't all fully grown.  Bomb out entirely
							return;
						}
					}

					zOffset--;
				}
				while (true);

				if (generatedHuge) {
					break;
				}

				xOffset--;
			}
			while (true);

			if (!generatedHuge) {
				xOffset = zOffset = 0;
				world.setBlock(x, y, z, 0);

				success = FCUtilsTrees.GenerateTrees(world, random, x, y, z, 4 + random.nextInt(7), 3, 3, false);
			}
		}
		else {	// metadata is 0
			if (random.nextInt(10) == 0)
			{
				FCUtilsGenBigTree bigTree = new FCUtilsGenBigTree(true);

				success = bigTree.generate(world, random, x, y, z);
			}
			else {
				success = FCUtilsTrees.GenerateTrees(world, random, x, y, z);
			}
		}

		if (!success) {
			// restore saplings at full growth

			int saplingMetadata = treeType + (3 << 2);

			if (generatedHuge) {
				// replace all the saplings if a huge tree was grown

				world.setBlockAndMetadata(x + xOffset, y, z + zOffset, blockID, saplingMetadata);
				world.setBlockAndMetadata(x + xOffset + 1, y, z + zOffset, blockID, saplingMetadata);
				world.setBlockAndMetadata(x + xOffset, y, z + zOffset + 1, blockID, saplingMetadata);
				world.setBlockAndMetadata(x + xOffset + 1, y, z + zOffset + 1, blockID, saplingMetadata);
			}
			else {
				world.setBlockAndMetadata(x, y, z, blockID, saplingMetadata);
			}
		}
		else if (planter) {
			world.setBlockMetadata(x, y, z, treeType);

			//Block break sfx
			world.playAuxSFX(2001, x, y - 1, z, FCBetterThanWolves.fcBlockPlanterSoil.blockID);

			world.setBlockAndMetadata(x, y - 1, z, Block.wood.blockID, treeType | 12);
		}
	}

	@Override
	public boolean OnBlockSawed(World world, int i, int j, int k)
	{
		return false;
	}

	@Override
	protected boolean CanGrowOnBlock(World world, int i, int j, int k)
	{
		Block blockOn = Block.blocksList[world.getBlockId(i, j, k)];

		return blockOn != null && blockOn.CanSaplingsGrowOnBlock(world, i, j, k);
	}

	//------------- Class Specific Methods ------------//

	public int GetSaplingGrowthStage(World world, int i, int j, int k)
	{
		int iMetadata = world.getBlockMetadata(i, j, k);

		int iGrowthStage = (iMetadata & (~3)) >> 2;

			return iGrowthStage;
	}

	public void attemptToGrow(World world, int x, int y, int z, Random rand) {
		int growthChance = 64;

		int blockIDBelow = world.getBlockId(x, y - 1, z);
		Block blockBelow = Block.blocksList[blockIDBelow];

		if (blockBelow.GetIsFertilizedForPlantGrowth(world, x, y - 1, z)) {
			growthChance /= blockBelow.GetPlantGrowthOnMultiplier(world, x, y - 1, z, this);
		}

		int metadata = world.getBlockMetadata(x, y, z);
		int growthStage = (metadata & (~3)) >> 2;


		if (rand.nextInt(growthChance) == 0) {
			if (growthStage < 3) {
				growthStage++;
				metadata = (metadata & 3) | (growthStage << 2);

				world.setBlockMetadataWithNotify(x, y, z, metadata);

				if (growthStage == 3) {
					blockBelow.NotifyOfFullStagePlantGrowthOn(world, x, y - 1, z, this);
				}
			}
			else {
				if (!(blockBelow instanceof FCBlockPlanterBase) || blockBelow.GetIsFertilizedForPlantGrowth(world, x, y - 1, z)) {
					growTree(world, x, y, z, rand);
				}
			}
		}
	}

	//----------- Client Side Functionality -----------//

	private Icon[][] m_IconArray = new Icon[4][4]; 
	public static final String[] m_sBaseTextureNames = new String[] { "fcBlockSaplingOak_0", "fcBlockSaplingSpruce_0", "fcBlockSaplingBirch_0", "fcBlockSaplingJungle_0" };

	public void registerIcons(IconRegister register)
	{
		for (int iTempSaplingType = 0; iTempSaplingType < 4; iTempSaplingType++)
		{
			for (int iTempGrowthStage = 0; iTempGrowthStage < 4; iTempGrowthStage++)
			{
				m_IconArray[iTempSaplingType][iTempGrowthStage] = register.registerIcon(m_sBaseTextureNames[iTempSaplingType] + iTempGrowthStage);
			}
		}
	}

	public Icon getIcon(int iSide, int iMetadata)
	{
		int iSaplingType = iMetadata & 3;
		int iGrowthStage = (iMetadata & (~3)) >> 2;

			return m_IconArray[iSaplingType][iGrowthStage];
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));

		par3List.add(new ItemStack(par1, 1, 12));
		par3List.add(new ItemStack(par1, 1, 13));
		par3List.add(new ItemStack(par1, 1, 14));
		par3List.add(new ItemStack(par1, 1, 15));
	}
}
