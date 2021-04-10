package net.minecraft.src;

public class YYBaitMod extends FCAddOn{
	
	public static Item yyItemBait;
	public static YYItemBaitMix yyItemBaitMix;
	
	public static YYBlockLargeBasket yyBlockLargeBasket;
	public static int yyLargeBasketContainerID = 240;
	public static Item yyBlockItemLargeBasket;
	public static Block yyBlockTorchLongTermUnlit;
	public static Block yyBlockTorchLongTermBurning;
	public static YYItemBarkStitching yyItemBarkStitching;
	public static Item yyItemStitchedBark;
	
	public static Item yyItemBrokenBrick;
	public static YYBlockHempBrickWet yyBlockHempBrickWet;
	public static Item yyItemHempBrickWet;
	public static YYBlockHempBrickDry yyBlockHempBrickDry;
	public static Item yyItemHempBrickDry;
	
	
	@Override
	public void Initialize() {
		//bowl nerf
		FCRecipes.RemoveVanillaRecipe(new ItemStack(Item.bowlEmpty, 4), new Object[] {"# #", " # ", '#', Block.planks});
		FCRecipes.AddRecipe(new ItemStack(Item.bowlEmpty), new Object[] {"# #", " # ", '#', Block.planks});
		
		// bait
		FCAddOnHandler.LogMessage("Initializing Bait Addon");
		yyItemBait = new Item(26000).SetBuoyant().setUnlocalizedName("yyItemBait").setCreativeTab(CreativeTabs.tabFood).setMaxStackSize(16);
		yyItemBaitMix = new YYItemBaitMix(26001);
		FCRecipes.AddShapelessRecipe(new ItemStack(yyItemBaitMix, 1, YYItemBaitMix.m_iBaitMixMaxDamage-1), new Object[] {new ItemStack(Item.bowlEmpty), new ItemStack(Item.rottenFlesh), new ItemStack(Item.dyePowder, 1, 15)} );
		FCRecipes.AddShapelessRecipe(new ItemStack(yyItemBait, 6), new Object[] {new ItemStack(FCBetterThanWolves.fcItemDogFood)});
		FCRecipes.AddCauldronRecipe(new ItemStack(FCBetterThanWolves.fcItemDogFood, 2), new ItemStack[] {new ItemStack(Item.rottenFlesh, 4), new ItemStack(Item.dyePowder, 4, 15), new ItemStack(Item.sugar, 1)});
		
		// large basket
		yyBlockLargeBasket = new YYBlockLargeBasket(1500);
		TileEntity.addMapping(YYTileEntityLargeBasket.class, "yyLargeBasket");
		yyBlockItemLargeBasket = new ItemBlock(1500-256);
		FCRecipes.AddRecipe(new ItemStack(yyBlockLargeBasket, 1), new Object[] {"WWW", "MWM", "MSM", 'W', new ItemStack(FCBetterThanWolves.fcItemWickerPiece), 'M', new ItemStack(FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, 32767), 'S', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767)});
		
		// chest iron
		FCRecipes.RemoveVanillaRecipe(new ItemStack(FCBetterThanWolves.fcBlockChest), new Object[] {"###", "# #", "###", '#', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767)});
		FCRecipes.AddRecipe(new ItemStack(FCBetterThanWolves.fcBlockChest), new Object[] {"SSS", "SIS", "SSS", 'S', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767), 'I', new ItemStack(Item.ingotIron, 1)});
		Item.itemsList[FCBetterThanWolves.fcBlockChest.blockID].SetNotIncineratedInCrucible();
		FCRecipes.AddStokedCrucibleRecipe(new ItemStack(FCBetterThanWolves.fcItemNuggetIron, 6), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcBlockChest)});
		FCRecipes.RemoveVanillaRecipe(new ItemStack(FCBetterThanWolves.fcHopper), new Object[] {"# #", "XYX", " Z ", '#', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767), 'X', FCBetterThanWolves.fcItemGear, 'Y', Block.pressurePlatePlanks, 'Z', new ItemStack(FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, 32767)});
		FCRecipes.AddRecipe(new ItemStack(FCBetterThanWolves.fcHopper), new Object[] {"#C#", "XYX", " Z ", '#', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767), 'X', FCBetterThanWolves.fcItemGear, 'Y', Block.pressurePlatePlanks, 'Z', new ItemStack(FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, 32767), 'C', new ItemStack(FCBetterThanWolves.fcBlockChest)});
		Item.itemsList[FCBetterThanWolves.fcHopper.blockID].SetNotIncineratedInCrucible();
		FCRecipes.AddStokedCrucibleRecipe(new ItemStack(FCBetterThanWolves.fcItemNuggetIron, 6), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcHopper)});
		
		// hamper
		FCRecipes.RemoveVanillaRecipe(new ItemStack(FCBetterThanWolves.fcBlockHamper), new Object[] {"###", "#P#", "###", '#', FCBetterThanWolves.fcItemWickerPiece, 'P', Block.planks});
		FCRecipes.AddRecipe(new ItemStack(FCBetterThanWolves.fcBlockHamper), new Object[] {"###", "# #", "###", '#', FCBetterThanWolves.fcItemWickerPiece});
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemWickerPiece, 4), new Object[] {new ItemStack(FCBetterThanWolves.fcBlockHamper)});
		
		// basket
		FCRecipes.RemoveVanillaShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemWickerPiece, 1), new Object[] {new ItemStack(FCBetterThanWolves.fcBlockBasketWicker)});
		yyItemBarkStitching = new YYItemBarkStitching(26002);
		yyItemStitchedBark = new Item(26003).SetBuoyant().setUnlocalizedName("yyItemStitchedBark");
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcBlockBasketWicker), new Object[] {new ItemStack(yyItemStitchedBark), new ItemStack(yyItemStitchedBark)});
		FCRecipes.AddShapelessRecipe(new ItemStack(yyItemBarkStitching, 1, YYItemBarkStitching.m_iBarkStitchingMaxDamage-1), new Object[] {new ItemStack(FCBetterThanWolves.fcItemBark), new ItemStack(FCBetterThanWolves.fcItemBark), new ItemStack(FCBetterThanWolves.fcItemBark), new ItemStack(FCBetterThanWolves.fcItemHempFibers)});
		FCRecipes.AddShapelessRecipe(new ItemStack(yyItemBarkStitching, 1, YYItemBarkStitching.m_iBarkStitchingMaxDamage-1), new Object[] {new ItemStack(FCBetterThanWolves.fcItemBark), new ItemStack(FCBetterThanWolves.fcItemBark), new ItemStack(FCBetterThanWolves.fcItemBark), new ItemStack(Item.silk)});
		FCRecipes.RemoveVanillaRecipe(new ItemStack(FCBetterThanWolves.fcBlockBasketWicker), new Object[] {"##", "##", '#', FCBetterThanWolves.fcItemWickerPiece});
		
		// torch stuff
		yyBlockTorchLongTermUnlit = new YYBlockTorchLongTermUnlit(1501);
		yyBlockTorchLongTermBurning = new YYBlockTorchLongTermBurning(1502);
		TileEntity.addMapping(YYTileEntityTorchLongTerm.class, "yyTorchLongTerm");
		Item.itemsList[yyBlockTorchLongTermUnlit.blockID] = new YYItemBlockTorchLongTermIdle(yyBlockTorchLongTermUnlit.blockID-256);
		Item.itemsList[yyBlockTorchLongTermBurning.blockID] = new YYItemBlockTorchLongTermBurning(yyBlockTorchLongTermBurning.blockID-256);
		FCRecipes.AddShapelessRecipe(new ItemStack(yyBlockTorchLongTermUnlit), new ItemStack[] {new ItemStack(Item.redstone), new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit)});
		
		// changed blocks
		Block.oreDiamond.SetChiselsEffectiveOn(false);
		Block.oreRedstone.SetChiselsEffectiveOn(false);
		Block.torchRedstoneActive.setLightValue(0);
		Block.blockRedstone.setLightValue(0);
		
		
		// hardcore compass
		FCRecipes.RemoveVanillaRecipe(new ItemStack(Item.compass, 1), new Object[] {" # ", "#X#", " # ", '#', FCBetterThanWolves.fcItemNuggetIron, 'X', Item.redstone});
		FCRecipes.AddRecipe(new ItemStack(Item.compass, 1), new Object[] {" # ", "#X#", " # ", '#', Item.ingotIron, 'X', FCBetterThanWolves.fcItemSoulUrn});
		
		
		// brick oven recovery
		yyItemBrokenBrick = new Item(26004).setUnlocalizedName("yyItemBrokenBrick");
		yyBlockHempBrickWet = new YYBlockHempBrickWet(1503);
		yyItemHempBrickWet = new YYItemHempBrickWet(26005);
		TileEntity.addMapping(YYTileEntityHempBrickWet.class, "yyBrickHempWet");
		yyBlockHempBrickDry = new YYBlockHempBrickDry(1504);
		yyItemHempBrickDry = new YYItemHempBrickDry(26006);
		TileEntity.addMapping(YYTileEntityHempBrickDry.class, "yyBrickHempDry");
		FCRecipes.AddRecipe(new ItemStack(yyItemHempBrickDry), new Object[] {"FBF", "FFF", "FBF", 'B', yyItemBrokenBrick, 'F', FCBetterThanWolves.fcItemHempFibers});
		
		
		FCAddOnHandler.LogMessage("Bait Addon Initialization Complete");
	}

	@Override
	public String GetLanguageFilePrefix()
	{
		return "YYB";
	}



}
